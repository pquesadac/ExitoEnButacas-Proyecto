/**
 * Interfaz gráfica principal para la selección y compra de asientos.
 * Muestra un mapa de la sala con el estado actual de cada asiento (libre, reservado, ocupado).
 * Permite al usuario realizar acciones como reservar o cancelar reservas.
 */

package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import controller.Sala;
import model.Asiento;
import model.EstadoAsiento;
import model.Usuario;

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
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

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

    /**
     * Constructor de la clase AsientosGUI.
     * Este constructor inicializa la interfaz gráfica para la selección de asientos de un usuario en la sala.
     * Además, configura los eventos de los botones y el comportamiento general de la ventana.
     */
    public AsientosGUI(Sala sala, Usuario usuario) {
        // Asigna la sala y el usuario actual a las variables de la clase.
        this.sala = sala;
        this.usuario = usuario;

        // Inicializa los componentes de la ventana (botones, tabla de asientos, etc.).
        initComponents();

        // Configura la tabla de asientos para permitir seleccionar múltiples celdas.
        jtAsientos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jtAsientos.setCellSelectionEnabled(true);

        // Configura el botón "Reservar". Cuando se haga clic, se ejecutará el método "reservarAsiento".
        jbReservar.addActionListener(e -> reservarAsiento());

        // Configura el botón "Volver Atrás". Cuando se haga clic:
        // 1. Se abrirá la ventana de inicio de sesión (LoginGUI).
        // 2. Se cerrará la ventana actual.
        jbVolverAtras.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI(); // Crea una nueva ventana de inicio de sesión.
            loginGUI.setVisible(true);          // La muestra al usuario.
            this.dispose();                     // Cierra la ventana actual.
        });

        // Configura los temporizadores que controlan eventos como el inicio y el fin de la película.
        configuracionTemporizadores();

        // Establece el título de la ventana con el nombre del usuario actual.
        setTitle("Selección de Asientos - " + usuario.getNombre());
    }

    private void initComponents() {
        // Creamos los componentes principales de la ventana
        jspAsientos = new javax.swing.JScrollPane(); // Área desplazable donde pondremos la tabla de asientos
        jtAsientos = new JTable(); // La tabla donde se mostrarán los asientos
        jbVolverAtras = new javax.swing.JButton(); // Botón para volver al menú anterior
        jbReservar = new javax.swing.JButton(); // Botón para realizar la compra de asientos

        // Configuración básica para cerrar la ventana
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Creamos el modelo de la tabla con datos iniciales (todos los asientos están libres)
        jtAsientos.setModel(new DefaultTableModel(
                new Object[][]{
                    // Cada fila representa una fila de asientos en el cine. 
                    // Todos los asientos empiezan con el estado "LIBRE".
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
                    // Este array define las "columnas" de la tabla. En este caso, no tienen título porque son asientos.
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                }
        ) {
            // Hacemos que las celdas de la tabla no puedan ser editadas
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false; // Ninguna celda puede ser modificada
            }
        });

        // Creamos un renderer para personalizar cómo se ven los asientos en la tabla
        jtAsientos.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(); // Cada celda se representará como una etiqueta (JLabel)

                if (value instanceof ImageIcon) { // Si la celda contiene un icono (imagen de asiento)
                    ImageIcon icon = (ImageIcon) value;

                    // Calculamos el tamaño de la imagen para que se ajuste a la celda
                    int cellWidth = table.getColumnModel().getColumn(column).getWidth();
                    int cellHeight = table.getRowHeight(row);
                    double aspectRatio = (double) icon.getIconWidth() / icon.getIconHeight();
                    int newWidth = cellWidth;
                    int newHeight = (int) (newWidth / aspectRatio);

                    if (newHeight > cellHeight) {
                        newHeight = cellHeight;
                        newWidth = (int) (newHeight * aspectRatio);
                    }

                    // Redimensionamos la imagen para que se ajuste perfectamente a la celda
                    Image image = icon.getImage();
                    Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaledImage)); // Asignamos la imagen redimensionada a la etiqueta
                    label.setPreferredSize(new Dimension(cellWidth, cellHeight)); // Ajustamos su tamaño
                }

                // Centramos la imagen dentro de la celda
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);

                // Si la celda está seleccionada, ponemos un borde verde
                if (table.isCellSelected(row, column)) {
                    label.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                } else {
                    label.setBorder(null); // Si no está seleccionada, no tiene borde
                }

                return label; // Devolvemos la etiqueta como representación de la celda
            }
        });

        // Configuramos las columnas de la tabla para que las imágenes se vean correctamente
        for (int i = 0; i < jtAsientos.getColumnCount(); i++) {
            jtAsientos.getColumnModel().getColumn(i).setPreferredWidth(ASIENTO_LIBRE.getIconWidth()); // Ajustamos el ancho
        }

        // Deshabilitamos los editores de celdas para que no se pueda interactuar con ellas directamente
        for (int i = 0; i < jtAsientos.getColumnCount(); i++) {
            jtAsientos.getColumnModel().getColumn(i).setCellEditor(null);
        }

        // Hacemos que las celdas se ajusten automáticamente
        jtAsientos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Asignamos la tabla al área desplazable (scroll pane)
        jspAsientos.setViewportView(jtAsientos);
        jtAsientos.getTableHeader().setUI(null); // Quitamos el encabezado de la tabla (porque no necesitamos títulos)

        // Configuramos el texto de los botones
        jbVolverAtras.setText("Volver Atrás"); // Botón para volver a la pantalla anterior
        jbReservar.setText("Comprar"); // Botón para comprar los asientos seleccionados

        // Creamos un diseño para organizar los componentes dentro de la ventana
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Organizamos los componentes horizontalmente
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspAsientos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Tabla de asientos
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbVolverAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE) // Botón "Volver Atrás"
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))) // Botón "Comprar"
                .addContainerGap()
        );

        // Organizamos los componentes verticalmente
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspAsientos, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE) // Tabla de asientos
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbVolverAtras) // Botón "Volver Atrás"
                    .addComponent(jbReservar)) // Botón "Comprar"
                .addContainerGap()
        );

        // Ajustamos la ventana al contenido
        pack();

        // Añadimos un evento para ajustar el tamaño de las filas de la tabla al tamaño de la ventana
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustRowHeight(); // Método para ajustar la altura de las filas
            }
        });
    }

    // Este método ajusta automáticamente la altura de las filas de la tabla de asientos 
    // para que se distribuyan de manera uniforme en el espacio visible del panel que las contiene.
    private void adjustRowHeight() {
        // Obtiene la cantidad total de filas que hay en la tabla.
        int totalFilas = jtAsientos.getRowCount();
        
        // Calcula la altura disponible en el área visible del panel donde está la tabla.
        int alturaDisponible = jspAsientos.getViewport().getHeight();
        
        // Divide la altura total disponible entre el número de filas para que todas tengan el mismo tamaño.
        int alturaPorFila = alturaDisponible / totalFilas;

        // Establece la nueva altura para todas las filas de la tabla.
        jtAsientos.setRowHeight(alturaPorFila);
    }

    private void reservarAsiento() {

        // Creamos una lista para almacenar los asientos que queremos reservar
        List<Integer> asientosAReservar = new ArrayList<>();
        int cantidadSeleccionada = 0; // Contador para la cantidad de asientos seleccionados
        boolean tieneAsientoOcupado = false; // Indicador para saber si hay algún asiento ya ocupado

        // Recorremos toda la tabla de asientos para comprobar cuáles están seleccionados
        for (int i = 0; i < jtAsientos.getRowCount(); i++) {
            for (int j = 0; j < jtAsientos.getColumnCount(); j++) {
                // Verificamos si la celda actual está seleccionada
                if (jtAsientos.isCellSelected(i, j)) {
                    // Obtenemos el estado del asiento (libre, ocupado o reservado)
                    Object valorAsiento = jtAsientos.getValueAt(i, j);

                    // Calculamos el identificador único del asiento (basado en la posición en la tabla)
                    int idAsiento = i * jtAsientos.getColumnCount() + j;

                    // Si el asiento está libre, lo añadimos a la lista de asientos a reservar
                    if (valorAsiento == ASIENTO_LIBRE) {
                        asientosAReservar.add(idAsiento);
                        cantidadSeleccionada++; // Incrementamos el contador de asientos seleccionados
                    } 
                    // Si el asiento está ocupado, marcamos que no podemos continuar con la reserva
                    else if (valorAsiento == ASIENTO_OCUPADO) {
                        tieneAsientoOcupado = true;
                        break; // Salimos del bucle si encontramos un asiento ocupado
                    }
                }
            }
            // Si encontramos un asiento ocupado, no continuamos revisando los demás
            if (tieneAsientoOcupado) {
                break;
            }
        }

        // Si hay un asiento ocupado, mostramos un mensaje de error y no permitimos la reserva
        if (tieneAsientoOcupado) {
            JOptionPane.showMessageDialog(this, "No puedes seleccionar asientos ya comprados.");
            return; // Salimos del método
        }

        // Comprobamos si hay asientos seleccionados para reservar
        if (cantidadSeleccionada > 0) {
            // Intentamos reservar los asientos en la sala
            boolean reservaExitosa = sala.reservarAsientos(usuario, asientosAReservar);

            // Si la reserva es exitosa, actualizamos la tabla para mostrar los asientos como reservados
            if (reservaExitosa) {
                for (int i = 0; i < jtAsientos.getRowCount(); i++) {
                    for (int j = 0; j < jtAsientos.getColumnCount(); j++) {
                        // Calculamos el identificador único del asiento
                        int idAsiento = i * jtAsientos.getColumnCount() + j;

                        // Si el asiento está en la lista de asientos reservados, lo marcamos como reservado
                        if (asientosAReservar.contains(idAsiento)) {
                            jtAsientos.setValueAt(ASIENTO_RESERVADO, i, j);
                        }
                    }
                }

                // Abrimos la ventana de compra para que el usuario pueda realizar el pago
                Compra compra = new Compra(this);

                // Mostramos la cantidad de asientos seleccionados en la ventana de compra
                compra.actualizarCantidadAsientos(cantidadSeleccionada);

                // Mostramos la ventana de compra al usuario
                compra.setVisible(true);
            } 
            // Si no se pudieron reservar los asientos, mostramos un mensaje de error
            else {
                JOptionPane.showMessageDialog(this, "No se pudieron reservar los asientos. Algunos ya están ocupados.");
            }
        } 
        // Si no se seleccionaron asientos, mostramos un mensaje indicando que debe seleccionar alguno
        else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona al menos un asiento.");
        }
    }

    public void liberarAsientosReservados() {
        // Recorremos todas las filas de la tabla de asientos
        for (int row = 0; row < jtAsientos.getRowCount(); row++) {
            // Recorremos todas las columnas de la tabla de asientos
            for (int col = 0; col < jtAsientos.getColumnCount(); col++) {
                // Obtenemos el valor actual de la celda en la tabla (el estado del asiento)
                Object currentValue = jtAsientos.getValueAt(row, col);

                // Si el asiento está reservado (tiene el icono de "reservado")
                if (currentValue instanceof ImageIcon && currentValue.equals(ASIENTO_RESERVADO)) {
                    // Cambiamos el estado del asiento a "libre" en la interfaz
                    jtAsientos.setValueAt(ASIENTO_LIBRE, row, col);

                    // Calculamos un identificador único para el asiento basándonos en su posición
                    int asientoId = row * jtAsientos.getColumnCount() + col;

                    // Llamamos a la clase Sala para liberar el asiento en la lógica interna
                    sala.cancelarAsientos(usuario, asientoId);
                }
            }
        }
        // Refrescamos la tabla para que los cambios se vean reflejados en pantalla
        jtAsientos.repaint();
    }

    public void marcarAsientosComoOcupados() {
        // Lista para guardar los asientos que se van a marcar como vendidos
        List<Asiento> asientosVendidos = new ArrayList<>();

        // Recorremos cada celda de la tabla de asientos
        for (int row = 0; row < jtAsientos.getRowCount(); row++) {
            for (int col = 0; col < jtAsientos.getColumnCount(); col++) {
                // Obtenemos el valor actual de la celda
                Object currentValue = jtAsientos.getValueAt(row, col);

                // Si el asiento está en estado "Reservado" (imagen de reservado)
                if (currentValue instanceof ImageIcon && currentValue.equals(ASIENTO_RESERVADO)) {
                    // Cambiamos el estado visual del asiento a "Ocupado" (imagen de ocupado)
                    jtAsientos.setValueAt(ASIENTO_OCUPADO, row, col);

                    // Creamos un nuevo objeto de asiento para registrar esta acción
                    Asiento asiento = new Asiento();

                    // Calculamos un ID único para el asiento basándonos en su posición en la tabla
                    asiento.setId(row * jtAsientos.getColumnCount() + col);

                    // Cambiamos el estado del asiento a "Vendido"
                    asiento.setEstado(EstadoAsiento.VENDIDO);

                    // Asociamos el asiento al usuario actual que lo compró
                    asiento.setUsuarioReservado(usuario);

                    // Añadimos este asiento a la lista de asientos vendidos
                    asientosVendidos.add(asiento);
                }
            }
        }

        // Si hay asientos vendidos, los guardamos en la lista de asientos de la sala
        if (!asientosVendidos.isEmpty()) {
            // Asociamos los asientos vendidos al usuario actual en el sistema de la sala
            sala.getAsientos().computeIfAbsent(usuario, k -> new ArrayList<>()).addAll(asientosVendidos);
        }
    }

    /**
     * Actualiza el estado visual de un asiento en la tabla de asientos.
     * 
     * Dado el identificador único de un asiento, este método calcula su posición
     * en la tabla (fila y columna) y cambia su estado en la interfaz gráfica según:
     * - LIBRE: El asiento está disponible para ser reservado.
     * - RESERVADO: El asiento está reservado pero no comprado.
     * - VENDIDO: El asiento ya ha sido comprado.
     * 
     * Una vez actualizado el estado, se refresca la tabla para reflejar los cambios.
     */
    public void actualizarAsiento(int asientoId, EstadoAsiento estado) {
        // Obtener el número total de filas y columnas de la tabla
        int rows = jtAsientos.getRowCount();
        int cols = jtAsientos.getColumnCount();

        // Calcular la posición (fila y columna) del asiento en la tabla
        int row = asientoId / cols; // La fila se calcula dividiendo el ID entre el número de columnas
        int col = asientoId % cols; // La columna se obtiene como el resto de la división

        // Verificar que la posición calculada esté dentro de los límites de la tabla
        if (row < rows && col < cols) {
            // Cambiar el estado visual del asiento según el nuevo estado proporcionado
            switch (estado) {
                case LIBRE:
                    jtAsientos.setValueAt(ASIENTO_LIBRE, row, col); // Cambiar a imagen de asiento libre
                    break;
                case RESERVADO:
                    jtAsientos.setValueAt(ASIENTO_RESERVADO, row, col); // Cambiar a imagen de asiento reservado
                    break;
                case VENDIDO:
                    jtAsientos.setValueAt(ASIENTO_OCUPADO, row, col); // Cambiar a imagen de asiento comprado
                    break;
            }
        }

        // Refrescar la tabla para que los cambios sean visibles en la interfaz
        jtAsientos.repaint();
    }

    // Método para obtener el estado actual de un asiento dado su ID
    private EstadoAsiento obtenerEstadoAsiento(int asientoId) {
        // Recorremos la lista de asientos de todos los usuarios
        for (List<Asiento> asientosList : sala.getAsientos().values()) {
            // Buscamos el asiento que coincide con el ID dado
            for (Asiento asiento : asientosList) {
                if (asiento.getId() == asientoId) {
                    return asiento.getEstado(); // Devolvemos su estado actual
                }
            }
        }
        return EstadoAsiento.LIBRE; // Si no encontramos el asiento, asumimos que está libre
    }

    // Método para ocultar los botones de "Reservar" y "Volver atrás"
    public void ocultarBotones() {
        jbReservar.setVisible(false); // Oculta el botón de "Reservar"
        jbVolverAtras.setVisible(false); // Oculta el botón de "Volver Atrás"
    }

    // Método para habilitar o deshabilitar la interacción con los botones y la tabla de asientos
    private void setInteraccion(boolean habilitado) {
        jbReservar.setEnabled(habilitado); // Activa o desactiva el botón de "Reservar"
        jbVolverAtras.setEnabled(habilitado); // Activa o desactiva el botón de "Volver Atrás"
        jtAsientos.setEnabled(habilitado); // Activa o desactiva la selección de asientos en la tabla
    }

    // Método que configura temporizadores para eventos relacionados con la película
    private void configuracionTemporizadores() {
        // Temporizador para cuando comienza la película
        inicioPeliculaTimer = new Timer(20 * 1000, e -> {
            JOptionPane.showMessageDialog(this, "La película ha comenzado. No se pueden comprar más asientos."); // Aviso al usuario
            setInteraccion(false); // Bloquea la interacción con los botones y la tabla
        });

        // Temporizador para cuando termina la película
        finPeliculaTimer = new Timer(30 * 1000, e -> {
            JOptionPane.showMessageDialog(this, "La película ha terminado. Puedes seleccionar asientos nuevamente."); // Aviso al usuario
            setInteraccion(true); // Permite nuevamente la interacción con los botones y la tabla
            realizarRifa(); // Inicia una rifa al final de la película
            resetearAsientosOcupados(); // Libera los asientos que estaban ocupados
        });

        inicioPeliculaTimer.setRepeats(false); // El temporizador solo se ejecutará una vez
        finPeliculaTimer.setRepeats(false); // El temporizador solo se ejecutará una vez
        inicioPeliculaTimer.start(); // Inicia el temporizador para el comienzo de la película
        finPeliculaTimer.start(); // Inicia el temporizador para el final de la película
    }

    // Método que actualiza en tiempo real el estado de los asientos
    private void initializeRealTimeUpdates() {
        Timer updateTimer = new Timer(1000, e -> { // Se ejecuta cada segundo
            actualizarEstadoAsientos(); // Actualiza el estado de los asientos en la tabla
        });
        updateTimer.start(); // Inicia el temporizador
    }

    // Método que actualiza el estado de todos los asientos en la interfaz
    private void actualizarEstadoAsientos() {
        // Recorremos cada fila de la tabla de asientos
        for (int row = 0; row < jtAsientos.getRowCount(); row++) {
            // Recorremos cada columna de la fila actual
            for (int col = 0; col < jtAsientos.getColumnCount(); col++) {
                // Calculamos el ID único del asiento basado en su posición en la tabla
                int asientoId = row * jtAsientos.getColumnCount() + col;

                // Obtenemos el estado actual del asiento (Libre, Reservado o Vendido)
                EstadoAsiento estado = obtenerEstadoAsiento(asientoId);

                // Dependiendo del estado, actualizamos la imagen en la interfaz
                switch (estado) {
                    case LIBRE:
                        // Si está libre, ponemos la imagen de asiento libre
                        jtAsientos.setValueAt(ASIENTO_LIBRE, row, col);
                        break;
                    case RESERVADO:
                        // Si está reservado, ponemos la imagen de asiento reservado
                        jtAsientos.setValueAt(ASIENTO_RESERVADO, row, col);
                        break;
                    case VENDIDO:
                        // Si ya está comprado, ponemos la imagen de asiento ocupado
                        jtAsientos.setValueAt(ASIENTO_OCUPADO, row, col);
                        break;
                }
            }
        }
    }

    // Método que reinicia los asientos comprados y los vuelve a poner como libres
    private void resetearAsientosOcupados() {
        // Recorremos cada fila de la tabla de asientos
        for (int row = 0; row < jtAsientos.getRowCount(); row++) {
            // Recorremos cada columna de la fila actual
            for (int col = 0; col < jtAsientos.getColumnCount(); col++) {
                // Obtenemos el valor actual del asiento en la tabla
                Object currentValue = jtAsientos.getValueAt(row, col);

                // Si el asiento tiene la imagen de ocupado, lo cambiamos a libre
                if (currentValue instanceof ImageIcon && currentValue.equals(ASIENTO_OCUPADO)) {
                    jtAsientos.setValueAt(ASIENTO_LIBRE, row, col);
                }
            }
        }
    }

    // Método que realiza una rifa con los asientos vendidos
    private void realizarRifa() {
        // Llama al método de la sala para realizar la rifa y obtener un mensaje con los ganadores
        String mensajeRifa = sala.iniciarRifa();

        // Muestra un cuadro de diálogo con los ganadores de la rifa
        JOptionPane.showMessageDialog(this, mensajeRifa);

        // Después de 5 segundos, cerramos automáticamente el programa
        Timer cierreProgramaTimer = new Timer(5 * 1000, e -> {
            System.exit(0); // Cerramos el programa
        });

        // Configuramos el temporizador para que no se repita
        cierreProgramaTimer.setRepeats(false);

        // Iniciamos el temporizador
        cierreProgramaTimer.start();
    }

    // Método que guarda una referencia a la ventana de compra (si se necesita en otro momento)
    public void setVentanaCompra(Compra ventanaCompra) {
        this.ventanaCompra = ventanaCompra; // Guardamos la ventana de compra en una variable
    }
  
}