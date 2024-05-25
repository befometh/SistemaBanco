/**
 * @author Cristyan Morales Acevedo
 * @desc Cuenta Empresa, almacena los datos de la empresa que posee la cuenta en cuestión
 */
package Esquema;

public class Empresa {
    private Persona [] propietarios;
    private String nombreEmp;

    /**
     * Constructor
     * @param nombreEmp nombre de la empresa
     * @param propietarios lista de propietarios que tiene la empresa
     */
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
