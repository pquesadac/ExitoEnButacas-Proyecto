import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerReserva {
    private Timer timer;
    private AsientosGUI asientosGUI;
    private Compra compra; // Referencia a la ventana de compra

    public TimerReserva(AsientosGUI asientosGUI, int delayInSeconds) {
        this.asientosGUI = asientosGUI;

        this.timer = new Timer(delayInSeconds * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuando se termine el tiempo:
                resetReserva();
            }
        });
        this.timer.setRepeats(false); // Solo ejecutar una vez
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

    // Resetear el estado de la reserva y cerrar la ventana de compra
    private void resetReserva() {
        asientosGUI.liberarAsientosReservados();
        if (compra != null) {
            compra.dispose(); // Cierra la ventana de compra
        }
        asientosGUI.setVisible(true); // Asegúrate de que la GUI principal esté visible
    }
}

