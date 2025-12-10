/*
 * @author Julian Salazar
 * @author Mateo Cortes
 * @author Alejandro Caro
 * Fecha: 10 / 12 / 2025
 * @version final
 */
package gui;

import javax.swing.*;
import java.awt.*;
import logica.SistemaServicios;

/**
 * Ventana principal de bienvenida del sistema.
 * Permite al usuario ingresar al sistema o salir del programa.
 */
public class VentanaBienvenida extends JFrame {

    /** Identificador de versión. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la clase VentanaBienvenida.
     * Inicializa los componentes gráficos de la interfaz.
     *
     * @param sistema objeto principal del sistema que gestiona los datos.
     */
    public VentanaBienvenida(SistemaServicios sistema) {
        setIconImage(new ImageIcon(getClass().getResource("/resources/soporte.jpg")).getImage());
        setTitle("Bienvenida - Proyecto 6");
        setSize(544, 419);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);

        JLabel lblTitulo = new JLabel("GRUPO 6");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(118, 48, 300, 40);
        getContentPane().add(lblTitulo);

        JLabel lblSub = new JLabel("Sistema de Conexión de Servicios Locales");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSub.setForeground(Color.LIGHT_GRAY);
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        lblSub.setBounds(80, 101, 380, 25);
        getContentPane().add(lblSub);
        
        ImageIcon original = new ImageIcon(getClass().getResource("/resources/soporte.jpg"));
        Image imagenEscalada = original.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(imagenEscalada));
        lblLogo.setBounds(10, 126, 150, 150);
        getContentPane().add(lblLogo);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(149, 281, 120, 35);
        getContentPane().add(btnEntrar);

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(279, 281, 120, 35);
        getContentPane().add(btnSalir);
        
        JLabel lblNewLabel = new JLabel("- Julian Andres Salazar Belilla 20251020131");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setBounds(160, 137, 226, 40);
        getContentPane().add(lblNewLabel);
        
        JLabel lblMateoCortes = new JLabel("- Mateo Cortes Galindo 20242020083");
        lblMateoCortes.setHorizontalAlignment(SwingConstants.CENTER);
        lblMateoCortes.setForeground(Color.WHITE);
        lblMateoCortes.setBounds(160, 176, 226, 40);
        getContentPane().add(lblMateoCortes);
        
        JLabel lblAlejandroRodriguez = new JLabel("- Alejandro Rodriguez Caro 20251020195");
        lblAlejandroRodriguez.setHorizontalAlignment(SwingConstants.CENTER);
        lblAlejandroRodriguez.setForeground(Color.WHITE);
        lblAlejandroRodriguez.setBounds(160, 216, 226, 40);
        getContentPane().add(lblAlejandroRodriguez);

        btnEntrar.addActionListener(e -> {
            new VentanaSeleccion(sistema).setVisible(true);
            dispose();
        });

        btnSalir.addActionListener(e -> {
            sistema.guardarDatos();
            JOptionPane.showMessageDialog(this, "Datos guardados.");
            System.exit(0);
        });
        
        setUndecorated(true);
    }
}
