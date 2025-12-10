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
 * Ventana que permite al prestador ver únicamente sus propias propuestas.
 */
public class VentanaMisPropuestas extends JFrame {

    private static final long serialVersionUID = 1L;
    private SistemaServicios sistema;
    private JComboBox<Prestador> comboPrestadores;
    private JTable tablaPropuestas;
    private DefaultTableModel modeloTabla;

    /**
     * Constructor de la ventana de mis propuestas.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaMisPropuestas(SistemaServicios sistema) {
        setIconImage(new ImageIcon(getClass().getResource("/resources/soporte.jpg")).getImage());

        this.sistema = sistema;

        setUndecorated(true);
        setTitle("Ver Mis Propuestas");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(40, 40, 40));
        getContentPane().setLayout(null);

        JLabel lblTitulo = new JLabel("Consulta de Propuestas Enviadas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(230, 20, 400, 25);
        getContentPane().add(lblTitulo);

        JLabel lblPrestador = new JLabel("Selecciona tu nombre:");
        lblPrestador.setForeground(Color.WHITE);
        lblPrestador.setBounds(40, 70, 200, 25);
        getContentPane().add(lblPrestador);

        comboPrestadores = new JComboBox<>();
        comboPrestadores.setBounds(200, 70, 300, 25);
        getContentPane().add(comboPrestadores);

        JButton btnVer = new JButton("Ver Propuestas");
        btnVer.setBounds(520, 70, 150, 25);
        getContentPane().add(btnVer);

        modeloTabla = new DefaultTableModel(
                new Object[]{"Solicitud", "Tarifa ($/h)", "Mensaje", "Estado"}, 0);
        tablaPropuestas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPropuestas);
        scrollPane.setBounds(40, 120, 720, 300);
        getContentPane().add(scrollPane);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(340, 440, 120, 30);
        getContentPane().add(btnVolver);

        cargarPrestadores();

        btnVer.addActionListener(e -> cargarPropuestasDePrestador());
        btnVolver.addActionListener(e -> dispose());
    }

    /**
     * Carga los prestadores en el combo box.
     */
    private void cargarPrestadores() {
        comboPrestadores.removeAllItems();
        for (Prestador p : sistema.getPrestadores()) {
            comboPrestadores.addItem(p);
        }
    }

    /**
     * Carga las propuestas del prestador seleccionado en la tabla.
     */
    private void cargarPropuestasDePrestador() {
        modeloTabla.setRowCount(0);
        Prestador seleccionado = (Prestador) comboPrestadores.getSelectedItem();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un prestador.");
            return;
        }

        boolean tienePropuestas = false;

        for (Solicitud s : sistema.getSolicitudes()) {
            for (Propuesta p : s.getPropuestas()) {
                if (p.getPrestador().equals(seleccionado)) {
                    modeloTabla.addRow(new Object[]{
                            s.getDescripcion(),
                            p.getTarifaOfrecida(),
                            p.getMensaje(),
                            p.getEstado()
                    });
                    tienePropuestas = true;
                }
            }
        }

        if (!tienePropuestas) {
            JOptionPane.showMessageDialog(this, "Este prestador no tiene propuestas enviadas.");
        }
    }
}