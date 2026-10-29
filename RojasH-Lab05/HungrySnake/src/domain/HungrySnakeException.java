package domain;

/**
 * Excepción de la capa de dominio de HungrySnake.
 * Se lanza cuando la configuración de una partida no es válida.
 *
 * @author  Juan David Rojas Heredia
 * @version 28-10-2026
 */
public class HungrySnakeException extends Exception {

    public static final String INVALID_BOARD_SIZE = "El tablero debe tener entre 5 y 50 filas y entre 5 y 50 columnas.";
    public static final String INVALID_FRUITS = "El número de frutas no puede ser negativo.";
    public static final String INVALID_PERCENTAGE = "El porcentaje de obstáculos debe estar entre 0 y 100.";
    public static final String TOO_MANY_ELEMENTS = "No caben tantas frutas y obstáculos en el tablero.";
    public static final String NUMBER_FORMAT = "Los valores deben ser números.";

    /**
     * Crea una excepción con el mensaje dado.
     *
     * @param message el mensaje que describe el error
     */
    public HungrySnakeException(String message){
        super(message);
    }
}