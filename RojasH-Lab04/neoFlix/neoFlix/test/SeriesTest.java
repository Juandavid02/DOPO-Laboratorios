package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SeriesTest{
   
 
    @Test
    public void shouldCalculateSeriessRating(){                              
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("The Hidden Village",s,120,199,50,430));
        s.addEpisode(new Episode("The First Mission",s,140,126,60,510));
        s.addEpisode(new Episode("The Rival",s,120,199,50,430));
        s.addEpisode(new Episode("The Tournament",s,180, 162, 90, 810));
        s.addEpisode(new Episode("The Final Battle",s, 200, 190, 120, 1080));
        try {
           assertEquals(8,s.rating());
        } catch (NeoFlixException e){
            fail("Threw a exception "+e.getMessage());
        }    
    }    

    
    @Test
    public void shouldThrowExceptionIfTheSeriessHasNoEpisodes(){
        Series s = new Series("The Neo Ninja", 2020);
        try { 
           int value=s.rating();
           fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.CONTENT_EMPTY,e.getMessage());
        }    
    }    
    
    
    @Test
    public void shouldThrowExceptionWhenAnEpisodeRatingIsUnknown(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("The Hidden Village",s,120,199,50,430));
        s.addEpisode(new Episode("The First Mission",s,140,126,60,510));
        s.addEpisode(new Episode("The Rival",s,120,199,0,0));
        s.addEpisode(new Episode("The Tournament",s,180, 162, 90, 810));
        s.addEpisode(new Episode("The Final Battle",s, 200, 190, 120, 1080));
        try { 
           int value=s.rating();
           fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.VALUE_UNKNOWN,e.getMessage());
        }    
    } 
    
   @Test
    public void shouldThrowExceptionIfAEpisodeContainsInvalidRatingData(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("The Hidden Village",s,120,199,50,430));
        s.addEpisode(new Episode("The First Mission",s,140,126,60,610));
        s.addEpisode(new Episode("The Rival",s,120,199,0,0));
        s.addEpisode(new Episode("The Tournament",s,180, 162, 90, 810));
        s.addEpisode(new Episode("The Final Battle",s, 200, 190, 120, 1080));
        try { 
           int value=s.rating();
           fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.DATA_ERROR,e.getMessage());
        }    
    }
    
   @Test
    public void shouldUseDefaultWhenAnEpisodeRatingIsUnknown(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("The Hidden Village",s,120,100,50,430));  // rating 8
        s.addEpisode(new Episode("The First Mission",s,0,0,0,0));          // desconocido
        s.addEpisode(new Episode("The Rival",s,100,90,60,510));            // rating 8
        try {
            assertEquals(7,s.rating(5));   // (8+5+8)/3
        } catch (NeoFlixException e){
            fail("Threw a exception "+e.getMessage());
        }
    }
    
    @Test
    public void shouldIgnoreEpisodesWithDataErrorWhenUsingDefault(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("The Hidden Village",s,120,100,50,430));  // rating 8
        s.addEpisode(new Episode("The First Mission",s,100,90,10,200));    // error de datos
        s.addEpisode(new Episode("The Rival",s,100,90,50,300));            // rating 6
        try {
            assertEquals(7,s.rating(5));   // (8+6)/2
        } catch (NeoFlixException e){
            fail("Threw a exception "+e.getMessage());
        }
    }
    
    @Test
    public void shouldThrowExceptionWhenAllEpisodesHaveDataErrorWithDefault(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("The Hidden Village",s,100,90,10,200));   // error de datos
        s.addEpisode(new Episode("The First Mission",s,100,90,10,300));    // error de datos
        try {
            int value = s.rating(5);
        } catch (NeoFlixException e){
            assertEquals(NeoFlixException.VALUE_UNKNOWN,e.getMessage());
        }
    }
        
    @Test
    public void shouldUsePreviousAverageWhenEpisodeUnknown(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("Uno",s,100,90,50,400));   // 8
        s.addEpisode(new Episode("Dos",s,100,90,20,120));   // 6
        s.addEpisode(new Episode("Tres",s,0,0,0,0));        // desconocido
        try {
            assertEquals(7,s.rating(true));   // (8+6+7)/3
        } catch (NeoFlixException e){
            fail("Threw a exception "+e.getMessage());
        }
    }
    
    @Test
    public void shouldCountReplacedEpisodesAsPrevious(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("Uno",s,100,90,50,400));   // 8
        s.addEpisode(new Episode("Dos",s,0,0,0,0));         // desconocido
        s.addEpisode(new Episode("Tres",s,0,0,0,0));        // desconocido
        try {
            assertEquals(8,s.rating(true));   // (8+8+8)/3
        } catch (NeoFlixException e){
            fail("Threw a exception "+e.getMessage());
        }
    }
    
    @Test
    public void shouldFollowTheBoardWhenPreviousIsTrue(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("Uno",s,100,90,100,1000)); // 10
        s.addEpisode(new Episode("Dos",s,0,0,0,0));         // desconocido
        s.addEpisode(new Episode("Tres",s,100,90,10,40));   // 4
        s.addEpisode(new Episode("Cuatro",s,0,0,0,0));      // desconocido
        try {
            assertEquals(8,s.rating(true));   // (10+10+4+8)/4
        } catch (NeoFlixException e){
            fail("Threw a exception "+e.getMessage());
        }
    }
    
    @Test
    public void shouldThrowUnknownWhenFirstEpisodeIsUnknownAndPreviousIsTrue(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("Uno",s,0,0,0,0));         // desconocido
        s.addEpisode(new Episode("Dos",s,100,90,50,400));   // 8
        try {
            int value = s.rating(true);
            fail("Did not throw exception");
        } catch (NeoFlixException e){
            assertEquals(NeoFlixException.VALUE_UNKNOWN,e.getMessage());
        }
    }
    
    @Test
    public void shouldUseAverageOfAllWhenPreviousIsFalse(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("Uno",s,100,90,100,1000)); // 10
        s.addEpisode(new Episode("Dos",s,100,90,50,540));   // error de datos
        s.addEpisode(new Episode("Tres",s,0,0,0,0));        // desconocido
        s.addEpisode(new Episode("Cuatro",s,100,90,10,40)); // 4
        try {
            assertEquals(7,s.rating(false));  // (10+7+7+4)/4
        } catch (NeoFlixException e){
            fail("Threw a exception "+e.getMessage());
        }
    }
    
    @Test
    public void shouldThrowUnknownWhenNoEpisodeHasRatingAndPreviousIsFalse(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("Uno",s,0,0,0,0));
        s.addEpisode(new Episode("Dos",s,100,90,50,540));   // error de datos
        try {
            int value = s.rating(false);
            fail("Did not throw exception");
        } catch (NeoFlixException e){
            assertEquals(NeoFlixException.VALUE_UNKNOWN,e.getMessage());
        }
    }
    
    
    @Test
    public void shouldCalculateSeriesPopularity(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("Uno",s,100,80,0,0));    // (80*100/100)=80
        s.addEpisode(new Episode("Dos",s,50,25,0,0));     // 50
        s.addEpisode(new Episode("Tres",s,20,10,0,0));    // 50
        try {
            assertEquals(60,s.popularity());   // (80+50+50)/3
        } catch (NeoFlixException e){
            fail("Threw a exception "+e.getMessage());
        }
    }
    
    @Test
    public void shouldIgnoreEpisodesWithoutAttemptsInPopularity(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("Uno",s,100,80,0,0));    // 80
        s.addEpisode(new Episode("Dos",s,50,25,0,0));     // 50
        s.addEpisode(new Episode("Tres",s,20,10,0,0));    // 50
        s.addEpisode(new Episode("Cuatro",s,0,0,0,0));    // sin intentos, se ignora
        try {
            assertEquals(60,s.popularity());   // sigue dividiendo entre 3
        } catch (NeoFlixException e){
            fail("Threw a exception "+e.getMessage());
        }
    }
    
    @Test
    public void shouldThrowDataErrorWhenAnEpisodeHasMoreCompletedThanAttempts(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("Uno",s,100,80,0,0));
        s.addEpisode(new Episode("Dos",s,50,25,0,0));
        s.addEpisode(new Episode("Tres",s,10,15,0,0));    // terminados > intentos
        try {
            int value = s.popularity();
            fail("Did not throw exception");
        } catch (NeoFlixException e){
            assertEquals(NeoFlixException.DATA_ERROR,e.getMessage());
        }
    }
    
    @Test
    public void shouldThrowUnknownWhenNoEpisodeHasAttempts(){
        Series s = new Series("The Neo Ninja", 2020);
        s.addEpisode(new Episode("Uno",s,0,0,0,0));
        s.addEpisode(new Episode("Dos",s,0,0,0,0));
        try {
            int value = s.popularity();
            fail("Did not throw exception");
        } catch (NeoFlixException e){
            assertEquals(NeoFlixException.VALUE_UNKNOWN,e.getMessage());
        }
    }
    
    @Test
    public void shouldThrowExceptionIfThePopularityOfAnEmptySeries(){
        Series s = new Series("The Neo Ninja", 2020);
        try {
            int value = s.popularity();
            fail("Did not throw exception");
        } catch (NeoFlixException e){
            assertEquals(NeoFlixException.CONTENT_EMPTY,e.getMessage());
        }
    }
}