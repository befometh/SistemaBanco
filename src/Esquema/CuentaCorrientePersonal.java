package Esquema;

import Interfaces.*;

public class CuentaCorrientePersonal extends CuentaBancaria implements Imprimible {
    Persona titular;
    double comision;

    /**
     * Constructor
     * @param nombre del titular de la cuenta
     * @param apellido del titular
     * @param dni del titular
     * @param iban asociado a la cuenta
     * @param saldo inicial de la cuenta
     * @param comision porcentaje de comisión que se lleva el banco.
     */
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
        mensaje.append("Coste de Comisión: " + this.getComision() +"\t|");
        return mensaje.toString();
    }
}
