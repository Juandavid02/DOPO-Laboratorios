package domain;
import java.awt.Color;

/**
 * León. Avanza una casilla por turno sobre tierra,
 * come la cebra que le queda vecina (gana 50% de energía) y se reproduce
 * si queda a una casilla de tierra de otro león.
 *
 * @author MoralesS-RojasH
 */
public class Lion extends Animal
{
    /**
     * Crea un león y lo pone en el safari.
     *
     * @param habitat el safari donde vive
     * @param row la fila donde nace
     * @param col la columna donde nace
     */
    public Lion(EcoSafari habitat, int row, int col)
    {
        super(habitat, row, col);
    }
    
    /**
     * Los leones comen cebras.
     *
     * @param e la entidad a revisar
     * @return true si es una cebra
     */
    @Override
    public boolean isFood(Entity e){
        if (e instanceof Zebra){
            return true;    
        } 
        return false;
    }
    
    /**
     * Los leones avanzan una casilla por turno.
     *
     * @return 1
     */
    @Override
    public int speed(){
        return 1;
    }
    
    /**
     * Al comer una cebra ganan 50% de energía.
     *
     * @return 0.5f
     */
    @Override
    public float energyGain(){
        return 0.5f;
    }
    
    /**
     * Crea un león nuevo. Nace con el turno ya gastado para que no
     * actúe en el mismo tic-tac en que nació.
     *
     * @param row la fila donde nace
     * @param col la columna donde nace
     */
    @Override
    public void createOffspring(int row, int col){
         Lion baby= new Lion(this.getHabitat(), row, col);
         baby.acted = true;
    }
    
    /**
     * Los leones son naranjas.
     *
     * @return el color naranja
     */
    public Color getColor(){
        return Color.ORANGE;
    }
}