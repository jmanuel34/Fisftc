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
<<<<<<< HEAD
    public Fisftc() throws SQLException{
        Interfaz interfaz = new Interfaz();
    }


    public static void main(String[] args) throws SQLException {
       Sentencias sn = new Sentencias();
       Conexion cn = sn.obtenerConexion();
       Fisftc aplicacion = new Fisftc();
=======
    /**
     * @param args the command line arguments
     */
    public Fisftc() throws SQLException {
      Interfaz interfaz = new Interfaz();  
    }
   
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        Fisftc aplicacion = new Fisftc();
        
>>>>>>> Nuevo-enlace-a-base-de-datos(mio)
    }

}
