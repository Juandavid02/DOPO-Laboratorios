/**
 * Representa una pared del laberinto mediante dos puntos y un rectangulo
 * que indican sus coordenadas inicial y final.
 *
 * Los puntos son las coordenadas inicial y final y el rectángulo es la
 * representacion visual.
 *
 * @author MoralesS - RojasH
 * @version 1.0 (10 August 2026)
 */
public class Wall
{

    private Rectangle rectangle;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    /**
     * Crea una nueva pared utilizando las coordenadas
     * de sus dos puntos extremos.
     *
     * También crea el rectángulo que representa visualmente
     * la pared.
     *
     * @param x1 coordenada x del primer punto.
     * @param y1 coordenada y del primer punto.
     * @param x2 coordenada x del segundo punto.
     * @param y2 coordenada y del segundo punto.
     */
    public Wall(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        rectangle = new Rectangle();
    }

    /**
     * Obtiene la coordenada x del primer punto de la pared.
     *
     * @return coordenada x del primer punto.
     */
    public int getX1(){
        return x1;
    }

    /**
     * Obtiene la coordenada y del primer punto de la pared.
     *
     * @return coordenada y del primer punto.
     */
    public int getY1(){
        return y1;
    }

    /**
     * Obtiene la coordenada x del segundo punto de la pared.
     *
     * @return coordenada x del segundo punto.
     */
    public int getX2(){
        return x2;
    }
    
    /**
     * Obtiene la coordenada y del segundo punto de la pared.
     *
     * @return coordenada y del segundo punto.
     */
    public int getY2(){
        return y2;
    }
    
    /**
     * Obtiene el rectángulo utilizado para representar
     * visualmente la pared.
     *
     * @return rectángulo que representa la pared.
     */
    public Rectangle getRectangle(){
        return rectangle;
    }
}