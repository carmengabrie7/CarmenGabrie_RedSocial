package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.*;

public class UserProfileGUI extends JFrame {

    public UserProfileGUI(Usuario yo, Usuario otro){

        setTitle(otro.getUser());
        setSize(800,600);
        setLocationRelativeTo(null);
        setLayout(null);

        // FOTO
        ImageIcon icon = new ImageIcon(
                "src/CarmenGabrie_RedSocial/imagenes/" + otro.getFotoPerfil()
        );

        Image img = icon.getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH);

        JLabel foto = new JLabel(new ImageIcon(img));
        foto.setBounds(50,40,120,120);
        add(foto);

        // USERNAME
        JLabel username = new JLabel(otro.getUser());
        username.setBounds(200,50,200,30);
        add(username);

        // FOLLOW BUTTON
        JButton follow = new JButton("Follow");
        follow.setBounds(200,90,100,30);
        add(follow);

        // MESSAGE BUTTON
        JButton message = new JButton("Message");
        message.setBounds(310,90,100,30);
        add(message);

        // POSTS GRID
        JPanel grid = new JPanel(new GridLayout(0,3,10,10));
        grid.setBackground(Color.white);

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBounds(50,200,700,350);
        add(scroll);

        try{

            PostManager pm = new PostManager();

            ArrayList<Post> posts = pm.getUserPosts(otro.getUser());

            for(Post p : posts){

                ImageIcon imgPost = new ImageIcon(p.getRutaImagen());

                Image scaled = imgPost.getImage().getScaledInstance(
                        200,200,Image.SCALE_SMOOTH
                );

                JLabel imagen = new JLabel(new ImageIcon(scaled));

                grid.add(imagen);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        setVisible(true);
    }
}