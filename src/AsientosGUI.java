package com.mycompany.iniciogui;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JLabel;
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
import com.mycompany.iniciogui.Compra;


public class AsientosGUI extends JFrame {

    private JButton jbCancelarReserva;
    private JButton jbReservar;
    private JScrollPane jspAsientos;
    private JTable jtAsientos;

    private static final String FS = File.separator;
    private static final ImageIcon ASIENTO_LIBRE = new ImageIcon("." + FS + "assets" + FS + "asiento_libre.jpg");
    private static final ImageIcon ASIENTO_OCUPADO = new ImageIcon("." + FS + "assets" + FS + "asiento_ocupado.jpg");

    // Constructor
    public AsientosGUI() {
        initComponents();
        jtAsientos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jtAsientos.setCellSelectionEnabled(true);
        // Add ActionListener to the "Comprar" button (jbReservar)
        jbReservar.addActionListener(e -> reservarAsiento()); // 
        jbCancelarReserva.addActionListener(e -> cancelarReserva());
    }

    private void initComponents() {
        jspAsientos = new javax.swing.JScrollPane();
        jtAsientos = new JTable();
        jbCancelarReserva = new javax.swing.JButton();
        jbReservar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Create a custom table model with all cells non-editable
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
            // Override isCellEditable to make all cells non-editable
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false; // Disable editing for all cells
            }
        });

        // Set custom cell renderer for the table
        jtAsientos.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();

                if (value instanceof ImageIcon) {
                    ImageIcon icon = (ImageIcon) value;

                    // Get the size of the cell
                    int cellWidth = table.getColumnModel().getColumn(column).getWidth();
                    int cellHeight = table.getRowHeight(row);

                    // Calculate the aspect ratio of the image
                    double aspectRatio = (double) icon.getIconWidth() / icon.getIconHeight();

                    // Calculate new dimensions while preserving the aspect ratio
                    int newWidth = cellWidth;
                    int newHeight = (int) (newWidth / aspectRatio);

                    // If the new height exceeds the cell height, adjust width accordingly
                    if (newHeight > cellHeight) {
                        newHeight = cellHeight;
                        newWidth = (int) (newHeight * aspectRatio);
                    }

                    // Scale the image to fit the cell while preserving aspect ratio
                    Image image = icon.getImage();
                    Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaledImage)); // Set the resized image as the label's icon
                    label.setPreferredSize(new Dimension(cellWidth, cellHeight)); // Ensure the label size matches the cell
                }

                // Center the image in the cell
                label.setHorizontalAlignment(JLabel.CENTER);  // Horizontal centering
                label.setVerticalAlignment(JLabel.CENTER);    // Vertical centering

                // Check if this cell is the selected one, apply a green border only if true
               if (table.isCellSelected(row, column)) {
                    label.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));  // Green border for selected cell
                } else {
                    label.setBorder(null);  // No border for non-selected cells
                }

                return label;
            }
        });

        // Adjust the column widths based on the image size
        for (int i = 0; i < jtAsientos.getColumnCount(); i++) {
            jtAsientos.getColumnModel().getColumn(i).setPreferredWidth(ASIENTO_LIBRE.getIconWidth());
        }

        // Disable cell editor to prevent modification
        for (int i = 0; i < jtAsientos.getColumnCount(); i++) {
            jtAsientos.getColumnModel().getColumn(i).setCellEditor(null); // Disable the cell editor for all columns
        }

        // Set the JTable to automatically resize columns and adjust table dimensions
        jtAsientos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Ensure the table adjusts its size with the window
        jspAsientos.setViewportView(jtAsientos);
        jtAsientos.getTableHeader().setUI(null);

        jbCancelarReserva.setText("Cancelar Compra");

        jbReservar.setText("Comprar");

        // Use GroupLayout to manage components dynamically
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        /* Cambiar automáticamente el tamaño de los componentes cuando varíe el
         * tamaño de la ventana.
         */
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
                        .addComponent(jspAsientos, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE) // Ensure table expands
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jbCancelarReserva)
                                .addComponent(jbReservar))
                        .addContainerGap()
        );

        pack();

        /* Añadir un ComponentListener para ajustar la altura de las filas de la
         * tabla cuando cambie el tamaño de la tabla.
         */
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustRowHeight();
            }
        });
    }

    /**
     * Ajusta la altura de las filas de forma dinámica.
     */
    private void adjustRowHeight() {
        int rowCount = jtAsientos.getRowCount();
        int tableHeight = jspAsientos.getViewport().getHeight(); // Get the height of the visible area of the table
        int rowHeight = tableHeight / rowCount; // Divide the height by the number of rows

        /* Establecer la altura de las filas.
         */
        jtAsientos.setRowHeight(rowHeight);
    }
    
    /**
     * Establece el asiento seleccionado como ocupado.
     */
private void reservarAsiento() {
    int[] selectedRows = jtAsientos.getSelectedRows();
    int[] selectedColumns = jtAsientos.getSelectedColumns();
    int numAsientos = 0; 

    for (int row : selectedRows) {
        for (int col : selectedColumns) {
            Object currentValue = jtAsientos.getValueAt(row, col);
            if (currentValue instanceof ImageIcon && currentValue.equals(ASIENTO_LIBRE)) {
                jtAsientos.setValueAt(ASIENTO_OCUPADO, row, col);
                numAsientos++; 
            }
        }
    }

    if (numAsientos > 0) {
        Compra ventanaCompra = new Compra(this);
        ventanaCompra.setVisible(true);
        ventanaCompra.actualizarTotal(numAsientos * 5); 
    } else {
        System.out.println("No ha seleccionado ningún asiento.");
    }
}

public void restaurarColorAsientos() {
    for (int row = 0; row < jtAsientos.getRowCount(); row++) {
        for (int col = 0; col < jtAsientos.getColumnCount(); col++) {
            Object currentValue = jtAsientos.getValueAt(row, col);
            if (currentValue instanceof ImageIcon && currentValue.equals(ASIENTO_OCUPADO)) {
                jtAsientos.setValueAt(ASIENTO_LIBRE, row, col); 
            }
        }
    }
}


    private void cancelarReserva() {
        int[] selectedRows = jtAsientos.getSelectedRows();
        int[] selectedColumns = jtAsientos.getSelectedColumns();
        for (int row : selectedRows) {
            for (int col : selectedColumns) {
                Object currentValue = jtAsientos.getValueAt(row, col);
                if (currentValue instanceof ImageIcon && currentValue.equals(ASIENTO_OCUPADO)) {
                    jtAsientos.setValueAt(ASIENTO_LIBRE, row, col);
                }
            }
        }
    }
}
