/*
 * @author ULIAN / ALEJANDRO / MATEO
 * PROGRAMA: PRESTADOR DE SERVICIOS
 * FECHA: 4/12/2025
 * @version 1.0
 */
package modelo;

import java.io.Serializable;

/**
 * Clase base abstracta para representar una persona del sistema.
 */
public abstract class Persona implements Serializable { // Herencia
    private static final long serialVersionUID = 1L;
    protected String nombre;
    protected String correo;
    protected String zona;

    /**
     * Constructor de la clase Persona.
     * 
     * @param nombre Nombre de la persona
     * @param correo Correo electrónico de la persona
     * @param zona Zona de la persona
     */
    public Persona(String nombre, String correo, String zona) {
        this.nombre = nombre;
        this.correo = correo;
        this.zona = zona;
    }


    /**
     * Obtiene el nombre de la persona.
     * 
     * @return Nombre de la persona
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el correo electrónico de la persona.
     * 
     * @return Correo electrónico de la persona
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Obtiene la zona de la persona.
     * 
     * @return Zona de la persona
     */
    public String getZona() {
        return zona;
    }

    /**
     * Método abstracto para obtener el rol de la persona.
     * Se redefine en las subclases.
     * 
     * @return Rol de la persona
     */
    public abstract String getRol();

    /**
     * Representación en texto de la persona.
     * 
     * @return String con información de la persona
     */
    @Override
    public String toString() {
        return getRol() + ": " + nombre + " (" + correo + "), Zona: " + zona;
    }
}