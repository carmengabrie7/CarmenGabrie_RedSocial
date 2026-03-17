
package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.FollowManager;
import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;


public class ProfilePanel extends JPanel {
    private Usuario perfil;
    private Usuario currentUser;
    
    private JPanel gridPosts;
    
    public ProfilePanel(Usuario perfil, Usuario currentUser) throws IOException{
        this.perfil=perfil;
        this.currentUser=currentUser;
        
        setLayout(null);
        setBackground(Color.white);
        
        crearHeader();
        crearGridPosts();
        cargarPosts();
        
    }
    
      private void crearHeader() throws IOException{

    JPanel header = new JPanel();
    header.setLayout(null);
    header.setBackground(Color.white);
    header.setBounds(200,20,800,180);
    add(header);

    String fotoPerfil = perfil.getFotoPerfil();
    if(fotoPerfil == null || fotoPerfil.isEmpty()){
        fotoPerfil = "default.jpg";
    }

    CircularImageLabel foto = new CircularImageLabel(
        "src/CarmenGabrie_RedSocial/imagenes/" + fotoPerfil,150
    );
    foto.setBounds(0,10,150,150);
    header.add(foto);

    JLabel username = new JLabel(perfil.getUser());
    username.setFont(new Font("Arial",Font.BOLD,22));
    username.setBounds(200,20,200,30);
    header.add(username);
    
    if(perfil.getUser().equals(currentUser.getUser())){
        JButton editBtn = new JButton ("Edit Profile");
        editBtn.setBounds(200,130,140,35);
        
        editBtn.addActionListener(e-> {
            
        });
        
        header.add(editBtn);
    }
    
    if(!perfil.getUser().equals(currentUser.getUser())){
        FollowManager fm = new FollowManager();
        JButton followBtn = new JButton();
        
        
        boolean following = fm.userAlreadyFollowing(
        currentUser.getUser(),
        perfil.getUser()
        );
        
        if(following){
            followBtn.setText("Following");
        }else{
            followBtn.setText("Follow");
        }
        
        followBtn.setBounds(200,130,120,35);
        followBtn.addActionListener(e -> {

    try{

        if(fm.userAlreadyFollowing(currentUser.getUser(),perfil.getUser())){

            fm.unfollowUser(currentUser.getUser(), perfil.getUser());
            followBtn.setText("Follow");

        }

        else if(perfil.isCuentaPrivada()){

            fm.sendFollowRequest(currentUser.getUser(), perfil.getUser());
            followBtn.setText("Requested");
            followBtn.setEnabled(false);

        }

        else{

            fm.followUser(currentUser.getUser(), perfil.getUser());
            fm.followers(currentUser.getUser(), perfil.getUser());

            followBtn.setText("Following");

        }
        
        refreshProfile();

    }catch(Exception ex){
        ex.printStackTrace();
    }
});
        
         if (following || !perfil.isCuentaPrivada()){
         JButton messageBtn = new JButton("Message");
         messageBtn.setBounds(330,130,120,35);
         
         messageBtn.addActionListener(e->{
             new MessagesPanel(currentUser);
         });
         
         header.add(messageBtn);
    }
        
        header.add(followBtn);
        
        if(perfil.isCuentaPrivada() && !following){
            JLabel privado = new JLabel ("Este perfil es privado");
            privado.setBounds(200,165,250,20);
            
            header.add(privado);
        }
    }
    

    JLabel nombre = new JLabel(perfil.getFullName());
    nombre.setBounds(200,50,300,25);
    header.add(nombre);

    int postCount = 0;

    try{
        PostManager pm = new PostManager();
        postCount = pm.getUserPosts(perfil.getUser()).size();
    }catch(Exception e){}

    JLabel posts = new JLabel(postCount + " posts");
    posts.setFont(new Font("Arial",Font.BOLD,14));
    posts.setBounds(200,90,100,30);
    header.add(posts);

    JLabel followers = new JLabel(contarFollowers() + " followers");
    followers.setFont(new Font("Arial",Font.BOLD,14));
    followers.setBounds(320,90,150,30);
    
    followers.addMouseListener(new java.awt.event.MouseAdapter() {
          
        public void mouseClicked(java.awt.event.MouseEvent e){
            new FollowListGUI(perfil.getUser(),"followers");
        }
    });
    
    header.add(followers);

    JLabel following = new JLabel(contarFollowing() + " following");
    following.setFont(new Font("Arial",Font.BOLD,14));
    following.setBounds(470,90,150,30);
    
    following.addMouseListener(new java.awt.event.MouseAdapter() {
        
        public void mouseClicked(java.awt.event.MouseEvent e){
            new FollowListGUI(perfil.getUser(),"following");
        }
    });
    header.add(following);

    JSeparator linea = new JSeparator();
    linea.setBounds(0,170,800,1);
    header.add(linea);
}
    
    private int contarFollowers(){
        int count =0;
        try{
            RandomAccessFile file = new RandomAccessFile(
              "INSTA_RAIZ/"+perfil.getUser()+"/followers.ins","r"
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
                    "INSTA_RAIZ/"+perfil.getUser()+"/following.ins","r"
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
        gridPosts.setLayout(new FlowLayout(FlowLayout.CENTER,20,20)); 
        gridPosts.setBackground(Color.white);
        
        JScrollPane scroll = new JScrollPane(gridPosts);
        scroll.setBounds(200,220,800,450);
        scroll.setBorder(null);
        add(scroll);   
    }
    
    private void cargarPosts(){

    gridPosts.removeAll();

    try{
        
        FollowManager fm = new FollowManager();
        if(perfil.isCuentaPrivada() && 
           !perfil.getUser().equals(currentUser.getUser()) && 
           !fm.userAlreadyFollowing(currentUser.getUser(), perfil.getUser())){
            
            JLabel privado = new JLabel("Cuenta privada");
            gridPosts.add(privado);
            return;
        }
        
        PostManager pm = new PostManager();
        ArrayList<Post> posts = pm.getUserPosts(perfil.getUser());
        posts.sort((a,b) -> Long.compare(b.getDate(), a.getDate()));

        if(posts.isEmpty()){

            JLabel vacio = new JLabel("No hay publicaciones.");
            gridPosts.add(vacio);

        }else{

            for(Post p : posts){

                if(p.getRutaImagen()!=null){

                    ImageIcon img = new ImageIcon(p.getRutaImagen());
                    Image scaled = img.getImage().getScaledInstance(240,240,Image.SCALE_SMOOTH);

                    JLabel postImg = new JLabel(new ImageIcon(scaled));

                    postImg.addMouseListener(new java.awt.event.MouseAdapter(){

                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e){

                            new PostViewerGUI(p,currentUser.getUser());

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
       public void refreshProfile() throws IOException{
        removeAll();
        
        crearHeader();
        crearGridPosts();
        cargarPosts();
        
        revalidate();
        repaint();
    }
}
