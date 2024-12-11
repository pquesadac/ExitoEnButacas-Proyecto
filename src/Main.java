public class Main {
    public static void main(String[] args) {
        Sala sala = new Sala();
        AsientosGUI asientosGUI = new AsientosGUI(sala);
        asientosGUI.setVisible(true);
    }
}