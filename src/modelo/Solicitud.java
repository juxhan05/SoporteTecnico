/*
 * @author ULIAN / ALEJANDRO / MATEO
 * PROGRAMA: PRESTADOR DE SERVICIOS
 * FECHA: 4/12/2025
 * @version 1.0
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una solicitud de servicio realizada por un cliente.
 */
public class Solicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contador = 1;
    private int id;
    private Cliente cliente;
    private String descripcion;
    private int horasEstimadas;
    private List<Propuesta> propuestas;

    /**
     * Constructor de la clase Solicitud.
     * 
     * @param cliente Cliente que realiza la solicitud
     * @param descripcion Descripción del servicio solicitado
     * @param horasEstimadas Horas estimadas para el servicio
     */
    public Solicitud(Cliente cliente, String descripcion, int horasEstimadas) {
        this.id = contador++;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.horasEstimadas = horasEstimadas;
        this.propuestas = new ArrayList<>();
    }

    /**
     * Obtiene el ID de la solicitud.
     * 
     * @return ID de la solicitud
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el cliente que realizó la solicitud.
     * 
     * @return Cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Obtiene la descripción de la solicitud.
     * 
     * @return Descripción del servicio
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene las horas estimadas para el servicio.
     * 
     * @return Horas estimadas
     */
    public int getHorasEstimadas() {
        return horasEstimadas;
    }

    /**
     * Obtiene la lista de propuestas para esta solicitud.
     * 
     * @return Lista de propuestas
     */
    public List<Propuesta> getPropuestas() {
        return propuestas;
    }

    /**
     * Agrega una propuesta a la solicitud.
     * 
     * @param p Propuesta a agregar
     */
    public void agregarPropuesta(Propuesta p) {
        propuestas.add(p);
    }

    /**
     * Representación en texto de la solicitud.
     * 
     * @return String con información de la solicitud
     */
    @Override
    public String toString() {
        return "Solicitud #" + id + ": " + descripcion + " (" + horasEstimadas + "h)";
    }
}