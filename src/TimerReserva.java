import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerReserva {
    private Timer timer;
    private AsientosGUI asientosGUI;

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

    // Iniciar el temporizador
    public void start() {
        timer.start();
    }

    // Detener el temporizador
    public void stop() {
        timer.stop();
    }

    // Resetear el estado de la reserva y regresar a la pantalla principal
    private void resetReserva() {
        asientosGUI.liberarAsientosReservados();
        asientosGUI.setVisible(true);
    }
}
