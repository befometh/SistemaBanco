/**
 * @author Cristyan Morales Acevedo
 * @desc Motor principal del programa: Banco, hace de controlador y a la vez resuelve problemas al conectar con la base "Esquema" y sus componentes
 */
package Motor;

import Esquema.*;

import java.util.Scanner;

public class Banco {

    private final CuentaBancaria[] cuentas;
    private final int MAX_CUENTAS = 100;
    private int numCuentas;

    /**
     * Constructor, crea la lista de cuentas según los datos ingresados
     */
    public Banco() {
        this.cuentas = new CuentaBancaria[this.MAX_CUENTAS];
        this.numCuentas = 0;
    }

    //Getters y Setters

    public int getMAX_CUENTAS() {
        return MAX_CUENTAS;
    }

    public int getNumCuentas() {
        return numCuentas;
    }

    public void setNumCuentas(int numCuentas) {
        this.numCuentas = numCuentas;
    }

    /**
     * Inicializador de cuentas
     * @param datosPersonales array de dos campos: Campo 1 = número de clientes (1 de no ser una empresa) y Campo 2 = tipo de dato, (puede ser nombre, apellido o DNI, en ese orden)
     * @param tipoCuenta 1 = Corriente, 2 = Ahorros
     * @param saldoInicial saldo con el que se crea la cuenta
     * @param esEmpresa booleano, verdadero si es empresa, falso si no
     * @return IBAN de la cuenta ya creada
     */
    public String abrirCuenta(String[][] datosPersonales, int tipoCuenta, double saldoInicial, boolean esEmpresa) {
        CuentaBancaria dato;
        if (this.numCuentas >= getMAX_CUENTAS()) {
            System.out.println("No hay espacio para mas cuentas");
            return "";
        } else {
            dato = switch (tipoCuenta) {
                case 1 -> crearCorriente(esEmpresa, datosPersonales, saldoInicial);
                case 2 -> crearAhorros(datosPersonales[0], saldoInicial);
                default -> null;
            };
            if (dato == null) {
                System.out.println("Se ha ingresado un valor inválido, vuelva a intentarlo");
                return "";
            }
            int pos = getNumCuentas();
            this.cuentas[pos] = dato;
            setNumCuentas(getNumCuentas()+1);
            return this.cuentas[pos].getIBAN();
        }
    }

    /**
     * Método que permite crear específicamente una cuenta corriente
     * @param esEmpresa le indica al método si trata con una empresa
     * @param datosPersonales un arreglo que contiene el nombre, apellido y dni de cada persona, en ese orden
     * @param saldoInicial valor inicial con el que empieza la cuenta
     * @return La cuenta corriente ya creada
     */
    private CuentaBancaria crearCorriente(boolean esEmpresa, String[][] datosPersonales, double saldoInicial) {
        CuentaBancaria dato;
        String nombreEmp;
        double comision;
        Persona[] propietarios = new Persona[datosPersonales.length];
        try {
            Scanner sc;
            if (esEmpresa) {
                boolean confirm = false;
                do {
                    sc = new Scanner(System.in);
                    System.out.println("Por favor ingrese el nombre de la empresa:");
                    nombreEmp = sc.nextLine();
                    System.out.println("El nombre de su empresa es: " + nombreEmp +
                            "\n¿Está seguro? 1:Si 2:No");
                    int eleccion = sc.nextInt();
                    if (eleccion == 1) {
                        confirm = true;
                    }
                } while (!confirm);
                int i = 0;
                for (String[] propietario : datosPersonales) {
                    propietarios[i] = new Persona(propietario[0], propietario[1], propietario[2]);
                }
                String iban = crearIBAN();
                double maxDescubierto = pedirDoble("el máximo descubierto permitido");
                comision = pedirDoble("El importe de comisión por descubierto");
                dato = new CuentaCorrienteEmpresa(nombreEmp, propietarios, iban, saldoInicial, maxDescubierto, comision);
            } else {
                String iban = crearIBAN();
                comision = pedirDoble("El importe por comisión");
                dato = new CuentaCorrientePersonal(datosPersonales[0][0],
                        datosPersonales[0][1],
                        datosPersonales[0][2],
                        iban,
                        saldoInicial,
                        comision);
            }
            return dato;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Luego de ingresar los datos básicos de una cuenta bancaria, se genera un creador para la cuenta de ahorros
     * @param datosPersonales datos del titular
     * @param saldoInicial saldo con el que empieza la cuenta
     * @return La cuenta con la estructura ya creada
     */
    private CuentaBancaria crearAhorros(String[] datosPersonales, double saldoInicial) {
        CuentaAhorro dato;
        String iban = crearIBAN();
        double interes = pedirDoble("el porcentaje de interés actual");
        dato = new CuentaAhorro(datosPersonales[0], datosPersonales[1], datosPersonales[2], iban, saldoInicial, interes);
        return dato;
    }

    /**
     *Generador de IBAN, crea el IBAN y garantiza que no se repita en la lista
     * @return el IBAN ya formado
     */
    private String crearIBAN() {
        StringBuilder temp;
        String iban;
        boolean repetido = true;
        do {
            temp = new StringBuilder("ES");
            for (int i = 0; i < 20; i++)
                temp.append((int)(Math.random() * 10));
            iban = temp.toString();
            if (getNumCuentas() > 0) {
                int j = 0;
                while (j < getNumCuentas() && !this.cuentas[j].getIBAN().equals(iban))
                    j++;

                if (j == getNumCuentas()) {
                    repetido = false;
                }
            } else repetido = false;
        } while (repetido);
        return iban;
    }

    /**
     *Devuelve un String con la lista de cuentas en formato tabla
     * @return la lista de cuentas
     */
    public String listarCuentas() {
        StringBuilder msg = new StringBuilder();
        for(int i = 0; i < this.getNumCuentas(); i++) {
            msg.append(this.cuentas[i].devolverInfoString()).append("\n");
        }
        return msg.toString();
    }

    /**
     *Pide los datos de ingreso adicionales para la creación de cuentas específicas, no sale del ciclo hasta que se ingrese el valor adecuadamente.
     * @param tipoDato Lo que aparece en pantalla, el dato requerido
     * @return El dato ya formateado
     */
    private double pedirDoble(String tipoDato) {
        Scanner sc;
        double dato = 0;
        boolean error;
        do {
            try {
                sc = new Scanner(System.in);
                System.out.println("Por favor ingrese " + tipoDato);
                dato = sc.nextDouble();
                error = false;
            } catch (Exception e) {
                error = true;
                System.out.println("El dato ingresado es incorrecto, recuerde que debe ingresar un número decimal, intente de nuevo");
            }
        } while (error);
        return dato;
    }

    /**
     * Muestra una cuenta bancaria solicitada
     * @param iban el IBAN de la cuenta a mostrar
     * @return la cuenta en cuestión en formato texto tipo tabla
     */
    public String mostrarCuenta(String iban) {
        int ubicacion = buscarCuenta(iban);
        if(ubicacion == -1)
            return "";
        else
            return cuentas[ubicacion].devolverInfoString();
    }

    /**
     * Buscador de cuentas, privado ya que funciona solo para el funcionamiento interno de la clase Banco
     * @param iban IBAN de la cuenta a buscar
     * @return la posición en el arreglo de la cuenta
     */
    private int buscarCuenta(String iban) {
        int i=0;
        int tam = getNumCuentas();
        while(i < tam && !cuentas[i].getIBAN().equals(iban))
            i++;
        if(i<tam)
            return i;
        else
            return -1;
    }

    /**
     * Modifica el saldo de la cuenta en caso de un ingreso
     * @param iban IBAN de la cuenta a modificar
     * @param ingreso valor del saldo nuevo
     * @return verdadero si se completa con éxito, falso si hubo un error en la transacción
     */
    public boolean ingresoBancario(String iban, double ingreso) {
        int pos = buscarCuenta(iban);
        if(ingreso > 0) {
            if (pos == -1) return false;
            else {
                cuentas[pos].setSaldo(cuentas[pos].getSaldo() + ingreso);
                return true;
            }
        } else{
            System.out.println("El monto del ingreso no puede ser un valor negativo");
            return false;
        }
    }

    /**
     * Modifica la cuenta en caso es un retiro
     * @param iban IBAN de la cuenta a la que se hará un retiro
     * @param retiro monto a retirar
     * @return verdadero si la transacción es exitosa, falso si hay errores como que el IBAN no se encuentre o no se pueda realizar el retiro
     */
    public boolean retiroBancario(String iban, double retiro) {
        int pos = buscarCuenta(iban);
        if(pos == -1) return false;
        else{
            double diferencia = cuentas[pos].getSaldo() - retiro;
            if(diferencia >= 0){
                cuentas[pos].setSaldo(diferencia);
                return true;
            }
            else return false;
        }
    }

    /**
     * Muestra el saldo de una cuenta en concreto
     * @param iban el IBAN de la cuenta a consultar
     * @return el saldo actual o -1 si no se encuentra dicha cuenta
     */
    public double obtenerSaldo(String iban) {
        int pos = buscarCuenta(iban);
        if(pos == -1) return -1;
        else return cuentas[pos].getSaldo();
    }
}
