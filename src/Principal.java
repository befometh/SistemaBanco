/**
 * @author Cristyan Morales Acevedo
 * @desc Clase principal: Interfaz de enlace con usuario y algunos validadores.
 */

import Motor.Banco;

import java.util.*;

import static Motor.Validador.*;

public class Principal {
    public static void main(String[] args) {

        Scanner sn;
        Banco banco = new Banco();
        int opcion;
        boolean salir = false;

        while (!salir) {
            sn = new Scanner(System.in); //Variable que recibe las opciones del usuario, y funciona como escucha pincipal
            sn.useDelimiter("\n"); //Indica al Scanner sn donde debe detenerse para dejar de recibir datos
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
                boolean error = false; //Booleano que va a auditar el programa constantemente, validando si hay errores de ingreso, si hay error, no se continúa más esta sección y se arroja error
                switch (opcion) {
                    case 1:
                        boolean esEmpresa = false; //Inicialización de la variable esEmpresa, quien va a ser verdadera si lo es y falsa si no lo es, más adelante
                        int seleccion; //Selección del usuario
                        String[][] propietarios = null; //Arreglo que contiene los propietarios de la cuenta, en caso de requerirse varios, de lo contrario será una única persona
                        int tipoCuenta = 1; //Valor base de la cuenta, inicializado en "Corriente" (opción 1)
                        double saldoInicial = 0; //Almacén del saldo con el que empieza la cuenta
                        System.out.println("Ha elegido crear nueva cuenta.");
                        separador();
                        seleccion = pedirOpcion("""
                                ¿Pertenece a una persona particular o una empresa?
                                1. Empresa
                                2. Particular
                                """);
                        switch (seleccion) {
                            case 1:
                                esEmpresa = true; //Se confirma que es una nueva cuenta para empresa
                                break;
                            case 2:
                                break;
                            default:
                                seleccion = -1; //De ahora en adelante, si selección toma valor -1 denota error
                        }
                        if(seleccion == -1)
                            error = true;
                        if (!error) {
                            if (!esEmpresa) {
                                seleccion = pedirOpcion("""
                                        Escoja el tipo de cuenta que quiere crear:
                                        1. Corriente
                                        2. Ahorros
                                        """);
                                switch (seleccion) {
                                    case 1:
                                        break;
                                    case 2:
                                        tipoCuenta = 2;
                                        break;
                                    default:
                                        seleccion = -1;
                                }
                                if(seleccion == -1)
                                    error = true;
                            }
                        }
                        if (!error) {
                            if (tipoCuenta == 1 && esEmpresa) {//Las empresas sólamente pueden tener cuentas corrientes, por ende estas dos condiciones deben ser verdaderas
                                seleccion = pedirOpcion("Por favor ingrese el número de personas que componen la titularidad de su empresa"); //Se solicita el número de propietarios de la empresa, y ese valor se indica para crear el arreglo que va a llevar sus datos personales
                                propietarios = (ingresarPropietarios(seleccion));
                            } else {
                                propietarios = (ingresarPropietarios(1)); //En el caso de no ser empresa, se da por hecho que solo hay un propietario como persona natural, aun siendo una cuenta corriente
                            }
                            saldoInicial = pedirDoble("La cantidad inicial a ingresar: "); //Se asigna la cantidad inicial, no debe ser un número negativo
                            if (saldoInicial < 0)
                                error = true;
                        }
                        if (!error) {
                            String iban = banco.abrirCuenta(propietarios, tipoCuenta, saldoInicial, esEmpresa); //Se crea la cuenta, abrirCuenta() de Banco devuelve el número de IBAN de la cuenta ya creada, para ser listada con facilidad.
                            System.out.println("Se ha creado la cuenta con éxito, cuenta creada: ");
                            cabeceroTabla();
                            System.out.println(banco.buscarCuenta(iban)); //Se muestra la información de la cuenta recién creada
                        }
                        else {
                            separador();
                            System.out.println("No se pudo crear la cuenta, Se ha producido un error"); //Arroja error si se presenta una falta tipográfica durante el programa
                        }
                        separador();
                        break; //Fin case 1

                    case 2:
                        if (banco.listaVacia()) System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para continuar"); //Este error se presenta constantemente en el código, se manifiesta si aún no se ha creado ninguna cuenta
                        else {
                            cabeceroTabla();
                            System.out.println(banco.listarCuentas()); //Se solicita la lista de cuentas total
                        }
                        separador();
                        break; //Fin case 2

                    case 3:
                        if(banco.listaVacia()) System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para continuar");
                        else{
                            String dato;
                            dato = pedirDato("Por favor ingrese el IBAN de la cuenta a buscar:");
                            if(validarPatron(dato,"[Ee][Ss][0-9]{20}")){ //se usa el método estático de Validadores validarPatron para verificar que el iban cumpla con las características
                                dato = dato.toUpperCase(); //Se asegura que la forma del IBAN tenga caracteres en mayúscula
                                String msg = banco.buscarCuenta(dato); //Se invoca al método de banco mostrarCuenta(), si falla arroja un String vacio ("")
                                if(msg.isEmpty()){
                                    System.out.println("No se ha encontrado el dato"); //Error si no se encuentra el dato
                                }
                                else{
                                    cabeceroTabla();
                                    System.out.println(msg); //Se enseña el dato que solicita el usuario
                                }
                            }
                            else System.out.println("El dato que ha ingresado no tiene el formato de un IBAN, vuelva a intentarlo.");
                        }
                        separador();
                        break; //Fin case 3
                    case 4:
                        if(banco.listaVacia()) System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para continuar");
                        else{
                            String dato;
                            dato = pedirDato("Por favor ingrese el IBAN de la cuenta a buscar:");
                            if(validarPatron(dato,"[Ee][Ss][0-9]{20}")){
                                dato = dato.toUpperCase();
                                double ingreso = pedirDoble("Ingrese el monto que desea ingresar"); //Se hace el ingreso del monto a sumar en la cuenta
                                error = banco.ingresoBancario(dato,ingreso); // se solicita la operación, tiene un booleano de nombre error que variará en función de si se pudo cumplir o no la operación
                                if(!error) System.out.println("No se ha encontrado el dato"); //Error si no se encuentra el IBAN
                                else System.out.println("Se ha realizado el ingreso con éxito. nuevo Saldo: "+ banco.obtenerSaldo(dato)); //Se muestra si se ingresa un monto con éxito, también enseña el saldo actual llamando a obtenerSaldo() de banco
                            }
                        }
                        separador();
                        break;//Fin case 4

                    //A efectos prácticos retiro e ingreso se comportan de manera similar
                    case 5:
                        if(banco.listaVacia()) System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para continuar");
                        else{
                            String dato;
                            dato = pedirDato("Por favor ingrese el IBAN de la cuenta a buscar:");
                            if(validarPatron(dato,"[Ee][Ss][0-9]{20}")){
                                dato = dato.toUpperCase();
                                double retiro = pedirDoble("Ingrese el monto que desea retirar");
                                error = banco.retiroBancario(dato,retiro);
                                if(!error) {
                                    if(banco.obtenerSaldo(dato) < retiro) //Confirma si el saldo es menor del que se trata de retirar
                                        System.out.println("Su cuenta tiene saldo insuficiente");
                                    else
                                        System.out.println("No se ha encontrado el dato");
                                }
                                else System.out.println("Su retiro se ha completado con éxito, nuevo Saldo: "+ banco.obtenerSaldo(dato));
                            }
                        }
                        separador();
                        break; //Fin case 5
                    //Igual que con retiro e ingreso mostrar el saldo se comportan de manera muy similar
                    case 6:
                        if(banco.listaVacia()) System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para continuar");
                        else{
                            String dato;
                            dato = pedirDato("Por favor ingrese el IBAN de la cuenta a buscar:");
                            if(validarPatron(dato,"[Ee][Ss][0-9]{20}")){
                                dato = dato.toUpperCase();
                                double monto = banco.obtenerSaldo(dato);
                                if(monto == -1) System.out.println("No se ha encontrado el dato");
                                else {
                                    System.out.println("El saldo actual de su cuenta es: " + monto);
                                }
                            }
                        }
                        separador();
                        break; // Fin case 6
                    case 7:
                        salir = true;
                        break;
                } //Fin del switch
            } catch (Exception e) {
                System.out.println("El valor que se ha ingresado es inválido"); //Error que se produce si en algún momento se ingresa un valor inválido al momento de crear una nueva cuenta bancaria
            }
        }//Fin del while
    }//Fin del main

    public static void separador(){ //Método enteramente estético, crea un separador
        System.out.println("--------------------------------------------------------");
    }

    /**
     * Metodo estético, añade cabeceros a las tablas de datos
     */
    public static void cabeceroTabla(){
        separador();
        System.out.println("|\tIBAN\t|\tPROPIETARIO\t|\tSALDO\t|\tDETALLES\t|");
        separador();
    }

}