package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;

/**
 * Clase de pruebas HungrySnakeTest.
 * Pruebas de la capa de dominio de HungrySnake: estado inicial, generación
 * de frutas y obstáculos, validaciones del constructor, dirección, movimiento,
 * crecimiento y fin del juego.
 *
 * @author  Juan David Rojas Heredia
 * @version 28-10-2026
 */
public class HungrySnakeTest
{
    private HungrySnake juego;

    /**
     * Constructor de la clase de pruebas HungrySnakeTest.
     */
    public HungrySnakeTest()
    {
    }

    /**
     * Prepara el escenario de pruebas.
     * Crea un tablero de 25x25 limpio (sin frutas ni obstáculos) para que
     * las pruebas no dependan del azar.
     *
     * Se ejecuta antes de cada método de prueba.
     */
    @BeforeEach
    public void setUp() throws HungrySnakeException{
        juego = new HungrySnake(25, 25, 0, 0);
    }

    // Auxiliares. IA generativa: No sabia como usar esos metodos que necesitaba para las pruebas

    /**
     * Cuenta las casillas del tablero que tienen fruta.
     *
     * @param j el juego que se va a recorrer
     * @return el número de frutas en el tablero
     */
    private int contarFrutas(HungrySnake j){
        int total = 0;
        for (int f = 0; f < j.getFilas(); f++){
            for (int c = 0; c < j.getColumnas(); c++){
                if (j.hayFruta(f, c)) total++;
            }
        }
        return total;
    }

    /**
     * Cuenta las casillas del tablero que tienen obstáculo.
     *
     * @param j el juego que se va a recorrer
     * @return el número de obstáculos en el tablero
     */
    private int contarObstaculos(HungrySnake j){
        int total = 0;
        for (int f = 0; f < j.getFilas(); f++){
            for (int c = 0; c < j.getColumnas(); c++){
                if (j.hayObstaculo(f, c)) total++;
            }
        }
        return total;
    }

    // Estado inicial

    /** La serpiente empieza con una unidad de longitud. */
    @Test
    public void shouldStartWithSizeOne(){
        assertEquals(1, juego.getTamano());
    }

    /** La cabeza empieza en la mitad de las filas, en la columna 1. */
    @Test
    public void shouldPlaceHeadInInitialPosition(){
        assertTrue(juego.hayCabeza(12, 1));
        assertTrue(juego.haySerpiente(12, 1));
    }

    /** La serpiente empieza mirando hacia el este. */
    @Test
    public void shouldStartFacingEast(){
        assertEquals(Direction.ESTE, juego.getDireccion());
    }

    /** Al crear el juego, el jugador todavía no ha perdido. */
    @Test
    public void shouldStartWithGameNotOver(){
        assertFalse(juego.isJuegoTerminado());
    }

    /** El modelo informa el tamaño del tablero con el que se creó. */
    @Test
    public void shouldKeepTheRequestedBoardSize(){
        assertEquals(25, juego.getFilas());
        assertEquals(25, juego.getColumnas());
    }

    /** El tablero más pequeño permitido (5x5) se puede crear. */
    @Test
    public void shouldCreateSmallestBoard() throws HungrySnakeException{
        HungrySnake j = new HungrySnake(5, 5, 1, 0);
        assertEquals(5, j.getFilas());
        assertEquals(5, j.getColumnas());
    }

    // Generación de frutas y obstáculos

    /** Se generan exactamente las frutas pedidas. Con 2% de 625 casillas (12.5) se redondea a 13 obstáculos. */
    @Test
    public void shouldCreateTheRequestedFruitsAndObstacles() throws HungrySnakeException{
        HungrySnake j = new HungrySnake(25, 25, 3, 2);
        assertEquals(3, contarFrutas(j));
        assertEquals(13, contarObstaculos(j));
    }

    /**
     * La casilla de la cabeza y la de enfrente nunca reciben fruta ni obstáculo.
     * Se repite 50 veces porque la generación depende del azar.
     */
    @Test
    public void shouldKeepStartCellsFree() throws HungrySnakeException{
        for (int i = 0; i < 50; i++){
            HungrySnake j = new HungrySnake(25, 25, 5, 2);
            assertFalse(j.hayFruta(12, 1));
            assertFalse(j.hayObstaculo(12, 1));
            assertFalse(j.hayFruta(12, 2));
            assertFalse(j.hayObstaculo(12, 2));
        }
    }

    // Excepciones del constructor

    /** Con menos de 5 o más de 50 filas no se puede crear el juego. */
    @Test
    public void shouldNotCreateBoardWithInvalidRows(){
        try {
            new HungrySnake(4, 25, 1, 2);
            fail("Debía lanzar HungrySnakeException");
        } catch (HungrySnakeException e) {
            assertEquals(HungrySnakeException.INVALID_BOARD_SIZE, e.getMessage());
        }
        try {
            new HungrySnake(51, 25, 1, 2);
            fail("Debía lanzar HungrySnakeException");
        } catch (HungrySnakeException e) {
            assertEquals(HungrySnakeException.INVALID_BOARD_SIZE, e.getMessage());
        }
    }

    /** Con menos de 5 o más de 50 columnas no se puede crear el juego. */
    @Test
    public void shouldNotCreateBoardWithInvalidColumns(){
        try {
            new HungrySnake(25, 4, 1, 2);
            fail("Debía lanzar HungrySnakeException");
        } catch (HungrySnakeException e) {
            assertEquals(HungrySnakeException.INVALID_BOARD_SIZE, e.getMessage());
        }
        try {
            new HungrySnake(25, 51, 1, 2);
            fail("Debía lanzar HungrySnakeException");
        } catch (HungrySnakeException e) {
            assertEquals(HungrySnakeException.INVALID_BOARD_SIZE, e.getMessage());
        }
    }

    /** El número de frutas no puede ser negativo. */
    @Test
    public void shouldNotCreateGameWithNegativeFruits(){
        try {
            new HungrySnake(25, 25, -1, 2);
            fail("Debía lanzar HungrySnakeException");
        } catch (HungrySnakeException e) {
            assertEquals(HungrySnakeException.INVALID_FRUITS, e.getMessage());
        }
    }

    /** El porcentaje de obstáculos debe estar entre 0 y 100. */
    @Test
    public void shouldNotCreateGameWithInvalidPercentage(){
        try {
            new HungrySnake(25, 25, 1, -1);
            fail("Debía lanzar HungrySnakeException");
        } catch (HungrySnakeException e) {
            assertEquals(HungrySnakeException.INVALID_PERCENTAGE, e.getMessage());
        }
        try {
            new HungrySnake(25, 25, 1, 101);
            fail("Debía lanzar HungrySnakeException");
        } catch (HungrySnakeException e) {
            assertEquals(HungrySnakeException.INVALID_PERCENTAGE, e.getMessage());
        }
    }

    /** Si las frutas y los obstáculos no caben en el tablero, no se crea el juego. */
    @Test
    public void shouldNotCreateGameWithTooManyElements(){
        try {
            new HungrySnake(5, 5, 23, 0);
            fail("Debía lanzar HungrySnakeException");
        } catch (HungrySnakeException e) {
            assertEquals(HungrySnakeException.TOO_MANY_ELEMENTS, e.getMessage());
        }
    }

    // Dirección

    /** Una dirección que no es la contraria a la actual se acepta. */
    @Test
    public void shouldChangeDirection(){
        juego.cambiarDireccion(Direction.NORTE);
        assertEquals(Direction.NORTE, juego.getDireccion());
    }

    /** La dirección contraria o la misma dirección a la actual se ignora. */
    @Test
    public void shouldIgnoreOppositeOrSameDirection(){
        juego.cambiarDireccion(Direction.OESTE);
        assertEquals(Direction.ESTE, juego.getDireccion());
        juego.cambiarDireccion(Direction.ESTE);
        assertEquals(Direction.ESTE, juego.getDireccion());
    }

    /** Dos cambios seguidos antes de mover no dejan girar hacia atrás. */
    @Test
    public void shouldNotTurnBackBetweenMoves(){
        juego.cambiarDireccion(Direction.NORTE);
        juego.cambiarDireccion(Direction.OESTE);
        assertEquals(Direction.NORTE, juego.getDireccion());
    }

    /** Cada dirección tiene su contraria. */
    @Test
    public void shouldGiveOppositeDirection(){
        assertEquals(Direction.SUR, Direction.NORTE.opuesta());
        assertEquals(Direction.NORTE, Direction.SUR.opuesta());
        assertEquals(Direction.OESTE, Direction.ESTE.opuesta());
        assertEquals(Direction.ESTE, Direction.OESTE.opuesta());
    }

    // Mover

    /** Sin pulsar nada, la serpiente avanza una casilla al este y no crece. */
    @Test
    public void shouldMoveEastByDefault(){
        juego.mover();
        assertTrue(juego.hayCabeza(12, 2));
        assertFalse(juego.haySerpiente(12, 1));
        assertEquals(1, juego.getTamano());
    }

    /** Si no se cambia la dirección, la serpiente sigue avanzando en la misma. */
    @Test
    public void shouldKeepMovingInTheSameDirection(){
        juego.mover();
        juego.mover();
        juego.mover();
        assertTrue(juego.hayCabeza(12, 4));
    }

    /** Después de pedir norte, la cabeza sube una fila. */
    @Test
    public void shouldMoveNorthAfterChangingDirection(){
        juego.cambiarDireccion(Direction.NORTE);
        juego.mover();
        assertTrue(juego.hayCabeza(11, 1));
    }

    // Comer

    /** Al comer una fruta, la serpiente crece una unidad y la fruta desaparece. Y aparece otra de manera aleatoria. */
    @Test
    public void shouldGrowAndCreateNewFruitWhenEating(){
        juego.colocarFruta(12, 2);
        juego.mover();
        assertEquals(2, juego.getTamano());
        assertTrue(juego.hayCabeza(12, 2));
        assertTrue(juego.haySerpiente(12, 1));
        assertFalse(juego.hayFruta(12, 2));
        assertEquals(1, contarFrutas(juego));
    }

    // Perder

    /** Al salir por el borde derecho del tablero, el jugador pierde. */
    @Test
    public void shouldLoseWhenHittingRightWall(){
        for (int i = 0; i < 23; i++){
            juego.mover();
        }
        assertFalse(juego.isJuegoTerminado());
        juego.mover();
        assertTrue(juego.isJuegoTerminado());
    }

    /** Al salir por el borde de arriba del tablero, el jugador pierde. */
    @Test
    public void shouldLoseWhenHittingTopWall(){
        juego.cambiarDireccion(Direction.NORTE);
        for (int i = 0; i < 12; i++){
            juego.mover();
        }
        assertFalse(juego.isJuegoTerminado());
        juego.mover();
        assertTrue(juego.isJuegoTerminado());
    }

    /** Al chocar contra un obstáculo, el jugador pierde. */
    @Test
    public void shouldLoseWhenHittingObstacle(){
        juego.colocarObstaculo(12, 2);
        juego.mover();
        assertTrue(juego.isJuegoTerminado());
    }

    /**
     * Al chocar contra su propio cuerpo, el jugador pierde.
     * La serpiente crece hasta tamaño 5 y luego gira sobre sí misma.
     */
    @Test
    public void shouldLoseWhenHittingItself(){
        juego.colocarFruta(12, 2);
        juego.colocarFruta(12, 3);
        juego.colocarFruta(12, 4);
        juego.colocarFruta(12, 5);
        for (int i = 0; i < 4; i++){
            juego.mover();
        }
        assertEquals(5, juego.getTamano());
        juego.cambiarDireccion(Direction.NORTE);
        juego.mover();
        juego.cambiarDireccion(Direction.OESTE);
        juego.mover();
        juego.cambiarDireccion(Direction.SUR);
        juego.mover();
        assertTrue(juego.isJuegoTerminado());
    }

    /** Cuando el juego ya terminó, mover no hace nada. */
    @Test
    public void shouldNotMoveWhenGameIsOver(){
        juego.colocarObstaculo(12, 2);
        juego.mover();
        assertTrue(juego.isJuegoTerminado());
        juego.mover();
        assertTrue(juego.hayCabeza(12, 1));
    }

    /**
     * Tablero lleno de obstáculos: la serpiente se come la fruta de enseguida,
     * no aparece otra fruta y en el siguiente movimiento el juego termina.
     */
    @Test
    public void shouldEndGameWhenNoFreeCellForNewFruit() throws HungrySnakeException{
        HungrySnake j = new HungrySnake(5, 5, 0, 0);
        for (int f = 0; f < 5; f++){
            for (int c = 0; c < 5; c++){
                if (!(f == 2 && (c == 1 || c == 2))){
                    j.colocarObstaculo(f, c);
                }
            }
        }
        j.colocarFruta(2, 2);
        j.mover();
        assertEquals(2, j.getTamano());
        assertEquals(0, contarFrutas(j));
        j.mover();
        assertTrue(j.isJuegoTerminado());
    }
    
    /**
     * Libera el escenario de pruebas.
     *
     * Se ejecuta después de cada método de prueba.
     */
    @AfterEach
    public void tearDown()
    {
    }
}