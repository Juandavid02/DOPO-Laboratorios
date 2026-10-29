package domain;

/**
 * Las cuatro direcciones en las que se puede mover la serpiente.
 *
 * @author  Juan David Rojas Heredia
 * @version 28-10-2026
 */
public enum Direction {
    /** Hacia arriba. */
    NORTE,
    /** Hacia abajo. */
    SUR,
    /** Hacia la derecha. */
    ESTE,
    /** Hacia la izquierda. */
    OESTE;

    /**
     * Da la dirección contraria a esta.
     * Por ejemplo, la contraria de NORTE es SUR.
     *
     * @return la dirección opuesta
     */
    //Ayuda de herramienta de IA 
    public Direction opuesta() {
        switch (this) {
            case NORTE: return SUR;
            case SUR: return NORTE;
            case ESTE: return OESTE;
            case OESTE: return ESTE;
            default: return this;
        }
    }
}