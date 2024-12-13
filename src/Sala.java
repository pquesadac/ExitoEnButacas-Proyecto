import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.HashSet;

public class Sala {
    private ConcurrentHashMap<Usuario, List<Asiento>> asientos;
    private final Object reservationLock = new Object();
    private volatile boolean rifaEnCurso = false;

    // Constructor
    public Sala() {
        this.asientos = new ConcurrentHashMap<>(); 
    }
    
    public ConcurrentHashMap<Usuario, List<Asiento>> getAsientos() {
        return asientos;
    }

    public void setAsientos(ConcurrentHashMap<Usuario, List<Asiento>> asientos) {
        this.asientos = asientos;
    }

    public synchronized boolean reservarAsientos(Usuario usuario, List<Integer> idAsientos) {
        if (rifaEnCurso) {
            return false;
        }

        synchronized (reservationLock) {
            Set<Integer> ocupados = new HashSet<>();
            for (List<Asiento> asientosLista : asientos.values()) {
                for (Asiento asiento : asientosLista) {
                    if (asiento.getEstado() != EstadoAsiento.LIBRE) {
                        ocupados.add(asiento.getId());
                    }
                }
            }

            for (Integer idAsiento : idAsientos) {
                if (ocupados.contains(idAsiento)) {
                    return false;
                }
            }

            List<Asiento> nuevosAsientos = new ArrayList<>();
            for (Integer idAsiento : idAsientos) {
                Asiento nuevoAsiento = new Asiento();
                nuevoAsiento.setId(idAsiento);
                nuevoAsiento.setEstado(EstadoAsiento.RESERVADO);
                nuevoAsiento.setUsuarioReservado(usuario);
                nuevoAsiento.setTiempoReserva(LocalDateTime.now());
                nuevosAsientos.add(nuevoAsiento);
            }

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

    public boolean comprarAsientos(Usuario usuario, int idAsiento) {
        if (rifaEnCurso) {
            return false;
        }

        synchronized (reservationLock) {
            List<Asiento> listaAsientos = asientos.get(usuario);
            if (listaAsientos != null) {
                for (Asiento asiento : listaAsientos) {
                    if (asiento.getId() == idAsiento && 
                        asiento.getEstado() == EstadoAsiento.RESERVADO && 
                        asiento.getUsuarioReservado().equals(usuario)) {
                        asiento.setEstado(EstadoAsiento.VENDIDO);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public boolean cancelarAsientos(Usuario usuario, int idAsientos) {
        if (rifaEnCurso) {
            return false;
        }

        List<Asiento> listaAsientos = asientos.get(usuario);
        if (listaAsientos != null) {
            for (Asiento asiento : listaAsientos) {
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

    public void timeoutReserva(Usuario usuario, int idAsiento) {
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

    public String iniciarRifa() {
        rifaEnCurso = true;
        List<Asiento> asientosVendidos = new ArrayList<>();
        Map<Asiento, Usuario> mapaAsientosUsuarios = new HashMap<>();

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

        if (asientosVendidos.size() >= 2) {
            Random random = new Random();
            Asiento ganador1 = asientosVendidos.get(random.nextInt(asientosVendidos.size()));
            Asiento ganador2;
            do {
                ganador2 = asientosVendidos.get(random.nextInt(asientosVendidos.size()));
            } while (ganador1.getId() == ganador2.getId());

            Usuario usuarioGanador1 = mapaAsientosUsuarios.get(ganador1);
            Usuario usuarioGanador2 = mapaAsientosUsuarios.get(ganador2);

            rifaEnCurso = false;

            return "¡Enhorabuena! Los ganadores son:\n" +
                   "Usuario: " + usuarioGanador1.getNombre() + " con el asiento \"" + ganador1.getId() + "\"\n" +
                   "Usuario: " + usuarioGanador2.getNombre() + " con el asiento \"" + ganador2.getId() + "\"";
        } else {
            rifaEnCurso = false;
            return "No hay suficientes asientos vendidos para realizar la rifa.";
        }
    }
    
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