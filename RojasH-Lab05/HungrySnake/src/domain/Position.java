package domain;

/**
 * Una casilla del tablero, dada por su fila y su columna.
 * Una vez creada no cambia.
 *
 * @author  Juan David Rojas Heredia
 * @version 28-10-2026
 */
public class Position {
    private final int fila;
    private final int columna;

    /**
     * Crea una posición.
     *
     * @param fila la fila de la casilla
     * @param columna la columna de la casilla
     */
    public Position(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Da la fila de la casilla.
     *
     * @return la fila
     */
    public int getFila(){
        return fila;
    }

    /**
     * Da la columna de la casilla.
     *
     * @return la columna
     */
    public int getColumna(){
        return columna;
    }

    /**
     * Dice si otra posición es la misma casilla, o sea, si tiene
     * la misma fila y la misma columna.
     *
     * @param otro el objeto con el que se compara
     * @return true si es una posición con la misma fila y columna, false si no
     */
    @Override
    public boolean equals(Object otro){
        if (this == otro) return true;
        if (otro instanceof Position){
            Position p = (Position) otro;
            if (p.getFila() == this.fila && p.getColumna() == this.columna){
                return true;
            }
        }
        return false;
    }

    /**
     * Da un número que depende de la fila y la columna. Posiciones iguales
     * dan el mismo número, como pide equals.
     *
     * @return el hashCode de la posición
     */
    //IA generativa para hacer hashCode
    @Override
    public int hashCode(){
        return 31 * fila + columna;
    }


}