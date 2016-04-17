package fisftc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Interfaz {

    Metodos metodos;
    Validacion validacion;

    Scanner sc = new Scanner(System.in);
    int opcionMenu;
    
    public Interfaz() throws SQLException {
        validacion = new Validacion();
        metodos = new Metodos();
    }

    /**
     * ******** Menu principal
     * *************************************************************
     */

    /* Funcion que implementa la funcionalidad del menu principal apoyandose en la
	 clase Metodos.*/
    void menuPrincipal() {
        int opcionMP = -1;

        do {

            MostrarMP();
            for (int i = 0; i < 3; i++) {
                System.out.println("");
            }
            System.out.println("    Seleccione una opcion:  ");
            /*CAPTURAR*/

            switch (opcionMP) {
                case 1: {/*MOSTRAR TFG*/
                    ArrayList<TFG> lista;

                    lista = new ArrayList<TFG>();
                    metodos.obtenerTfgDisponible(lista);
                    mostarTFGDisponibles(lista);
                }
                break;

                case 2: {
                }
                break;
                case 3: {
                }
                break;
                case 4: {
                }
                break;
                case 5: {
                }
                break;
                case 0: {
                }
                break;
                default: {
                    System.out.println("    OPCION NO VALIDA ");
                }
                break;
            }
        } while (opcionMP != 0);

    }

    /*Funcion que muestra las opiones del menu principal al usuario*/
    void MostrarMP() {
        for (int i = 0; i < 24; i++) {
            System.out.println("");
        }

        System.out.println("|--------------------------------------------------|");
        System.out.println("|-- Aplicación para la Gestíón de Trabajos T.F.G --|");
        System.out.println("|--------------------------------------------------|");

        System.out.println("			 MENU PRINCIPAL");
        System.out.println("    1.- Listar TFG disponibles");
        System.out.println("    2.- Añadir TFG");
        System.out.println("    3.- Archivo de defensas");
        System.out.println("    4.- Añadir defensa");
        System.out.println("    5.- Otras opciones");
        System.out.println("    0.- SALIR");
    }

    /**
     * ******** Otras Opciones
     * **************++++***********************************************
     */

    /* Funcion que implementa la funcionalidad del menu otras opciones apoyandose 
	 en la clase Metodos.*/
    void menuOtrasOpciones() {
        int opcionOO = -1;
        do {

            mostrarOO();
            for (int i = 0; i < 3; i++) {
                System.out.println("");
            }
            System.out.println("    Seleccione una opcion:  ");
            /*CAPTURAR*/

            switch (opcionOO) {
                case 1: {
                }
                break;
                case 2: {
                }
                break;
                case 3: {
                }
                break;
                case 4: {
                }
                break;
                case 0: {
                }
                break;
                default: {
                    System.out.println("    OPCION NO VALIDA ");
                }
                break;
            }
        } while (opcionOO != 0);
    }

    /*Funcion que muestra las opiones del menu otras opciones*/
    void mostrarOO() {
        for (int i = 0; i < 24; i++) {
            System.out.println("");
        }
        System.out.println("|--------------------------------------------------|");
        System.out.println("|--			     	Otras Opciones               --|");
        System.out.println("|--------------------------------------------------|");

        System.out.println("    1.- Modificar TFG");
        System.out.println("    2.- Borrar un TFG");
        System.out.println("    3.- Modificar un profesor");
        System.out.println("    4.- Dar de baja un profesor");
        System.out.println("    0.- MENU PRINCIPAL");
    }

    void mostarTFGDisponibles(ArrayList<TFG> lista) {
        int i = 0, k = -1;
        String aux;
        for (int j = 0; j < 24; j++) {
            System.out.println("");
        }

        System.out.println("Listado de TFG disponibles:");

        do {
            System.out.println(i + 1 + "-   " + lista.get(i).getTituloM());
            i++;
        } while (i < lista.size());
        System.out.println("");
        System.out.println("Selecione un trabajo para ampliar:               0 Para salir");
        do {
            aux = sc.nextLine();
        } while (!validacion.getInt(aux, 0, lista.size()));
        k = Integer.parseInt(aux);
        if (k != 0) {
            mostrarTFG(lista.get(k + 1), metodos.obtenerProfesor(lista.get(k + 1)));
        }
    }

    /**
     * funcion que muestra por pantalla la info de un trabajo.
     *
     * @param trabajo el trabajo que se quiere mostrar
     * @param profe el profesor que lleva el trabajo
     */
    void mostrarTFG(TFG trabajo, Profesor profe) {
        String aux;
        for (int i = 0; i < 24; i++) {
            System.out.println("");
            System.out.println("DETALLES DEL PROYECTO:");
            System.out.println("");
            System.out.println("TITULO:" + trabajo.getTituloM());
            System.out.println("Descripcion:" + trabajo.getDescripcionM());
            System.out.println("TUTOR:" + profe.getNombreM());
            System.out.println("0 Para salir");
            do {
                aux = sc.nextLine();
            } while (!validacion.getInt(aux, 0, 0));
        }
    }

}
