/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fisftc;

import java.sql.SQLException;

/**
 *
 * @author jm
 */
// 
public class Fisftc {
    public Fisftc() throws SQLException{
        Interfaz interfaz = new Interfaz();
    }


    public static void main(String[] args) throws SQLException {
       Sentencias sn = new Sentencias();
       Conexion cn = sn.obtenerConexion();
       Fisftc aplicacion = new Fisftc();
    }

}
