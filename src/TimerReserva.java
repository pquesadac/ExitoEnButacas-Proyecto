import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase TimerReserva
 * Gestiona un temporizador que libera asientos reservados automáticamente
 * si no se completa la compra dentro de un tiempo límite definido.
 */
public class TimerReserva {
    private Timer timer;    // Temporizador que controla el tiempo de reserva.
    private AsientosGUI asientosGUI; // Referencia a la interfaz gráfica de los asientos.
    private Compra compra; // Referencia a la ventana de compra


    /**
     * Constructor de la clase TimerReserva.
     * Referencia a la interfaz gráfica que gestiona los asientos.
     * Tiempo límite en segundos para completar la compra antes
     * de que los asientos reservados se liberen automáticamente.
     */
    public TimerReserva(AsientosGUI asientosGUI, int delayInSeconds) {
        this.asientosGUI = asientosGUI;

        // Inicializa el temporizador con el tiempo de espera especificado y define la acción al expirar.
        this.timer = new Timer(delayInSeconds * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuando se termine el tiempo:
                resetReserva();
            }
        });
        this.timer.setRepeats(false); // Solo se ejecuta una vez
    }

    // Asigna la ventana de compra
    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    // Iniciar el temporizador
    public void start() {
        timer.start();
    }

    // Detener el temporizador
    public void stop() {
        timer.stop();
    }

        /**
        * Método privado que se ejecuta cuando el temporizador expira.
        * Su funcionamiento es liberar los asientos reservados, cierra la ventana de compra (si está abierta)
        * y vuelve a mostrar la ventana principal de selección de asientos.
        */    
        private void resetReserva() {
        asientosGUI.liberarAsientosReservados();
        if (compra != null) {
            compra.dispose(); // Cierra la ventana de compra
        }
        asientosGUI.setVisible(true); 
    }
}