import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ProcesoCompra extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Map<Integer, JPanel> panelesUsuarios;
    private Map<Integer, JLabel> etiquetasAsientos;
    private static final String FS = File.separator;
    private static final ImageIcon ICONO_USUARIO = new ImageIcon("." + FS + "assets" + FS + "usuario_icono.jpg");

    public ProcesoCompra(String[] nombresUsuarios) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panelesUsuarios = new HashMap<>();
        etiquetasAsientos = new HashMap<>();

        JLabel titulo = new JLabel("Usuarios:");
        titulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(50, 20, 500, 30); // Posición y tamaño del título
        contentPane.add(titulo);
        
        // Crear etiquetas y paneles para los usuarios
        for (int i = 0; i < nombresUsuarios.length; i++) {
        	JLabel lblUsuario = new JLabel(nombresUsuarios[i]);
            lblUsuario.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
            lblUsuario.setIcon(escalaIcono(ICONO_USUARIO, 40, 30)); // Añade el ícono con escala
            lblUsuario.setHorizontalTextPosition(SwingConstants.RIGHT); // Texto a la derecha del ícono
            lblUsuario.setIconTextGap(10); // Espacio entre ícono y texto
            lblUsuario.setBounds(50, 105 + (50 * i), 180, 35); // Ajustar posición
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
        lblVerde.setBounds(72, 368, 80, 13);
        contentPane.add(lblVerde);

        JPanel pnlNaranja = new JPanel();
        pnlNaranja.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlNaranja.setBackground(new Color(255, 165, 0));
        pnlNaranja.setBounds(175, 369, 12, 12);
        contentPane.add(pnlNaranja);

        JLabel lblNaranja = new JLabel("Comprando...");
        lblNaranja.setBounds(197, 368, 93, 13);
        contentPane.add(lblNaranja);

        JPanel pnlRojo = new JPanel();
        pnlRojo.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlRojo.setBackground(new Color(255, 0, 0));
        pnlRojo.setBounds(300, 369, 12, 12);
        contentPane.add(pnlRojo);

        JLabel lblRojo = new JLabel("Comprado");
        lblRojo.setBounds(322, 368, 66, 13);
        contentPane.add(lblRojo);
        
        JPanel pnlMorado = new JPanel();
        pnlMorado.setBorder(new LineBorder(new Color(0, 0, 0)));
        pnlMorado.setBackground(new Color(147, 112, 219));
        pnlMorado.setBounds(425, 369, 12, 12);
        contentPane.add(pnlMorado);
        
        JLabel lblRendido = new JLabel("Rendido");
        lblRendido.setBounds(447, 368, 74, 13);
        contentPane.add(lblRendido);
    }

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
            panel.setVisible(true);
            repaint();
        }
    }

    public void actualizarAsientosComprados(int userId, java.util.List<Integer> asientosComprados) {
        JLabel etiqueta = etiquetasAsientos.get(userId);
        if (etiqueta != null) {
            // Mostrar mensaje apropiado si no hay asientos comprados
            String asientosTexto = asientosComprados == null || asientosComprados.isEmpty() 
                    ? "Asientos: No compró ningún asiento" 
                    : "Asientos: " + asientosComprados.toString();
            etiqueta.setText(asientosTexto);
            repaint();
        }
    }
    
    private ImageIcon escalaIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
}
