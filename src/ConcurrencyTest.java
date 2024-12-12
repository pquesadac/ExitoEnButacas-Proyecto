import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTest {
    private static final int NUM_USUARIOS = 10;
    private static final int ASIENTOS_POR_USUARIO = 3;
    private static final int MAX_INTENTOS = 10;
    private static final int TIEMPO_ESPERA_ENTRE_INTENTOS = 500; // milisegundos
    
    public static void main(String[] args) {
        Sala sala = new Sala();
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_USUARIOS);
        CountDownLatch latch = new CountDownLatch(NUM_USUARIOS);
        
        for (int i = 0; i < NUM_USUARIOS; i++) {
            final int userId = i;
            executorService.submit(() -> {
                try {
                    Usuario usuario = new Usuario();
                    usuario.setId(userId);
                    usuario.setNombre("Usuario" + userId);
                    
                    boolean compraCompleta = false;
                    int intentos = 0;
                    
                    while (!compraCompleta && intentos < MAX_INTENTOS) {
                        intentos++;
                        Random random = new Random();
                        List<Integer> asientosSeleccionados = new ArrayList<>();
                        
                        // Seleccionar asientos aleatorios
                        for (int j = 0; j < ASIENTOS_POR_USUARIO; j++) {
                            int asientoId = random.nextInt(120);
                            asientosSeleccionados.add(asientoId);
                        }
                        
                        // Intentar reservar
                        boolean reservaExitosa = sala.reservarAsientos(usuario, asientosSeleccionados);
                        
                        if (reservaExitosa) {
                            boolean todosComprados = true;
                            
                            // Intentar comprar los asientos reservados
                            for (Integer asientoId : asientosSeleccionados) {
                                boolean compraExitosa = sala.comprarAsientos(usuario, asientoId);
                                if (compraExitosa) {
                                    System.out.println("Usuario " + userId + " compró exitosamente el asiento " + asientoId);
                                } else {
                                    System.out.println("Usuario " + userId + " falló al comprar el asiento " + asientoId + ". Liberando reserva.");
                                    sala.cancelarAsientos(usuario, asientoId);
                                    todosComprados = false;
                                }
                            }
                            
                            if (todosComprados) {
                                compraCompleta = true;
                                System.out.println("Usuario " + userId + " completó su compra después de " + intentos + " intentos");
                            } else {
                                // Si no se pudieron comprar todos, liberar los que se hayan reservado
                                for (Integer asientoId : asientosSeleccionados) {
                                    sala.cancelarAsientos(usuario, asientoId);
                                }
                                Thread.sleep(TIEMPO_ESPERA_ENTRE_INTENTOS);
                            }
                        } else {
                            System.out.println("Usuario " + userId + " no pudo reservar los asientos. Intento " + intentos + " de " + MAX_INTENTOS);
                            Thread.sleep(TIEMPO_ESPERA_ENTRE_INTENTOS);
                        }
                    }
                    
                    if (!compraCompleta) {
                        System.out.println("Usuario " + userId + " no logró completar su compra después de " + MAX_INTENTOS + " intentos");
                    }
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Operación interrumpida para usuario " + userId);
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
            System.err.println("Prueba de concurrencia interrumpida");
        } finally {
            executorService.shutdown();
        }
    }
}