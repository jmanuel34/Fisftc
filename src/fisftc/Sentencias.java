package fisftc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sentencias {

    private Conexion cn;
    private ResultSet rs;
    private boolean conectado = false;
    private static final boolean trazas = false;

    public Sentencias() {
        cn = new Conexion();
        if (this.conectar()) {
            this.montarBase();
        }
        this.cerrar();
    }

    public boolean conectar() {
        rs = null; // ojo con esto es un parche por si hay problemas con los rs fijarse aqui
        if (!conectado) {
            conectado = cn.conectarDB();
        }
        return conectado;
    }

    public void cerrar() {
        conectado = false;
        cn.close();
    }

    public void montarBase() {
        if (!this.controlTablas()) {
            cn.noSelect("CREATE TABLE profesor("
                    + "  idProfesor INTEGER not null GENERATED ALWAYS AS IDENTITY,"
                    + "  emailProfesor VARCHAR(70) not null,"
                    + "  nombre VARCHAR(45) not null,"
                    + "  ape1 VARCHAR(45),"
                    + "  ape2 VARCHAR(45),"
                    + "  despacho VARCHAR(4),"
                    + "  estado BOOLEAN,"
                    + "  PRIMARY KEY (idProfesor)"
                    + ")");

            cn.noSelect("CREATE TABLE tfg("
                    + "  idTfg INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,"
                    + "  idProfesor INTEGER NOT NULL CONSTRAINT tfg_idprofesor_fk"
                    + "  REFERENCES profesor ON DELETE RESTRICT,"
                    + "  titulo VARCHAR(45) not null,"
                    + "  descripcion VARCHAR(300),"
                    + "  fechaRegistro DATE NOT NULL," // introducir con este formato '2016-03-11'
                    + "  finalizado BOOLEAN,"
                    + "  PRIMARY KEY (idTfg)"
                    + ")");
            cn.noSelect("CREATE TABLE alumno("
                    + "  idAlumno INTEGER not null GENERATED ALWAYS AS IDENTITY,"
                    + "  emailAlumno VARCHAR(70) not null,"
                    + "  idTfg  INTEGER CONSTRAINT alumno_idTfg_fk"
                    + "  REFERENCES tfg ON DELETE CASCADE,"
                    + "  numMat VARCHAR(6) not null,"
                    + "  nombre VARCHAR(45) not null,"
                    + "  ape1 VARCHAR(45),"
                    + "  ape2 VARCHAR(45),"
                    + "  fechaAsignacion DATE,"
                    + " PRIMARY KEY (idAlumno)"
                    + ")");

            cn.noSelect("CREATE TABLE convocatoria("
                    + " idConvocatoria INTEGER not null GENERATED ALWAYS AS IDENTITY,"
                    + " anio VARCHAR(4) NOT NULL,"
                    + " mes VARCHAR(45) NOT NULL,"
                    + " tipo VARCHAR(45) NOT NULL,"
                    + " PRIMARY KEY (idConvocatoria)"
                    + ")");

            cn.noSelect("CREATE TABLE defensa("
                    + " idTfg INTEGER NOT NULL CONSTRAINT defensa_idTfg_fk"
                    + " REFERENCES tfg ON DELETE RESTRICT,"
                    + " idAlumno INTEGER NOT NULL CONSTRAINT defensa_idAlumno_fk"
                    + " REFERENCES alumno ON DELETE RESTRICT,"
                    + " idConvocatoria INTEGER NOT NULL CONSTRAINT fkCo_idConvocatoria "
                    + " REFERENCES convocatoria ON DELETE RESTRICT, "
                    + " fechaDefensa DATE,"
                    + " nota DECIMAL, "
                    + " PRIMARY KEY (idTfg, idConvocatoria, idAlumno)"
                    + ")");

        }

    }

    /**
     * Funcion para obtener las relaciones (defensas) entre alumno, TFG, y
     * convocatoria para una convocatoria en concreto.
     *
     * @param conv espera un objeto de tipo Convocatoria para obtener su id
     * @return objeto ResulSet
     */
    public ResultSet obtenerHistorico(Convocatoria conv) {
        if (this.conectar()) {
            rs = cn.Select("SELECT * from " + Alumno.tabla + " as a join " + Defensa.tabla
                    + " as c on a." + Alumno.idAlumno + "=c." + Defensa.idAlumno + " join " + TFG.tabla
                    + " as t on c." + Defensa.idTfg + "=t." + TFG.idTfg + " where c." + Defensa.idConvocatoria
                    + "=" + conv.getidConvocatoriaM());
        }

        return rs;
    }

    public ResultSet obtenerTFGs() {
        if (this.conectar()) {
            rs = cn.Select("select * from " + TFG.tabla);
        }
        return rs;

    }

    /**
     * que serán aquellos en los que su idtfg no este en ningún alumno
     *
     * @return
     */
    public ResultSet obtenerTFGDisponibles() {
        if (this.conectar()) {
            rs = cn.Select("select * from " + TFG.tabla + " where " + TFG.idTfg + " NOT IN(select " + Alumno.idtfg + " from " + Alumno.tabla + ")");
        }
        return rs;

    }

    public ResultSet obtenerProfesores() {
        if (this.conectar()) {
            rs = cn.Select("select * from " + Profesor.tabla);
        }

        return rs;
    }

    /**
     * La funcion devuelve una fila de la tabla profesor que coincida con el
     * idprofesor contenido en el TFG que se pasa como parámetro.
     *
     * @param trabajo espera un objeto de tipo TFG
     * @return ResultSet con el resultado de la busqueda.
     */
    public ResultSet obtenerProfesor(TFG trabajo) {
        if (this.conectar()) {
            rs = cn.Select("select * from " + Profesor.tabla + " where " + Profesor.idProfesor + " = " + trabajo.getIdProfesorM());
        }
        return rs;
    }

    public ResultSet obtenerAlumnos() {
        if (this.conectar()) {
            rs = cn.Select("select * from " + Alumno.tabla);
        }

        return rs;
    }

    /**
     *
     * Obtiene de los alumnos que cuentan con un trabajo y que o bien no estan
     * en defensa o bien estan y no cuentan con fecha de asigacion
     *
     * @return
     */
    public ResultSet obtenerAlumnosDefendibles() {
        if (this.conectar()) {
            rs = cn.Select("select * from " + Alumno.tabla
                    + " where " + Alumno.idtfg + " > " + 0
                    + " and false = ( select " + TFG.finalizado + " from " + TFG.tabla + " where " + TFG.idTfg + " = alumno." + Alumno.idtfg + ")");
        }

        return rs;
    }

    public ResultSet obtenerConvocatoria() {
        if (this.conectar()) {
            rs = cn.Select("select * from " + Convocatoria.tabla);
        }

        return rs;
    }

    public ResultSet obtenerDefensa() {
        if (this.conectar()) {
            rs = cn.Select("select * from " + Defensa.tabla);
        }

        return rs;
    }

    /**
     * Lista ,no repetidos, los anios de convocatoria.
     *
     * @return ResultSet con la busqueda.
     */
    public ResultSet obtenerAnios() {
        if (this.conectar()) {
            rs = cn.Select("select distinct " + Convocatoria.anio + " from " + Convocatoria.tabla);
        }
        return rs;
    }

    /**
     *
     * Inserta el trabajo en la base de datos y devuelve el id del objeto
     * insertado
     *
     * @param trabajo espera un objeto de tipo TFG
     * @return 0 si no ha podido insertar y el numero de su id si ha tenido
     * exito
     */
    public int insertarTFG(TFG trabajo) {
        int id = 0;
        boolean exito = true;
        if (this.conectar()) {
            Calendar c = new GregorianCalendar();
            int dia = c.get(Calendar.DATE);
            int mes = c.get(Calendar.MONTH) + 1;
            int año = c.get(Calendar.YEAR);
            trabajo.setFechaM(año + "-" + mes + "-" + dia);

            exito = cn.noSelect("INSERT INTO " + TFG.tabla
                    + " (" + TFG.idProfesor + "," + TFG.titulo + "," + TFG.descripcion + "," + TFG.fecha + "," + TFG.finalizado + ")values"
                    + " (" + trabajo.getIdProfesorM()
                    + ",'" + trabajo.getTituloM() + "','" + trabajo.getDescripcionM()
                    + "','" + trabajo.getFechaM() + "'," + trabajo.getFinalizadoM() + ")");

            if (exito) {
                rs = cn.Select("Select max (" + TFG.idTfg + ") from " + TFG.tabla);
                try {
                    if (rs != null && rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    if (trazas) {
                        Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        }

        return id;
    }

    /**
     * Función para insertar que devoverá el id del objeto insertado
     *
     * @param profesor espera un objeto de tipo Profesor
     * @return 0 si no ha podido insertar y el numero de su id si ha tenido
     * exito
     */
    public int insertarProfesor(Profesor profesor) {
        int id = 0;
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("INSERT INTO " + Profesor.tabla + " (" + Profesor.emailProfesor + "," + Profesor.nombre + "," + Profesor.ape1 + "," + Profesor.ape2 + "," + Profesor.despacho + "," + Profesor.estado
                    + ")values('" + profesor.getEmailProfesorM() + "','" + profesor.getNombreM() + "','" + profesor.getApe1M() + "','" + profesor.getApe2M() + "','" + profesor.getDespachoM() + "'," + profesor.isEstadoM() + ")");

            if (exito) {
                rs = cn.Select("Select max (" + Profesor.idProfesor + ") from " + Profesor.tabla);
                try {
                    if (rs != null && rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    if (trazas) {
                        Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }

        return id;
    }

    /**
     * Función para insertar que devoverá el id del objeto insertado
     *
     * @param alumno espera un objeto de tipo Alumno
     * @return 0 si no ha podido insertar y el numero de su id si ha tenido
     * exito
     */
    public int insertarAlumno(Alumno alumno) {//revisar las comas de los strings 
        int id = 0;
        boolean exito = false;
        if (this.conectar()) {
            if (alumno.getIdtfgM() > 0) {
                exito = cn.noSelect("INSERT INTO " + Alumno.tabla + " ("
                        + Alumno.nombre + "," + Alumno.ape1 + "," + Alumno.ape2 + "," + Alumno.emailAlumno + "," + Alumno.numMat + "," + Alumno.idtfg + "," + Alumno.fechaAsignacion
                        + ")values('" + alumno.getNombreM() + "','" + alumno.getApe1M() + "','" + alumno.getApe2M() + "','" + alumno.getEmailAlumnoM()
                        + "','" + alumno.getNumMatM() + "'," + alumno.getIdtfgM() + "," + alumno.getFechaAsignacionM() + ")");
            }
            if (exito) {
                rs = cn.Select("Select max (" + Alumno.idAlumno + ") from " + Alumno.tabla);
                try {
                    if (rs != null && rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    if (trazas) {
                        Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }

        return id;
    }

    /**
     * Función para insertar que devoverá el id del objeto insertado
     *
     * @param convocatoria espera un objeto de tipo Convocatoria
     * @return 0 si no ha podido insertar y el numero de su id si ha tenido
     * exito
     */
    public int insertarConvocatoria(Convocatoria convocatoria) {
        int id = 0;
        boolean exito = false;
        if (this.conectar()) {

            exito = cn.noSelect("INSERT INTO " + Convocatoria.tabla + " (" + Convocatoria.anio + "," + Convocatoria.mes + "," + Convocatoria.tipo
                    + ")values('" + convocatoria.getAnioM() + "','" + convocatoria.getMesM() + "','" + convocatoria.getTipoM() + "')");

            if (exito) {
                rs = cn.Select("Select max (" + Convocatoria.idConvocatoria + ") from " + Convocatoria.tabla);
                try {
                    if (rs != null && rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    if (trazas) {
                        Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }

        return id;
    }

    public boolean insertarDefensa(Defensa defensa) {
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("INSERT INTO " + Defensa.tabla + " (" + Defensa.idTfg + "," + Defensa.idAlumno + "," + Defensa.idConvocatoria + "," + Defensa.fechaDefensa + "," + Defensa.nota
                    + ")values(" + defensa.getIdTfgM() + "," + defensa.getIdAlumnoM() + "," + defensa.getIdConvocatoriaM() + ",'" + defensa.getFechaDefensaM() + "'," + defensa.getNotaM() + ")");
        }

        return exito;
    }

    public boolean modificarConvocatoria(Convocatoria convocatoria) {

        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("UPDATE " + Convocatoria.tabla
                    + " SET " + Convocatoria.anio + " = '" + convocatoria.getAnioM() + "'," + Convocatoria.mes + " = '" + convocatoria.getMesM() + "'," + Convocatoria.tipo + " = '" + convocatoria.getTipoM()
                    + "' where " + Convocatoria.idConvocatoria + " = " + convocatoria.getidConvocatoriaM());

        }

        return exito;
    }

    public boolean modificarTFG(TFG trabajo) {
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("UPDATE " + TFG.tabla
                    + " SET " + TFG.idProfesor + "=" + trabajo.getIdProfesorM() + ","
                    + TFG.titulo + "='" + trabajo.getTituloM() + "',"
                    + TFG.descripcion + "='" + trabajo.getDescripcionM() + "',"
                    + TFG.finalizado + "=" + trabajo.getFinalizadoM()
                    + " where " + TFG.idTfg + " = " + trabajo.getIdTfgM()
            );
        }

        return exito;
    }

    public boolean modificarProfesores(Profesor profesor) {
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect(" UPDATE " + Profesor.tabla
                    + " SET "
                    + Profesor.nombre + "='" + profesor.getNombreM() + "',"
                    + Profesor.ape1 + "='" + profesor.getApe1M() + "',"
                    + Profesor.ape2 + "='" + profesor.getApe2M() + "',"
                    + Profesor.emailProfesor + "='" + profesor.getEmailProfesorM() + "',"
                    + Profesor.despacho + "='" + profesor.getDespachoM() + "',"
                    + Profesor.estado + "=" + profesor.isEstadoM()
                    + " where " + Profesor.idProfesor + "=" + profesor.getIdProfesorM());
        }
        return exito;
    }

    public boolean modificarAlumno(Alumno alumno) {
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("UPDATE " + Alumno.tabla
                    + " SET " + Alumno.emailAlumno + "='" + alumno.getEmailAlumnoM() + "'," + Alumno.idtfg + "=" + alumno.getIdtfgM() + ","
                    + Alumno.numMat + "='" + alumno.getNumMatM() + "'," + Alumno.nombre + "='" + alumno.getNombreM() + "',"
                    + Alumno.ape1 + "='" + alumno.getApe1M() + "'," + Alumno.ape2 + "='" + alumno.getApe2M() + "'"
                    + " where " + Alumno.idAlumno + "=" + alumno.getIdAlumnoM());
        }

        return exito;
    }

    public boolean modificarDefensa(Defensa defensa) {
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("UPDATE INTO defensa (" + Defensa.fechaDefensa + "," + Defensa.nota + ")values('"
                    + defensa.getFechaDefensaM() + "'," + defensa.getNotaM() + "')"
                    + "where" + Defensa.idTfg + "=" + defensa.getIdTfgM() + "AND" + Defensa.idAlumno + "=" + defensa.getIdAlumnoM() + "AND" + Defensa.idConvocatoria + "=" + defensa.getIdConvocatoriaM() + ")");
        }

        return exito;
    }

    public boolean eliminarTFG(TFG trabajo) {
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("DELETE * FROM " + TFG.tabla
                    + " where " + TFG.idTfg + " = " + trabajo.getIdTfgM());
        }

        return exito;
    }

    public boolean eliminarProfesores(Profesor profesor) {
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("DELETE * FROM" + Profesor.tabla
                    + " where " + Profesor.idProfesor + " = " + profesor.getIdProfesorM());
        }

        return exito;
    }

    public boolean eliminarAlumno(Alumno alumno) {
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("DELETE * FROM" + Alumno.tabla
                    + " where " + Alumno.idAlumno + " = " + alumno.getIdAlumnoM());
        }

        return exito;
    }

    public boolean eliminarConvocatoria(Convocatoria convocatoria) {
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("DELETE * FROM" + Convocatoria.tabla
                    + " where " + Convocatoria.idConvocatoria + " = " + convocatoria.getidConvocatoriaM());
        }

        return exito;
    }

    public boolean elimnarDefensa(Defensa defensa) {
        boolean exito = false;
        if (this.conectar()) {
            exito = cn.noSelect("DELETE * FROM" + Defensa.tabla
                    + " where " + Defensa.idAlumno + " = " + defensa.getIdAlumnoM()
                    + " AND " + Defensa.idConvocatoria + " = " + defensa.getIdConvocatoriaM()
                    + " AND " + Defensa.idTfg + " = " + defensa.getIdTfgM());
        }

        return exito;
    }

    /**
     * Esta función se encarga de comprobar que esten todas las tablas
     *
     * @return true si estan todas false en caso contrario
     */
    public boolean controlTablas() {
        if (trazas) {
            this.listarTablas();
        }
        boolean encontrado = true;
        String[] lista = {Alumno.tabla, Convocatoria.tabla, Defensa.tabla, Profesor.tabla, TFG.tabla};
        boolean[] esta = {false, false, false, false, false,};
        String aux;
        if (this.conectar()) {
            try {
                rs = cn.obtenerTablas();
                while (rs.next()) {
                    aux = rs.getString("TABLE_NAME");
                    for (int i = 0; i < lista.length; i++) {
                        if (aux.contentEquals(lista[i].toUpperCase())) {
                            esta[i] = true;
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                for (int i = 0; i < esta.length; i++) {
                    if (!esta[i]) {
                        encontrado = false;
                    }
                }
                return encontrado;
            }
        }
        for (int i = 0; i < esta.length; i++) {
            if (!esta[i]) {
                encontrado = false;
            }
        }
        return encontrado;
    }

    // solo para el desarrollo
    public void listarTablas() {
        if (this.conectar()) {
            try {
                rs = cn.obtenerTablas();
                while (rs.next()) {
                    System.out.println(rs.getString("TABLE_NAME"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
    /*
        Jm. Tiempo 15 minutos
        Obtiene los Tfgs de un profesor
    PENDIENTE DE DEFINIR: CÓMO Tratar los  trabajos no finalizados
    */ 
       ResultSet obtenerTFGProfesor(Profesor profesor) {
        if (this.conectar()) {
          rs = cn.Select("(SELECT * FROM " +TFG.tabla 
                    + " WHERE "+TFG.idProfesor+ " = "+profesor.getIdProfesorM()
                    + ")");
        }
        return rs;
    }
       
    boolean borrarTodosTfg(Profesor profesor) {
        boolean exito = false;
        if (this.conectar()) {
            cn.noSelect("DELETE FROM " + TFG.tabla
                    + " WHERE " + TFG.idProfesor + " = " + profesor.getIdProfesorM()
                    + "AND " + TFG.finalizado + "= 0"
                    + ")");
            exito = true;
        }
        return exito;
    }
    
    /*   ** Jm. Tiempo 15 minutos
        Borra un Tfg habiendo pasado su identificador
    */
    public boolean borrarTfg(int idTfgM) {
        boolean exito = false;
        if (this.conectar()) {
            cn.noSelect("DELETE FROM "+TFG.tabla
                    + " WHERE " + TFG.idTfg+ " = " +idTfgM+ ""); 
            exito = true;
        }
        return exito;
    
    }
    
    public ResultSet obtenerTFG(int id) {
        if (this.conectar()) {
            rs = cn.Select("select * from " + TFG.tabla
            + " WHERE " +TFG.idTfg+ " = " + id
            + "");
        }
        return rs;   
    }

}
