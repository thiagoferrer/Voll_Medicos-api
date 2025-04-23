package med.voll.api.domian.consulta;

import java.time.LocalDateTime;

public record DetalhesConsultaCanceladaDto(
        Long id,
        Long idMedico,
        Long idPaciente,
        LocalDateTime data,
        MotivoCancelamento motivo,
        String status
) {
    public DetalhesConsultaCanceladaDto(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getMedico().getId(),
                consulta.getPaciente().getId(),
                consulta.getData(),
                consulta.getMotivoCancelamento(),
                "CANCELADA"
        );
    }
}