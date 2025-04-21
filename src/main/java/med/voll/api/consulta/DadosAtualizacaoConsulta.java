package med.voll.api.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record DadosAtualizacaoConsulta(
        @Future
        LocalDateTime novaData,

        @NotNull
        MotivoCancelamento motivoCancelamento
) {}