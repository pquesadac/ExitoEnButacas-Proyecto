import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.Icon;

public class ProcesoCompra extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private Map<Integer, JPanel> panelesUsuarios;

	private static final String FS = File.separator;
	private static final ImageIcon ICONO_USUARIO = new ImageIcon("." + FS + "assets" + FS + "usuario_icono.jpg");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcesoCompra frame = new ProcesoCompra();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProcesoCompra() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        panelesUsuarios = new HashMap<>();
		
		JLabel lblUsuario1 = new JLabel("Usuario 1");
		lblUsuario1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		lblUsuario1.setBounds(85, 105, 114, 25);
		contentPane.add(lblUsuario1);
		
		JLabel lblUsuario2 = new JLabel("Usuario 2");
		lblUsuario2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		lblUsuario2.setBounds(85, 155, 130, 25);
		contentPane.add(lblUsuario2);
		
		JLabel lblUsuario3 = new JLabel("Usuario 3");
		lblUsuario3.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		lblUsuario3.setBounds(85, 205, 130, 25);
		contentPane.add(lblUsuario3);
		
		JLabel lblUsuario4 = new JLabel("Usuario 4");
		lblUsuario4.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		lblUsuario4.setBounds(85, 255, 130, 25);
		contentPane.add(lblUsuario4);
		
		JLabel lblUsuario5 = new JLabel("Usuario 5");
		lblUsuario5.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		lblUsuario5.setBounds(85, 305, 130, 25);
		contentPane.add(lblUsuario5);
		
		JPanel pnlVerde = new JPanel();
		pnlVerde.setBounds(50, 369, 12, 12);
		contentPane.add(pnlVerde);
		pnlVerde.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlVerde.setBackground(new Color(0, 255, 0));
		
		JPanel pnlNaranja = new JPanel();
		pnlNaranja.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlNaranja.setBackground(new Color(255, 165, 0));
		pnlNaranja.setBounds(180, 369, 12, 12);
		contentPane.add(pnlNaranja);
		
		JPanel pnlRojo = new JPanel();
		pnlRojo.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlRojo.setBackground(new Color(255, 0, 0));
		pnlRojo.setBounds(310, 369, 12, 12);
		contentPane.add(pnlRojo);
		
		JLabel lblVerde = new JLabel("Sin comprar");
		lblVerde.setBounds(72, 368, 74, 13);
		contentPane.add(lblVerde);
		
		JLabel lblNaranja = new JLabel("Comprando...");
		lblNaranja.setBounds(202, 368, 74, 13);
		contentPane.add(lblNaranja);
		
		JLabel lblRojo = new JLabel("Comprado");
		lblRojo.setBounds(332, 368, 74, 13);
		contentPane.add(lblRojo);
		
		JLabel lblIcono1 = new JLabel(ICONO_USUARIO);
		lblIcono1.setBounds(26, 100, 39, 35);
		contentPane.add(lblIcono1);
		
		JLabel lblIcono2 = new JLabel(ICONO_USUARIO);
		lblIcono2.setBounds(26, 150, 39, 35);
		contentPane.add(lblIcono2);
		
		JLabel lblIcono3 = new JLabel(ICONO_USUARIO);
		lblIcono3.setBounds(26, 200, 39, 35);
		contentPane.add(lblIcono3);
		
		JLabel lblIcono4 = new JLabel(ICONO_USUARIO);
		lblIcono4.setBounds(26, 250, 39, 35);
		contentPane.add(lblIcono4);
		
		JLabel lblIcono5 = new JLabel(ICONO_USUARIO);
		lblIcono5.setBounds(26, 300, 39, 35);
		contentPane.add(lblIcono5);
		
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

            panelesUsuarios.put(i, panelEstado);
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
