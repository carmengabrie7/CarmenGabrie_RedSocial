package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.ChatClient;
import CarmenGabrie_RedSocial.Inbox;
import CarmenGabrie_RedSocial.InboxManager;
import CarmenGabrie_RedSocial.Usuario;
import java.awt.*;
import javax.swing.*;

public class MessagesPanel extends JPanel {

    private Usuario currentUser;

    private JPanel chatList;
    private JPanel mensajesPanel;
    private JTextField mensajeField;

    private String currentChatUser;

    public MessagesPanel(Usuario user){

        this.currentUser = user;

        setLayout(new BorderLayout());
        setBackground(Color.white);

        crearPanelIzquierdo();
        crearPanelDerecho();
        escucharMensajes();
    }

    // PANEL IZQUIERDO (lista de chats)
    private void crearPanelIzquierdo(){

        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(250,600));
        left.setBackground(Color.white);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top,BoxLayout.Y_AXIS));
        top.setBackground(Color.white);

        JLabel inbox = new JLabel("Inbox");
inbox.setFont(new Font("Arial",Font.BOLD,22));
inbox.setAlignmentX(Component.LEFT_ALIGNMENT);

JTextField search = new JTextField();
search.setMaximumSize(new Dimension(220,30));
search.setBorder(BorderFactory.createTitledBorder("Search"));

top.add(inbox);
top.add(Box.createVerticalStrut(10));
top.add(search);

left.add(top,BorderLayout.NORTH);

        left.add(search,BorderLayout.NORTH);

        chatList = new JPanel();
        chatList.setLayout(new BoxLayout(chatList,BoxLayout.Y_AXIS));
        chatList.setBackground(Color.white);

        JScrollPane scroll = new JScrollPane(chatList);
        scroll.setBorder(null);

        left.add(scroll,BorderLayout.CENTER);

        add(left,BorderLayout.WEST);

        cargarConversaciones();
    }

    // PANEL DERECHO (chat)
    private void crearPanelDerecho(){

        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(Color.white);

        mensajesPanel = new JPanel();
        mensajesPanel.setLayout(new BoxLayout(mensajesPanel,BoxLayout.Y_AXIS));
        mensajesPanel.setBackground(Color.white);
        
        JLabel empty = new JLabel("Selecciona un chat para empezar a mensajear");
empty.setFont(new Font("Arial",Font.PLAIN,16));
empty.setHorizontalAlignment(SwingConstants.CENTER);

mensajesPanel.setLayout(new BorderLayout());
mensajesPanel.add(empty,BorderLayout.CENTER);

        JScrollPane mensajesScroll = new JScrollPane(mensajesPanel);

        right.add(mensajesScroll,BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());

        mensajeField = new JTextField();
        JButton send = new JButton("Send");

        bottom.add(mensajeField,BorderLayout.CENTER);
        bottom.add(send,BorderLayout.EAST);

        send.addActionListener(e -> enviarMensaje());

        right.add(bottom,BorderLayout.SOUTH);

        add(right,BorderLayout.CENTER);
    }

    // CARGAR LISTA DE CHATS
    private void cargarConversaciones(){

        chatList.removeAll();

        try{

            InboxManager im = new InboxManager();

            for(String usuario : im.getChats(currentUser.getUser())){

                JButton chatBtn = new JButton("@"+usuario);

                chatBtn.setHorizontalAlignment(SwingConstants.LEFT);

                chatBtn.addActionListener(e -> abrirChat(usuario));

                chatList.add(chatBtn);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        chatList.revalidate();
        chatList.repaint();
    }

    // ABRIR CHAT
    private void abrirChat(String user){

        currentChatUser = user;

        mensajesPanel.removeAll();
        mensajesPanel.setLayout(new BoxLayout(mensajesPanel,BoxLayout.Y_AXIS));

        try{

            InboxManager im = new InboxManager();

            for(Inbox m : im.getConversation(currentUser.getUser(),user)){

                JLabel msg = new JLabel(m.getEmisor()+": "+m.getContenido());
                msg.setFont(new Font("Arial",Font.PLAIN,14));

                mensajesPanel.add(msg);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        mensajesPanel.revalidate();
        mensajesPanel.repaint();
    }

    // ENVIAR MENSAJE
    private void enviarMensaje(){
        
        if(mensajeField.getText().trim().isEmpty()){}

        if(currentChatUser == null) return;

        try{

            InboxManager im = new InboxManager();

            Inbox i = new Inbox(
                    currentUser.getUser(),
                    currentChatUser,
                    System.currentTimeMillis(),
                    mensajeField.getText(),
                    "text",
                    false
            );

            im.sendMessage(i);

            mensajeField.setText("");

            abrirChat(currentChatUser);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void escucharMensajes(){

    new Thread(() -> {

        try{

            ChatClient client = new ChatClient(currentUser.getUser());

            while(true){

                String msg = client.receive();

                if(msg != null){

                    String[] parts = msg.split(";",2);

                    String emisor = parts[0];
                    String texto = parts[1];

                    if(emisor.equals(currentChatUser)){

                        JLabel label = new JLabel(emisor + ": " + texto);
                        mensajesPanel.add(label);

                        mensajesPanel.revalidate();
                        mensajesPanel.repaint();
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }).start();
}
}