/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fisftc;

import java.sql.ResultSet;
import java.sql.SQLException;
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
                    cn.noSelect("create table profesor("
                            + "  idProfesor integer not null GENERATED ALWAYS AS IDENTITY,"
                            + "  nombre varchar(45) not null,"
                            + "  ape1 varchar(45),"
                            + "  ape2 varchar(45),"
                            + "  email varchar(45),"
                            + "  despacho integer,"
                            + "  primary key (idProfesor)"
                            + ")");
                    cn.noSelect("create table alumno("
                            + "  idAlumno integer not null GENERATED ALWAYS AS IDENTITY,"
                            + "  numMat varchar(6) not null,"
                            + "  nombre varchar(45) not null,"
                            + "  ape1 varchar(45),"
                            + "  ape2 varchar(45),"
                            + "  email varchar(45),"
                            
                            + "  primary key (idalumno)"
                            + ")");
                    cn.noSelect("create table tfg("
                            + "  idTfg integer not null GENERATED ALWAYS AS IDENTITY,"
                            + "  idProfesor integer not null constraint fk_profesor"
                            + "  references profesor on delete cascade,"
                            + "  idAlumno integer constraint fk_alumno"
                            + "  references profesor on delete cascade,"
                            + "  titulo varchar(45) not null,"
                            + "  descripcion varchar(300),"
                            + "  convocatoria varchar(2) not null,"
                            + "  fechaRegistro date not null,"                  // introducir con este formato '2016-03-11'
                            + "  fechaDefensa date,"
                            + "  notaFinal integer,"
                            + "  fechaAsignacion date,"
                            + "  primary key (idtfg)"
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
               
                if(rs.getString("TABLE_NAME").contentEquals(s.toUpperCase()))
                    encontrado = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sentencias.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            return encontrado;
        }
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
                System.out.print("Email: " + rs.getString ("email") + "; ");
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
                System.out.println("Email: " + rs.getString ("email") + " ");
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
    
    public void insertarDatosIniciales() {      
        cn.noSelect("INSERT INTO profesor (nombre)"         // Muestra de sentencia para insercion de un SOLO campo
                    + "VALUES ('juan')");
 
       cn.noSelect("INSERT INTO profesor (nombre, ape1, ape2, email, despacho)"      // Muestra de sentencia para insercion de varios campos especificados
                        + "VALUES ('Jose', 'Martin', 'Fernandez', 'jmartinf@upm.es', 1105)") ;
        
        cn.noSelect("INSERT INTO alumno (numMat, nombre, ape1, ape2, email)"
                        + "VALUES ('AA0045', 'Eduardo', 'Navarro', 'Del Este', 'edu@alumnos.upm.es')");
 
        cn.noSelect("INSERT INTO tfg (idProfesor, idAlumno, titulo, descripcion, convocatoria, fechaRegistro)" 
                           + "VALUES (1, 1, 'La obsolescencia programada', 'Este es el campo de la descripción de la obsolescencia programada',"
                           +"'03', '2015-11-23')");
                           
       }
 /*
  Fin de codigo de prueba
 */   

}
