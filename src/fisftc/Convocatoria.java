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
public class Convocatoria {

    static final String idConvocatoria = "idConvocatoria";
    static final String anio = "anio";
    static final String mes = "mes";
    static final String tipo = "tipo";
    static final String tabla = "convocatoria";

    private int idConvocatoriaM;
    private String anioM;
    private String mesM;
    private String tipoM;

    /*Constructor Convocatoria*/
    public Convocatoria() {
        this.anioM = "";
        this.mesM = "";
        this.tipoM = "";
    }

    public int getidConvocatoriaM() {
        return idConvocatoriaM;
    }

    public String getAnioM() {
        return anioM;
    }

    public String getMesM() {
        return mesM;
    }

    public String getTipoM() {
        return tipoM;
    }

    public void setidConvocatoriaM(int idConvocatoriaM) {
        this.idConvocatoriaM = idConvocatoriaM;
    }

    public void setAnioM(String anioM) {
        this.anioM = anioM;
    }

    public void setMesM(String mesM) {
        this.mesM = mesM;
    }

    public void setTipoM(String tipoM) {
        this.tipoM = tipoM;
    }
}
