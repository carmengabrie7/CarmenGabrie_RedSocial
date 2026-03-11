package CarmenGabrie_RedSocial;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class PostManager {
    
    private static final String RAIZ = "INSTA_RAIZ";
    
    public void createPost(Post p) throws IOException {
        RandomAccessFile rposts = new RandomAccessFile(RAIZ + "/" + p.getAuthor() + "/insta.ins","rw");
        rposts.seek(rposts.length());
        
        rposts.writeUTF(p.getAuthor());
        rposts.writeLong(p.getDate());
        rposts.writeUTF(p.getContent());
        rposts.writeUTF(p.getHashtags());
        rposts.writeUTF(p.getMentions());
        rposts.writeUTF(p.getRutaImagen());
        rposts.writeUTF(p.getTipoMedia());
        rposts.close();
    }

    public ArrayList<Post> getUserPosts(String username) throws IOException{
        ArrayList<Post> posts = new ArrayList<>();
        RandomAccessFile file = new RandomAccessFile(RAIZ + "/" + username + "/insta.ins","rw");
        
        file.seek(0);
        while (file.getFilePointer() < file.length()){
            String autor = file.readUTF();
            long fecha = file.readLong();
            String content = file.readUTF();
            String hashtags = file.readUTF();
            String mentions = file.readUTF();
            String rutaImagen = file.readUTF();
            String tipoMedia = file.readUTF();
            
            
        }
        
    }
}
