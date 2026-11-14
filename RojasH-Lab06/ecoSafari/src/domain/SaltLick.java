package domain;
import java.awt.Color;
 
/**
 * Un SaltLick es un depósito mineral, muy común en las sabanas africanas,
 * donde los animales van a reponer sus sales minerales.
 * Es una Entidad (Entity) que NO es un Organismo (Organism): no tiene energía, nunca se mueve
 * y nunca muere por causas naturales.
 * En cada tic restaura RECOVERY puntos de energía a cada organismo
 * ubicado en las ocho celdas de alrededor.
 */
public class SaltLick implements Entity{
    public static final int RECOVERY = 10;
 
    private final EcoSafari habitat;
 
    /**
     * Crea un nuevo SaltLick y lo coloca en el hábitat
     * @param habitat el EcoSafari donde vive
     * @param row la fila
     * @param column la columna
     */
    public SaltLick(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
    }
 
    /**
     * Restaura energía a cada organismo en las ocho celdas vecinas
     */
    @Override
    public void tic(){
        int[] position = habitat.find(this);
        if (position != null){
            for (int i = -1; i <= 1; i++){
                for (int j = -1; j <= 1; j++){
                    if (i != 0 || j != 0){
                        Entity e = habitat.get(position[0] + i, position[1] + j);
                        if (e != null && e.isOrganism()){
                            ((Organism)e).changeEnergy(RECOVERY);
                        }
                    }
                }
            }
        }
    }
 
    @Override
    public Color getColor(){
        return Color.PINK;
    }
 
    @Override
    public EcoSafari getHabitat(){
        return habitat;
    }
}
