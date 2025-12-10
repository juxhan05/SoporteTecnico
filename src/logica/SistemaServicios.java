/*
 * @author ULIAN / ALEJANDRO / MATEO
 * PROGRAMA: PRESTADOR DE SERVICIOS
 * FECHA: 10/12/2025
 * @version final
 */
package logica;

import modelo.*;
import persistencia.ManejadorArchivos;
import java.util.*;

/**
 * Clase principal de lógica del sistema.
 * Gestiona clientes, prestadores, solicitudes, propuestas y órdenes.
 */
public class SistemaServicios implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private List<Cliente> clientes = new ArrayList<>();
    private List<Prestador> prestadores = new ArrayList<>();
    private List<Solicitud> solicitudes = new ArrayList<>();
    private List<OrdenServicio> ordenes = new ArrayList<>();

    private static final double COMISION = 0.10;

    // ====== GETTERS ======
    
    /**
     * Obtiene la lista de clientes.
     * 
     * @return Lista de clientes
     */
    public List<Cliente> getClientes() { return clientes; }
    
    /**
     * Obtiene la lista de prestadores.
     * 
     * @return Lista de prestadores
     */
    public List<Prestador> getPrestadores() { return prestadores; }
    
    /**
     * Obtiene la lista de solicitudes.
     * 
     * @return Lista de solicitudes
     */
    public List<Solicitud> getSolicitudes() { return solicitudes; }
    
    /**
     * Obtiene la lista de órdenes de servicio.
     * 
     * @return Lista de órdenes
     */
    public List<OrdenServicio> getOrdenes() { return ordenes; }

    /**
     * Registra un nuevo cliente en el sistema.
     * 
     * @param nombre Nombre del cliente
     * @param correo Correo electrónico del cliente
     * @param zona Zona del cliente
     * @return El cliente registrado
     */
    public Cliente registrarCliente(String nombre, String correo, String zona) {
        Cliente c = new Cliente(nombre, correo, zona);
        clientes.add(c);
        return c;
    }

    /**
     * Registra un nuevo prestador en el sistema.
     * 
     * @param nombre Nombre del prestador
     * @param correo Correo electrónico del prestador
     * @param zona Zona del prestador
     * @param especialidad Especialidad del prestador
     * @param tarifa Tarifa por hora del prestador
     * @return El prestador registrado
     */
    public Prestador registrarPrestador(String nombre, String correo, String zona, String especialidad, double tarifa) {
        Prestador p = new Prestador(nombre, correo, zona, especialidad, tarifa);
        prestadores.add(p);
        return p;
    }
    /**
     * Crea una nueva solicitud de servicio.
     * 
     * @param c Cliente que realiza la solicitud
     * @param descripcion Descripción del servicio solicitado
     * @param horas Horas estimadas para el servicio
     * @return La solicitud creada
     */
    public Solicitud crearSolicitud(Cliente c, String descripcion, int horas) {
        Solicitud s = new Solicitud(c, descripcion, horas);
        solicitudes.add(s);
        return s;
    }

    /**
     * Envía una propuesta para una solicitud.
     * 
     * @param s Solicitud a la que se envía la propuesta
     * @param p Prestador que envía la propuesta
     * @param tarifa Tarifa ofrecida por el prestador
     * @param mensaje Mensaje adicional de la propuesta
     * @return La propuesta enviada
     */
    public Propuesta enviarPropuesta(Solicitud s, Prestador p, double tarifa, String mensaje) {
        Propuesta prop = new Propuesta(p, tarifa, mensaje);
        s.agregarPropuesta(prop);
        return prop;
    }

    /**
     * Acepta una propuesta y genera una orden de servicio.
     * 
     * @param s Solicitud asociada
     * @param p Propuesta aceptada
     * @return La orden de servicio generada
     */
    public OrdenServicio aceptarPropuesta(Solicitud s, Propuesta p) {
        p.setEstado("Aceptada");
        OrdenServicio o = new OrdenServicio(s, p);
        ordenes.add(o);
        return o;
    }

    /**
     * Calcula el costo total de una orden de servicio.
     * 
     * @param o Orden de servicio
     * @return Costo total con comisión
     */
    public double calcularCosto(OrdenServicio o) {
        return o.calcularCostoTotal(COMISION);
    }
    
    /**
     * Elimina una orden de servicio por su ID.
     * 
     * @param id ID de la orden a eliminar
     */
    public void eliminarOrdenPorId(int id) {
        ordenes.removeIf(o -> o.getId() == id);
    }

    /**
     * Elimina un cliente y todo lo relacionado (solicitudes y órdenes).
     * 
     * @param nombre Nombre del cliente a eliminar
     */
    public void eliminarCliente(String nombre) {
        // Eliminar solicitudes y órdenes relacionadas
        solicitudes.removeIf(s -> s.getCliente().getNombre().equalsIgnoreCase(nombre));
        ordenes.removeIf(o -> o.getSolicitud().getCliente().getNombre().equalsIgnoreCase(nombre));

        // Eliminar cliente
        clientes.removeIf(c -> c.getNombre().equalsIgnoreCase(nombre));

        guardarDatos();
    }

    /**
     * Elimina un prestador y todo lo relacionado (propuestas y órdenes).
     * 
     * @param nombre Nombre del prestador a eliminar
     */
    public void eliminarPrestador(String nombre) {
        // Eliminar propuestas del prestador
        for (Solicitud s : solicitudes) {
            s.getPropuestas().removeIf(p -> p.getPrestador().getNombre().equalsIgnoreCase(nombre));
        }

        // Eliminar órdenes relacionadas
        ordenes.removeIf(o -> o.getPropuesta().getPrestador().getNombre().equalsIgnoreCase(nombre));

        // Eliminar prestador
        prestadores.removeIf(p -> p.getNombre().equalsIgnoreCase(nombre));

        guardarDatos();
    }

    /**
     * Elimina una propuesta específica y cualquier orden asociada.
     * 
     * @param descripcionSolicitud Descripción de la solicitud
     * @param nombrePrestador Nombre del prestador
     */
    public void eliminarPropuesta(String descripcionSolicitud, String nombrePrestador) {
        for (Solicitud s : solicitudes) {
            if (s.getDescripcion().equalsIgnoreCase(descripcionSolicitud)) {
                s.getPropuestas().removeIf(p ->
                        p.getPrestador().getNombre().equalsIgnoreCase(nombrePrestador));
            }
        }

        ordenes.removeIf(o ->
                o.getSolicitud().getDescripcion().equalsIgnoreCase(descripcionSolicitud)
                        && o.getPropuesta().getPrestador().getNombre().equalsIgnoreCase(nombrePrestador));

        guardarDatos();
    }

    /**
     * Guarda los datos del sistema en archivo.
     */
    public void guardarDatos() {
        ManejadorArchivos.guardar(this, "servicios.dat");
    }

    /**
     * Carga los datos del sistema desde archivo.
     * 
     * @return Instancia del sistema con datos cargados
     */
    public static SistemaServicios cargarDatos() {
        Object obj = ManejadorArchivos.cargar("servicios.dat");
        return (obj instanceof SistemaServicios) ? (SistemaServicios) obj : new SistemaServicios();
    }
}