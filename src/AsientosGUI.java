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

	private JButton jbVolverAtras;
    private JButton jbReservar;
    private JScrollPane jspAsientos;
    private JTable jtAsientos;
    private Timer inicioPeliculaTimer;
    private Timer finPeliculaTimer;

    private Compra ventanaCompra;
    private Sala sala;
    private Usuario usuario;

    private static final String FS = File.separator;
    private static final ImageIcon ASIENTO_LIBRE = new ImageIcon("." + FS + "assets" + FS + "asiento_libre.jpg");
    private static final ImageIcon ASIENTO_OCUPADO = new ImageIcon("." + FS + "assets" + FS + "asiento_ocupado.jpg");
    private static final ImageIcon ASIENTO_RESERVADO = new ImageIcon("." + FS + "assets" + FS + "asiento_reservado.jpg");

    // Constructor
    public AsientosGUI(Sala sala, Usuario usuario) {
        this.sala = sala;
        this.usuario = usuario;
        initComponents();
        jtAsientos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jtAsientos.setCellSelectionEnabled(true);

        jbReservar.addActionListener(e -> reservarAsiento());
        jbVolverAtras.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
            this.dispose(); 
        });
        configuracionTemporizadores();
        setTitle("Selección de Asientos - " + usuario.getNombre());
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

    Timer cierreProgramaTimer = new Timer(5 * 1000, e -> {
        System.exit(0); 
    });
    cierreProgramaTimer.setRepeats(false); 
    cierreProgramaTimer.start();
}

    private void setInteraccion(boolean habilitado) {
        jbReservar.setEnabled(habilitado);
        jbVolverAtras.setEnabled(habilitado);
        jtAsientos.setEnabled(habilitado);
    }

    private void initComponents() {
        jspAsientos = new javax.swing.JScrollPane();
        jtAsientos = new JTable();
        jbVolverAtras = new javax.swing.JButton();
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

        jbVolverAtras.setText("Volver Atrás");

        jbReservar.setText("Comprar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jspAsientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jbVolverAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(jbVolverAtras)
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

        List<Integer> asientosAReservar = new ArrayList<>();
        int cantidadSeleccionada = 0;
        boolean tieneAsientoOcupado = false;

        // Primero, tenemos que verificar el estado de los asientos seleccionados
        for (int i = 0; i < jtAsientos.getRowCount(); i++) {
            for (int j = 0; j < jtAsientos.getColumnCount(); j++) {
                if (jtAsientos.isCellSelected(i, j)) {
                    Object valorAsiento = jtAsientos.getValueAt(i, j);
                    int idAsiento = i * jtAsientos.getColumnCount() + j;

                    if (valorAsiento == ASIENTO_LIBRE) {
                        asientosAReservar.add(idAsiento);
                        cantidadSeleccionada++;
                    } else if (valorAsiento == ASIENTO_OCUPADO) {
                        tieneAsientoOcupado = true;
                        break;
                    }
                }
            }
            if (tieneAsientoOcupado) {
                break;
            }
        }

        if (tieneAsientoOcupado) {
            JOptionPane.showMessageDialog(this, "No puedes seleccionar asientos ya comprados.");
            return;
        }
        // Si hay asientos libres, podemos seguir con la reserva

        if (cantidadSeleccionada > 0) {
        	// Intentamos reservar los asientos en la Sala
            boolean reservaExitosa = sala.reservarAsientos(usuario, asientosAReservar);
            if (reservaExitosa) {
                // Marcamos asientos como reservados en la interfaz
                for (int i = 0; i < jtAsientos.getRowCount(); i++) {
                    for (int j = 0; j < jtAsientos.getColumnCount(); j++) {
                        int idAsiento = i * jtAsientos.getColumnCount() + j;
                        if (asientosAReservar.contains(idAsiento)) {
                            jtAsientos.setValueAt(ASIENTO_RESERVADO, i, j);
                        }
                    }
                }
                Compra compra = new Compra(this);
                compra.actualizarCantidadAsientos(cantidadSeleccionada);
                compra.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudieron reservar los asientos. Algunos ya están ocupados.");
            }
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
                    asiento.setUsuarioReservado(usuario); // Asocia al usuario actual
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

    private void initializeRealTimeUpdates() {
        Timer updateTimer = new Timer(1000, e -> {
            actualizarEstadoAsientos();
        });
        updateTimer.start();
    }

    private void actualizarEstadoAsientos() {
        for (int row = 0; row < jtAsientos.getRowCount(); row++) {
            for (int col = 0; col < jtAsientos.getColumnCount(); col++) {
                int asientoId = row * jtAsientos.getColumnCount() + col;
                EstadoAsiento estado = obtenerEstadoAsiento(asientoId);

                switch (estado) {
                    case LIBRE:
                        jtAsientos.setValueAt(ASIENTO_LIBRE, row, col);
                        break;
                    case RESERVADO:
                        jtAsientos.setValueAt(ASIENTO_RESERVADO, row, col);
                        break;
                    case VENDIDO:
                        jtAsientos.setValueAt(ASIENTO_OCUPADO, row, col);
                        break;
                }
            }
        }
    }

    private EstadoAsiento obtenerEstadoAsiento(int asientoId) {
        for (List<Asiento> asientosList : sala.getAsientos().values()) {
            for (Asiento asiento : asientosList) {
                if (asiento.getId() == asientoId) {
                    return asiento.getEstado();
                }
            }
        }
        return EstadoAsiento.LIBRE;
    }
    
    public void actualizarAsiento(int asientoId, EstadoAsiento estado) {
        int rows = jtAsientos.getRowCount();
        int cols = jtAsientos.getColumnCount();

        int row = asientoId / cols;
        int col = asientoId % cols;

        if (row < rows && col < cols) {
            switch (estado) {
                case LIBRE:
                    jtAsientos.setValueAt(ASIENTO_LIBRE, row, col);
                    break;
                case RESERVADO:
                    jtAsientos.setValueAt(ASIENTO_RESERVADO, row, col);
                    break;
                case VENDIDO:
                    jtAsientos.setValueAt(ASIENTO_OCUPADO, row, col);
                    break;
            }
        }
        jtAsientos.repaint();
    }

  public void ocultarBotones() {
	  jbReservar.setVisible(false);
	  jbVolverAtras.setVisible(false);
  }
    
}