package Esquema;

public class Empresa {
    private Persona [] propietarios;
    private String nombreEmp;

    //Constructor
    public Empresa(String nombreEmp, Persona[] propietarios) {
        this.propietarios = propietarios;
        this.nombreEmp = nombreEmp;
    }

    //Getters y Setters


    public Persona[] getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(Persona[] propietarios) {
        this.propietarios = propietarios;
    }

    public String getNombreEmp() {
        return nombreEmp;
    }

    public void setNombreEmp(String nombreEmp) {
        this.nombreEmp = nombreEmp;
    }
}
