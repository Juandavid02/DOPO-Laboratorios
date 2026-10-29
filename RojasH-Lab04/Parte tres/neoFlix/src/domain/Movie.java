package domain;

public class Movie extends Audiovisual{
    private int budge;
    
    public Movie(String title, int budge){
        super(title);
        this.budge=budge;
    }
}