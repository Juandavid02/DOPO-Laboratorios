package domain;  

public abstract class Audiovisual extends Content{
    
    private int attempts;
    private int completed;
    private int votes;
    private int sumVotes; 
    //INV: 0<=completed<=attempts and 0<=votes and 0<=sumVotes<=votes*10

    public Audiovisual(String title, int attempts, int completed, int votes, int sumVotes){
        super(title);
        this.attempts=attempts;
        this.completed=completed;
        this.votes=votes;
        this.sumVotes=sumVotes;
    }
    
 
    public Audiovisual(String title){
        this(title,0,0,0,0);
    }
    
    /**
     * Calcula la popularidad: el porcentaje de usuarios que lo terminaron
     * entre los que lo empezaron.
     *
     * @return la popularidad, un número entre 0 y 100
     * @throws NeoFlixException VALUE_UNKNOWN si nadie lo ha empezado a ver,
     *         DATA_ERROR si hay más terminados que intentos
     */
    @Override
    public int popularity() throws NeoFlixException{
       if (attempts == 0) throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
       if (attempts < completed) throw new NeoFlixException(NeoFlixException.DATA_ERROR);
       return (completed*100)/attempts;
    }    
    
    
    @Override
    public int rating() throws NeoFlixException{
       if (votes == 0) throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
       if (sumVotes>votes*10) throw new NeoFlixException(NeoFlixException.DATA_ERROR);
       return sumVotes/votes;
    }  
    
    
    @Override
    public String data(boolean withIndicators){
        String theData= title;
        if (withIndicators){
            try{
                theData+=" ( " +popularity()+" - "+ rating()+" )";
            } catch (NeoFlixException e){
                theData += "*** Indicadores incompletos";
            }
        }
        return theData;
    }
}
