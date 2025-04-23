package med.voll.api.domian.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domian.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(

        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
