/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fisftc;

public class TFG {

        static final String idTfg = "idTfg";
        static final String idProfesor = "idProfesor";
        static final String titulo = "titulo";
        static final String descripcion = "descripcion";
        static final String fecha = "fechaRegistro";
        static final String finalizado = "finalizado";
        static final String tabla = "tfg";

        private int idTfgM;
        private int idProfesorM;
        private String tituloM;
        private String descripcionM;
        private String fechaM;
        private boolean finalizadoM;

        public TFG() {
            this.idTfgM = 0;
            this.idProfesorM = 0;
            this.tituloM = "";
            this.descripcionM = "";
            this.fechaM = "";
            this.finalizadoM = false;
        }

        public int getIdTfgM() {
            return idTfgM;
        }

        public int getIdProfesorM() {
            return idProfesorM;
        }

        public String getTituloM() {
            return tituloM;
        }

        public String getDescripcionM() {
            return descripcionM;
        }

        public String getFechaM() {
            return fechaM;
        }

        public boolean getFinalizadoM() {
            return finalizadoM;
        }

        public void setIdTfgM(int idTfgM) {
            this.idTfgM = idTfgM;
        }

        public void setIdProfesorM(int idProfesorM) {
            this.idProfesorM = idProfesorM;
        }

        public void setTituloM(String tituloM) {
            this.tituloM = tituloM;
        }

        public void setDescripcionM(String descripcionM) {
            this.descripcionM = descripcionM;
        }

        public void setFechaM(String fechaM) {
            this.fechaM = fechaM;
        }

        public void setFinalizadoM(boolean finalizadoM) {
            this.finalizadoM = finalizadoM;
        }

    

}
