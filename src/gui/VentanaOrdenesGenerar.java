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
 * Ventana para aceptar propuestas y generar órdenes de servicio.
 */
public class VentanaOrdenesGenerar extends JFrame {

    private static final long serialVersionUID = 1L;
    private SistemaServicios sistema;
    private JTable tablaPropuestas;
    private DefaultTableModel modeloTabla;

    /**
     * Constructor de la ventana para generar órdenes.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaOrdenesGenerar(SistemaServicios sistema) {
        setIconImage(new ImageIcon(getClass().getResource("/resources/soporte.jpg")).getImage());

        this.sistema = sistema;
        setUndecorated(true);

        setTitle("Aceptar Propuestas y Generar Órdenes");
        setSize(850, 515);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblTitulo = new JLabel("Propuestas Pendientes de Aceptación", SwingConstants.CENTER);
        lblTitulo.setBounds(0, 0, 834, 33);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        getContentPane().add(lblTitulo);

        modeloTabla = new DefaultTableModel(
            new Object[]{"Solicitud", "Cliente", "Prestador", "Tarifa", "Mensaje", "Estado"}, 0);
        tablaPropuestas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPropuestas);
        scrollPane.setBounds(0, 34, 834, 415);
        getContentPane().add(scrollPane);

        JPanel panelBotones = new JPanel();
        panelBotones.setBounds(0, 449, 834, 33);
        JButton btnAceptar = new JButton("Aceptar Propuesta");
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnVolver);
        getContentPane().add(panelBotones);

        cargarPropuestas();

        btnAceptar.addActionListener(e -> {
            int fila = tablaPropuestas.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una propuesta para aceptar.");
                return;
            }

            // Buscar la propuesta seleccionada
            String descSolicitud = (String) modeloTabla.getValueAt(fila, 0);
            Propuesta propuestaSeleccionada = null;
            Solicitud solicitudAsociada = null;

            for (Solicitud s : sistema.getSolicitudes()) {
                if (s.getDescripcion().equals(descSolicitud)) {
                    for (Propuesta p : s.getPropuestas()) {
                        if (p.getPrestador().getNombre().equals(modeloTabla.getValueAt(fila, 2))) {
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
                        "Propuesta aceptada.\nSe generó la orden #" + nuevaOrden.getId() +
                                " para el cliente " + solicitudAsociada.getCliente().getNombre() + ".");
                cargarPropuestas();
            }
        });

        btnVolver.addActionListener(e -> dispose());
    }

    /**
     * Carga las propuestas pendientes en la tabla.
     */
    private void cargarPropuestas() {
        modeloTabla.setRowCount(0);
        for (Solicitud s : sistema.getSolicitudes()) {
            for (Propuesta p : s.getPropuestas()) {
                if (p.getEstado().equalsIgnoreCase("Pendiente")) {
                    modeloTabla.addRow(new Object[]{
                            s.getDescripcion(),
                            s.getCliente().getNombre(),
                            p.getPrestador().getNombre(),
                            p.getTarifaOfrecida(),
                            p.getMensaje(),
                            p.getEstado()
                    });
                }
            }
        }
    }
}