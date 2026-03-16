package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import java.awt.Font;
import java.awt.Image;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PostViewerGUI extends JFrame{

    public PostViewerGUI(Post post){

        setTitle("Post");
        setSize(500,600);
        setLocationRelativeTo(null);
        setLayout(null);

        // IMAGEN
        if(post.getRutaImagen()!=null){

            ImageIcon icon = new ImageIcon(post.getRutaImagen());
            Image img = icon.getImage().getScaledInstance(400,300,Image.SCALE_SMOOTH);

            JLabel imagen = new JLabel(new ImageIcon(img));
            imagen.setBounds(50,30,400,300);

            add(imagen);
        }

        // USERNAME escribió
        JLabel autor = new JLabel("@" + post.getAuthor() + " escribió:");
        autor.setFont(new Font("Arial",Font.BOLD,14));
        autor.setBounds(50,350,300,30);

        add(autor);

        // CONTENIDO
        JLabel contenido = new JLabel(
                "\"" + post.getContent() + "\" " +
                post.getHashtags() + " " +
                post.getMentions()
        );

        contenido.setBounds(50,380,400,30);
        add(contenido);

        // FECHA
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        JLabel fecha = new JLabel(
                sdf.format(post.getDate())
        );

        fecha.setBounds(50,420,200,30);
        add(fecha);

        setVisible(true);
    }
}