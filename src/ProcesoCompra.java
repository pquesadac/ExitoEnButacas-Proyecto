import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ProcesoCompra extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Map<Integer, JPanel> panelesUsuarios;
    private Map<Integer, JLabel> etiquetasAsientos;
    private static final String FS = File.separator;
    private static final ImageIcon ICONO_USUARIO = new ImageIcon("." + FS + "assets" + FS + "usuario_icono.jpg");

    public ProcesoCompra() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panelesUsuarios = new HashMap<>();
        etiquetasAsientos = new HashMap<>();

        // Crear etiquetas y paneles para los usuarios
        for (int i = 0; i < 5; i++) {
            JLabel lblUsuario = new JLabel("Usuario " + (i + 1));
            lblUsuario.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
            lblUsuario.setBounds(85, 105 + (50 * i), 130, 25);
            contentPane.add(lblUsuario);

            JPanel panelEstado = new JPanel();
            panelEstado.setBounds(250, 105 + (50 * i), 25, 25);
            panelEstado.setBorder(new LineBorder(Color.BLACK));
            panelEstado.setVisible(false); // Ocultar inicialmente
            contentPane.add(panelEstado);

            JLabel lblAsientosComprados = new JLabel("Asientos: ");
            lblAsientosComprados.setFont(new Font("Arial", Font.PLAIN, 14));
            lblAsientosComprados.setBounds(290, 105 + (50 * i), 250, 25);
            contentPane.add(lblAsientosComprados);

            panelesUsuarios.put(i, panelEstado);
            etiquetasAsientos.put(i, lblAsientosComprados);
        }

        // Leyenda de estados
        agregarLeyendaDeEstados();
    }

    private void agregarLeyendaDeEstados() {
        JPanel pnlVerde = new JPanel();
        pnlVerde.setBounds(50, 369, 12, 12);
        pnlVerde.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlVerde.setBackground(new Color(0, 255, 0));
        contentPane.add(pnlVerde);

        JLabel lblVerde = new JLabel("Sin comprar");
        lblVerde.setBounds(72, 368, 74, 13);
        contentPane.add(lblVerde);

        JPanel pnlNaranja = new JPanel();
        pnlNaranja.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlNaranja.setBackground(new Color(255, 165, 0));
        pnlNaranja.setBounds(180, 369, 12, 12);
        contentPane.add(pnlNaranja);

        JLabel lblNaranja = new JLabel("Comprando...");
        lblNaranja.setBounds(202, 368, 84, 13);
        contentPane.add(lblNaranja);

        JPanel pnlRojo = new JPanel();
        pnlRojo.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlRojo.setBackground(new Color(255, 0, 0));
        pnlRojo.setBounds(310, 369, 12, 12);
        contentPane.add(pnlRojo);

        JLabel lblRojo = new JLabel("Comprado");
        lblRojo.setBounds(332, 368, 74, 13);
        contentPane.add(lblRojo);
    }

    /**
     * Actualiza los IDs de los asientos comprados de un usuario.
     *
     * @param userId             ID del usuario (0-4).
     * @param asientosComprados  Lista de IDs de los asientos comprados.
     */
    public void actualizarAsientosComprados(int userId, List<Integer> asientosComprados) {
        JLabel etiqueta = etiquetasAsientos.get(userId);
        if (etiqueta != null) {
            String asientosTexto = asientosComprados.isEmpty() ? "Asientos: Ninguno" : "Asientos: " + asientosComprados.toString();
            etiqueta.setText(asientosTexto);
            repaint();
        }
    }

    /**
     * Actualiza el estado visual de un usuario.
     *
     * @param userId ID del usuario (0-4).
     * @param estado Estado: "verde", "naranja", "rojo", "morado".
     */
    public void actualizarEstadoUsuario(int userId, String estado) {
        JPanel panel = panelesUsuarios.get(userId);
        if (panel != null) {
            switch (estado) {
                case "verde":
                    panel.setBackground(Color.GREEN);
                    break;
                case "naranja":
                    panel.setBackground(Color.ORANGE);
                    break;
                case "rojo":
                    panel.setBackground(Color.RED);
                    break;
                case "morado":
                    panel.setBackground(new Color(123, 104, 238));
                    break;
            }
            panel.setVisible(true); // Mostrar el panel al actualizar el estado
            repaint();
        }
    }
}
