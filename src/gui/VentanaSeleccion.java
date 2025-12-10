/*
 * @author Julian Salazar / Mateo Cortes / Alejandro Caro
 * Fecha: 10 / 12 / 2025
 * @version final
 */
package gui;

import javax.swing.*;
import java.awt.*;
import logica.SistemaServicios;

/**
 * Ventana para seleccionar el rol (cliente o prestador).
 */
public class VentanaSeleccion extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la ventana de selección de rol.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaSeleccion(SistemaServicios sistema) {
        setIconImage(new ImageIcon(getClass().getResource("/resources/soporte.jpg")).getImage());

        setTitle("Seleccionar Rol");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);

        JLabel lblTitulo = new JLabel("Seleccione un rol porfavor");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(70, 50, 350, 30);
        getContentPane().add(lblTitulo);

        JButton btnCliente = new JButton("Cliente");
        btnCliente.setBounds(182, 91, 120, 35);
        getContentPane().add(btnCliente);

        JButton btnPrestador = new JButton("Prestador");
        btnPrestador.setBounds(182, 137, 120, 35);
        getContentPane().add(btnPrestador);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(378, 231, 106, 30);
        getContentPane().add(btnVolver);

        btnCliente.addActionListener(e -> {
            new VentanaPrincipalCliente(sistema).setVisible(true);
            dispose();
        });

        btnPrestador.addActionListener(e -> {
            new VentanaPrincipalPrestador(sistema).setVisible(true);
            dispose();
        });

        btnVolver.addActionListener(e -> {
            new VentanaBienvenida(sistema).setVisible(true);
            dispose();
        });
        
        setUndecorated(true);
    }
}