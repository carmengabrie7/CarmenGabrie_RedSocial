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
    
    public void createUser(Usuario u) throws IOException{
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
        RandomAccessFile f6 = new RandomAccessFile(dir + "/follow_requests.ins","rw");
        
        f1.close();
        f2.close();
        f3.close();
        f4.close();
        f5.close();
        f6.close();
    }
    
    public boolean userExists(String username) throws IOException{
        ruser.seek(0);
        while (ruser.getFilePointer()< ruser.length()){
            ruser.readUTF();
            ruser.readChar();
            String user = ruser.readUTF();
            ruser.readUTF();
            ruser.readLong(); //8
            ruser.readInt(); //4
            ruser.readBoolean(); //1
            ruser.readUTF(); //
            ruser.readBoolean();
            
            if (user.equals(username)){
               return true;
            }
        }
        return false;
    }
    
    public Usuario login(String username, String password) throws IOException{
        ruser.seek(0);
        while (ruser.getFilePointer() < ruser.length()){
            String fullName = ruser.readUTF();
            char gender = ruser.readChar();
            String user = ruser.readUTF();
            String pass = ruser.readUTF();
            long fecha = ruser.readLong();
            int age = ruser.readInt();
            boolean estado = ruser.readBoolean();
            String foto = ruser.readUTF();
            boolean privada = ruser.readBoolean();
            
            if (user.equals(username) && pass.equals(password) && estado){
                return new Usuario (fullName, gender, user, pass, fecha, age, estado, foto, privada);
            }
        }
        return null;
    }
    
    public Usuario searchUser(String username) throws IOException{
        ruser.seek(0);
        while (ruser.getFilePointer()<ruser.length()){
            String name = ruser.readUTF();
            char gender =ruser.readChar();
            String user = ruser.readUTF();
            String pass = ruser.readUTF();
            long fecha = ruser.readLong();
            int age = ruser.readInt();
            boolean estado =ruser.readBoolean();
            String foto = ruser.readUTF();
            boolean privada =ruser.readBoolean();
            
            if (user.equals(username) && estado){
                return new Usuario(name, gender, user, pass, fecha, age , estado, foto, privada);
            }
        }
        return null;
    }
    
    public void desactivarCuenta(String username) throws IOException{
        ruser.seek(0);
        while (ruser.getFilePointer()<ruser.length()){
            ruser.readUTF();
            ruser.readChar();
            String user = ruser.readUTF();
            ruser.readUTF();
            ruser.readLong();
            ruser.readInt();
            long pos = ruser.getFilePointer();
            ruser.readBoolean();
            ruser.readUTF();
            ruser.readBoolean();
            
            if (user.equals(username)){
                ruser.seek(pos);
                ruser.writeBoolean(false);
                return;
            }
        }
    }
    
     
    
    
    
    
}
