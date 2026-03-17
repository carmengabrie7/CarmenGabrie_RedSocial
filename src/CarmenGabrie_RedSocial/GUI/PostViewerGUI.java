package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;
import CarmenGabrie_RedSocial.UsuarioManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PostViewerGUI extends JFrame {

    private JPanel comentariosPanel;
    private JTextField comentar;
    private Post post;
    private String currentUser;

    public PostViewerGUI(Post post, String currentUser){

        this.post = post;
        this.currentUser = currentUser;

        setTitle("Post");
        setSize(900,600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.white);
        add(mainPanel);


        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(Color.black);

        if(post.getRutaImagen()!=null){

            ImageIcon icon = new ImageIcon(post.getRutaImagen());
            Image scaled = icon.getImage().getScaledInstance(500,600,Image.SCALE_SMOOTH);

            JLabel imagen = new JLabel(new ImageIcon(scaled));
            left.add(imagen,BorderLayout.CENTER);
        }

        mainPanel.add(left,BorderLayout.WEST);


        JPanel right = new JPanel();
        right.setLayout(null);
        right.setBackground(Color.white);
        right.setPreferredSize(new java.awt.Dimension(400,600));

        mainPanel.add(right,BorderLayout.CENTER);

        PostManager pm = new PostManager();


        JLabel username = new JLabel("@" + post.getAuthor());
username.setForeground(Color.BLUE);
username.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

username.addMouseListener(new java.awt.event.MouseAdapter(){

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e){

        try{

            UsuarioManager um = new UsuarioManager();

            Usuario perfil = um.searchUser(post.getAuthor());

            feedGUI frame = (feedGUI) javax.swing.SwingUtilities.getWindowAncestor(username);

            frame.abrirPerfil(perfil);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
});
        username.setFont(new Font("Arial",Font.BOLD,16));
        username.setBounds(20,20,300,30);
        right.add(username);
        
        if(currentUser.equals(post.getAuthor())){

        JButton deleteBtn = new JButton("Delete");

        deleteBtn.setBounds(250,20,100,30);

        deleteBtn.addActionListener(e -> {

        pm.deletePost(post);

        dispose();

       });

    right.add(deleteBtn);
}

        JLabel content = new JLabel(
        "<html><b>@" + post.getAuthor() + "</b> " +
        post.getContent() + " " + post.getHashtags() + "</html>"
        );

        content.setBounds(20,60,350,60);
        right.add(content);


        JLabel likes = new JLabel(pm.getLikes(post) + " likes");
        likes.setFont(new Font("Arial",Font.BOLD,14));
        likes.setBounds(20,130,200,30);
        right.add(likes);


        JButton likeBtn = new JButton("❤");
        likeBtn.setBounds(20,160,60,30);

        likeBtn.addActionListener(e -> {

            pm.likePost(post,currentUser);

            likes.setText(pm.getLikes(post) + " likes");

        });

        right.add(likeBtn);
        
        JLabel linea = new JLabel("___________________________");
        linea.setBounds(20,190,350,20);
        right.add(linea);


        comentariosPanel = new JPanel();
        comentariosPanel.setLayout(new BoxLayout(
                comentariosPanel,
                BoxLayout.Y_AXIS
        ));
        comentariosPanel.setBackground(Color.white);

        JScrollPane scroll = new JScrollPane(comentariosPanel);
        scroll.setBounds(20,200,350,250);
        scroll.setBorder(null);

        right.add(scroll);


        comentar = new JTextField();
        comentar.setBounds(20,470,260,30);
        right.add(comentar);


        JButton postComment = new JButton("Post");
        postComment.setBounds(290,470,80,30);

        postComment.addActionListener(e -> {

            String texto = comentar.getText().trim();

            if(!texto.isEmpty()){

                pm.addComment(post,currentUser,texto);

                comentar.setText("");

                loadComments();
            }

        });

        right.add(postComment);


        loadComments();

        setVisible(true);
    }


    private void loadComments(){

        comentariosPanel.removeAll();

        try{

            PostManager pm = new PostManager();

            for(String c : pm.getComments(post)){

                JLabel comment = new JLabel("<html>" + c + "</html");
                comment.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
                comentariosPanel.add(comment);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        comentariosPanel.revalidate();
        comentariosPanel.repaint();
    }
}