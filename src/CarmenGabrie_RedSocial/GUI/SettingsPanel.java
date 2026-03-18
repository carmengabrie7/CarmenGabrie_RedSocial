package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Usuario;
import CarmenGabrie_RedSocial.UsuarioManager;
import java.awt.*;
import javax.swing.*;

public class SettingsPanel extends JPanel{

    private CardLayout card;
    private JPanel content;

    public SettingsPanel(Usuario user){

        setLayout(new BorderLayout());
        setBackground(Color.white);


        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(10,1));
        menu.setPreferredSize(new Dimension(200,600));
        menu.setBackground(Color.white);


        JButton editProfile = new JButton("Edit Profile");
        JButton deactivate = new JButton("Deactivate Account");

        menu.add(editProfile);
        menu.add(deactivate);

        add(menu,BorderLayout.WEST);


        card = new CardLayout();
        content = new JPanel(card);

        content.add(new EditProfilePanel(user),"edit");

        add(content,BorderLayout.CENTER);
        card.show(content, "edit");

        editProfile.addActionListener(e->{
            card.show(content,"edit");
        });


        deactivate.addActionListener(e->{

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas desactivar tu cuenta?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if(confirm == JOptionPane.YES_OPTION){

                try{

                    UsuarioManager um = new UsuarioManager();
                    um.desactivarCuenta(user.getUser());

                    JOptionPane.showMessageDialog(this,"Cuenta desactivada");

                    SwingUtilities.getWindowAncestor(this).dispose();
new inicioGUI();

                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }

        });

    }
}