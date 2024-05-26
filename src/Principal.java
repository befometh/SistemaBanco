/**
 * @author Cristyan Morales Acevedo
 * @desc Clase principal: Interfaz de enlace con usuario y algunos validadores.
 */

import Motor.Banco;

import java.io.*;
import java.util.*;

import static Motor.Validador.*;

public class Principal {
    static final String nomArchivo = "datoscuentabancaria.dat"; //Se ingresan los archivos de
    static final String rutaWindows = "C:\\BD\\"+nomArchivo; // Ruta base si el so es basado en Windows
    static final String rutaLinux = System.getProperty("user.home") +"/BD/"+ nomArchivo; // Ruta base si el SO es basado en Linux
    static String sistemaOp; //Variable que va a almacenar el nombre del sistema operativo
    static File archivo; // Entidad que representa al archivo donde se encuentra la BD
    static Banco banco; // bjeto que interactuará con la clase banco

    public static void main(String[] args) {

        Scanner teclado; //Listener del teclado
        banco = new Banco(); //Se inicializa el ventilador base de datos al momento de iniciar el programa
        int opcion; //variable que almacena donde se va a guardar el dato
        boolean salir = false; //variable que cambia si se decide guardar la información

        sistemaOp = cargarBDSegunSO(); //Se solicita la orden de cargar la BD dependiendo el SO en el que nos encontremos

        //menú inicial
        while (!salir) {
            teclado = new Scanner(System.in); //Variable que recibe las opciones del usuario, y funciona como escucha pincipal
            teclado.useDelimiter("\n"); //Indica al Scanner teclado donde debe detenerse para dejar de recibir datos
            System.out.println("""
                    1. Abrir una nueva cuenta.
                    2. Ver un listado de las cuentas disponibles
                    3. Obtener los datos de una cuenta concreta.
                    4. Realizar un ingreso en una cuenta.
                    5. Retirar efectivo de una cuenta
                    6. Consultar el saldo actual de una cuenta.
                    7. Eliminar cuenta
                    8. Salir de la aplicación.
                    \n
                    Ingrese una opción:""");

            try {
                //Se escucha al teclado el valor, si no se ingresa correctamente, arroja error a la pantalla y vuelva  intentarlo
                opcion = teclado.nextInt();
                /*Booleano que va a auditar el programa constantemente, validando si hay errores de ingreso, si hay error,
                 no se continúa más esta sección y se arroja error*/
                boolean error = false; //Variable que escuchará permanentemente si se presenta un error
                switch (opcion) { //Ingreso a la lista de opciones de manera lógica
                    case 1:

                        //Inicialización de la variable esEmpresa, quien va a ser verdadera si lo es y falsa si no lo es,
                        // más adelante
                        boolean esEmpresa = false;
                        int seleccion; //Selección del usuario

                        //Arreglo que contiene los propietarios de la cuenta, en caso de requerirse varios,
                        // de lo contrario será una única persona
                        String[][] propietarios = null;
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
                        if (seleccion == -1)
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
                                if (seleccion == -1)
                                    error = true;
                            }
                        }
                        if (!error) {
                            //Las empresas sólamente pueden tener cuentas corrientes, por ende estas dos condiciones
                            // deben ser verdaderas
                            if (tipoCuenta == 1 && esEmpresa) { //Se confirma si se trata con una empresa o no

                                //Se solicita el número de propietarios de la empresa, y ese valor se indica para crear
                                // el arreglo que va a llevar sus datos personales
                                seleccion = pedirOpcion("Por favor ingrese el número de personas que componen la " +
                                        "titularidad de su empresa");

                                //Se llama al método de validador que permite ingresar a todos los propietarios
                                propietarios = (ingresarPropietarios(seleccion));
                            } else {

                                //En el caso de no ser empresa, se da por hecho que solo hay un propietario como persona
                                // natural, aun siendo una cuenta corriente
                                propietarios = (ingresarPropietarios(1));
                            }

                            //Se asigna la cantidad inicial,
                            // no debe ser un número negativo
                            saldoInicial = pedirDoble("La cantidad inicial a ingresar: ");
                            if (saldoInicial < 0)
                                error = true;
                        }
                        if (!error) {

                            //Se crea la cuenta, abrirCuenta() de Banco devuelve el número de IBAN de la cuenta ya
                            // creada, para ser listada con facilidad.
                            String iban = banco.abrirCuenta(propietarios, tipoCuenta, saldoInicial, esEmpresa);
                            System.out.println("Se ha creado la cuenta con éxito, cuenta creada: ");
                            cabeceroTabla(); //Se crea gráficamente un cabecero
                            System.out.println(banco.buscarCuenta(iban)); //Se muestra la información de la cuenta recién creada
                        } else {
                            separador();
                            System.out.println("No se pudo crear la cuenta, Se ha producido un error"); //Arroja error si se presenta una falta tipográfica durante el programa
                        }
                        break; //Fin case 1

                    case 2:
                        if (banco.listaVacia()) {

                            //Este error se presenta constantemente en el código, se manifiesta si aún no se ha creado ninguna cuenta
                            System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para " +
                                    "continuar");
                        }
                        else {
                            cabeceroTabla();
                            System.out.println(banco.listarCuentas()); //Se solicita la lista de cuentas total
                        }
                        break; //Fin case 2

                    case 3:
                        if (banco.listaVacia())
                            System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para " +
                                    "continuar");
                        else {
                            String dato;
                            dato = pedirDato("Por favor ingrese el IBAN de la cuenta a buscar:");

                            // Se usa el método estático de Validadores validarPatron para verificar que el iban cumpla
                            // con las características
                            if (validarPatron(dato, "[Ee][Ss][0-9]{20}")) {

                                //Se asegura que la forma del IBAN tenga caracteres en mayúscula
                                dato = dato.toUpperCase();

                                //Se invoca al método de banco mostrarCuenta(), si falla arroja un String vacio ("")
                                String msg = banco.buscarCuenta(dato);
                                if (msg.isEmpty()) {
                                    System.out.println("No se ha encontrado el dato"); //Error si no se encuentra el dato
                                } else {
                                    cabeceroTabla();
                                    System.out.println(msg); //Se enseña el dato que solicita el usuario
                                }
                            } else
                                System.out.println("El dato que ha ingresado no tiene el formato de un IBAN, vuelva a " +
                                        "intentarlo.");
                        }
                        break; //Fin case 3
                    case 4:
                        if (banco.listaVacia())
                            System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para " +
                                    "continuar");
                        else {
                            String dato; //Se declara la variable escucha
                            dato = pedirDato("Por favor ingrese el IBAN de la cuenta a buscar:");

                            //Se confirma que el dato esté bien consolidado
                            if (validarPatron(dato, "[Ee][Ss][0-9]{20}")) {

                                //Se convierte el dato ingresado por el usuario al formato almacenado, en mayúsculas
                                dato = dato.toUpperCase();

                                //Se hace el ingreso del monto a sumar en la cuenta
                                double ingreso = pedirDoble("Ingrese el monto que desea ingresar");

                                // se solicita la operación, tiene un booleano de nombre error que variará en función de
                                // si se pudo cumplir o no la operación
                                error = banco.ingresoBancario(dato, ingreso);
                                if (!error)
                                    System.out.println("No se ha encontrado el dato"); //Error si no se encuentra el IBAN
                                else {

                                    //Se muestra si se ingresa un monto con éxito, también enseña el saldo actual
                                    // llamando a obtenerSaldo() de banco
                                    System.out.println("Se ha realizado el ingreso con éxito. nuevo Saldo: "
                                            + banco.obtenerSaldo(dato));
                                }
                            }
                        }
                        break;//Fin case 4

                    //A efectos prácticos retiro e ingreso se comportan de manera similar
                    case 5:
                        if (banco.listaVacia())
                            System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para continuar");
                        else {
                            String dato;
                            dato = pedirDato("Por favor ingrese el IBAN de la cuenta a buscar:");
                            if (validarPatron(dato, "[Ee][Ss][0-9]{20}")) {
                                dato = dato.toUpperCase();
                                double retiro = pedirDoble("Ingrese el monto que desea retirar");
                                error = banco.retiroBancario(dato, retiro);
                                if (!error) {
                                    if (banco.obtenerSaldo(dato) < retiro) //Confirma si el saldo es menor del que se trata de retirar
                                        System.out.println("Su cuenta tiene saldo insuficiente");
                                    else
                                        System.out.println("No se ha encontrado el dato");
                                } else
                                    System.out.println("Su retiro se ha completado con éxito, nuevo Saldo: " + banco.obtenerSaldo(dato));
                            }
                        }
                        break; //Fin case 5
                    //Igual que con retiro e ingreso mostrar el saldo se comportan de manera muy similar
                    case 6:
                        if (banco.listaVacia())
                            System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para continuar");
                        else {
                            String dato;
                            dato = pedirDato("Por favor ingrese el IBAN de la cuenta a buscar, (RECUERDE QUE LA CUENTA NO DEBE TENER SALDO):");
                            if (validarPatron(dato, "[Ee][Ss][0-9]{20}")) {
                                dato = dato.toUpperCase();
                                double monto = banco.obtenerSaldo(dato);
                                if (monto == -1) System.out.println("No se ha encontrado el dato");
                                else {
                                    System.out.println("El saldo actual de su cuenta es: " + monto);
                                }
                            }
                        }
                        break; // Fin case 6
                    case 7:
                        if (banco.listaVacia())
                            System.out.println("Aún no se ha ingresado ningún valor, cree al menos una cuenta para continuar");
                        else {
                            String dato = pedirDato("Por favor ingrese el IBAN de la cuenta a eliminar:");
                            if (validarPatron(dato, "[Ee][Ss][0-9]{20}")) {
                                dato = dato.toUpperCase();
                                String cuenta = banco.buscarCuenta(dato); //Se busca la cuenta a eliminar para enseñarla al usuario
                                if (cuenta.isEmpty()) //Se confirma que la cuenta exista
                                    System.out.println("No se ha encontrado la cuenta solicitada");
                                else {
                                    System.out.println("Está solicitando eliminar la cuenta:");
                                    cabeceroTabla();
                                    System.out.println(cuenta); //Se enseña la cuenta a eliminar al usuario
                                    separador();
                                    opcion = pedirInt("¿Desea continuar?\n1.Si\t2.No");
                                    switch (opcion) {
                                        case 1:
                                            if (banco.obtenerSaldo(dato) == 0) { //Se confirma que la cuenta no tenga saldo al momento de eliminarla
                                                boolean op = banco.eliminarCuenta(dato); //Se elimina la cuenta, si falla op será falso
                                                if (op)
                                                    System.out.println("Se ha eliminado la cuenta de manera exitosa.");
                                                else
                                                    System.out.println("Ha ocurrido un error, vuelva a intentarlo"); //Error por fallo general
                                            } else
                                                System.out.println("El saldo de la cuenta debe ser 0 antes de ser eliminada."); //Error si saldo no es 0
                                            break;
                                        case 2:
                                            break;
                                        default:
                                            System.out.println("Valor ingresado no es válido, vuelva a intentarlo");
                                    }
                                }
                            }
                        }
                        break; //Fin case 7
                    case 8:
                        if (!archivo.exists()) { //Se confirma si el archivo no existe
                            comprobarDirectorio(); //Se llama el método de comprobación y creación de archivo
                        }
                        salir = banco.subirBD(archivo.getPath()); //Se cambia variable de salida
                        break; //Fin case 8
                } //Fin del switch
                separador();
            } catch (Exception e) {
                System.out.println("El valor que se ha ingresado es inválido"); //Error que se produce si en algún momento se ingresa un valor inválido al momento de crear una nueva cuenta bancaria
            }
        }//Fin del while
    }//Fin del main

    public static void separador() { //Método enteramente estético, crea un separador
        System.out.println("-".repeat(130)); //Se repite un guión 130 veces para hacer un separador
    }

    /**
     * Metodo estético, añade cabeceros a las tablas de datos
     */
    public static void cabeceroTabla() {
        separador(); //Se implementa el método separador para dar estructura a la tabla
        System.out.println("|\tIBAN\t|\tPROPIETARIO\t|\tSALDO\t|\tDETALLES\t|"); //formato del cabecero de las tablas
        separador();
    }

    /**
     * Método que permite cargar el archivo
     */
    public static String cargarBDSegunSO(){
        String nombreSO = System.getProperty("os.name").toLowerCase(); //Se trae el nombre del sistema operativo y se almacena en la variable
        try{
            /*
            Se verifica opciones:
                Si el nombreSO contiene win, se entiende que es un windows
                Si el nombreSO nux es linux, o nix es unix, ambos casos utilizan el formato /root
             */
            if (nombreSO.contains("win")) archivo = new File(rutaWindows);
            else if (nombreSO.contains("nux")||nombreSO.contains("nix")) archivo = new File(rutaLinux);

            if(archivo.exists()) // Si la base de datos existe se carga el archivo y se trae la base de datos
                banco.cargarBD(archivo.getPath()); //Se implementa cargarBD de Banco
        } catch (Exception e){
            System.err.println("No ha sido posible cargar la BD"+e.getMessage()); //Error si se presenta una excepción
        }
        return nombreSO;
    }

    /**
     * Método que confirma que la carpeta de la BD ha sido creada, si no lo está, la crea
     */
    private static void comprobarDirectorio() {
        String regex = ""; //Se crea e inicializa la variable que luego hará parte del split
        String builder = ""; //Se crea e inicializa la variable que luego permitirá reconstruir la ruta
        try{
            if(sistemaOp.contains("win")){ //Caso 1: Sistema operativo windows
            regex = "[\\\\]"; //crea un string formato regex para reconocer los \ (es cuadruple porque son un par de caracteres escape y valor)
            builder = "\\"; //crea un string para reconocer los \, (es doble porque el primero es un caracter de escape)
            } else if (sistemaOp.contains("nix")||sistemaOp.contains("nux")) { //Caso 2: Sistema operativo linux/unix
            regex = "/"; //en el caso de linux se utiliza solamente la barra diagonal, en esta situación no hay problema y no requiere variables de escape
            builder = regex;
            }

            //se parte la ruta en sus partes fundamentales y se asigna a un array, se usa el regex tratado anteriormente
            String [] elementos = archivo.getPath().split(regex);
            //Se crea un buffer de carga de la ruta confirmada
            StringBuilder temp = new StringBuilder();
            /*
            Contador que garantiza:
            1. Que las "\" o "/" terminen en la parte de directorios de la ruta
            2. Que se aplique el mkdir para los directorios y el createNewFile() para el fichero de la BD
             */
            int contador = 0;
            for(String elemento : elementos) { //For que recorre el arreglo de piezas que conforman la ruta de la BD
                contador ++; //el contador empieza en 1

                //Al buffer se le va reconstruyendo la ruta completa poco a poco, verificando que cada carpeta esté creada
                temp.append(elemento);

                //Se confirma que no estemos en la sección del fichero en la parte /directorios/fichero.dat
                if(contador != elementos.length) {

                    //Se le asigna el valor constructor builder para darle forma a la ruta
                    temp.append(builder);

                    //Se confirma que la sección del directorio en la que nos encontremos no existe, para proceder a la creación
                    if (!(new File(temp.toString()).exists())) {

                        //Si no existe, se crea el directorio, creando una variable temporal directorio
                        File directorio = new File(temp.toString());
                        directorio.mkdir(); //Orden de creación
                    }
                } else {
                    //Al llegar al final del contador el tratamiento es distinto
                    File bd = new File(temp.toString());
                    bd.createNewFile(); //Se crea el fichero
                }
            }
        }catch(IOException e){
            System.out.println("No es posible acceder al archivo");
        }
    }
}