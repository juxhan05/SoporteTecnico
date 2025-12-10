/*
 * @author Julian Salazar / Mateo Cortes / Alejandro Caro
 * Fecha: 10 / 12 / 2025
 * @version final
 */
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import logica.SistemaServicios;
import modelo.*;

/**
 * Ventana para gestionar solicitudes en el sistema.
 * Permite crear y visualizar solicitudes de servicio.
 */
public class VentanaSolicitudes extends JFrame {

    private static final long serialVersionUID = 1L;
    private SistemaServicios sistema;
    private JTable tablaSolicitudes;
    private DefaultTableModel modeloTabla;
    private JComboBox<Cliente> comboClientes;
    private JTextField txtDescripcion, txtHoras;

    /**
     * Constructor de la ventana de gestión de solicitudes.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaSolicitudes(SistemaServicios sistema) {
        this.sistema = sistema;
        setUndecorated(true);

        setTitle("Gestión de Solicitudes");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(40, 40, 40));

        JLabel lblTitulo = new JLabel("Registro de Solicitudes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(280, 20, 300, 25);
        getContentPane().add(lblTitulo);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setForeground(Color.WHITE);
        lblCliente.setBounds(50, 70, 100, 25);
        getContentPane().add(lblCliente);

        comboClientes = new JComboBox<>();
        comboClientes.setBounds(150, 70, 200, 25);
        getContentPane().add(comboClientes);

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setForeground(Color.WHITE);
        lblDescripcion.setBounds(50, 110, 100, 25);
        getContentPane().add(lblDescripcion);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(150, 110, 200, 25);
        getContentPane().add(txtDescripcion);

        JLabel lblHoras = new JLabel("Horas estimadas:");
        lblHoras.setForeground(Color.WHITE);
        lblHoras.setBounds(50, 150, 120, 25);
        getContentPane().add(lblHoras);

        txtHoras = new JTextField();
        txtHoras.setBounds(150, 150, 200, 25);
        getContentPane().add(txtHoras);

        JButton btnRegistrar = new JButton("Crear Solicitud");
        btnRegistrar.setBounds(50, 227, 150, 30);
        getContentPane().add(btnRegistrar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(213, 227, 150, 30);
        getContentPane().add(btnVolver);

        // Tabla para mostrar solicitudes
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Cliente", "Descripción", "Horas"}, 0);
        tablaSolicitudes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaSolicitudes);
        scrollPane.setBounds(400, 70, 360, 300);
        getContentPane().add(scrollPane);

        cargarClientes();
        cargarSolicitudes();

        // Acción Registrar
        btnRegistrar.addActionListener(e -> {
            Cliente clienteSeleccionado = (Cliente) comboClientes.getSelectedItem();
            String descripcion = txtDescripcion.getText().trim();
            String horasTexto = txtHoras.getText().trim();

            if (clienteSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Debe haber al menos un cliente registrado.");
                return;
            }

            if (descripcion.isEmpty() || horasTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            try {
                int horas = Integer.parseInt(horasTexto);
                Solicitud nueva = sistema.crearSolicitud(clienteSeleccionado, descripcion, horas);
                sistema.guardarDatos();

                modeloTabla.addRow(new Object[]{
                        nueva.getId(),
                        clienteSeleccionado.getNombre(),
                        nueva.getDescripcion(),
                        nueva.getHorasEstimadas()
                });

                JOptionPane.showMessageDialog(this, "Solicitud registrada correctamente.");
                limpiarCampos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Las horas deben ser un número entero.");
            }
        });

        // Acción Volver
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
     * Carga las solicitudes del sistema en la tabla.
     */
    private void cargarSolicitudes() {
        modeloTabla.setRowCount(0);
        for (Solicitud s : sistema.getSolicitudes()) {
            modeloTabla.addRow(new Object[]{
                    s.getId(),
                    s.getCliente().getNombre(),
                    s.getDescripcion(),
                    s.getHorasEstimadas()
            });
        }
    }

    /**
     * Limpia los campos de entrada de texto.
     */
    private void limpiarCampos() {
        txtDescripcion.setText("");
        txtHoras.setText("");
    }
}