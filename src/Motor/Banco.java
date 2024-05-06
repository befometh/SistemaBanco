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

    public boolean abrirCuenta(String[][] datosPersonales, int tipoCuenta, double saldoInicial, boolean esEmpresa) {
        CuentaBancaria dato;
        if (this.numCuentas >= this.MAX_CUENTAS) {
            System.out.println("No hay espacio para mas cuentas");
            return false;
        } else {
            switch (tipoCuenta) {
                case 1:
                    dato = crearAhorros(datosPersonales[0], saldoInicial);
                    break;
                case 2:
                    dato = crearCorriente(esEmpresa, datosPersonales, saldoInicial);
                    break;
                default:
                    dato = null;
            }
            if (dato == null) {
                System.out.println("Se ha ingresado un valor inválido, vuelva a intentarlo");
                return false;
            }
            this.cuentas[this.numCuentas] = dato;
            this.numCuentas++;
            return true;
        }
    }

    public String listarCuentas() {
        StringBuffer msg = new StringBuffer(
                "|\tIBAN\t|\tPROPIETARIO\t|\tSALDO\t|\tDETALLES\t|"
        );
        for (CuentaBancaria cuenta : this.cuentas) {
            msg.append("|\t" + cuenta.getIBAN());
            switch(cuenta.tipoCuenta()) {
                case "Ahorros":
                    msg.append(mostrarCuenta((CuentaAhorro) cuenta));
                    break;
                case "Personal":
                    msg.append(mostrarCuenta((CuentaCorrientePersonal) cuenta));
                    break;
                case "Empresa":
                    msg.append(mostrarCuenta((CuentaCorrienteEmpresa) cuenta));
                    break;
            }
        }
        return msg.toString();
    }

    private String mostrarCuenta(CuentaAhorro cuenta) {
        StringBuffer msg = new StringBuffer();
        msg.append("\t|\t" + cuenta.getTitular().getNombre() + " ");
        msg.append(cuenta.getTitular().getApellido());
        msg.append("\t|\t" + cuenta.getSaldo());
        msg.append("\t|\tInteres de Remuneración: " + cuenta.getInteres());
        return msg.toString();
    }

    private String mostrarCuenta(CuentaCorrientePersonal cuenta){
        StringBuffer msg = new StringBuffer();
        msg.append("\t|\t" + cuenta.getTitular().getNombre() + " ");
        msg.append(cuenta.getTitular().getApellido());
        msg.append("\t|\t" + cuenta.getSaldo());
        msg.append("\t|\tComision de Manejo: " + cuenta.getComision());
        return msg.toString();
    }

    private String mostrarCuenta(CuentaCorrienteEmpresa cuenta){
        StringBuffer msg = new StringBuffer();
        msg.append("\t|\t" + cuenta.getEmpresa().getNombreEmp() + " ");
        msg.append("\t|\t" + cuenta.getSaldo());
        msg.append("\t|\tMáximo Descubierto: " + cuenta.getMaxDescubierto());
        msg.append("\tComisión fija por cada descubierto: " + cuenta.comisionDescubierto());
        return msg.toString();
    }

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
                    System.out.println("Por favor ingrese el nombre de la empresa:");
                    nombreEmp = sc.nextLine();
                    System.out.println("El nombre de su empresa es: " + nombreEmp +
                            "\n¿Está seguro? 1:Si 2:No");
                    if (sc.nextInt() == 1) {
                        confirm = true;
                    }
                } while (confirm);
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

    private CuentaBancaria crearAhorros(String[] datosPersonales, double saldoInicial) {
        CuentaAhorro dato;
        String iban = crearIBAN();
        double interes = pedirDoble("el porcentaje de interés actual");
        dato = new CuentaAhorro(datosPersonales[0], datosPersonales[1], datosPersonales[2], iban, saldoInicial, interes);
        return dato;
    }

    private String crearIBAN() {
        StringBuffer temp;
        String dato;
        boolean repetido = true;
        do {
            temp = new StringBuffer("ES");
            for (int i = 0; i < 20; i++)
                temp.append(Math.random() * 10);
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
}
