package Esquema;

import Interfaces.*;

public class CuentaCorrientePersonal extends CuentaBancaria implements Imprimible, ClaseCuenta {
    Persona titular;
    double comision;
//Constructor

    public CuentaCorrientePersonal(String nombre,
                                   String apellido,
                                   String dni,
                                   String iban,
                                   double saldo,
                                   double comision) {
        super(iban, saldo);
        this.titular = new Persona(nombre, apellido, dni);
        this.comision = comision;
    }


    //Getters y Setters

    public Persona getTitular() {
        return titular;
    }

    public void setTitular(Persona titular) {
        this.titular = titular;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    //Interfaz: Imprimible
    @Override
    public String devolverInfoString() {
        String mensaje = "Titular: " + titular.getNombre() + "\n" +
                super.devolverInfoString() +
                "\nComision de mantenimiento: " + this.comision;
        return mensaje;
    }

    public String tipoCuenta() {
        return "Personal";
    }
}
