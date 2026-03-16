package CarmenGabrie_RedSocial.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CircularImageLabel extends JLabel {
    private Image image;
    
    public CircularImageLabel(String path, int size){
        ImageIcon icon = new ImageIcon(path);
        image = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        
        setPreferredSize(new Dimension(size,size));
    }
    
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setClip(new java.awt.geom.Ellipse2D.Float(0,0,getWidth(),getHeight()));
        g2.drawImage(image, 0,0,getWidth(),getHeight(),null);
        g2.dispose();
    }
}
