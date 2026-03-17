package CarmenGabrie_RedSocial.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.RandomAccessFile;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FollowListGUI extends JFrame{

    public FollowListGUI(String username,String type){

        setTitle(type);
        setSize(350,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        main.setBackground(Color.white);

        JLabel title = new JLabel(type);
        title.setFont(new Font("Arial",Font.BOLD,18));
        title.setHorizontalAlignment(JLabel.CENTER);

        add(title,BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(main);
        scroll.setBorder(null);

        add(scroll,BorderLayout.CENTER);

        try{

            RandomAccessFile file = new RandomAccessFile(
                    "INSTA_RAIZ/"+username+"/"+type.toLowerCase()+".ins","r"
            );

            while(file.getFilePointer()<file.length()){

                String user = file.readUTF();

                JLabel label = new JLabel("@"+user);
                label.setFont(new Font("Arial",Font.PLAIN,16));
                label.setBorder(javax.swing.BorderFactory.createEmptyBorder(10,15,10,10));

                main.add(label);
            }

            file.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        setVisible(true);
    }
}