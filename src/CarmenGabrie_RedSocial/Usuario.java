package CarmenGabrie_RedSocial;


public class Usuario{
    private String fullName;
    private char gender;
    private String user;
    private String password;
    private long fechaCreacion;
    private int age;
    private boolean estado;
    private String fotoPerfil;
    private boolean cuentaPrivada;
    
    public Usuario(String fullName, char gender,String user, String password, long fechaCreacion, int age, boolean estado, String fotoPerfil, boolean cuentaPrivada){
        this.fullName=fullName;
        this.gender=gender;
        this.user=user;
        this.password=password;
        this.fechaCreacion=fechaCreacion;
        this.age=age;
        this.estado=estado;
        this.fotoPerfil=fotoPerfil;
        this.cuentaPrivada=cuentaPrivada;
    }
    
    public String getFullName(){
        return fullName;
    }
    public void setFullName(String fullName){
        this.fullName=fullName;
    }
    
    public char getGender(){
        return gender;
    }
    public void setGender(char gender){
        this.gender=gender;
    }
    
    public void setUser(String user){
        this.user=user;
    }
    public String getUser(){
        return user;
    }

    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return password;
    }
    
    public long getFechaCreacion(){
        return fechaCreacion;
    }
    public void setFechaCreacion(long fechaCreacion){
        this.fechaCreacion=fechaCreacion;
    }
    
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age=age;
    }
    
    public boolean isEstado(){
        return estado;
    }
    public void setEstado(boolean estado){
        this.estado=estado;
    }
    
    public String getFotoPerfil(){
        return fotoPerfil;
    }
    public void setFotoPerfil(String fotoPerfil){
        this.fotoPerfil=fotoPerfil;
    }
    
    public boolean isCuentaPrivada(){
        return cuentaPrivada;
    }
    public void setCuentaPrivada(boolean cuentaPrivada){
        this.cuentaPrivada=cuentaPrivada;
    }

    
    
    
}
