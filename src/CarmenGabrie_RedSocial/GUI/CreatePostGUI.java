package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;
import java.awt.Font;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class CreatePostGUI extends JFrame {
    private Usuario usuario;
    private JTextField txtCaption;
    private JTextField txtHashTags;
    private JLabel lblImagen;
    private String rutaImagen;
    
    public CreatePostGUI(Usuario usuario){
        this.usuario=usuario;
        
        setTitle("Crear Post");
        setSize(400,350);
        setLocationRelativeTo(null);
        setLayout(null);
        
        JLabel titulo = new JLabel("Crear publicacion");
        titulo.setFont(new Font("Arial",Font.BOLD,18));
        titulo.setBounds(120,20,200,30);
        add(titulo);
        
        JButton btnImagen = new JButton ("Seleccionar imagen");
        btnImagen.setBounds(120,70,160,30);
        add(btnImagen);
        
        lblImagen = new JLabel("Ninguna imagen seleccionada");
        lblImagen.setBounds(80,110,250,20);
        add(lblImagen);
        
        JLabel captionLabel = new JLabel("Caption: ");
        captionLabel.setBounds(50,140,100,20);
        add(captionLabel);
        
        txtCaption = new JTextField();
        txtCaption.setBounds(120,140,200,25);
        add(txtCaption);
        
        JLabel hashLabel = new JLabel ("Hashtags: ");
        hashLabel.setBounds(50,180,100,20);
        add(hashLabel);
        
        txtHashTags = new JTextField();
        txtHashTags.setBounds(120,180,200,25);
        add(txtHashTags);
        
        JButton publicar = new JButton ("Publicar");
        publicar.setBounds(140,230,120,35);
        add(publicar);
        
        btnImagen.addActionListener(e-> seleccionarImagen());
        
        publicar.addActionListener(e-> publicarPost());
        
        setVisible(true);
    }
    
    private void seleccionarImagen(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            rutaImagen = file.getAbsolutePath();
            lblImagen.setText(file.getName());
        }
    }
    
    private void publicarPost(){
        try{
            String caption = txtCaption.getText();
            String hashtags = txtHashTags.getText();
            
            Post post = new Post(
                    usuario.getUser(),
                    System.currentTimeMillis(),
                    caption,
                    hashtags,
                    "",
                    rutaImagen,
                    "imagen"
            );
            
            PostManager pm = new PostManager();
            pm.createPost(post);
            
            JOptionPane.showMessageDialog(this, "Post publicado");
            
            dispose();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
