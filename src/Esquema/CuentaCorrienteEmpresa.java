package Esquema;

import Interfaces.*;

public class CuentaCorrienteEmpresa extends CuentaBancaria implements Imprimible, ClaseCuenta {
    Empresa empresa;
    double maxDescubierto, interesDescubierto;


    //Constructor

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

    @Override
    public String devolverInfoString() {
    //"|\tIBAN\t|\tPROPIETARIO\t|\tSALDO\t|\tDETALLES\t|"
        String[] cuenta = super.devolverInfoString().split(",");
        StringBuffer mensaje = new StringBuffer("|\t");
        mensaje.append(cuenta[0]+"\t|\t");
        mensaje.append(empresa.getNombreEmp()+"\t|\t");
        mensaje.append(cuenta[1]+"\t|\t");
        mensaje.append("Max Descubierto: " + maxDescubierto + " , Interes Descubierto: " + interesDescubierto + "\t|");
        return mensaje.toString();
    }

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

    public String tipoCuenta() {
        return "Empresa";
    }
}

