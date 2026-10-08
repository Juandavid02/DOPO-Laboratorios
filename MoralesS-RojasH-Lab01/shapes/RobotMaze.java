/**
 * Representa un laberinto en el que un robot puede desplazarse,
 * cambiar de dirección, interactuar con paredes y encontrar una salida.
 *
 * El laberinto tiene un tamaño definido por filas y columnas,
 * una cantidad de vidas para el robot, una entrada y una salida
 * ubicadas aleatoriamente en caras opuestas.
 *
 * @author MoralesS - RojasH
 * @version 1.0 (12 August 2026)
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

public class RobotMaze
{
    private static final int CELL_SIZE = 100;
    private static final int WALL_THICKNESS = 8;
    private static final int MARGIN_X = 60;
    private static final int MARGIN_Y = 60;
    
    private Robot robot;        
    private int sizeX;
    private int sizeY;
    private int lives;
    private boolean started;
    private int exitX;
    private int exitY;
    private int entryFace;
    private int exitFace;
    // IA generativa me recomendó el uso de List para almacenar las paredes.
    private List<Wall> walls;
    private Stack<RobotState> history;
    
    /**
     * Crea un nuevo laberinto con el tamaño especificado.
     *
     * Se inicializa el robot, se establen 10 vidas, crea la lista
     * de paredes y selecciona aleatoriamente la cara de entrada y
     * la salida esta en la cara opuesta.
     * También crea las paredes exteriores del laberinto,
     * posiciona visualmente el robot y lo hace visible.
     *
     * @param sizeX cantidad de columnas que tendrá el laberinto.
     * @param sizeY cantidad de filas que tendrá el laberinto.
     */
    public RobotMaze(int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        robot = new Robot();
        lives = 10;
        started = false;
        walls = new ArrayList<Wall>();
        history = new Stack<RobotState>();
        Random random = new Random();
        entryFace = random.nextInt(4);
        int position;
        if (entryFace == 0) { // Norte -> Sur
            position = random.nextInt(sizeX);
            robot.setPosition(position, 0);
            exitX = random.nextInt(sizeX);
            exitY = sizeY - 1;
            exitFace = 1;
        }
        else if (entryFace == 1) { // Sur -> Norte
            position = random.nextInt(sizeX);
            robot.setPosition(position, sizeY - 1);
            exitX = random.nextInt(sizeX);
            exitY = 0;
            exitFace = 0;
        }
        else if (entryFace == 2) { // Oeste -> Este
            position = random.nextInt(sizeY);
            robot.setPosition(0, position);
            exitX = sizeX - 1;
            exitY = random.nextInt(sizeY);
            exitFace = 3;
        }
        else { // Este -> Oeste
            position = random.nextInt(sizeY);
            robot.setPosition(sizeX - 1, position);
            exitX = 0;
            exitY = random.nextInt(sizeY);
            exitFace = 2;
        }
        addBorderWalls();
        robot.moveOrigin(MARGIN_X, MARGIN_Y);
        robot.makeVisible();
    }
        
    
    /**
     * Agrega una pared al laberinto entre dos puntos.
     *
     * La pared puede ser horizontal o vertical. Si el juego
     * ya ha comenzado, no se permite agregar nuevas paredes.
     * La pared se representa visualmente mediante un rectángulo.
     *
     * @param x1 coordenada x del primer punto.
     * @param y1 coordenada y del primer punto.
     * @param x2 coordenada x del segundo punto.
     * @param y2 coordenada y del segundo punto.
     */
    public void addWall(int x1, int y1, int x2, int y2){
        if (!started) {
            Wall wall = new Wall(x1, y1, x2, y2);
            Rectangle rectangle = wall.getRectangle();
            rectangle.changeColor("black");
            if (y1 == y2) {
                int length = (x2 - x1) * CELL_SIZE;
                rectangle.changeSize(WALL_THICKNESS, length);
                int pixelX = x1 * CELL_SIZE + MARGIN_X;
                int pixelY = y1 * CELL_SIZE + MARGIN_Y;
                rectangle.moveHorizontal(pixelX);
                rectangle.moveVertical(pixelY);
            }
            else if (x1 == x2) {
                int length = (y2 - y1) * CELL_SIZE;
                rectangle.changeSize(length, WALL_THICKNESS);
                int pixelX = x1 * CELL_SIZE + MARGIN_X;
                int pixelY = y1 * CELL_SIZE + MARGIN_Y;
                rectangle.moveHorizontal(pixelX);
                rectangle.moveVertical(pixelY);
            }
            else {
                return;
            }
            rectangle.makeVisible();
            walls.add(wall);
        }
    }
    
    /**
     * Crea las paredes que rodean el borde exterior del laberinto.
     *
     * Las paredes se generan en las cuatro caras del laberinto,
     * dejando libres la posición de entrada y la posición de salida.
     */
     private void addBorderWalls(){
        int robotX = robot.coordinates()[0];
        int robotY = robot.coordinates()[1];
        for (int x = 0; x < sizeX; x++) {
            boolean isEntryGap = (entryFace == 0 && x == robotX);
            boolean isExitGap  = (exitFace == 0 && x == exitX);
            if (!isEntryGap && !isExitGap) {
                addWall(x, 0, x + 1, 0);
            }
        }
        for (int x = 0; x < sizeX; x++) {
            boolean isEntryGap = (entryFace == 1 && x == robotX);
            boolean isExitGap  = (exitFace == 1 && x == exitX);
            if (!isEntryGap && !isExitGap) {
                addWall(x, sizeY, x + 1, sizeY);
            }
        }
        for (int y = 0; y < sizeY; y++) {
            boolean isEntryGap = (entryFace == 2 && y == robotY);
            boolean isExitGap  = (exitFace == 2 && y == exitY);
            if (!isEntryGap && !isExitGap) {
                addWall(0, y, 0, y + 1);
            }
        }
        for (int y = 0; y < sizeY; y++) {
            boolean isEntryGap = (entryFace == 3 && y == robotY);
            boolean isExitGap  = (exitFace == 3 && y == exitY);
            if (!isEntryGap && !isExitGap) {
                addWall(sizeX, y, sizeX, y + 1);
            }
        }
    }
    
    /**
     * Comprueba si una posición se encuentra dentro de los límites
     * del laberinto.
     *
     * @param x coordenada horizontal que se desea comprobar.
     * @param y coordenada vertical que se desea comprobar.
     * @return true si la posición está dentro del laberinto;
     * false si está fuera de sus límites.
     */
    private boolean inside(int x, int y){
        return x >= 0 && x < sizeX && y >= 0 && y < sizeY;
    }  
    

    /**
     * Comprueba si existe una pared entre dos posiciones.
     *
     * Busca en la lista de paredes una pared que se encuentre
     * entre las dos posiciones indicadas. Si encuentra una pared,
     * la colorea de rojo y devuelve true.
     *
     * @param x1 coordenada x de la primera posición.
     * @param y1 coordenada y de la primera posición.
     * @param x2 coordenada x de la segunda posición.
     * @param y2 coordenada y de la segunda posición.
     * @return true si existe una pared entre las posiciones;
     * false en caso contrario.
     */
    // Ayuda de IA generativa para dectar si hay paredes
    private boolean hasWall(int x1, int y1, int x2, int y2){
        if (x1 == x2) {
            int boundaryY = Math.max(y1, y2);
            for (Wall wall : walls) {
                if (wall.getY1() == wall.getY2() && wall.getY1() == boundaryY) {
                    int wallMinX = Math.min(wall.getX1(), wall.getX2());
                    int wallMaxX = Math.max(wall.getX1(), wall.getX2());
                    if (x1 >= wallMinX && x1 < wallMaxX) {
                        wall.getRectangle().changeColor("red");
                        return true;
                    }
                }
            }
        }
        else if (y1 == y2) {
            int boundaryX = Math.max(x1, x2);
            for (Wall wall : walls) {
                if (wall.getX1() == wall.getX2() && wall.getX1() == boundaryX) {
                    int wallMinY = Math.min(wall.getY1(), wall.getY2());
                    int wallMaxY = Math.max(wall.getY1(), wall.getY2());
                    if (y1 >= wallMinY && y1 < wallMaxY) {
                        wall.getRectangle().changeColor("red");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Obtiene la cantidad de vidas restantes del robot.
     *
     * @return cantidad de vidas restantes.
     */
    public int lives(){
        return lives;
    }  
    
    /**
     * Obtiene las coordenadas actuales del robot.
     *
     * @return un arreglo de dos posiciones donde el índice 0
     * representa x y el índice 1 representa y.
     */
    public int[] coordinates(){
        return robot.coordinates();
    }
    
    /**
     * Obtiene la dirección actual del robot.
     *
     * @return dirección actual del robot: N, S, E o W.
     */
    public char direction(){
        return robot.direction();
    }
    
    /**
     * Mueve el robot una cantidad determinada de pasos.
     *
     * El método comprueba cada paso para determinar si el robot
     * permanece dentro del laberinto y si existe una pared.
     * Si el robot choca contra una pared o sale del laberinto,
     * pierde una vida y el movimiento se marca como incorrecto.
     *
     * Si el movimiento es válido, el robot avanza la cantidad
     * de pasos permitidos.
     *
     * @param step cantidad de pasos que debe intentar realizar
     * el robot. Un valor negativo permite desplazarse
     * en sentido contrario.
     */
    public void move(int step){
        int[] oldPosition = robot.coordinates();
        int oldX = oldPosition[0];
        int oldY = oldPosition[1];
        char oldDirection = robot.direction();
        
        int directionStep = 1;
        if (step < 0) {
            directionStep = -1;
            step = -step;
        }
        
        int[] pos = robot.coordinates();
        int currentX = pos[0];
        int currentY = pos[1];
        int validSteps = 0;
        boolean crashed = false;
        
        for (int i = 0; i < step; i++) {
            int newX = currentX;
            int newY = currentY;
            if (robot.direction() == 'N') {
                newY = newY - directionStep;
            }
            else if (robot.direction() == 'S') {
                newY = newY + directionStep;
            }
            else if (robot.direction() == 'E') {
                newX = newX + directionStep;
            }
            else if (robot.direction() == 'W') {
                newX = newX - directionStep;
            }
            boolean outOfBounds = !inside(newX, newY);
            boolean wallHit = hasWall(currentX, currentY, newX, newY);
            if (outOfBounds || wallHit) {
                crashed = true;
                break;
            }
            currentX = newX;
            currentY = newY;
            validSteps++;
        }
        if (validSteps > 0) {
            history.push(new RobotState(oldX, oldY, oldDirection));
            robot.move(directionStep * validSteps);
        }
        if (crashed) {
            lives--;
            robot.setOK(false);
            if (lives == 0){
                robot.changeColor("red");
            }
        } else {
            robot.setOK(true);
        }
        isGameOver();
    }
    
    /**
     * Cambia la dirección del robot.
     *
     * @param newDirection nueva dirección del robot: 'N', 'S', 'E' o 'W'.
     */
    public void turn(char newDirection){
        robot.turn(newDirection);
    }

    /**
     * Indica si el último movimiento realizado por el robot
     * fue correcto.
     *
     * @return true si el último movimiento fue correcto;
     * false si el robot chocó o intentó salir del laberinto.
     */
    public boolean isOK(){
        return robot.isOK();
    }
    
    /**
     * Comprueba si el robot ha llegado a la salida del laberinto.
     *
     * Si el robot se encuentra en las coordenadas de la salida,
     * cambia su color a verde.
     *
     * @return true si el robot está en la salida;
     * false en caso contrario.
     */
    public boolean isExit(){
        int[] pos = robot.coordinates();
        if (pos[0] == exitX && pos[1] == exitY){
            robot.changeColor("green");
            return true;
        };
        return false;
    }
    
    /**
     * Comprueba si el juego ha terminado.
     *
     * El juego termina cuando el robot llega a la salida
     * o cuando pierde todas sus vidas.
     *
     * @return true si el juego terminó;
     * false si el juego continúa.
     */
    public boolean isGameOver(){
        return isExit() || lives <= 0;
    }
    
    /**
     * Inicia el juego.
     *
     * Una vez iniciado, no se pueden agregar nuevas paredes
     * mediante el método addWall.
     */
    public void start(){
        started = true;
    }
    
    /**
     * Indica si el juego ya ha comenzado.
     *
     * @return true si el juego ha comenzado;
     * false si todavía no ha comenzado.
     */ 
    public boolean isStarted(){
        return started;
    }
    
    /**
     * Calcula la distancia Manhattan entre dos posiciones.
     *
     * @param x1 coordenada x de la primera posición.
     * @param y1 coordenada y de la primera posición.
     * @param x2 coordenada x de la segunda posición.
     * @param y2 coordenada y de la segunda posición.
     * @return distancia Manhattan entre las dos posiciones.
     */
    private int manhattanDistance(int x1, int y1, int x2, int y2)
    {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    
    /**
     * Realiza el mejor movimiento posible utilizando una estrategia Greedy.
     *
     * La máquina evalúa las cuatro direcciones posibles y selecciona
     * el movimiento válido que minimiza la distancia Manhattan entre
     * la posición resultante del robot y la salida.
     *
     * Si ninguna dirección es válida, el robot permanece en su posición.
     */
    public void goodMove(){
        int[] pos = robot.coordinates();
        int currentX = pos[0];
        int currentY = pos[1]; 
        char bestDirection = robot.direction();
        int bestDistance = Integer.MAX_VALUE;   
    
        int newX = currentX;
        int newY = currentY - 1;
        if (inside(newX, newY) && !hasWall(currentX, currentY, newX, newY)){
           int distance = manhattanDistance(newX, newY, exitX, exitY);
           if (distance < bestDistance){
                bestDistance = distance;
                bestDirection = 'N';
            }
        }
        newX = currentX;
        newY = currentY + 1;
        if (inside(newX, newY) && !hasWall(currentX, currentY, newX, newY)){
           int distance = manhattanDistance(newX, newY, exitX, exitY);
           if (distance < bestDistance){
                bestDistance = distance;
                bestDirection = 'S';
            }
        }
        newX = currentX+1;
        newY = currentY;
        if (inside(newX, newY) && !hasWall(currentX, currentY, newX, newY)){
           int distance = manhattanDistance(newX, newY, exitX, exitY);
           if (distance < bestDistance){
                bestDistance = distance;
                bestDirection = 'E';
            }
        }
        newX = currentX-1;
        newY = currentY;
        if (inside(newX, newY) && !hasWall(currentX, currentY, newX, newY)){
           int distance = manhattanDistance(newX, newY, exitX, exitY);
           if (distance < bestDistance){
                bestDistance = distance;
                bestDirection = 'W';
            }
        }
        
        if (bestDistance != Integer.MAX_VALUE) {
            turn(bestDirection);
            move(1);
        }
    }
        
    /**
     * Deshace el último movimiento realizado por el robot.
     *
     * Recupera la posición y dirección anteriores almacenadas
     * en el historial. El uso de este método no consume vidas.
     *
     * Si no existe ningún movimiento anterior, el robot
     * permanece en su posición actual.
     */
    public void undo(){
        if (!history.empty()) {
            RobotState previous = history.pop();
            robot.setPosition(previous.getX(),previous.getY());
            robot.turn(previous.getDirection());
            robot.setOK(true);
        }
    }
    
    
}