/*
 * @author ULIAN / ALEJANDRO / MATEO
 * PROGRAMA: PRESTADOR DE SERVICIOS
 * FECHA: 4/12/2025
 * @version 1.0
 */
package modelo;

import java.io.Serializable;

/**
 * Representa una propuesta de servicio enviada por un prestador.
 */
public class Propuesta implements Serializable {
    private static final long serialVersionUID = 1L;
    private Prestador prestador;
    private double tarifaOfrecida;
    private String mensaje;
    private String estado;

    /**
     * Constructor de la clase Propuesta.
     * 
     * @param prestador Prestador que envía la propuesta
     * @param tarifaOfrecida Tarifa ofrecida por el prestador
     * @param mensaje Mensaje adicional de la propuesta
     */
    public Propuesta(Prestador prestador, double tarifaOfrecida, String mensaje) {
        this.prestador = prestador;
        this.tarifaOfrecida = tarifaOfrecida;
        this.mensaje = mensaje;
        this.estado = "Pendiente";
    }

    /**
     * Obtiene el prestador que envió la propuesta.
     * 
     * @return Prestador
     */
    public Prestador getPrestador() {
        return prestador;
    }

    /**
     * Obtiene la tarifa ofrecida en la propuesta.
     * 
     * @return Tarifa ofrecida
     */
    public double getTarifaOfrecida() {
        return tarifaOfrecida;
    }

    /**
     * Obtiene el mensaje de la propuesta.
     * 
     * @return Mensaje de la propuesta
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Obtiene el estado de la propuesta.
     * 
     * @return Estado de la propuesta
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la propuesta.
     * 
     * @param estado Nuevo estado de la propuesta
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Representación en texto de la propuesta.
     * 
     * @return String con información de la propuesta
     */
    @Override
    public String toString() {
        return prestador.getNombre() + " ofrece $" + tarifaOfrecida + "/h - " + mensaje + " [" + estado + "]";
    }
}