package CarmenGabrie_RedSocial;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class inicioGUI extends JFrame {
    
    public inicioGUI(){
    setTitle("Instagram");    
    setSize(1366,768);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
    
    ImageIcon logo = new ImageIcon("src/CarmenGabrie_RedSocial/imagenes/InstaLogo.png"); 
    setIconImage(logo.getImage()); 
    
    JPanel fondo = new JPanel();
    fondo.setBackground(Color.white);
    fondo.setBounds(0, 0, 1366, 768);
    
    JLabel imagen = new JLabel();
    imagen.setIcon(new ImageIcon("src/CarmenGabrie_RedSocial/imagenes/imgInicio.png"));
    imagen.setBounds(4, 4, 10, 10);
    fondo.add(imagen);
    
    setContentPane(fondo);

}
}
