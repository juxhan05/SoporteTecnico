/*
 * @author ULIAN / ALEJANDRO / MATEO
 * PROGRAMA: PRESTADOR DE SERVICIOS
 * FECHA: 4/12/2025
 * @version 1.0
 */
package modelo;

import java.io.Serializable;

/**
 * Orden generada cuando un cliente acepta una propuesta.
 */
public class OrdenServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contador = 1;
    private int id;
    private Solicitud solicitud;
    private Propuesta propuesta;
    private String estado;

    /**
     * Constructor de la clase OrdenServicio.
     * 
     * @param solicitud Solicitud asociada a la orden
     * @param propuesta Propuesta aceptada para la orden
     */
    public OrdenServicio(Solicitud solicitud, Propuesta propuesta) {
        this.id = contador++;
        this.solicitud = solicitud;
        this.propuesta = propuesta;
        this.estado = "Pendiente";
    }

    /**
     * Obtiene el ID de la orden.
     * 
     * @return ID de la orden
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la solicitud asociada.
     * 
     * @return Solicitud asociada
     */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /**
     * Obtiene la propuesta asociada.
     * 
     * @return Propuesta asociada
     */
    public Propuesta getPropuesta() {
        return propuesta;
    }

    /**
     * Obtiene el estado de la orden.
     * 
     * @return Estado de la orden
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la orden.
     * 
     * @param estado Nuevo estado de la orden
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Calcula el costo total de la orden incluyendo comisión.
     * 
     * @param comision Porcentaje de comisión (0.0 a 1.0)
     * @return Costo total
     */
    public double calcularCostoTotal(double comision) { // Abstracción
        double base = solicitud.getHorasEstimadas() * propuesta.getTarifaOfrecida();
        return base + (base * comision);
    }

    /**
     * Representación en texto de la orden.
     * 
     * @return String con información de la orden
     */
    @Override
    public String toString() {
        return "Orden #" + id + " [" + estado + "] - " + solicitud.getDescripcion();
    }
}