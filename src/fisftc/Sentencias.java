package fisftc;

import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
=======
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
>>>>>>> Nuevo-enlace-a-base-de-datos(mio)
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
            cn.noSelect("CREATE TABLE "+Profesor.tabla + " ("
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
            // currarlo
            rs = cn.Select("SELECT * FROM " + TFG.tabla
            + " WHERE idTfg NOT IN (SELECT idTfg FROM " + Alumno.tabla+ " WHERE idTfg IS NOT NULL)");
        }
        
        
        return rs;
        /*
        select idTfg, titulo, idprofesor from tfg
             WHERE idTfg NOT IN (Select idTfg from alumno WHERE IDTFG is not NULL );
        */

<<<<<<< HEAD
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
            rs =cn.Select("select * from " + Convocatoria.tabla);
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
     * 
     * Obtiene de los alumnos que cuentan con un trabajo y que o bien no estan 
     * en defensa o bien estan y no cuentan con fecha de asigacion
     * 
     * @return 
     */
    public ResultSet obtenerAlumnosAsignables(){
        if (this.conectar()) {
            rs = cn.Select("select * from "+ Alumno.tabla 
                        + " where " +Alumno.idtfg + " > " + 0
                        + " and 1 <= ( select cout(*) from " 
                                    + Defensa.tabla +" where "+ Defensa.idTfg + " = " +  Alumno.idtfg
                                    + " and " + Defensa.fechaDefensa + " = null" );
        }
        
        return rs;
    }
    
   
/**
     * La funcion devuelve una fila de la tabla profesor que coincida con el idprofesor
     * contenido en el TFG que se pasa como parámetro.
     * 
     * @param trabajo espera un objeto de tipo TFG
     * @return ResultSet con el resultado de la busqueda.
     */
    public ResultSet obtenerProfesor(TFG trabajo){
        if (this.conectar()) {
            rs =cn.Select("select * from " + Profesor.tabla + " where "+Profesor.idProfesor + " = " +trabajo.getIdProfesorM());
        }
        return rs;
    }
    
    /*
     Devuelve un profesor buscando por su email
    */
    public ResultSet estaProfesor ( String email) {
        if (this.conectar()) {
         rs = cn.Select("SELECT * FROM profesor "
                 + " WHERE emailProfesor = "+email+" );");
        }
        return rs;
    }
    
    /**
     *  Lista ,no repetidos, los anios de convocatoria.
     * 
     * @return ResultSet con la busqueda.
     */
    public ResultSet obtenerAnios(){
        if(this.conectar()){
            rs = cn.Select("select distinct "+Convocatoria.anio +" from " +Convocatoria.tabla);
        }
        return rs;
    }
    

    
/**
     * 
     * Inserta el trabajo en la base de datos y devuelve el id del objeto insertado
     *
     * @param trabajo espera un objeto de tipo TFG
     * @return 0 si no ha podido insertar y el numero de su id si ha tenido
     * exito
     */
    public int insertarTFG(TFG trabajo) {
        int id = 0;
        boolean exito= true;
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
=======
        if (!this.buscarTabla("tfg")) {
            if (!this.buscarTabla("profesor")) {
                if (!this.buscarTabla("alumno")) {
                    cn.noSelect("CREATE TABLE profesor("
                            + "  idProfesor INTEGER not null GENERATED ALWAYS AS IDENTITY,"
                            + "  emailProfesor VARCHAR(45) not null,"                        
                            + "  nombre VARCHAR(45) not null,"
                            + "  ape1 VARCHAR(45),"
                            + "  ape2 VARCHAR(45),"
                            + "  despacho VARCHAR(4),"
                            + "  PRIMARY KEY (idProfesor)"
                            + ")");
                    cn.noSelect("CREATE TABLE alumno("
                            + "  idAlumno INTEGER not null GENERATED ALWAYS AS IDENTITY,"
                            + "  emailAlumno VARCHAR(45) not null,"                           
                            + "  numMat VARCHAR(6) not null,"
                            + "  nombre VARCHAR(45) not null,"
                            + "  ape1 VARCHAR(45),"
                            + "  ape2 VARCHAR(45),"
                            + " PRIMARY KEY (idAlumno)"
                            + ")");
                    cn.noSelect("CREATE TABLE tfg("
                            + "  idTfg INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,"
                            + "  idProfesor INTEGER NOT NULL CONSTRAINT fk_profesor"
                            + "  REFERENCES profesor ON DELETE CASCADE,"
                            + "  idAlumno INTEGER CONSTRAINT fk_alumno"
                            + "  REFERENCES alumno,"
                            + "  titulo VARCHAR(45) not null,"
                            + "  descripcion VARCHAR(300),"
                            + "  convocatoria VARCHAR(2) not null,"
                            + "  fechaRegistro DATE NOT NULL,"                  // introducir con este formato '2016-03-11'
                            + "  fechaDefensa DATE,"
                            + "  notaFinal INTEGER,"
                            + "  fechaAsignacion DATE,"
                            + "  PRIMARY KEY (idTfg)"
                            + ")");                 
>>>>>>> Nuevo-enlace-a-base-de-datos(mio)
                }
            
            }

        }

        return id;
    }

   /** Author Jm
     * Función para insertar que devoverá el id del objeto insertado
     *
     * @param profesor espera un objeto de tipo Profesor
     * @return 0 si no ha podido insertar y el numero de su id si ha tenido
     * exito
     */
    public int insertarProfesor(Profesor profesor) {
        int id = 0;
        String s = "";
        boolean exito = true;
        if (this.conectar()) {

            exito = cn.noSelect("INSERT INTO" +Profesor.tabla 
                    + "("+Profesor.emailProfesor+ ", "
                    + Profesor.nombre+ ", "
                    + Profesor.ape1+ ", "
                    + Profesor.ape2+ ", "
                    + Profesor.despacho+ ", "
                    + Profesor.estado
                    + ") VALUES (" +profesor.getEmailProfesorM()+ ", "
                    + profesor.getNombreM()+ ", "
                    + profesor.getApe1M()+ ", "
                    + profesor.getApe2M()+ ", "
                    + profesor.getDespachoM()+ ", "
                    + profesor.isEstadoM()+ ", "
                    + ")");

            if (exito){
                rs = cn.Select("Select idProfesor from "+Profesor.tabla +"where emailProfesor = "
                        + ""+profesor.getEmailProfesorM()+");");
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
     * Campos:idAlumno, emailAlumno, idTfg, numMat, nombre, ape1, ape2, fechaAsignacion
     *  IdTfg se asignará en .... otra funcion
     */
    public int insertarAlumno(Alumno alumno) {
        int id = 0;
        if (this.conectar()) {
            cn.noSelect("INSERT INTO" +Alumno.tabla 
                    + "("+Alumno.emailAlumno+ ", "
                    + Alumno.numMat+ ", "
                    + Alumno.nombre+ ", "
                    + Alumno.ape1+ ", "
                    + Alumno.ape2+ ", "
                    + ") VALUES (" +alumno.getEmailAlumnoM()+ ", "
                    + alumno.getNumMatM()+ ", "
                    + alumno.getNombreM()+ ", "
                    + alumno.getApe1M()+ ", "
                    + alumno.getApe2M()+ ", "
                    + ")");
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

    
    /* Jm
            Sin enviar
    */
    public boolean modificarTFG(TFG trabajo) {
        boolean exito = false;
        if (this.conectar()) {
            Calendar c = new GregorianCalendar();
            int dia = c.get(Calendar.DATE);
            int mes = c.get(Calendar.MONTH) + 1;
            int año = c.get(Calendar.YEAR);
            
//           Campos: idTfg,idProfesor, titulo , descripcion,  fechaRegistro ,  finalizado 
            cn.noSelect("UPDATE " +TFG.tabla 
                    + "("+TFG.idProfesor+ ", "
                    + TFG.titulo+ ", "
                    + TFG.descripcion+ ", "
                    + TFG.fecha+ ", "
                    + TFG.finalizado+ ", "
                    + Profesor.estado
                    + ") VALUES (" +trabajo.getIdProfesorM()+ ", "
                    + trabajo.getTituloM()+ ", "
                    + trabajo.getDescripcionM()+ ", "
                    + trabajo.getFechaM()+ ", "
                    + "WHERE "+ TFG.idTfg+ "= "+trabajo.getIdTfgM()
                    + ")");          
        }

        return exito;
    }
    
       public void insertarTFG(int idProf) {
        Calendar c = new GregorianCalendar();
        int dia = c.get(Calendar.DATE);
        int mes = c.get(Calendar.MONTH) + 1;
        int año = c.get(Calendar.YEAR);
        String titulo;
        System.out.println("Introduce el titulo del TFG:");

<<<<<<< HEAD
    /*
    Jm Tiempo 15 minutos
    @Param profesor
    Modifica el profesor pasado como parametro con los datos actuales
    */
    
    public boolean modificarProfesores(Profesor profesor) {
        boolean exito = false;
        if (this.conectar()) {
            // exito = cn. lo que toque
            cn.noSelect("UPDATE " +Profesor.tabla 
                    + "("+Profesor.emailProfesor+ ", "
                    + Profesor.nombre+ ", "
                    + Profesor.ape1+ ", "
                    + Profesor.ape2+ ", "
                    + Profesor.despacho+ ", "
                    + Profesor.estado
                    + ") VALUES (" +profesor.getEmailProfesorM()+ ", "
                    + profesor.getNombreM()+ ", "
                    + profesor.getApe1M()+ ", "
                    + profesor.getApe2M()+ ", "
                    + profesor.getDespachoM()+ ", "
                    + profesor.isEstadoM()+ ", "
                    + "WHERE "+ Profesor.idProfesor+ "= "+profesor.getEmailProfesorM()
                    + ")");          
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

    /*
       Segun las especificaciones, no se dara un baja un profesor, sino que su estado pasara a false
    */
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
=======
        titulo = scan.nextLine();

        cn.noSelect("INSERT INTO tfg (titulo,idprofesor,convocatoria,fecharegistro)values('" + titulo + "'," + idProf + ",0,'" + año + "-" + mes + "-" + dia + "')");
    }

    public void insertarTFG(int idProf, int idAlum) {
        Calendar c = new GregorianCalendar();
        int dia = c.get(Calendar.DATE);
        int mes = c.get(Calendar.MONTH) + 1;
        int año = c.get(Calendar.YEAR);
        String titulo;
        System.out.println("Introduce el titulo del TFG:");
        titulo = scan.nextLine();
        cn.noSelect("INSERT INTO tfg (titulo,idprofesor,idalumno,convocatoria,fecharegistro)values('" + titulo + "'," + idProf + "," + idAlum + ",0,'" + año + "-" + mes + "-" + dia + "')");
    }
    
 public void listaTFG() {
        //cn.noSelect("delete from tfg where fecharegistro='2016-03-14'");
        int cont = 1;
        rs = cn.Select("Select t1.idTfg,  t1.titulo, t1.convocatoria, p1.idProfesor, p1.nombre, p1.ape1, p1.ape2, p1.emailProfesor "
                + "FROM tfg t1, profesor p1 "
                + "WHERE t1.idProfesor = p1.idProfesor AND "
                + "idAlumno IS NULL ");
        try {
            System.out.println("CAMBIANDOLO .....");
            while (rs.next()) {
                System.out.print(cont + " ");
                System.out.println(rs.getString("idTfg") + " - ");
//                System.out.println(rs.getString("t1.idTfg") + " - ");            
                System.out.println(rs.getString("titulo") + " - ");

                System.out.println(rs.getString("convocatoria") + " - ");
                System.out.println(rs.getString("idProfesor") + " - ");
                System.out.println(rs.getString("nombre") + " - ");
                System.out.println(rs.getString("ape1") + " - ");
                System.out.println(rs.getString("ape2") + " - ");
                System.out.println(rs.getString("emailProfesor") + " - ");
                
                            }
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
>>>>>>> Nuevo-enlace-a-base-de-datos(mio)
        }
        for (int i = 0; i < esta.length; i++) {
            if (!esta[i]) {
                encontrado = false;
            }
        }
        return encontrado;
    }
<<<<<<< HEAD

    // solo para el desarrollo
    public void listarTablas() {
        if (this.conectar()) {
            try {
                rs = cn.obtenerTablas();
                while (rs.next()) {
                    System.out.println(rs.getString("TABLE_NAME"));
=======
 
 
 
 public int mostrarProfesor() {
        Scanner sc = new Scanner(System.in);
        int cont;
        int opcion = 0;
        int id = 0;
        System.out.println("******Profesores registrados******");

        do {
            cont = 1;
            rs = cn.Select("Select idprofesor,nombre,apellido1,apellido2,despacho from profesor");
            //cn.noSelect("delete from profesor where nombre='pepe'");
            try {
                while (rs.next()) {
                    System.out.print(cont + " ");
                    System.out.println(rs.getString("nombre") + " " + rs.getString("ape1") + " " + rs.getString("ape2") + " " + rs.getInt("despacho"));
                    cont++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.print("Que profesor es (pon el numero): ");
            System.out.println("[0] Para salir");
            try {
                opcion = sc.nextInt();
            } catch (Exception e) {
                System.out.println("[ERROR]");
                sc.next();
                opcion = 0;
            }

        } while (0 < opcion && opcion > cont);

        if (opcion != 0) {
            rs = cn.Select("Select idprofesor,nombre,apellido1,apellido2,despacho from profesor");
            cont = 1;
            try {
                while (rs.next()) {
                    if (opcion == cont) {
                        id = rs.getInt("idProfesor");
                    }
                    cont++;
>>>>>>> Nuevo-enlace-a-base-de-datos(mio)
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

<<<<<<< HEAD
    }

public void listarDatosIniciales() {
=======
        return id;
    }

      
    public boolean comprobarAlumno(String email) throws SQLException {
        boolean result = false;
        rs = cn.Select("Select * from alumno "  
                        + "WHERE emailAlumno = '"+email+"'");
        if (rs.next() ){ 
            result = true;
        }
        return result;   
    }
    
    public boolean comprobarProfesor(String email) throws SQLException {
        boolean result = false;
        rs = cn.Select("Select * from profesor "  
                        + "WHERE emailProfesor = '"+email+"'");
        if (rs.next() ){ 
            result = true;
        }
        return result;   
    }
 
  public void insertarProfesor(String nombre, String ape1, String ape2, String email, int despacho) {
        cn.noSelect("INSERT INTO profesor (nombre, ape1, ape2, emailProfesor, despacho) "
                        + "VALUES ('"+nombre+"', '"+ape1+"', '"+ape2+"','"+email+"', '"+despacho+"' )");        
    }
  
  public void insertarAlumno(String nombre, String ape1, String ape2, String email, String numMat) {
        cn.noSelect("INSERT INTO alumno (nombre, ape1, ape2, emailAlumno, numMat) "
                        + "VALUES ('"+nombre+"', '"+ape1+"', '"+ape2+"','"+email+"', '"+numMat+"' )");        
    }

  public void borrarProfesor(String email) {
        cn.noSelect("DELETE * FROM profesor "
                        + "WHERE emailProfesor = '"+email+"' )");        
    }
  
 public void borrarAlumno(String email) {
        cn.noSelect("DELETE * FROM alumno "
                        + "WHERE emailAlumno = '"+email+"' )");        
    }
  public ResultSet listarProfesores() {
      return rs = cn.Select("SELECT * FROM profesor");
  }
  public ResultSet listarAlumnos() {
      return rs = cn.Select("SELECT * FROM alumno");
  }
  public ResultSet listarTfg() {
      return rs = cn.Select("SELECT * FROM tfg");
  }
  
    
    public void insertarDatosIniciales() {      
        cn.noSelect("INSERT INTO profesor (nombre, emailProfesor)"         // Muestra de sentencia para insercion de un SOLO campo
                    + "VALUES ('juan', 'juan@upm.es')");
 
       cn.noSelect("INSERT INTO profesor (nombre, ape1, ape2, emailProfesor, despacho)"      // Muestra de sentencia para insercion de varios campos especificados
                        + "VALUES ('Jose', 'Martin', 'Fernandez', 'jmartinf@upm.es', '1105')") ;
        
        cn.noSelect("INSERT INTO alumno (numMat, nombre, ape1, ape2, emailAlumno)"
                        + "VALUES ('AA0045', 'Eduardo', 'Navarro', 'Del Este', 'edu@alumnos.upm.es')");
 
        cn.noSelect("INSERT INTO tfg (idProfesor, idAlumno, titulo, descripcion, convocatoria, fechaRegistro)" 
                           + "VALUES (1, 1, 'La obsolescencia programada II', 'Este es el campo de la descripción de la obsolescencia programada',"
                           +"'03', '2015-11-23')");
         cn.noSelect("INSERT INTO tfg (idProfesor,  titulo, descripcion, convocatoria, fechaRegistro)" 
                           + "VALUES (1, 'La obsolescencia programada', 'Este es el campo de la descripción de la obsolescencia programada.II',"
                           +"'03', '2015-11-23')");
                           
       }
    
        /* Codigo de prueba Jm
        Quitar cuando se entregue
*/
    
    public void listarDatosIniciales() {
>>>>>>> Nuevo-enlace-a-base-de-datos(mio)
        rs = cn.Select("Select * from profesor");    
        try {
            while (rs.next()) {
                System.out.print("Id de Profesor: " + rs.getString("idProfesor")+ "; ");
                System.out.print("Nombre: " + rs.getString("nombre")+ " ");
                System.out.print(rs.getString("ape1") +" " + rs.getString("ape2")+ "; ");
                System.out.print("Email: " + rs.getString ("emailProfesor") + "; ");
                System.out.println ("Despacho " + rs.getString("despacho"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        rs = cn.Select("Select * from alumno");    
        try {
            while (rs.next()) {
                System.out.print("Id de Alumno: " + rs.getString("idAlumno")+ "; ");
                System.out.print("Nombre: " + rs.getString("nombre")+ " ");
                System.out.print(rs.getString("ape1") +" " + rs.getString("ape2")+ "; ");
                System.out.println("Email: " + rs.getString ("emailAlumno") + " ");
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        rs = cn.Select("Select * from tfg");    
        try {
            while (rs.next()) {
                System.out.print("Id Tfg: " + rs.getString("idTfg")+ "; ");
                System.out.print("Id Profesor: " + rs.getString("idProfesor")+ "; ");
                System.out.print("Id Alumno: " + rs.getString("idAlumno")+ "; ");
                
                System.out.print("Titulo: " + rs.getString("titulo")+ "; ");
                System.out.print("Descripcion: " +" " + rs.getString("descripcion")+ "; ");
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    // solo para el desarrollo
    public void listarTablas() {
        try {
            rs = cn.obtenerTablas();
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // solo para el desarrollo
    public boolean buscarTabla(String s) {
        boolean encontrado = false;

        try {
            rs = cn.obtenerTablas();
            while (rs.next()) {

                if (rs.getString("TABLE_NAME").contentEquals(s.toUpperCase())) {
                    encontrado = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return encontrado;
        }
    }

 /*
  Fin de codigo de prueba
 */   

<<<<<<< HEAD
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
                    + " and false = ( select "+TFG.finalizado+" from "+TFG.tabla+" where "+ TFG.idTfg + " = alumno."+Alumno.idtfg+")"
                    + " and 0 = ( select count(*) from "+Defensa.tabla+" where "+Alumno.idtfg+" = alumno."+Alumno.idtfg
                    + " and "+ Alumno.idAlumno +"= alumno." +Alumno.idAlumno+" and "+ Defensa.nota +"= -1)"
                    );
        }

        return rs;
    }

    
    /*
        Jm. Tiempo 15 minutos
        Obtiene los Tfgs de un profesor
    PENDIENTE: Insertar el no finalizado
    */ 
    ResultSet obtenerTFGProfesor(Profesor profesor) {
        if (this.conectar()) {
           cn.Select("SELECT * FROM" +TFG.tabla 
                    + "WHERE "+TFG.idProfesor+ " = "+profesor.getIdProfesorM()
                    + ")");
        }
        return rs;
    }
     /*
    ** Jm. Tiempo 15 minutos
        Borra un Tfg habiendo pasado su identificador
    */
    public boolean borrarTfg(int idTfgM) {
        boolean exito = false;
        if (this.conectar()) {
            cn.noSelect("DELETE FROM "+TFG.tabla
                    + " WHERE " + TFG.idTfg+ " = " +idTfgM+ ")"); 
            exito = true;
        }
        return exito;
    
    }
    /*
    Jm
    */
    /*
    void obtenerTFGs(int idTfg, int idProfesor) {
        lista.clear();
        rs = sent.obtenerTFGProfesor(profesor);
        try {
            while(rs.next()) {
                tfg = new TFG();
                tfg.setIdTfgM(rs.getInt(TFG.idTfg));
                tfg.setIdProfesorM(rs.getInt(TFG.idProfesor));
                tfg.setTituloM(rs.getString(TFG.titulo));
                tfg.setDescripcionM(rs.getString(TFG.descripcion));
                tfg.setFechaM(rs.getString(TFG.fecha));
                tfg.setFinalizadoM(rs.getBoolean(TFG.finalizado));
                lista.add(tfg);                    
            }
        } catch (SQLException e) {
          if (trazas) {
              Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//*/
    
    ResultSet obtenerTFG(ArrayList<TFG> tfgLista, int idTfg, int idProfesor) {
        if (this.conectar()) {
            cn.noSelect("SELECT * FROM "+TFG.tabla
                    + " WHERE " + TFG.idTfg+ " = " +idTfg+ 
                    ", idProfesor "+TFG.idProfesor
                    + ")"); 
        }
        return rs;
    }


=======
>>>>>>> Nuevo-enlace-a-base-de-datos(mio)

}
