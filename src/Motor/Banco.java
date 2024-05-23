package Motor;

import Esquema.*;

import java.util.Scanner;

public class Banco {

    private CuentaBancaria[] cuentas;
    private final int MAX_CUENTAS = 100;
    private int numCuentas;

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
     * @param datosPersonales
     * @param tipoCuenta
     * @param saldoInicial
     * @param esEmpresa
     * @return
     */
    public String abrirCuenta(String[][] datosPersonales, int tipoCuenta, double saldoInicial, boolean esEmpresa) {
        CuentaBancaria dato;
        if (this.numCuentas >= getMAX_CUENTAS()) {
            System.out.println("No hay espacio para mas cuentas");
            return "";
        } else {
            switch (tipoCuenta) {
                case 1:
                    dato = crearCorriente(esEmpresa, datosPersonales, saldoInicial);
                    break;
                case 2:
                    dato = crearAhorros(datosPersonales[0], saldoInicial);
                    break;
                default:
                    dato = null;
            }
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
            Scanner sc = new Scanner(System.in);
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
                return dato;
            } else {
                String iban = crearIBAN();
                comision = pedirDoble("El importe por comisión");
                dato = new CuentaCorrientePersonal(datosPersonales[0][0],
                        datosPersonales[0][1],
                        datosPersonales[0][2],
                        iban,
                        saldoInicial,
                        comision);
                return dato;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param datosPersonales
     * @param saldoInicial
     * @return
     */
    private CuentaBancaria crearAhorros(String[] datosPersonales, double saldoInicial) {
        CuentaAhorro dato;
        String iban = crearIBAN();
        double interes = pedirDoble("el porcentaje de interés actual");
        dato = new CuentaAhorro(datosPersonales[0], datosPersonales[1], datosPersonales[2], iban, saldoInicial, interes);
        return dato;
    }

    /**
     *
     * @return
     */
    private String crearIBAN() {
        StringBuffer temp;
        String dato;
        boolean repetido = true;
        do {
            temp = new StringBuffer("ES");
            for (int i = 0; i < 20; i++)
                temp.append((int)(Math.random() * 10));
            dato = temp.toString();
            if (this.numCuentas > 0) {
                int j = 0;
                while (j < this.numCuentas && this.cuentas[j].getIBAN() != dato)
                    j++;

                if (j == this.numCuentas) {
                    repetido = false;
                }
            } else repetido = false;
        } while (repetido);
        return dato;
    }

    /**
     *
     * @return
     */
    public String listarCuentas() {
        StringBuffer msg = new StringBuffer();
        for(int i = 0; i < this.getNumCuentas(); i++) {
            msg.append(this.cuentas[i].devolverInfoString()+"\n");
        }
        return msg.toString();
    }

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

    public String mostrarCuenta(String dato) {
        int ubicacion = buscarCuenta(dato);
        if(ubicacion == -1)
            return "";
        else
            return cuentas[ubicacion].devolverInfoString();
    }

    private int buscarCuenta(String dato) {
        int i=0;
        int tam = getNumCuentas();
        while(i < tam && !cuentas[i].getIBAN().equals(dato))
            i++;
        if(i<tam)
            return i;
        else
            return -1;
    }

    public boolean ingresoBancario(String dato, double ingreso) {
        int pos = buscarCuenta(dato);
        if(pos == -1) return false;
        else{
            cuentas[pos].setSaldo(cuentas[pos].getSaldo() + ingreso);
            return true;
        }
    }

    public boolean retiroBancario(String dato, double retiro) {
        int pos = buscarCuenta(dato);
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

    public double obtenerSaldo(String dato) {
        int pos = buscarCuenta(dato);
        if(pos == -1) return -1;
        else return cuentas[pos].getSaldo();
    }
}
