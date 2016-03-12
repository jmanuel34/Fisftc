/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fisftc;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Damian
 */
public class Conexion {

    private static Connection conexion;
    private static ResultSet rs;
    private static Statement stat;
    private static boolean exito;

    public Conexion() {
        conexion = null;
        rs = null;
        stat = null;
        exito = false;

    }

    private boolean driver() {
        boolean exitoD = false;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

            exitoD = true;
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            return exitoD;
        }
    }

    public boolean crearDB() {

        try {
            if (this.driver()) {
                conexion = DriverManager.getConnection(
                        "jdbc:derby:.\\DB\\Derby.DB;create=true");
                System.out.println("Registro exitoso");
                exito = true;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        } finally {
            return exito;
        }
    }

    /**
     * Esta función es para introducir sentencias sql que no devuelvan valores
     *
     * @param s es la sentecia sql
     * @return true/false si ha conseguido o no introducir la sentencia en la
     * base de datos
     */
    public boolean noSelect(String s) {
        boolean conseguido = false;
        try {
            if (exito) {
                try {
                    stat = conexion.createStatement();
                    stat.executeUpdate(s);
                    conseguido = true;
                } catch (SQLException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            return conseguido;
        }

    }

    /**
     * Esta función es para introducir sentencias sql que devuelven valores
     * recogidos en un objeto ResultSet que hay que trataro según se necesite
     *
     * @param s es la sentencia sql
     * @return ResultSet que son las filas resultado de la consulta
     */
    public ResultSet Select(String s) {
        try {
            if (exito) {
                try {
                    stat = conexion.createStatement();
                    rs = stat.executeQuery(s);
                } catch (SQLException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            return rs;
        }

    }

    public void close() {
        try {
            if (conexion != null) {
                conexion.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ResultSet obtenerTablas() throws SQLException {
        String[] types = {"TABLE"};
        DatabaseMetaData dbm = conexion.getMetaData();
        rs = dbm.getTables(null, null, "%", types);
        return rs;
    }
}
