package domain;


/**
 * Elefante enfermo del safari. Se mueve en diagonal solo un turno de cada dos
 * y pierde más energía que un elefante normal.
 *
 * @author MoralesS-RojasH
 */
public class SickElephant extends Elephant
{
    private int countTicTac;
    private boolean hasActed;
    /**
     * Crea un elefante enfermo y lo pone en el hábitat.
     *
     * @param habitat el safari donde vive el elefante
     * @param row fila donde queda
     * @param column columna donde queda
     */
    public SickElephant(EcoSafari habitat,int row, int column)
    {
        super(habitat, row, column);
        countTicTac = 0;
        hasActed=false;
    }
    
    /**
     * Turno del elefante enfermo. Si todavía no ha actuado, alterna entre dos casos:
     * en un turno intenta moverse en diagonal y pierde 20 de energía; en el siguiente
     * se queda quieto y pierde 10. Si se queda sin energía, desaparece.
     */
    @Override
    public void tic() {
        if (!hasActed){
            if (countTicTac % 2 == 0) {
                if (moveIfEmpty(1, 1)){
                    changeEnergy(-20);
                    countTicTac++;
                    if (getEnergy()<=0){
                        disappear();
                    }
                } 
            }
            else {
                changeEnergy(-10);
                countTicTac++;
                if (getEnergy()<=0){
                    disappear();
                }
            }
            hasActed=true;
        }
    }
    
    /**
     * Deja al elefante enfermo listo para actuar en el siguiente turno.
     */
    @Override
    public void tac(){
        hasActed=false;
    }    
}