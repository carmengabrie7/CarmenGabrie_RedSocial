package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;
import CarmenGabrie_RedSocial.UsuarioManager;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.*;

public class OtherProfileGUI extends JFrame{

    private Usuario yo;
    private Usuario otro;
    private JPanel gridPosts;

    public OtherProfileGUI(Usuario yo, Usuario otro){

        this.yo = yo;
        this.otro = otro;

        setTitle(otro.getUser());
        setSize(800,600);
        setLocationRelativeTo(null);
        setLayout(null);

        crearHeader();
        crearGrid();
        cargarPosts();

        setVisible(true);
    }

    private void crearHeader(){

        JLabel username = new JLabel(otro.getUser());
        username.setBounds(100,40,200,30);
        add(username);

        JButton follow = new JButton("Follow");
        follow.setBounds(100,80,100,30);
        add(follow);

        JButton message = new JButton("Message");
        message.setBounds(210,80,100,30);
        add(message);

        follow.addActionListener(e -> {

            try{

                UsuarioManager um = new UsuarioManager();
                um.followUser(yo.getUser(), otro.getUser());

                JOptionPane.showMessageDialog(null,"Ahora sigues a " + otro.getUser());

            }catch(Exception ex){
                ex.printStackTrace();
            }

        });

    }

    private void crearGrid(){

        gridPosts = new JPanel();
        gridPosts.setLayout(new GridLayout(0,3,10,10));
        gridPosts.setBackground(Color.white);

        JScrollPane scroll = new JScrollPane(gridPosts);
        scroll.setBounds(50,150,700,400);

        add(scroll);
    }

    private void cargarPosts(){

        try{

            PostManager pm = new PostManager();
            ArrayList<Post> posts = pm.getUserPosts(otro.getUser());

            if(posts.isEmpty()){

                gridPosts.add(new JLabel("No hay publicaciones."));

            }else{

                for(Post p : posts){

                    ImageIcon img = new ImageIcon(p.getRutaImagen());
                    Image scaled = img.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);

                    JLabel postImg = new JLabel(new ImageIcon(scaled));

                    postImg.addMouseListener(new java.awt.event.MouseAdapter(){

                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e){

                            new PostViewerGUI(p);

                        }

                    });

                    gridPosts.add(postImg);
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}