package med.voll.api.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(

        @NotBlank
        String logradouro,

        @NotBlank
        String bairro,

        @NotBlank
        @Pattern(regexp = "\\d{8}") //Formato do CEP
        String cep,

        @NotBlank
        String cidade,

        @NotBlank
        String uf,

        String complemento,

        String numero) {

        // Construtor padrão, getters e setters são gerados automaticamente pelo Lombok
        public Endereco toEntity() {
                return new Endereco(logradouro, bairro, cep, cidade, uf, complemento, numero);
        }
}
