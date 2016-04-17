package fisftc;

import java.util.Scanner;
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
}
