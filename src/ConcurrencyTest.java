import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTest {

    // Constantes para la configuración de la concurrencia
    private static final int NUM_USUARIOS = 5; // Número total de usuarios
    private static final int MIN_ASIENTOS_POR_USUARIO = 1; // Número mínimo de asientos que puede intentar comprar un usuario
    private static final int MAX_ASIENTOS_POR_USUARIO = 5; // Número máximo de asientos que puede intentar comprar un usuario
    private static final int MAX_INTENTOS = 10; // Número máximo de intentos que hará un usuario para comprar asientos
    private static final int TIEMPO_ESPERA_ENTRE_INTENTOS = 500; // Tiempo de espera entre intentos de compra en milisegundos
    private static final int TIEMPO_ESPERA_COMPRANDO = 2000; // Tiempo que simula el estado de "comprando"

    // Interfaces gráficas que se actualizan durante el proceso
    private final ProcesoCompra procesoCompraGUI; // Ventana que muestra el estado de los usuarios
    private final AsientosGUI asientosGUI;        // Ventana que muestra el estado de los asientos

    // Nombres de los usuarios en la prueba
    private static final String[] NOMBRES_USUARIOS = {"Juanito", "Joselillo", "Olmo", "Maeb", "Alberto"};

    // Constructor: inicializa las interfaces gráficas
    public ConcurrencyTest(ProcesoCompra procesoCompraGUI, AsientosGUI asientosGUI) {
        this.procesoCompraGUI = procesoCompraGUI;
        this.asientosGUI = asientosGUI;
    }

    // Método principal que inicia la prueba de concurrencia
    public void iniciarTestConcurrencia(Sala sala) {
        // Generador de números aleatorios
        Random random = new Random();

        // Ejecutores para manejar los hilos concurrentes
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_USUARIOS);

        // Latch para sincronizar los hilos, espera a que todos terminen
        CountDownLatch latch = new CountDownLatch(NUM_USUARIOS);

        // Bucle para inicializar cada usuario en su propio hilo
        for (int i = 0; i < NUM_USUARIOS; i++) {
            final int userId = i;

            executorService.submit(() -> {
                try {
                    // Crear un nuevo usuario con ID y nombre correspondiente
                    Usuario usuario = new Usuario();
                    usuario.setId(userId);
                    usuario.setNombre(NOMBRES_USUARIOS[userId]);

                    // Variables para controlar el flujo de cada usuario
                    boolean compraCompleta = false; // Indica si el usuario ha completado la compra
                    boolean primeraIntentoCancelado = false; // Solo para "Alberto", controla si ya canceló su primera compra
                    int intentos = 0; // Contador de intentos
                    List<Integer> asientosSeleccionados = new ArrayList<>(); // Lista de asientos que intenta comprar

                    // Actualizar el estado del usuario a "verde" inicialmente
                    procesoCompraGUI.actualizarEstadoUsuario(userId, "verde");

                    // Simular un tiempo de espera inicial antes de comenzar el proceso
                    Thread.sleep(random.nextInt(2000));

                    // Ciclo para intentar reservar y comprar asientos
                    while (!compraCompleta && intentos < MAX_INTENTOS) {
                        intentos++;
                        asientosSeleccionados.clear(); // Limpiar la lista de asientos seleccionados

                        // Seleccionar un número aleatorio de asientos
                        int numAsientos = random.nextInt(MAX_ASIENTOS_POR_USUARIO - MIN_ASIENTOS_POR_USUARIO + 1) + MIN_ASIENTOS_POR_USUARIO;
                        for (int j = 0; j < numAsientos; j++) {
                            asientosSeleccionados.add(random.nextInt(120)); // Selección aleatoria de asientos
                        }

                        // Cambiar estado del usuario a "naranja" (comprando)
                        procesoCompraGUI.actualizarEstadoUsuario(userId, "naranja");

                        boolean reservaExitosa = false; // Indica si la reserva de asientos fue exitosa

                        // Comportamiento especial para "Maeb" y "Alberto"
                        if ("Maeb".equals(usuario.getNombre())) {
                            // "Maeb" siempre falla en sus intentos de reserva
                            reservaExitosa = false;
                        } else if ("Alberto".equals(usuario.getNombre()) && !primeraIntentoCancelado) {
                            // "Alberto" intenta reservar, cancela y vuelve a intentarlo
                            reservaExitosa = sala.reservarAsientos(usuario, asientosSeleccionados);
                            if (reservaExitosa) {
                                // Actualizar asientos como reservados en la interfaz
                                asientosSeleccionados.forEach(asientoId -> asientosGUI.actualizarAsiento(asientoId, EstadoAsiento.RESERVADO));

                                // Simular tiempo comprando y luego cancelar
                                Thread.sleep(TIEMPO_ESPERA_COMPRANDO);
                                asientosSeleccionados.forEach(asientoId -> {
                                    sala.cancelarAsientos(usuario, asientoId);
                                    asientosGUI.actualizarAsiento(asientoId, EstadoAsiento.LIBRE);
                                });

                                // Volver a estado "verde" después de cancelar
                                procesoCompraGUI.actualizarEstadoUsuario(userId, "verde");
                                primeraIntentoCancelado = true; // Marca que Alberto ya canceló su primera compra
                                Thread.sleep(TIEMPO_ESPERA_ENTRE_INTENTOS);
                                continue; // Reinicia el ciclo para Alberto
                            }
                        } else {
                            // Intentar reservar los asientos normalmente para los demás usuarios
                            reservaExitosa = sala.reservarAsientos(usuario, asientosSeleccionados);
                        }

                        // Si la reserva es exitosa, intentar comprar
                        if (reservaExitosa) {
                            // Actualizar los asientos reservados en la interfaz
                            asientosSeleccionados.forEach(asientoId -> asientosGUI.actualizarAsiento(asientoId, EstadoAsiento.RESERVADO));

                            // Simular tiempo comprando
                            Thread.sleep(TIEMPO_ESPERA_COMPRANDO);

                            boolean todosComprados = true; // Indica si todos los asientos reservados fueron comprados

                            // Intentar comprar cada asiento reservado
                            for (Integer asientoId : asientosSeleccionados) {
                                boolean compraExitosa = sala.comprarAsientos(usuario, asientoId);

                                if (!compraExitosa) {
                                    // Si no se puede comprar un asiento, liberarlo
                                    todosComprados = false;
                                    asientosGUI.actualizarAsiento(asientoId, EstadoAsiento.LIBRE);
                                } else {
                                    // Actualizar asiento como vendido en la interfaz
                                    asientosGUI.actualizarAsiento(asientoId, EstadoAsiento.VENDIDO);
                                }
                            }

                            if (todosComprados) {
                                // Si todos los asientos fueron comprados, completar la compra
                                compraCompleta = true;
                                procesoCompraGUI.actualizarEstadoUsuario(userId, "rojo"); // Estado final: comprado
                                procesoCompraGUI.actualizarAsientosComprados(userId, asientosSeleccionados);
                            } else {
                                // Si no se compraron todos, esperar antes de reintentar
                                Thread.sleep(TIEMPO_ESPERA_ENTRE_INTENTOS);
                            }
                        } else {
                            // Si no se pudo reservar, volver al estado "verde" y esperar para reintentar
                            procesoCompraGUI.actualizarEstadoUsuario(userId, "verde");
                            Thread.sleep(TIEMPO_ESPERA_ENTRE_INTENTOS);
                        }
                    }

                    // Si el usuario no pudo comprar después de 10 intentos
                    if (!compraCompleta && !"Alberto".equals(usuario.getNombre())) {
                        procesoCompraGUI.actualizarEstadoUsuario(userId, "morado"); // Estado final: fallido
                        procesoCompraGUI.actualizarAsientosComprados(userId, new ArrayList<>()); // Mostrar que no compró ningún asiento
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Manejar interrupción del hilo
                } finally {
                    latch.countDown(); // Indicar que este usuario terminó
                }
            });
        }

        try {
            latch.await(60, TimeUnit.SECONDS); // Esperar hasta que todos los usuarios terminen o pase el tiempo límite
            System.out.println("Total de asientos vendidos: " + sala.contarAsientosVendidos()); // Imprimir resumen de ventas
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Manejar interrupción durante la espera
        } finally {
            executorService.shutdown(); // Apagar el ejecutor de hilos
        }
    }
}