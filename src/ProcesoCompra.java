import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.io.File;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.Icon;

public class ProcesoCompra extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
		
		JPanel pnlMorado = new JPanel();
		pnlMorado.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlMorado.setBackground(new Color(123, 104, 238));
		pnlMorado.setBounds(440, 369, 12, 12);
		contentPane.add(pnlMorado);
		
		JLabel lblVerde = new JLabel("Sin comprar");
		lblVerde.setBounds(72, 368, 74, 13);
		contentPane.add(lblVerde);
		
		JLabel lblNaranja = new JLabel("Comprando...");
		lblNaranja.setBounds(202, 368, 74, 13);
		contentPane.add(lblNaranja);
		
		JLabel lblRojo = new JLabel("Comprado");
		lblRojo.setBounds(332, 368, 74, 13);
		contentPane.add(lblRojo);
		
		JLabel lblMorado = new JLabel("Denegado");
		lblMorado.setBounds(462, 368, 74, 13);
		contentPane.add(lblMorado);
		
		JPanel pnlVerdeUsuario1 = new JPanel();
		pnlVerdeUsuario1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlVerdeUsuario1.setBackground(Color.GREEN);
		pnlVerdeUsuario1.setBounds(202, 105, 25, 25);
		contentPane.add(pnlVerdeUsuario1);
		
		JPanel pnlVerdeUsuario2 = new JPanel();
		pnlVerdeUsuario2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlVerdeUsuario2.setBackground(Color.GREEN);
		pnlVerdeUsuario2.setBounds(202, 155, 25, 25);
		contentPane.add(pnlVerdeUsuario2);
		
		JPanel pnlVerdeUsuario3 = new JPanel();
		pnlVerdeUsuario3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlVerdeUsuario3.setBackground(Color.GREEN);
		pnlVerdeUsuario3.setBounds(202, 205, 25, 25);
		contentPane.add(pnlVerdeUsuario3);
		
		JPanel pnlVerdeUsuario4 = new JPanel();
		pnlVerdeUsuario4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlVerdeUsuario4.setBackground(Color.GREEN);
		pnlVerdeUsuario4.setBounds(202, 255, 25, 25);
		contentPane.add(pnlVerdeUsuario4);
		
		JPanel pnlVerdeUsuario5 = new JPanel();
		pnlVerdeUsuario5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlVerdeUsuario5.setBackground(Color.GREEN);
		pnlVerdeUsuario5.setBounds(202, 305, 25, 25);
		contentPane.add(pnlVerdeUsuario5);
		
		JPanel pnlNaranjaUsuario1 = new JPanel();
		pnlNaranjaUsuario1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlNaranjaUsuario1.setBackground(new Color(255, 165, 0));
		pnlNaranjaUsuario1.setBounds(202, 105, 25, 25);
		contentPane.add(pnlNaranjaUsuario1);
		
		JPanel pnlNaranjaUsuario2 = new JPanel();
		pnlNaranjaUsuario2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlNaranjaUsuario2.setBackground(new Color(255, 165, 0));
		pnlNaranjaUsuario2.setBounds(202, 155, 25, 25);
		contentPane.add(pnlNaranjaUsuario2);
		
		JPanel pnlNaranjaUsuario3 = new JPanel();
		pnlNaranjaUsuario3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlNaranjaUsuario3.setBackground(new Color(255, 165, 0));
		pnlNaranjaUsuario3.setBounds(202, 205, 25, 25);
		contentPane.add(pnlNaranjaUsuario3);
		
		JPanel pnlNaranjaUsuario4 = new JPanel();
		pnlNaranjaUsuario4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlNaranjaUsuario4.setBackground(new Color(255, 165, 0));
		pnlNaranjaUsuario4.setBounds(202, 255, 25, 25);
		contentPane.add(pnlNaranjaUsuario4);
		
		JPanel pnlNaranjaUsuario5 = new JPanel();
		pnlNaranjaUsuario5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlNaranjaUsuario5.setBackground(new Color(255, 165, 0));
		pnlNaranjaUsuario5.setBounds(202, 305, 25, 25);
		contentPane.add(pnlNaranjaUsuario5);
		
		JPanel pnlRojoUsuario1 = new JPanel();
		pnlRojoUsuario1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlRojoUsuario1.setBackground(new Color(255, 0, 0));
		pnlRojoUsuario1.setBounds(202, 105, 25, 25);
		contentPane.add(pnlRojoUsuario1);
		
		JPanel pnlRojoUsuario2 = new JPanel();
		pnlRojoUsuario2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlRojoUsuario2.setBackground(Color.RED);
		pnlRojoUsuario2.setBounds(202, 155, 25, 25);
		contentPane.add(pnlRojoUsuario2);
		
		JPanel pnlRojoUsuario3 = new JPanel();
		pnlRojoUsuario3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlRojoUsuario3.setBackground(Color.RED);
		pnlRojoUsuario3.setBounds(202, 205, 25, 25);
		contentPane.add(pnlRojoUsuario3);
		
		JPanel pnlRojoUsuario4 = new JPanel();
		pnlRojoUsuario4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlRojoUsuario4.setBackground(Color.RED);
		pnlRojoUsuario4.setBounds(202, 255, 25, 25);
		contentPane.add(pnlRojoUsuario4);
		
		JPanel pnlRojoUsuario5 = new JPanel();
		pnlRojoUsuario5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlRojoUsuario5.setBackground(Color.RED);
		pnlRojoUsuario5.setBounds(202, 305, 25, 25);
		contentPane.add(pnlRojoUsuario5);
		
		JPanel pnlMoradoUsuario1 = new JPanel();
		pnlMoradoUsuario1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlMoradoUsuario1.setBackground(new Color(123, 104, 238));
		pnlMoradoUsuario1.setBounds(202, 105, 25, 25);
		contentPane.add(pnlMoradoUsuario1);
		
		JPanel pnlMoradoUsuario2 = new JPanel();
		pnlMoradoUsuario2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlMoradoUsuario2.setBackground(new Color(123, 104, 238));
		pnlMoradoUsuario2.setBounds(202, 155, 25, 25);
		contentPane.add(pnlMoradoUsuario2);
		
		JPanel pnlMoradoUsuario3 = new JPanel();
		pnlMoradoUsuario3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlMoradoUsuario3.setBackground(new Color(123, 104, 238));
		pnlMoradoUsuario3.setBounds(202, 205, 25, 25);
		contentPane.add(pnlMoradoUsuario3);
		
		JPanel pnlMoradoUsuario4 = new JPanel();
		pnlMoradoUsuario4.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlMoradoUsuario4.setBackground(new Color(123, 104, 238));
		pnlMoradoUsuario4.setBounds(202, 255, 25, 25);
		contentPane.add(pnlMoradoUsuario4);
		
		JPanel pnlMoradoUsuario5 = new JPanel();
		pnlMoradoUsuario5.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlMoradoUsuario5.setBackground(new Color(123, 104, 238));
		pnlMoradoUsuario5.setBounds(202, 305, 25, 25);
		contentPane.add(pnlMoradoUsuario5);
		
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
	}
}
