import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GUI extends javax.swing.JFrame {
    private Sala salaCine;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;

    public GUI(Sala salaCine) {
        initComponents();

        this.salaCine = salaCine;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new ModeloTablaDeAsientos(salaCine.getAsientos()));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }

    public static class TablaDeAsientos {
        private JTable table;
        private ConcurrentHashMap<String, Asiento> dataMap;

        public TablaDeAsientos() {
            dataMap = new ConcurrentHashMap<>();
            table = new JTable(new ModeloTablaDeAsientos(dataMap));

            // Add the table to a scroll pane and display it
            JScrollPane scrollPane = new JScrollPane(table);
            JFrame frame = new JFrame("Table Example");
            frame.add(scrollPane);
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    static class ModeloTablaDeAsientos extends AbstractTableModel {
        //private final List<Map.Entry<String, Asiento>> dataList;

        public ModeloTablaDeAsientos(ConcurrentHashMap<Usuario, List<Asiento>> map) {
            // Convert the map entries to a List for easier handling
            //dataList = new ArrayList<>(map.entrySet());
        }

        @Override
        public int getRowCount() {
            return dataList.size();  // Number of rows equals the number of entries in the map
        }

        @Override
        public int getColumnCount() {
            return 2;  // Two columns: one for key and one for value
        }







        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Map.Entry<String, Asiento> entry = dataList.get(rowIndex);
            if (columnIndex == 0) {
                return entry.getKey();  // Key column
            } else if (columnIndex == 1) {
                return entry.getValue();  // Value column
            }
            return null;
        }

        @Override
        public String getColumnName(int column) {
            return switch (column) {
                case 0 -> "Key";
                case 1 -> "Value";
                default -> "";
            };
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 0 -> String.class;  // The key is a String
                case 1 -> Asiento.class;  // The value is an object of type MyObject
                default -> Object.class;
            };
        }
    }
}
