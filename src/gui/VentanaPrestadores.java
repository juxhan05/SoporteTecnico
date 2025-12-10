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
import modelo.Prestador;

/**
 * Ventana para gestionar prestadores en el sistema.
 * Permite registrar, visualizar y eliminar prestadores.
 */
public class VentanaPrestadores extends JFrame {

    private static final long serialVersionUID = 1L;
    private SistemaServicios sistema;
    private JTable tablaPrestadores;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtCorreo, txtZona, txtEspecialidad;

    /**
     * Constructor de la ventana de gestión de prestadores.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaPrestadores(SistemaServicios sistema) {
        this.sistema = sistema;
        setUndecorated(true);

        setTitle("Gestión de Prestadores");
        setSize(800, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.DARK_GRAY);

        JLabel lblTitulo = new JLabel("Registro de Prestadores");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(270, 20, 300, 25);
        getContentPane().add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(50, 70, 100, 25);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 70, 150, 25);
        getContentPane().add(txtNombre);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(50, 110, 100, 25);
        getContentPane().add(lblCorreo);

        txtCorreo = new JTextField();
        txtCorreo.setBounds(150, 110, 150, 25);
        getContentPane().add(txtCorreo);

        JLabel lblZona = new JLabel("Zona:");
        lblZona.setForeground(Color.WHITE);
        lblZona.setBounds(50, 150, 100, 25);
        getContentPane().add(lblZona);

        txtZona = new JTextField();
        txtZona.setBounds(150, 150, 150, 25);
        getContentPane().add(txtZona);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setForeground(Color.WHITE);
        lblEspecialidad.setBounds(50, 190, 100, 25);
        getContentPane().add(lblEspecialidad);

        txtEspecialidad = new JTextField();
        txtEspecialidad.setBounds(150, 190, 150, 25);
        getContentPane().add(txtEspecialidad);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(50, 240, 120, 30);
        getContentPane().add(btnRegistrar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(180, 240, 120, 30);
        getContentPane().add(btnEliminar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(111, 290, 120, 30);
        getContentPane().add(btnVolver);

        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Correo", "Zona", "Especialidad"}, 0);
        tablaPrestadores = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPrestadores);
        scrollPane.setBounds(350, 70, 400, 320);
        getContentPane().add(scrollPane);

        cargarPrestadoresEnTabla();

        // Registrar
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String zona = txtZona.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();

            if (nombre.isEmpty() || correo.isEmpty() || zona.isEmpty() || especialidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.");
                return;
            }

            Prestador nuevo = sistema.registrarPrestador(nombre, correo, zona, especialidad, 0); // Tarifa = 0, no usada
            sistema.guardarDatos();
            modeloTabla.addRow(new Object[]{nuevo.getNombre(), nuevo.getCorreo(), nuevo.getZona(), nuevo.getEspecialidad()});
            JOptionPane.showMessageDialog(this, "Prestador registrado exitosamente.");
            limpiarCampos();
        });

        // Eliminar
        btnEliminar.addActionListener(e -> {
            int fila = tablaPrestadores.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un prestador para eliminar.");
                return;
            }

            String nombre = (String) modeloTabla.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar al prestador \"" + nombre + "\" y todas sus propuestas u órdenes?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                sistema.eliminarPrestador(nombre);
                modeloTabla.removeRow(fila);
                JOptionPane.showMessageDialog(this, "Prestador eliminado correctamente.");
            }
        });

        btnVolver.addActionListener(e -> dispose());
    }

    /**
     * Limpia los campos de entrada de texto.
     */
    private void limpiarCampos() {
        txtNombre.setText("");
        txtCorreo.setText("");
        txtZona.setText("");
        txtEspecialidad.setText("");
    }

    /**
     * Carga los prestadores del sistema en la tabla.
     */
    private void cargarPrestadoresEnTabla() {
        modeloTabla.setRowCount(0);
        for (Prestador p : sistema.getPrestadores()) {
            modeloTabla.addRow(new Object[]{
                    p.getNombre(), p.getCorreo(), p.getZona(), p.getEspecialidad()
            });
        }
    }
}