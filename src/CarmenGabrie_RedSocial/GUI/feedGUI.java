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
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class feedGUI extends JFrame{
    private Usuario usuario;
    
    private JPanel panelPrincipal;
    private CardLayout cardLayout;
    private HomePanel homePanel;
    private ProfilePanel profilePanel;
    
    public feedGUI (Usuario usuario) throws IOException{
        this.usuario = usuario;
        
        setTitle ("Instagram");
        setSize (1366,768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel lineaSidebar = new JPanel();
        lineaSidebar.setBackground(Color.LIGHT_GRAY);
        lineaSidebar.setBounds(200,0,1,768);
        add(lineaSidebar);
        
        
        crearSidebar();
        crearFeed();
    
        setVisible(true);
    }
    
    private void crearSidebar() throws IOException{
     
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
       
       JButton createPost = crearBoton(
               "Create Post",
               "src/CarmenGabrie_RedSocial/iconos/create.png",
               340
       );
       
       JButton notifications  = crearBoton(
               "Notifications",
               "src/CarmenGabrie_RedSocial/iconos/notifications.png",
               390
       );
       
       JButton settings = crearBoton(
               "Settings",
               "src/CarmenGabrie_RedSocial/iconos/settings.png",
               440
       );
        
        sidebar.add(home);
        sidebar.add(profile);
        sidebar.add(messages);
        sidebar.add(search);
        sidebar.add(createPost);
        sidebar.add(notifications);
        sidebar.add(settings);
        
        home.addActionListener(e -> { 
                homePanel.refreshFeed();
                cardLayout.show(panelPrincipal, "home");      
            });
        
        profile.addActionListener(e -> { 
            try {
                profilePanel.refreshProfile();
            } catch (IOException ex) {
                System.getLogger(feedGUI.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            cardLayout.show(panelPrincipal, "profile");
                });
        
        messages.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "messages");
                });
        search.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "search");
                });
        createPost.addActionListener(e -> {
            new CreatePostGUI(usuario);
                });

        notifications.addActionListener(e -> {
           cardLayout.show(panelPrincipal,"notifications");
                });

        settings.addActionListener(e -> {
           cardLayout.show(panelPrincipal,"settings");
                });
    }
    
    private void crearFeed() throws IOException{
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBounds(200, 0, 1166, 768);
        add(panelPrincipal);
        
        homePanel = new HomePanel(usuario);
        panelPrincipal.add(homePanel,"home");
        
        profilePanel = new ProfilePanel(usuario,usuario);
        panelPrincipal.add(profilePanel,"profile");
        
        panelPrincipal.add(new MessagesPanel(usuario),"messages");
        panelPrincipal.add(new SearchPanel(usuario,this),"search");
        
        panelPrincipal.add(new JPanel(),"notifications");
        panelPrincipal.add(new JPanel(),"settings");
    }
    
    public void abrirPerfil(Usuario perfil){
        try{
            profilePanel= new ProfilePanel(perfil,usuario);
            panelPrincipal.add(profilePanel,"perfilTemporal");
            cardLayout.show(panelPrincipal, "perfilTemporal");
            
        }catch(Exception e){
            e.printStackTrace();
        }
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
