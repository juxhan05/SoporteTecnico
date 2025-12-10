/*
 * @author Julian Salazar / Mateo Cortes / Alejandro Caro
 * Fecha: 10 / 12 / 2025
 * @version final
 */
package gui;

import logica.SistemaServicios;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana para ver propuestas recibidas por clientes.
 * Permite visualizar y aceptar propuestas.
 */
public class VentanaVerPropuestas extends JFrame {

    private static final long serialVersionUID = 1L;
    private SistemaServicios sistema;
    private JTable tablaPropuestas;
    private DefaultTableModel modeloTabla;
    private JComboBox<Cliente> comboClientes;

    /**
     * Constructor de la ventana para ver propuestas.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaVerPropuestas(SistemaServicios sistema) {
        setIconImage(new ImageIcon(getClass().getResource("/resources/soporte.jpg")).getImage());

        this.sistema = sistema;
        setUndecorated(true);

        setTitle("Propuestas Recibidas");
        setSize(900, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(40, 40, 40));
        getContentPane().setLayout(null);

        JLabel lblTitulo = new JLabel("Propuestas Recibidas por el Cliente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(280, 15, 400, 25);
        getContentPane().add(lblTitulo);

        JLabel lblCliente = new JLabel("Selecciona el Cliente:");
        lblCliente.setForeground(Color.WHITE);
        lblCliente.setBounds(60, 60, 150, 25);
        getContentPane().add(lblCliente);

        comboClientes = new JComboBox<>();
        comboClientes.setBounds(200, 60, 250, 25);
        getContentPane().add(comboClientes);

        JButton btnVer = new JButton("Ver Propuestas");
        btnVer.setBounds(470, 60, 150, 25);
        getContentPane().add(btnVer);

        // Tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{"Solicitud", "Prestador", "Tarifa", "Mensaje", "Estado"}, 0);
        tablaPropuestas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPropuestas);
        scrollPane.setBounds(60, 100, 780, 350);
        getContentPane().add(scrollPane);

        JButton btnAceptar = new JButton("Aceptar Propuesta");
        btnAceptar.setBounds(300, 470, 150, 30);
        getContentPane().add(btnAceptar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(480, 470, 150, 30);
        getContentPane().add(btnVolver);

        cargarClientes();

        // Acción: ver propuestas del cliente seleccionado
        btnVer.addActionListener(e -> cargarPropuestasDelCliente());

        // Acción: aceptar propuesta
        btnAceptar.addActionListener(e -> aceptarPropuestaSeleccionada());

        // Acción: volver
        btnVolver.addActionListener(e -> dispose());
    }

    /**
     * Carga los clientes en el combo box.
     */
    private void cargarClientes() {
        comboClientes.removeAllItems();
        for (Cliente c : sistema.getClientes()) {
            comboClientes.addItem(c);
        }
    }

    /**
     * Carga las propuestas del cliente seleccionado en la tabla.
     */
    private void cargarPropuestasDelCliente() {
        modeloTabla.setRowCount(0);
        Cliente clienteSeleccionado = (Cliente) comboClientes.getSelectedItem();

        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }

        for (Solicitud s : sistema.getSolicitudes()) {
            if (s.getCliente().equals(clienteSeleccionado)) {
                for (Propuesta p : s.getPropuestas()) {
                    modeloTabla.addRow(new Object[]{
                            s.getDescripcion(),
                            p.getPrestador().getNombre(),
                            p.getTarifaOfrecida(),
                            p.getMensaje(),
                            p.getEstado()
                    });
                }
            }
        }

        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Este cliente aún no ha recibido propuestas.");
        }
    }

    /**
     * Acepta la propuesta seleccionada por el cliente.
     */
    private void aceptarPropuestaSeleccionada() {
        int fila = tablaPropuestas.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una propuesta para aceptar.");
            return;
        }

        Cliente cliente = (Cliente) comboClientes.getSelectedItem();
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }

        String descSolicitud = (String) modeloTabla.getValueAt(fila, 0);
        String nombrePrestador = (String) modeloTabla.getValueAt(fila, 1);

        Propuesta propuestaSeleccionada = null;
        Solicitud solicitudAsociada = null;

        for (Solicitud s : sistema.getSolicitudes()) {
            if (s.getCliente().equals(cliente) && s.getDescripcion().equals(descSolicitud)) {
                for (Propuesta p : s.getPropuestas()) {
                    if (p.getPrestador().getNombre().equals(nombrePrestador)) {
                        propuestaSeleccionada = p;
                        solicitudAsociada = s;
                        break;
                    }
                }
            }
        }

        if (propuestaSeleccionada != null && solicitudAsociada != null) {
            OrdenServicio nuevaOrden = sistema.aceptarPropuesta(solicitudAsociada, propuestaSeleccionada);
            sistema.guardarDatos();

            JOptionPane.showMessageDialog(this,
                    "Has aceptado la propuesta de " + nombrePrestador +
                            ".\nSe generó la orden #" + nuevaOrden.getId() + ".");
            cargarPropuestasDelCliente();
        }
    }
}