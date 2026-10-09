package domain;
import java.util.TreeMap;

public class Collection extends Content{

    private String theme;
    private TreeMap<String, Content> contents;
    

    public Collection(String title, String theme){
        super(title);
        this.theme=theme;
        contents= new TreeMap<String,Content>();
    }
    
    @Override
    public int popularity() throws NeoFlixException{
        throw new NeoFlixException(NeoFlixException.TO_BE_IMPLEMENTED);
    }

    @Override
    public int rating() throws NeoFlixException{
        throw new NeoFlixException(NeoFlixException.TO_BE_IMPLEMENTED);
    }
    
    @Override
    public String data(boolean withIndicators) throws NeoFlixException{
        throw new NeoFlixException(NeoFlixException.TO_BE_IMPLEMENTED);
    }
    
}
