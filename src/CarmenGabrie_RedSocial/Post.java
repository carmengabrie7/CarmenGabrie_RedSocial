package CarmenGabrie_RedSocial;


public class Post {
    //datos que deben llevar cada publicacion
    
    private String author;
    private long date;
    private String content;
    private String hashtags;
    private String mentions;
    private String rutaImagen;
    private String tipoMedia;
    
    public Post(String author, long date, String content, String hashtags, String mentions, String rutaImagen, String tipoMedia){
        this.author=author;
        this.date=date;
        this.content=content;
        this.hashtags=hashtags;
        this.mentions=mentions;
        this.rutaImagen=rutaImagen;
        this.tipoMedia=tipoMedia;
    }
    
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author=author;
    }
    
    public long getDate(){
        return date;
    }
    public void setDate (long date){
        this.date=date;
    }
    
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content=content;
    }
    
    public String getHashtags(){
        return hashtags;
    }
    public void setHashtags(String hashtags){
        this.hashtags=hashtags;
    }
    
    public String getMentions(){
        return mentions;
    }
    public void setMentions(String mentions){
        this.mentions=mentions;
    }
    
    public String getRutaImagen(){
        return rutaImagen;
    }
    public void setRutaImagen(String rutaImagen){
        this.rutaImagen=rutaImagen;
    }
    
    public String getTipoMedia(){
        return tipoMedia;
    }
    public void setTipoMedia(String tipoMedia){
        this.tipoMedia=tipoMedia;
    }
}
