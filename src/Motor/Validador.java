/**
 * @author Cristyan Morales Acevedo
 * @desc Clase validadora de datos, confirma que los datos se implementen correctamente.
 */
package Motor;

import java.util.Scanner;
import java.util.regex.*;

public class Validador {
    /**
     * Seleccionador de opciones, verifica que la opción que ingresa el usuario sea válida
     * @param nomDato nombre del dato a ingresar, lo que se enseña en pantalla al llamar el método.
     * @return dato ya verificado, si falla retorna -1
     */
    public static int pedirOpcion(String nomDato) {
        Scanner teclado;
        int opcion;
        try {
            teclado = new Scanner(System.in);
            System.out.println(nomDato);
            opcion = teclado.nextInt();
        } catch (Exception e) {
            System.out.println("El dato que ha ingresado no es válido, vuelva a intentarlo.");
            return -1;
        }
        return opcion;
    }

    /**
     * Registro de números en formato double, permite que el usuario ingrese valores con "," o "." dependiendo si es necesario y confirma que el valor sea válido
     * @param nomDato nombre del dato a ingresar, lo que se enseña en pantalla al llamar el método.
     * @return dato ya verificado, si falla retorna -1
     */
    public static double pedirDoble(String nomDato) {
        Scanner teclado;
        String opcion;
        try {
            teclado = new Scanner(System.in);
            System.out.println(nomDato);
            opcion = teclado.nextLine();
            opcion = opcion.replace(",","."); //Reemplaza en la cadena de caracteres la "," por un "." para evitar errores
            return Double.parseDouble(opcion);
        } catch (Exception e) {
            System.err.println("El dato que ha ingresado no es válido, vuelva a intentarlo.");
            return -1;
        }
    }

    /**
     * Registro de números en formato int
     * @param nomDato nombre del dato a ingresar, lo que se enseña en pantalla al llamar el método.
     * @return dato ya verificado, si falla retorna -1
     */
    public static int pedirInt(String nomDato) {
        Scanner teclado;
        int opcion;
        try {
            teclado = new Scanner(System.in);
            System.out.println(nomDato);
            opcion = teclado.nextInt();
            return opcion;
        } catch (Exception e) {
            System.err.println("El dato que ha ingresado no es válido, vuelva a intentarlo.");
            return -1;
        }
    }

    /**
     * Pide los datos del usuario
     * @param nomDato recibe el nombre del dato, lo que se enseña en pontalla
     * @return devuelve el dato recibido
     */
    public static String pedirDato(String nomDato) {
        Scanner teclado;
        teclado = new Scanner(System.in);
        System.out.println(nomDato);
        return teclado.nextLine();
    }

    /**
     * Sobrecarga del método anterior, incluye funciones de validación
     * @param nomDato recibe el nombre del dato, lo que se enseña en pantalla
     * @param patron patron a cumplir para que el dato sea válido, en formato Regex
     * @param msgerr mensaje de error si el dato no cumple las características acordadas
     * @return el valor ya validado
     */
    public static String pedirDato(String nomDato, String patron, String msgerr) {
        Scanner teclado;
        String dato;
        boolean error = true;
        do {
            teclado = new Scanner(System.in);
            System.out.println(nomDato);
            dato = teclado.nextLine();
            if (validarPatron(dato, patron))
                error = false;
            else
                System.err.println(msgerr);
        } while (error); //El ciclo se ejecuta de manera constante hasta que se cumpla las características necesarias para continuar
        return dato;
    }

    /**
     * Recibe el número de propietarios y verifica que se cumpla su escritura
     * @param num total de propietarios que lleva la cuenta bancaria en proceso de creación
     * @return la lista de propietarios ya formateada, tal como lo necesita el programa.
     */
    public static String[][] ingresarPropietarios(int num) {
        String[][] propietarios = new String[num][3];
        int contador = 1; //Enseña el número del propietario actual, su trabajo es enteramente estético, no altera al funcionamiento del programa
        for (String[] propietario : propietarios) {
            propietario[0] = pedirDato(contador + ". Nombres: ", "[A-Za-zÁÉÍÓÚáéíóúÑñ ]+","Asegurese que está ingresando un valor correcto y vuelva a intentarlo");
            propietario[1] = pedirDato("Apellidos: ","[A-Za-zÁÉÍÓÚáéíóúÑñ ]+","Asegurese que está ingresando un valor correcto y vuelva a intentarlo");
            propietario[2] = pedirDato("DNI: ", "[XxYy0-9][0-9]{7}[A-Za-z]","El dato ingresado no tiene el formato esperado, recuerde el formato DNI 12345678Z o X2345678Z");
            contador++;
        }
        return propietarios;
    }

    /**
     * Método base que se encarga de comprobar los patrones, revisa que los Regex se cumplan correctamente
     * @param dato dato que requiere validación
     * @param patron patrón en formato Regex con el cual se realizará la validación
     * @return verdadero si es válido, falso si no
     */
    public static boolean validarPatron(String dato, String patron){
        Pattern validador = Pattern.compile(patron);
        Matcher matcher = validador.matcher(dato);
        return matcher.matches();
    }


}//Fin de clase