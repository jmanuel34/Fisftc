package fisftc;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacion {

    boolean trazas;
    Scanner sc = new Scanner(System.in);
    private static final String CARACTERES_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Permite comprobar que un String tiene un tamaño mínimo
     *
     * @param min determina el tamaño mínimo del String
     * @return el String
     */
    public boolean getString(int min, String st) {
        boolean resul;

        if (st.length() < min) {
            resul = false;
        } else {
            resul = true;
        }

        return resul;
    }

    /**
     * Permite comprobar que un String tiene un tamaño valido entre un mínimo y
     * un máximo
     *
     * @param min determina el tamaño mínimo del String
     * @param max determina el tamaño máximo del String
     * @return true si tiene tamaño valido y false si no tiene un tamaño valido
     */
    public boolean getString(int min, int max, String st) {
        boolean resul;

        if (st.length() < min) {
            resul = false;
        } else if (st.length() > max) {
            resul = false;
        } else {
            resul = true;
        }
        return resul;
    }

    /**
     * Comprueba que el dato recibido es de tipo entero
     *
     * @param st el dato de entrada de tipo String
     * @return el número entero
     */
    public boolean getInt(String st) {
        int entero = 0;
        boolean resul = false;
        try {
            entero = Integer.parseInt(st);
            resul = true;
        } catch (Exception e) {
        }

        return resul;
    }

    /**
     * Permite comrobar que un entero está entre dos valores.
     *
     * @param min longitud mínima del String
     * @param max longitud máxima del String
     * @return true si el String es de longitud entre min y max
     */
    public boolean getInt(String st, int min, int max) {
        int entero = 0;
        boolean resul = false;
        try {
            entero = Integer.parseInt(st);
            if (entero >= min && entero <= max) {
                resul = true;
            }
        } catch (Exception e) {
        }

        return resul;

    }

    /**
     * Método para validar email. Comprueba que tiene el formato correcto.
     *
     * @param email correo para validar
     * @return true si correo es válido, falso si no lo es
     */
    public static boolean validarEmail(String email) {

        // convierte el correo introducido en un patrón
        Pattern pattern = Pattern.compile(CARACTERES_CORREO);

        // compara el patrón con el correo
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    /**
     * Formato aceptado yyyy-mm-dd
     *
     * @param sFecha
     * @param sMascara
     * @return
     */
    public boolean validarFecha(String sFecha) {

        Calendar c = new GregorianCalendar();
        int dia = c.get(Calendar.DATE);
        int mes = c.get(Calendar.MONTH) + 1;
        int año = c.get(Calendar.YEAR);
        boolean retorno = false;

        StringTokenizer tokens = new StringTokenizer(sFecha, "-");
        int nDatos = tokens.countTokens();
        if (nDatos == 3) {
            int[] datos = new int[nDatos];
            int i = 0;
            while (tokens.hasMoreTokens()) {
                String str = tokens.nextToken();
                datos[i] = Integer.valueOf(str).intValue();
                i++;
            }

            if (datos[0] < 1900) {
                retorno = false;
            } else {
                try {
                    LocalDate.of(datos[0], datos[1], datos[2]);

                    if (datos[0] > año && datos[0] < año + 2) {
                        retorno = true;
                    }
                    if (retorno || datos[0] == año && datos[0] < año + 2 && datos[1] > mes) {
                        retorno = true;
                    }
                    if (retorno || datos[0] == año && datos[0] < año + 2 && datos[1] == mes && datos[2] >= dia) {
                        retorno = true;
                    }

                } catch (DateTimeException e) {
                    retorno = false;
                }
            }

        }

        return retorno;
    }

    public Date obtenerDate(String sFecha) {
        Date date = new Date();

        StringTokenizer tokens = new StringTokenizer(sFecha, "-");
        int nDatos = tokens.countTokens();
        if (nDatos == 3) {
            int[] datos = new int[nDatos];
            int i = 0;
            while (tokens.hasMoreTokens()) {
                String str = tokens.nextToken();
                datos[i] = Integer.valueOf(str).intValue();
                i++;
            }
            date.setYear(datos[0]);
            date.setMonth(datos[1]-1);
            date.setDate(datos[2]);

        }
        return date;
    }
}
