import java.util.*;

public class Principal {
    public static void main(String[] args) {


        Scanner sn;

        int opcion;
        boolean salir = false;

        while (!salir) {
            sn = new Scanner(System.in);
            sn.useDelimiter("\n");
            sn.useLocale(Locale.US);
            System.out.println("1. Abrir una nueva cuenta.");
            System.out.println("""
                    1. Abrir una nueva cuenta.
                    2. Ver un listado de las cuentas disponibles
                    3. Obtener los datos de una cuenta concreta.
                    4. Realizar un ingreso en una cuenta.
                    5. Retirar efectivo de una cuenta
                    6. Consultar el saldo actual de una cuenta.
                    7. Salir de la aplicación.
                    \n
                    Ingrese una opción:""");

            try {
                opcion = sn.nextInt();
                switch (opcion) {

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
                        salir = true;
                        break;
                }
            } catch (Exception e) {
                System.out.println("El valor que se ha ingresado es inválido");
            }
        }
    }//Fin del main
}