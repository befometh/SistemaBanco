/**
 * @author Cristyan Morales Acevedo
 * @desc Clase CuentaBancaria, contiene la estructura base del resto de cuentas, Clase padre
 */
package Esquema;

import java.io.Serializable;

import Interfaces.Imprimible;

public abstract class CuentaBancaria implements Imprimible, Serializable { //Esta clase es abstracta, ya que no hay ninguna cuenta bancaria que no esté definida bajo los estándares de sus hijas
    String iban; //almacén del IBAN de la cuenta en concreto
    double saldo; //almacén del saldo de la cuenta

    /**
     * Constructor
     * @param iban de la cuenta creada
     * @param saldo inicial de la cuenta creada
     */
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

    /**
     * Método que devuelve la información de cada una de las cuentas
     * @return el dato sin construir, para ser tratado por las clases hijas.
     */
    //Interfaz: Imprimible
    @Override
    public String devolverInfoString() {
        return iban +","+ saldo;
    }
}
