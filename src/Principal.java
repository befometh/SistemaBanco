import Motor.Banco;

import java.util.*;

import static Motor.Validador.validarPatron;

public class Principal {
    public static void main(String[] args) {


        Scanner sn;
        Banco banco = new Banco();
        int opcion;
        boolean salir = false;

        while (!salir) {
            sn = new Scanner(System.in);
            sn.useDelimiter("\n");
            sn.useLocale(Locale.US);
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
                        boolean esEmpresa = false;
                        int seleccion;
                        String[][] propietarios = null;
                        int tipoCuenta = 1;
                        double saldoInicial = 0;
                        System.out.println("Ha elegido crear nueva cuenta.");
                        seleccion = pedirOpcion("""
                                --------------------------------------------------
                                ¿Pertenece a una persona particular o una empresa?
                                1. Empresa
                                2. Particular
                                """);
                        switch (seleccion) {
                            case 1:
                                esEmpresa = true;
                                break;
                            case 2:
                                esEmpresa = false;
                                break;
                            default:
                                seleccion = -1;
                        }
                        if (seleccion != -1) {
                            if (!esEmpresa) {
                                seleccion = pedirOpcion("""
                                        Escoja el tipo de cuenta que quiere crear:
                                        1. Corriente
                                        2. Ahorros
                                        """);
                                switch (seleccion) {
                                    case 1:
                                        tipoCuenta = 1;
                                        break;
                                    case 2:
                                        tipoCuenta = 2;
                                        break;
                                    default:
                                        seleccion = -1;
                                }
                            }
                        }
                        if (seleccion != -1) {
                            if (tipoCuenta == 1 && esEmpresa) {
                                seleccion = pedirOpcion("Por favor ingrese el número de personas que componen la titularidad de su empresa");
                                propietarios = (ingresarPropietarios(seleccion));
                            } else {
                                propietarios = (ingresarPropietarios(1));
                            }
                            saldoInicial = pedirDoble("La cantidad inicial a ingresar: ");
                        }
                        if (seleccion != -1)
                            banco.abrirCuenta(propietarios, tipoCuenta, saldoInicial, esEmpresa);
                        else System.out.println("No se pudo crear la cuenta, Se ha producido un error");
                        break;

                    case 2:
                        if (banco.getNumCuentas() == 0) System.out.println("Aún no ha ingresado ningún valor");
                        else {
                            System.out.println(banco.listarCuentas());
                        }
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

    public static double pedirDoble(String nomDato) {
        Scanner teclado;
        double opcion;
        try {
            teclado = new Scanner(System.in);
            System.out.println(nomDato);
            opcion = teclado.nextDouble();
        } catch (Exception e) {
            System.err.println("El dato que ha ingresado no es válido, vuelva a intentarlo.");
            return -1;
        }
        return opcion;
    }

    public static String pedirDato(String nomDato) {
        Scanner teclado;
        teclado = new Scanner(System.in);
        System.out.println(nomDato);
        return teclado.nextLine();
    }

    public static String pedirDato(String nomDato, String patron) {
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
                System.err.println("El dato ingresado no tiene el formato esperado, recuerde el formato DNI 12345678Z o X2345678Z");
        } while (error);
        return dato;
    }

    public static String[][] ingresarPropietarios(int num) {
        String[][] propietarios = new String[num][3];
        int contador = 1;
        for (String[] propietario : propietarios) {
            propietario[0] = pedirDato(contador + ". Nombres: ");
            propietario[1] = pedirDato("Apellidos: ");
            propietario[2] = pedirDato("DNI: ", "[XxYy0-9][0-9]{7}[A-Za-z]");
            contador++;
        }
        return propietarios;
    }
}