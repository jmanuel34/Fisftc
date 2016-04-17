package fisftc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Metodos {

    private TFG tfg;
    private Profesor profesor;
    private Alumno alumno;
    private Convocatoria convocatoria;
    private Defensa defensa;

    private ResultSet rs;
    private Sentencias sent;
    private boolean trazas = true;

    /**
     * Introduce en un arraylist de TFG's los tfg disponibles de la base de
     * datos
     *
     * @param lista hay que pasar un arraylist vacio
     */
    public void obtenerTfgDisponible(ArrayList<TFG> lista) {
        //vaciar el arraylist
        lista.clear();
        rs = sent.obtenerTFGDisponibles();
        try {
            while (rs != null && rs.next()) {
                tfg = new TFG();
                tfg.setIdTfgM(rs.getInt(TFG.idTfg));
                tfg.setIdProfesorM(rs.getInt(TFG.idProfesor));
                tfg.setTituloM(rs.getString(TFG.titulo));
                tfg.setDescripcionM(rs.getString(TFG.descripcion));
                tfg.setFechaM(rs.getString(TFG.fecha));
                tfg.setFinalizadoM(rs.getBoolean(TFG.finalizado)); // ojo aqui al probar

                lista.add(tfg);
            }
        } catch (SQLException ex) {
            if (trazas) {
                Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Introduce en un arraylist de string actualizado los resultados de la
     * busqueda en convocatoria
     *
     * @param lista hay que pasar un arraylist vacio
     * @param conv hay que pasar un objeto de tipo convocatoria para buscar
     */
    public void obtenerHistorial(ArrayList<String> lista, Convocatoria conv) {
        //vaciar el arraylist
        lista.clear();
        rs = sent.obtenerHistorico(conv);
        try {
            while (rs != null && rs.next()) {
                String nombre = rs.getString(Alumno.nombre);
                String titulo = rs.getString(TFG.titulo);
                float nota = rs.getFloat(Defensa.nota);

                lista.add("Alumno: " + nombre + ". Titulo: " + titulo + ". Nota: " + nota + ".");
            }
        } catch (SQLException ex) {
            if (trazas) {
                Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Introduce en un arraylist actualizado los TFG's de la base de datos
     *
     * @param trabajos hay que pasar un arraylist vacio
     */
    public void obtenerTFGs(ArrayList<TFG> trabajos) {
        //vaciar el arraylist
        trabajos.clear();
        rs = sent.obtenerTFGs();
        try {
            while (rs != null && rs.next()) {
                tfg = new TFG();
                tfg.setIdTfgM(rs.getInt(TFG.idTfg));
                tfg.setIdProfesorM(rs.getInt(TFG.idProfesor));
                tfg.setTituloM(rs.getString(TFG.titulo));
                tfg.setDescripcionM(rs.getString(TFG.descripcion));
                tfg.setFechaM(rs.getString(TFG.fecha));
                tfg.setFinalizadoM(rs.getBoolean(TFG.finalizado));// ojo aqui al probar

                trabajos.add(tfg);
            }
        } catch (SQLException ex) {
            if (trazas) {
                Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Devuelve un arraylist actualizado con los profesores de la base de datos
     *
     * @param profesores hay que pasar un arraylist vacio
     */
    public void obtenerProfesores(ArrayList<Profesor> profesores) {
        //vaciar el arraylist
        profesores.clear();
        rs = sent.obtenerProfesores();
        try {
            while (rs != null && rs.next()) {
                profesor = new Profesor();
                profesor.setIdProfesorM(rs.getInt(Profesor.idProfesor));
                profesor.setNombreM(rs.getString(Profesor.nombre));
                profesor.setApe1M(rs.getString(Profesor.ape1));
                profesor.setApe2M(rs.getString(Profesor.ape2));
                profesor.setEmailProfesorM(rs.getString(Profesor.emailProfesor));
                profesor.setDespachoM(rs.getString(Profesor.despacho));
                profesor.setEstadoM(rs.getBoolean(Profesor.estado));

                profesores.add(profesor);
            }
        } catch (SQLException ex) {
            if (trazas) {
                Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Devuelve un arraylist actualizado con los alumnos de la base de datos
     *
     * @param alumnos hay que pasar un arraylist vacio
     */
    public void obtenerAlumnos(ArrayList<Alumno> alumnos) {
        //vaciar arraylist
        alumnos.clear();
        rs = sent.obtenerAlumnos();
        try {
            while (rs != null && rs.next()) {
                alumno = new Alumno();
                alumno.setIdAlumnoM(rs.getInt(Alumno.idAlumno));
                alumno.setNombreM(rs.getString(Alumno.nombre));
                alumno.setApe1M(rs.getString(Alumno.ape1));
                alumno.setApe2M(rs.getString(Alumno.ape2));
                alumno.setEmailAlumnoM(rs.getString(Alumno.emailAlumno));
                alumno.setNumMatM(rs.getString(Alumno.numMat));

                alumnos.add(alumno);
            }
        } catch (SQLException ex) {
            if (trazas) {
                Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Devuelve un arraylist actualizado con las convocatorias de la base de
     * datos
     *
     * @param convocatorias hay que pasar un arraylist vacio
     */
    public void obtenerConvocatoria(ArrayList<Convocatoria> convocatorias) {
        //vaciar arraylist
        convocatorias.clear();
        rs = sent.obtenerConvocatoria();
        try {
            while (rs != null && rs.next()) {
                convocatoria = new Convocatoria();
                convocatoria.setidConvocatoriaM(rs.getInt(Convocatoria.idConvocatoria));
                convocatoria.setAnioM(rs.getString(Convocatoria.anio));
                convocatoria.setMesM(rs.getString(Convocatoria.mes));
                convocatoria.setTipoM(rs.getString(Convocatoria.tipo));

                convocatorias.add(convocatoria);
            }
        } catch (SQLException ex) {
            if (trazas) {
                Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Devuelve un arraylist actualizado con las defensas de la base de datos
     *
     * @param defensas hay que pasar un arraylist vacio
     */
    public void obtenerDefensa(ArrayList<Defensa> defensas) {
        //vaciar arraylist
        defensas.clear();
        rs = sent.obtenerDefensa();
        try {
            while (rs != null && rs.next()) {
                defensa = new Defensa();
                defensa.setIdTfgM(rs.getInt(Defensa.idTfg));
                defensa.setIdAlumnoM(rs.getInt(Defensa.idAlumno));
                defensa.setIdConvocatoriaM(rs.getInt(Defensa.idConvocatoria));
                defensa.setFechaDefensaM(rs.getString(Defensa.fechaDefensa));
                defensa.setNotaM(rs.getFloat(Defensa.nota));

                defensas.add(defensa);
            }
        } catch (SQLException ex) {
            if (trazas) {
                Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * introduce en la base de datos un tfg
     *
     * @param trabajo espera un objeto de tipo TFG
     * @return 0 en caso de fallo, id del objeto insertado en caso de exito
     */
    public int aniadirTfg(TFG trabajo) {
        return sent.insertarTFG(trabajo);
    }

    /**
     * introduce en la base de datos un Profesor
     *
     * @param profesor espera un objeto de tipo Profesor
     * @return 0 en caso de fallo, id del objeto insertado en caso de exito
     */
    public int aniadirProfesor(Profesor profesor) {
        return sent.insertarProfesor(profesor);
    }

    /**
     * introduce en la base de datos un Alumno
     *
     * Consideraciones: esta función tiene que comprobar que en caso de tener
     * idtfg este idtfg no este asignado a ningun otro alumno<---borrar despues
     * de realizar
     *
     * @param alumno espera un objeto de tipo Alumno
     * @return 0 en caso de fallo, id del objeto insertado en caso de exito
     */
    public int aniadirAlumno(Alumno alumno) {
        return sent.insertarAlumno(alumno);
    }

    /**
     *
     * introduce en la base de datos una convocatoria y verifica que no exista
     * otra instancia con mismos meses y anios
     *
     * @param convocatoria espera un objeto Convocatoria
     * @return 0 en caso de fallo, id del objeto insertado en caso de exito
     */
    public int aniadirConvocatoria(Convocatoria convocatoria) {
        int id = 0;
        boolean existe = false;
        ArrayList<Convocatoria> conv = new ArrayList<Convocatoria>();
        this.obtenerConvocatoria(conv);
        for (int i = 0; i < conv.size(); i++) {
            Convocatoria aux = conv.remove(0);
            if (convocatoria.getAnioM().equals(aux.getAnioM())
                    && convocatoria.getMesM().equals(aux.getMesM())) {
                existe = true;
            }
        }
        if (!existe) {
            id = sent.insertarConvocatoria(convocatoria);
        }
        return id;
    }

    /**
     * introduce en la base de datos una defensa
     *
     * @param defensa espera un objeto Defensa
     * @return si ha tenido exito la operacion
     */
    public boolean aniadirDefensa(Defensa defensa) {
        return sent.insertarDefensa(defensa);
    }

    /**
     * Modificara en la base de datos la fila que tenga el id del objeto que
     * pasamos como parametro
     *
     * @param trabajo espera un objeto TFG.
     * @return false en caso de fallo true en caso de exito.
     */
    public boolean modificarTFG(TFG trabajo) {
        return sent.modificarTFG(trabajo);
    }

    /**
     * Modificara en la base de datos la fila que tenga el id del objeto que
     * pasamos como parametro
     *
     * Consideraciones: esta función tiene que comprobar que en caso de tener
     * idtfg este idtfg no este asignado a ningun otro alumno<---borrar despues
     * de realizar
     *
     * @param trabajo espera un objeto TFG.
     * @return false en caso de fallo true en caso de exito.
     */
    public boolean modificarAlumno(Alumno alumno) {
        return sent.modificarAlumno(alumno);
    }
    
    /**
     * 
     * Pasado un trabajo devuelve el profesor asignado a dicho trabajo
     * 
     * @param trabajo espera un objeto de tipo Trabajo
     * @return un objeto de tipo Profesor
     */
public Profesor obtenerProfesor(TFG trabajo) {
        ArrayList<Profesor> prof = new ArrayList<Profesor>();
        boolean encontrado = false;
        profesor = new Profesor();
        this.obtenerProfesores(prof);
        for (int i = 0; i < prof.size() || !encontrado; i++) {
            profesor = prof.remove(0);
            if (profesor.getIdProfesorM() == trabajo.getIdProfesorM()) {
                encontrado = true;
            }
        }
        if (!encontrado) {
            profesor = new Profesor();
        }
        return profesor;
    }

}
