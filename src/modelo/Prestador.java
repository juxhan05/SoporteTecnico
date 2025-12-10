/*
 * @author ULIAN / ALEJANDRO / MATEO
 * PROGRAMA: PRESTADOR DE SERVICIOS
 * FECHA: 4/12/2025
 * @version 1.0
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un prestador de servicios.
 * Hereda de la clase Persona.
 */
public class Prestador extends Persona { // Herencia
    private static final long serialVersionUID = 1L;
    private String especialidad;
    private double tarifaPorHora;
    private List<String> disponibilidad;

    /**
     * Constructor de la clase Prestador.
     * 
     * @param nombre Nombre del prestador
     * @param correo Correo electrónico del prestador
     * @param zona Zona del prestador
     * @param especialidad Especialidad del prestador
     * @param tarifaPorHora Tarifa por hora del prestador
     */
    public Prestador(String nombre, String correo, String zona, String especialidad, double tarifaPorHora) {
        super(nombre, correo, zona);
        this.especialidad = especialidad;
        this.tarifaPorHora = tarifaPorHora;
        this.disponibilidad = new ArrayList<>();
    }

    /**
     * Obtiene la especialidad del prestador.
     * 
     * @return Especialidad del prestador
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Obtiene la tarifa por hora del prestador.
     * 
     * @return Tarifa por hora
     */
    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    /**
     * Obtiene la lista de disponibilidad del prestador.
     * 
     * @return Lista de franjas de disponibilidad
     */
    public List<String> getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Agrega una franja de disponibilidad al prestador.
     * 
     * @param franja Franja de disponibilidad a agregar
     */
    public void agregarDisponibilidad(String franja) {
        disponibilidad.add(franja);
    }

    /**
     * Obtiene el rol del prestador.
     * 
     * @return "Prestador"
     */
    @Override
    public String getRol() { // Polimorfismo
        return "Prestador";
    }

    /**
     * Representación en texto del prestador.
     * 
     * @return String con información del prestador
     */
    @Override
    public String toString() {
        return super.toString() + " | Especialidad: " + especialidad + " | Tarifa: $" + tarifaPorHora + "/h";
    }
}