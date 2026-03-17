package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;
import CarmenGabrie_RedSocial.UsuarioManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PostCardPanel extends JPanel {
    private JLabel likesLabel;
    private Post post;
    private String currentUser;

    
    public PostCardPanel(Post post, String currentUser){
        this.post=post;
        this.currentUser=currentUser;

        setBackground(Color.white);
        setPreferredSize(new Dimension(620,560));

        setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        // IMAGEN
        if(post.getRutaImagen()!=null){

            ImageIcon icon = new ImageIcon(post.getRutaImagen());
            Image img = icon.getImage().getScaledInstance(600,400,Image.SCALE_SMOOTH);

            JLabel imagen = new JLabel(new ImageIcon(img));
            imagen.setBounds(10,10,600,400);

            add(imagen);
        }
        
        PostManager pm = new PostManager();
        
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.setBackground(Color.white);
        actions.setBounds(20,510,200,40);
        
        JButton likeBtn = new JButton("❤");
        likeBtn.addActionListener(e-> {
            pm.likePost(post, currentUser);
            int likes = pm.getLikes(post);
            
            likesLabel.setText(likes + " likes");
        });
        
        
        JButton commentBtn = new JButton("💬");
        commentBtn.addActionListener(e-> {
                new PostViewerGUI(post,currentUser);
            });
        
        actions.add(likeBtn);
        actions.add(commentBtn);
        add(actions);
        
        likesLabel = new JLabel(pm.getLikes(post) + " likes");
        likesLabel.setBounds(150,510,100,40);
        
        add(likesLabel);

       /* // USERNAME escribió
        JLabel autor = new JLabel("@" + post.getAuthor() + " escribió:");
        autor.setFont(new Font("Arial",Font.BOLD,14));
        autor.setBounds(20,420,400,25);*/
       
       JLabel autor = new JLabel("@" + post.getAuthor());
autor.setForeground(Color.BLUE);
autor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

autor.addMouseListener(new java.awt.event.MouseAdapter(){

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e){

        try{

            UsuarioManager um = new UsuarioManager();

            Usuario perfil = um.searchUser(post.getAuthor());

            feedGUI frame = (feedGUI) javax.swing.SwingUtilities.getWindowAncestor(autor);

            frame.abrirPerfil(perfil);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
});

        add(autor);

        // CONTENIDO
        JLabel contenido = new JLabel(
                "\"" + post.getContent() + "\" " + post.getHashtags()
        );

        contenido.setBounds(20,450,500,25);

        add(contenido);

        // FECHA
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        JLabel fecha = new JLabel(
                sdf.format(post.getDate())
        );

        fecha.setBounds(20,480,200,20);

        add(fecha);
        
    }
}
