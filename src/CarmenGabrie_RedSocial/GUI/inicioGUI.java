package CarmenGabrie_RedSocial.GUI;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


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
    fondo.setLayout(null);
    
    
    JLabel imagen = new JLabel();
    ImageIcon img = new ImageIcon("src/CarmenGabrie_RedSocial/imagenes/imgInicio.png");
    Image scaled = img.getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH);
    imagen.setIcon(new ImageIcon(scaled));
    imagen.setBounds(50,150,500,400);
    
    JLabel textoInicio = new JLabel ("Iniciar sesion en Instagram");
    
    JTextField user = new JTextField ();
    
    JPasswordField pass = new JPasswordField();
    
    JButton login = new JButton ("Iniciar sesion");
    
    JButton registro = new JButton ("Crear nueva cuenta");
    
    fondo.add(imagen);
    fondo.add(textoInicio);
    fondo.add(user);
    fondo.add(pass);
    fondo.add(login);
    fondo.add(registro);
    setContentPane(fondo);

    }
    
    
}
