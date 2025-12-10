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
 * Ventana para gestionar propuestas en el sistema.
 * Permite enviar, visualizar y eliminar propuestas.
 */
public class VentanaPropuestas extends JFrame {

    private static final long serialVersionUID = 1L;
    private SistemaServicios sistema;
    private JTable tablaPropuestas;
    private DefaultTableModel modeloTabla;
    private JComboBox<Solicitud> comboSolicitudes;
    private JComboBox<Prestador> comboPrestadores;
    private JTextField txtTarifa, txtMensaje;

    /**
     * Constructor de la ventana de gestión de propuestas.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaPropuestas(SistemaServicios sistema) {
        setIconImage(new ImageIcon(getClass().getResource("/resources/soporte.jpg")).getImage());

        this.sistema = sistema;
        setUndecorated(true);

        setTitle("Gestión de Propuestas");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(40, 40, 40));
        getContentPane().setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Propuestas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(340, 20, 300, 25);
        getContentPane().add(lblTitulo);

        JLabel lblSolicitud = new JLabel("Solicitud:");
        lblSolicitud.setForeground(Color.WHITE);
        lblSolicitud.setBounds(40, 70, 100, 25);
        getContentPane().add(lblSolicitud);

        comboSolicitudes = new JComboBox<>();
        comboSolicitudes.setBounds(130, 70, 250, 25);
        getContentPane().add(comboSolicitudes);

        JLabel lblPrestador = new JLabel("Prestador:");
        lblPrestador.setForeground(Color.WHITE);
        lblPrestador.setBounds(40, 110, 100, 25);
        getContentPane().add(lblPrestador);

        comboPrestadores = new JComboBox<>();
        comboPrestadores.setBounds(130, 110, 250, 25);
        getContentPane().add(comboPrestadores);

        JLabel lblTarifa = new JLabel("Tarifa ($/h):");
        lblTarifa.setForeground(Color.WHITE);
        lblTarifa.setBounds(40, 150, 100, 25);
        getContentPane().add(lblTarifa);

        txtTarifa = new JTextField();
        txtTarifa.setBounds(130, 150, 250, 25);
        getContentPane().add(txtTarifa);

        JLabel lblMensaje = new JLabel("Mensaje:");
        lblMensaje.setForeground(Color.WHITE);
        lblMensaje.setBounds(40, 190, 100, 25);
        getContentPane().add(lblMensaje);

        txtMensaje = new JTextField();
        txtMensaje.setBounds(130, 190, 250, 25);
        getContentPane().add(txtMensaje);

        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.setBounds(130, 240, 120, 30);
        getContentPane().add(btnEnviar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(260, 240, 120, 30);
        getContentPane().add(btnEliminar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(190, 280, 120, 30);
        getContentPane().add(btnVolver);

        modeloTabla = new DefaultTableModel(
                new Object[]{"Solicitud", "Prestador", "Tarifa", "Mensaje", "Estado"}, 0);
        tablaPropuestas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPropuestas);
        scrollPane.setBounds(420, 70, 450, 330);
        getContentPane().add(scrollPane);

        cargarCombos();
        cargarPropuestasEnTabla();

        btnEnviar.addActionListener(e -> {
            Solicitud solicitud = (Solicitud) comboSolicitudes.getSelectedItem();
            Prestador prestador = (Prestador) comboPrestadores.getSelectedItem();
            String tarifaTxt = txtTarifa.getText().trim();
            String mensaje = txtMensaje.getText().trim();

            if (solicitud == null || prestador == null) {
                JOptionPane.showMessageDialog(this, "Seleccione solicitud y prestador.");
                return;
            }

            if (tarifaTxt.isEmpty() || mensaje.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                return;
            }

            try {
                double tarifa = Double.parseDouble(tarifaTxt);
                Propuesta nueva = sistema.enviarPropuesta(solicitud, prestador, tarifa, mensaje);
                sistema.guardarDatos();
                modeloTabla.addRow(new Object[]{
                        solicitud.getDescripcion(),
                        prestador.getNombre(),
                        tarifa,
                        mensaje,
                        nueva.getEstado()
                });
                JOptionPane.showMessageDialog(this, "Propuesta enviada correctamente.");
                txtTarifa.setText("");
                txtMensaje.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tarifa inválida.");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaPropuestas.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una propuesta para eliminar.");
                return;
            }

            String solicitudDesc = (String) modeloTabla.getValueAt(fila, 0);
            String nombrePrestador = (String) modeloTabla.getValueAt(fila, 1);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar propuesta de " + nombrePrestador + " para \"" + solicitudDesc + "\"?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                sistema.eliminarPropuesta(solicitudDesc, nombrePrestador);
                modeloTabla.removeRow(fila);
                JOptionPane.showMessageDialog(this, "Propuesta eliminada correctamente.");
            }
        });

        btnVolver.addActionListener(e -> dispose());
    }

    /**
     * Carga las solicitudes y prestadores en los combobox.
     */
    private void cargarCombos() {
        comboSolicitudes.removeAllItems();
        comboPrestadores.removeAllItems();
        for (Solicitud s : sistema.getSolicitudes()) comboSolicitudes.addItem(s);
        for (Prestador p : sistema.getPrestadores()) comboPrestadores.addItem(p);
    }

    /**
     * Carga las propuestas del sistema en la tabla.
     */
    private void cargarPropuestasEnTabla() {
        modeloTabla.setRowCount(0);
        for (Solicitud s : sistema.getSolicitudes()) {
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
}