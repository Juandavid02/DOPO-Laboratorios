package domain;
import java.awt.Color;

/**
 * Hierba (pasto): el recurso que comen las cebras.
 * No es un Organismo (no tiene energía) y no actúa.
 * Cuando desaparece (una cebra se la come), es reemplazada por suelo.
 */

public class Grass implements Entity{
    private final EcoSafari habitat;

    /**
     * Crea una nueva Hierba y la coloca en el hábitat
     * @param habitat el EcoSafari donde vive
     * @param row la fila
     * @param column la columna
     */
    public Grass(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
    }

    @Override
    public void tic(){
    }

    @Override
    public Color getColor(){
        return new Color(34, 139, 34);
    }

    @Override
    public EcoSafari getHabitat(){
        return habitat;
    }

    /**
     * Elimina la hierba y deja suelo en su lugar
     * @return true si se encontraba en el hábitat
     */

    @Override
    public boolean disappear(){
        int[] position = habitat.find(this);
        boolean ok = false;
        if (position != null){
            new Soil(habitat, position[0], position[1]);
            ok = true;
        }
        return ok;
    }
}