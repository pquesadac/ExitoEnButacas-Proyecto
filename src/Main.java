/*public class Main {
    public static void main(String[] args) {
        Sala sala = new Sala();
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
    }
}*/

/*public class Main {
    public static void main(String[] args) {
        // Crear la sala y la interfaz gráfica
        Sala sala = new Sala();
        ProcesoCompra procesoCompraGUI = new ProcesoCompra();
        
        // Hacer visible la interfaz
        procesoCompraGUI.setVisible(true);
        
        // Ejecutar el test de concurrencia
        ConcurrencyTest concurrencyTest = new ConcurrencyTest();
        concurrencyTest.iniciarTestConcurrencia(sala, procesoCompraGUI);
    }
}*/

public class Main {
    public static void main(String[] args) {
        // Crear la sala y el usuario principal
        Sala sala = new Sala();
        Usuario usuarioAdmin = new Usuario();
        usuarioAdmin.setId(0);
        usuarioAdmin.setNombre("Administrador");

        // Crear las interfaces
        ProcesoCompra procesoCompraGUI = new ProcesoCompra();
        AsientosGUI asientosGUI = new AsientosGUI(sala, usuarioAdmin);

        // Ajustar tamaños y posiciones de las ventanas
        asientosGUI.setSize(900, 610); // Ventana más pequeña para los asientos
        procesoCompraGUI.setSize(550, 450); // Tamaño original para la interfaz de usuarios

        asientosGUI.setLocation(40, 100); // Posición a la izquierda
        procesoCompraGUI.setLocation(940, 100); // Posición a la derecha, junto a los asientos

        // Mostrar las interfaces
        asientosGUI.ocultarBotones();
        asientosGUI.setVisible(true);
        procesoCompraGUI.setVisible(true);
        

        // Ejecutar el test de concurrencia
        ConcurrencyTest concurrencyTest = new ConcurrencyTest(procesoCompraGUI, asientosGUI);
        concurrencyTest.iniciarTestConcurrencia(sala);
    }
}
