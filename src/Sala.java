import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Sala {
    private ConcurrentHashMap<Usuario, List<Asiento>> asientos;
    private boolean rifaEnCurso = false;

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

    public boolean reservarAsientos(Usuario usuario, List<Integer> idAsientos) {
        if (rifaEnCurso) {
            return false;
        }

        List<Asiento> listaAsientosDisponibles = new ArrayList<>();
        for (List<Asiento> listaAsientos : asientos.values()) {
            for (Asiento asiento : listaAsientos) {
                if (asiento.getEstado() != EstadoAsiento.LIBRE) {
                    return false;
                }
                listaAsientosDisponibles.add(asiento);
            }
        }

        for (Asiento asiento : listaAsientosDisponibles) {
            asiento.setEstado(EstadoAsiento.RESERVADO);
            asiento.setUsuarioReservado(usuario);
            asiento.setTiempoReserva(LocalDateTime.now());
        }

        asientos.putIfAbsent(usuario, new ArrayList<>());
        asientos.get(usuario).addAll(listaAsientosDisponibles);
        return true;
    }

    public boolean comprarAsientos(Usuario usuario, int idAsientos) {
        if (rifaEnCurso) {
            return false;
        }

        List<Asiento> listaAsientos = asientos.get(usuario);
        if (listaAsientos != null) {
            for (Asiento asiento : listaAsientos) {
                if (asiento.getId() == idAsientos) {
                    if (asiento.getEstado() != EstadoAsiento.RESERVADO) {
                        asiento.setEstado(EstadoAsiento.VENDIDO);
                        return true;
                    }
                }
            }
        }
        return false;
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

    public boolean timeoutReserva(Usuario usuario, int idAsientos) {
        if (rifaEnCurso) {
            return false;
        }

        List<Asiento> listaAsientos = asientos.get(usuario);
        if (listaAsientos != null) {
            for (Asiento asiento : listaAsientos) {
                if (asiento.getId() == idAsientos && asiento.getEstado() != EstadoAsiento.RESERVADO) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(60000);
                            if (asiento.getEstado() == EstadoAsiento.RESERVADO) {
                                asiento.setEstado(EstadoAsiento.LIBRE);
                                asiento.setTiempoReserva(null);
                                asiento.setUsuarioReservado(null);
                                System.out.println("Asiento " + idAsientos + " ya no está reservado");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }).start();
                    return true;
                }
            }
        }
        return false;
    }

    public String iniciarRifa() {
        rifaEnCurso = true;
        List<Asiento> asientosVendidos = new ArrayList<>();
        for (List<Asiento> listaAsientos : asientos.values()) {
            for (Asiento asiento : listaAsientos) {
                if (asiento.getEstado() == EstadoAsiento.VENDIDO) {
                    asientosVendidos.add(asiento);
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

            rifaEnCurso = false;

            return "¡Enhorabuena, los asientos \"" + ganador1.getId() + "\" y \"" + ganador2.getId() + "\" han ganado!";
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