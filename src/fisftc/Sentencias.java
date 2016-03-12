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

    public Sentencias() {
        cn = new Conexion();
        scan = new Scanner(System.in);
        if(cn.crearDB()){
            this.motarBase();
        }
    }


    public void cerrar() {
        cn.close();
    }

    public void motarBase() {

        if (!this.buscarTabla("tfg")) {
            if (!this.buscarTabla("profesor")) {
                if (!this.buscarTabla("alumno")) {
                    cn.noSelect("create table profesor("
                            + "  idprofesor integer not null GENERATED ALWAYS AS IDENTITY,"
                            + "  nombre varchar(45) not null,"
                            + "  apellido1 varchar(45),"
                            + "  apellido2 varchar(45),"
                            + "  email varchar(45),"
                            + "  despacho integer,"
                            + "  primary key (idprofesor)"
                            + ")");
                    cn.noSelect("create table alumno("
                            + "  idalumno integer not null GENERATED ALWAYS AS IDENTITY,"
                            + "  nombre varchar(45) not null,"
                            + "  apellido1 varchar(45),"
                            + "  apellido2 varchar(45),"
                            + "  email varchar(45),"
                            + "  numMat integer,"
                            + "  primary key (idalumno)"
                            + ")");
                    cn.noSelect("create table tfg("
                            + "  idtfg integer not null GENERATED ALWAYS AS IDENTITY,"
                            + "  idprofesor integer not null constraint fk_profesor"
                            + "  references profesor on delete cascade,"
                            + "  idalumno integer constraint fk_alumno"
                            + "  references profesor on delete cascade,"
                            + "  titulo varchar(45) not null,"
                            + "  descripcion varchar(300),"
                            + "  convocatoria integer not null,"
                            + "  fecharegistro date not null,"// introducir con este formato '2016-03-11'
                            + "  fechadefensa date,"
                            + "  fechaasignacion date,"
                            + "  notafinal integer,"
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

        rs = cn.Select("Select* from clientes");
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

}
