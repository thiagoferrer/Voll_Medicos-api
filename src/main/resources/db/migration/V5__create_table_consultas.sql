CREATE TABLE consultas (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    data DATETIME NOT NULL,
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
    CONSTRAINT fk_consulta_medico FOREIGN KEY (medico_id) REFERENCES medicos(id)
);
