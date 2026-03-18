
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
    
    public int countUnreadMessages(String receptor,String emisor) throws IOException{

    int count = 0;

    RandomAccessFile file = new RandomAccessFile(
            RAIZ + "/" + receptor + "/inbox.ins","r");

    file.seek(0);

    while(file.getFilePointer() < file.length()){

        String em = file.readUTF();
        String rec = file.readUTF();
        long fecha = file.readLong();
        String contenido = file.readUTF();
        String tipo = file.readUTF();
        boolean estado = file.readBoolean();

        if(rec.equals(receptor) && em.equals(emisor) && !estado){
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
    
    public ArrayList<String> getChats(String username) throws IOException{
        ArrayList<String> chats = new ArrayList<>();
        
        RandomAccessFile file = new RandomAccessFile(
             RAIZ + "/" + username + "/inbox.ins","r"
        );
        
        file.seek(0);
        
        while(file.getFilePointer() < file.length()){

        String emisor = file.readUTF();
        String receptor = file.readUTF();
        file.readLong();
        file.readUTF();
        file.readUTF();
        file.readBoolean();

        String otro;

        if(emisor.equals(username))
            otro = receptor;
        else
            otro = emisor;

        if(!chats.contains(otro))
            chats.add(otro);
    }

    file.close();

    return chats;
    }
    
    public void deleteConversation(String user1, String user2){

    try{

        RandomAccessFile file = new RandomAccessFile(RAIZ + "/" + user1 + "/inbox.ins","rw");

        ArrayList<Inbox> mensajes = new ArrayList<>();

        while(file.getFilePointer() < file.length()){

            String emisor = file.readUTF();
            String receptor = file.readUTF();
            long fecha = file.readLong();
            String contenido = file.readUTF();
            String tipo = file.readUTF();
            boolean estado = file.readBoolean();

            Inbox m = new Inbox(emisor,receptor,fecha,contenido,tipo,estado);

            boolean mismaConversacion =
                    (emisor.equals(user1) && receptor.equals(user2)) ||
                    (emisor.equals(user2) && receptor.equals(user1));

            if(!mismaConversacion){
                mensajes.add(m);
            }
        }

        file.setLength(0);
        file.seek(0);

        for(Inbox m : mensajes){

            file.writeUTF(m.getEmisor());
            file.writeUTF(m.getReceptor());
            file.writeLong(m.getFecha());
            file.writeUTF(m.getContenido());
            file.writeUTF(m.getTipo());
            file.writeBoolean(m.isEstado());
        }

        file.close();

    }catch(Exception e){
        e.printStackTrace();
    }
}
    
    public long getLastMessageTime(String user, String otherUser) throws IOException{

    long last = 0;

    RandomAccessFile file = new RandomAccessFile(
            RAIZ + "/" + user + "/inbox.ins","r");

    file.seek(0);

    while(file.getFilePointer() < file.length()){

        String emisor = file.readUTF();
        String receptor = file.readUTF();
        long fecha = file.readLong();
        String contenido = file.readUTF();
        String tipo = file.readUTF();
        boolean estado = file.readBoolean();

        if(
            (emisor.equals(user) && receptor.equals(otherUser)) ||
            (emisor.equals(otherUser) && receptor.equals(user))
        ){
            if(fecha > last){
                last = fecha;
            }
        }
    }

    file.close();
    return last;
}
}
