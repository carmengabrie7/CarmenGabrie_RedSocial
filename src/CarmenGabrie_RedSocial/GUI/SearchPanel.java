package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;
import CarmenGabrie_RedSocial.UsuarioManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class SearchPanel extends JPanel {

    private feedGUI feed;
    private Usuario usuario;
    private JTextField campoBusqueda;
    private JPanel resultados;

    public SearchPanel(Usuario usuario, feedGUI feed){

        this.usuario = usuario;
        this.feed=feed;
        
        setLayout(null);
        setBackground(Color.white);

        JPanel barraBusqueda = new JPanel();
barraBusqueda.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
barraBusqueda.setBackground(Color.white);
barraBusqueda.setBounds(50,30,500,60);

JLabel titulo = new JLabel("Buscar");
titulo.setFont(new Font("Arial",Font.BOLD,16));

campoBusqueda = new JTextField(20);

JButton buscar = new JButton("Buscar");

barraBusqueda.add(titulo);
barraBusqueda.add(campoBusqueda);
barraBusqueda.add(buscar);

add(barraBusqueda);

        resultados = new JPanel();
        resultados.setLayout(new BoxLayout(resultados,BoxLayout.Y_AXIS));
        resultados.setBackground(Color.white);

        JScrollPane scroll = new JScrollPane(resultados);
        scroll.setBounds(50,120,600,500);
        scroll.setBorder(null);
        add(scroll);

        buscar.addActionListener(e -> buscar());
    }

    private void buscar(){
        resultados.removeAll();
        String texto= campoBusqueda.getText().trim();
        
        try{
            if(texto.isEmpty()){
                resultados.add(new JLabel("Escribe algo para buscar"));
            }
            
            else if(texto.startsWith("#")){
                PostManager pm = new PostManager();
                ArrayList<Post> posts = pm.searchHashTag(texto);
                
                for (Post p : posts){
                    resultados.add(new PostCardPanel(p,usuario.getUser()));
                }
            } 
            
            else if(texto.startsWith("@")){
                PostManager pm = new PostManager();
                String username = texto.substring(1);
                ArrayList<Post> posts = pm.searchMentions(username);
                
                for(Post p : posts){
                    resultados.add(new PostCardPanel(p,usuario.getUser()));
                }
            }
            
            else{
                UsuarioManager um = new UsuarioManager();
                Usuario u = um.searchUser(texto);
                
                if(u != null){
                    JPanel userResult = new JPanel();
                    userResult.setLayout(new BoxLayout(userResult, BoxLayout.X_AXIS));
                    userResult.setBackground(Color.white);
                    
                    userResult.setAlignmentX(Component.LEFT_ALIGNMENT);
                    userResult.setMaximumSize(new Dimension(500,60));
                    
                    userResult.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    
                    userResult.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                    
                    String fotoPerfil = u.getFotoPerfil();
                    if(fotoPerfil == null || fotoPerfil.isEmpty()){
                        fotoPerfil = "default.jpg";
                    }
                    
                    ImageIcon icon = new ImageIcon(
                      "src/CarmenGabrie_RedSocial/imagenes/" + fotoPerfil
                    );
                    
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    JLabel foto = new JLabel(new ImageIcon(img));
                    
                    JPanel textoPanel = new JPanel();
                    textoPanel.setLayout(new BoxLayout(textoPanel,BoxLayout.Y_AXIS));
                    textoPanel.setBackground(Color.white);
                    
                    JLabel username = new JLabel(u.getUser());
                    username.setFont(new Font("Arial",Font.BOLD,14));
                    
                    JLabel nombre = new JLabel(u.getFullName());
                    
                    textoPanel.add(username);
                    textoPanel.add(nombre);
                    
                    userResult.add(foto);
                    userResult.add(Box.createHorizontalStrut(10));
                    userResult.add(textoPanel);
                    
                    userResult.addMouseListener(new java.awt.event.MouseAdapter() {
                       public void mouseClicked(java.awt.event.MouseEvent e){
                           feed.abrirPerfil(u);
                       }
                    });
                    
                    resultados.add(userResult);
                    resultados.add(Box.createVerticalStrut(10));
                } else{
                    resultados.add(new JLabel ("Usuario no encontrado"));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        resultados.revalidate();
        resultados.repaint();
    }
}
