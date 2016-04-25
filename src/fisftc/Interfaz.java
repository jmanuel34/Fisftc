package fisftc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Interfaz {

    private Metodos metodos;
    private Validacion validacion;
    private boolean trazas = true;
    boolean prueba;
    int probar;

    Scanner sc = new Scanner(System.in);
    int opcionMenu;

    public Interfaz() {
        validacion = new Validacion();
        metodos = new Metodos();
        this.menuPrincipal();
    }

    private void menuPrincipal() {
        int opcionMP = -1;
        String captura;

        do {

            MostrarMP();
            for (int i = 0; i < 3; i++) {
                System.out.println("");
            }
            System.out.println("    Seleccione una opcion:  ");

            do {
                captura = sc.nextLine();
                if (!validacion.getInt(captura, 0, 6)) {
                    System.out.print("Opcion no valida, seleccione otra:");
                }
            } while (!validacion.getInt(captura, 0, 6));

            opcionMP = Integer.parseInt(captura);
            switch (opcionMP) {
                case 1:/*MOSTRAR TFG*/
                    ArrayList<TFG> lista;
                    lista = new ArrayList<TFG>();
                    metodos.obtenerTfgDisponible(lista);
                    if (!lista.isEmpty()) {
                        mostarTFGDisponibles(lista);
                    } else {
                        System.out.println("Lista Vacia...");
                        System.out.println("pulse cualquier tecla para continuar");
                        sc.nextLine();
                    }

                    break;
                case 2:
                    this.aniadirTFG();
                    break;
                case 3:
                    this.asignarAlumnoATfg();
                    break;
                case 4:
                    this.mostrarDefensas();
                    break;
                case 5:
                    this.aniadirDefensa();
                    break;
                case 6:
                    this.menuOtrasOpciones();
                    break;
                case 0:
                    break;
            }
        } while (opcionMP != 0);

    }

    /**
     * Funcion que muestra las opiones del menu principal al usuario
     */
    private void MostrarMP() {
        for (int i = 0; i < 24; i++) {
            System.out.println("");
        }

        System.out.println("|--------------------------------------------------|");
        System.out.println("|-- Aplicación para la Gestíón de Trabajos T.F.G --|");
        System.out.println("|--------------------------------------------------|");

        System.out.println("			 MENU PRINCIPAL");
        System.out.println("    1.- Listar TFG disponibles");
        System.out.println("    2.- Añadir TFG");
        System.out.println("    3.- Asignar TFG");
        System.out.println("    4.- Archivo de defensas");
        System.out.println("    5.- Añadir defensa");
        System.out.println("    6.- Otras opciones");
        System.out.println("    0.- SALIR");

    }

    private void asignarAlumnoATfg() {
        ArrayList<TFG> lista;
        lista = new ArrayList<TFG>();
        metodos.obtenerTfgDisponible(lista);
        if (!lista.isEmpty()) {
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
                if (!validacion.getInt(aux, 0, lista.size())) {
                    System.out.print("Opcion no valida, seleccione otra:");
                }
            } while (!validacion.getInt(aux, 0, lista.size()));
            k = Integer.parseInt(aux);
            if (k != 0) {
                this.ampliarTFG(lista.get(k - 1));
            }
        } else {
            System.out.println("Lista Vacia...");
            System.out.println("pulse cualquier tecla para continuar");
            sc.nextLine();
        }

    }

    private void ampliarTFG(TFG trabajo) {
        int i = -1;
        String aux;
        mostrarTFG(trabajo, metodos.obtenerProfesor(trabajo));
        System.out.println(" OPCIONES");
        System.out.println(" 1- Asignar Alumno");
        System.out.println(" 0- Volver");
        do {
            aux = sc.nextLine();
            if (!validacion.getInt(aux, 0, 1)) {
                System.out.print("Opcion no valida, seleccione otra:");
            }
        } while (!validacion.getInt(aux, 0, 1));
        i = Integer.parseInt(aux);
        if (i == 1) {
            asignarAlumno(trabajo);
        }
    }

    /**
     * Pide datos y añade una defensa.
     */
    private void aniadirDefensa() {
        int contador, opcionAlumno, opcionConv;
        String captura;
        ArrayList<Convocatoria> convLista = new ArrayList<Convocatoria>();
        ArrayList<Alumno> alumnoLista = new ArrayList<Alumno>();
        Convocatoria conv = new Convocatoria();
        Alumno alum = new Alumno();
        TFG tfg = new TFG();
        Defensa def = new Defensa();
        Iterator<Convocatoria> iterConvocatoria;
        Calendar c = new GregorianCalendar();
        int anioActual = c.get(Calendar.YEAR);

        metodos.obtenerAlumnosDefendibles(alumnoLista);

        contador = 1;
        Iterator<Alumno> iterAlumno = alumnoLista.iterator();
        System.out.println("Alumnos a los que se le puede asignar Defensa:");
        while (iterAlumno.hasNext()) {
            alum = iterAlumno.next();
            tfg = metodos.obtenerTFG(alum);
            System.out.println("[" + contador + "]" + "  Nombre: " + alum.getNombreM()
                    + "\t Titulo: " + tfg.getTituloM());
            contador++;
        }
        if (contador > 1) {
            System.out.println("Elige una opcion:\n [0] Salir");
            do {
                captura = sc.nextLine();
                if (!validacion.getInt(captura, 0, contador)) {
                    System.out.println("Opcion no valida, seleccione otra:");
                }
            } while (!validacion.getInt(captura, 0, contador));

            opcionAlumno = Integer.parseInt(captura);

            if (opcionAlumno != 0) {

                alum = alumnoLista.get(opcionAlumno - 1);

                metodos.obtenerConvocatoria(convLista);
                if (!convLista.isEmpty()) {
                    System.out.println("Convocatorias que se le puede asignar Defensa:");
                    iterConvocatoria = convLista.iterator();
                    int i = 0;
                    while (iterConvocatoria.hasNext()) {
                        if (Integer.parseInt(iterConvocatoria.next().getAnioM()) < anioActual) {
                            convLista.remove(i);
                        }
                        i++;
                    }

                    contador = 1;
                    iterConvocatoria = convLista.iterator();
                    while (iterConvocatoria.hasNext()) {
                        conv = iterConvocatoria.next();
                        System.out.println("[" + contador + "]" + "  Año: " + conv.getAnioM()
                                + " Mes: " + conv.getMesM()
                                + " Tipo: " + conv.getTipoM());
                        contador++;
                    }
                    if (contador > 1) {
                        do {
                            captura = sc.nextLine();
                            if (!validacion.getInt(captura, 0, contador)) {
                                System.out.println("Opcion no valida, seleccione otra:");
                            }
                        } while (!validacion.getInt(captura, 0, contador));

                        opcionConv = Integer.parseInt(captura);

                        if (opcionConv != 0) {
                            conv = convLista.get(opcionConv - 1);

                            def.setIdAlumnoM(alum.getIdAlumnoM());
                            def.setIdTfgM(alum.getIdtfgM());
                            def.setIdConvocatoriaM(conv.getidConvocatoriaM());

                            System.out.println("Introduce el dia");

                            do {
                                captura = sc.nextLine();
                                captura = conv.getAnioM() + "-" + conv.getMesM() + "-" + captura;
                                if (!validacion.validarFecha(captura)) {
                                    System.out.println("Día no válido.");
                                }
                            } while (!validacion.validarFecha(captura));

                            def.setFechaDefensaM(captura);

                            if (!metodos.aniadirDefensa(def) && trazas) {
                                System.out.println("Error al introducir Defensa");
                            } else {
                                System.out.println("Exito al introducir Defensa");
                            }
                        }
                    }
                } else {
                    System.out.println("No hay Convocatorias que asignar a Defensa.");
                    System.out.println("¿Deseas crear una nueva convocatoria?");
                    System.out.println("[1] Si");
                    System.out.println("[0] Salir");

                    do {
                        captura = sc.nextLine();
                        if (!validacion.getInt(captura, 0, 1)) {
                            System.out.println("Opcion no valida, seleccione otra:");
                        }
                    } while (!validacion.getInt(captura, 0, 1));

                    if (Integer.parseInt(captura) == 1) {
                        int id = this.crearConvocatoria();
                        if (id != 0) {
                            def.setIdAlumnoM(alum.getIdAlumnoM());
                            def.setIdTfgM(alum.getIdtfgM());
                            def.setIdConvocatoriaM(id);
                        }
                    }
                }

            }
        } else {
            System.out.println("No hay alumnos a los que asignar Defensa.");
        }

    }

    private int crearConvocatoria() {
        String captura;
        int opcion, id = 0;
        int[] datos = null;
        int i = 0;
        Convocatoria conv = new Convocatoria();
        System.out.println("Introduce una fecha(yyyy-mm-dd):");
        do {
            captura = sc.nextLine();
            if (!validacion.validarFecha(captura)) {
                System.out.println("Fecha no valida.");
            }
        } while (!validacion.validarFecha(captura));

        StringTokenizer tokens = new StringTokenizer(captura, "-");
        int nDatos = tokens.countTokens();
        if (nDatos == 3) {
            datos = new int[nDatos];
            while (tokens.hasMoreTokens()) {
                String str = tokens.nextToken();
                datos[i] = Integer.valueOf(str).intValue();
                i++;
            }
        }
        conv.setAnioM("" + datos[0]);
        conv.setMesM("" + datos[1]);

        System.out.println("De caracter: \n[1]Ordinario \n[2]Extraordinario");
        do {
            captura = sc.nextLine();
            if (!validacion.getInt(captura, 0, 2)) {
                System.out.println("Opcion no valida, seleccione otra:");
            }
        } while (!validacion.getInt(captura, 0, 2));

        opcion = Integer.parseInt(captura);
        if (opcion == 1) {
            conv.setTipoM("Ordinaria");
        } else if (opcion == 2) {
            conv.setTipoM("Extraordinaria");
        }

        if (opcion != 0) {
            id = metodos.aniadirConvocatoria(conv);
        }
        return id;
    }

    /**
     *
     * Esta función se encarga de pedir año y mes de convocatoria para luego
     * buscar en la base de datos las defensas que coincidan con la convocatora
     * pedida y muestra Titulo,Nombre,Año,Mes,Tipo,Nota por pantalla.
     *
     */
    private void mostrarDefensas() {
        int contador, opcionAnio = 0, opcionMes = 0, alumnoEncontrado, tfgEncontrado;
        String captura;
        boolean encontradoTfg, encontradoAlumno;
        ArrayList<String> aniosConv = new ArrayList<String>();
        ArrayList<String> mesesConv = new ArrayList<String>();
        ArrayList<Convocatoria> convLista = new ArrayList<Convocatoria>();
        ArrayList<Defensa> defensaLista = new ArrayList<Defensa>();
        ArrayList<Alumno> alumnoLista = new ArrayList<Alumno>();
        ArrayList<TFG> tfgLista = new ArrayList<TFG>();
        Convocatoria conv = new Convocatoria();
        Defensa def = new Defensa();
        TFG tfg = new TFG();
        Alumno alumno = new Alumno();
        Iterator<String> iterAniosConv;

        metodos.obtenerAnios(aniosConv);

        System.out.println("Años de Defensa disponibles");

        if (!aniosConv.isEmpty()) {

            contador = 1;

            iterAniosConv = aniosConv.iterator();

            while (iterAniosConv.hasNext()) {
                System.out.println("[" + contador + "]" + "  " + iterAniosConv.next());
                contador++;
            }

            System.out.println("Marca el que desees");
            System.out.println("[0] Salir");

            do {
                captura = sc.nextLine();
                if (!validacion.getInt(captura, 0, contador)) {
                    System.out.print("Opcion no valida, seleccione otra:");
                }
            } while (!validacion.getInt(captura, 0, contador));

            opcionAnio = Integer.parseInt(captura);

        } else {
            System.out.println("No hay defensas disponibles");
            System.out.println("pulse cualquier tecla para continuar");
            sc.nextLine();
        }

        if (opcionAnio != 0) {
            System.out.println("Meses de defensas disponibles para el año " + aniosConv.get(opcionAnio - 1));
            metodos.obtenerConvocatoria(convLista);
            metodos.obtenerConvocatoria(convLista, aniosConv.get(opcionAnio - 1));

            if (!convLista.isEmpty()) {
                contador = 1;
                Iterator<Convocatoria> iterConv = convLista.iterator();

                while (iterConv.hasNext()) {
                    System.out.println("[" + contador + "]" + "  " + iterConv.next().getMesM());
                    contador++;
                }

                System.out.println("Marca el que desees");
                System.out.println("[0] Salir");

                do {
                    captura = sc.nextLine();
                    if (!validacion.getInt(captura, 0, contador)) {
                        System.out.print("Opcion no valida, seleccione otra:");
                    }
                } while (!validacion.getInt(captura, 0, contador));

                opcionMes = Integer.parseInt(captura);

                if (opcionMes != 0) {
                    conv = convLista.get(opcionMes - 1);
                    metodos.obtenerAlumnos(alumnoLista);
                    metodos.obtenerDefensa(defensaLista);
                    metodos.obtenerTFGs(tfgLista);

                    if (!defensaLista.isEmpty()) {
                        Iterator<Defensa> iterDefensa = defensaLista.iterator();

                        while (iterDefensa.hasNext()) {
                            def = iterDefensa.next();

                            if (def.getIdConvocatoriaM() == conv.getidConvocatoriaM()) {
                                alumnoEncontrado = 0;
                                tfgEncontrado = 0;
                                encontradoTfg = false;
                                encontradoAlumno = false;
                                Iterator<Alumno> iterAlumno = alumnoLista.iterator();
                                Iterator<TFG> iterTFG = tfgLista.iterator();

                                while (iterAlumno.hasNext() && !encontradoTfg) {
                                    if (iterAlumno.next().getIdAlumnoM() != def.getIdAlumnoM()) {
                                        alumnoEncontrado++;
                                    } else {
                                        encontradoTfg = true;
                                    }
                                }

                                while (iterTFG.hasNext() && !encontradoAlumno) {
                                    if (iterTFG.next().getIdTfgM() != def.getIdTfgM()) {
                                        tfgEncontrado++;
                                    } else {
                                        encontradoAlumno = true;
                                    }
                                }

                                tfg = tfgLista.get(tfgEncontrado);
                                alumno = alumnoLista.get(alumnoEncontrado);

                                System.out.println("Titulo: " + tfg.getTituloM()
                                        + "  Alumno: " + alumno.getNombreM()
                                        + "  Año: " + conv.getAnioM()
                                        + "  Mes: " + conv.getMesM()
                                        + "  Tipo: " + conv.getTipoM()
                                        + "  Nota: " + def.getNotaM());
                            }
                        }
                    }
                    System.out.println("pulse cualquier tecla para continuar");
                    sc.nextLine();
                }
            } else if (trazas) {
                System.out.println("OJO meses vacios");
            }
        }

    }

    /**
     *
     * Muestra por pantalla una lista de trabajos
     *
     * @param lista lista de trabajos a mostrar
     */
    private void mostarTFGDisponibles(ArrayList<TFG> lista) {
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
            if (!validacion.getInt(aux, 0, lista.size())) {
                System.out.print("Opcion no valida, seleccione otra:");
            }
        } while (!validacion.getInt(aux, 0, lista.size()));
        k = Integer.parseInt(aux);
        if (k != 0) {
            mostrarTFG(lista.get(k - 1), metodos.obtenerProfesor(lista.get(k - 1)));
        }

        System.out.println("pulse cualquier tecla para continuar");
        sc.nextLine();

    }

    private void asignarAlumno(TFG trabajo) {
        String aux;
        int idAlumno;
        Alumno alum = new Alumno();
        System.out.println("Introduzca el nombre del alumno");
        aux = sc.nextLine();
        alum.setNombreM(aux);
        System.out.println("Introduzca el primer apellido del alumno");
        aux = sc.nextLine();
        alum.setApe1M(aux);
        System.out.println("Introduzca el segundo apellido del alumno");
        aux = sc.nextLine();
        alum.setApe2M(aux);
        System.out.println("Introduzca el email del alumno");
        do {
            aux = sc.nextLine();
            if (!validacion.validarEmail(aux)) {
                System.out.print("Opcion no valida, seleccione otra:");
            }
        } while (!validacion.validarEmail(aux));

        alum.setEmailAlumnoM(aux);
        alum.setIdtfgM(trabajo.getIdTfgM());

        idAlumno = metodos.aniadirAlumno(alum);
        alum.setIdAlumnoM(idAlumno);
        alum.setIdtfgM(trabajo.getIdTfgM());
        metodos.modificarAlumno(alum);
        if (trazas) {
            System.out.println("OJO" + idAlumno);
            System.out.println("OJO--" + trabajo.getIdTfgM());
        }
    }

    private void borrarProfesor() {
        String aux;
        int opcion = -1;
        Profesor profesor = new Profesor();
        Iterator<TFG> iteradorTfg;
        ArrayList<TFG> tfgList = new ArrayList<TFG>();
        System.out.println ("Borrando profesor ");
        System.out.println("Introduzca el email del profesor: ");
        aux = sc.nextLine();
        do {
            aux = sc.nextLine();
            if (!validacion.validarEmail(aux)) {
                System.out.print("Opcion no valida, seleccione otra:");
            }
        } while (!validacion.validarEmail(aux));
        profesor = metodos.obtenerProfesor(aux);
        if (profesor.getEmailProfesorM()!= null ) {
            // Falta: 
            // Contemplar la posibilidad de, si no tiene TFGs, quitarlo de la base de datos
            metodos.obtenerTFGProfesor(tfgList, profesor);
            iteradorTfg = tfgList.iterator();
            
            while (opcion!= 0 && iteradorTfg.hasNext()) {
                System.out.println ("El trabajo de titulo " + iteradorTfg.next().getTituloM()+" Esta asignado a " +profesor.getEmailProfesorM());
                opcion = menuCambioTfg(opcion);
//                Trabajo trabajo = new Trabajo(iterador.next());
                System.out.println ("Indique accion: ");

                if (opcion == 0) metodos.borrarTfg(iteradorTfg.next().getIdTfgM()); 
                
 //               else metodos.modificarTFG(iteradorTfg.next().getIdTfgM());
                else cambiarProfesorTfg((iteradorTfg.next().getIdTfgM(), (iteradorTfg.next().getIdProfesorM() ) {
                
            }
            }
                
            profesor.setEstadoM(false);
            metodos.modificarProfesor(profesor);
            trazas = false;
            System.out.println( "Profesor con email " +aux+"dado de baja");
        }
        else {
        if (trazas) {
            System.out.println("OJO" + profesor.getIdProfesorM());
        }
        }
    }
    private int menuCambioTfg(int opcion) {
        String captura;
//*
            for (int i = 0; i < 24; i++) {
            System.out.println("");
        }

        System.out.println("|--------------------------------------------------|");
        System.out.println("|-- Aplicación para la Gestíón de Trabajos T.F.G --|");
        System.out.println("|--------------------------------------------------|");

        System.out.println("			 MENU CAMBIO TFG");
        System.out.println("    1.- Borrar TFG");
        System.out.println("    2.- Reasignar Profesor de TFG ");

        System.out.println("    0.- SALIR");
//*/
//            do {
            for (int i = 0; i < 3; i++) {
                System.out.println("");
            }
            System.out.println("    Seleccione una opcion:  ");

            do {
                captura = sc.nextLine();
                if (!validacion.getInt(captura, 0, 2)) {
                    System.out.print("Opcion no valida, seleccione otra:");
                }
            } while (!validacion.getInt(captura, 0, 2));

            opcion = Integer.parseInt(captura);
//            } while (opcion != 0);
    return opcion;    
  }
    
    /**
     * funcion que muestra por pantalla la info de un trabajo.
     *
     * @param trabajo el trabajo que se quiere mostrar
     * @param profe el profesor que lleva el trabajo
     */
    private void mostrarTFG(TFG trabajo, Profesor profe) {
        String aux;
        for (int i = 0; i < 24; i++) {
        }
        System.out.println("");
        System.out.println("DETALLES DEL PROYECTO:");
        System.out.println("");
        System.out.println("TITULO:" + trabajo.getTituloM());
        System.out.println("Descripcion:" + trabajo.getDescripcionM());
        System.out.println("TUTOR:" + profe.getNombreM());
    }

    /**
     * ***********************Aniadir TFG************************************
     */
    private void aniadirTFG() {
        String aux;
        int idProfesor, contador;
        int j = -1;
        TFG trab = new TFG();
        for (int i = 0; i < 24; i++) {
            System.out.println("");
        }
        System.out.print("TITULO:");
        aux = sc.nextLine();
        trab.setTituloM(aux);

        for (int i = 0; i < 24; i++) {
            System.out.println("");
        }

        System.out.print("DESCRIPCION:");
        aux = sc.nextLine();
        trab.setDescripcionM(aux);

        for (int i = 0; i < 24; i++) {
            System.out.println("");
        }

        System.out.println("PROFESOR:");
        System.out.println("");
        System.out.println("0- Profesor existente");
        System.out.println("1- Nuevo profesor");

        do {
            aux = sc.nextLine();
            if (!validacion.getInt(aux, 0, 1)) {
                System.out.print("Opcion no valida, seleccione otra:");
            }
        } while (!validacion.getInt(aux, 0, 1));
        j = Integer.parseInt(aux);
        if (j == 0) {
            ArrayList<Profesor> profesores = new ArrayList<Profesor>();
            metodos.obtenerProfesores(profesores);
            if (!profesores.isEmpty()) {
                Profesor profe = this.mostarProfesores(profesores);
                trab.setIdProfesorM(profe.getIdProfesorM());
                metodos.aniadirTfg(trab);
            } else {
                System.out.println("Lista vacia...");
            }
        } else if (j == 1) {
            idProfesor = aniadirProfesor();
            trab.setIdProfesorM(idProfesor);
            int idtfg = metodos.aniadirTfg(trab);
            if (trazas) {
                System.out.println("OJO" + idProfesor);
                System.out.println("OJO tfg" + idtfg);
            }
        }
    }

    /**
     * Funcion que implementa la funcionalidad del menu otras opciones
     * apoyandose en la clase Metodos.
     */
    private void menuOtrasOpciones() {
        int opcionOO = -1;
        String captura;
        do {

            mostrarOO();
            for (int i = 0; i < 3; i++) {
                System.out.println("");
            }
            System.out.println("    Seleccione una opcion:  ");
            /*CAPTURAR*/

            do {
                captura = sc.nextLine();
                if (!validacion.getInt(captura, 0, 8)) {
                    System.out.print("Opcion no valida, seleccione otra:");
                }
            } while (!validacion.getInt(captura, 0, 8));
            opcionOO = Integer.parseInt(captura);
            switch (opcionOO) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 0:
                    break;
            }
        } while (opcionOO != 0);
    }

    /**
     * Funcion que muestra las opiones del menu otras opciones
     */
    private void mostrarOO() {
        for (int i = 0; i < 24; i++) {
            System.out.println("");
        }
        System.out.println("|--------------------------------------------------|");
        System.out.println("|--			     	Otras Opciones               --|");
        System.out.println("|--------------------------------------------------|");

        System.out.println("    1.- Modificar TFG");
        System.out.println("    2.- Borrar un TFG");
        System.out.println("    3.- Modificar Convocatoria");
        System.out.println("    4.- Dar de baja una Convocatoria");
        System.out.println("    5.- Modificar un profesor");
        System.out.println("    6.- Dar de baja un profesor");
        System.out.println("    7.- Modificar un alumno");
        System.out.println("    8.- Dar de baja un alumno");
        System.out.println("\n    0.- MENU PRINCIPAL");
    }

    private void modificatTFG() {
        ArrayList<TFG> tfgLista = new ArrayList<TFG>();
        ArrayList<Profesor> profesorLista = new ArrayList<Profesor>();
        int contador, opcion, opcionCampos = 0;
        String captura;
        TFG tfg = new TFG();
        Profesor prof = new Profesor();

        metodos.obtenerTFGs(tfgLista);

        if (!tfgLista.isEmpty()) {

            Iterator<TFG> iterTfg = tfgLista.iterator();
            contador = 0;

            while (iterTfg.hasNext()) {
                tfg = iterTfg.next();
                prof = metodos.obtenerProfesor(tfg);
                System.out.println("");
                System.out.println("DETALLES DEL PROYECTO[" + (contador + 1) + "]");
                System.out.println("");
                System.out.print("Titulo: " + tfg.getTituloM());
                System.out.print(" Tutor:" + prof.getNombreM());
                System.out.print("\nDescripcion:" + tfg.getDescripcionM());

                contador++;
            }

            if (contador > 0) {
                System.out.println("Seleciona el TFG que deseas modificar: \n[0] Para salir");
                do {
                    captura = sc.nextLine();
                    if (!validacion.getInt(captura, 0, contador - 1)) {
                        System.out.print("Opcion no valida, seleccione otra:");
                    }
                } while (!validacion.getInt(captura, 0, contador - 1));
                opcion = Integer.parseInt(captura);

                tfg = tfgLista.get(opcion);

                do {

                    System.out.println("Selecciona elemento a Modificar:");
                    System.out.println("[1]Profesor");
                    System.out.println("[2]Titulo");
                    System.out.println("[3]Descripcion");
                    System.out.println("[4]Finalizado");
                    System.out.println("\n[0] Salir");

                    do {
                        captura = sc.nextLine();
                        if (!validacion.getInt(captura, 0, 4)) {
                            System.out.print("Opcion no valida, seleccione otra:");
                        }
                    } while (!validacion.getInt(captura, 0, 4));

                    opcionCampos = Integer.parseInt(captura);

                    switch (opcionCampos) {
                        case 1:
                            System.out.print("Profesor actual: " + prof.getNombreM());
                            System.out.print("Reasignar: ");
                            System.out.print("1- Profesor existente");
                            System.out.print("2- Nuevo profesor");
                            System.out.print("0- Salir");

                            do {
                                captura = sc.nextLine();
                                if (!validacion.getInt(captura, 0, 2)) {
                                    System.out.print("Opcion no valida, seleccione otra:");
                                }
                            } while (!validacion.getInt(captura, 0, 2));

                            opcionCampos = Integer.parseInt(captura);

                            if (opcionCampos == 1) {
                                metodos.obtenerProfesores(profesorLista);
                                if (!profesorLista.isEmpty()) {
                                    contador = 1;
                                    Iterator<Profesor> iterProfe = profesorLista.iterator();
                                    while (iterProfe.hasNext()) {
                                        prof = iterProfe.next();
                                        System.out.println("[" + contador + "] Nombre: " + prof.getNombreM());
                                    }

                                    do {
                                        captura = sc.nextLine();
                                        if (!validacion.getInt(captura, 0, contador)) {
                                            System.out.print("Opcion no valida, seleccione otra:");
                                        }
                                    } while (!validacion.getInt(captura, 0, contador));

                                    prof = profesorLista.get(Integer.parseInt(captura) - 1);
                                }
                            } else if (opcionCampos == 2) {
                                tfg.setIdProfesorM(this.aniadirProfesor());
                            }
                            if (opcionCampos != 0) {
                                tfg.setIdProfesorM(prof.getIdProfesorM());

                                metodos.modificarTFG(tfg);
                            }
                            break;
                        case 2:
                            System.out.print("Titulo actual: " + tfg.getTituloM() + "Titulo nuevo:");
                            do {
                                captura = sc.nextLine();
                                if (!validacion.getString(3, captura)) {
                                    System.out.print("El titulo debe contener al menos 3 caracteres");
                                }
                            } while (!validacion.getString(3, captura));

                            tfg.setTituloM(captura);
                            metodos.modificarTFG(tfg);
                            break;
                        case 3:
                            System.out.print("Descripcion actual:\n" + tfg.getDescripcionM() + "\nTitulo nuevo:");
                            captura = sc.nextLine();
                            tfg.setTituloM(captura);
                            metodos.modificarTFG(tfg);
                            break;
                        case 4:
                            if (!tfg.getFinalizadoM()) {
                                System.out.print("Estado actual: No Finalizado \n[1]Para marcarlo como finalizado\n[0]Salir");

                                do {
                                    captura = sc.nextLine();
                                    if (!validacion.getInt(captura, 0, 1)) {
                                        System.out.print("Opcion no valida, seleccione otra:");
                                    }
                                } while (!validacion.getInt(captura, 0, 1));
                                if (Integer.parseInt(captura) == 1) {
                                    tfg.setFinalizadoM(true);
                                    metodos.modificarTFG(tfg);
                                }
                            } else {
                                System.out.print("Opcion no valida el tfg está finalizado, seleccione otra:");
                            }
                            break;
                        case 0:
                            break;
                    }
                } while (opcionCampos != 0);

            }
        }
    }

    private int aniadirProfesor() {
        String aux;
        Profesor prof = new Profesor();
        System.out.print("Nombre:");
        aux = sc.nextLine();
        prof.setNombreM(aux);
        System.out.print("Primer Apellido:");
        aux = sc.nextLine();
        prof.setApe1M(aux);
        System.out.print("Segundo Apellido");
        aux = sc.nextLine();
        prof.setApe2M(aux);
        System.out.print("email:");
        do {
            aux = sc.nextLine();
            if (!validacion.validarEmail(aux)) {
                System.out.print("Opcion no valida, seleccione otra:");
            }
        } while (!validacion.validarEmail(aux));
        prof.setEmailProfesorM(aux);
        System.out.print("Despacho:");
        aux = sc.nextLine();
        prof.setDespachoM(aux);
        return metodos.aniadirProfesor(prof);
    }

    private void modificatProfesor() {
        ArrayList<Profesor> profesorLista = new ArrayList<Profesor>();
        int contador, opcion, opcionCampos = 0;
        String captura, estado, aux;
        TFG tfg = new TFG();
        Profesor prof = new Profesor();

        metodos.obtenerProfesores(profesorLista);

        if (!profesorLista.isEmpty()) {

            Iterator<Profesor> iterProfesor = profesorLista.iterator();
            contador = 0;

            while (iterProfesor.hasNext()) {
                prof = iterProfesor.next();
                System.out.println("");
                System.out.println("DETALLES DEL PROFESOR[" + (contador + 1) + "]");
                System.out.println("");
                System.out.println(" NOMBRE: " + prof.getNombreM());
                System.out.println(" PRIMER APELLIDO: " + prof.getApe1M());
                System.out.println(" SEGUNDO APELLIDO: " + prof.getApe2M());
                System.out.println(" EMAIL: " + prof.getEmailProfesorM());
                System.out.println(" DESPACHO: " + prof.getDespachoM());
                if (prof.isEstadoM()) {
                    estado = "activo";
                } else {
                    estado = "baja";
                }
                System.out.println(" ESTADO: " + estado);
                contador++;
            }

            if (contador > 0) {
                System.out.println("Seleciona el Profesor que deseas modificar: \n[0] Para salir");
                do {
                    captura = sc.nextLine();
                    if (!validacion.getInt(captura, 0, contador - 1)) {
                        System.out.print("Opcion no valida, seleccione otra:");
                    }
                } while (!validacion.getInt(captura, 0, contador - 1));
                opcion = Integer.parseInt(captura);

                prof = profesorLista.get(opcion);

                do {

                    System.out.println("Selecciona elemento a Modificar:");
                    System.out.println("[1]Nombre");
                    System.out.println("[2]Primer apellido");
                    System.out.println("[3]Segundo apellido");
                    System.out.println("[4]Email");
                    System.out.println("[5]Despacho");
                    System.out.println("[6]Estado");
                    System.out.println("\n[0] Salir");

                    do {
                        captura = sc.nextLine();
                        if (!validacion.getInt(captura, 0, 6)) {
                            System.out.print("Opcion no valida, seleccione otra:");
                        }
                    } while (!validacion.getInt(captura, 0, 6));

                    opcionCampos = Integer.parseInt(captura);

                    switch (opcionCampos) {
                        case 1:
                            System.out.print("Nombre actual: " + prof.getNombreM() + "Nombre nuevo: ");
                            do {
                                captura = sc.nextLine();
                                if (!validacion.getString(1, 45, captura)) {
                                    System.out.print("El nombre debe contener entre 1 y 45 caracteres");
                                }
                            } while (!validacion.getString(1, 45, captura));
                            prof.setNombreM(captura);
                            metodos.modificarProfesor(prof);
                            break;
                        case 2:
                            System.out.print("Primer apellido actual: " + prof.getApe1M() + " Primer apellido nuevo: ");
                            do {
                                captura = sc.nextLine();
                                if (!validacion.getString(0, 45, captura)) {
                                    System.out.print("El primer apellido debe contener entre 0 y 45 caracteres");
                                }
                            } while (!validacion.getString(0, 45, captura));
                            prof.setApe1M(captura);
                            metodos.modificarProfesor(prof);
                            break;
                        case 3:
                            System.out.print("Segundo apellido actual: " + prof.getApe2M() + " Segundo apellido nuevo: ");
                            do {
                                captura = sc.nextLine();
                                if (!validacion.getString(0, 45, captura)) {
                                    System.out.print("El segundo apellido debe contener entre 0 y 45 caracteres");
                                }
                            } while (!validacion.getString(0, 45, captura));
                            prof.setApe2M(captura);
                            metodos.modificarProfesor(prof);
                            break;
                        case 4:
                            System.out.print("Email actual: " + prof.getEmailProfesorM() + " Email nuevo: ");
                            do {
                                captura = sc.nextLine();
                                if (!validacion.validarEmail(captura)) {
                                    System.out.print("El Email no es correcto");
                                }
                            } while (!validacion.getString(1, 45, captura));
                            prof.setEmailProfesorM(captura);
                            metodos.modificarProfesor(prof);
                            break;
                        case 5:
                            System.out.print("Despacho actual: " + prof.getDespachoM() + " Despacho nuevo: ");
                            do {
                                captura = sc.nextLine();
                                if (!validacion.getString(0, 4, captura)) {
                                    System.out.print("El despacho debe contener entre 0 y 4 caracteres");
                                }
                            } while (!validacion.getString(0, 4, captura));
                            prof.setDespachoM(captura);
                            metodos.modificarProfesor(prof);
                            break;
                        case 6:
                            if (prof.isEstadoM()) {
                                estado = "activo";
                            } else {
                                estado = "baja";
                            }
                            do {
                                System.out.println("Estado actual: " + estado + " Estado nuevo: ");
                                System.out.println(" 1- activo");
                                System.out.println(" 2- baja");
                                System.out.println(" 0- Salir");
                                captura = sc.nextLine();
                                if (!validacion.getInt(captura, 0, 2)) {
                                    System.out.print("Opcion no valida, seleccione otra:");
                                }
                            } while (!validacion.getInt(captura, 0, 2));

                            opcionCampos = Integer.parseInt(captura);

                            if (opcionCampos == 1) {
                                prof.setEstadoM(true);
                            } else if (opcionCampos == 2) {
                                prof.setEstadoM(false);
                            }
                            if (opcionCampos != 0) {
                                tfg.setIdProfesorM(prof.getIdProfesorM());

                                metodos.modificarProfesor(prof);
                            }
                            break;
                        case 0:
                            break;
                    }
                } while (opcionCampos != 0);

            }
        }
    }

    private Profesor mostarProfesores(ArrayList<Profesor> lista) {
        int i = 0, k = -1;
        String aux;
        for (int j = 0; j < 24; j++) {
            System.out.println("");
        }

        System.out.println("Listado de Profesores:");

        do {
            System.out.println(i + 1 + "-   " + lista.get(i).getNombreM());
            i++;
        } while (i < lista.size());
        System.out.println("");
        System.out.println("Selecione un Profesor:");
        do {
            aux = sc.nextLine();
            if (!validacion.getInt(aux, 1, lista.size())) {
                System.out.print("Opcion no valida, seleccione otra:");
            }
        } while (!validacion.getInt(aux, 1, lista.size()));
        k = Integer.parseInt(aux);
        return lista.get(k - 1);
    }

    /*
    Jm. 
    */
    private void cambiarProfesorTfg(int idTfg, int idProfesor) {
        ArrayList<TFG> lista = new ArrayList<TFG>();      
        metodos.obtenerTFGProfesor(lista, idProfesor);
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
