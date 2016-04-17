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
public class Profesor {

    static final String idProfesor = "idProfesor";
    static final String nombre = "nombre";
    static final String ape1 = "ape1";
    static final String ape2 = "ape2";
    static final String emailProfesor = "emailProfesor";
    static final String despacho = "despacho";
    static final String estado = "estado";
    static final String tabla = "profesor";

    private int idProfesorM;
    private String nombreM;
    private String ape1M;
    private String ape2M;
    private String emailProfesorM;
    private String despachoM;
    private Boolean estadoM;

    /*Constructor profesor*/
    public Profesor() {
        this.idProfesorM = 0;
        this.nombreM = "";
        this.ape1M = "";
        this.ape2M = "";
        this.despachoM = "";
        this.emailProfesorM = "";
        this.estadoM = false;
    }

    public int getIdProfesorM() {
        return idProfesorM;
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

    public String getEmailProfesorM() {
        return emailProfesorM;
    }

    public String getDespachoM() {
        return despachoM;
    }

    public Boolean isEstadoM() {
        return estadoM;
    }

    public void setIdProfesorM(int idProfesorM) {
        this.idProfesorM = idProfesorM;
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

    public void setEmailProfesorM(String emailProfesorM) {
        this.emailProfesorM = emailProfesorM;
    }

    public void setDespachoM(String despachoM) {
        this.despachoM = despachoM;
    }

    public void setEstadoM(Boolean estadoM) {
        this.estadoM = estadoM;
    }

}
