package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Post;
import CarmenGabrie_RedSocial.PostManager;
import CarmenGabrie_RedSocial.Usuario;
import CarmenGabrie_RedSocial.UsuarioManager;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class SearchPanel extends JPanel {

    private Usuario usuario;
    private JTextField campoBusqueda;
    private JPanel resultados;

    public SearchPanel(Usuario usuario){

        this.usuario = usuario;

        setLayout(null);
        setBackground(Color.white);

        JLabel titulo = new JLabel("Buscar");
        titulo.setBounds(50,30,200,30);
        add(titulo);

        campoBusqueda = new JTextField();
        campoBusqueda.setBounds(50,70,300,30);
        add(campoBusqueda);

        JButton buscar = new JButton("Buscar");
        buscar.setBounds(370,70,100,30);
        add(buscar);

        resultados = new JPanel();
        resultados.setLayout(new BoxLayout(resultados,BoxLayout.Y_AXIS));
        resultados.setBackground(Color.white);

        JScrollPane scroll = new JScrollPane(resultados);
        scroll.setBounds(50,120,600,500);
        scroll.setBorder(null);
        add(scroll);

        buscar.addActionListener(e -> buscar());
    }

    private void buscar(){

        resultados.removeAll();

        String texto = campoBusqueda.getText();

        try{

            // BUSCAR HASHTAG
            if(texto.startsWith("#")){

                PostManager pm = new PostManager();

                ArrayList<Post> posts = pm.searchHashTag(texto);

                for(Post p : posts){

                    resultados.add(new PostCardPanel(p));
                }

            }

            // BUSCAR USUARIO
            else{

                UsuarioManager um = new UsuarioManager();

                Usuario u = um.searchUser(texto);

                if(u != null){

                    JButton userBtn = new JButton(u.getUser() + " - " + u.getFullName());

userBtn.addActionListener(e -> {
    new OtherProfileGUI(usuario, u);
});

resultados.add(userBtn);

                }else{

                    resultados.add(new JLabel("Usuario no encontrado"));

                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        resultados.revalidate();
        resultados.repaint();
    }
}
