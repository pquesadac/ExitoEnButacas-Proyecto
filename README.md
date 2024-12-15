# Sistema de Gestión de Reservas de Asientos

## Componentes
- **Java Swing**: Para la interfaz gráfica.
- **ConcurrentHashMap**: Para la gestión concurrente de asientos reservados por usuarios.
- **Multithreading**: Simulación de usuarios concurrentes intentando reservar y comprar asientos.
- **Temporizadores (`Timer`)**: Controlan eventos como el tiempo límite para completar reservas.

---

## Estructura Concurrente
### Estructura Utilizada: `ConcurrentHashMap`
El proyecto utiliza un `ConcurrentHashMap` para gestionar los asientos reservados por usuarios. Este mapa permite acceder y modificar de manera segura los datos compartidos entre múltiples hilos.

### Descripción
El `ConcurrentHashMap` asocia:
- **Clave (`Usuario`)**: Representa al usuario que reserva los asientos.
- **Valor (`List<Asiento>`)**: Lista de asientos asociados al usuario, con su estado (`LIBRE`, `RESERVADO`, `VENDIDO`).

Se utiliza para:
1. **Reserva de asientos.**
2. **Compra de asientos.**
3. **Liberación automática de asientos tras el vencimiento del tiempo de reserva.**
4. **Rifas de los asientos vendidos.**

---

## Introducción
Este proyecto es un sistema de reservas de asientos para salas de cine, diseñado para demostrar el uso de estructuras concurrentes. Incluye una interfaz gráfica que simula a múltiples usuarios reservando y comprando asientos al mismo tiempo.

### Casos de Uso
- **Reserva de asientos:** Los usuarios pueden seleccionar asientos y reservarlos.
- **Compra de asientos:** Una vez reservados, los asientos deben comprarse antes de que expire el tiempo límite.
- **Cancelación de reservas:** Un usuario puede cancelar sus reservas.
- **Rifa:** Se realiza una rifa al final, seleccionando ganadores entre los compradores de asientos.

---

## Métodos de Interés

| Clase           | Método                          | Descripción                                                                 |
|------------------|---------------------------------|-----------------------------------------------------------------------------|
| `Sala`          | `reservarAsientos`             | Reserva asientos para un usuario.                                           |
| `Sala`          | `comprarAsientos`              | Cambia el estado de un asiento reservado a "vendido".                       |
| `Sala`          | `timeoutReserva`               | Libera automáticamente los asientos tras un tiempo.                        |
| `Sala`          | `iniciarRifa`                  | Realiza una rifa entre los compradores de asientos.                        |
| `AsientosGUI`   | `actualizarAsiento`            | Cambia el estado visual de un asiento en la interfaz gráfica.              |
| `TimerReserva`  | `resetReserva`                 | Libera los asientos reservados si no se completó la compra.                |
| `ConcurrencyTest` | `iniciarTestConcurrencia`    | Simula usuarios concurrentes reservando y comprando asientos.              |

---

## Descripción de los Ficheros del Proyecto

### Carpeta: `src/main/java`
- **`model`**
  - `Usuario`: Representa a un usuario con su ID y nombre.
  - `Asiento`: Define un asiento con su estado, usuario asociado y tiempo de reserva.
  - `EstadoAsiento`: Enum que representa los posibles estados de un asiento.
- **`view`**
  - `LoginGUI`: Interfaz para que los usuarios ingresen al sistema.
  - `AsientosGUI`: Interfaz para seleccionar y comprar asientos.
  - `Compra`: Interfaz que muestra el detalle de la compra de asientos.
  - `ProcesoCompra`: Muestra el estado de los usuarios durante la simulación concurrente.
- **`controller`**
  - `Sala`: Lógica principal para gestionar reservas, compras y rifas de asientos.
  - `ConcurrencyTest`: Simula la concurrencia de múltiples usuarios.
  - `TimerReserva`: Gestiona el tiempo límite para completar la compra.
- **`Main`:** Clase principal para ejecutar la aplicación.

### Carpeta: `src/main/resources`
- `assets`: Contiene imágenes utilizadas en la interfaz gráfica (e.g., iconos de usuarios y estados de asientos).

---

## Cómo Ejecutar
1. Asegúrate de tener configurado un entorno de desarrollo Java (JDK 8+).
2. Organiza el proyecto en la estructura de carpetas descrita anteriormente.
3. Ejecuta la clase `Main`:
 - `Usuario individual`: Ejecútalo como usuario independiente
 - `Gestión de concurrencia`: Ejecuta la parte comentada del código para ver la interfaz con varios usuarios