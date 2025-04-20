CREATE TABLE pacientes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    cpf VARCHAR(14) NOT NULL,
    logradouro VARCHAR(100),
    bairro VARCHAR(100),
    cep VARCHAR(9),
    complemento VARCHAR(100),
    numero VARCHAR(20),
    cidade VARCHAR(100),
    uf VARCHAR(2),
    ativo BOOLEAN
);
