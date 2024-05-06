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
        StringBuffer mensaje = new StringBuffer("Nombre Empresa: " + empresa.getNombreEmp() + "\n");
        for (Persona persona : empresa.getPropietarios())
            mensaje.append("\tPropietario: " + persona.getNombre() + "\n");
        mensaje.append(super.devolverInfoString());
        mensaje.append("Max Descubierto: " + maxDescubierto + "\n");
        mensaje.append("Interes Descubierto: " + interesDescubierto + "\n");
        return mensaje.toString();
    }

    public String tipoCuenta() {
        return "Empresa";
    }
}

