/*public class Main {
    public static void main(String[] args) {
        Sala sala = new Sala();
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
    }
}*/

public class Main {
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
}
