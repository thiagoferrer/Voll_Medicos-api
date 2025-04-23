package med.voll.api.domian.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domian.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
