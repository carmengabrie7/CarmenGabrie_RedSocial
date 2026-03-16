package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Usuario;

import java.awt.Color;

import javax.swing.*;

public class MessagesPanel extends JPanel {

    private Usuario usuario;

    public MessagesPanel(Usuario usuario){

        this.usuario = usuario;

        setLayout(null);
        setBackground(Color.white);

        JLabel titulo = new JLabel("Inbox");
        titulo.setBounds(50,30,200,30);
        add(titulo);

        JTextArea chat = new JTextArea();
        chat.setEditable(false);

        JScrollPane scrollChat = new JScrollPane(chat);
        scrollChat.setBounds(50,80,500,400);
        add(scrollChat);

        JTextField mensaje = new JTextField();
        mensaje.setBounds(50,500,400,30);
        add(mensaje);

        JButton enviar = new JButton("Enviar");
        enviar.setBounds(460,500,90,30);
        add(enviar);

        enviar.addActionListener(e -> {

            String texto = mensaje.getText();

            if(!texto.isEmpty()){

                chat.append(usuario.getUser() + ": " + texto + "\n");

                mensaje.setText("");
            }
        });
    }
}