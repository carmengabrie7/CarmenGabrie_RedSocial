package CarmenGabrie_RedSocial;

import java.io.File;
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
        RandomAccessFile file = new RandomAccessFile(RAIZ + "/" + username + "/insta.ins","r");
        
        file.seek(0);
        while (file.getFilePointer() < file.length()){
            String autor = file.readUTF();
            long fecha = file.readLong();
            String content = file.readUTF();
            String hashtags = file.readUTF();
            String mentions = file.readUTF();
            String rutaImagen = file.readUTF();
            String tipoMedia = file.readUTF();
            
            Post p = new Post(
                      autor,
                      fecha,
                      content,
                      hashtags,
                      mentions,
                      rutaImagen,
                      tipoMedia
                    );
            posts.add(p);
        }
        file.close();
        return posts;
    }
    
    public ArrayList<Post> showTimeLine(String username) throws IOException{
        ArrayList<Post> timeline = new ArrayList<>();
        timeline.addAll(getUserPosts(username));
        
        RandomAccessFile following = new RandomAccessFile (RAIZ + "/" + username + "/following.ins","r");
        following.seek(0);
        
        while (following.getFilePointer() < following.length()){
            String userSeguido = following.readUTF();
            timeline.addAll(getUserPosts(userSeguido));
        }
        following.close();
        
        return timeline;
    }
    
    public ArrayList<Post> searchHashTag(String hashtag) throws IOException{
        ArrayList<Post> resultados = new ArrayList<>();
        
        File raiz = new File(RAIZ);
        File[] usuarios = raiz.listFiles();
        
        if (usuarios==null){
            return resultados;
        }
        
        for (File usuario : usuarios){
            
            if (usuario.isDirectory()){
                
            ArrayList<Post> posts = getUserPosts(usuario.getName());
            
                for (Post p : posts){
                    if (p.getHashtags().contains(hashtag)){
                        resultados.add(p);
                    }
                }
            }
        }
        return resultados;
    }
    
    public ArrayList<Post> searchMentions (String username) throws IOException{
        ArrayList<Post> resultados = new ArrayList<>();
        
        File raiz = new File(RAIZ);
        File[] usuarios = raiz.listFiles();
        
        for (File usuario : usuarios){
            if (usuario.isDirectory()){
                
                ArrayList<Post> posts = getUserPosts(usuario.getName());
                
                for (Post p : posts){
                    if (p.getMentions().contains("@" + username));
                    resultados.add(p);                
            }
        }
        }
        return resultados;
    }
    
    public ArrayList<Post> getFeedPosts(String username) throws IOException {
        ArrayList<Post> feed = new ArrayList<>();
        
        feed.addAll(getUserPosts(username));
        
        RandomAccessFile following =new RandomAccessFile(RAIZ + "/" + username + "/following.ins","rw");
        following.seek(0);
        
        while(following.getFilePointer() < following.length()){
            String seguido = following.readUTF();
            
            try{
                feed.addAll(getUserPosts(seguido));
            }catch(Exception e){
                
            }
        }
        following.close();
        return feed;
    }
    
}
    
    
    

