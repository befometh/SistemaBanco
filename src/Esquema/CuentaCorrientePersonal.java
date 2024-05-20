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
        //"|\tIBAN\t|\tPROPIETARIO\t|\tSALDO\t|\tDETALLES\t|"
        String[] cuenta = super.devolverInfoString().split(",");
        StringBuffer mensaje = new StringBuffer("|\t");
        mensaje.append(cuenta[0]+"\t|\t");
        mensaje.append(this.getTitular().getNombre()+" "+this.getTitular().getApellido()+"\t|\t");
        mensaje.append(cuenta[1]+"\t|\t");
        mensaje.append("Coste de Comisión: " + this.getComision() +"\t|");
        return mensaje.toString();
    }

    public String tipoCuenta() {
        return "Personal";
    }
}
