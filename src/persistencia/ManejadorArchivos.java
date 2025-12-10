/*
 * @author ULIAN / ALEJANDRO / MATEO
 * PROGRAMA: PRESTADOR DE SERVICIOS
 * FECHA: 10/12/2025
 * @version final
 */
package persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Clase para manejar la persistencia de datos mediante serialización.
 */
public class ManejadorArchivos {

    /**
     * Guarda un objeto en un archivo mediante serialización.
     * 
     * @param objeto Objeto a guardar
     * @param nombreArchivo Nombre del archivo donde se guardará
     */
    public static void guardar(Object objeto, String nombreArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(objeto);
            System.out.println("Datos guardados en " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar archivo: " + e.getMessage());
        }
    }

    /**
     * Carga un objeto desde un archivo mediante deserialización.
     * 
     * @param nombreArchivo Nombre del archivo a cargar
     * @return Objeto cargado o null si hay error
     */
    public static Object cargar(String nombreArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado, se creará nuevo al guardar.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar archivo: " + e.getMessage());
        }
        return null;
    }
}