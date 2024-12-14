import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ProcesoCompra extends JFrame {

    // Constantes y atributos de la clase
    private static final long serialVersionUID = 1L; // ID único para la serialización
    private JPanel contentPane; // Panel principal de la ventana
    private Map<Integer, JPanel> panelesUsuarios; // Map para asociar usuarios con sus paneles de estado
    private Map<Integer, JLabel> etiquetasAsientos; // Map para asociar usuarios con etiquetas que muestran sus asientos
    private static final String FS = File.separator; // Separador de archivos según el sistema operativo
    private static final ImageIcon ICONO_USUARIO = new ImageIcon("." + FS + "assets" + FS + "usuario_icono.jpg"); // Icono del usuario

    // Constructor de la ventana
    public ProcesoCompra(String[] nombresUsuarios) {
        // Configuración básica de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la ventana al salir
        setBounds(100, 100, 600, 450); // Establecer tamaño y posición inicial
        contentPane = new JPanel(); // Inicializar el panel principal
        contentPane.setBackground(SystemColor.activeCaption); // Color de fondo
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Márgenes
        setContentPane(contentPane); // Asignar el panel principal
        contentPane.setLayout(null); // Usar un layout absoluto para posicionar componentes

        // Inicializar los mapas para los paneles y etiquetas
        panelesUsuarios = new HashMap<>();
        etiquetasAsientos = new HashMap<>();

        // Título de la ventana
        JLabel titulo = new JLabel("Usuarios:");
        titulo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24)); // Estilo del texto
        titulo.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
        titulo.setBounds(50, 20, 500, 30); // Posición y tamaño del título
        contentPane.add(titulo); // Añadir el título al panel principal

        // Crear los paneles y etiquetas para cada usuario
        for (int i = 0; i < nombresUsuarios.length; i++) {
            // Crear etiqueta con el nombre del usuario y su icono
            JLabel lblUsuario = new JLabel(nombresUsuarios[i]);
            lblUsuario.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20)); // Estilo del texto
            lblUsuario.setIcon(escalaIcono(ICONO_USUARIO, 40, 30)); // Escalar el icono
            lblUsuario.setHorizontalTextPosition(SwingConstants.RIGHT); // Posicionar el texto a la derecha del icono
            lblUsuario.setIconTextGap(10); // Espacio entre el texto y el icono
            lblUsuario.setBounds(50, 105 + (50 * i), 180, 35); // Posición y tamaño
            contentPane.add(lblUsuario); // Añadir la etiqueta al panel principal

            // Crear un panel para mostrar el estado del usuario
            JPanel panelEstado = new JPanel();
            panelEstado.setBounds(250, 105 + (50 * i), 25, 25); // Posición y tamaño
            panelEstado.setBorder(new LineBorder(Color.BLACK)); // Borde negro
            panelEstado.setVisible(false); // Ocultar inicialmente
            contentPane.add(panelEstado); // Añadir el panel al panel principal

            // Crear una etiqueta para mostrar los asientos comprados por el usuario
            JLabel lblAsientosComprados = new JLabel("Asientos: ");
            lblAsientosComprados.setFont(new Font("Arial", Font.PLAIN, 14)); // Estilo del texto
            lblAsientosComprados.setBounds(290, 105 + (50 * i), 250, 25); // Posición y tamaño
            contentPane.add(lblAsientosComprados); // Añadir la etiqueta al panel principal

            // Asociar el panel de estado y la etiqueta de asientos con el usuario
            panelesUsuarios.put(i, panelEstado);
            etiquetasAsientos.put(i, lblAsientosComprados);
        }

        // Añadir la leyenda de los estados al final de la ventana
        agregarLeyendaDeEstados();
    }

    // Método para agregar la leyenda de colores que representan los estados de los usuarios
    private void agregarLeyendaDeEstados() {
        // Estado "Sin comprar" (verde)
        JPanel pnlVerde = new JPanel();
        pnlVerde.setBounds(50, 369, 12, 12);
        pnlVerde.setBorder(new LineBorder(new Color(0, 0, 0))); // Borde negro
        pnlVerde.setBackground(new Color(0, 255, 0)); // Color verde
        contentPane.add(pnlVerde);

        JLabel lblVerde = new JLabel("Sin comprar");
        lblVerde.setBounds(72, 368, 80, 13); // Posición y tamaño
        contentPane.add(lblVerde); // Añadir la etiqueta

        // Estado "Comprando..." (naranja)
        JPanel pnlNaranja = new JPanel();
        pnlNaranja.setBorder(new LineBorder(new Color(0, 0, 0))); // Borde negro
        pnlNaranja.setBackground(new Color(255, 165, 0)); // Color naranja
        pnlNaranja.setBounds(175, 369, 12, 12);
        contentPane.add(pnlNaranja);

        JLabel lblNaranja = new JLabel("Comprando...");
        lblNaranja.setBounds(197, 368, 93, 13); // Posición y tamaño
        contentPane.add(lblNaranja);

        // Estado "Comprado" (rojo)
        JPanel pnlRojo = new JPanel();
        pnlRojo.setBorder(new LineBorder(new Color(0, 0, 0))); // Borde negro
        pnlRojo.setBackground(new Color(255, 0, 0)); // Color rojo
        pnlRojo.setBounds(300, 369, 12, 12);
        contentPane.add(pnlRojo);

        JLabel lblRojo = new JLabel("Comprado");
        lblRojo.setBounds(322, 368, 66, 13); // Posición y tamaño
        contentPane.add(lblRojo);

        // Estado "Rendido" (morado)
        JPanel pnlMorado = new JPanel();
        pnlMorado.setBorder(new LineBorder(new Color(0, 0, 0))); // Borde negro
        pnlMorado.setBackground(new Color(147, 112, 219)); // Color morado
        pnlMorado.setBounds(425, 369, 12, 12);
        contentPane.add(pnlMorado);

        JLabel lblRendido = new JLabel("Rendido");
        lblRendido.setBounds(447, 368, 74, 13); // Posición y tamaño
        contentPane.add(lblRendido);
    }

    // Método para actualizar el estado visual de un usuario
    public void actualizarEstadoUsuario(int userId, String estado) {
        JPanel panel = panelesUsuarios.get(userId); // Obtener el panel del usuario
        if (panel != null) {
            // Cambiar el color del panel según el estado
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
            panel.setVisible(true); // Mostrar el panel
            repaint(); // Actualizar la interfaz gráfica
        }
    }

    // Método para actualizar los asientos comprados por un usuario
    public void actualizarAsientosComprados(int userId, java.util.List<Integer> asientosComprados) {
        JLabel etiqueta = etiquetasAsientos.get(userId); // Obtener la etiqueta del usuario
        if (etiqueta != null) {
            // Si no hay asientos comprados, mostrar un mensaje por defecto
            String asientosTexto = asientosComprados == null || asientosComprados.isEmpty()
                    ? "Asientos: No compró ningún asiento"
                    : "Asientos: " + asientosComprados.toString(); // Mostrar los asientos comprados
            etiqueta.setText(asientosTexto); // Actualizar el texto de la etiqueta
            repaint(); // Actualizar la interfaz gráfica
        }
    }

    // Método para escalar un icono a un tamaño específico
    private ImageIcon escalaIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage(); // Obtener la imagen del icono
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH); // Escalar la imagen
        return new ImageIcon(imagenEscalada); // Devolver un nuevo ImageIcon escalado
    }
}