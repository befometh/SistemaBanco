/**
 * @author Cristyan Morales Acevedo
 * @desc Motor principal del programa: Banco, hace de controlador y a la vez resuelve problemas al conectar con la base "Esquema" y sus componentes
 */
package Motor;

import Esquema.*;

import java.util.HashMap;
import java.util.Scanner;

import static Motor.Validador.pedirDoble;

public class Banco {
    /*
    Se implementa HashMap porque el proyecto no requiere organización,
    pero si necesita una clave de búsqueda, en este caso el DNI, que debe
    ser único para cada cuenta bancaria.
     */
    private final HashMap<String,CuentaBancaria> cuentas = new HashMap<>();

    public boolean listaVacia() {
        return cuentas.isEmpty();
    }

    /**
     * Inicializador de cuentas
     *
     * @param datosPersonales array de dos campos: Campo 1 = número de clientes (1 de no ser una empresa) y Campo 2 = tipo de dato, (puede ser nombre, apellido o DNI, en ese orden)
     * @param tipoCuenta      1 = Corriente, 2 = Ahorros
     * @param saldoInicial    saldo con el que se crea la cuenta
     * @param esEmpresa       booleano, verdadero si es empresa, falso si no
     * @return IBAN de la cuenta ya creada
     */
    public String abrirCuenta(String[][] datosPersonales, int tipoCuenta, double saldoInicial, boolean esEmpresa) {
        CuentaBancaria dato;
        dato = switch (tipoCuenta) {
            case 1 -> crearCorriente(esEmpresa, datosPersonales, saldoInicial);
            case 2 -> crearAhorros(datosPersonales[0], saldoInicial);
            default -> null;
        };
        if (dato == null) {
            System.out.println("Se ha ingresado un valor inválido, vuelva a intentarlo");
            return "";
        }
        cuentas.put(dato.getIBAN(),dato);
        return dato.getIBAN();
    }

    /**
     * Método que permite crear específicamente una cuenta corriente
     *
     * @param esEmpresa       le indica al método si trata con una empresa
     * @param datosPersonales un arreglo que contiene el nombre, apellido y dni de cada persona, en ese orden
     * @param saldoInicial    valor inicial con el que empieza la cuenta
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
                if(maxDescubierto == -1)return null;
                comision = pedirDoble("El importe de comisión por descubierto");
                if(comision == -1) return null;
                dato = new CuentaCorrienteEmpresa(nombreEmp, propietarios, iban, saldoInicial, maxDescubierto, comision);
            } else {
                String iban = crearIBAN();
                comision = pedirDoble("El importe por comisión");
                if(comision==-1) return null;
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
     *
     * @param datosPersonales datos del titular
     * @param saldoInicial    saldo con el que empieza la cuenta
     * @return La cuenta con la estructura ya creada
     */
    private CuentaBancaria crearAhorros(String[] datosPersonales, double saldoInicial) {
        CuentaAhorro dato;
        String iban = crearIBAN();
        double interes = pedirDoble("el porcentaje de interés actual");
        if(interes == -1) return null;
        dato = new CuentaAhorro(datosPersonales[0], datosPersonales[1], datosPersonales[2], iban, saldoInicial, interes);
        return dato;
    }

    /**
     * Generador de IBAN, crea el IBAN y garantiza que no se repita en la lista
     *
     * @return el IBAN ya formado
     */
    private String crearIBAN() {
        StringBuilder temp;
        String iban;
        boolean repetido = false;
        do {
            temp = new StringBuilder("ES");
            for (int i = 0; i < 20; i++)
                temp.append((int) (Math.random() * 10));
            iban = temp.toString();
            if (cuentas.isEmpty()) {
                return iban;
            } else {
                for (CuentaBancaria valor : cuentas.values()) {
                    if (valor.getIBAN().equals(iban)) {
                        repetido = true;
                        break;
                    }
                }
            }
        } while (repetido);
        return iban;
    }

    /**
     * Devuelve un String con la lista de cuentas en formato tabla
     *
     * @return la lista de cuentas
     */
    public String listarCuentas() {
        StringBuilder msg = new StringBuilder();
        for(CuentaBancaria dato : cuentas.values()) {
            msg.append(dato.devolverInfoString()).append("\n");
        }
        return msg.toString();
    }

    /**
     * Muestra una cuenta bancaria solicitada
     *
     * @param iban el IBAN de la cuenta a mostrar
     * @return la cuenta en cuestión en formato texto tipo tabla
     */
    public String buscarCuenta(String iban) {
        if (cuentas.containsKey(iban)) {
            return cuentas.get(iban).devolverInfoString();
        }
        else return "";
    }

    /**
     * Modifica el saldo de la cuenta en caso de un ingreso
     *
     * @param iban    IBAN de la cuenta a modificar
     * @param ingreso valor del saldo nuevo
     * @return verdadero si se completa con éxito, falso si hubo un error en la transacción
     */
    public boolean ingresoBancario(String iban, double ingreso) {
        if (cuentas.containsKey(iban)) {
            if(ingreso > 0) {
                CuentaBancaria dato = cuentas.get(iban);
                dato.setSaldo(dato.getSaldo() + ingreso);
                cuentas.put(iban, dato);
                return true;
            }
            else {
                System.out.println("El valor que va a ingresar ha de ser un número positivo");
                return false;
            }
        }else {
            System.out.println("La cuenta bancaria no existe");
            return false;
        }
    }

    /**
     * Modifica la cuenta en caso es un retiro
     *
     * @param iban   IBAN de la cuenta a la que se hará un retiro
     * @param retiro monto a retirar
     * @return verdadero si la transacción es exitosa, falso si hay errores como que el IBAN no se encuentre o no se pueda realizar el retiro
     */
    public boolean retiroBancario(String iban, double retiro) {
        if (cuentas.containsKey(iban)) {
                CuentaBancaria dato = cuentas.get(iban);
                double diferencia = dato.getSaldo() - retiro;
                if(diferencia >= 0) {
                    dato.setSaldo(diferencia);
                    cuentas.put(iban, dato);
                    return true;
                }else {
                    return false;
                }
        }else {
            return false;
        }
    }

    /**
     * Muestra el saldo de una cuenta en concreto
     *
     * @param iban el IBAN de la cuenta a consultar
     * @return el saldo actual o -1 si no se encuentra dicha cuenta
     */
    public double obtenerSaldo(String iban) {
        if (cuentas.containsKey(iban)) {
            return cuentas.get(iban).getSaldo();
        }
        else return -1;
    }

    /**
     * Elimina la cuenta
     * @param iban de la cuenta a eliminar
     * @return verdadero si es exitoso, falso si no
     */
    public boolean eliminarCuenta(String iban) {
        if (cuentas.containsKey(iban)) {
            cuentas.remove(iban);
            return true;
        }
        else return false;
    }
}
