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
 * Ventana principal del Prestador.
 * El prestador puede registrarse, enviar propuestas y ver sus órdenes.
 */
public class VentanaPrincipalPrestador extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la ventana principal del prestador.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaPrincipalPrestador(SistemaServicios sistema) {
        setIconImage(new ImageIcon(getClass().getResource("/resources/soporte.jpg")).getImage());

        setUndecorated(true);

        setTitle("Panel del Prestador");
        setSize(600, 451);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);

        JButton btnPrestadores = new JButton("Registrar Prestador");
        btnPrestadores.setBounds(346, 158, 170, 30);
        getContentPane().add(btnPrestadores);

        JButton btnPropuestas = new JButton("Enviar Propuestas");
        btnPropuestas.setBounds(346, 199, 170, 30);
        getContentPane().add(btnPropuestas);

        JButton btnVerPropuestas = new JButton("Ver Mis Propuestas");
        btnVerPropuestas.setBounds(346, 240, 170, 30);
        getContentPane().add(btnVerPropuestas);

        JButton btnOrdenes = new JButton("Ver Órdenes");
        btnOrdenes.setBounds(346, 281, 170, 30);
        getContentPane().add(btnOrdenes);

        JButton btnVolver = new JButton("Cerrar Sesión");
        btnVolver.setBounds(211, 371, 170, 30);
        getContentPane().add(btnVolver);

        JLabel lblBienvenido = new JLabel("¡Bienvenido apreciado trabajador!");
        lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
        lblBienvenido.setForeground(Color.WHITE);
        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 20));
        lblBienvenido.setBounds(136, 11, 355, 30);
        getContentPane().add(lblBienvenido);

        JLabel lblSelecciona = new JLabel("Por favor, seleccione una opción:");
        lblSelecciona.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelecciona.setForeground(Color.WHITE);
        lblSelecciona.setFont(new Font("Bell MT", Font.BOLD, 17));
        lblSelecciona.setBounds(160, 52, 300, 30);
        getContentPane().add(lblSelecciona);

        JLabel lblOrden = new JLabel("Sigue este orden recomendado:");
        lblOrden.setHorizontalAlignment(SwingConstants.CENTER);
        lblOrden.setForeground(Color.WHITE);
        lblOrden.setFont(new Font("Bell MT", Font.BOLD, 17));
        lblOrden.setBounds(10, 97, 307, 30);
        getContentPane().add(lblOrden);

        ImageIcon original = new ImageIcon(getClass().getResource("/resources/prestador.png"));
        Image imagenEscalada = original.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(imagenEscalada));
        lblLogo.setBounds(60, 145, 200, 150);
        getContentPane().add(lblLogo);

        JLabel lbl1 = new JLabel("1.");
        lbl1.setForeground(Color.WHITE);
        lbl1.setFont(new Font("Bell MT", Font.BOLD, 17));
        lbl1.setBounds(289, 157, 47, 30);
        getContentPane().add(lbl1);

        JLabel lbl2 = new JLabel("2.");
        lbl2.setForeground(Color.WHITE);
        lbl2.setFont(new Font("Bell MT", Font.BOLD, 17));
        lbl2.setBounds(289, 199, 47, 30);
        getContentPane().add(lbl2);

        JLabel lbl3 = new JLabel("3.");
        lbl3.setForeground(Color.WHITE);
        lbl3.setFont(new Font("Bell MT", Font.BOLD, 17));
        lbl3.setBounds(289, 240, 47, 30);
        getContentPane().add(lbl3);

        JLabel lbl4 = new JLabel("4.");
        lbl4.setForeground(Color.WHITE);
        lbl4.setFont(new Font("Bell MT", Font.BOLD, 17));
        lbl4.setBounds(289, 281, 47, 30);
        getContentPane().add(lbl4);

        // 🔹 Acciones de botones
        btnPrestadores.addActionListener(e -> new VentanaPrestadores(sistema).setVisible(true));
        btnPropuestas.addActionListener(e -> new VentanaPropuestas(sistema).setVisible(true));
        btnVerPropuestas.addActionListener(e -> new VentanaMisPropuestas(sistema).setVisible(true));
        btnOrdenes.addActionListener(e -> new VentanaOrdenes(sistema).setVisible(true));
        btnVolver.addActionListener(e -> {
            new VentanaSeleccion(sistema).setVisible(true);
            dispose();
        });
    }
}