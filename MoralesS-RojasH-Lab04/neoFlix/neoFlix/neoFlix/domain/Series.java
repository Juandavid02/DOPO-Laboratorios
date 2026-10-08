package domain;  
 
import java.util.ArrayList;

public class Series extends Content{
   
    private int year;
    private ArrayList<Episode> episodes;
    

    public Series(String title, int year){
        super(title);
        this.year=year;
        episodes= new ArrayList<Episode>();
    }


  
    public void addEpisode(Episode e){
        episodes.add(e);
    }
       
    
   /**
   * Returns the calculated rating
   * @return
   * @throws NeoFlixException, CONTENT_EMPTY if the series has no episodes
   *                         , VALUE_UNKNOWN if the rating for any episode is unknown
   *                         , DATA_ERROR if the rating for any episode cannot be calculated due to a data error
   */
   @Override
   public int rating() throws NeoFlixException{
       return 0;
   }
    
 
   /**
   * Returns the calculated rating, using the default value for episodes with an unknown rating and ignoring those with errors.
   * @return
   * @throws NeoFlixException, UNKNOWN_VALUE if the rating cannot be calculated.
   */

   public int rating(int default_) throws NeoFlixException{
       return 0;
   }
 
   //If an episode has no rating, use the average of the previous episodes or of all episodes, depending on the value of the previous parameter.
   //Throw CONTENT_EMPTY and VALUE_UNKNOWN if either of these cases occurs.
   public int rating(boolean previous) throws NeoFlixException{
        return 0;
   }
    
   
   public int popularity() throws NeoFlixException{
       return 0;
   }
    
    
    @Override
    public String data(boolean withIndicators) throws NeoFlixException{
        StringBuffer answer=new StringBuffer();
        answer.append(title+": "+year+ ( withIndicators ? " ( " +popularity()+" - "+ rating()+" )":""));
        for(Episode e: episodes) {
            answer.append("\n\t"+(e.data(withIndicators)));
        }
        return answer.toString();
    } 
    

}
