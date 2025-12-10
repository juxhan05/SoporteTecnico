/*
 * @author ULIAN / ALEJANDRO / MATEO
 * PROGRAMA: PRESTADOR DE SERVICIOS
 * FECHA: 10/12/2025
 * @version final
 */
package principal;

import logica.SistemaServicios;
import gui.VentanaBienvenida;

/**
 * Clase principal que inicia la aplicación.
 */
public class Main {
    
    /**
     * Método principal de la aplicación.
     * Carga los datos del sistema y muestra la ventana de bienvenida.
     * 
     * @param args Argumentos de línea de comandos 
     */
    public static void main(String[] args) {
        SistemaServicios sistema = SistemaServicios.cargarDatos();
        new VentanaBienvenida(sistema).setVisible(true);
    }
}