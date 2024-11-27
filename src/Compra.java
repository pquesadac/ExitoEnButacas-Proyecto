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

public class Compra extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	private JTextField txt_precio;
	private JPanel panel;
	private final int precio_asiento = 5;

	private static final String FS = File.separator;
	private static final ImageIcon CARRITO = new ImageIcon("." + FS + "assets" + FS + "carrito.png");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Compra dialog = new Compra();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Compra() {
		setBounds(100, 100, 523, 379);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(0, 128, 192));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel imageLabel = new JLabel();
		imageLabel.setBounds(10, 10, 100, 100);
		imageLabel.setIcon(CARRITO);
		contentPanel.add(imageLabel);

		JLabel lbl1 = new JLabel("HA COMPRADO:");
		lbl1.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
		lbl1.setBounds(30, 166, 210, 27);
		contentPanel.add(lbl1);

		JLabel lbl3 = new JLabel("ASIENTOS");
		lbl3.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
		lbl3.setBounds(360, 166, 159, 27);
		contentPanel.add(lbl3);

		JLabel lbl2_n_asientos = new JLabel("0");
		lbl2_n_asientos.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
		lbl2_n_asientos.setBounds(290, 166, 45, 27);
		contentPanel.add(lbl2_n_asientos);

		JLabel lbl4 = new JLabel("PRECIO POR ASIENTO:");
		lbl4.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
		lbl4.setBounds(30, 203, 240, 27);
		contentPanel.add(lbl4);

		String precio = String.valueOf(precio_asiento);
		JLabel lbl5 = new JLabel(precio);
		lbl5.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
		lbl5.setBounds(290, 203, 45, 27);
		contentPanel.add(lbl5);

		JLabel lbl6 = new JLabel("€");
		lbl6.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
		lbl6.setBounds(360, 203, 159, 27);
		contentPanel.add(lbl6);

		JLabel lblTotal = new JLabel("TOTAL:");
		lblTotal.setFont(new Font("Book Antiqua", Font.PLAIN, 25));
		lblTotal.setBounds(192, 252, 102, 52);
		contentPanel.add(lblTotal);

		JLabel lbl7 = new JLabel("€");
		lbl7.setFont(new Font("Book Antiqua", Font.PLAIN, 30));
		lbl7.setBounds(471, 250, 39, 52);
		contentPanel.add(lbl7);

		txt_precio = new JTextField();
		txt_precio.setBounds(297, 261, 164, 27);
		contentPanel.add(txt_precio);
		txt_precio.setColumns(10);

		panel = new JPanel();
		panel.setBounds(0, 0, 509, 137);
		panel.setLayout(null);
		contentPanel.add(panel);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btn_pagar = new JButton("Pagar");
				btn_pagar.setActionCommand("OK");
				buttonPane.add(btn_pagar);
				getRootPane().setDefaultButton(btn_pagar);
			}
			{
				JButton btn_cancelar = new JButton("Cancelar");
				btn_cancelar.setActionCommand("Cancel");
				buttonPane.add(btn_cancelar);
			}
		}
	}
}