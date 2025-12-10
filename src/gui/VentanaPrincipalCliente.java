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
 * Ventana principal para clientes.
 * Proporciona acceso a las funcionalidades principales para clientes.
 */
public class VentanaPrincipalCliente extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la ventana principal del cliente.
     * 
     * @param sistema Instancia del sistema de servicios
     */
    public VentanaPrincipalCliente(SistemaServicios sistema) {
        setIconImage(new ImageIcon(getClass().getResource("/resources/soporte.jpg")).getImage());

        setUndecorated(true);
        setTitle("Panel del Cliente");
        setSize(600, 451);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(null);

        JLabel lblTitulo = new JLabel("¡Bienvenido apreciado usuario!");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(142, 11, 307, 30);
        getContentPane().add(lblTitulo);

        JButton btnClientes = new JButton("Registrar Cliente");
        btnClientes.setBounds(352, 141, 170, 30);
        getContentPane().add(btnClientes);

        JButton btnSolicitudes = new JButton("Crear Solicitud");
        btnSolicitudes.setBounds(352, 182, 170, 30);
        getContentPane().add(btnSolicitudes);
        
        JButton btnVerPropuestas = new JButton("Ver Propuestas");
        btnVerPropuestas.setBounds(352, 223, 170, 30);
        getContentPane().add(btnVerPropuestas);

        JButton btnOrdenes = new JButton("Ver Órdenes");
        btnOrdenes.setBounds(352, 265, 170, 30);
        getContentPane().add(btnOrdenes);

        JButton btnVolver = new JButton("Cerrar Sesión");
        btnVolver.setBounds(200, 371, 170, 30);
        getContentPane().add(btnVolver);
        
        JLabel lblSeleccionaUnaOpcion = new JLabel("Porfavor seleccione una opcion:");
        lblSeleccionaUnaOpcion.setHorizontalAlignment(SwingConstants.CENTER);
        lblSeleccionaUnaOpcion.setForeground(Color.WHITE);
        lblSeleccionaUnaOpcion.setFont(new Font("Bell MT", Font.BOLD, 17));
        lblSeleccionaUnaOpcion.setBounds(149, 48, 300, 30);
        getContentPane().add(lblSeleccionaUnaOpcion);
        
        ImageIcon original = new ImageIcon(getClass().getResource("/resources/atencion-al-cliente-jpg-800.jpg"));
        Image imagenEscalada = original.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
        JLabel lblLogo = new JLabel(iconoEscalado);
        lblLogo.setBounds(63, 141, 200, 150);
        getContentPane().add(lblLogo);

        JLabel lblParaUnUso = new JLabel("Para un uso adecuado sigue este orden:");
        lblParaUnUso.setHorizontalAlignment(SwingConstants.CENTER);
        lblParaUnUso.setForeground(Color.WHITE);
        lblParaUnUso.setFont(new Font("Bell MT", Font.BOLD, 17));
        lblParaUnUso.setBounds(10, 89, 307, 30);
        getContentPane().add(lblParaUnUso);
        
        JLabel lblSeleccionaUnaOpcion_1 = new JLabel("1.");
        lblSeleccionaUnaOpcion_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblSeleccionaUnaOpcion_1.setForeground(Color.WHITE);
        lblSeleccionaUnaOpcion_1.setFont(new Font("Bell MT", Font.BOLD, 17));
        lblSeleccionaUnaOpcion_1.setBounds(270, 140, 47, 30);
        getContentPane().add(lblSeleccionaUnaOpcion_1);
        
        JLabel lblSeleccionaUnaOpcion_1_1 = new JLabel("2.");
        lblSeleccionaUnaOpcion_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblSeleccionaUnaOpcion_1_1.setForeground(Color.WHITE);
        lblSeleccionaUnaOpcion_1_1.setFont(new Font("Bell MT", Font.BOLD, 17));
        lblSeleccionaUnaOpcion_1_1.setBounds(270, 181, 47, 30);
        getContentPane().add(lblSeleccionaUnaOpcion_1_1);
        
        JLabel lblSeleccionaUnaOpcion_1_2 = new JLabel("3.");
        lblSeleccionaUnaOpcion_1_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblSeleccionaUnaOpcion_1_2.setForeground(Color.WHITE);
        lblSeleccionaUnaOpcion_1_2.setFont(new Font("Bell MT", Font.BOLD, 17));
        lblSeleccionaUnaOpcion_1_2.setBounds(270, 222, 47, 30);
        getContentPane().add(lblSeleccionaUnaOpcion_1_2);
        
        JLabel lblSeleccionaUnaOpcion_1_3 = new JLabel("4.");
        lblSeleccionaUnaOpcion_1_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblSeleccionaUnaOpcion_1_3.setForeground(Color.WHITE);
        lblSeleccionaUnaOpcion_1_3.setFont(new Font("Bell MT", Font.BOLD, 17));
        lblSeleccionaUnaOpcion_1_3.setBounds(270, 264, 47, 30);
        getContentPane().add(lblSeleccionaUnaOpcion_1_3);

        // Acciones
        btnClientes.addActionListener(e -> new VentanaClientes(sistema).setVisible(true));
        btnSolicitudes.addActionListener(e -> new VentanaSolicitudes(sistema).setVisible(true));
        btnVerPropuestas.addActionListener(e -> new VentanaVerPropuestas(sistema).setVisible(true));
        btnOrdenes.addActionListener(e -> new VentanaOrdenes(sistema).setVisible(true));
        btnVolver.addActionListener(e -> {
            new VentanaSeleccion(sistema).setVisible(true);
            dispose();
        });
        
        setUndecorated(true);
    }
}