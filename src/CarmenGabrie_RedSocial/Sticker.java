package CarmenGabrie_RedSocial;


public class Sticker {
    private String nombre;
    private String ruta;
    
    public Sticker(String nombre, String ruta){
        this.nombre=nombre;
        this.ruta=ruta;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getRuta(){
        return ruta;
    }
}
