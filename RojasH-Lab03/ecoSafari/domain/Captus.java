package domain;
import java.awt.Color;
/**
 * Write a description of class captus here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Captus extends Bush
{
    // instance variables - replace the example below with your own
    private boolean hasActed;

    /**
     * Constructor for objects of class captus
     */
    public Captus(EcoSafari habitat, int row, int column)
    {
        super(habitat, row, column);
        hasActed = false;
    }

    @Override
    public Color getColor(){
        return Color.GREEN;
    }
    
    @Override
    public void tic(){
        if (!hasActed){
            moveIfEmpty(0,1);
            hasActed = true;
        }
    }
    
    @Override
    public void tac(){
        hasActed = false; 
    }
}