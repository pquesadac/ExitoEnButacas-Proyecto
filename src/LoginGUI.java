import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 475);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setForeground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoginTitle = new JLabel("Login");
		lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginTitle.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
		lblLoginTitle.setBounds(104, 30, 197, 47);
		contentPane.add(lblLoginTitle);
		
		txtID = new JTextField();
		txtID.setText("  DNI/NIE");
		txtID.setBounds(53, 175, 305, 35);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setText("  Contraseña");
		txtPassword.setColumns(10);
		txtPassword.setBounds(53, 233, 305, 35);
		contentPane.add(txtPassword);
		
		JButton btnAcceso = new JButton("ACCEDER");
		btnAcceso.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		btnAcceso.setBounds(138, 316, 134, 35);
		contentPane.add(btnAcceso);
	}
}
