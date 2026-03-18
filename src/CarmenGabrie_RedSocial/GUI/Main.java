package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.ChatServer;
import java.io.IOException;
import javax.swing.SwingUtilities;



public class Main {
    public static void main(String[] args) throws IOException {

    // iniciar servidor en segundo plano
    new Thread(() -> {
        try {
            new ChatServer().startServer();
        } catch (Exception e) {
            System.out.println("Servidor ya activo o no disponible");
        }
    }).start();

    // abrir la aplicación
    new inicioGUI();
}
}
