package fisftc;

public class Asignado {

    static final String idTfg = "idTfg";
    static final String idAlumno = "idAlumno";
    static final String fechaAsignacion = "fechaAsignacion";
    static final String tabla = "fechaAsignacion";

    private int idTfgM;
    private int idAlumnoM;
    private String fechaAsignacionM;

    public Asignado() {
        this.idTfgM = 0;
        this.idAlumnoM = 0;
        this.fechaAsignacionM = "";
    }

    public int getIdTfgM() {
        return idTfgM;
    }

    public int getIdAlumnoM() {
        return idAlumnoM;
    }

    public String getFechaAsignacionM() {
        return fechaAsignacionM;
    }

    public void setIdTfgM(int idTfgM) {
        this.idTfgM = idTfgM;
    }

    public void setIdAlumnoM(int idAlumnoM) {
        this.idAlumnoM = idAlumnoM;
    }

    public void setFechaAsignacionM(String fechaAsignacionM) {
        this.fechaAsignacionM = fechaAsignacionM;
    }

}
