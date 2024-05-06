package Motor;

import java.util.regex.*;


public class Validador {
    public static boolean validarPatron(String dato, String patron){
        Pattern validador = Pattern.compile(patron);
        Matcher matcher = validador.matcher(dato);
        return matcher.matches();
    }
}

//String dni = "[XxYy0-9][0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke]";
