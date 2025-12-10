/*
 * @author Julian Salazar / Mateo Cortes / Alejandro Caro
 * Fecha: 10 / 12 / 2025
 * @version final
 */
package gui;

import logica.SistemaServicios;
import modelo.OrdenServicio;
import modelo.Propuesta;
import modelo.Solicitud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Ventana para visualizar y gestionar órdenes de servicio.
 * Muestra todas las órdenes registradas, su estado y permite actualizarlas o calcular costos.
 */
public class VentanaOrdenes extends JFrame {

    private static final long serialVersionUID = 1L;
    private SistemaServicios sistema;
    private JTable tablaOrdenes;
    private DefaultTableModel modeloTabla;

    /**
     * Constructor de la ventana de gestión de órdenes.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaOrdenes(SistemaServicios sistema) {
        setIconImage(new ImageIcon(getClass().getResource("/resources/soporte.jpg")).getImage());

        this.sistema = sistema;
        setUndecorated(true);

        setTitle("Gestión de Órdenes de Servicio");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Encabezado
        JLabel lblTitulo = new JLabel("Órdenes de Servicio Registradas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Cliente", "Prestador", "Descripción", "Estado", "Costo Total"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita edición directa
            }
        };

        tablaOrdenes = new JTable(modeloTabla);
        tablaOrdenes.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tablaOrdenes);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel();
        JButton btnActualizar = new JButton("Actualizar Estado");
        JButton btnCalcularCosto = new JButton("Calcular Costo");
        JButton btnEliminar = new JButton("Eliminar Orden");
        JButton btnRefrescar = new JButton("Refrescar Lista");
        JButton btnVolver = new JButton("Volver");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnCalcularCosto);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarOrdenes();

        // === Eventos ===

        // Actualizar estado
        btnActualizar.addActionListener((ActionEvent e) -> {
            int fila = tablaOrdenes.getSelectedRow();
            if (fila >= 0) {
                OrdenServicio orden = sistema.getOrdenes().get(fila);
                String nuevoEstado = (String) JOptionPane.showInputDialog(
                        this,
                        "Seleccione el nuevo estado:",
                        "Actualizar Estado",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Pendiente", "Confirmado", "En curso", "Finalizado", "Cancelado"},
                        orden.getEstado()
                );

                if (nuevoEstado != null) {
                    orden.setEstado(nuevoEstado);
                    sistema.guardarDatos();
                    cargarOrdenes();
                    JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una orden para actualizar.");
            }
        });

        // Calcular costo
        btnCalcularCosto.addActionListener((ActionEvent e) -> {
            int fila = tablaOrdenes.getSelectedRow();
            if (fila >= 0) {
                OrdenServicio orden = sistema.getOrdenes().get(fila);
                double costo = sistema.calcularCosto(orden);
                JOptionPane.showMessageDialog(this,
                        "El costo total del servicio es: $" + String.format("%.2f", costo));
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una orden para calcular el costo.");
            }
        });

        // Eliminar orden
        btnEliminar.addActionListener(e -> {
            int fila = tablaOrdenes.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una orden para eliminar.");
                return;
            }

            int id = (int) modeloTabla.getValueAt(fila, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar la orden de servicio seleccionada?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                sistema.eliminarOrdenPorId(id);
                sistema.guardarDatos();
                cargarOrdenes();
                JOptionPane.showMessageDialog(this, "Orden eliminada correctamente.");
            }
        });

        // Refrescar lista
        btnRefrescar.addActionListener(e -> cargarOrdenes());

        // Volver
        btnVolver.addActionListener(e -> dispose());
    }

    /**
     * Carga las órdenes actuales en la tabla.
     * Filtra automáticamente las órdenes que ya no tienen cliente o prestador válido.
     */
    private void cargarOrdenes() {
        modeloTabla.setRowCount(0);

        for (OrdenServicio o : sistema.getOrdenes()) {
            Solicitud s = o.getSolicitud();
            Propuesta p = o.getPropuesta();

            // Evita mostrar órdenes inválidas
            if (s == null || s.getCliente() == null || p == null || p.getPrestador() == null) {
                continue;
            }

            double costo = sistema.calcularCosto(o);

            modeloTabla.addRow(new Object[]{
                    o.getId(),
                    s.getCliente().getNombre(),
                    p.getPrestador().getNombre(),
                    s.getDescripcion(),
                    o.getEstado(),
                    String.format("$%.2f", costo)
            });
        }
    }
}