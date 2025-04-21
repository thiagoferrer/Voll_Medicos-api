package med.voll.api.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosCadastroConsulta(
        @NotNull
        Long idPaciente,

        Long idMedico,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade
) {}