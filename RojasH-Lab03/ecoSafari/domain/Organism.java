package domain;

/**
 * Organismo del safari. Tiene energía entre 0 y 100.
 *
 * @author MoralesS-RojasH
 */
public abstract class Organism{
    
    private int energy; //Inv: 0<=energy<=100   
   
    /**
     * Crea un organismo nuevo con la energía en 100.
     */
    public Organism(){
        energy=100;
    }

    
    /**
     * Cambia la energía sumándole un valor. La energía siempre queda entre 0 y 100.
     *
     * @param value lo que se suma a la energía (puede ser negativo)
     */
    public final void changeEnergy(int value){
        energy+=value;
        energy= (energy<0? 0 : (energy>100? 100 : energy));
    }  
    
    /**
     * Cambia la energía según un porcentaje de la energía actual.
     * Por ejemplo, 0.1 sube el 10% y -0.1 baja el 10%.
     *
     * @param percentage el porcentaje como decimal
     */
    public final void changeEnergy(float percentage){
        changeEnergy((int)Math.ceil(energy*percentage));
    }    
    
    /**
     * Devuelve la energía actual del organismo.
     *
     * @return la energía, entre 0 y 100
     */   
    public final int getEnergy(){
        return energy;
    }    
   
    /**
     * Dice que esto es un organismo.
     *
     * @return siempre true
     */
    public final boolean isOrganism(){
        return true;
    } 
    
}