/**
 * Interfaz gráfica inicial para que los usuarios inicien sesión.
 * Permite al usuario ingresar un identificador único (ID) y un nombre
 * antes de acceder al sistema de selección de asientos.
 */

package view;

import java.awt.Font;
import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Sala;
import model.Usuario;

public class LoginGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtID;
    private JTextField txtName;
    private Sala sala;

    public LoginGUI() {
    	this.sala = new Sala();
    
     // Configuración de la ventana principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 420, 475);

        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.inactiveCaption);
        contentPane.setForeground(SystemColor.inactiveCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Título del login
        JLabel lblLoginTitle = new JLabel("Login");
        lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoginTitle.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
        lblLoginTitle.setBounds(104, 30, 197, 47);
        contentPane.add(lblLoginTitle);

        // Campo de texto para ID
        txtID = new JTextField();
        txtID.setText("  ID");
        txtID.setBounds(53, 175, 305, 35);
        txtID.setColumns(10);
        contentPane.add(txtID);

        // Campo de texto para Nombre
        txtName = new JTextField();
        txtName.setText("  Nombre");
        txtName.setBounds(53, 233, 305, 35);
        txtName.setColumns(10);
        contentPane.add(txtName);

        // Botón de acceso
        JButton btnAcceso = new JButton("ACCEDER");
        btnAcceso.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
        btnAcceso.setBounds(138, 316, 134, 35);
        contentPane.add(btnAcceso);

        // Acción del botón "Acceder"
        btnAcceso.addActionListener(e -> {
            if (validateLogin()) {
                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(txtID.getText().trim())); // Obtén el ID del campo
                usuario.setNombre(txtName.getText().trim()); // Obtén el nombre del campo

                sala.getAsientos().putIfAbsent(usuario, new ArrayList<>()); // Registra usuario en la sala

                AsientosGUI asientosGUI = new AsientosGUI(sala, usuario);
                asientosGUI.setVisible(true);
                dispose(); // Cierra la ventana de login
            }
        });

        addPlaceholderBehavior();
    }


    
 // Método que maneja el comportamiento de los placeholders en los campos de texto
  private void addPlaceholderBehavior() {

      // Comportamiento para el campo de texto ID cuando recibe el foco
        txtID.addFocusListener(new java.awt.event.FocusAdapter() {
             // Se limpia el texto cuando el campo recibe el foco, si es el texto predeterminado
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtID.getText().equals("  ID")) {
                    txtID.setText(""); // Borra el texto predeterminado
                }
            }

            // Se restablece el texto predeterminado si el campo se queda vacío al perder el foco
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtID.getText().isEmpty()) {
                    txtID.setText("  ID"); //Restaura el texto predeterminado
                }
            }
        });

            // Comportamiento para el campo de texto Nombre cuando recibe el foco
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            // Se limpia el texto cuando el campo recibe el foco, si es el texto predeterminado
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtName.getText().equals("  Nombre")) {
                    txtName.setText(""); //Borra el texto predeterminado
                }
            }
    
            // Se restablece el texto predeterminado si el campo se queda vacío al perder el foco
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtName.getText().isEmpty()) {
                    txtName.setText("  Nombre"); //Restaura el texto predeterminado
                }
            }
        });
    }

    // Método que valida si los campos del formulario están completos y si el ID es numérico
  private boolean validateLogin() {
	    String id = txtID.getText().trim(); // Obtiene el ID ingresado y elimina espacios adicionales  
	    String nombre = txtName.getText().trim(); // Obtiene el nombre ingresado y elimina espacios adicionales

        // Verifica si el campo ID está vacío o tiene el valor predeterminado
	    if (id.isEmpty() || id.equals("ID")) {
	        JOptionPane.showMessageDialog(this,
	            "El campo ID está vacío. \nPor favor, introduce tu ID.",
	            "Campo Vacío",
	            JOptionPane.WARNING_MESSAGE);
	        return false; // Retorna falso si el ID no es válido
	    }

        // Verifica si el campo Nombre está vacío o tiene el valor predeterminado
	    if (nombre.isEmpty() || nombre.equals("Nombre")) {
	        JOptionPane.showMessageDialog(this,
	            "El campo Nombre está vacío. \nPor favor, introduce tu nombre.",
	            "Campo Vacío",
	            JOptionPane.WARNING_MESSAGE);
	        return false; // Retorna falso si el nombre no es válido
	    }
  
  // Verifica si el ID es un número válido
	  try {
	      Integer.parseInt(id); //Intentamos pasar el ID a un número
	  } catch (NumberFormatException e) {
	      JOptionPane.showMessageDialog(this,
	          "El ID debe ser un número válido.",
	          "Error de Formato",
	          JOptionPane.ERROR_MESSAGE);
	      return false; // Devuelve falso si el ID no es numérico
	  }
	  return true; // Devuelve verdadero si todos los campos son válidos
	}
}