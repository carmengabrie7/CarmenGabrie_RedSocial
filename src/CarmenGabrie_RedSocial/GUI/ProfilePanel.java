
package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ProfilePanel extends JPanel {
    private Usuario usuario;
    private JPanel gridPosts;
    
    public ProfilePanel(Usuario usuario){
        this.usuario=usuario;
        setLayout(null);
        setBackground(Color.white);
        
        crearHeader();
        crearGridPosts();
        cargarPosts();
        
    }
    
    private void crearHeader(){
        ImageIcon icon = new ImageIcon(
            "src/CarmenGabrie_RedSocial/imagenes/" + usuario.getFotoPerfil()
        );
        
        Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        
        CircularImageLabel foto = new CircularImageLabel(
           "src/CarmenGabrie_RedSocial.Imagenes/"+usuario.getFotoPerfil(),120
        );
        foto.setBounds(80,40,120,120);
        add(foto);
        
        JLabel username = new JLabel(usuario.getUser());
        username.setFont(new Font("Arial",Font.BOLD,20));
        username.setBounds(240,60,200,30);
        add(username);
        
        JLabel nombre = new JLabel(usuario.getFullName());
        nombre.setBounds(240,90,200,30);
        add(nombre);
        
        try{
            PostManager pm = new PostManager();
            int postCount = pm.getUserPosts(usuario.getUser()).size();
            
            JLabel posts = new JLabel(postCount + " posts");
            posts.setBounds(400,60,100,30);
            add(posts);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        JLabel followers = new JLabel(contarFollowers() + " followers");
        followers.setBounds(500,60,120,30);
        add(followers);
        
        JLabel following = new JLabel(contarFollowing() + " following");
        following.setBounds(650,60,120,30);
        add(following);
        
        JButton crearPost = new JButton("Crear Post");
        crearPost.setBounds(240,130,120,30);
        add(crearPost);
        
        crearPost.addActionListener(e-> {
            new CreatePostGUI(usuario);
            gridPosts.removeAll();
            cargarPosts();
            gridPosts.revalidate();
            gridPosts.repaint();
        });
        
        JPanel linea = new JPanel();
        linea.setBackground(new Color(220,220,220));
        linea.setBounds(80,180,800,1);
        add(linea);
    }
    
    private int contarFollowers(){
        int count =0;
        try{
            RandomAccessFile file = new RandomAccessFile(
              "INSTA_RAIZ/"+usuario.getUser()+"/followers.ins","r"
            );
            
            while(file.getFilePointer() < file.length()){
                file.readUTF();
                count++;
            }
            file.close();
        }catch(Exception e){
            
        }
        return count;
    }
    
    private int contarFollowing(){
        int count =0;
        
        try{
            RandomAccessFile file = new RandomAccessFile(
                    "INSTA_RAIZ/"+usuario.getUser()+"/following.ins","r"
            );
            
            while(file.getFilePointer() < file.length()){
                file.readUTF();
                count++;
            }
            file.close();
        }catch(Exception e){}
        return count;
    }
    
    private void crearGridPosts(){
        gridPosts = new JPanel();
        gridPosts.setLayout(new GridLayout(0,3,5,5));
        gridPosts.setBackground(Color.white);
        
        JScrollPane scroll = new JScrollPane(gridPosts);
        scroll.setBounds(80,200,800,500);
        scroll.setBorder(null);
        add(scroll);   
    }
    
    private void cargarPosts(){

    gridPosts.removeAll();

    try{
        PostManager pm = new PostManager();
        ArrayList<Post> posts = pm.getUserPosts(usuario.getUser());

        if(posts.isEmpty()){

            JLabel vacio = new JLabel("No hay publicaciones.");
            gridPosts.add(vacio);

        }else{

            for(Post p : posts){

                if(p.getRutaImagen()!=null){

                    ImageIcon img = new ImageIcon(p.getRutaImagen());
                    Image scaled = img.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH);

                    JLabel postImg = new JLabel(new ImageIcon(scaled));

                    // CLICK PARA ABRIR POST
                    postImg.addMouseListener(new java.awt.event.MouseAdapter(){

                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e){

                            new PostViewerGUI(p);

                        }
                    });

                    gridPosts.add(postImg);
                }
            }
        }

        gridPosts.revalidate();
        gridPosts.repaint();

    }catch(Exception e){
        e.printStackTrace();
    }
}
}
