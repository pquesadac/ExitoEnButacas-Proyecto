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
    private static final int TIEMPO_ESPERA_ENTRE_INTENTOS = 500; // milisegundos

    /**
     * Inicia la prueba de concurrencia con la interfaz de usuarios.
     *
     * @param sala     Objeto de tipo Sala para gestionar las reservas.
     * @param interfaz Interfaz gráfica para mostrar los estados de los usuarios.
     */
    public void iniciarTestConcurrencia(Sala sala, ProcesoCompra interfaz) {
        Random random = new Random();
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_USUARIOS);
        CountDownLatch latch = new CountDownLatch(NUM_USUARIOS);

        for (int i = 0; i < NUM_USUARIOS; i++) {
            final int userId = i;
            executorService.submit(() -> {
                try {
                    Usuario usuario = new Usuario();
                    usuario.setId(userId);
                    usuario.setNombre("Usuario " + userId);

                    boolean compraCompleta = false;
                    int intentos = 0;

                    // Número aleatorio de asientos para este usuario
                    int numAsientos = random.nextInt(MAX_ASIENTOS_POR_USUARIO - MIN_ASIENTOS_POR_USUARIO + 1) + MIN_ASIENTOS_POR_USUARIO;

                    // Mostrar estado inicial (verde)
                    interfaz.actualizarEstadoUsuario(userId, "verde");

                    // Simular un tiempo de espera aleatorio antes de comenzar el proceso
                    Thread.sleep(random.nextInt(2000));

                    while (!compraCompleta && intentos < MAX_INTENTOS) {
                        intentos++;
                        List<Integer> asientosSeleccionados = new ArrayList<>();

                        // Seleccionar asientos aleatorios
                        for (int j = 0; j < numAsientos; j++) {
                            int asientoId = random.nextInt(120);
                            asientosSeleccionados.add(asientoId);
                        }

                        // Cambiar a estado comprando (naranja)
                        interfaz.actualizarEstadoUsuario(userId, "naranja");

                        // Simular tiempo de compra
                        Thread.sleep(random.nextInt(2000));

                        // Intentar reservar
                        boolean reservaExitosa = sala.reservarAsientos(usuario, asientosSeleccionados);

                        if (reservaExitosa) {
                            boolean todosComprados = true;

                            for (Integer asientoId : asientosSeleccionados) {
                                boolean compraExitosa = sala.comprarAsientos(usuario, asientoId);
                                if (!compraExitosa) {
                                    sala.cancelarAsientos(usuario, asientoId);
                                    todosComprados = false;
                                }
                            }

                            if (todosComprados) {
                                compraCompleta = true;

                                // Cambiar a estado comprado (rojo)
                                interfaz.actualizarEstadoUsuario(userId, "rojo");
                            } else {
                                // Si no se pudieron comprar todos, esperar e intentar de nuevo
                                Thread.sleep(TIEMPO_ESPERA_ENTRE_INTENTOS);
                            }
                        } else {
                            // Si no se pudieron reservar asientos, esperar e intentar de nuevo
                            Thread.sleep(TIEMPO_ESPERA_ENTRE_INTENTOS);
                        }
                    }

                    // Si después de 10 intentos no se completó la compra, cambiar a estado denegado (morado)
                    if (!compraCompleta) {
                        interfaz.actualizarEstadoUsuario(userId, "morado");
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

            System.out.println("\nResultados finales:");
            System.out.println("Total de asientos vendidos: " + sala.contarAsientosVendidos());
            sala.imprimirAsientosVendidos();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executorService.shutdown();
        }
    }
}
