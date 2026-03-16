package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Usuario;
import CarmenGabrie_RedSocial.UsuarioManager;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class inicioGUI extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private UsuarioManager usuarioManager;
    
    public inicioGUI() throws IOException{
        usuarioManager = new UsuarioManager();
    setTitle("Instagram");    
    setSize(900,600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
    setLayout(null);
    
    ImageIcon logo = new ImageIcon("src/CarmenGabrie_RedSocial/imagenes/InstaLogo.png"); 
    setIconImage(logo.getImage()); 
    
    JPanel fondo = new JPanel();
    fondo.setBackground(Color.white);
    fondo.setBounds(0, 0, 900, 600);
    fondo.setLayout(null);
    setContentPane(fondo);
    
    JLabel imagen = new JLabel();
    ImageIcon img = new ImageIcon("src/CarmenGabrie_RedSocial/imagenes/imgInicio.png");
    Image scaled = img.getImage().getScaledInstance(350, 300, Image.SCALE_SMOOTH);
    imagen.setIcon(new ImageIcon(scaled));
    imagen.setBounds(50,120,350,300);
    fondo.add(imagen);
    
    JPanel linea = new JPanel();
    linea.setBackground(Color.LIGHT_GRAY);
    linea.setBounds(450, 0, 2, 600);
    fondo.add(linea);
    
    JLabel textoInicio = new JLabel ("Iniciar sesion en Instagram");
    textoInicio.setBounds(560, 120, 250, 30);
    fondo.add(textoInicio);
    
    txtUser = new JTextField ();
    txtUser.setBounds(520,170,250,35);
    fondo.add(txtUser);
    
    txtPass = new JPasswordField();
    txtPass.setBounds(520,220,250,35);
    fondo.add(txtPass);
    
    JButton login = new JButton ("Iniciar sesion");
    login.setBounds(520, 280, 250, 40);
    login.setBackground(new Color(22,119,225));
    login.addActionListener(e-> hacerLogin());
    fondo.add(login);
    
    
    JButton registro = new JButton ("Crear nueva cuenta");
    registro.setBounds(520, 340, 250, 40);
    registro.addActionListener(e -> {
        new registroGUI();
        dispose();
    });
    fondo.add(registro);
    
    setVisible(true);

    }
    
    private void hacerLogin(){
        try {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            
            Usuario u = usuarioManager.login(user, pass);
            
            if (u != null){
                JOptionPane.showMessageDialog(this, "Bienvenido " + u.getUser());
                new feedGUI(u);
                dispose();
            }else{
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    
}
