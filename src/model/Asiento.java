/**
 * Clase que representa un asiento dentro del sistema.
 * Cada asiento tiene un estado (libre, reservado, vendido), un identificador único,
 * el usuario asociado a su reserva y un tiempo de reserva.
 */

package model;

import java.time.LocalDateTime;

public class Asiento {
    private EstadoAsiento estado; // Estado actual del asiento (libre, reservado, vendido)
    private int id; // Identificador único del asiento.
    private LocalDateTime tiempoReserva; // Tiempo exacto en el que se reservó el asiento.
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