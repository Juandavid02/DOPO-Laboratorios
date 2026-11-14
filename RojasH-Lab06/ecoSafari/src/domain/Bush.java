package domain;
import java.awt.Color;
/**
 * Arbusto del safari. Se pone amarillo con el tiempo, se reproduce
 * y desaparece si tiene un elefante cerca.
 *
 * @author MoralesS-RojasH
 */
public class Bush extends Organism implements Entity

{
    private EcoSafari habitat;
    private int count;
    private boolean justBorn;

    /**
     * Crea un arbusto y lo pone en el hábitat.
     *
     * @param habitat el safari donde vive el arbusto
     * @param row fila donde queda
     * @param column columna donde queda
     */
    public Bush(EcoSafari habitat, int row, int column)
    {
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        count = 0;
        justBorn = false;
    }
    
    /**
     * Devuelve el color del arbusto: verde si lleva menos de 4 turnos,
     * amarillo si lleva 4 o más.
     *
     * @return el color según su edad
     */
    @Override
    public Color getColor(){
        return (count<4 ? Color.GREEN : Color.YELLOW);
    }
    
    /**
     * Devuelve el hábitat donde está el arbusto.
     *
     * @return el safari del arbusto
     */
    @Override
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    /**
     * Mira las 8 casillas de alrededor para ver si hay un elefante.
     *
     * @return true si hay un elefante cerca, false si no
     */
    private boolean elephantNearby(){
        int[] posicion = habitat.find(this);
        int fila = posicion[0];
        int columna = posicion[1];
        int[][] direcciones = {{-1, 0}, {-1, 1}, {0, 1}, {1,1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        for (int[] pos : direcciones){
            Entity e = habitat.get(fila + pos[0], columna+pos[1]);
            if (e instanceof Elephant){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Busca un espacio libre para un arbusto nuevo (arriba, abajo, izquierda o derecha).
     * Solo se reproduce si lleva 2 turnos o más y sigue verde.
     *
     * @return la posición {fila, columna} libre, o null si no puede reproducirse
     */
    private int[] reproduce(){
        if (count>=2 && this.getColor().equals(Color.GREEN)){
            int[] posicion = habitat.find(this);
            int fila = posicion[0];
            int columna = posicion[1];
            int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] pos : direcciones){
                Entity e = habitat.get(fila + pos[0], columna + pos[1]);
                if  (e == null){
                    int[] gap = new int[] {fila + pos[0], columna + pos[1]};
                   return gap;
                } 
            }
        }   
        return null;
    }
    
    /**
     * Turno del arbusto. Si acaba de nacer, no hace nada este turno.
     * Si no, envejece un turno; si hay un elefante cerca desaparece,
     * y si no, intenta reproducirse.
     */
    @Override
    public void tic(){
        if (justBorn){
            justBorn = false;
            return;
        }
        count++;
        if (this.elephantNearby()){
            this.disappear();
        }
        else {
            int[] gap = this.reproduce();
            if (gap !=null){
                Bush hijo  = new Bush(getHabitat(), gap[0], gap[1]);
                hijo.justBorn = true;
            }
        }
    }
}