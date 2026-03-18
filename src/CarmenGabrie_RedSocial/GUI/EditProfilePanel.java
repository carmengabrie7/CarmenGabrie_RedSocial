package CarmenGabrie_RedSocial.GUI;

import CarmenGabrie_RedSocial.Usuario;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import javax.swing.*;

public class EditProfilePanel extends JPanel{

    public EditProfilePanel(Usuario user){

        setLayout(null);
        setBackground(Color.white);

        JLabel title = new JLabel("Edit Profile");
        title.setFont(new Font("Arial",Font.BOLD,22));
        title.setBounds(200,40,200,40);
        add(title);


        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setBounds(200,120,200,25);
        add(nameLabel);

        JTextField name = new JTextField(user.getFullName());
        name.setBounds(200,150,300,35);
        add(name);


        JCheckBox privada = new JCheckBox("Private account");
        privada.setSelected(user.isCuentaPrivada());
        privada.setBounds(200,210,200,30);
        add(privada);


        JButton changePhoto = new JButton("Change profile photo");
        changePhoto.setBounds(200,260,200,35);

        changePhoto.addActionListener(e -> {

    JFileChooser chooser = new JFileChooser();
    int result = chooser.showOpenDialog(null);

    if(result == JFileChooser.APPROVE_OPTION){

        File file = chooser.getSelectedFile();

        try{

            File destino = new File(
                "src/CarmenGabrie_RedSocial/imagenes/" + file.getName()
            );

            java.nio.file.Files.copy(
                file.toPath(),
                destino.toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            user.setFotoPerfil(file.getName());

            JOptionPane.showMessageDialog(this,"Foto actualizada");

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

});

        add(changePhoto);


        JButton save = new JButton("Save changes");
        save.setBounds(200,330,200,40);

        save.addActionListener(e->{

            user.setFullName(name.getText());
            user.setCuentaPrivada(privada.isSelected());

            JOptionPane.showMessageDialog(this,"Perfil actualizado");

        });

        add(save);
    }
}