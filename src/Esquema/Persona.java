/**
 * @author Cristyan Morales Acevedo
 * @desc Clase persona, almacena los datos de cada cliente y/o propietario de empresa
 */
package Esquema;

public class Persona {
    private String nombre;
    private String apellido;
    private String dni;

    /**
     * Constructor
     * @param nombre del titular o titulares
     * @param apellido del titular o titulares
     * @param dni del titular o titulares
     */
    public Persona(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
