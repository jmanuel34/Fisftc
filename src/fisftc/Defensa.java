package fisftc;

import java.util.Date;

public class Defensa {

    static final String idTfg = "idTfg";
    static final String idAlumno = "idAlumno";
    static final String idConvocatoria = "idConvocatoria";
    static final String fechaDefensa = "fechaDefensa";
    static final String nota = "nota";
    static final String tabla = "defensa";

    private int idTfgM;
    private int idAlumnoM;
    private int idConvocatoriaM;
    private String fechaDefensaM;
    private float notaM;

    public Defensa() {
        this.idTfgM = 0;
        this.idAlumnoM = 0;
        this.idConvocatoriaM = 0;
        this.fechaDefensaM = null;
        this.notaM = -1;// Así marcaremos que no está puesta la nota aún
    }

    public int getIdTfgM() {
        return idTfgM;
    }

    public int getIdAlumnoM() {
        return idAlumnoM;
    }

    public int getIdConvocatoriaM() {
        return idConvocatoriaM;
    }

    public String getFechaDefensaM() {
        return fechaDefensaM;
    }

    public float getNotaM() {
        return notaM;
    }

    public void setIdTfgM(int idTfgM) {
        this.idTfgM = idTfgM;
    }

    public void setIdAlumnoM(int idAlumnoM) {
        this.idAlumnoM = idAlumnoM;
    }

    public void setIdConvocatoriaM(int idConvocatoriaM) {
        this.idConvocatoriaM = idConvocatoriaM;
    }

    public void setFechaDefensaM(String fechaDefensaM) {
        this.fechaDefensaM = fechaDefensaM;
    }

    public void setNotaM(float notaM) {
        this.notaM = notaM;
    }
}
