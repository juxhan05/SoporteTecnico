/*
 * @author ULIAN / ALEJANDRO / MATEO
 * PROGRAMA: PRESTADOR DE SERVICIOS
 * FECHA: 4/12/2025
 * @version 1.0
 */
package modelo;

/**
 * Representa a un cliente del sistema.
 * Hereda de la clase Persona.
 */
public class Cliente extends Persona { // Herencia
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la clase Cliente.
     * 
     * @param nombre Nombre del cliente
     * @param correo Correo electrónico del cliente
     * @param zona Zona del cliente
     */
    public Cliente(String nombre, String correo, String zona) {
        super(nombre, correo, zona);
    }

    /**
     * Obtiene el rol del cliente.
     * 
     * @return "Cliente"
     */
    @Override
    public String getRol() { // Polimorfismo
        return "Cliente";
    }
}