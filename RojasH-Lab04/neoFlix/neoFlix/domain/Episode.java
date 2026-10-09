package domain;

public class Episode extends Audiovisual{
    private Series series;
    
    public Episode(String title, Series series){
        super(title);
        this.series=series;
    }
    
    public Episode(String title, Series series, int attempts, int completed, int votes, int sumVotes){
        super(title, attempts, completed, votes, sumVotes);
        this.series=series;
    }
}