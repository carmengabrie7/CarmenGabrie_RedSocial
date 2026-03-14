package CarmenGabrie_RedSocial;


public class ListaStickers {
    private NodoSticker first;
    
    public ListaStickers(){
        first = null;
    }
    
    public void agregarSticker(Sticker s){
        NodoSticker nuevo = new NodoSticker(s);
            
            if (first == null){
            first = nuevo;
        }else{
                NodoSticker actual = first;
                
                while(actual.next !=null){
                    actual = actual.next;
                }
                
                actual.next=nuevo;
            }
    }
}
