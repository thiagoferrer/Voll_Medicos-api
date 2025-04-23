package med.voll.api.domian.consulta;

import jakarta.validation.Valid;
import med.voll.api.exception.ValidacaoException;
import med.voll.api.domian.medico.Medico;
import med.voll.api.domian.medico.MedicoRepository;
import med.voll.api.domian.paciente.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AgendaDeConsultasService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public AgendaDeConsultasService(
            ConsultaRepository consultaRepository,
            MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public DetalhesConsultaDto agendar(DadosCadastroConsulta dados) {
        validarDadosAgendamento(dados);

        var paciente = pacienteRepository.findById(dados.idPaciente())
                .orElseThrow(() -> new ValidacaoException("Paciente não encontrado"));

        var medico = escolherMedico(dados);

        var consulta = new Consulta(dados, medico, paciente);
        consultaRepository.save(consulta);

        return new DetalhesConsultaDto(consulta);
    }

    @Transactional
    public DetalhesConsultaDto cancelar(Long id, @Valid DadosCancelamentoConsulta dados) {
        var consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Consulta não encontrada"));

        validarCancelamento(consulta);

        consulta.cancelar(dados);
        // O save é opcional aqui pois o cancelar() modifica o objeto gerenciado
        return new DetalhesConsultaDto(consulta);
    }

    private void validarCancelamento(Consulta consulta) {
        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new ValidacaoException("Consulta já está cancelada");
        }

        if (consulta.getStatus() == StatusConsulta.REALIZADA) {
            throw new ValidacaoException("Não é possível cancelar uma consulta já realizada");
        }

        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, consulta.getData()).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("Cancelamento deve ser feito com antecedência mínima de 30 minutos");
        }
    }

    private Medico escolherMedico(DadosCadastroConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.findById(dados.idMedico())
                    .orElseThrow(() -> new ValidacaoException("Médico não encontrado"));
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(
                        dados.especialidade(), dados.data())
                .orElseThrow(() -> new ValidacaoException("Nenhum médico disponível para esta data"));
    }

    private void validarDadosAgendamento(DadosCadastroConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Paciente não encontrado");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Médico não encontrado");
        }

        validarHorarioFuncionamento(dados.data());
        validarAntecedenciaMinima(dados.data());
        validarPacienteSemOutraConsultaNoDia(dados.idPaciente(), dados.data());

        if (dados.idMedico() != null) {
            validarMedicoSemOutraConsultaNoMesmoHorario(dados.idMedico(), dados.data());
        }
    }

    private void validarHorarioFuncionamento(LocalDateTime data) {
        var domingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = data.getHour() < 7;
        var depoisDoEncerramento = data.getHour() > 18;

        if (domingo || antesDaAbertura || depoisDoEncerramento) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }
    }

    private void validarAntecedenciaMinima(LocalDateTime data) {
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, data).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }
    }

    private void validarPacienteSemOutraConsultaNoDia(Long idPaciente, LocalDateTime data) {
        var primeiroHorario = data.withHour(7).withMinute(0);
        var ultimoHorario = data.withHour(18).withMinute(0);

        var pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween(
                idPaciente, primeiroHorario, ultimoHorario);

        if (pacientePossuiOutraConsultaNoDia) {
            throw new ValidacaoException("Paciente já possui uma consulta agendada nesse dia");
        }
    }

    private void validarMedicoSemOutraConsultaNoMesmoHorario(Long idMedico, LocalDateTime data) {
        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndDataAndStatus(
                idMedico, data, StatusConsulta.AGENDADA);

        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse horário");
        }
    }
}