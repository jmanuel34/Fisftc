package fisftc;

import java.util.Scanner;

public class Interfaz {
    	Scanner sc = new Scanner(System.in);
	int opcionMenu;
        Sentencias sen;
        String rs;

    public Interfaz () {
         sen = new Sentencias(); 
         sen.montarBase();
         mostrarInterfaz();
    }

    public void mostrarInterfaz() {
	System.out.println("Iniciando aplicación");
	do {
	    System.out.println("    1- Mostrar lista TFG disponibles.");
	    System.out.println("    2- Añadir un TFG a la base de datos.");
	    System.out.println("    3- Borrar un TFG de la base de datos.");
	    System.out.println("    4- Buscar información de un TFG en la base de datos.");
	    System.out.println("    5- Modificar un TFG en la base de datos.");
            System.out.println("    33- Datos de prueba");
	    System.out.println("    6- Finalizar.");
	    System.out.print("Opción deseada: ");
	    opcionMenu = sc.nextInt();
	    switch (opcionMenu) {
		case 1:
		    System.out.println("------------en progreso------------");
		    //funcion mostrar lista
 //                   sen.listarTablas();
                                    
		    break;
		case 2:                  
                    sen.listarTablas();
		    int opcion2_1,
		     opcion2_2,
		     opcion2_3;
		    boolean otraVez2 = true;
		    boolean profCorrecto = false;
		    boolean alumnoCorrecto = false;
		    do {
			do {
			    System.out.println("Como es el profesor:");
			    System.out.println("    1- Es un profesor nuevo en la base de datos.");
			    System.out.println("    2- Es un profesor ya existente en la base de datos.");
			    System.out.print("Opción deseada: ");
			    opcion2_1 = sc.nextInt();
			    switch (opcion2_1) {
				case 1:
				    //constructor profesor;
				    profCorrecto = true;
				    break;
				case 2:
				    //buscar profesor en base de datos con datos nombre, apellidos y despacho
				    /* String nombre;
				     String apellidos;
				     String despacho;
				     System.out.println("Datos del profesor. (nombre, apellidos , despacho)");
				     System.out.println("    Nombre:");
				     nombre = sc.next();
				     System.out.println("    Apellidos:");
				     apellidos = sc.next();
				     System.out.println("    Despacho:");
				     despacho = sc.next();*/

				    //o se puede hacer con una lista de profesores existentes
				    profCorrecto = true;
				    break;
				default:
				    profCorrecto = false;
				    System.out.println("Opción no valida.");
				    break;
			    }
			} while (!profCorrecto);
			do {
			    System.out.println("El profesor dispone de un alumno interesado:");
			    System.out.println("    1- Si.");
			    System.out.println("    2- No.");
			    opcion2_2 = sc.nextInt();
			    switch (opcion2_2) {
				case 1:
				    //obtener datos del alumno y construir o que se pidan los datos en el constructor de alumnos
				    /* String nombre;
				     String apellidos;
				     String matricula;
				     System.out.println("Datos del alumno. (nombre, apellidos , matricula)");
				     System.out.println("    Nombre:");
				     nombre = sc.next();
				     System.out.println("    Apellidos:");
				     apellidos = sc.next();
				     System.out.println("    matricula:");
				     matricula = sc.next();
				     --- constructor alumno ---
				     */

				    //constructor TFG con parametros de alumno
				    //    por ej--> TFG(profesor,alumno) y que dentro se pidan el nombre y la descripcion del TFG
				    alumnoCorrecto = true;
				    break;
				case 2:
				    //constructor TFG sin parametros de alumno
				    //    por ej--> TFG(profesor) y que dentro se pidan el nombre y la descripcion del TFG
				    alumnoCorrecto = true;
				    break;
				default:
				    alumnoCorrecto = false;
				    System.out.println("Opción no valida.");
				    break;
			    }
			} while (!alumnoCorrecto);
			System.out.println("Quieres añadir mas TFG:");
			System.out.println("    1- Si.");
			System.out.println("    2- No.");
			opcion2_3 = sc.nextInt();
			switch (opcion2_3) {
			    case 1:
				otraVez2 = false;
				break;
			    case 2:
				otraVez2 = true;
				break;
			    default:
				otraVez2 = false;
				System.out.println("Opción no valida.");
				break;
			}
		    } while (!otraVez2);

		    break;
		case 3:
		    System.out.println("------------en progreso------------");
		    //funcion borrar un TFG
		    break;
		case 4:
		    System.out.println("------------en progreso------------");
		    //funcion buscar TFG
		    break;
		case 5:
		    int opcion5_1,opcion5_2;
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
		    } while (!otraVez5);
		    break;
		case 6:
		    System.out.println("finalizando la aplicacion");
		    break;
                case 33:
                    System.out.println( "Secuencia de acciones iniciales.\n"
                            + "1.- Montar base de datos\n"
                            + "2.- Insertar ejemplos de registros.\n"
                            + "3.- Listar los ejemplos");
                    System.out.println();
                    sen.insertarDatosIniciales();
                    sen.listarDatosIniciales(); 
                    break;
                    
		default:
		    System.out.println("Opción no valida.");
		    break;
	    }

	} while (opcionMenu != 6);

    }

}
