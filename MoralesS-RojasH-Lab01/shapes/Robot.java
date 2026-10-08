
/**
 * Representa un robot que puede desplazarse por un laberinto,
 * cambiar de dirección y mostrar su posición y apariencia.
 * El robot está compuesto visualmente por un cuerpo, dos ojos,
 * una boca y dos pies.
 *
 * @author MoralesS - RojasH
 * @version 1.0 (08 August 2026)
 */
public class Robot
{
    private static final int CELL_PADDING = 20;
    
    private int x;
    private int y;
    private char direction;
    private boolean ok;
    // Diseño del robot
    private Rectangle body;
    private Circle eye1;
    private Circle eye2;
    private Rectangle mouth;
    private Rectangle foot1;
    private Rectangle foot2;
    // Posición actual de cada parte relativa al cuerpo
    private int eye1X = 10,  eye1Y = 10;
    private int eye2X = 30, eye2Y = 10;
    private int mouthX = 12, mouthY = 30, mouthW = 25, mouthH = 10;
    private int foot1X = 5,  foot1Y = 50;
    private int foot2X = 30, foot2Y = 50;
    
    /**
     * Crea un nuevo robot con los valores predeterminados.
     *
     * El robot comienza en la posición (0, 0), orientado
     * hacia el norte y con un estado correcto.
     * También crea y organiza visualmente todas las partes
     * que componen el robot.
     */
    public Robot(){
        x = 0;
        y = 0;
        direction = 'N';
        ok = true;
        
        body = new Rectangle();
        eye1 = new Circle();
        eye2 = new Circle();
        mouth = new Rectangle();
        foot1 = new Rectangle();
        foot2 = new Rectangle();
        // Organizar las partes del robot
        eye1.changeColor("black");
        eye2.changeColor("black");
        body.changeColor("blue");
        foot1.changeColor("blue");
        foot2.changeColor("blue");
        mouth.changeColor("black");
        
        body.changeSize(50, 50);
        eye1.changeSize(10);
        eye2.changeSize(10);
        mouth.changeSize(10, 24);
        foot1.changeSize(15,15);
        foot2.changeSize(15,15);

        eye1.moveHorizontal(10);
        eye1.moveVertical(10);
        eye2.moveHorizontal(30);
        eye2.moveVertical(10);
        mouth.moveHorizontal(13);
        mouth.moveVertical(30);
        foot1.moveHorizontal(5);
        foot1.moveVertical(50);
        foot2.moveHorizontal(30);
        foot2.moveVertical(50);
        
        anchorToGrid();
    }
    
    /**
     * Ajusta visualmente todas las partes del robot
     * al margen definido para la cuadrícula.
     */
    private void anchorToGrid(){
        body.moveHorizontal(CELL_PADDING);
        body.moveVertical(CELL_PADDING);
        eye1.moveHorizontal(CELL_PADDING);
        eye1.moveVertical(CELL_PADDING);
        eye2.moveHorizontal(CELL_PADDING);
        eye2.moveVertical(CELL_PADDING);
        mouth.moveHorizontal(CELL_PADDING);
        mouth.moveVertical(CELL_PADDING);
        foot1.moveHorizontal(CELL_PADDING);
        foot1.moveVertical(CELL_PADDING);
        foot2.moveHorizontal(CELL_PADDING);
        foot2.moveVertical(CELL_PADDING);
    }   
    
    /**
     * Desplaza visualmente toda la figura del robot sin modificar
     * sus coordenadas lógicas de la cuadrícula (x, y).
     * 
     * Este método se utiliza para alinear visualmente el robot
     * con el margen del tablero definido en RobotMaze.
     *
     * @param dx desplazamiento horizontal.
     * @param dy desplazamiento vertical.
     */
    public void moveOrigin(int dx, int dy){
        body.moveHorizontal(dx);
        body.moveVertical(dy);
        eye1.moveHorizontal(dx);
        eye1.moveVertical(dy);
        eye2.moveHorizontal(dx);
        eye2.moveVertical(dy);
        mouth.moveHorizontal(dx);
        mouth.moveVertical(dy);
        foot1.moveHorizontal(dx);
        foot1.moveVertical(dy);
        foot2.moveHorizontal(dx);
        foot2.moveVertical(dy);
    }
    
    /**
     * Reubica los ojos, la boca y los pies según la dirección
     * actual del robot para que su cara quede orientada hacia
     * la dirección en la que avanza.
     * 
     * El cuerpo del robot no cambia de posición.
     */
    private void applyOrientation(){
        int nE1X, nE1Y, nE2X, nE2Y, nMX, nMY, nMW, nMH, nF1X, nF1Y, nF2X, nF2Y;
        if (direction == 'N') {
            nE1X = 10;  nE1Y = 10;
            nE2X = 30; nE2Y = 10;
            nMX = 13;  nMY = 30; nMW = 24; nMH = 10;
            nF1X = 5;  nF1Y = 50;
            nF2X = 30; nF2Y = 50;
        }
        else if (direction == 'S') {
            nE1X = 10;  nE1Y = 30;
            nE2X = 30; nE2Y = 30;
            nMX = 13;  nMY = 10;  nMW = 24; nMH = 10;
            nF1X = 5;  nF1Y = -15;
            nF2X = 30; nF2Y = -15;
        }
        else if (direction == 'E') {
            nE1X = 30; nE1Y = 10;
            nE2X = 30; nE2Y = 30;
            nMX = 10;  nMY = 13; nMW = 10; nMH = 24;
            nF1X = -15; nF1Y = 5;
            nF2X = -15; nF2Y = 30;
        }
        else { // W
            nE1X = 10;  nE1Y = 30;
            nE2X = 10;  nE2Y = 10;
            nMX = 30;  nMY = 13; nMW = 10; nMH = 24;
            nF1X = 50; nF1Y = 30;
            nF2X = 50; nF2Y = 5;
        }
        
        eye1.moveHorizontal(nE1X - eye1X);
        eye1.moveVertical(nE1Y - eye1Y);
        eye2.moveHorizontal(nE2X - eye2X);
        eye2.moveVertical(nE2Y - eye2Y);
        mouth.moveHorizontal(nMX - mouthX);
        mouth.moveVertical(nMY - mouthY);
        if (nMW != mouthW || nMH != mouthH) {
            mouth.changeSize(nMH, nMW);
        }
        foot1.moveHorizontal(nF1X - foot1X);
        foot1.moveVertical(nF1Y - foot1Y);
        foot2.moveHorizontal(nF2X - foot2X);
        foot2.moveVertical(nF2Y - foot2Y);
        
        eye1X = nE1X; eye1Y = nE1Y;
        eye2X = nE2X; eye2Y = nE2Y;
        mouthX = nMX; mouthY = nMY; mouthW = nMW; mouthH = nMH;
        foot1X = nF1X; foot1Y = nF1Y;
        foot2X = nF2X; foot2Y = nF2Y;
    }
    
    /**
     * Obtiene las coordenadas actuales del robot.
     *
     * @return un arreglo de enteros donde la posición 0 contiene
     * la coordenada x y la posición 1 contiene la coordenada y.
     */    
    public int[] coordinates(){
        return new int[] {x,y};
    }
    
    /**
     * Obtiene la dirección actual del robot.
     *
     * @return la dirección del robot: N, S, E o W.
     */
    public char direction(){
        return direction;
    }
    
    /**
     * Desplaza el robot una cantidad determinada de pasos
     * en la dirección en la que se encuentra orientado.
     *
     * @param step cantidad de pasos que debe avanzar el robot.
     */
    public void move(int step){
        int oldX = x;
        int oldY = y;
        if (direction == 'N') {
            y = y - step;
        }
        else if (direction == 'S') {
            y = y + step;
        }
        else if (direction == 'E') {
            x = x + step;
        }
        else if (direction == 'W') {
            x = x - step;
        }
        int dx = x - oldX;
        int dy = y - oldY;
        body.moveHorizontal(dx * 100);
        body.moveVertical(dy * 100);
        eye1.moveHorizontal(dx * 100);
        eye1.moveVertical(dy * 100);
        eye2.moveHorizontal(dx * 100);
        eye2.moveVertical(dy * 100);
        mouth.moveHorizontal(dx * 100);
        mouth.moveVertical(dy * 100);    
        foot1.moveHorizontal(dx * 100);
        foot1.moveVertical(dy * 100);
        foot2.moveHorizontal(dx * 100);
        foot2.moveVertical(dy * 100);
    }
    
    /**
     * Cambia la dirección hacia la que está orientado el robot.
     * 
     * Las direcciones válidas son N (norte), E (este),
     * S (sur) y W (oeste). Después de cambiar la dirección,
     * se actualiza visualmente la orientación de las partes
     * del robot.
     *
     * @param newDirection nueva dirección del robot.
     */
    public void turn(char newDirection){
        if (newDirection == 'N' ||
            newDirection == 'E' ||
            newDirection == 'S' ||
            newDirection == 'W'){
            direction = newDirection;
            applyOrientation();
        }
    }
  
    /**
     * Establece el estado del último movimiento del robot.
     *
     * @param value nuevo estado del movimiento.
     */
    public void setOK(boolean value)
    {
        ok = value;
    }
    
    /**
     * Indica si el último movimiento realizado por el robot
     * fue correcto.
     *
     * @return true si el último movimiento fue correcto o
     * false en caso contrario.
     */
    public boolean isOK(){
        return ok;
    }    

    /**
     * Hace visible el robot mostrando su cuerpo, ojos,
     * boca y pies.
     */
    public void makeVisible()
    {
        body.makeVisible();
        eye1.makeVisible();
        eye2.makeVisible();
        mouth.makeVisible();
        foot1.makeVisible();
        foot2.makeVisible();
    }
    
    /**
     * Hace invisible el robot ocultando su cuerpo, ojos,
     * boca y pies.
     */
    public void makeInvisible()
    {
        body.makeInvisible();
        eye1.makeInvisible();
        eye2.makeInvisible();
        mouth.makeInvisible();
        foot1.makeInvisible();
        foot2.makeInvisible();
    }

    /**
     * Establece una nueva posición para el robot.
     *
     * Actualiza las coordenadas lógicas del robot y desplaza
     * visualmente todas sus partes hasta la nueva posición.
     *
     * @param newX nueva coordenada x del robot.
     * @param newY nueva coordenada y del robot.
     */
    public void setPosition(int newX, int newY){
        int dx = newX - x;
        int dy = newY - y;
        x = newX;
        y = newY;
        body.moveHorizontal(dx * 100);
        body.moveVertical(dy * 100);
        eye1.moveHorizontal(dx * 100);
        eye1.moveVertical(dy * 100);
        eye2.moveHorizontal(dx * 100);
        eye2.moveVertical(dy * 100);
        mouth.moveHorizontal(dx * 100);
        mouth.moveVertical(dy * 100);
        foot1.moveHorizontal(dx * 100);
        foot1.moveVertical(dy * 100);
        foot2.moveHorizontal(dx * 100);
        foot2.moveVertical(dy * 100);
    }

    /**
     * Cambia el color del cuerpo y de los pies del robot.
     * Después de cambiar el color, hace visible el robot.
     *
     * @param newColor nuevo color que se aplicará al robot.
     */
    public void changeColor(String newColor){
        body.changeColor(newColor);
        foot1.changeColor(newColor);
        foot2.changeColor(newColor);
        makeVisible();
    }
}