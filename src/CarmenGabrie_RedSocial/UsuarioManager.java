package CarmenGabrie_RedSocial;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


public class UsuarioManager {
    
    RandomAccessFile ruser;
    private static final String RAIZ = "INSTA_RAIZ";
    
    public UsuarioManager() throws IOException{
        try {

    File mf = new File(RAIZ);

    if(!mf.exists()){
        mf.mkdir();
    }

    ruser = new RandomAccessFile(RAIZ + "/users.ins","rw");

    } catch(IOException e){
    System.out.println("Error al crear archivos.");
     }
    }
    
    public void guardarDatos(Usuario u) throws IOException{
        ruser.seek(ruser.length()); //se van agregando
        ruser.writeUTF(u.getFullName());
        ruser.writeChar(u.getGender());
        ruser.writeUTF(u.getUser());
        ruser.writeUTF(u.getPassword());
        ruser.writeLong(u.getFechaCreacion());
        ruser.writeInt(u.getAge());
        ruser.writeBoolean(u.isEstado());
        ruser.writeUTF(u.getFotoPerfil());
        ruser.writeBoolean(u.isCuentaPrivada());
    }
    
    public void crearUsuario(Usuario u) throws IOException{
        guardarDatos(u);
        createUserFolders(u);
        createUserFiles(u);
    }
    
    private String userFolder(Usuario u){
        return RAIZ + "/"+u.getUser();
    }
    
    private void createUserFolders(Usuario u) throws IOException{
         File dir = new File(userFolder(u));
         dir.mkdir();
    }
    
    private void createUserFiles(Usuario u)throws IOException{
        String dir = userFolder(u);
        
        RandomAccessFile f1 = new RandomAccessFile(dir + "/followers.ins","rw");
        RandomAccessFile f2 = new RandomAccessFile(dir + "/following.ins","rw");
        RandomAccessFile f3 = new RandomAccessFile(dir + "/insta.ins","rw");
        RandomAccessFile f4 = new RandomAccessFile(dir + "/inbox.ins","rw");
        RandomAccessFile f5 = new RandomAccessFile(dir + "/stickers.ins","rw");
        
        f1.close();
        f2.close();
        f3.close();
        f4.close();
        f5.close();
    }
    
     
    
    
    
    
}
