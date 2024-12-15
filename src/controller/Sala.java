/**
 * Clase que gestiona las operaciones principales del sistema de reservas:
 * - Reserva de asientos.
 * - Compra de asientos reservados.
 * - Liberación automática de reservas tras un tiempo.
 * - Realización de una rifa entre los usuarios que compraron asientos.
 * Utiliza un ConcurrentHashMap para manejar los asientos de manera segura en un entorno concurrente.
 */

package controller;

import model.Asiento;
import model.EstadoAsiento;
import model.Usuario;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.HashSet;

public class Sala {

    // Mapa que asocia un usuario a una lista de asientos reservados
    private ConcurrentHashMap<Usuario, List<Asiento>> asientos;

    // Objeto utilizado para asegurar que las operaciones de reserva sean exclusivas (sincronizadas)
    private final Object reservationLock = new Object();

    // Indica si una rifa está en curso
    private volatile boolean rifaEnCurso = false;

    // Constructor de la clase Sala
    public Sala() {
        // Inicializa el mapa de asientos
        this.asientos = new ConcurrentHashMap<>(); 
    }

    // Método que devuelve el mapa de asientos
    public ConcurrentHashMap<Usuario, List<Asiento>> getAsientos() {
        return asientos;
    }

    // Método que establece el mapa de asientos
    public void setAsientos(ConcurrentHashMap<Usuario, List<Asiento>> asientos) {
        this.asientos = asientos;
    }

    // Método sincronizado para reservar asientos
    public synchronized boolean reservarAsientos(Usuario usuario, List<Integer> idAsientos) {
        // Si la rifa está en curso, no se permiten reservas
        if (rifaEnCurso) {
            return false;
        }

        // Bloqueo para evitar que dos usuarios reserven el mismo asiento al mismo tiempo
        synchronized (reservationLock) {
            Set<Integer> ocupados = new HashSet<>();

            // Verifica los asientos ocupados
            for (List<Asiento> asientosLista : asientos.values()) {
                for (Asiento asiento : asientosLista) {
                    if (asiento.getEstado() != EstadoAsiento.LIBRE) {
                        ocupados.add(asiento.getId());
                    }
                }
            }

            // Verifica que los asientos solicitados no estén ocupados
            for (Integer idAsiento : idAsientos) {
                if (ocupados.contains(idAsiento)) {
                    return false;
                }
            }

            // Crea nuevos asientos reservados para el usuario
            List<Asiento> nuevosAsientos = new ArrayList<>();
            for (Integer idAsiento : idAsientos) {
                Asiento nuevoAsiento = new Asiento();
                nuevoAsiento.setId(idAsiento);
                nuevoAsiento.setEstado(EstadoAsiento.RESERVADO);
                nuevoAsiento.setUsuarioReservado(usuario);
                nuevoAsiento.setTiempoReserva(LocalDateTime.now());
                nuevosAsientos.add(nuevoAsiento);
            }

            // Asocia los nuevos asientos al usuario en el mapa de asientos
            asientos.compute(usuario, (key, existingList) -> {
                if (existingList == null) {
                    return nuevosAsientos;
                } else {
                    existingList.addAll(nuevosAsientos);
                    return existingList;
                }
            });

            return true;
        }
    }

    // Método para comprar un asiento reservado
    public boolean comprarAsientos(Usuario usuario, int idAsiento) {
        // Si la rifa está en curso, no se pueden comprar asientos
        if (rifaEnCurso) {
            return false;
        }

        // Bloqueo para realizar la operación de forma segura
        synchronized (reservationLock) {
            List<Asiento> listaAsientos = asientos.get(usuario);

            // Verifica que el usuario tenga el asiento reservado
            if (listaAsientos != null) {
                for (Asiento asiento : listaAsientos) {
                    if (asiento.getId() == idAsiento && 
                        asiento.getEstado() == EstadoAsiento.RESERVADO && 
                        asiento.getUsuarioReservado().equals(usuario)) {
                        // Cambia el estado del asiento a "VENDIDO"
                        asiento.setEstado(EstadoAsiento.VENDIDO);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    // Método para cancelar un asiento reservado
    public boolean cancelarAsientos(Usuario usuario, int idAsientos) {
        // Si la rifa está en curso, no se pueden cancelar asientos
        if (rifaEnCurso) {
            return false;
        }

        List<Asiento> listaAsientos = asientos.get(usuario);
        if (listaAsientos != null) {
            for (Asiento asiento : listaAsientos) {
                // Cancela el asiento si está reservado
                if (asiento.getId() == idAsientos && asiento.getEstado() == EstadoAsiento.RESERVADO) {
                    try {
                        asiento.setEstado(EstadoAsiento.LIBRE);
                        asiento.setUsuarioReservado(null);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return false;
    }

    // Método para liberar un asiento que estuvo reservado por un tiempo y no fue comprado
    public void timeoutReserva(Usuario usuario, int idAsiento) {
        // Establece un retraso para liberar el asiento si no se compró en 60 segundos
        CompletableFuture.delayedExecutor(60, TimeUnit.SECONDS).execute(() -> {
            synchronized (reservationLock) {
                List<Asiento> listaAsientos = asientos.get(usuario);
                if (listaAsientos != null) {
                    listaAsientos.stream()
                        .filter(asiento -> asiento.getId() == idAsiento && 
                                         asiento.getEstado() == EstadoAsiento.RESERVADO)
                        .findFirst()
                        .ifPresent(asiento -> {
                            asiento.setEstado(EstadoAsiento.LIBRE);
                            asiento.setUsuarioReservado(null);
                            asiento.setTiempoReserva(null);
                        });
                }
            }
        });
    }

    // Método para iniciar la rifa de asientos vendidos
    public String iniciarRifa() {
        rifaEnCurso = true;
        List<Asiento> asientosVendidos = new ArrayList<>();
        Map<Asiento, Usuario> mapaAsientosUsuarios = new HashMap<>();

        // Recoge los asientos vendidos y los usuarios asociados
        for (Map.Entry<Usuario, List<Asiento>> entry : asientos.entrySet()) {
            Usuario usuario = entry.getKey();
            List<Asiento> listaAsientos = entry.getValue();

            for (Asiento asiento : listaAsientos) {
                if (asiento.getEstado() == EstadoAsiento.VENDIDO) {
                    asientosVendidos.add(asiento);
                    mapaAsientosUsuarios.put(asiento, usuario);
                }
            }
        }

        // Si hay suficientes asientos vendidos, realiza la rifa
        if (asientosVendidos.size() >= 3) {
            Random random = new Random();
            Asiento ganador1 = asientosVendidos.get(random.nextInt(asientosVendidos.size()));
            Asiento ganador2;
            do {
                ganador2 = asientosVendidos.get(random.nextInt(asientosVendidos.size()));
            } while (ganador1.getId() == ganador2.getId());

            Usuario usuarioGanador1 = mapaAsientosUsuarios.get(ganador1);
            Usuario usuarioGanador2 = mapaAsientosUsuarios.get(ganador2);

            rifaEnCurso = false;

            // Devuelve los resultados de la rifa
            return "¡Enhorabuena! Los ganadores son:\n" +
                   "Usuario: " + usuarioGanador1.getNombre() + " con el asiento \"" + ganador1.getId() + "\"\n" +
                   "Usuario: " + usuarioGanador2.getNombre() + " con el asiento \"" + ganador2.getId() + "\"";
        } else {
            rifaEnCurso = false;
            return "No hay suficientes asientos vendidos para realizar la rifa.";
        }
    }

    // Método para contar el número de asientos vendidos
    public int contarAsientosVendidos() {
        int vendidos = 0;
        for (List<Asiento> lista : asientos.values()) {
            for (Asiento asiento : lista) {
                if (asiento.getEstado() == EstadoAsiento.VENDIDO) {
                    vendidos++;
                }
            }
        }
        return vendidos;
    }

    // Método para imprimir los asientos vendidos
    public void imprimirAsientosVendidos() {
        for (Map.Entry<Usuario, List<Asiento>> entry : asientos.entrySet()) {
            Usuario usuario = entry.getKey();
            List<Asiento> lista = entry.getValue();
            System.out.println("Usuario: " + usuario.getNombre());
            for (Asiento asiento : lista) {
                if (asiento.getEstado() == EstadoAsiento.VENDIDO) {
                    System.out.println("  Asiento ID: " + asiento.getId() + " Estado: " + asiento.getEstado());
                }
            }
        }
    }
}