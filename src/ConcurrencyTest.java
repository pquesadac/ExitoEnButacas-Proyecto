import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTest {

    private static final int NUM_USUARIOS = 5;
    private static final int MIN_ASIENTOS_POR_USUARIO = 1; // Mínimo número de asientos por usuario
    private static final int MAX_ASIENTOS_POR_USUARIO = 5; // Máximo número de asientos por usuario
    private static final int MAX_INTENTOS = 10;
    private static final int TIEMPO_ESPERA_ENTRE_INTENTOS = 500; // Milisegundos
    private static final int TIEMPO_ESPERA_COMPRANDO = 2000; // Milisegundos (simular tiempo "comprando")

    private final ProcesoCompra procesoCompraGUI; // Interfaz de usuarios
    private final AsientosGUI asientosGUI;        // Interfaz de asientos
    
    private static final String[] NOMBRES_USUARIOS = {"Juanito", "Joselillo", "Olmo", "Maeb", "Alberto"};

    /**
     * Constructor para inicializar las interfaces.
     *
     * @param procesoCompraGUI La interfaz de usuarios.
     * @param asientosGUI      La interfaz de asientos.
     */
    public ConcurrencyTest(ProcesoCompra procesoCompraGUI, AsientosGUI asientosGUI) {
        this.procesoCompraGUI = procesoCompraGUI;
        this.asientosGUI = asientosGUI;
    }

    public void iniciarTestConcurrencia(Sala sala) {
        Random random = new Random();
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_USUARIOS);
        CountDownLatch latch = new CountDownLatch(NUM_USUARIOS);

        for (int i = 0; i < NUM_USUARIOS; i++) {
            final int userId = i;
            executorService.submit(() -> {
                try {
                    Usuario usuario = new Usuario();
                    usuario.setId(userId);
                    usuario.setNombre(NOMBRES_USUARIOS[userId]); // Asignar nombres personalizados

                    boolean compraCompleta = false;
                    int intentos = 0;
                    List<Integer> asientosSeleccionados = new ArrayList<>();

                    procesoCompraGUI.actualizarEstadoUsuario(userId, "verde");
                    Thread.sleep(random.nextInt(2000)); // Simular espera inicial

                    while (!compraCompleta && intentos < MAX_INTENTOS) {
                        intentos++;
                        asientosSeleccionados.clear();
                        int numAsientos = random.nextInt(MAX_ASIENTOS_POR_USUARIO - MIN_ASIENTOS_POR_USUARIO + 1) + MIN_ASIENTOS_POR_USUARIO;
                        for (int j = 0; j < numAsientos; j++) {
                            asientosSeleccionados.add(random.nextInt(120));
                        }

                        procesoCompraGUI.actualizarEstadoUsuario(userId, "naranja");
                        boolean reservaExitosa = sala.reservarAsientos(usuario, asientosSeleccionados);

                        if (reservaExitosa) {
                            asientosSeleccionados.forEach(asientoId -> asientosGUI.actualizarAsiento(asientoId, EstadoAsiento.RESERVADO));
                            Thread.sleep(TIEMPO_ESPERA_COMPRANDO);

                            boolean todosComprados = true;
                            for (Integer asientoId : asientosSeleccionados) {
                                boolean compraExitosa = sala.comprarAsientos(usuario, asientoId);
                                if (!compraExitosa) {
                                    todosComprados = false;
                                    asientosGUI.actualizarAsiento(asientoId, EstadoAsiento.LIBRE);
                                } else {
                                    asientosGUI.actualizarAsiento(asientoId, EstadoAsiento.VENDIDO);
                                }
                            }

                            if (todosComprados) {
                                compraCompleta = true;
                                procesoCompraGUI.actualizarEstadoUsuario(userId, "rojo");
                                procesoCompraGUI.actualizarAsientosComprados(userId, asientosSeleccionados);
                            } else {
                                Thread.sleep(TIEMPO_ESPERA_ENTRE_INTENTOS);
                            }
                        } else {
                            procesoCompraGUI.actualizarEstadoUsuario(userId, "verde");
                            Thread.sleep(TIEMPO_ESPERA_ENTRE_INTENTOS);
                        }
                    }

                    if (!compraCompleta) {
                        procesoCompraGUI.actualizarEstadoUsuario(userId, "morado");
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await(60, TimeUnit.SECONDS);
            System.out.println("Total de asientos vendidos: " + sala.contarAsientosVendidos());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executorService.shutdown();
        }

    }
}

