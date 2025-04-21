package med.voll.api.consulta;

import java.time.LocalDateTime;

public record DetalhesConsultaDto(
        Long id,
        Long idMedico,
        String nomeMedico,
        Long idPaciente,
        String nomePaciente,
        LocalDateTime data,
        StatusConsulta status,
        MotivoCancelamento motivoCancelamento
) {
    public DetalhesConsultaDto(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getMedico() != null ? consulta.getMedico().getId() : null,
                consulta.getMedico() != null ? consulta.getMedico().getNome() : null,
                consulta.getPaciente().getId(),
                consulta.getPaciente().getNome(),
                consulta.getData(),
                consulta.getStatus(),
                consulta.getMotivoCancelamento()
        );
    }
}