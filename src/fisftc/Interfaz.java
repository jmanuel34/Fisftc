package fisftc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Interfaz {
        protected Sentencias sen;
    	Scanner sc = new Scanner(System.in);
	int opcionMenu;
        String rs;

    public Interfaz () throws SQLException {
         sen = new Sentencias(); 
         sen.montarBase();
         mostrarInterfaz();
    }

 public void menuAltas() throws SQLException {
     int opcion = -1;     
        System.out.println ("|--------------------------------------------------|");
        System.out.println ("|-- Aplicación para la Gestíón de Trabajos T.F.G --|");
        System.out.println ("|--------------------------------------------------|");
        System.out.println();
        System.out.println();
        System.out.println(" |---------------- 1.- MENU DE ALTAS --------------|");
        do {

            System.out.println("    1.- Alta Profesor.");
            System.out.println("    2.- Alta Alumno.");
            System.out.println("    3.- Alta TFG.");    
            System.out.println("");
            System.out.println("");
            System.out.println();
            System.out.println("    0- Finalizar.");
            System.out.print("Opción deseada: ");
            opcion = sc.nextInt();
        switch (opcion) {
                case 1:
                    altaProfesor();
                    break;
                case 2:
                    altaAlumno();
                    break;
                case 3:
//                    altaTfg();                   
                    break;
                default:
                    break;
            }
        } while (opcionMenu != 0);
    }
    
    private void altaProfesor() throws SQLException  {
        boolean result;
        Scanner sc = new Scanner(System.in);
        String nombre, ape1, ape2, email;
        int desp = 0;
            System.out.println("******NUEVO PROFESOR******");
            System.out.print("Introduce el nombre del profesor: ");
            nombre = sc.nextLine();
            System.out.print("Introduce el primer apellido: ");
            ape1 = sc.nextLine();
            System.out.print("Introduce el segundo apellido: ");
            ape2 = sc.nextLine();
            System.out.print("Introduce el email: ");
            email = sc.nextLine();
            System.out.print("Introduce el número de despacho(4 dígitos): ");
            desp = sc.nextInt();
            // Comprobar que el profesor no existe
            result = sen.comprobarProfesor(email);
            if (!result) {
                sen.insertarProfesor(nombre, ape1, ape2, email, desp);
                System.out.println ("Profesor insertado correctamente. ");
            }
            else {
                System.out.println("!!! Error en la inserción de profesor. ");
                System.out.println("!!! El profesor ya existe. ");
            }
    }

    private void altaAlumno() throws SQLException {
        boolean result;
        Scanner sc = new Scanner(System.in);
        String nombre, ape1, ape2, email;
        String numMat;
        boolean repetido = false;
        boolean continuar = true;
        String opcion = "0";
            System.out.println("****** NUEVO ALUMNO******");
            System.out.print("Introduce el nombre del Alumno: ");
            nombre = sc.nextLine();
            System.out.print("Introduce el primer apellido: ");
            ape1 = sc.nextLine();
            System.out.print("Introduce el segundo apellido: ");
            ape2 = sc.nextLine();
            System.out.print("Introduce el email: ");
            email = sc.nextLine();
            System.out.print("Introduce el número de matrícula(6 caracteres): ");
            numMat = sc.nextLine();
            result = sen.comprobarAlumno(email);
            if (!result) {
                sen.insertarAlumno(nombre, ape1, ape2, email, numMat);
            } else {
                System.out.println("!!! Error en la inserción de Alumno. ");
                System.out.println("!!! El Alumno ya existe. ");
            }
        } 
     private void bajaProfesor() throws SQLException  {
        boolean result;
        Scanner sc = new Scanner(System.in);
        String email;
            System.out.println("****** PROCEDIMIENTO DE BAJA PARA PROFESOR ******");
            System.out.print("Introduce el email: ");
            email = sc.nextLine();
            result = sen.comprobarProfesor(email);
            if (result) {
                sen.borrarProfesor(email);
                System.out.println ("Profesor borrado correctamente. ");
            }
            else {
                System.out.println("!!! Error en la baja de Profesor. ");
                System.out.println("!!! El email de Profesor NO existe. ");
            }
    }

    private void bajaAlumno() throws SQLException {
        boolean result;
        Scanner sc = new Scanner(System.in);
        String email;
            System.out.println("****** PROCEDIMIENTO DE BAJA PARA ALUMNO ******");
            System.out.print("Introduce el email: ");
            email = sc.nextLine();
            result = sen.comprobarAlumno(email);
            if (result) {
                sen.borrarAlumno(email);
            } else {
                System.out.println("!!! Error en la baja de Alumno. ");
                System.out.println("!!! El email de Alumno NO existe. ");
            }
        } 

    private void menuBajas() throws SQLException {
        int opcion = -1;     
        System.out.println ("|--------------------------------------------------|");
        System.out.println ("|-- Aplicación para la Gestíón de Trabajos T.F.G --|");
        System.out.println ("|--------------------------------------------------|");
        System.out.println();
        System.out.println();
        System.out.println(" |---------------- 2.- MENU DE BAJAS --------------|");
        do {

            System.out.println("    1.- Baja Profesor.");
            System.out.println("    2.- Baja Alumno.");
            System.out.println("    3.- Baja TFG.");    
            System.out.println("");
            System.out.println("");
            System.out.println();
            System.out.println("    0- Finalizar.");
            System.out.print("Opción deseada: ");
            opcion = sc.nextInt();
        switch (opcion) {
                case 1:
                    bajaProfesor();
                    break;
                case 2:
                    bajaAlumno();
                    break;
                case 3:
//                    bajaTfg();                   
                    break;
                default:
                    break;
            }
        } while (opcionMenu != 0);

    }
   private void menuInformes() throws SQLException {
        int opcion = -1;     
        System.out.println ("|--------------------------------------------------|");
        System.out.println ("|-- Aplicación para la Gestíón de Trabajos T.F.G --|");
        System.out.println ("|--------------------------------------------------|");
        System.out.println();
        System.out.println();
        System.out.println(" |---------------- 5.- MENU DE INFORMES --------------|");
        do {

            System.out.println("    1.- Informes de Profesores.");
            System.out.println("    2.- Informes de Alumnos.");
            System.out.println("    3.- Informes de TFGs.");    
            System.out.println("");
            System.out.println("");
            System.out.println();
            System.out.println("    0- Finalizar.");
            System.out.print("Opción deseada: ");
            opcion = sc.nextInt();
        switch (opcion) {
                case 1:
                    listarProfesores();
                    break;
                case 2:
                    listarAlumnos();
                    break;
                case 3:
                    listarTfg();                   
                    break;
                default:
                    break;
            }
        } while (opcionMenu != 0);

    }
   private void listarProfesores() {
       ResultSet rs;
       rs = sen.listarProfesores();
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
   }
   
   private void listarAlumnos() {
       ResultSet rs;
       rs = sen.listarAlumnos();
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
        
   }
   private void listarTfg() {
       ResultSet rs;
       rs = sen.listarTfg();
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
       
 

    public void opcion5() {
        System.out.println("------------en progreso------------");
        /*Scanner sc = new Scanner(System.in);
        int opcion5_1, opcion5_2;
        boolean modificoCorrecto = false;
        boolean otraVez5 = false;
        do {
            do {
                System.out.println("Que modificacion quieres realizar:");
                System.out.println("    1- Añadir alumno.");
                System.out.println("    2- Borrar alumno.");
                System.out.print("Opción deseada: ");
                opcion5_1 = sc.nextInt();
                switch (opcion5_1) {
                    case 1:
                        //pedir datos del TFG al que hay que añadir alumno, pedir datos del alumno a añadir y añadirlo;
                        //sec.insertarAlumno();
                        modificoCorrecto = true;
                        break;
                    case 2:
                        //pedir datos del TFG al que hay que borrar alumno, poner el apartado alumno a null;
                        modificoCorrecto = true;
                        break;
                    default:
                        modificoCorrecto = false;
                        System.out.println("Opción no valida.");
                        break;
                }
            } while (!modificoCorrecto);
            System.out.println("Quieres modificar mas TFG:");
            System.out.println("    1- Si.");
            System.out.println("    2- No.");
            opcion5_2 = sc.nextInt();
            switch (opcion5_2) {
                case 1:
                    otraVez5 = false;
                    break;
                case 2:
                    otraVez5 = true;
                    break;
                default:
                    otraVez5 = false;
                    System.out.println("Opción no valida.");
                    break;
            }
        } while (!otraVez5);*/
    }
    public void opcion33() {
        sen.insertarDatosIniciales();
        sen.listarDatosIniciales();
    }
    public void mostrarInterfaz() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcionMenu = -1;
       
        System.out.println ("|--------------------------------------------------|");
        System.out.println ("|-- Aplicación para la Gestíón de Trabajos T.F.G --|");
        System.out.println ("|--------------------------------------------------|");
        System.out.println();
        System.out.println();
        System.out.println(" |------------------- MENU PRINCIPAL ----------------|");
        do {

            System.out.println("    1.- Altas.");
            System.out.println("    2.- Bajas.");
            System.out.println("    3.- Modificaciones.");
            System.out.println("    4.- Consultas.");
            System.out.println("    5.- Informes.");
            System.out.println("   33.- Insertar datos iniciales");
            System.out.println("");
            System.out.println("");
            
            /* La siguiente opcion debera ocultarse a la hora de hacer la presentación al cliente.
            **
            */       

            
            System.out.println();
            System.out.println("    0.- Finalizar.");
            System.out.print("Opción deseada: ");
            try {
                opcionMenu = sc.nextInt();

            } catch (Exception e) {
                System.out.println("[ERROR]");
                sc.next();
                opcionMenu = 0;
            }

            switch (opcionMenu) {
                case 1:
                    //funcion mostrar lista
                    menuAltas();
                    break;
                case 2:
                    //funcion añadir profesor
                    menuBajas();
                    break;
                case 3:
                    //funcion borrar un TFG
//                    menuModificaciones();
                    break;
                case 4:
                    //funcion buscar TFG
//                    menuConsultas();
                    break;
                 case 5:
                    //funcion buscar TFG
                    menuInformes();
                    break;
                    
                case 33:
                    opcion33();
                default:
                    System.out.println("Opción no valida.");
                    break;
            }

        } while (opcionMenu != 0);
        sen.cerrar();
    }
    
    //*
    private void insertarProfesor() throws SQLException {
        boolean result;
        Scanner sc = new Scanner(System.in);
        String nombre, ape1, ape2, email;
        int desp = 0;
        boolean repetido = false;
        boolean continuar = true;
        String opcion = "0";

        do {
            System.out.println("******NUEVO PROFESOR******");
            System.out.print("Introduce el nombre del profesor: ");
            nombre = sc.nextLine();
            System.out.print("Introduce el primer apellido: ");
            ape1 = sc.nextLine();
            System.out.print("Introduce el segundo apellido: ");
            ape2 = sc.nextLine();
            System.out.print("Introduce el email: ");
            email = sc.nextLine();
            System.out.print("Introduce el número de despacho(4 dígitos): ");
            desp = sc.nextInt();
            // Comprobar que el profesor no existe
            result = sen.comprobarProfesor(email);
            if (!result) {
                sen.insertarProfesor(nombre, ape1, ape2, email, desp);
            }
            else {
                System.out.println("Error en la inserción de profesor. ");
                System.out.println("Pulse para continuar .");
                System.out.println("0 para salir .");
            }    
            opcion = sc.nextLine();
                
    }
        while (opcion != "0");
//*/
    }
}
        