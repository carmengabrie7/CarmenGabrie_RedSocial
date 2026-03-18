package CarmenGabrie_RedSocial;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class FollowManager{

    private static final String RAIZ = "INSTA_RAIZ";
    
    //user1 -> mi usuario, user2 -> usuario A SEGUIR.
    
    public void followUser(String user1, String user2) throws IOException{
        RandomAccessFile following = new RandomAccessFile (RAIZ + "/" + user1 + "/following.ins","rw");
        following.seek(following.length());
        following.writeUTF(user2);
        following.close();
    }
    
    public void followers (String user1, String user2) throws IOException{
        RandomAccessFile followers = new RandomAccessFile(RAIZ + "/" + user2 + "/followers.ins","rw");
        followers.seek(followers.length());
        followers.writeUTF(user1);
        followers.close();
    }
    
    public boolean userAlreadyFollowing (String user1, String user2) throws IOException{
        RandomAccessFile following = new RandomAccessFile (RAIZ + "/" + user1 + "/following.ins","rw");
        following.seek(0);
        while (following.getFilePointer() < following.length()){
            String usuario = following.readUTF();
            
            if (usuario.equals(user2)){
                following.close();
                return true;
            }
        }
        following.close();
        return false;
    }
    
    public void unfollowUser (String user1, String user2) throws IOException{
        //eliminar user 2 de following
        RandomAccessFile following = new RandomAccessFile (RAIZ + "/" + user1 + "/following.ins","rw");
           RandomAccessFile temp = new RandomAccessFile (RAIZ + "/" + user1 + "/temp.ins","rw");
          
        following.seek(0);
        while (following.getFilePointer() < following.length()){
            String usuario = following.readUTF();
            
            if(!usuario.equals(user2)){
                temp.writeUTF(usuario); //el archivo temporal GUARDA los datos que NO deseamos borrar
            }
        }
        following.close();
        temp.close();
        
        File original = new File(RAIZ + "/" + user1 + "/following.ins");
        original.delete();
        
        File nuevo = new File(RAIZ + "/" + user1 + "/temp.ins");
        nuevo.renameTo(new File(RAIZ + "/" + user1 + "/following.ins"));
        
        //eliminar user1 de followers
        RandomAccessFile followers = new RandomAccessFile (RAIZ + "/" + user2 + "/followers.ins","rw");
        RandomAccessFile temp2 = new RandomAccessFile (RAIZ + "/" + user2 + "/temp2.ins","rw");
        
        followers.seek(0);
        while(followers.getFilePointer() < followers.length()){
            String usuario = followers.readUTF();
            
            if (!usuario.equals(user1)){
                temp2.writeUTF(usuario);
            }
        }
        followers.close();
        temp2.close();
        
        File original2 = new File(RAIZ + "/" + user2 + "/followers.ins");
        original2.delete();
        
        File nuevo2 = new File(RAIZ + "/" + user2 + "/temp2.ins");
        nuevo2.renameTo(new File(RAIZ + "/" + user2 + "/followers.ins"));
    }
    
    public void sendFollowRequest(String fromUser,String toUser) throws IOException{

    File file = new File(RAIZ + "/" + toUser + "/follow_requests.ins");

    if(!file.exists()){
        file.createNewFile();
    }

    RandomAccessFile req = new RandomAccessFile(file,"rw");

    req.seek(req.length());
    req.writeUTF(fromUser);

    req.close();
}
    
    public boolean requestAlreadySent(String fromUser,String toUser) throws IOException{

    File file = new File(RAIZ + "/" + toUser + "/follow_requests.ins");

    if(!file.exists()){
        return false;
    }

    RandomAccessFile req = new RandomAccessFile(file,"r");

    while(req.getFilePointer()<req.length()){

        String user = req.readUTF();

        if(user.equals(fromUser)){
            req.close();
            return true;
        }
    }

    req.close();
    return false;
}
    
    public java.util.List<String> getFollowing(String username) throws IOException{

    java.util.List<String> lista = new java.util.ArrayList<>();

    RandomAccessFile file = new RandomAccessFile(
            "INSTA_RAIZ/" + username + "/following.ins","r");

    while(file.getFilePointer() < file.length()){

        String user = file.readUTF();
        lista.add(user);

    }

    file.close();
    return lista;
}
    
    public ArrayList<String> getFollowRequests(String username) throws IOException{

    ArrayList<String> requests = new ArrayList<>();

    RandomAccessFile file = new RandomAccessFile(
        "INSTA_RAIZ/" + username + "/follow_requests.ins",
        "r"
    );

    while(file.getFilePointer() < file.length()){
        requests.add(file.readUTF());
    }

    file.close();
    return requests;
}
    
}
