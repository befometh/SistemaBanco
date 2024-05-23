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
        //"|\tIBAN\t|\tPROPIETARIO\t|\tSALDO\t|\tDETALLES\t|"
        String[] cuenta = super.devolverInfoString().split(",");
        StringBuffer mensaje = new StringBuffer("|\t");
        mensaje.append(cuenta[0]+"\t|\t");
        mensaje.append(this.getTitular().getNombre()+" "+this.getTitular().getApellido()+"\t|\t");
        mensaje.append(cuenta[1]+"\t|\t");
        mensaje.append("Ganancia por Interés: " + this.getInteres() +"\t|");
        return mensaje.toString();
    }

    public String tipoCuenta() {
        return "Ahorros";
    }

}


