package Esquema;

import Interfaces.*;

public class CuentaAhorro extends CuentaBancaria implements ClaseCuenta, Imprimible {
    private double interes;
    private Persona titular;

    public CuentaAhorro(String nombre, String apellido, String dni, String iban, double saldo, double interes) {
        super(iban, saldo);
        titular = new Persona(nombre, apellido, dni);
        this.interes = interes;
    }

    //Getters y Setters
    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public Persona getTitular() {
        return titular;
    }

    public void setTitular(Persona titular) {
        this.titular = titular;
    }


    //Interfaz: Imprimible
    @Override
    public String devolverInfoString() {
        String mensaje = "Titular: " + titular.getNombre() + "\n" +
                super.devolverInfoString() +
                "\nTipo de Interes: " + this.interes;
        return mensaje;
    }

    public String tipoCuenta() {
        return "Ahorros";
    }

}


