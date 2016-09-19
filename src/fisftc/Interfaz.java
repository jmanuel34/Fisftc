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
    private boolean trazas = false;
    private Scanner sc = new Scanner(System.in);

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
            System.out.println("\tSeleccione una opción: ");

            do {
                captura = sc.nextLine();
                if (!validacion.getInt(captura, 0, 6)) {
                    System.out.print("Opción no válida, seleccione otra: ");
                }
            } while (!validacion.getInt(captura, 0, 6));

            opcionMP = Integer.parseInt(captura);

            switch (opcionMP) {
                case 1:/*MOSTRAR TFG*/
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    ArrayList<TFG> lista;
                    lista = new ArrayList<TFG>();
                    metodos.obtenerTfgDisponible(lista);
                    if (!lista.isEmpty()) {
                        mostarTFGDisponibles(lista);
                    } else {
                        System.out.println("No hay TFGs almacenados.");
                        System.out.println("Pulse ENTER para continuar.");
                        sc.nextLine();
                    }
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    break;
                case 2:
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    this.aniadirTFG();
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    break;
                case 3:
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    this.asignarAlumnoATfg();
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    break;
                case 4:
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    this.mostrarDefensas();
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    break;
                case 5:
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    this.aniadirDefensa();
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    break;
                case 6:
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
                    this.menuOtrasOpciones();
                    for (int i = 0; i < 15; i++) {
                        System.out.println();
                    }
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
        System.out.println("|---------------------------------------------------|");
        System.out.println("|--Aplicación para la Gestíón de Trabajos (T.F.G.)--|");
        System.out.println("|---------------------------------------------------|");

        System.out.println("\t\t\tMENÚ PRINCIPAL\n");

        System.out.println("\t[1] Listar TFG disponibles");
        System.out.println("\t[2] Añadir TFG");
        System.out.println("\t[3] Asignar TFG");
        System.out.println("\t[4] Archivo de defensas");
        System.out.println("\t[5] Añadir defensa");
        System.out.println("\t[6] Otras opciones");
        System.out.println("\n\t[0] SALIR");
    }

    private void asignarAlumnoATfg() {
        ArrayList<TFG> lista;
        lista = new ArrayList<TFG>();
        metodos.obtenerTfgDisponible(lista);
        if (!lista.isEmpty()) {
            int i = 0, k = -1;
            String aux;
            System.out.println("Listado de TFG disponibles:\n");

            do {
                System.out.println("[" + (i + 1) + "]  " + lista.get(i).getTituloM());
                i++;
            } while (i < lista.size());
            System.out.println("\nElige una opción:\n\t[0] VOLVER");
            do {
                aux = sc.nextLine();
                if (!validacion.getInt(aux, 0, lista.size())) {
                    System.out.print("Opción no válida, seleccione otra: ");
                }
            } while (!validacion.getInt(aux, 0, lista.size()));
            k = Integer.parseInt(aux);
            if (k != 0) {
                this.ampliarTFG(lista.get(k - 1));
            }
        } else {
            System.out.println("No hay TFGs disponibles.");
            System.out.println("Pulse ENTER para continuar.");
            sc.nextLine();
        }

    }

    private void ampliarTFG(TFG trabajo) {
        int i = -1;
        String aux;
        mostrarTFG(trabajo, metodos.obtenerProfesor(trabajo));
        System.out.println("\t[1]  Asignar Alumno");
        System.out.println("\t[0]  VOLVER");
        do {
            aux = sc.nextLine();
            if (!validacion.getInt(aux, 0, 1)) {
                System.out.print("Opción no válida, seleccione otra: ");
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
        boolean encontrado;

        metodos.obtenerAlumnosDefendibles(alumnoLista);

        contador = 1;
        Iterator<Alumno> iterAlumno = alumnoLista.iterator();
        System.out.println("Alumnos a los que se le puede asignar defensa:");
        while (iterAlumno.hasNext()) {
            alum = iterAlumno.next();
            tfg = metodos.obtenerTFG(alum);
            System.out.println("[" + contador + "]" + "  Nombre: " + alum.getNombreM() 
                    + "\t Titulo: " + tfg.getTituloM());
            contador++;
        }
        if (contador > 1) {
            System.out.println("Elige una opción:\n\t[0] VOLVER");
            do {
                captura = sc.nextLine();
                if (!validacion.getInt(captura, 0, contador - 1)) {
                    System.out.println("Opción no válida, seleccione otra:");
                }
            } while (!validacion.getInt(captura, 0, contador - 1));

            opcionAlumno = Integer.parseInt(captura);

            if (opcionAlumno != 0) {

                alum = alumnoLista.get(opcionAlumno - 1);

                metodos.obtenerConvocatoria(convLista);

                System.out.println("Convocatorias que se le puede asignar defensa:");

                contador = 2;
                iterConvocatoria = convLista.iterator();
                System.out.println("[1] Crear nueva convocatoria.");
                while (iterConvocatoria.hasNext()) {
                    conv = iterConvocatoria.next();
                    System.out.println("[" + contador + "]" + "  Año: " + conv.getAnioM()
                            + " Mes: " + conv.getMesM()
                            + " Tipo: " + conv.getTipoM());
                    contador++;
                }
                do {
                    encontrado = false;
                    captura = sc.nextLine();
                    if (!validacion.getInt(captura, 0, contador - 1)) {
                        System.out.println("Opción no válida, seleccione otra:");
                    } else {
                        opcionConv = Integer.parseInt(captura);
                        if (opcionConv > 1) {
                            conv = convLista.get(opcionConv - 2);
                            encontrado = metodos.validarDefensa(alum, conv);
                            if (encontrado) {
                                System.out.println("EL alumno ya contiene una defensa en esa convocatoria."
                                        + "\n\t[1] Crear nueva convocatoria."
                                        + "\n\t[0] MENÚ PRINCIPAL");
                            }
                        }

                    }
                } while (!validacion.getInt(captura, 0, contador - 1) || encontrado);

                opcionConv = Integer.parseInt(captura);

                if (contador > 2 && opcionConv >= 2) {

                    conv = convLista.get(opcionConv - 2);

                    def.setIdAlumnoM(alum.getIdAlumnoM());
                    def.setIdTfgM(alum.getIdtfgM());
                    def.setIdConvocatoriaM(conv.getidConvocatoriaM());

                    System.out.println("Introduce el día");

                    do {
                        captura = sc.nextLine();
                        captura = conv.getAnioM() + "-" + conv.getMesM() + "-" + captura;
                        if (!validacion.validarFecha(captura)) {
                            System.out.println("Día no válido.");
                        }
                    } while (!validacion.validarFecha(captura));

                    def.setFechaDefensaM(captura);

                    System.out.println("Introduzca nota:");
                    do {
                        captura = sc.nextLine();
                        if (!validacion.getFloat(captura, 0, 10)) {
                            System.out.println("Opción no válida, seleccione otra:");
                        }
                    } while (!validacion.getFloat(captura, 0, 10));

                    def.setNotaM(Float.parseFloat(captura));

                    if (metodos.aniadirDefensa(def)) {
                        if (Float.parseFloat(captura) >= 5) {
                            tfg = metodos.obtenerTFG(alum);
                            tfg.setFinalizadoM(true);
                            metodos.modificarTFG(tfg);
                            System.out.println("Defensa guardada.");
                            System.out.println("Pulse ENTER para continuar.");
                            sc.nextLine();
                        }
                    }

                } else if (opcionConv != 0) {

                    if (Integer.parseInt(captura) == 1) {
                        conv = this.crearConvocatoria();
                        if (conv.getidConvocatoriaM() != 0) {
                            def.setIdAlumnoM(alum.getIdAlumnoM());
                            def.setIdTfgM(alum.getIdtfgM());
                            def.setIdConvocatoriaM(conv.getidConvocatoriaM());
                            System.out.println("Introduce el día");

                            do {
                                captura = sc.nextLine();
                                captura = conv.getAnioM() + "-" + conv.getMesM() + "-" + captura;
                                if (!validacion.validarFecha(captura)) {
                                    System.out.println("Día no válido.");
                                }
                            } while (!validacion.validarFecha(captura));

                            def.setFechaDefensaM(captura);

                            System.out.println("Introduzca nota:");
                            do {
                                captura = sc.nextLine();
                                if (!validacion.getFloat(captura, 0, 10)) {
                                    System.out.println("Opción no válida, seleccione otra:");
                                }
                            } while (!validacion.getFloat(captura, 0, 10));

                            def.setNotaM(Float.parseFloat(captura));

                            if (metodos.aniadirDefensa(def)) {
                                if (Float.parseFloat(captura) >= 5) {
                                    tfg = metodos.obtenerTFG(alum);
                                    tfg.setFinalizadoM(true);
                                    metodos.modificarTFG(tfg);
                                    System.out.println("Defensa guardada.");
                                    System.out.println("Pulse ENTER para continuar.");
                                    sc.nextLine();
                                }

                            }

                        }
                    }

                }

            }
        } else {
            System.out.println("No hay alumnos a los que asignar defensa.");
        }

    }

    private Convocatoria crearConvocatoria() {
        String captura;
        int opcion, id = 0;
        int[] datos = null;
        int i = 0;
        Convocatoria conv = new Convocatoria();
        System.out.println("Introduce una fecha(yyyy-mm): ");
        do {
            captura = sc.nextLine();
            captura = captura + "-01";
            if (!validacion.validarFecha(captura)) {
                System.out.println("Fecha no válida.");
                System.out.println("Revise el formato (yyyy-mm) y que\nla fecha sea"
                        + " anterior a la actual.");
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

        System.out.println("De carácter: \n\t[1]Ordinario \n\t[2]Extraordinario");
        do {
            captura = sc.nextLine();
            if (!validacion.getInt(captura, 0, 2)) {
                System.out.println("Opción no válida, seleccione otra:");
            }
        } while (!validacion.getInt(captura, 0, 2));

        opcion = Integer.parseInt(captura);
        if (opcion == 1) {
            conv.setTipoM("Ordinaria");
        } else if (opcion == 2) {
            conv.setTipoM("Extraordinaria");
        }

        if (opcion != 0) {
            conv.setidConvocatoriaM(metodos.aniadirConvocatoria(conv));
        }
        return conv;
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

        System.out.println("Años de defensa disponibles");

        if (!aniosConv.isEmpty()) {

            contador = 1;

            iterAniosConv = aniosConv.iterator();

            while (iterAniosConv.hasNext()) {
                System.out.println("[" + contador + "]" + "  " + iterAniosConv.next());
                contador++;
            }

            System.out.println("Marca el que desees: ");
            System.out.println("\t[0] SALIR");

            do {
                captura = sc.nextLine();
                if (!validacion.getInt(captura, 0, contador-1)) {
                    System.out.print("Opción no válida, seleccione otra:");
                }
            } while (!validacion.getInt(captura, 0, contador-1));

            opcionAnio = Integer.parseInt(captura);

        } else {
            System.out.println("No hay defensas disponibles.");
            System.out.println("Pulse ENTER para continuar.");
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

                System.out.println("Marca el que desees: ");
                System.out.println("\t[0] SALIR");

                do {
                    captura = sc.nextLine();
                    if (!validacion.getInt(captura, 0, contador - 1)) {
                        System.out.print("Opción no válida, seleccione otra:");
                    }
                } while (!validacion.getInt(captura, 0, contador - 1));

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

                                System.out.println("TÍTULO: " + tfg.getTituloM()
                                        + "  ALUMNO: " + alumno.getNombreM()
                                        + "\n  AÑO: " + conv.getAnioM()
                                        + "  MES: " + conv.getMesM()
                                        + "\n  TIPO: " + conv.getTipoM()
                                        + "  NOTA: " + def.getNotaM());
                            }
                        }
                    }
                    System.out.println("Pulse ENTER para continuar.");
                    sc.nextLine();
                }
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
        System.out.println("Listado de TFG disponibles:\n");

        do {
            System.out.println("[" + (i + 1) + "] " + lista.get(i).getTituloM());
            i++;
        } while (i < lista.size());
        System.out.println("\nSeleccione un trabajo o [0] para SALIR: ");
        do {
            aux = sc.nextLine();
            if (!validacion.getInt(aux, 0, lista.size())) {
                System.out.print("Opción no válida, seleccione otra: ");
            }
        } while (!validacion.getInt(aux, 0, lista.size()));
        k = Integer.parseInt(aux);
        if (k != 0) {
            mostrarTFG(lista.get(k - 1), metodos.obtenerProfesor(lista.get(k - 1)));
        }

        System.out.println("Pulse ENTER para continuar.");
        sc.nextLine();

    }

    private void asignarAlumno(TFG trabajo) {
        String aux;
        int idAlumno;
        Alumno alum = new Alumno();
        System.out.println("Introduzca el nombre del alumno: ");
        aux = sc.nextLine();
        alum.setNombreM(aux);
        System.out.println("Introduzca el primer apellido del alumno: ");
        aux = sc.nextLine();
        alum.setApe1M(aux);
        System.out.println("Introduzca el segundo apellido del alumno: ");
        aux = sc.nextLine();
        alum.setApe2M(aux);
        System.out.println("Introduzca el email del alumno: ");
        do {
            aux = sc.nextLine();
            if (!validacion.validarEmail(aux)) {
                System.out.print("Opción no válida, seleccione otra:");
            }
        } while (!validacion.validarEmail(aux));

        alum.setEmailAlumnoM(aux);
        System.out.println("Introduzca el número de matrícula del alumno: ");
        do {
            aux = sc.nextLine();
            if (!validacion.getString(6, 6, aux)) {
                System.out.print("Opcion no valida. Debe tener 6 caracteres: ");
            }
        } while (!validacion.getString(6, 6, aux));

        alum.setNumMatM(aux);
        alum.setIdtfgM(trabajo.getIdTfgM());
        idAlumno = metodos.aniadirAlumno(alum);
        alum.setIdAlumnoM(idAlumno);
        alum.setIdtfgM(trabajo.getIdTfgM());

        metodos.modificarAlumno(alum);
    }

    /**
     * funcion que muestra por pantalla la info de un trabajo.
     *
     * @param trabajo el trabajo que se quiere mostrar
     * @param profe el profesor que lleva el trabajo
     */
    private void mostrarTFG(TFG trabajo, Profesor profe) {
        String aux;

        System.out.println("\nDETALLES DEL PROYECTO:\n");
        System.out.println("TÍTULO: " + trabajo.getTituloM());
        System.out.println("DESCRIPCIÓN: " + trabajo.getDescripcionM());
        System.out.println("TUTOR: " + profe.getNombreM());
    }

    /**
     * ***********************Aniadir TFG************************************
     */
    private void aniadirTFG() {
        String aux;
        int idProfesor;
        int j = -1;
        boolean continuar = false;
        boolean salir = false;
        TFG trab = new TFG();

        System.out.println("TÍTULO: ");
        do {
            aux = sc.nextLine();
            if (!validacion.getString(1, 45, aux)) {
                System.out.println("Título no válido, introduzca otro: ");
            }
        } while (!validacion.getString(1, 45, aux));
        trab.setTituloM(aux);

        System.out.println("DESCRIPCIÓN: ");
        do {
            aux = sc.nextLine();
            if (!validacion.getString(0, 300, aux)) {
                System.out.println("Descripción no válida, introduzca otro: ");
            }
        } while (!validacion.getString(0, 300, aux));
        trab.setDescripcionM(aux);

        do {
            System.out.println("PROFESOR:\n");
            System.out.println("\t[1] Profesor existente");
            System.out.println("\t[2] Nuevo profesor");
            System.out.println("\t[0] SALIR");

            do {
                aux = sc.nextLine();
                if (!validacion.getInt(aux, 0, 2)) {
                    System.out.println("Opción no válida, seleccione otra:");
                }
            } while (!validacion.getInt(aux, 0, 2));
            j = Integer.parseInt(aux);
            if (j == 1) {
                ArrayList<Profesor> profesores = new ArrayList<Profesor>();
                metodos.obtenerProfesores(profesores);
                if (!profesores.isEmpty()) {
                    Profesor profe = this.mostarProfesores(profesores);
                    trab.setIdProfesorM(profe.getIdProfesorM());
                    continuar = true;
                } else {
                    System.out.println("No hay profesores almacenados.");
                }
            } else if (j == 2) {
                idProfesor = aniadirProfesor();
                trab.setIdProfesorM(idProfesor);
                continuar = true;
            } else if (j == 0) {
                continuar = true;
                salir = true;
            }
        } while (!continuar);

        if (!salir) {
            trab.setIdTfgM(metodos.aniadirTfg(trab));

            System.out.println("¿Deseas asignar un alumno?");
            System.out.println("\t[1] Sí");
            System.out.println("\t[0] No");

            do {
                aux = sc.nextLine();
                if (!validacion.getInt(aux, 0, 1)) {
                    System.out.println("Opción no válida, seleccione otra:");
                }
            } while (!validacion.getInt(aux, 0, 1));
            j = Integer.parseInt(aux);
            if (j == 1) {
                asignarAlumno(trab);
            }
        }

    }

    /**
     * Función que implementa la funcionalidad del menu otras opciones
     * apoyandose en la clase Metodos.
     */
    private void menuOtrasOpciones() {
        int opcionOO = -1;
        String captura;
        do {

            mostrarOO();
            System.out.println("\n\tSeleccione una opción:");
            do {
                captura = sc.nextLine();
                if (!validacion.getInt(captura, 0, 7)) {
                    System.out.print("Opción no válida, seleccione otra:");
                }
            } while (!validacion.getInt(captura, 0, 7));
            opcionOO = Integer.parseInt(captura);
            switch (opcionOO) {
                case 1:
                    this.modificarTFG();
                    break;
                case 2:
                    System.out.println("En desarrolo...");
                    break;
                case 3:
                    System.out.println("En desarrolo...");
                    break;
                case 4:
                    System.out.println("En desarrolo...");
                    break;
                case 5:
                    this.modificarProfesor();
                    break;
                case 6:
                    this.borrarProfesor();
                    break;
                case 7:
                    System.out.println("En desarrolo...");
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
        System.out.println("|--------------------------------------------------|");
        System.out.println("|-          	Otras Opciones                -|");
        System.out.println("|--------------------------------------------------|");

        System.out.println("\t[1] Modificar TFG");
        System.out.println("\t[2] Borrar un TFG");
        System.out.println("\t[3] Modificar convocatoria");
        System.out.println("\t[4] Dar de baja una convocatoria");
        System.out.println("\t[5] Modificar un profesor");
        System.out.println("\t[6] Dar de baja un profesor");
        System.out.println("\t[7] Modificar un alumno");
        System.out.println("\n\t[0] MENÚ PRINCIPAL");
    }

    private int aniadirProfesor() {
        String aux;
        Profesor prof = new Profesor();
        System.out.println("NOMBRE: ");
        aux = sc.nextLine();
        prof.setNombreM(aux);
        System.out.println("PRIMER APELLIDO: ");
        aux = sc.nextLine();
        prof.setApe1M(aux);
        System.out.println("SEGUNDO APELLIDO: ");
        aux = sc.nextLine();
        prof.setApe2M(aux);
        System.out.println("EMAIL: ");
        do {
            aux = sc.nextLine();
            if (!validacion.validarEmail(aux)) {
                System.out.println("Opción no válida, seleccione otra:");
            }
        } while (!validacion.validarEmail(aux));
        prof.setEmailProfesorM(aux);
        System.out.println("DESPACHO: ");
        aux = sc.nextLine();
        prof.setDespachoM(aux);
         prof.setEstadoM(true);
        return metodos.aniadirProfesor(prof);
    }

    private Profesor mostarProfesores(ArrayList<Profesor> lista) {
        int i = 0, k = -1;
        String aux;
        System.out.println("Listado de profesores:");

        do {
            System.out.println("[" + (i + 1) + "] " + lista.get(i).getNombreM());
            i++;
        } while (i < lista.size());
        System.out.println("\nSeleccione un profesor:");
        do {
            aux = sc.nextLine();
            if (!validacion.getInt(aux, 1, lista.size())) {
                System.out.print("Opción no válida, seleccione otra:");
            }
        } while (!validacion.getInt(aux, 1, lista.size()));
        k = Integer.parseInt(aux);
        return lista.get(k - 1);
    }

    private void modificarProfesor() {
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

    private void modificarTFG() {
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
                            /*  Jm: Modificados los print por println
                            
                            */
                            System.out.println("Profesor actual: " + prof.getNombreM());
                            System.out.println("Reasignar: ");
                            System.out.println("1- Profesor existente");
                            System.out.println("2- Nuevo profesor");
                            System.out.println("0- Salir");

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
                                if (!validacion.getString(3, 45, captura)) {
                                    System.out.print("El titulo debe contener al menos 3 caracteres");
                                }
                            } while (!validacion.getString(3, 45, captura));

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
    
/* Jm    
*/
    private void borrarProfesor() {
        String aux;
        int opcion = -1;
        boolean result;
        Iterator<TFG> iteradorTfg;
        ArrayList<TFG> tfgList = new ArrayList<TFG>();
        ArrayList<Profesor> listaProfesores = new ArrayList<Profesor>();
//        System.out.println("Borrando profesor ");
        metodos.obtenerProfesores(listaProfesores);                                         // Obtiene lista profesores         
        if (!listaProfesores.isEmpty()) {
            Profesor profesor = this.mostarProfesores(listaProfesores);                     // Muestra lista de profesores y pone en profesor el elegido       
            metodos.obtenerTFGProfesor(tfgList, profesor);                                  // Obtiene los TFGs del profesor elegido
 //           iteradorTfg = tfgList.iterator();

                if (!tfgList.isEmpty()) {                                                   // profesor tiene TFGs asignados                                                                              
                    opcion = this.menuCambioTfg();                                          // Ofrece opciones al usuario
                    while (opcion != 0 && !tfgList.isEmpty()) {                               // Presenta los TFGs restantes del profesor
   
                        if (opcion == 3) {
                            result = metodos.borrarTodosTfg(profesor);                          // Opcion Rapida de menu(3). Borra los TFGs restantes
                            opcion = 0;                                                         //  Salir del bucle
                        } 
                        else if (opcion == 1) {                                                 // Borramos el Tfg elegido
                            metodos.borrarTfg(tfgList.get(0).getIdTfgM());
                        }
                        else {
                            TFG tfg = metodos.obtenerTFG (tfgList.get(0).getIdTfgM());          // Cambiamos de profesor al Tfg
                            System.out.println ("El trabajo de titulo: " +tfg.getTituloM());
                            System.out.println(" Esta asignado a: " + profesor.getEmailProfesorM());
                            cambiarProfesorATfg(tfg, profesor);                             // Cambia el profesor titular del TFG
                        }
                        opcion = this.menuCambioTfg();
                        metodos.obtenerTFGProfesor(tfgList, profesor);
                    }
                }
            else {
                metodos.obtenerTFGProfesor(tfgList, profesor);
                if (tfgList.isEmpty()) {                                                     // Profesor NO tiene trabajos asignados
                    profesor.setEstadoM(false);                                                 // Lo damos de baja
                    result = metodos.modificarProfesor(profesor);
                    System.out.println("Profesor con email: " + profesor.getEmailProfesorM() + " dado de baja");
                }
            }
                

                //result = metodos.borrarProfesor(profesor);                              // borramos profesor sin tfgs
                
            } else { 
                System.out.println ("No hay profesores ");
            //      } else if (trazas) {
            //System.out.println("OJO" + profesor.getIdProfesorM());
            }   
        }
    
     private int menuCambioTfg() {
        String captura;
        int opcion;
        for (int i = 0; i < 24; i++) {
            System.out.println("");
        }

        System.out.println("|--------------------------------------------------|");
        System.out.println("|-- Aplicación para la Gestíón de Trabajos T.F.G --|");
        System.out.println("|--------------------------------------------------|");
        System.out.println("			 MENU CAMBIO TFG");
        System.out.println("    1.- Borrar TFG del profesor");
        System.out.println("    2.- Reasignar Profesor de TFG ");
        System.out.println("    3.- Borrar Todos los TFG de profesor ");
        
        System.out.println("    0.- SALIR");
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
        return opcion;
    }
    
    private void cambiarProfesorATfg(TFG tfg, Profesor prof) {
        String captura;
        int opcionCampos;
        int contador;
        ArrayList<Profesor> profesorLista = new ArrayList<Profesor>();
        System.out.println("Profesor actual: " + prof.getNombreM());
        System.out.println("Reasignar: ");
        System.out.println("1- Profesor existente");
        System.out.println("2- Nuevo profesor");
        System.out.println();
        System.out.println("0- Salir");
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
                    
                    contador++;                                                         // Jm He tenido que añadir esto
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
    }
    
    
}
