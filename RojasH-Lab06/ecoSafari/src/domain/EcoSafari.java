package domain;
import java.util.Random;
import java.io.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
/**
 * Safari de 25x25 casillas donde viven las entidades (elefantes, arbustos y tormentas).
 * Guarda dónde está cada una y qué casillas están afectadas por una tormenta.
 *
 * @author MoralesS-RojasH
 */
public class EcoSafari implements Serializable {

    private static final int SIZE=25;
    private Random random = new Random();
    private Entity[][] cells;
    private boolean[][] affected;
    
    /**
     * Crea un safari vacío. Los elementos se agregan importando un archivo.
     */
    public EcoSafari() {
        cells = new Entity[SIZE][SIZE];
        affected = new boolean[SIZE][SIZE];
    }

    /**
     * Devuelve el generador de números aleatorios utilizado por las entidades.
     * @return El generador de números aleatorios.
     */

    public Random getRandom(){
        return random;
    }
    
    /**
     * Cambia el generador de números aleatorios (útil para hacer que las pruebas sean predecibles)
     * @param random el nuevo generador
     */
    public void setRandom(Random random){
        this.random = random;
    }
    
    /**
     * Devuelve el tamaño del safari (filas y columnas).
     *
     * @return el tamaño del safari
     */
    public int  getSize(){
        return SIZE;
    }

    /**
     * Dice si una posición está dentro del safari.
     *
     * @param r la fila
     * @param c la columna
     * @return true si la posición está dentro, false si no
     */
    public boolean isInside(int r, int c){
        return ((0<=r) && (r<SIZE) && (0<=c) && (c<SIZE));
    }
    
    /**
     * Devuelve la entidad que está en una posición.
     *
     * @param r la fila
     * @param c la columna
     * @return la entidad de esa casilla, o null si está vacía o fuera del safari
     */
    public Entity get(int r,int c){
        return (isInside(r,c)? cells[r][c]: null);
    }

    /**
     * Pone una entidad en una posición. Si la posición está fuera del safari, no hace nada.
     * Si se pasa null, deja la casilla vacía.
     *
     * @param e la entidad
     * @param r la fila
     * @param c la columna
     */
    public void set(Entity e, int r, int c){
        if (isInside(r,c)){ 
            cells[r][c]=e;
        }
    }

    
    /**
     * Busca la posición de una entidad.
     *
     * @param e la entidad
     * @return un arreglo {fila, columna} si la encuentra, o null si no está
     */
    public int[]  find(Entity e){
       int[] position=null;
       for (int r=0 ; r<SIZE && position== null; r++){
           for (int c=0 ; c<SIZE && position==null ;c++){
               if (cells[r][c]==e){
                   position=new int [] {r,c};
               }
           }
       }
       return position;
    }
    
    /**
     * Avanza la simulación un turno. Primero todas las entidades hacen su tic()
     * y después todas hacen su tac().
     */
    public void ticTac(){ 
        for (int i=0; i<SIZE; i++){
            for (int j=0; j<SIZE; j++){
                Entity e = get(i, j);
                if (null !=e){
                    e.tic();
                }
            }
        }
        for (int i=0; i<SIZE; i++){
            for (int j=0; j<SIZE; j++){
                Entity e = get(i, j);
                if (null !=e){
                    e.tac();
                }
            }
        }
    }

    /**
     * Marca una casilla como afectada por una tormenta. Si la posición se sale
     * del safari, da la vuelta por el lado contrario.
     *
     * @param r la fila
     * @param c la columna
     */
    public void setAffected(int r, int c){
        int filaNormalizada = ((r % SIZE) + SIZE) % SIZE;
        int columnaNormalizada = ((c % SIZE) + SIZE) % SIZE;
        affected[filaNormalizada][columnaNormalizada] = true;
    }
    
    /**
     * Dice si una casilla está afectada por una tormenta. Si la posición se sale
     * del safari, da la vuelta por el lado contrario.
     *
     * @param r la fila
     * @param c la columna
     * @return true si la casilla está afectada, false si no
     */
    public boolean isAffected(int r, int c){
        int filaNormalizada = ((r % SIZE) + SIZE) % SIZE;
        int columnaNormalizada = ((c % SIZE) + SIZE) % SIZE;
        return affected[filaNormalizada][columnaNormalizada];
    }
    
    /**
     * Quita la marca de afectada a una casilla. Si la posición se sale
     * del safari, da la vuelta por el lado contrario.
     *
     * @param r la fila
     * @param c la columna
     */
    public void unsetAffected(int r, int c){
        int filaNormalizada = ((r % SIZE) + SIZE) % SIZE;
        int columnaNormalizada = ((c % SIZE) + SIZE) % SIZE;
        affected[filaNormalizada][columnaNormalizada] = false;
    }
    
    /**
     * Abre un archivo (por ahora está en construcción).
     *
     * @param file el archivo a abrir
     * @throws EcoSafariException siempre, porque la opción está en construcción
     */
    public void open00(File file) throws EcoSafariException {
        throw new EcoSafariException("Opción abrir en construcción. Archivo " + file.getName());
    }

    /**
     * Guarda el safari en un archivo (por ahora está en construcción).
     *
     * @param file el archivo donde guardar
     * @throws EcoSafariException siempre, porque la opción está en construcción
     */
    public void saveAs00(File file) throws EcoSafariException {
        throw new EcoSafariException("Opción salvar como en construcción. Archivo " + file.getName());
    }

    /**
     * Importa desde un archivo de texto (por ahora está en construcción).
     *
     * @param file el archivo a importar
     * @throws EcoSafariException siempre, porque la opción está en construcción
     */
    public void import_00(File file) throws EcoSafariException {
        throw new EcoSafariException("Opción importar en construcción. Archivo " + file.getName());
    }

    /**
     * Exporta a un archivo de texto (por ahora está en construcción).
     *
     * @param file el archivo donde exportar
     * @throws EcoSafariException siempre, porque la opción está en construcción
     */
    public void exportAs00(File file) throws EcoSafariException {
        throw new EcoSafariException("Opción exportar como en construcción. Archivo " + file.getName());
    }
    

    /**
     * Guarda el estado actual del safari en un archivo (versión 01: un solo mensaje general).
     *
     * @param file el archivo donde guardar
     * @throws EcoSafariException si no se pudo guardar
     */
    public void saveAs01(File file) throws EcoSafariException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            throw new EcoSafariException(EcoSafariException.SAVE_ERROR);
        }
    }

    /**
     * Lee un safari guardado en un archivo (versión 01: un solo mensaje general).
     *
     * @param file el archivo a leer
     * @return el safari leído del archivo
     * @throws EcoSafariException si no se pudo abrir
     */
    public EcoSafari open01(File file) throws EcoSafariException {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            EcoSafari safari = (EcoSafari) in.readObject();
            in.close();
            return safari;
        } catch (IOException e) {
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR);
        } catch (ClassNotFoundException e) {
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR);
        }
    }

    /**
     * Guarda el estado actual del safari en un archivo .dat.
     * Cada tipo de error se informa con un mensaje distinto.
     *
     * @param file el archivo donde guardar
     * @throws EcoSafariException si el archivo es null, es una carpeta, no tiene
     *         extensión .dat, la carpeta no existe, no hay permiso de escritura,
     *         el safari tiene elementos no serializables o falla la escritura
     */
    public void saveAs(File file) throws EcoSafariException {
        if (file == null) {
            throw new EcoSafariException(EcoSafariException.SAVE_NULL_FILE);
        }
        if (file.isDirectory()) {
            throw new EcoSafariException(EcoSafariException.SAVE_IS_DIRECTORY);
        }
        if (!file.getName().toLowerCase().endsWith(".dat")) {
            throw new EcoSafariException(EcoSafariException.SAVE_BAD_EXTENSION);
        }
        File folder = file.getAbsoluteFile().getParentFile();
        if (folder == null || !folder.isDirectory()) {
            throw new EcoSafariException(EcoSafariException.SAVE_MISSING_FOLDER);
        }
        if (file.exists() ? !file.canWrite() : !folder.canWrite()) {
            throw new EcoSafariException(EcoSafariException.SAVE_NO_PERMISSION);
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(this);
        } catch (NotSerializableException e) {
            throw new EcoSafariException(EcoSafariException.SAVE_NOT_SERIALIZABLE);
        } catch (IOException e) {
            throw new EcoSafariException(EcoSafariException.SAVE_ERROR);
        }
    }

    /**
     * Lee un safari guardado en un archivo .dat.
     * Cada tipo de error se informa con un mensaje distinto.
     *
     * @param file el archivo a leer
     * @return el safari leído del archivo
     * @throws EcoSafariException si el archivo es null, es una carpeta, no existe,
     *         no tiene extensión .dat, no se puede leer, no contiene un safari,
     *         tiene clases desconocidas, es de una versión incompatible o falla la lectura
     */
    public EcoSafari open(File file) throws EcoSafariException {
        if (file == null) {
            throw new EcoSafariException(EcoSafariException.OPEN_NULL_FILE);
        }
        if (file.isDirectory()) {
            throw new EcoSafariException(EcoSafariException.OPEN_IS_DIRECTORY);
        }
        if (!file.exists()) {
            throw new EcoSafariException(EcoSafariException.OPEN_NOT_FOUND);
        }
        if (!file.getName().toLowerCase().endsWith(".dat")) {
            throw new EcoSafariException(EcoSafariException.OPEN_BAD_EXTENSION);
        }
        if (!file.canRead()) {
            throw new EcoSafariException(EcoSafariException.OPEN_NO_PERMISSION);
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object read = in.readObject();
            if (!(read instanceof EcoSafari)) {
                throw new EcoSafariException(EcoSafariException.OPEN_NOT_SAFARI);
            }
            return (EcoSafari) read;
        } catch (InvalidClassException e) {
            throw new EcoSafariException(EcoSafariException.OPEN_INCOMPATIBLE);
        } catch (StreamCorruptedException e) {
            throw new EcoSafariException(EcoSafariException.OPEN_NOT_SAFARI);
        } catch (EOFException e) {
            throw new EcoSafariException(EcoSafariException.OPEN_NOT_SAFARI);
        } catch (ClassNotFoundException e) {
            throw new EcoSafariException(EcoSafariException.OPEN_UNKNOWN_CLASS);
        } catch (IOException e) {
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR);
        }
    }

    /**
     * Exporta el estado actual del safari a un archivo de texto (versión 01: un solo mensaje general).
     * Escribe una línea por entidad: el nombre de la clase y su posición.
     *
     * @param file el archivo donde exportar
     * @throws EcoSafariException si no se pudo exportar
     */
    public void exportAs01(File file) throws EcoSafariException {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(file));
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Entity entity = get(r, c);
                    if (entity != null) {
                        out.println(entity.getClass().getSimpleName() + " " + r + ", " + c);
                    }
                }
            }
            out.close();
        } catch (IOException e) {
            throw new EcoSafariException(EcoSafariException.EXPORT_ERROR);
        }
    }


    /**
     * Exporta el estado actual del safari a un archivo de texto (.txt).
     * Escribe una línea por entidad: el nombre de la clase y su posición.
     * Cada tipo de error se informa con un mensaje distinto.
     *
     * @param file el archivo donde exportar
     * @throws EcoSafariException si el archivo es null, es una carpeta, no tiene
     *         extensión .txt, la carpeta no existe, no hay permiso de escritura
     *         o falla la escritura
     */
    public void exportAs(File file) throws EcoSafariException {
        if (file == null) {
            throw new EcoSafariException(EcoSafariException.EXPORT_NULL_FILE);
        }
        if (file.isDirectory()) {
            throw new EcoSafariException(EcoSafariException.EXPORT_IS_DIRECTORY);
        }
        if (!file.getName().toLowerCase().endsWith(".txt")) {
            throw new EcoSafariException(EcoSafariException.EXPORT_BAD_EXTENSION);
        }
        File folder = file.getAbsoluteFile().getParentFile();
        if (folder == null || !folder.isDirectory()) {
            throw new EcoSafariException(EcoSafariException.EXPORT_MISSING_FOLDER);
        }
        if (file.exists() ? !file.canWrite() : !folder.canWrite()) {
            throw new EcoSafariException(EcoSafariException.EXPORT_NO_PERMISSION);
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Entity entity = get(r, c);
                    if (entity != null) {
                        out.println(entity.getClass().getSimpleName() + " " + r + ", " + c);
                    }
                }
            }
            if (out.checkError()) {
                throw new EcoSafariException(EcoSafariException.EXPORT_ERROR);
            }
        } catch (IOException e) {
            throw new EcoSafariException(EcoSafariException.EXPORT_ERROR);
        }
    }

    /**
     * Importa entidades desde un archivo de texto (versión 01: un solo mensaje general).
     * Cada línea tiene el nombre de la clase y la posición, por ejemplo: Elephant 10, 20
     *
     * @param file el archivo a importar
     * @throws EcoSafariException si no se pudo importar
     */
    public void import_01(File file) throws EcoSafariException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    importLine(line);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new EcoSafariException(EcoSafariException.IMPORT_ERROR);
        }
    }

    /**
     * Importa entidades desde un archivo de texto (.txt) (versión 02).
     * Informa la línea, la palabra y la causa de cada error.
     *
     * @param file el archivo a importar
     * @throws EcoSafariException si el archivo o alguna de sus líneas tiene errores
     */
    public void import_02(File file) throws EcoSafariException {
        if (file == null) {
            throw new EcoSafariException(EcoSafariException.IMPORT_NULL_FILE);
        }
        if (file.isDirectory()) {
            throw new EcoSafariException(EcoSafariException.IMPORT_IS_DIRECTORY);
        }
        if (!file.exists()) {
            throw new EcoSafariException(EcoSafariException.IMPORT_NOT_FOUND);
        }
        if (!file.getName().toLowerCase().endsWith(".txt")) {
            throw new EcoSafariException(EcoSafariException.IMPORT_BAD_EXTENSION);
        }
        if (!file.canRead()) {
            throw new EcoSafariException(EcoSafariException.IMPORT_NO_PERMISSION);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    importLine(line);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new EcoSafariException(EcoSafariException.IMPORT_ERROR);
        }
    }

    /**
     * Importa entidades desde un archivo de texto (.txt).
     * Cada línea tiene el nombre de la clase y la posición, por ejemplo: Elephant 10, 20
     * Sirve para cualquier clase de entidad (se crea por reflexión).
     * Si hay errores, se informan todos (línea, palabra y causa) y no se importa nada.
     *
     * @param file el archivo a importar
     * @throws EcoSafariException si el archivo es null, es una carpeta, no existe,
     *         no tiene extensión .txt, no se puede leer, alguna línea tiene un error
     *         (formato, clase desconocida, número inválido o posición fuera del safari)
     *         o falla la lectura
     */
    public void import_(File file) throws EcoSafariException {
        if (file == null) {
            throw new EcoSafariException(EcoSafariException.IMPORT_NULL_FILE);
        }
        if (file.isDirectory()) {
            throw new EcoSafariException(EcoSafariException.IMPORT_IS_DIRECTORY);
        }
        if (!file.exists()) {
            throw new EcoSafariException(EcoSafariException.IMPORT_NOT_FOUND);
        }
        if (!file.getName().toLowerCase().endsWith(".txt")) {
            throw new EcoSafariException(EcoSafariException.IMPORT_BAD_EXTENSION);
        }
        if (!file.canRead()) {
            throw new EcoSafariException(EcoSafariException.IMPORT_NO_PERMISSION);
        }
        List<Object[]> items = new ArrayList<Object[]>();
        StringBuilder errors = new StringBuilder();
        int count = 0;
        int number = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                number++;
                if (!line.trim().isEmpty()) {
                    String error = parseLine(line, number, items);
                    if (error != null) {
                        errors.append(error).append("\n");
                        count++;
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new EcoSafariException(EcoSafariException.IMPORT_ERROR);
        }
        if (count > 0) {
            throw new EcoSafariException("Se encontró " + count + (count == 1 ? " error" : " errores")
                + " en el archivo:\n" + errors.toString().trim());
        }
        for (Object[] item : items) {
            createEntity((String) item[0], (Integer) item[1], (Integer) item[2]);
        }
    }

    /**
     * Importa entidades desde un archivo de texto (.txt) (versión 03).
     * Cada línea tiene el nombre de la clase y la posición, por ejemplo: Elephant 10, 20
     * Sirve para cualquier clase de entidad (se crea por reflexión).
     * Si hay errores, se informan todos (línea, palabra y causa) y no se importa nada.
     *
     * @param file el archivo a importar
     * @throws EcoSafariException si el archivo es null, es una carpeta, no existe,
     *         no tiene extensión .txt, no se puede leer, alguna línea tiene un error
     *         (formato, clase desconocida, número inválido o posición fuera del safari)
     *         o falla la lectura
     */
    public void import_03(File file) throws EcoSafariException {
        if (file == null) {
            throw new EcoSafariException(EcoSafariException.IMPORT_NULL_FILE);
        }
        if (file.isDirectory()) {
            throw new EcoSafariException(EcoSafariException.IMPORT_IS_DIRECTORY);
        }
        if (!file.exists()) {
            throw new EcoSafariException(EcoSafariException.IMPORT_NOT_FOUND);
        }
        if (!file.getName().toLowerCase().endsWith(".txt")) {
            throw new EcoSafariException(EcoSafariException.IMPORT_BAD_EXTENSION);
        }
        if (!file.canRead()) {
            throw new EcoSafariException(EcoSafariException.IMPORT_NO_PERMISSION);
        }
        List<Object[]> items = new ArrayList<Object[]>();
        StringBuilder errors = new StringBuilder();
        int count = 0;
        int number = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                number++;
                if (!line.trim().isEmpty()) {
                    String error = parseLine(line, number, items);
                    if (error != null) {
                        errors.append(error).append("\n");
                        count++;
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new EcoSafariException(EcoSafariException.IMPORT_ERROR);
        }
        if (count > 0) {
            throw new EcoSafariException("Se encontró " + count + (count == 1 ? " error" : " errores")
                + " en el archivo:\n" + errors.toString().trim());
        }
        for (Object[] item : items) {
            createEntity((String) item[0], (Integer) item[1], (Integer) item[2]);
        }
    }

    /**
     * Revisa una línea del archivo sin crear nada.
     *
     * @param line la línea, con el formato "Clase fila, columna"
     * @param number el número de la línea en el archivo
     * @param items donde se agrega {clase, fila, columna} si la línea es correcta
     * @return null si la línea es correcta; si no, el detalle del error (línea, palabra y causa)
     */
    private String parseLine(String line, int number, List<Object[]> items) {
        String prefix = "Línea " + number + ": ";
        String[] data = line.trim().split("\\s+", 2);
        if (data.length != 2) {
            return prefix + "\"" + line.trim() + "\" - " + EcoSafariException.IMPORT_BAD_FORMAT;
        }
        String classCause = classError(data[0]);
        if (classCause != null) {
            return prefix + "\"" + data[0] + "\" - " + classCause;
        }
        String[] position = data[1].split(",");
        if (position.length != 2) {
            return prefix + "\"" + data[1].trim() + "\" - " + EcoSafariException.IMPORT_BAD_FORMAT;
        }
        int r;
        int c;
        try {
            r = Integer.parseInt(position[0].trim());
        } catch (NumberFormatException e) {
            return prefix + "\"" + position[0].trim() + "\" - " + EcoSafariException.IMPORT_INVALID_NUMBER;
        }
        try {
            c = Integer.parseInt(position[1].trim());
        } catch (NumberFormatException e) {
            return prefix + "\"" + position[1].trim() + "\" - " + EcoSafariException.IMPORT_INVALID_NUMBER;
        }
        if (!isInside(r, c)) {
            return prefix + "\"" + r + ", " + c + "\" - " + EcoSafariException.IMPORT_OUT_OF_SAFARI;
        }
        items.add(new Object[] {data[0], r, c});
        return null;
    }

    /**
     * Busca una clase por su nombre. Si el nombre no tiene punto, se busca en el
     * mismo paquete que EcoSafari; si lo tiene, se toma como nombre completo.
     *
     * @param name el nombre de la clase
     * @return la clase, o null si no existe
     */
    private Class<?> loadClass(String name) {
        String fullName = name.contains(".") ? name : EcoSafari.class.getPackage().getName() + "." + name;
        try {
            return Class.forName(fullName, false, EcoSafari.class.getClassLoader());
        } catch (ClassNotFoundException | LinkageError e) {
            return null;
        }
    }

    /**
     * Revisa si con el nombre se puede crear una entidad: la clase debe existir,
     * implementar Entity, no ser abstracta y tener el constructor (EcoSafari, int, int).
     *
     * @param name el nombre de la clase
     * @return null si se puede crear; si no, la causa del error
     */
    private String classError(String name) {
        Class<?> cls = loadClass(name);
        if (cls == null) {
            return EcoSafariException.IMPORT_UNKNOWN_CLASS;
        }
        if (!Entity.class.isAssignableFrom(cls)) {
            return EcoSafariException.IMPORT_NOT_ENTITY;
        }
        if (cls.isInterface() || Modifier.isAbstract(cls.getModifiers())) {
            return EcoSafariException.IMPORT_CANNOT_CREATE;
        }
        try {
            cls.getConstructor(EcoSafari.class, int.class, int.class);
        } catch (NoSuchMethodException e) {
            return EcoSafariException.IMPORT_CANNOT_CREATE;
        }
        return null;
    }

    /**
     * Crea la entidad que describe una línea del archivo.
     *
     * @param line la línea, con el formato "Clase fila, columna"
     * @throws EcoSafariException si la línea no tiene el formato correcto
     */
    private void importLine(String line) throws EcoSafariException {
        String[] data = line.trim().split("\\s+", 2);
        if (data.length != 2) {
            throw new EcoSafariException(EcoSafariException.IMPORT_BAD_FORMAT);
        }
        String[] position = data[1].split(",");
        if (position.length != 2) {
            throw new EcoSafariException(EcoSafariException.IMPORT_BAD_FORMAT);
        }
        try {
            int r = Integer.parseInt(position[0].trim());
            int c = Integer.parseInt(position[1].trim());
            createEntity(data[0], r, c);
        } catch (NumberFormatException e) {
            throw new EcoSafariException(EcoSafariException.IMPORT_INVALID_NUMBER);
        }
    }

    /**
     * Crea una entidad según el nombre de su clase, usando reflexión.
     *
     * @param name el nombre de la clase
     * @param r la fila
     * @param c la columna
     * @throws EcoSafariException si la clase no existe, no se puede crear o la posición está fuera del safari
     */
    private void createEntity(String name, int r, int c) throws EcoSafariException {
        if (!isInside(r, c)) {
            throw new EcoSafariException(EcoSafariException.IMPORT_OUT_OF_SAFARI);
        }
        Class<?> cls = loadClass(name);
        if (cls == null) {
            throw new EcoSafariException(EcoSafariException.IMPORT_UNKNOWN_CLASS);
        }
        try {
            cls.getConstructor(EcoSafari.class, int.class, int.class).newInstance(this, r, c);
        } catch (ReflectiveOperationException e) {
            throw new EcoSafariException(EcoSafariException.IMPORT_CANNOT_CREATE);
        }
    }

}