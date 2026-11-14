package domain;

/**
 * Excepción para los errores del EcoSafari.
 *
 * @author Juan David Rojas Heredia
 */
public class EcoSafariException extends Exception {

    

    public static final String OPEN_ERROR = "Error al abrir el archivo";
    public static final String SAVE_ERROR = "Error al guardar el archivo";
    public static final String EXPORT_ERROR = "Error al exportar el archivo";
    public static final String IMPORT_ERROR = "Error al importar el archivo";

    // Errores detallados de guardar (saveAs)
    public static final String SAVE_NULL_FILE = "No se indicó el archivo donde guardar";
    public static final String SAVE_IS_DIRECTORY = "El nombre indicado corresponde a una carpeta, no a un archivo";
    public static final String SAVE_BAD_EXTENSION = "El archivo debe tener extensión .dat";
    public static final String SAVE_MISSING_FOLDER = "La carpeta donde se quiere guardar no existe";
    public static final String SAVE_NO_PERMISSION = "No hay permiso para escribir en esa ubicación";
    public static final String SAVE_NOT_SERIALIZABLE = "El safari contiene elementos que no se pueden guardar";

    // Errores detallados de exportar (exportAs)
    public static final String EXPORT_NULL_FILE = "No se indicó el archivo donde exportar";
    public static final String EXPORT_IS_DIRECTORY = "El nombre indicado corresponde a una carpeta, no a un archivo";
    public static final String EXPORT_BAD_EXTENSION = "El archivo debe tener extensión .txt";
    public static final String EXPORT_MISSING_FOLDER = "La carpeta donde se quiere exportar no existe";
    public static final String EXPORT_NO_PERMISSION = "No hay permiso para escribir en esa ubicación";

    // Errores detallados de importar (import_)
    public static final String IMPORT_NULL_FILE = "No se indicó el archivo que se quiere importar";
    public static final String IMPORT_IS_DIRECTORY = "El nombre indicado corresponde a una carpeta, no a un archivo";
    public static final String IMPORT_NOT_FOUND = "El archivo no existe";
    public static final String IMPORT_BAD_EXTENSION = "El archivo debe tener extensión .txt";
    public static final String IMPORT_NO_PERMISSION = "No hay permiso para leer el archivo";
    public static final String IMPORT_BAD_FORMAT = "Hay una línea que no tiene el formato \"Clase fila, columna\"";
    public static final String IMPORT_UNKNOWN_CLASS = "El archivo menciona una clase que no existe en el safari";
    public static final String IMPORT_INVALID_NUMBER = "La fila o la columna no es un número entero";
    public static final String IMPORT_OUT_OF_SAFARI = "Hay una posición fuera del safari";

    // Errores detallados de abrir (open)
    public static final String OPEN_NULL_FILE = "No se indicó el archivo que se quiere abrir";
    public static final String OPEN_IS_DIRECTORY = "El nombre indicado corresponde a una carpeta, no a un archivo";
    public static final String OPEN_NOT_FOUND = "El archivo no existe";
    public static final String OPEN_BAD_EXTENSION = "El archivo debe tener extensión .dat";
    public static final String OPEN_NO_PERMISSION = "No hay permiso para leer el archivo";
    public static final String OPEN_NOT_SAFARI = "El archivo no contiene un safari guardado";
    public static final String OPEN_UNKNOWN_CLASS = "El archivo contiene elementos de clases desconocidas";
    public static final String OPEN_INCOMPATIBLE = "El archivo fue guardado con una versión incompatible";
    

    //Minicompilador flexible
    public static final String IMPORT_NOT_ENTITY = "La clase existe pero no es una entidad del safari";
    public static final String IMPORT_CANNOT_CREATE = "No se puede crear la entidad (la clase es abstracta o no tiene el constructor (EcoSafari, fila, columna))";

    /**
     * Crea la excepción con un mensaje.
     *
     * @param message el mensaje del error
     */
    public EcoSafariException(String message) {
        super(message);
    }
}