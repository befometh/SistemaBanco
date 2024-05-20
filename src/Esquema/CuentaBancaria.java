package Esquema;

import Interfaces.*;

public abstract class CuentaBancaria implements Imprimible, ClaseCuenta {
    String iban;
    double saldo;

    public CuentaBancaria(String iban, double saldo) {
        this.iban = iban;
        this.saldo = saldo;
    }

    //Getters y Setters

    public String getIBAN() {
        return iban;
    }

    public void setIBAN(String iban) {
        this.iban = iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    //Interfaz: Imprimible
    @Override
    public String devolverInfoString() {
        return iban +","+ saldo;
    }

    public String tipoCuenta(){
        return "";
    }
}
