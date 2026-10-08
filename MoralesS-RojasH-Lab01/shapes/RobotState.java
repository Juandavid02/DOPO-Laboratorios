/**
 * Representa el estado del robot en un momento determinado.
 *
 * Guarda la posición y dirección del robot para poder
 * restaurarlas posteriormente mediante la funcionalidad
 * de deshacer.
 *
 * @author MoralesS - RojasH
 * @version 1.0 (12 August 2026)
 */
public class RobotState
{
    private int x;
    private int y;
    private char direction;

    /**
     * Crea un estado del robot con la posición y dirección
     * especificadas.
     *
     * @param x coordenada x del robot.
     * @param y coordenada y del robot.
     * @param direction dirección del robot.
     */
    public RobotState(int x, int y, char direction)
    {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    /**
     * Obtiene la coordenada x guardada.
     *
     * @return coordenada x.
     */
    public int getX()
    {
        return x;
    }

    /**
     * Obtiene la coordenada y guardada.
     *
     * @return coordenada y.
     */
    public int getY()
    {
        return y;
    }

    /**
     * Obtiene la dirección guardada.
     *
     * @return dirección del robot.
     */
    public char getDirection()
    {
        return direction;
    }

}