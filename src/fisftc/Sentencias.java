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
    private static final boolean trazas = true;

    public Sentencias() {
        cn = new Conexion();
        if (this.conectar()) {
            this.montarBase();
        }
        this.cerrar();
    }

    //BORRAR DESPUES DE PROBAR
    public Conexion obtenerConexion() {
        return cn;
    }

    public boolean conectar() {
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
                    + "  REFERENCES profesor ON DELETE CASCADE,"
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
                    + " REFERENCES tfg,"
                    + " idAlumno INTEGER NOT NULL CONSTRAINT defensa_idAlumno_fk"
                    + " REFERENCES alumno,"
                    + " idConvocatoria INTEGER NOT NULL CONSTRAINT fkCo_idConvocatoria "
                    + " REFERENCES convocatoria, "
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

    public ResultSet obtenerTFGDisponibles() {
        if (this.conectar()) {
            rs = cn.Select("select * from " + TFG.tabla);
        }
        return rs;

    }

    public ResultSet obtenerProfesores() {
        if (this.conectar()) {
            rs= cn.Select("Select * from" + Profesor.tabla);
        }

        return rs;
    }

    public ResultSet obtenerAlumnos() {
        if (this.conectar()) {
            rs= cn.Select("Select * from" + Profesor.tabla);
        }

        return rs;
    }

    public ResultSet obtenerConvocatoria() {
        if (this.conectar()) {
            rs= cn.Select("Select * from" + Convocatoria.tabla);
        
        }

        return rs;
    }

    public ResultSet obtenerDefensa() {
        if (this.conectar()) {
            rs= cn.Select("Select * from" + Defensa.tabla);
        }        

        return rs;
    }

    /**
     * Función para insertar que devoverá el id del objeto insertado
     *
     * @param trabajo espera un objeto de tipo TFG
     * @return 0 si no ha podido insertar y el numero de su id si ha tenido
     * exito
     */
    public int insertarTFG(TFG trabajo) {
        int id = 0;
        if (this.conectar()) {
            Calendar c = new GregorianCalendar();
            int dia = c.get(Calendar.DATE);
            int mes = c.get(Calendar.MONTH) + 1;
            int año = c.get(Calendar.YEAR);

            //modificar  exito = cn.noSelect("INSERT INTO tfg (titulo,idprofesor,convocatoria,fecharegistro)values('" + titulo + "'," + idProf + ",0,'" + año + "-" + mes + "-" + dia + "')");
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
        if (this.conectar()) {
            // exito = cn. lo que toque
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
    public int insertarAlumno(Alumno alumno) {
        int id = 0;
        if (this.conectar()) {
            //exito = cn. lo qu toque
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
        if (this.conectar()) {
            //exito = cn. lo qu toque
        }

        return id;
    }

    public boolean insertarDefensa(Defensa defensa) {
        boolean exito = false;
        if (this.conectar()) {
            //exito = cn. lo qu toque
        }

        return exito;
    }

    public boolean modificarTFG(TFG trabajo) {
        boolean exito = false;
        if (this.conectar()) {
            Calendar c = new GregorianCalendar();
            int dia = c.get(Calendar.DATE);
            int mes = c.get(Calendar.MONTH) + 1;
            int año = c.get(Calendar.YEAR);

            //modificar  exito = cn.noSelect("INSERT INTO tfg (titulo,idprofesor,convocatoria,fecharegistro)values('" + titulo + "'," + idProf + ",0,'" + año + "-" + mes + "-" + dia + "')");
        }

        return exito;
    }

    public boolean modificarProfesores(Profesor profesor) {
        boolean exito = false;
        if (this.conectar()) {
            // exito = cn. lo que toque
        }

        return exito;
    }

    public boolean modificarAlumno(Alumno alumno) {
        boolean exito = false;
        if (this.conectar()) {
            //exito = cn. lo qu toque
        }

        return exito;
    }

    public boolean modificarConvocatoria(Convocatoria convocatoria) {
        boolean exito = false;
        if (this.conectar()) {
            //exito = cn. lo qu toque
        }

        return exito;
    }

    public boolean modificarDefensa(Defensa defensa) {
        boolean exito = false;
        if (this.conectar()) {
            //exito = cn. lo qu toque
        }

        return exito;
    }

    public boolean eliminarTFG(TFG trabajo) {
        boolean exito = false;
        if (this.conectar()) {
            //modificar  exito = cn.noSelect("INSERT INTO tfg (titulo,idprofesor,convocatoria,fecharegistro)values('" + titulo + "'," + idProf + ",0,'" + año + "-" + mes + "-" + dia + "')");
        }

        return exito;
    }

    public boolean eliminarProfesores(Profesor profesor) {
        boolean exito = false;
        if (this.conectar()) {
            // exito = cn. lo que toque
        }

        return exito;
    }

    public boolean eliminarAlumno(Alumno alumno) {
        boolean exito = false;
        if (this.conectar()) {
            //exito = cn. lo qu toque
        }

        return exito;
    }

    public boolean eliminarConvocatoria(Convocatoria convocatoria) {
        boolean exito = false;
        if (this.conectar()) {
            //exito = cn. lo qu toque
        }

        return exito;
    }

    public boolean elimnarDefensa(Defensa defensa) {
        boolean exito = false;
        if (this.conectar()) {
            //exito = cn. lo qu toque
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

}
