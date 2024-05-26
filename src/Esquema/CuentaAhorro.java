/**
 * @author Cristyan Morales Acevedo
 * @desc Clase CuentaAhorro, contiene lo básico que necesita todas las cuentas de ahorro, clase tipo POYO, hereda de CuentaBancaria
 */
package Esquema;

import java.io.Serializable;

import Interfaces.*;

public class CuentaAhorro extends CuentaBancaria implements Imprimible, Serializable {
    private double interes; //Porcentaje de interés de la cuenta
    private Persona titular; //Nombre del titular

    /**
     * Constructor
     * @param nombre del titular
     * @param apellido del titular
     * @param dni del titular
     * @param iban de la cuenta
     * @param saldo inicial de la cuenta
     * @param interes mensual que se pagará al usuario
     */
    public CuentaAhorro(String nombre, String apellido, String dni, String iban, double saldo, double interes) {
        super(iban, saldo); //Se invoca el constructor de CuentaBancaria, y se le entregan los datos necesarios, como el iban y el saldo
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

    /**
     * Método que permite que se entregue los datos a los usuarios, cumple con el formato del método cabecera() de la clase Principal
     * @return El dato ya construido.
     */
    //Interfaz: Imprimible
    @Override
    public String devolverInfoString() {
        //"|\tIBAN\t|\tPROPIETARIO\t|\tSALDO\t|\tDETALLES\t|"
        String[] cuenta = super.devolverInfoString().split(",");
        StringBuffer mensaje = new StringBuffer("|\t");
        mensaje.append(cuenta[0]+"\t|\t");
        mensaje.append(this.getTitular().getNombre()+" "+this.getTitular().getApellido()+"\t|\t");
        mensaje.append(cuenta[1]+"\t|\t");
        mensaje.append("Ganancia por Interés: " + this.getInteres() +"\t|");
        return mensaje.toString();
    }

}


