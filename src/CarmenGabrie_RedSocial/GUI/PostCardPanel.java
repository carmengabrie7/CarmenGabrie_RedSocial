package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PostCardPanel extends JPanel {

    public PostCardPanel(Post post){

        setLayout(null);
        setBackground(Color.white);
        setPreferredSize(new Dimension(500,450));

        setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        // IMAGEN
        if(post.getRutaImagen()!=null){

            ImageIcon icon = new ImageIcon(post.getRutaImagen());
            Image img = icon.getImage().getScaledInstance(480,300,Image.SCALE_SMOOTH);

            JLabel imagen = new JLabel(new ImageIcon(img));
            imagen.setBounds(10,10,480,300);

            add(imagen);
        }

        // USERNAME escribió
        JLabel autor = new JLabel("@" + post.getAuthor() + " escribió:");
        autor.setFont(new Font("Arial",Font.BOLD,14));
        autor.setBounds(20,320,400,25);

        add(autor);

        // CONTENIDO
        JLabel contenido = new JLabel(
                "\"" + post.getContent() + "\" " + post.getHashtags()
        );

        contenido.setBounds(20,345,400,25);

        add(contenido);

        // FECHA
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        JLabel fecha = new JLabel(
                sdf.format(post.getDate())
        );

        fecha.setBounds(20,370,200,20);

        add(fecha);
    }
}
