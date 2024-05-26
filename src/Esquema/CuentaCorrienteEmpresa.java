package Esquema;

import java.io.Serializable;

import Interfaces.*;

public class CuentaCorrienteEmpresa extends CuentaBancaria implements Imprimible, Serializable {
    Empresa empresa;
    double maxDescubierto, interesDescubierto;

    /**
     * Constructor
     * @param nombreEmp Nombre de la empresa que va a estar asociada a la cuenta
     * @param propietarios de la empresa
     * @param iban asociado a la empresa
     * @param saldo inicial con el que empieza la cuenta
     * @param maxDescubierto Máximo en descubierto con el que inicia la cuenta
     * @param interesDescubierto El interés por descubierto con el que inicia la cuenta
     */
    public CuentaCorrienteEmpresa(String nombreEmp, Persona[] propietarios, String iban, double saldo, double maxDescubierto, double interesDescubierto) {
        super(iban, saldo);
        this.maxDescubierto = maxDescubierto;
        this.interesDescubierto = interesDescubierto;
        this.empresa = new Empresa(nombreEmp, propietarios);
    }

    //Getters y Setters

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public double getMaxDescubierto() {
        return maxDescubierto;
    }

    public void setMaxDescubierto(double maxDescubierto) {
        this.maxDescubierto = maxDescubierto;
    }

    public double comisionDescubierto() {
        return interesDescubierto;
    }

    public void setInteresDescubierto(double interesDescubierto) {
        this.interesDescubierto = interesDescubierto;
    }

    /**
     * Método que permite que se entregue los datos a los usuarios, cumple con el formato del método cabecera() de la clase Principal
     * @return El dato ya construido.
     */
    @Override
    public String devolverInfoString() {
    //"|\tIBAN\t|\tPROPIETARIO\t|\tSALDO\t|\tDETALLES\t|"
        String[] cuenta = super.devolverInfoString().split(",");
        StringBuilder mensaje = new StringBuilder("|\t");
        mensaje.append(cuenta[0]+"\t|\t");
        mensaje.append("Empresa: "+empresa.getNombreEmp()+"\t|\t");
        mensaje.append(cuenta[1]+"\t|\t");
        mensaje.append("Max Descubierto: " + maxDescubierto + " , Interés por Descubierto: " + interesDescubierto + "\t|");
        return mensaje.toString();
    }

    /**
     * Método disponible para futuros usos, devuelve el nombre de los propietarios de la empresa creada
     * @return la lista de propietarios
     */
    public String devolverPropietarios(){
        StringBuffer mensaje = new StringBuffer("Lista de propietarios:\n");
        int contador=1;
        for(Persona propietario: empresa.getPropietarios()) {
            mensaje.append(contador+". "+propietario.getNombre() +" "+ propietario.getApellido() +"\n");
            mensaje.append("DNI: "+propietario.getDni()+"\n");
            contador++;
        }
        return mensaje.toString();
    }

}

