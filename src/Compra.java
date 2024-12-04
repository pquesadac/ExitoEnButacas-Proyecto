import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class Compra extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPanel = new JPanel();
    private JTextField txt_precio; 
    private JPanel panel;
    private AsientosGUI asientosGUI;
    private int cantidad_asientos = 0; 
    private double precio_total = 0.0; 
    private final int precio_asiento = 5; 
    private JLabel lblCantidadAsientos;

    private TimerReserva timerReserva;

    private static final String FS = File.separator;
    private static final ImageIcon CARRITO = new ImageIcon("." + FS + "assets" + FS + "carrito.png");

    /**
     * Create the dialog.
     */
    public Compra(AsientosGUI asientosGUI) {
        this.asientosGUI = asientosGUI;

        this.timerReserva = new TimerReserva(asientosGUI, 3); // Cambiar segundos 
        this.timerReserva.start(); // Iniciar temporizador al abrir la ventana
                
        setBounds(100, 100, 526, 298);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(192, 192, 192));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(210, 54, 100, 100);
        imageLabel.setIcon(CARRITO);
        contentPanel.add(imageLabel);

        JLabel lblCesta = new JLabel("C E S T A");
        lblCesta.setHorizontalAlignment(SwingConstants.CENTER);
        lblCesta.setFont(new Font("Leelawadee UI", Font.BOLD, 25));
        lblCesta.setBounds(152, 10, 210, 34);
        contentPanel.add(lblCesta);

        // Inicializa la etiqueta de cantidad de asientos
        lblCantidadAsientos = new JLabel(cantidad_asientos + " (" + precio_asiento + "€ por asiento)");
        lblCantidadAsientos.setBounds(41, 168, 210, 52);
        contentPanel.add(lblCantidadAsientos);
        lblCantidadAsientos.setFont(new Font("Book Antiqua", Font.PLAIN, 16));

        precio_total = cantidad_asientos * precio_asiento; 
        
        txt_precio = new JTextField(String.valueOf(precio_total) + " €");
        txt_precio.setEditable(false);
        txt_precio.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
        txt_precio.setBounds(361, 178, 112, 27);
        contentPanel.add(txt_precio);

        panel = new JPanel();
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setBounds(31, 181, 450, 27);
        contentPanel.add(panel);

        JLabel lblAsientos = new JLabel("Asientos:");
        lblAsientos.setFont(new Font("Serif", Font.PLAIN, 12));
        lblAsientos.setBounds(31, 158, 134, 13);
        contentPanel.add(lblAsientos);

        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Serif", Font.PLAIN, 12));
        lblTotal.setBounds(347, 158, 126, 13);
        contentPanel.add(lblTotal);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btn_pagar = new JButton("Pagar");
        btn_pagar.setActionCommand("OK");
        btn_pagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                asientosGUI.marcarAsientosComoOcupados();
                dispose();
            }
        });
        buttonPane.add(btn_pagar);
        getRootPane().setDefaultButton(btn_pagar);

        JButton btn_cancelar = new JButton("Cancelar");
        btn_cancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                asientosGUI.liberarAsientosReservados();
                dispose();
            }
        });
        btn_cancelar.setActionCommand("Cancel");
        buttonPane.add(btn_cancelar);
    }

    // Método para actualizar la cantidad de asientos y el total
   public void actualizarCantidadAsientos(int cantidad) {
        this.cantidad_asientos = cantidad;
        // Actualiza la etiqueta de cantidad de asientos
        lblCantidadAsientos.setText(cantidad_asientos + " (" + precio_asiento + "€ por asiento)");
        // Recalcula el precio total
        precio_total = cantidad_asientos * precio_asiento;
        txt_precio.setText(String.valueOf(precio_total) + " €");
    }

    // Actualiza el total (si es necesario)
    public void actualizarTotal(int total) {
        txt_precio.setText("$" + total);
    }
}
