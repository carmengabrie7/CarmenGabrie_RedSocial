package CarmenGabrie_RedSocial.GUI;

import java.io.IOException;
import javax.swing.SwingUtilities;



public class Main {
    public static void main (String[]args){
    
        SwingUtilities.invokeLater(() -> {
            try {
                new inicioGUI();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
  }
}
