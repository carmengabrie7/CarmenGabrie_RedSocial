
package CarmenGabrie_RedSocial;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class InboxManager {
    
    private static final String RAIZ ="INSTA_RAIZ";
    
    public void sendMessage(Inbox i) throws IOException{
        RandomAccessFile sender = new RandomAccessFile(RAIZ + "/" + i.getEmisor() + "/inbox.ins","rw");
        sender.seek(sender.length());
        
        sender.writeUTF(i.getEmisor());
        sender.writeUTF(i.getReceptor());
        sender.writeLong(i.getFecha());
        sender.writeUTF(i.getContenido());
        sender.writeUTF(i.getTipo());
        sender.writeBoolean(i.isEstado());
        
        sender.close();
        
        RandomAccessFile receiver = new RandomAccessFile(RAIZ + "/" + i.getReceptor() + "/inbox.ins","rw");
        receiver.seek(receiver.length());
        
        receiver.writeUTF(i.getEmisor());
        receiver.writeUTF(i.getReceptor());
        receiver.writeLong(i.getFecha());
        receiver.writeUTF(i.getContenido());
        receiver.writeUTF(i.getTipo());
        receiver.writeBoolean(i.isEstado());
        
        receiver.close();
    }
    
    public ArrayList<Inbox> getConversation(String user1, String user2) throws IOException{
        ArrayList<Inbox> mensajes = new ArrayList<>();
        
        RandomAccessFile file = new RandomAccessFile (RAIZ + "/" + user1 + "/inbox.ins","r");
        file.seek(0);
        
        while (file.getFilePointer() < file.length()){
            String emisor = file.readUTF();
            String receptor = file.readUTF();
            long fecha = file.readLong();
            String contenido = file.readUTF();
            String tipo = file.readUTF();
            boolean estado = file.readBoolean();
            
            if ((emisor.equals(user1)&&receptor.equals(user2))||
                    (emisor.equals(user2) && receptor.equals(user1))){
                
                Inbox i = new Inbox(
                emisor,
                receptor,
                fecha,
                contenido,
                tipo,
                estado );
                
                mensajes.add(i);
            }
        }
        file.close();
        return mensajes;
    }
    
    public int countUnreadMessages(String username) throws IOException{
        int count =0;
        
        RandomAccessFile file = new RandomAccessFile (RAIZ + "/" + username + "/inbox.ins","r");
        file.seek(0);
        
        while (file.getFilePointer() < file.length()){
            String emisor = file.readUTF();
            String receptor = file.readUTF();
            long fecha = file.readLong();
            String contenido = file.readUTF();
            String tipo = file.readUTF();
            boolean estado = file.readBoolean();
            
            if (receptor.equals(username) && !estado){
                count++;
            }
        }
        file.close();
        return count;
    }
    
    public void markAsRead(String user1, String user2) throws IOException{
        RandomAccessFile file = new RandomAccessFile(RAIZ + "/" + user1 + "/inbox.ins","rw");
        file.seek(0);
        
        while (file.getFilePointer() < file.length()){
            String emisor = file.readUTF();
            String receptor = file.readUTF();
            long fecha = file.readLong();
            String contenido = file.readUTF();
            String tipo = file.readUTF();
            
            long posEstado = file.getFilePointer();
            boolean estado = file.readBoolean();
            
            if (emisor.equals(user2) && receptor.equals(user1) && !estado){
                file.seek(posEstado);
                file.writeBoolean(true);
            }
        }
        file.close();
    }
}
