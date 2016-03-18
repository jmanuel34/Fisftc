/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fisftc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Damian
 */
public class Sentencias {

    private Conexion cn;
    private ResultSet rs;
    private Scanner scan;
    String sql;
    public Sentencias() {
        cn = new Conexion();
        scan = new Scanner(System.in);
        if(cn.crearDB()){
            this.montarBase();
        }
    }


    public void cerrar() {
        cn.close();
    }

    public void montarBase() {

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
                }
            }
        }
 
    }
    
      /**
     * En este ejemplo podeis ver que las sentencias que devuelven algo hay que
     * tratarlas por medio del bucle while y rs.next() para más infomación
     * goglear...
     *
     * @return
     */
    public boolean ejemplo2() {
        boolean exito = false;

        rs = cn.Select("Select * from clientes");
        exito = true;

        try {
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
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
        }
    }
 
 
 
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
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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


}
