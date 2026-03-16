package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Usuario;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class feedGUI extends JFrame{
    private Usuario usuario;
    
    private JPanel panelPrincipal;
    private JPanel rightPanel;
    private CardLayout cardLayout;
    
    public feedGUI (Usuario usuario) throws IOException{
        this.usuario = usuario;
        
        setTitle ("Instagram");
        setSize (1366,768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel linea1 = new JPanel();
        linea1.setBackground(Color.LIGHT_GRAY);
        linea1.setBounds(200,0,1,768);
        add(linea1);
        
        JPanel linea2 = new JPanel();
        linea2.setBackground(Color.LIGHT_GRAY);
        linea2.setBounds(1000,0,1,768);
        add(linea2);
        
        crearSidebar();
        crearFeed();
        crearRightPanel();
    
        setVisible(true);
    }
    
    private void crearSidebar(){
     
        JPanel sidebar = new JPanel();
        sidebar.setLayout(null);
        sidebar.setBackground(Color.white);
        sidebar.setBounds(0,0,200,768);
        add(sidebar);
        
        ImageIcon logo = new ImageIcon("src/CarmenGabrie_RedSocial/imagenes/logoInsta.png");
        Image img = logo.getImage().getScaledInstance(35,35,Image.SCALE_SMOOTH);

        JLabel logoLabel = new JLabel(new ImageIcon(img));
        logoLabel.setBounds(20,30,35,35);

        sidebar.add(logoLabel);
        
        JButton home = crearBoton(
                  "Home",
                  "src/CarmenGabrie_RedSocial/iconos/home.png",
                  140
                );

        JButton profile = crearBoton(
                 "Profile",
                 "src/CarmenGabrie_RedSocial/iconos/profile.png",
                 190
                );

       JButton messages = crearBoton(
                 "Messages",
                 "src/CarmenGabrie_RedSocial/iconos/messages.png",
                 240
                );

       JButton search = crearBoton(
                "Search",
                "src/CarmenGabrie_RedSocial/iconos/search.png",
                290
               );
        
        sidebar.add(home);
        sidebar.add(profile);
        sidebar.add(messages);
        sidebar.add(search);
        
        home.addActionListener(e -> { 
                cardLayout.show(panelPrincipal, "home");
                rightPanel.setVisible(true);       
            });
        profile.addActionListener(e -> { 
            cardLayout.show(panelPrincipal, "profile");
            rightPanel.setVisible(false);
                });
        messages.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "messages");
            rightPanel.setVisible(false);
                });
        search.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "search");
            rightPanel.setVisible(false);
                });
        
    }
    
    private void crearFeed() throws IOException{
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBounds(200, 0, 800, 768);
        
        add(panelPrincipal);
        
        panelPrincipal.add(new HomePanel(usuario),"home");
        panelPrincipal.add(new ProfilePanel(usuario),"profile");
        panelPrincipal.add(new MessagesPanel(usuario),"messages");
        panelPrincipal.add(new SearchPanel(usuario),"search");
    }
    
    private void crearRightPanel(){
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.white);
        rightPanel.setBounds(1000,0,366,768);
        add(rightPanel);
        
        JLabel nombre = new JLabel (usuario.getFullName());
        nombre.setBounds(40,40,200,30);
        
        JLabel username = new JLabel("@" + usuario.getUser());
        username.setBounds(40,70,200,30);
        
        rightPanel.add(nombre);
        rightPanel.add(username);
        
    }
    
    private JButton crearBoton(String texto,String iconPath, int y){
        ImageIcon icon = new ImageIcon (iconPath);
        Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        
        JButton boton = new JButton(texto, new ImageIcon(img));
        boton.setBounds(20,y,160,40);
        
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setIconTextGap(15);
        
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);

        boton.setFont(new Font("Arial",Font.PLAIN,16));
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
        
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt){
                boton.setBackground(new Color(240,240,240));
                boton.setOpaque(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt){
                boton.setOpaque(false);
            }
        });
        
        return boton;
    }
}
