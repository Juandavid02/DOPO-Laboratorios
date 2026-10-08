package domain;
import java.awt.Color;

/**
 * Cualquier cosa que puede estar en el safari (elefantes, arbustos, tormentas, etc.).
 * Tiene métodos para actuar cada turno, moverse y desaparecer.
 *
 * @author MoralesS-RojasH
 */
public interface Entity{
    public static final int SQUARE = 2;
    public static final int ROUND = 1;
    
    /**
    * Hace que la entidad actúe en el turno actual (por ejemplo, moverse o cambiar).
    */
    public void tic();
    
    /**
    * Segunda parte de la acción del turno, para las entidades que la necesiten.
    * Por defecto no hace nada.
    */
    public default void tac(){
    }
    
    /**
    * Devuelve la forma con la que se dibuja la entidad (cuadrada o redonda).
    * Por defecto es cuadrada.
    *
    * @return la forma de la entidad
    */
    public default int shape(){
      return SQUARE;
    }
    
    /**
    * Devuelve el color con el que se dibuja la entidad.
    *
    * @return el color de la entidad
    */
    public abstract Color getColor();
    
    /**
    * Indica si la entidad es un organismo (con energía y vida propia).
    * Por defecto no lo es.
    *
    * @return true si es un organismo, false si no
    */  
    public default boolean isOrganism(){
      return false;
    }
    
    /**
    * Devuelve el EcoSafari al que pertenece esta entidad.
    *
    * @return el hábitat de la entidad
    */
    public abstract EcoSafari getHabitat();
    
    /**
    * Hace desaparecer la entidad de su posición en el safari, dejando la
    * casilla vacía.
    *
    * @return true si se pudo hacer desaparecer, false si no se encontró
    */
    public default boolean disappear(){
    boolean ok=false;
    int [] position=this.getHabitat().find(this);
    if (position!=null){
        getHabitat().set(null,position[0],position[1]);
        ok=true;
    }
    return ok;
    }

    /**
     * Mueve la entidad sumándole filas y columnas a su posición actual,
     * siempre que la nueva posición esté dentro del safari (puede sobrescribir
     * lo que haya en la casilla destino).
     *
     * @param deltaRows cuántas filas se mueve
     * @param deltaColumns cuántas columnas se mueve
     * @return true si se pudo mover, false si no
     */
    public default boolean move(int deltaRows, int deltaColumns){
        int [] position=getHabitat().find(this);
        EcoSafari habitat=getHabitat();
        boolean ok=false;
        if (position!=null){
            int r = position[0];
            int c = position[1];
            if (habitat.isInside(r+deltaRows,c+deltaColumns)){
                habitat.set(null,r,c);
                habitat.set(this,r+deltaRows,c+deltaColumns);
                ok=true;
            }
        }
        return ok;
    }

    /**
     * Mueve la entidad sumándole filas y columnas a su posición actual,
     * pero solo si la casilla destino está vacía. No se mueve si la nueva
     * posición queda fuera del safari o si ya hay algo ahí.
     *
     * @param deltaRows cuántas filas se mueve
     * @param deltaColumns cuántas columnas se mueve
     * @return true si se pudo mover, false si no
     */
    public default boolean moveIfEmpty(int deltaRows, int deltaColumns){
        int[] position = getHabitat().find(this);
        EcoSafari habitat = getHabitat();
        boolean ok = false;
        if (position != null){
            int r = position[0];
            int c = position[1];
            int newR = r + deltaRows;
            int newC = c + deltaColumns;
            if (habitat.isInside(newR, newC) && habitat.get(newR, newC) == null){
                habitat.set(null, r, c);
                habitat.set(this, newR, newC);
                ok = true;
            }
        }
        return ok;
    }
}