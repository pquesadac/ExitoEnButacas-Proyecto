import java.time.LocalDateTime;

public class Asiento {
    private EstadoAsiento estado;
    private int id;
    private LocalDateTime tiempoReserva;
    private Usuario usuarioReservado;

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTiempoReserva() {
        return tiempoReserva;
    }

    public void setTiempoReserva(LocalDateTime tiempoReserva) {
        this.tiempoReserva = tiempoReserva;
    }

    public Usuario getUsuarioReservado() {
        return usuarioReservado;
    }

    public void setUsuarioReservado(Usuario usuarioReservado) {
        this.usuarioReservado = usuarioReservado;
    }
}