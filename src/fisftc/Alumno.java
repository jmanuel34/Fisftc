/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fisftc;

/**
 *
 * @author Damian
 */
public class Alumno {

    static final String idAlumno = "idAlumno";
    static final String nombre = "nombre";
    static final String ape1 = "ape1";
    static final String ape2 = "ape2";
    static final String emailAlumno = "emailAlumno";
    static final String numMat = "numMat";
    static final String tabla = "ALUMNO";
    static final String idtfg = "idTfg";
    static final String fechaAsignacion = "fechaAsignacion";

    private int idAlumnoM;
    private int idtfgM;
    private String nombreM;
    private String ape1M;
    private String ape2M;
    private String emailAlumnoM;
    private String numMatM;
    private String fechaAsignacionM;
    

    /*Constructor alumno*/
    public Alumno() {
        this.idAlumnoM = 0;
        this.nombreM = "";
        this.ape1M = "";
        this.ape2M = "";
        this.emailAlumnoM = "";
        this.numMatM = "";
    }

    public int getIdAlumnoM() {
        return idAlumnoM;
    }

    public String getNombreM() {
        return nombreM;
    }

    public String getApe1M() {
        return ape1M;
    }

    public String getApe2M() {
        return ape2M;
    }

    public String getEmailAlumnoM() {
        return emailAlumnoM;
    }

    public String getNumMatM() {
        return numMatM;
    }

    
    public int getIdtfgM() {
        return idtfgM;
    }

    public String getFechaAsignacionM() {
        return fechaAsignacionM;
    }

    public void setFechaAsignacionM(String fechaAsignacionM) {
        this.fechaAsignacionM = fechaAsignacionM;
    }

    public void setIdtfgM(int idtfgM) {
        this.idtfgM = idtfgM;
    }
    public void setIdAlumnoM(int idAlumnoM) {
        this.idAlumnoM = idAlumnoM;
    }

    public void setNombreM(String nombreM) {
        this.nombreM = nombreM;
    }

    public void setApe1M(String ape1M) {
        this.ape1M = ape1M;
    }

    public void setApe2M(String ape2M) {
        this.ape2M = ape2M;
    }

    public void setEmailAlumnoM(String emailAlumnoM) {
        this.emailAlumnoM = emailAlumnoM;
    }

    public void setNumMatM(String numMatM) {
        this.numMatM = numMatM;
    }

}
