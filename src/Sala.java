import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Sala {
    private ConcurrentHashMap<Usuario, List<Asiento>> asientos;
    private boolean rifaEnCurso = false;
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
                if (asiento.getEstado()!= EstadoAsiento.LIBRE) {
                    return false;
                }
                listaAsientos.add(asiento);
            }
        }
        for (Asiento asiento : listaAsientosDisponibles) {
            asiento.setEstado(EstadoAsiento.RESERVADO);
            asiento.setUsuarioReservado(usuario);
            asiento.setTiempoReserva(LocalDateTime.now());
        }
        asientos.putIfAbsent(usuario,new ArrayList<>());
        asientos.get(usuario).addAll(listaAsientosDisponibles);
        return true;
    }
    //comprar asiento

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

    //cancelar asiento

    public boolean cancelarAsientos(Usuario usuario, int idAsientos) {

        if (rifaEnCurso) {
            return false;
        }

        List<Asiento> listaAsientos = asientos.get(usuario);
        if (listaAsientos != null) {
            for (Asiento asiento : listaAsientos) {
                if (asiento.getId() == idAsientos && asiento.getEstado() == EstadoAsiento.RESERVADO) {
                    try{
                        asiento.setEstado(EstadoAsiento.LIBRE);
                        asiento.setUsuarioReservado(null);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return false;
    }

    //timeout
    
    public boolean timeoutReserva(Usuario usuario, int idAsientos) {

        if (rifaEnCurso) {
            return false;
        }

        List<Asiento> listaAsientos = asientos.get(usuario);
        if (listaAsientos != null) {
            for (Asiento asiento : listaAsientos) {
                if (asiento.getId() == idAsientos && asiento.getEstado() != EstadoAsiento.RESERVADO) {
                    new  Thread(() -> {
                        try {
                            Thread.sleep(60000);
                            if (asiento.getEstado() == EstadoAsiento.RESERVADO) {
                                asiento.setEstado(EstadoAsiento.LIBRE);
                                asiento.setTiempoReserva(null);
                                asiento.setUsuarioReservado(null);
                                System.out.println("Asiento " + idAsientos + " ya no esta reservado");

                            }
                        }catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }).start();
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean iniciarRifa() {
        rifaEnCurso = true;
        List<Asiento> asientosVendidos = new ArrayList<>();
        for (List<Asiento> listaAsientos : asientos.values()) {
            for (Asiento asiento : listaAsientos) {
                if (asiento.getEstado() == EstadoAsiento.VENDIDO) {
                    asientosVendidos.add(asiento);
                }
            }
        }
        if (asientosVendidos.size() >= 5) {
            Random random = new Random();
            Asiento ganador1 = asientosVendidos.get(random.nextInt(asientosVendidos.size()));
            Asiento ganador2 = asientosVendidos.get(random.nextInt(asientosVendidos.size()));
            while (ganador1.getId() == ganador2.getId()) {
                ganador2 = asientosVendidos.get(random.nextInt(asientosVendidos.size()));
            }
            rifaEnCurso = false;
            return true;
        } else {
            rifaEnCurso = false;
            return false;
        }
    }
}