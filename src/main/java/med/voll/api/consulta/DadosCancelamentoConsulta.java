package med.voll.api.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull(message = "ID da consulta é obrigatório")
        Long idConsulta,

        @NotNull(message = "Motivo do cancelamento é obrigatório")
        MotivoCancelamento motivo
) {
    public MotivoCancelamento motivoCancelamento() {
        return motivo;  // Corrigido para retornar o motivo
    }
}