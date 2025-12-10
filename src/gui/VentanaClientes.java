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
import modelo.Cliente;

/**
 * Ventana para gestionar clientes en el sistema.
 * Permite registrar, visualizar y eliminar clientes.
 */
public class VentanaClientes extends JFrame {

    private static final long serialVersionUID = 1L;
    private SistemaServicios sistema;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtCorreo, txtZona;

    /**
     * Constructor de la ventana de gestión de clientes.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaClientes(SistemaServicios sistema) {
        setIconImage(new ImageIcon(getClass().getResource("/resources/soporte.jpg")).getImage());

        this.sistema = sistema;
        setUndecorated(true);

        setTitle("Gestión de Clientes");
        setSize(650, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.DARK_GRAY);

        JLabel lblTitulo = new JLabel("Registro de Clientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(230, 20, 250, 25);
        getContentPane().add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(50, 70, 80, 25);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 70, 150, 25);
        getContentPane().add(txtNombre);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(50, 110, 80, 25);
        getContentPane().add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(130, 110, 150, 25);
        getContentPane().add(txtCorreo);

        JLabel lblZona = new JLabel("Zona:");
        lblZona.setForeground(Color.WHITE);
        lblZona.setBounds(50, 150, 80, 25);
        getContentPane().add(lblZona);

        txtZona = new JTextField();
        txtZona.setBounds(130, 150, 150, 25);
        getContentPane().add(txtZona);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(50, 199, 120, 30);
        getContentPane().add(btnRegistrar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(176, 199, 120, 30);
        getContentPane().add(btnEliminar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(115, 240, 120, 30);
        getContentPane().add(btnVolver);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Correo", "Zona"}, 0);
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setBounds(320, 70, 290, 250);
        getContentPane().add(scrollPane);

        cargarClientesEnTabla();

        // Acción Registrar
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String zona = txtZona.getText().trim();

            if (nombre.isEmpty() || correo.isEmpty() || zona.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
                return;
            }

            Cliente nuevo = sistema.registrarCliente(nombre, correo, zona);
            sistema.guardarDatos();
            modeloTabla.addRow(new Object[]{nuevo.getNombre(), nuevo.getCorreo(), nuevo.getZona()});
            JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.");

            txtNombre.setText("");
            txtCorreo.setText("");
            txtZona.setText("");
        });

        // Acción Eliminar
        btnEliminar.addActionListener(e -> {
            int fila = tablaClientes.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.");
                return;
            }

            String nombre = (String) modeloTabla.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar al cliente \"" + nombre + "\" y sus datos relacionados?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                sistema.eliminarCliente(nombre);
                modeloTabla.removeRow(fila);
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
            }
        });

        // Acción Volver
        btnVolver.addActionListener(e -> dispose());
    }

    /**
     * Carga los clientes del sistema en la tabla.
     */
    private void cargarClientesEnTabla() {
        modeloTabla.setRowCount(0);
        for (Cliente c : sistema.getClientes()) {
            modeloTabla.addRow(new Object[]{c.getNombre(), c.getCorreo(), c.getZona()});
        }
    }
}