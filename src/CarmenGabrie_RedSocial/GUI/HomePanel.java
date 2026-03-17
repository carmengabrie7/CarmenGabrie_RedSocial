package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
        
        setLayout(new BorderLayout());
        setBackground(Color.white);
        
        timeline = new JPanel();
        timeline.setLayout(new BoxLayout(timeline,BoxLayout.Y_AXIS));
        timeline.setBackground(Color.white);
        
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(Color.white);
        centerPanel.add(timeline);
        
        JScrollPane scroll = new JScrollPane(centerPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scroll, BorderLayout.CENTER);
        
        cargarPosts();

    }
    
    private void cargarPosts(){
        try{
            PostManager pm = new PostManager();
            ArrayList<Post> posts = pm.getFeedPosts(usuario.getUser());
            
            posts.sort((a,b) -> Long.compare(b.getDate(), a.getDate()));
            
            for(Post p: posts){
                PostCardPanel card = new PostCardPanel(p,usuario.getUser());
                
                timeline.add(card);
                timeline.add(Box.createVerticalStrut(40));
            }
            timeline.revalidate();
            timeline.repaint();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void refreshFeed(){
        timeline.removeAll();
        cargarPosts();
        
        timeline.revalidate();
        timeline.repaint();
    }
}
