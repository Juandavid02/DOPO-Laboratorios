package domain;
import java.awt.Color;

/**
 * Elefante del safari. Se mueve en diagonal y gasta energía cada vez que se mueve.
 *
 * @author MoralesS-RojasH
 */
public class Elephant extends Organism implements Entity{
    private final EcoSafari habitat;
    private boolean hasActed;
    
    /**
     * Crea un elefante y lo pone en el hábitat.
     *
     * @param habitat el safari donde vive el elefante
     * @param row fila donde queda
     * @param column columna donde queda
     */
    public Elephant(EcoSafari habitat,int row, int column){
        this.habitat=habitat;
        habitat.set((Entity)this, row, column);  
        hasActed=false;
    }

    /**
     * Devuelve el hábitat donde está el elefante.
     *
     * @return el safari del elefante
     */
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    /**
     * Devuelve el color del elefante: gris oscuro si tiene 80 o más de energía,
     * gris claro si tiene menos.
     *
     * @return el color según su energía
     */
    public final Color getColor(){
        return(getEnergy()>=80? Color.DARK_GRAY: Color.LIGHT_GRAY);
    }

    /**
     * Devuelve la forma del elefante.
     *
     * @return la forma redonda
     */
    public final int shape(){
        return Entity.ROUND;
    }

    /**
     * Turno del elefante. Si todavía no ha actuado, intenta moverse en diagonal
     * hacia una casilla vacía y pierde 10 de energía. Si se queda sin energía,
     * desaparece.
     */
    public void tic(){
        if ((! hasActed) && (moveIfEmpty(1, 1))) {
            changeEnergy(-10);
            if (getEnergy()==0){
                disappear();
            }
        }
        hasActed=true;
    }
    
    /**
     * Deja al elefante listo para actuar en el siguiente turno.
     */
    public void tac(){
        hasActed=false;
    }    
}