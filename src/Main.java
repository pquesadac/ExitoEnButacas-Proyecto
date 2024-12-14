public class Main {
    public static void main(String[] args) {
        // Creamos una instancia de la clase Sala.
        Sala sala = new Sala();
        // Creamos una instancia de la interfaz gráfica del login.
        LoginGUI loginGUI = new LoginGUI();
        // Se hace visible la ventana de inicio de sesión
        loginGUI.setVisible(true);
    }
}

/*public class Main {
    public static void main(String[] args) {
        // Crear la sala y el usuario principal
        Sala sala = new Sala();
        Usuario usuarioAdmin = new Usuario();
        usuarioAdmin.setId(0);
        usuarioAdmin.setNombre("Administrador");

        String[] nombresUsuarios = {"Juanito", "Joselillo", "Olmo", "Maeb", "Alberto"};
        
        // Crear las interfaces
        ProcesoCompra procesoCompraGUI = new ProcesoCompra(nombresUsuarios);
        AsientosGUI asientosGUI = new AsientosGUI(sala, usuarioAdmin);

        // Ajustar tamaños y posiciones de las ventanas
        asientosGUI.setSize(900, 610); // Ventana más pequeña para los asientos
        procesoCompraGUI.setSize(600, 450); // Tamaño original para la interfaz de usuarios

        asientosGUI.setLocation(20, 100); // Posición a la izquierda
        procesoCompraGUI.setLocation(920, 100); // Posición a la derecha, junto a los asientos

        // Mostrar las interfaces
        asientosGUI.ocultarBotones();
        asientosGUI.setVisible(true);
        procesoCompraGUI.setVisible(true);
        

        // Ejecutar el test de concurrencia
        ConcurrencyTest concurrencyTest = new ConcurrencyTest(procesoCompraGUI, asientosGUI);
        concurrencyTest.iniciarTestConcurrencia(sala);
    }
}*/
