import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;

public class AsientosGUI extends JFrame {

    private JButton jbCancelarReserva;
    private JButton jbReservar;
    private JScrollPane jspAsientos;
    private JTable jtAsientos;
    private Timer inicioPeliculaTimer;
    private Timer finPeliculaTimer;

    private Compra ventanaCompra;
    private Sala sala;

    private static final String FS = File.separator;
    private static final ImageIcon ASIENTO_LIBRE = new ImageIcon("." + FS + "assets" + FS + "asiento_libre.jpg");
    private static final ImageIcon ASIENTO_OCUPADO = new ImageIcon("." + FS + "assets" + FS + "asiento_ocupado.jpg");
    private static final ImageIcon ASIENTO_RESERVADO = new ImageIcon("." + FS + "assets" + FS + "asiento_reservado.jpg");

    // Constructor
    public AsientosGUI(Sala sala) {
    	this.sala = sala;
        initComponents();
        jtAsientos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jtAsientos.setCellSelectionEnabled(true);

        jbReservar.addActionListener(e -> reservarAsiento());
        jbCancelarReserva.addActionListener(e -> cancelarReserva());
        configuracionTemporizadores();
    }

    private void configuracionTemporizadores() {
        inicioPeliculaTimer = new Timer(20 * 1000, e -> {
            JOptionPane.showMessageDialog(this, "La película ha comenzado. No se pueden comprar más asientos.");
            setInteraccion(false);
        });

        finPeliculaTimer = new Timer(30 * 1000, e -> {
            JOptionPane.showMessageDialog(this, "La película ha terminado. Puedes seleccionar asientos nuevamente.");
            setInteraccion(true);
            realizarRifa(); // Realiza la rifa después de que termine la película
            resetearAsientosOcupados();
        });

        inicioPeliculaTimer.setRepeats(false);
        finPeliculaTimer.setRepeats(false);
        inicioPeliculaTimer.start();
        finPeliculaTimer.start();
    }
    
    private void resetearAsientosOcupados() {
        for (int row = 0; row < jtAsientos.getRowCount(); row++) {
            for (int col = 0; col < jtAsientos.getColumnCount(); col++) {
                Object currentValue = jtAsientos.getValueAt(row, col);
                if (currentValue instanceof ImageIcon && currentValue.equals(ASIENTO_OCUPADO)) {
                    jtAsientos.setValueAt(ASIENTO_LIBRE, row, col);
                }
            }
        }
    }

    private void realizarRifa() {
        String mensajeRifa = sala.iniciarRifa();
        JOptionPane.showMessageDialog(this, mensajeRifa);
    }


    private void setInteraccion(boolean habilitado) {
        jbReservar.setEnabled(habilitado);
        jbCancelarReserva.setEnabled(habilitado);
        jtAsientos.setEnabled(habilitado);
    }

    private void initComponents() {
        jspAsientos = new javax.swing.JScrollPane();
        jtAsientos = new JTable();
        jbCancelarReserva = new javax.swing.JButton();
        jbReservar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtAsientos.setModel(new DefaultTableModel(
                new Object[][]{
                    {ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE},
                    {ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE},
                    {ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE},
                    {ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE},
                    {ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE},
                    {ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE},
                    {ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE},
                    {ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE, ASIENTO_LIBRE}
                },
                new String[]{
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                }
        ) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });

        jtAsientos.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();

                if (value instanceof ImageIcon) {
                    ImageIcon icon = (ImageIcon) value;

                    int cellWidth = table.getColumnModel().getColumn(column).getWidth();
                    int cellHeight = table.getRowHeight(row);

                    double aspectRatio = (double) icon.getIconWidth() / icon.getIconHeight();

                    int newWidth = cellWidth;
                    int newHeight = (int) (newWidth / aspectRatio);

                    if (newHeight > cellHeight) {
                        newHeight = cellHeight;
                        newWidth = (int) (newHeight * aspectRatio);
                    }

                    Image image = icon.getImage();
                    Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaledImage));
                    label.setPreferredSize(new Dimension(cellWidth, cellHeight));
                }

                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);

                if (table.isCellSelected(row, column)) {
                    label.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                } else {
                    label.setBorder(null);
                }

                return label;
            }
        });

        for (int i = 0; i < jtAsientos.getColumnCount(); i++) {
            jtAsientos.getColumnModel().getColumn(i).setPreferredWidth(ASIENTO_LIBRE.getIconWidth());
        }

        for (int i = 0; i < jtAsientos.getColumnCount(); i++) {
            jtAsientos.getColumnModel().getColumn(i).setCellEditor(null);
        }

        jtAsientos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        jspAsientos.setViewportView(jtAsientos);
        jtAsientos.getTableHeader().setUI(null);

        jbCancelarReserva.setText("Cancelar Compra");

        jbReservar.setText("Comprar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jspAsientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jbCancelarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jbReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap()
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jspAsientos, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jbCancelarReserva)
                                .addComponent(jbReservar))
                        .addContainerGap()
        );

        pack();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustRowHeight();
            }
        });
    }

    private void adjustRowHeight() {
        int rowCount = jtAsientos.getRowCount();
        int tableHeight = jspAsientos.getViewport().getHeight();
        int rowHeight = tableHeight / rowCount;

        jtAsientos.setRowHeight(rowHeight);
    }

    private void reservarAsiento() {
        int cantidadSeleccionada = 0;

        for (int i = 0; i < jtAsientos.getRowCount(); i++) {
            for (int j = 0; j < jtAsientos.getColumnCount(); j++) {
                if (jtAsientos.isCellSelected(i, j)) {
                    if (jtAsientos.getValueAt(i, j) == ASIENTO_LIBRE) {
                        jtAsientos.setValueAt(ASIENTO_RESERVADO, i, j);
                        cantidadSeleccionada++;
                    }
                }
            }
        }

        if (cantidadSeleccionada > 0) {
            Compra compra = new Compra(this);
            compra.actualizarCantidadAsientos(cantidadSeleccionada);
            compra.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona al menos un asiento.");
        }
    }

    public void liberarAsientosReservados() {
        for (int row = 0; row < jtAsientos.getRowCount(); row++) {
            for (int col = 0; col < jtAsientos.getColumnCount(); col++) {
                Object currentValue = jtAsientos.getValueAt(row, col);
                if (currentValue instanceof ImageIcon && currentValue.equals(ASIENTO_RESERVADO)) {
                    jtAsientos.setValueAt(ASIENTO_LIBRE, row, col);
                }
            }
        }
    }

    public void marcarAsientosComoOcupados() {
        Usuario usuario = new Usuario(); // Puedes usar un usuario real en tu flujo
        usuario.setId(1); // ID estático para pruebas
        usuario.setNombre("Usuario Prueba");

        List<Asiento> asientosVendidos = new ArrayList<>();

        for (int row = 0; row < jtAsientos.getRowCount(); row++) {
            for (int col = 0; col < jtAsientos.getColumnCount(); col++) {
                Object currentValue = jtAsientos.getValueAt(row, col);
                if (currentValue instanceof ImageIcon && currentValue.equals(ASIENTO_RESERVADO)) {
                    jtAsientos.setValueAt(ASIENTO_OCUPADO, row, col);

                    // Registra el asiento en Sala
                    Asiento asiento = new Asiento();
                    asiento.setId(row * jtAsientos.getColumnCount() + col); // Genera un ID único para el asiento
                    asiento.setEstado(EstadoAsiento.VENDIDO);
                    asiento.setUsuarioReservado(usuario);
                    asientosVendidos.add(asiento);
                }
            }
        }

        if (!asientosVendidos.isEmpty()) {
            sala.getAsientos().computeIfAbsent(usuario, k -> new ArrayList<>()).addAll(asientosVendidos);
        }
    }

    public void setVentanaCompra(Compra ventanaCompra) {
        this.ventanaCompra = ventanaCompra;
    }

    private void cancelarReserva() {
        int[] selectedRows = jtAsientos.getSelectedRows();
        int[] selectedColumns = jtAsientos.getSelectedColumns();
        for (int row : selectedRows) {
            for (int col : selectedColumns) {
                Object currentValue = jtAsientos.getValueAt(row, col);
                if (currentValue instanceof ImageIcon && currentValue.equals(ASIENTO_OCUPADO)) {
                    jtAsientos.setValueAt(ASIENTO_LIBRE, row, col);
                    JOptionPane.showMessageDialog(rootPane, "Asientos cancelados. El dinero se ha enviado a tu cuenta.");
                }
            }
        }
    }
}
