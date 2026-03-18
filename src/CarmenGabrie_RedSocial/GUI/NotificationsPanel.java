package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.FollowManager;
import CarmenGabrie_RedSocial.Usuario;
import java.awt.*;
import java.io.RandomAccessFile;
import javax.swing.*;

public class NotificationsPanel extends JPanel{

    private Usuario currentUser;
    private JPanel lista;

    public NotificationsPanel(Usuario currentUser){

        this.currentUser=currentUser;

        setLayout(null);
        setBackground(Color.white);

        JLabel title = new JLabel("Notifications");
        title.setFont(new Font("Arial",Font.BOLD,22));
        title.setBounds(80,20,300,30);
        add(title);

        lista = new JPanel();
        lista.setLayout(new BoxLayout(lista,BoxLayout.Y_AXIS));
        lista.setBackground(Color.white);

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBounds(80,70,500,500);
        scroll.setBorder(null);

        add(scroll);

        cargarRequests();
        cargarMensajes();
    }

    private void cargarRequests(){

        try{

            RandomAccessFile file = new RandomAccessFile(
            "INSTA_RAIZ/"+currentUser.getUser()+"/follow_requests.ins","r"
            );

            FollowManager fm = new FollowManager();

            while(file.getFilePointer()<file.length()){

                String user = file.readUTF();

                JPanel request = new JPanel(new FlowLayout(FlowLayout.LEFT));
                request.setBackground(Color.white);
                request.setMaximumSize(new Dimension(450,50));

                JLabel texto = new JLabel(user + " quiere seguirte");
                texto.setFont(new Font("Arial",Font.PLAIN,14));

                JButton accept = new JButton("Accept");
                JButton decline = new JButton("Decline");

                accept.addActionListener(e->{

                    try{

                        fm.followUser(user,currentUser.getUser());
                        fm.followers(user,currentUser.getUser());

                        lista.remove(request);
                        lista.revalidate();
                        lista.repaint();

                    }catch(Exception ex){
                        ex.printStackTrace();
                    }

                });

                decline.addActionListener(e->{

                    lista.remove(request);
                    lista.revalidate();
                    lista.repaint();

                });

                request.add(texto);
                request.add(accept);
                request.add(decline);

                lista.add(request);
            }

            file.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void cargarMensajes(){

        try{

            RandomAccessFile file = new RandomAccessFile(
            "INSTA_RAIZ/"+currentUser.getUser()+"/inbox.ins","r"
            );

            while(file.getFilePointer()<file.length()){

                String emisor = file.readUTF();
                String receptor = file.readUTF();
                long fecha = file.readLong();
                String contenido = file.readUTF();
                String tipo = file.readUTF();
                boolean estado = file.readBoolean();

                if(receptor.equals(currentUser.getUser()) && !estado){

                    JPanel notif = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    notif.setBackground(Color.white);
                    notif.setMaximumSize(new Dimension(450,40));

                    JLabel texto = new JLabel(emisor + " te envió un mensaje");

                    notif.add(texto);

                    lista.add(notif);
                }

            }

            file.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}