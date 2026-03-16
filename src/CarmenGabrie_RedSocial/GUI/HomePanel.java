package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class HomePanel extends JPanel {
    private Usuario usuario;
    private JPanel timeline;
    
    public HomePanel(Usuario usuario) throws IOException{
        this.usuario=usuario;
        
        setLayout(null);
        setBackground(Color.white);
        
        timeline = new JPanel();
        timeline.setLayout(new BoxLayout(timeline, BoxLayout.Y_AXIS));
        timeline.setBackground(Color.white);
        
        JScrollPane scroll = new JScrollPane(timeline);
        scroll.setBounds(250,20,500,650);
        scroll.setBorder(null);
        add(scroll);
        
        cargarPosts();

    }
    
    private void cargarPosts(){
        try{
            PostManager pm = new PostManager();
            
            ArrayList<Post> posts = pm.getFeedPosts(usuario.getUser());
            
            posts.sort((a,b) -> Long.compare(b.getDate(), a.getDate()));
            
            for(Post p: posts){
                PostCardPanel card = new PostCardPanel(p);
                timeline.add(card);
                timeline.add(Box.createVerticalStrut(15));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
