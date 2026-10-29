package domain;
import java.util.*;

/**
 * Modelo del juego HungrySnake (capa de dominio).
 * Guarda el tablero con sus frutas y obstáculos, y la serpiente. La serpiente
 * se mueve en una dirección, crece al comer fruta y el juego termina si choca
 * con un obstáculo, con un borde o con ella misma.
 *
 * @author  Juan David Rojas Heredia
 * @version 28-10-2026
 */
public class HungrySnake {
    private int filas;
    private int columnas;
    private double porcentajeObstaculos;
    private int numeroFrutas;
    private Direction direccion;
    private Direction ultimaDireccion;
    private Deque<Position> cuerpo;
    private boolean[][] frutas;
    private boolean[][] obstaculos;
    private boolean juegoTerminado;
    

    private Random random;

    /**
     * Crea un juego nuevo. La serpiente empieza con tamaño 1, en la mitad de
     * las filas y en la columna 1, mirando al este. Las frutas y los obstáculos
     * se ponen al azar, sin tocar la cabeza ni la casilla de enfrente.
     *
     * @param filas el número de filas, entre 5 y 50
     * @param columnas el número de columnas, entre 5 y 50
     * @param numeroFrutas el número de frutas, no puede ser negativo
     * @param porcentajeObstaculos el porcentaje de casillas con obstáculo, entre 0 y 100
     * @throws HungrySnakeException si el tablero, las frutas o el porcentaje no son válidos,
     *         o si las frutas y los obstáculos no caben en el tablero
     */
    public HungrySnake(int filas, int columnas, int numeroFrutas, double porcentajeObstaculos) throws HungrySnakeException{
        if ((5>filas || filas>50) || (5>columnas || columnas>50 )){
            throw new HungrySnakeException(HungrySnakeException.INVALID_BOARD_SIZE);
        }
        else if (numeroFrutas<0){
            throw new HungrySnakeException(HungrySnakeException.INVALID_FRUITS);
        }
        else if (0>porcentajeObstaculos || 100<porcentajeObstaculos){
            throw new HungrySnakeException(HungrySnakeException.INVALID_PERCENTAGE);
        }
        int numObstaculos = calcularObstaculos(filas, columnas, porcentajeObstaculos);
        int casillasLibres = (filas * columnas) - 2;
        int casillasOcupadas = numObstaculos + numeroFrutas;
        if (casillasLibres <= casillasOcupadas){
            throw new HungrySnakeException(HungrySnakeException.TOO_MANY_ELEMENTS);
        } 

        this.filas = filas;
        this.columnas = columnas;
        this.porcentajeObstaculos = porcentajeObstaculos;
        this.numeroFrutas = numeroFrutas;
        this.frutas = new boolean[filas][columnas];
        this.obstaculos = new boolean[filas][columnas];
        this.cuerpo = new ArrayDeque<>();
        this.direccion = Direction.ESTE;
        this.ultimaDireccion = Direction.ESTE;
        Position cabeza = new Position(this.filas/2, 1);
        cuerpo.addFirst(cabeza);
        juegoTerminado = false;

        this.random = new Random();
        generarFrutas();
        generarObstaculos();
    }

    /**
     * Da el número de filas del tablero.
     *
     * @return las filas
     */
    public int getFilas(){
        return filas;
    }

    /**
     * Da el número de columnas del tablero.
     *
     * @return las columnas
     */
    public int getColumnas(){
        return columnas;
    }

    /**
     * Da el tamaño de la serpiente, o sea, cuántas casillas ocupa.
     *
     * @return el tamaño de la serpiente
     */
    public int getTamano(){
        return cuerpo.size();
    }

    /**
     * Da la dirección hacia la que va la serpiente.
     *
     * @return la dirección actual
     */
    public Direction getDireccion(){
        return this.direccion;
    }

    /**
     * Da el número de frutas con el que se creó el juego.
     *
     * @return el número de frutas
     */
    public int getNumeroFrutas(){
        return this.numeroFrutas;
    }

    /**
     * Da el porcentaje de obstáculos con el que se creó el juego.
     *
     * @return el porcentaje de obstáculos
     */
    public double getPorcentajeObstaculos(){
        return this.porcentajeObstaculos;
    } 

    /**
     * Dice si el jugador ya perdió.
     *
     * @return true si el juego terminó, false si sigue
     */
    public boolean isJuegoTerminado(){
        return juegoTerminado;
    }

    /**
     * Dice si hay una fruta en una casilla.
     *
     * @param fila la fila de la casilla
     * @param columna la columna de la casilla
     * @return true si hay fruta
     */
    public boolean hayFruta(int fila, int columna){
        return this.frutas[fila][columna];
    }

    /**
     * Dice si hay un obstáculo en una casilla.
     *
     * @param fila la fila de la casilla
     * @param columna la columna de la casilla
     * @return true si hay obstáculo
     */
    public boolean hayObstaculo(int fila, int columna){
        return this.obstaculos[fila][columna];
    }

    /**
     * Dice si hay una parte de la serpiente (cabeza o cuerpo) en una casilla.
     *
     * @param fila la fila de la casilla
     * @param columna la columna de la casilla
     * @return true si hay serpiente
     */
    public boolean haySerpiente(int fila, int columna){
        Position otro = new Position(fila, columna);
        for (Position pos : cuerpo){
            if (pos.equals(otro)){
                return true;
            }
        }
        return false;
    }

    /**
     * Dice si la cabeza de la serpiente está en una casilla.
     *
     * @param fila la fila de la casilla
     * @param columna la columna de la casilla
     * @return true si la cabeza está ahí
     */
    public boolean hayCabeza(int fila, int columna){
        Position otro = new Position(fila, columna);
        return cuerpo.getFirst().equals(otro);
    }

    /**
     * Cambia la dirección de la serpiente. Se ignora si es la contraria a la
     * última dirección en la que se movió, porque no puede devolverse sobre sí misma.
     *
     * @param nueva la dirección que se pide
     */
    public void cambiarDireccion(Direction nueva){
        if (this.ultimaDireccion!=nueva.opuesta()){
            this.direccion = nueva;
        }
    }

    /**
     * Mueve la serpiente una casilla en su dirección. Si choca con un borde,
     * un obstáculo o su cuerpo, el juego termina. Si se come una fruta, crece
     * una unidad y aparece otra fruta (si queda una casilla libre). Si el juego
     * ya terminó, no hace nada.
     */
    public void mover(){
        if (isJuegoTerminado()){
            return;
        }
        Position pos = casillaSiguiente();
        if (!isInside(pos.getFila(), pos.getColumna()) || hayObstaculo(pos.getFila(), pos.getColumna()) || haySerpiente(pos.getFila(), pos.getColumna()) ){
            juegoTerminado = true;
            return;
        }
        ultimaDireccion = direccion;

        boolean res = hayFruta(pos.getFila(), pos.getColumna());
        cuerpo.addFirst(pos);
        if (res){
            frutas[pos.getFila()][pos.getColumna()] = false;
            Position casilla = elegirCasillaLibre();
            if (casilla != null){
                this.frutas[casilla.getFila()][casilla.getColumna()] = true;
            }
        }
        else{
            cuerpo.removeLast();
        }
    }

    /**
     * Pone una fruta en una casilla. Sirve para armar escenarios en las pruebas.
     *
     * @param fila la fila de la casilla
     * @param columna la columna de la casilla
     */
    public void colocarFruta(int fila, int columna){
        frutas[fila][columna]=true;
    }

    /**
     * Pone un obstáculo en una casilla. Sirve para armar escenarios en las pruebas.
     *
     * @param fila la fila de la casilla
     * @param columna la columna de la casilla
     */
    public void colocarObstaculo(int fila, int columna){
        obstaculos[fila][columna]=true;
    }

    /**
     * Pone al azar las frutas que pide el juego, cada una en una casilla libre.
     */
    private void generarFrutas(){
        for (int i=0; i<numeroFrutas; i++){
            Position casilla = elegirCasillaLibre();
            this.frutas[casilla.getFila()][casilla.getColumna()] = true;
        }
    }

    /**
     * Pone al azar los obstáculos que salen del porcentaje, cada uno en una casilla libre.
     */
    private void generarObstaculos(){
        int numObstaculos = calcularObstaculos(filas, columnas, porcentajeObstaculos);
        for (int i=0; i<numObstaculos; i++){
            Position casilla = elegirCasillaLibre();
            this.obstaculos[casilla.getFila()][casilla.getColumna()] = true;
        }
    }

    /**
     * Elige al azar una casilla libre. Nunca elige la casilla de enfrente de
     * la cabeza al empezar, para que la serpiente tenga por dónde arrancar.
     *
     * @return una casilla libre, o null si no queda ninguna
     */
    private Position elegirCasillaLibre(){
        List<Position> libres = new ArrayList<>();
        for (int f = 0; f < filas; f++){
            for (int c = 0; c < columnas; c++){
                if (estaLibre(f, c) && !(f == filas/2 && c == 2)){
                    libres.add(new Position(f, c));
                }
            }
        }
        if (libres.isEmpty()){
            return null;
        }
        return libres.get(this.random.nextInt(libres.size()));
    }

    /**
     * Dice si una casilla está libre, o sea, sin fruta, obstáculo ni serpiente.
     *
     * @param fila la fila de la casilla
     * @param columna la columna de la casilla
     * @return true si está libre
     */
    private boolean estaLibre(int fila, int columna){
        return !(hayFruta(fila, columna) || hayObstaculo(fila, columna) || haySerpiente(fila, columna));
    }

    /**
     * Dice si una casilla está dentro del tablero.
     *
     * @param fila la fila de la casilla
     * @param columna la columna de la casilla
     * @return true si está dentro
     */
    private boolean isInside(int fila, int columna){
        return (0 <= fila && fila <= filas - 1 && 0 <= columna && columna <= columnas - 1);
    }

    /**
     * Da la casilla a la que llegaría la cabeza en el siguiente movimiento,
     * según la dirección actual.
     *
     * @return la casilla siguiente (puede quedar fuera del tablero)
     */
    private Position casillaSiguiente(){
        Position cabeza = cuerpo.getFirst();
        switch (this.direccion){
            case NORTE: return new Position(cabeza.getFila()-1, cabeza.getColumna());
            case SUR: return new Position(cabeza.getFila()+1, cabeza.getColumna());
            case ESTE: return new Position(cabeza.getFila(), cabeza.getColumna()+1);
            case OESTE: return new Position(cabeza.getFila(), cabeza.getColumna()-1);
            default: return cabeza;
        }
    }

    /**
     * Calcula cuántos obstáculos salen de un porcentaje del tablero,
     * redondeando al entero más cercano.
     *
     * @param filas las filas del tablero
     * @param columnas las columnas del tablero
     * @param porcentaje el porcentaje de casillas con obstáculo
     * @return el número de obstáculos
     */
    private static  int calcularObstaculos(int filas, int columnas, double porcentaje){
        return (int)Math.round(filas * columnas * porcentaje / 100);
    }
}