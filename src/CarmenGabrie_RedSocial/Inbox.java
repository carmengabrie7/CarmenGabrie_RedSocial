package CarmenGabrie_RedSocial;


public class Inbox {
    private String emisor;
    private String receptor;
    private long fecha;
    private String contenido;
    private String tipo;
    private boolean estado;
    
    public Inbox(String emisor, String receptor, long fecha, String contenido, String tipo, boolean estado){
        this.emisor=emisor;
        this.receptor=receptor;
        this.fecha=fecha;
        this.contenido=contenido;
        this.tipo=tipo;
        this.estado=estado;
    }
    
    public String getEmisor(){
        return emisor;
    }
    public void setEmisor(String emisor){
        this.emisor=emisor;
    }
    
    public String getReceptor(){
        return receptor;
    }
    public void setReceptor(String receptor){
        this.receptor=receptor;
    }
    
    public long getFecha(){
        return fecha;
    }
    public void setFecha(long fecha){
        this.fecha=fecha;
    }
    
    public String getContenido(){
        return contenido;
    }
    public void setContenido(String contenido){
        this.contenido=contenido;
    }
    
    public String getTipo(){
        return tipo;
    }
    public void setTipo(String tipo){
        this.tipo=tipo;
    }
    
}
