package domain;
import java.awt.Color;

/**
 * Hipopótamo del safari. Por ahora solo está quieto.
 */
public class Hippo implements Entity {
    private final EcoSafari habitat;

    /**
     * Crea un hipopótamo y lo pone en el hábitat.
     *
     * @param habitat el safari donde vive
     * @param row fila donde queda
     * @param column columna donde queda
     */
    public Hippo(EcoSafari habitat, int row, int column) {
        this.habitat = habitat;
        habitat.set(this, row, column);
    }

    @Override
    public void tic() {
    }

    @Override
    public Color getColor() {
        return Color.PINK;
    }

    @Override
    public EcoSafari getHabitat() {
        return habitat;
    }
}