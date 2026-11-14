package domain;
import java.awt.Color;

/**
 * Tormenta del safari. Cada turno se mueve en diagonal (arriba a la derecha),
 * avanzando hasta encontrar una casilla vacía, y afecta las casillas de
 * alrededor de su nueva posición.
 *
 * @author MoralesS-RojasH
 */
public class Storm implements Entity
{
    private EcoSafari habitat;
    private boolean hasCenter;
    private boolean actuo;

    public Storm(EcoSafari habitat, int row, int column)
    {
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        hasCenter = false;
        actuo = false;
    }
    
    public void tic(){
        if (actuo){
            return;
        }
    
        int[] posicion = habitat.find(this);
        int size = habitat.getSize();
        int fila = posicion[0];
        int columna = posicion[1];
        int pasos = 0;
        boolean libre = false;
    
        while (!libre && pasos < size){
            fila = fila - 1;
            columna = columna + 1;
            if (fila == -1){
                fila = size - 1;
            }
            if (columna == size){
                columna = 0;
            }
            pasos++;
            libre = puedePisar(fila, columna);
        }
    
        if (!libre){
            actuo = true;
            return;
        }
    
        if (hasCenter){
            for (int i = -1; i<=1; i++){
                for (int j = -1; j<=1; j++){
                    habitat.unsetAffected(posicion[0]+i, posicion[1]+j);
                }
            }
        }
    
        habitat.set(null, posicion[0], posicion[1]);
        habitat.set(this, fila, columna);
    
        for (int i = -1; i<=1; i++){
            for (int j = -1; j<=1; j++){
                habitat.setAffected(fila+i, columna+j);
            }
        }
        hasCenter = true;
        actuo = true;
    }
    
    public void tac(){
        actuo = false;
    }
    
    /**
     * Dice si la tormenta puede pisar una casilla: vacía, con un elefante,
     * un arbusto o un saltlick (los destruye al pasar). No puede pisar
     * tierra, pasto, cebras ni leones.
     *
     * @param r la fila
     * @param c la columna
     * @return true si puede pisar esa casilla
     */
    private boolean puedePisar(int r, int c){
        Entity e = habitat.get(r, c);
        return (e == null) || (e instanceof Elephant) || (e instanceof Bush) || (e instanceof SaltLick);
    }
    
    public Color getColor(){
        return Color.BLACK;
    }
    
    public EcoSafari getHabitat(){
        return habitat;
    }

}