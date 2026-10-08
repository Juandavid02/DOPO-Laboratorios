import java.util.TreeMap;

/**
 * Clase que representa un sistema para administrar múltiples listas de
 * reproducción de música.
 *
 * Permite crear listas de reproducción, asignar canciones, consultar su
 * tamaño y representación, realizar operaciones unarias y binarias,
 * ordenar canciones, calcular frecuencias y obtener la duración total
 * de una lista.
 *
 * @MoralesS-RojasH
 * @version 1.0
 */
public class MiniTunes{
    
    private TreeMap<String,Playlist> playlists;
    private boolean ok; 
    
    /**
     * Crea una nueva instancia de MiniTunes.
     *
     * Inicializa una estructura de tipo TreeMap para almacenar las listas
     * de reproducción. Las listas se organizan automáticamente en orden
     * alfabético según su nombre.
     *
     * Inicialmente, el estado de la última operación se establece como
     * exitoso.
     */
    public MiniTunes(){
        playlists = new TreeMap<>();
        ok = true; 
    }

    /**
     * Normaliza el nombre de una lista de reproducción.
     *
     * Elimina los espacios al inicio y al final del nombre, reemplaza
     * múltiples espacios consecutivos por un único espacio y convierte
     * el nombre a letras mayúsculas.
     *
     * Si el nombre es nulo o está vacío después de eliminar los espacios,
     * se retorna null.
     *
     * @param name el nombre que se desea normalizar
     * @return el nombre normalizado en mayúsculas o null si el
     * nombre no es válido
     */
    private String normalizar(String name){
        if (name == null){
            return null;
        }
        else{
            String nameNormalized = name.trim().replaceAll("\\s+", " ");
            if (!nameNormalized.isEmpty()){
                nameNormalized = nameNormalized.toUpperCase();
                return nameNormalized;
            }
            return null;
        }
    }
    
     /**
     * Define una nueva lista de reproducción con el nombre indicado.
     *
     * El nombre es normalizado antes de ser almacenado. La lista solo se
     * crea si el nombre es válido y no existe previamente una lista con
     * el mismo nombre.
     *
     * @param name el nombre de la nueva lista de reproducción
     */
    public void define(String name){
        String nameNormalized = normalizar(name);
        if (nameNormalized != null){
            if (!playlists.containsKey(nameNormalized)){
                playlists.put(nameNormalized, new Playlist());
                ok = true;
            }
            else{
                ok = false;
            }
        }
        else{
            ok = false; 
        }
    }
     
    /**
     * Asigna un conjunto de canciones a una lista de reproducción existente.
     *
     * La operación solo se realiza si el nombre de la lista es válido es decir 
     * si la lista existe y el arreglo de canciones no es null.
     * La información proporcionada se utiliza para crear una nueva instancia
     * de Playlist la cual reemplaza el contenido actual de la lista
     * indicada.
     *
     * @param a el nombre de la lista de reproducción a la que se asignarán
     * las canciones
     * @param playlist el arreglo bidimensional que contiene la información
     * de las canciones
     */
    public void assign(String a, String [] [] playlist){
        String nameNormalized = normalizar(a);
        if (nameNormalized != null && playlist != null){
            if (playlists.containsKey(nameNormalized)){
                Playlist playlistAssign = new Playlist(playlist);
                playlists.put(nameNormalized, playlistAssign);
                ok = true;
            }
            else{
                ok = false;
            }
        }    
        else{
            ok =false;
        }
    }

    /**
     * Obtiene el número de canciones almacenadas en una lista de reproducción.
     *
     * @param a el nombre de la lista de reproducción
     * @return el número de canciones de la lista si existe, si 
     * el nombre no es válido o la lista no existe es -1
     */
    public int size(String a){
        String nameNormalized = normalizar(a);
        if (nameNormalized != null && playlists.containsKey(nameNormalized)){
            ok = true; 
            return playlists.get(nameNormalized).size();
        }
        else{
            ok = false;
            return -1;   
        }
    }
    
    /**
     * Obtiene los nombres de todas las listas de reproducción.
     *
     * Los nombres se retornan en orden alfabético y separados por comas.
     *
     * @return una cadena que contiene los nombres de las listas 
     */
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (String name : playlists.keySet()){
            result.append(name+", ");
        }
        if (result.length() > 0){
            result.setLength(result.length() - 2);
        }
        ok = true;
        return result.toString();
    }
    
    /**
     * Obtiene la representación en forma de tabla de una lista de
     * reproducción específica.
     *
     * @param name el nombre de la lista de reproducción
     * @return la representación de la lista si existe y si no una cadena
     * vacía
     */
    public String toString(String name){
        String nameNormalized = normalizar(name);
        if (nameNormalized != null && playlists.containsKey(nameNormalized)){
            ok = true;
            return playlists.get(nameNormalized).toString();
        }
        else{
            ok = false;
            return "";       
        }
    }    
    
    /**
     * Asigna el resultado de una operación unaria a una lista de reproducción.
     *
     * La operación se realiza tomando como base la lista b y
     * almacenando el resultado en la lista a.
     *
     * Las operaciones disponibles son: 'a' (agrega una canción),'d' (elimina una canción) y 
     * 's' selecciona canciones según un patrón de búsqueda.
     *
     * La operación solo se realiza si los nombres son válidos, la lista
     * de origen existe y la lista de destino también existe.
     *
     * @param a el nombre de la lista donde se almacenará el resultado
     * @param b el nombre de la lista sobre la cual se realizará la operación
     * @param op el carácter que representa la operación
     * @param values los valores necesarios para realizar la operación
     */
    public void assignUnary(String a, String b, char op, String [] values){
        String nameNormalizedA = normalizar(a);
        String nameNormalizedB = normalizar(b);
        char opNormalize = Character.toLowerCase(op);
        if (nameNormalizedA != null && nameNormalizedB != null && playlists.containsKey(nameNormalizedB)){
            if (opNormalize == 'a'){
                if (playlists.containsKey(nameNormalizedA)){
                    playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).add(values));
                    ok = true;
                }
                else{
                    ok = false;
                }
            }
            else if (opNormalize == 'd'){
                if (playlists.containsKey(nameNormalizedA)){ 
                    playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).delete(values));
                    ok = true;  
                }
                else{
                    ok = false;
                }
            }
            else if (opNormalize == 's'){
                if (playlists.containsKey(nameNormalizedA)){
                    playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).select(values));
                    ok = true;
                }
                else{
                    ok = false;
                }
            }
            else{
                ok = false;
            }
        }
        else{
            ok = false;
        }
    }
      
    
     /**
     * Asigna el resultado de una operación binaria a una lista de reproducción.
     *
     * La operación se realiza entre las listas b y c, y el
     * resultado se almacena en la lista a.
     *
     * Las operaciones disponibles son: 'u' (unión de las listas), 'i' (intersección de las listas)
     * y 'd' (diferencia entre las listas).
     * 
     * Las canciones conservan su orden original en la lista resultante.
     *
     * @param a el nombre de la lista donde se almacenará el resultado
     * @param b el nombre de la primera lista utilizada en la operación
     * @param op el carácter que representa la operación
     * @param c el nombre de la segunda lista utilizada en la operación
     */
    public void assignBinary(String a, String b, char op, String c){
        String nameNormalizedA = normalizar(a);
        String nameNormalizedB = normalizar(b);
        String nameNormalizedC = normalizar(c);
        char opNormalize = Character.toLowerCase(op);
        if (nameNormalizedA != null && nameNormalizedB != null && nameNormalizedC != null && playlists.containsKey(nameNormalizedB) && playlists.containsKey(nameNormalizedC)){
            if (opNormalize == 'u'){
                playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).union(playlists.get(nameNormalizedC)));
                ok = true;
            }
            else if (opNormalize == 'i'){
                playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).intersection(playlists.get(nameNormalizedC)));
                ok = true;
            }
            else if (opNormalize == 'd'){
                playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).difference(playlists.get(nameNormalizedC)));
                ok = true;
            }
            else {
                ok = false;
            }
        }
        else{
            ok = false;
        }
    }
    
     /**
     * Ordena las canciones de una lista de reproducción según el criterio
     * indicado.
     *
     * La operación solo se realiza si el nombre de la lista es válido y
     * la lista existe.
     *
     * @param name el nombre de la lista que se desea ordenar
     * @param op el carácter que indica el criterio de ordenamiento
     */
    public void sort(String name, char op){
        String nameNormalized = normalizar(name);
        char opNormalize = Character.toLowerCase(op);
        if (nameNormalized != null && playlists.containsKey(nameNormalized)){
            playlists.put(nameNormalized, playlists.get(nameNormalized).sort(op));
            ok = true;
        }
        else {
            ok = false;
        }
    }
    
    /**
     * Calcula la cantidad de veces que aparece un valor determinado en una
     * característica específica de las canciones de una lista.
     *
     * El criterio de búsqueda se determina mediante el carácter op, la
     * operacion solo se realiza si la lista a buscar no es nula y
     * debe esta en las paylists
     *
     * @param name el nombre de la lista de reproducción
     * @param value el valor cuya frecuencia se desea calcular
     * @param op el carácter que indica el atributo sobre el cual se realizará
     * la búsqueda
     * @return el número de apariciones del valor; -1 si la operación
     * no se puede realizar
     */
    public int frequency(String name, String value, char op){
        String nameNormalized = normalizar(name);
        int count = 0;
        char opNormalize = Character.toLowerCase(op);
        if (nameNormalized != null && playlists.containsKey(nameNormalized)){
            count = playlists.get(nameNormalized).frequency(value , opNormalize);
            if (count == -1){
                ok = false;
            }
            else {
                ok = true;
            }
        }
        else{
            count = -1;
            ok = false;
        }
        return count;
    }
    
    /**
     * Calcula la duración total de todas las canciones de una lista de
     * reproducción.
     *
     * @param name el nombre de la lista de reproducción
     * @return la duración total de la lista si existe, -1 si el
     * nombre no es válido o la lista no existe
     */
    public int totalDuration(String name){
        String nameNormalized = normalizar(name);
        if (nameNormalized != null && playlists.containsKey(nameNormalized)){
            ok = true;
            return playlists.get(nameNormalized).totalDuration();
        }
        ok = false;
        return -1;
    }
    
    /**
     * Comprueba si la última operación realizada fue exitosa.
     *
     * @return true si la última operación se completó correctamente;
     * y false en caso contrario
     */
    public boolean ok(){
        return ok;
    }
    

}
    



