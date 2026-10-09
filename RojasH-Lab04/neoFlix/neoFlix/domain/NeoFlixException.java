package domain;

/**
 * Excepción propia del programa NeoFlix.
 * Se usa cuando algo sale mal.
 *
 * @author Juan David Rojas Heredia
 */
public class NeoFlixException extends Exception {

    /** Mensaje para cuando un valor no se encuentra. */
    public static final String VALUE_UNKNOWN = "Valor no encontrado";

    /** Mensaje para cuando hay errores en los datos. */
    public static final String DATA_ERROR = "Hay errores en los datos";

    /** Mensaje para cuando algo todavía no se ha implementado. */
    public static final String TO_BE_IMPLEMENTED = "Debe ser implementado";

    /** Mensaje para cuando el contenido está vacío. */
    public static final String CONTENT_EMPTY = "El contenido esta vacio";

    /**
     * Crea una excepción con el mensaje que le pasemos.
     *
     * @param message el mensaje que explica el error
     */
    public NeoFlixException(String message)
    {
        super(message);
    }
}