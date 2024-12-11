import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LoginGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtID;
    private JTextField txtPassword;
    private Sala sala;
    public LoginGUI() {
    	this.sala = new Sala();
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
         btnAcceso.addActionListener(e -> {
            if (validateLogin()) {
                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(txtID.getText().trim())); 
                usuario.setNombre(txtID.getText().trim()); 
                
                AsientosGUI asientosGUI = new AsientosGUI(sala, usuario);
                asientosGUI.setVisible(true);
                dispose(); 
            }
        });
        addPlaceholderBehavior();
    }
  private void addPlaceholderBehavior() {
        txtID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtID.getText().equals("  DNI/NIE")) {
                    txtID.setText("");
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtID.getText().isEmpty()) {
                    txtID.setText("  DNI/NIE");
                }
            }
        });
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtPassword.getText().equals("  Contraseña")) {
                    txtPassword.setText("");
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtPassword.getText().isEmpty()) {
                    txtPassword.setText("  Contraseña");
                }
            }
        });
    }
    private boolean validateLogin() {
        String dni = txtID.getText().trim();
        String password = txtPassword.getText().trim();
        if (dni.isEmpty() || dni.equals("  DNI/NIE")) {
            JOptionPane.showMessageDialog(this, 
                "El campo DNI/NIE está vacío. \nPor favor, introduce tu DNI/NIE.", 
                "Campo Vacío", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (password.isEmpty() || password.equals("  Contraseña")) {
            JOptionPane.showMessageDialog(this, 
                "El campo Contraseña está vacío. \nPor favor, introduce tu contraseña.", 
                "Campo Vacío", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
      
        try {
            Integer.parseInt(dni);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El DNI/NIE debe ser un número válido.", 
                "Error de Formato", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}