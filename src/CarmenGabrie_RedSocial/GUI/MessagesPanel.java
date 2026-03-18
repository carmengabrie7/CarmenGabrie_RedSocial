package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.ChatClient;
import CarmenGabrie_RedSocial.FollowManager;
import CarmenGabrie_RedSocial.Inbox;
import CarmenGabrie_RedSocial.InboxManager;
import CarmenGabrie_RedSocial.Usuario;
import java.awt.*;
import javax.swing.*;

public class MessagesPanel extends JPanel {

    private Usuario currentUser;
    private ChatClient client;

    private JPanel chatList;
    private JPanel mensajesPanel;
    private JTextField mensajeField;

    private String currentChatUser;

    public MessagesPanel(Usuario user){

    this.currentUser = user;

   try{
    client = new ChatClient(currentUser.getUser());
    }catch(Exception e){
    client = null;
    System.out.println("Chat en modo offline");
}

    setLayout(new BorderLayout());
    setBackground(Color.white);

    crearPanelIzquierdo();
    crearPanelDerecho();
    escucharMensajes();
}
    
    public MessagesPanel(Usuario user, String chatUser){

    this(user); // llama al constructor anterior

    abrirChat(chatUser);

}

    // PANEL IZQUIERDO (lista de chats)
    private void crearPanelIzquierdo(){

        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(230,600));
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
        search.addActionListener(e -> buscarUsuarios(search.getText()));

        top.add(inbox);
        top.add(Box.createVerticalStrut(10));
        top.add(search);

        left.add(top,BorderLayout.NORTH);

        chatList = new JPanel();
        chatList.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        chatList.setLayout(new BoxLayout(chatList,BoxLayout.Y_AXIS));
        chatList.setBackground(Color.white);

        JScrollPane scroll = new JScrollPane(chatList);
        scroll.setBorder(null);

        left.add(scroll,BorderLayout.CENTER);

        add(left,BorderLayout.WEST);

        cargarConversaciones();
    }

    private void crearPanelDerecho(){

    JPanel right = new JPanel(new BorderLayout());
    right.setBackground(Color.white);

    // PANEL DE MENSAJES
    mensajesPanel = new JPanel();
    mensajesPanel.setLayout(new BoxLayout(mensajesPanel, BoxLayout.Y_AXIS));
    mensajesPanel.setBackground(Color.white);

    JScrollPane mensajesScroll = new JScrollPane(mensajesPanel);
    mensajesScroll.setBorder(null);

    right.add(mensajesScroll, BorderLayout.CENTER);

    // PANEL DE ESCRIBIR
    JPanel bottom = new JPanel(new BorderLayout(10,0));
bottom.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
bottom.setBackground(Color.white);

    mensajeField = new JTextField();
mensajeField.setFont(new Font("Arial",Font.PLAIN,14));

mensajeField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220,220,220),1,true),
        BorderFactory.createEmptyBorder(8,12,8,12)
));

    JButton send = new JButton("Send");
send.setFocusPainted(false);
send.setBackground(new Color(0,149,246));
send.setForeground(Color.white);
send.setBorder(BorderFactory.createEmptyBorder(6,15,6,15));

    bottom.add(mensajeField, BorderLayout.CENTER);
    bottom.add(send, BorderLayout.EAST);

    mensajeField.addActionListener(e -> enviarMensaje());
    send.addActionListener(e -> enviarMensaje());

    right.add(bottom, BorderLayout.SOUTH);

    add(right, BorderLayout.CENTER);
}

   private void cargarConversaciones(){

    chatList.removeAll();

    try{

        InboxManager im = new InboxManager();

        java.util.List<String> chats = im.getChats(currentUser.getUser());
        chats.sort((a,b)->{
            try{
                InboxManager im2 = new InboxManager();
                
                long lastA = im2.getLastMessageTime(currentUser.getUser(),a);
                long lastB = im2.getLastMessageTime(currentUser.getUser(),b);
                
                return Long.compare(lastB, lastA);
            }catch(Exception e){
                return 0;
            }
            
        });

        if(currentChatUser != null && !chats.contains(currentChatUser)){
            chats.add(currentChatUser);
        }

        for(String usuario : chats){

            JPanel item = new JPanel(new BorderLayout());
            item.setMaximumSize(new Dimension(250,70));
            item.setBackground(Color.white);
            item.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

            int unread = im.countUnreadMessages(currentUser.getUser(),usuario);
            
            JLabel name = new JLabel("@"+usuario);
            name.setFont(new Font("Arial",Font.BOLD,14));

            item.add(name,BorderLayout.WEST);

if(unread > 0){

    JLabel badge = new JLabel(String.valueOf(unread));
    badge.setOpaque(true);
    badge.setBackground(new Color(0,149,246));
    badge.setForeground(Color.white);
    badge.setBorder(BorderFactory.createEmptyBorder(2,6,2,6));

    item.add(badge,BorderLayout.EAST);
}

            item.addMouseListener(new java.awt.event.MouseAdapter(){

    public void mouseClicked(java.awt.event.MouseEvent e){
        abrirChat(usuario);
    }

    public void mouseEntered(java.awt.event.MouseEvent e){
        item.setBackground(new Color(245,245,245));
    }

    public void mouseExited(java.awt.event.MouseEvent e){
        item.setBackground(Color.white);
    }

    public void mousePressed(java.awt.event.MouseEvent e){

        if(SwingUtilities.isRightMouseButton(e)){

            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Eliminar conversación con " + usuario + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if(option == JOptionPane.YES_OPTION){

                try{
                    InboxManager im = new InboxManager();
                    im.deleteConversation(currentUser.getUser(), usuario);
                    cargarConversaciones();
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        }
    }
});

            chatList.add(item);
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    chatList.revalidate();
    chatList.repaint();
}
   
   private void buscarUsuarios(String texto){
       
       if(texto.trim().isEmpty()){
    cargarConversaciones();
    return;
}

    chatList.removeAll();

    try{

        FollowManager fm = new FollowManager();

        java.util.List<String> following = fm.getFollowing(currentUser.getUser());

        for(String usuario : following){

            if(usuario.toLowerCase().contains(texto.toLowerCase())){

                JPanel item = new JPanel(new BorderLayout());
                item.setMaximumSize(new Dimension(250,60));
                item.setBackground(Color.white);
                item.setBorder(BorderFactory.createEmptyBorder(8,15,8,15));

                JLabel name = new JLabel("@"+usuario);
                name.setFont(new Font("Arial",Font.BOLD,14));

                item.add(name,BorderLayout.CENTER);

                item.addMouseListener(new java.awt.event.MouseAdapter(){

                    public void mouseClicked(java.awt.event.MouseEvent e){
                        abrirChat(usuario);
                    }

                });

                chatList.add(item);
            }
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    chatList.revalidate();
    chatList.repaint();
}

   public void abrirChat(String user){

    currentChatUser = user;

    cargarConversaciones();

    mensajesPanel.removeAll();
    mensajesPanel.setLayout(new BoxLayout(mensajesPanel,BoxLayout.Y_AXIS));

    try{

        InboxManager im = new InboxManager();

        // marcar mensajes como leídos
        im.markAsRead(currentUser.getUser(), user);

        for(Inbox m : im.getConversation(currentUser.getUser(),user)){

            boolean esMio = m.getEmisor().equals(currentUser.getUser());

JPanel fila = new JPanel(new BorderLayout());
fila.setBorder(BorderFactory.createEmptyBorder(3,20,3,20));
fila.setOpaque(false);

JPanel bubble = new JPanel(new FlowLayout(
        esMio ? FlowLayout.RIGHT : FlowLayout.LEFT));

bubble.setOpaque(false);

JLabel msg = new JLabel(m.getContenido());
msg.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(230,230,230),1,true),
        BorderFactory.createEmptyBorder(8,12,8,12)
));
msg.setOpaque(true);

if(esMio){
    msg.setBackground(new Color(220,248,198));
}else{
    msg.setBackground(new Color(240,240,240));
}

bubble.add(msg);
fila.add(bubble, BorderLayout.CENTER);

mensajesPanel.add(fila);
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    mensajesPanel.revalidate();
    mensajesPanel.repaint();

    // scroll automático al último mensaje
    SwingUtilities.invokeLater(() -> {
        JScrollBar vertical = ((JScrollPane)mensajesPanel.getParent().getParent()).getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    });
}

    private void enviarMensaje(){
        
        if(mensajeField.getText().trim().isEmpty()) return;

        if(currentChatUser == null) return;

        try{
            
            String texto = mensajeField.getText();

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
            
            if(client != null){
    client.send(currentChatUser, texto);
}

            mensajeField.setText("");

            abrirChat(currentChatUser);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void escucharMensajes(){

    if(client == null) return;

    new Thread(() -> {

        try{

            while(true){

                String msg = client.receive();

                if(msg != null){

                    String[] parts = msg.split(";",2);

                    String emisor = parts[0];
                    String texto = parts[1];

                    if(emisor.equals(currentChatUser)){

                        JPanel fila = new JPanel(new BorderLayout());
fila.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));
fila.setOpaque(false);

JPanel bubble = new JPanel(new FlowLayout(FlowLayout.LEFT));
bubble.setOpaque(false);

JLabel label = new JLabel(texto);
label.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
label.setOpaque(true);
label.setBackground(new Color(240,240,240));

bubble.add(label);
fila.add(bubble, BorderLayout.CENTER);

mensajesPanel.add(fila);

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