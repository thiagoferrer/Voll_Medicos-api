package med.voll.api.domian.consulta;

public enum MotivoCancelamento {
    PACIENTE_DESISTIU,
    MEDICO_CANCELOU,
    OUTROS;

    public static boolean isValid(String name) {
        try {
            MotivoCancelamento.valueOf(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}