//Each song is described by its title, artist, genre, duration, and rating.
//The title and artist are mandatory. The genre, duration, and rating may be unknown.
//The combination (title, artist) must be unique. Two songs cannot have the same title and artist.
//The duration (minutes) must be between 1 and 9.
//The rating must be between * and *****.

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Clase que representa una lista de reproducción de canciones.
 *
 * Cada canción está compuesta por un título, un artista, un género,
 * una duración y una calificación. El título y el artista son obligatorios,
 * mientras que el género, la duración y la calificación pueden ser
 * desconocidos.
 *
 * La combinación de título y artista debe ser única dentro de la lista
 * de reproducción.
 *
 * Permite agregar, eliminar, seleccionar, ordenar y realizar operaciones
 * de conjuntos entre listas de reproducción.
 *
 * @author MoralesS-RojasR
 * @version 1.0
 */

public class Playlist {
    
    private List<String[]> songs;
    
    /**
     * Crea una nueva lista de reproducción vacía.
     *
     * Inicializa una lista que almacenará la información de las canciones.
     */
    public Playlist(){
        this.songs = new ArrayList<>();
    }
    
    /**
     * Crea una nueva lista de reproducción a partir de un arreglo de canciones.
     *
     * Cada canción es normalizada antes de ser agregada. Las canciones con
     * información inválida o que tengan la misma combinación de título y
     * artista que una canción existente no son agregadas.
     *
     * @param songs el arreglo bidimensional que contiene las canciones
     * que se desean agregar a la lista de reproducción
     */
    public Playlist(String[][] songs) {
        this.songs = new ArrayList<>();
        for (String[] song : songs) {
            String[] normalizedSong = normalizar(song);
            if (normalizedSong != null && !check(normalizedSong)) {
                this.songs.add(normalizedSong);
            }
        }
    }
    
    /**
     * Crea una nueva lista de reproducción utilizando directamente una lista
     * de canciones previamente procesadas.
     *
     * Este constructor se utiliza internamente para crear nuevas listas como
     * resultado de operaciones, evitando normalizar nuevamente las canciones.
     *
     * @param songsValidas la lista de canciones que formará parte de la nueva
     * lista de reproducción y se nombran asi porque ya pasaron por el proceso de 
     * normalizacion
     */
    private Playlist(List<String[]> songsValidas) { //IA generativa: Se hace para que no haya reprocesamiento
        this.songs = songsValidas;
    }

    /**
     * Comprueba si una canción ya existe en la lista de reproducción.
     *
     * Dos canciones se consideran iguales si tienen el mismo título y el
     * mismo artista.
     *
     * @param normalizedSong la canción normalizada que se desea verificar
     * @return true si ya existe una canción con el mismo título y
     * artista; false} en caso contrario
     */
    private boolean check(String[] normalizedSong) {
        for (String[] song : this.songs) {
            if (song[0].equals(normalizedSong[0]) && song[1].equals(normalizedSong[1])) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Normaliza y valida la información de una canción.
     *
     * El título y el artista son obligatorios. Ambos valores se eliminan de
     * espacios innecesarios y se convierten a mayúsculas.
     *
     * El género es opcional y si existe, se normaliza eliminando espacios
     * innecesarios y convirtiéndolo a mayúsculas.
     *
     * La duración es opcional, pero si se proporciona debe ser un número
     * entre 1 y 9.
     *
     * La calificación es opcional, pero si se proporciona debe contener
     * únicamente caracteres '*' y tener entre uno y cinco caracteres.
     *
     * @param song el arreglo que contiene la información de la canción
     * @return un nuevo arreglo con la información normalizada o
     * null si la canción contiene información inválida
     */
    private String[] normalizar(String [] song){
        String titleSong = song[0];
        String nameArtist = song[1];
    
        String genre = null;
        String duraction = null;
        String rating = null;
    
        if (song.length > 2){
            genre = song[2];
        }
    
        if (song.length > 3){
            duraction = song[3];
        }
    
        if (song.length > 4){
            rating = song[4];
        }
        if (titleSong != null && !titleSong.isEmpty() && nameArtist != null && !nameArtist.isEmpty()){
            titleSong = titleSong.trim().replaceAll("\\s+", " "); //IA generativa
            titleSong = titleSong.toUpperCase();
            nameArtist = nameArtist.trim().replaceAll("\\s+", " ");
            nameArtist = nameArtist.toUpperCase();
        }
        else{
            return null;
        }
        
        if (genre == null || genre.isEmpty()){
            genre = null;                 
        }
        else {
            genre = genre.trim().replaceAll("\\s+", " ");
            genre = genre.toUpperCase();
        }
        
        if (duraction == null || duraction.isEmpty()){
            duraction = null;
        }
        else {
            duraction = duraction.replace(" ", "");
            if (duraction.length() == 1 &&
                duraction.charAt(0) >= '1' &&
                duraction.charAt(0) <= '9') {}
            else {
                return null;
            }
        }
        
        if (rating == null || rating.isEmpty()){
            rating = null;                 
        }
        else {
            rating = rating.replace(" ", "");
            if (rating.length()<1 || rating.length() > 5){
               return null;
            }
            else{
                for (int j = 0; j < rating.length(); j++) {
                    if (rating.charAt(j) != '*') {
                        return null;
                    }
                }
            }   
        }
        return new String[]{titleSong, nameArtist, genre, duraction, rating};
    }
    
    /**
     * Agrega una canción a la lista de reproducción.
     *
     * La canción es normalizada antes de ser agregada. La operación solo
     * se realiza si la información es válida y no existe otra canción con
     * la misma combinación de título y artista.
     *
     * La lista original no es modificada; se retorna una nueva lista con
     * la canción agregada.
     *
     * @param song la información de la canción que se desea agregar
     * @return una nueva lista de reproducción con la canción agregada o la
     * misma lista si la canción no es válida o ya existe
     */
    public Playlist add(String [] song){
        String[] normalizedSong = normalizar(song);
        if (normalizedSong == null || check(normalizedSong)) {
            return this;
        }
        List<String[]> nueva = new ArrayList<>(this.songs);
        nueva.add(normalizedSong);
        return new Playlist(nueva);   
    }
    
    /**
     * Elimina una canción de la lista de reproducción.
     *
     * La canción se identifica mediante la combinación de su título y artista.
     * La lista original no es modificada ya que se retorna una nueva lista sin la
     * canción indicada.
     *
     * Si la canción no es válida o no existe en la lista, se retorna la
     * lista actual sin modificaciones.
     *
     * @param song la información de la canción que se desea eliminar
     * @return una nueva lista sin la canción indicada o la misma lista si
     * la canción no es válida o no existe
     */
    public Playlist delete(String [] song){
        String[] normalizedSong = normalizar(song);
        if (normalizedSong == null) {
            return this;
        }
        int index = -1;
         for (int i = 0; i < this.songs.size(); i++){
            if (this.songs.get(i)[0].equals(normalizedSong[0]) && this.songs.get(i)[1].equals(normalizedSong[1])){
                index = i;
                break;    
            }
        }
        if (index == -1) {
            return this;
        }
        // Creo una LISTA NUEVA (copia) a partir de this.songs, así modificar "nueva" 
        // (agregar o quitar canciones) no afecta a this.songs, manteniendo la playlist original intacta.
        List<String[]> nueva = new ArrayList<>(this.songs); // Diferente campo en memoria es decir no afecta a this.songs
        nueva.remove(index);
        return new Playlist(nueva);
    }
    
     /**
     * Selecciona las canciones que cumplen con los criterios especificados.
     *
     * Los valores proporcionados corresponden, en orden, a título, artista,
     * género, duración y calificación. Los valores nulos o vacíos no se
     * utilizan como criterio de búsqueda.
     *
     * Las canciones seleccionadas deben cumplir con todos los criterios
     * válidos proporcionados.
     *
     * @param values los valores que definen los criterios de búsqueda
     * @return una nueva lista de reproducción que contiene las canciones
     * que cumplen con los criterios indicados
     */
    public Playlist select(String [] values){
        String titleSong = null;
        String nameArtist = null;
        String genre = null;
        String duraction = null;
        String rating = null;
        if (values.length > 0){
            titleSong = values[0];
        }
        if (values.length > 1){
            nameArtist = values[1];
        }
        if (values.length > 2){
            genre = values[2];
        }
        if (values.length > 3){
            duraction = values[3];
        }
        if (values.length > 4){
            rating = values[4];
        }
        
        if (titleSong != null && !titleSong.isEmpty()){
            titleSong = titleSong.trim().replaceAll("\\s+", " ");
            titleSong = titleSong.toUpperCase();
        }
        else{
            titleSong = null;
        }
        if (nameArtist != null && !nameArtist.isEmpty()){
            nameArtist = nameArtist.trim().replaceAll("\\s+", " ");
            nameArtist = nameArtist.toUpperCase();
        }
        else{
            nameArtist = null;
        }
        if (genre != null && !genre.isEmpty()){
            genre = genre.trim().replaceAll("\\s+", " ");
            genre = genre.toUpperCase();                
        }
        else {
            genre = null; 
        }
        
        if (duraction == null || duraction.isEmpty()){
            duraction = null;
        }
        else {
            duraction = duraction.replace(" ", "");
            if (duraction.length() == 1 &&
                duraction.charAt(0) >= '1' &&
                duraction.charAt(0) <= '9') {}
            else {
                duraction = null;
            }
        }
            
        if (rating == null || rating.isEmpty()){
            rating = null;                 
        }
        else {
            rating = rating.replace(" ", "");
            if (rating.length()<1 || rating.length() > 5){
               rating = null;
            }
            else{
                for (int j = 0; j < rating.length(); j++) {
                    if (rating.charAt(j) != '*') {
                        rating = null;
                    }
                }
            }   
        }
        List<String[]> selectedList = new ArrayList<>();
        for (String [] song: this.songs){
            if ((titleSong == null ||  song[0].equals(titleSong)) &&
            (nameArtist == null || song[1].equals(nameArtist)) &&
            (genre == null ||  (song[2] != null && song[2].equals(genre))) &&
            (duraction == null || (song[3] != null && song[3].equals(duraction))) &&
            (rating == null || (song[4] != null && song[4].equals(rating)))) {
                selectedList.add(song);
            }
        }  
        return new Playlist(selectedList);
    }
    
    /**
     * Obtiene el número de canciones almacenadas en la lista de reproducción.
     *
     * @return la cantidad de canciones de la lista
     */
    public int size(){
        return this.songs.size();
    }    
    
    /**
     * Obtiene una representación textual de las canciones de la lista.
     *
     * La representación incluye las columnas TITLE, ARTIST, GENRE,
     * DURATION y RATING. Las columnas se ajustan según la longitud de
     * la información almacenada y se les suman 3 espacios para mantener
     * los datos alineados.
     *
     * Los valores desconocidos se representan como espacios vacíos.
     *
     * @return una cadena que contiene la información de todas las canciones
     * de la lista en formato de tabla
     */
    // Songs are in uppercase with unnecessary spaces removed.
    // Columns are aligned and separated by three spaces.
    //TITLE    ARTIST          GENRE   DURATION   RATING
    //ONE      U2              ROCK           4   *****
    //NUMB     LINKIN PARK     ROCK           3
    //ALIVE    PEARL JAM       ROCK           5   ****
    //CREEP    RADIOHEAD       ROCK               *****
    //DREAMS   FLEETWOOD MAC                  4   ****
    public String toString() {
        int maxCharsTitle = 4;
        int maxCharsArtist = 6;
        int maxCharsGenre = 5;
        int maxCharsDuration = 8;   // ancho de alineacion de "DURATION" (sin el margen)
        int maxCharsRating = 9;
        for (String[] song: songs){
            int currentTitle = song[0].length();
            int currentArtist = song[1].length();
            int currentGenre = song[2] == null ? 0 : song[2].length();
            if (currentTitle > maxCharsTitle){
                maxCharsTitle = currentTitle;
            }
            if (currentArtist > maxCharsArtist){
                maxCharsArtist = currentArtist;
            }
            if (currentGenre > maxCharsGenre){
                maxCharsGenre = currentGenre;
            }
        }
        maxCharsTitle += 3 ;
        maxCharsArtist += 3;
        maxCharsGenre += 3;
        StringBuilder result = new StringBuilder(); //Recomendacion IA generativa ya que String es inmutable
        result.append(String.format("%-" + maxCharsTitle + "s", "TITLE"));
        result.append(String.format("%-" + maxCharsArtist + "s", "ARTIST"));
        result.append(String.format("%-" + maxCharsGenre + "s", "GENRE"));
        result.append(String.format("%-" + maxCharsDuration + "s   ", "DURATION"));
        result.append(String.format("%-" + maxCharsRating + "s\n", "RATING"));
        
        for (String[] song : songs) {
            //IA generativa recomendo operador ternario: condición ? valorSiVerdadero : valorSiFalso
            //Se usa para reemplazar un valor null por un String vacío ("") y evitar errores al trabajar con ese dato.
            String genre = song[2] == null ? "" : song[2]; 
            String duration = song[3] == null ? "" : song[3];
            String rating = song[4] == null ? "" : song[4];
            result.append(String.format("%-" + maxCharsTitle + "s", song[0]));
            result.append(String.format("%-" + maxCharsArtist + "s", song[1]));
            result.append(String.format("%-" + maxCharsGenre + "s", genre));
            result.append(String.format("%" + maxCharsDuration + "s   ", duration));
            result.append(String.format("%-" + maxCharsRating + "s\n", rating));
        }
        return result.toString();
    }
    
    /**
     * Compara esta lista de reproducción con otra lista.
     *
     * Dos listas se consideran iguales si contienen la misma cantidad de
     * canciones y cada canción se encuentra en la misma posición con la
     * misma información.
     *
     * @param pl la lista de reproducción con la que se desea comparar
     * @return true si ambas listas contienen las mismas canciones
     * en el mismo orden; false en caso contrario
     */
    public boolean equals(Playlist pl){
        if (this.songs.size() != pl.songs.size()){
            return false;
        }
        else {
            for (int i = 0; i<pl.songs.size(); i++){
                //IA generativa: Se usa Arrays.equals() ya que se necesita comparar por contenido
                //Si usaba ArrayList.equals() el equal de String[] no me funcionaba 
                if (!Arrays.equals(this.songs.get(i), pl.songs.get(i))){
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Compara este objeto con otro objeto para determinar si representan
     * la misma lista de reproducción.
     *
     * El objeto debe ser una instancia de Playlist para poder
     * realizar la comparación.
     *
     * @param o el objeto con el que se desea comparar esta lista
     * @return true si el objeto representa una lista equivalente;
     * false en caso contrario
     */
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        // Consultado en la API de Java: instanceof sirve para comprobar si un objeto es una instancia de una clase determinada
        if (o == null || !(o instanceof Playlist)){
            return false;
        }
        return equals((Playlist)o);
    }

    /**
     * Calcula la unión entre dos listas de reproducción.
     *
     * La lista resultante contiene primero las canciones de la primera lista y
     * posteriormente las canciones de la segunda lista que no estén
     * presentes en la anterior.
     *
     * La combinación de título y artista se utiliza para determinar si
     * una canción ya existe.
     *
     * @param b la lista de reproducción con la que se realizará la unión
     * @return una nueva lista que contiene la unión de ambas listas
     */
    public Playlist union(Playlist b){
        Playlist result = new Playlist();
        for (String[] song : this.songs){
            result = result.add(song);
        }
    
        for (String[] songb : b.songs){
            boolean flag = false;
            for (String[] songr : result.songs){
                if (songr[0].equals(songb[0]) && songr[1].equals(songb[1])){
                    flag = true;
                    break;
                }
            }
            if (!flag){
                result = result.add(songb);
            }
        }
        return result;
    }
    
    /**
     * Calcula la intersección entre dos listas de reproducción.
     *
     * La lista resultante contiene únicamente las canciones que aparecen
     * en ambas listas. La comparación se realiza utilizando el título y
     * el artista de cada canción.
     *
     * @param b la lista de reproducción con la que se realizará la
     * intersección
     * @return una nueva lista que contiene las canciones comunes entre
     * ambas listas
     */
    public Playlist intersection(Playlist b){
        Playlist result = new Playlist();
        for (String[] songa : this.songs){
            for (String[] songb : b.songs){
                if (songa[0].equals(songb[0]) && songa[1].equals(songb[1])){
                    result = result.add(songa);
                    break;
                }
            }
        }
        return result;
    }
    
    /**
     * Calcula la diferencia entre esta dos listas de reproducción.
     *
     * La lista resultante contiene las canciones de esta lista que no
     * aparecen en la lista proporcionada como parámetro.
     *
     * @param b la lista de reproducción cuyas canciones serán eliminadas
     * de esta lista
     * @return una nueva lista que contiene la diferencia entre ambas listas
     */
    public Playlist difference(Playlist b){
        Playlist result = new Playlist();
        for (String[] song : this.songs){
            result = result.add(song);
        }
        for (String[] songb : b.songs){
            result = result.delete(songb);
        }
        return result;
    }
    
    /**
     * Ordena las canciones de la lista según el criterio indicado.
     *
     * Los criterios disponibles son: 't' (ordena por título), 'a' 
     * (ordena por artista) 'g' (ordena por género.), 'd' 
     * (ordena por duración de mayor a menor) 'r' (ordena por calificación de mayor a menor).
     * 
     * Los valores nulos de género, duración y calificación se ubican al
     * final cuando se utilizan esos criterios.
     *
     * @param value el carácter que representa el criterio de ordenamiento
     * @return una nueva lista de reproducción con las canciones ordenadas
     */
    public Playlist sort(char value){
        List<String[]> result = new ArrayList<>(this.songs);
        // IA generativa: se utiliza nullsLast para manejar valores nulos y reverseOrder para ordenar de mayor a menor.
        if (value == 't'){
            result.sort(Comparator.comparing(fila -> fila[0]));
        }
        else if (value == 'a'){
            result.sort(Comparator.comparing(fila -> fila[1]));
        }
        else if (value == 'g'){
            result.sort(Comparator.comparing(fila -> fila[2], Comparator.nullsLast(String::compareTo)));
        }
        else if (value == 'd'){
        result.sort(Comparator.comparing(fila -> fila[3], Comparator.nullsLast(Comparator.reverseOrder())));
        }
        else if (value == 'r'){result.sort(Comparator.comparing(fila -> fila[4], Comparator.nullsLast(Comparator.reverseOrder())));
        }
        return new Playlist(result);
    }
    
     /**
     * Calcula la frecuencia de un valor dentro de un atributo específico
     * de las canciones.
     *
     * Los operadores disponibles son: 't' (busca por título) 'a' 
     * busca por artista 'g' (busca por género) 'd' (busca por duración)
     * 'r' (busca por calificación)
     * 
     * El valor es normalizado antes de realizar la búsqueda según el
     * atributo seleccionado.
     *
     * @param value el valor cuya frecuencia se desea calcular
     * @param op el carácter que indica el atributo en el que se realizará
     * la búsqueda
     * @return el número de canciones que contienen el valor indicado o
     * -1 si el valor o el operador no son válidos
     */
    public int frequency(String value, char op){
        int count = 0;
        if (value != null && !value.isEmpty()){        
            if (op == 't'){
                value = value.trim().replaceAll("\\s+", " ");
                value = value.toUpperCase();
                for (String[] song : this.songs){
                    if (song[0].equals(value)){
                        count++;
                    }
                }
            }
            else if (op == 'a'){
                value = value.trim().replaceAll("\\s+", " ");
                value = value.toUpperCase();                
                 for (String[] song : this.songs){
                    if (song[1].equals(value)){
                        count++;
                    }
                }
            }
            else if (op == 'g'){
                value = value.trim().replaceAll("\\s+", " ");
                value = value.toUpperCase();
                 for (String[] song : this.songs){
                    if (song[2] != null && song[2].equals(value)){
                        count++;
                    }
                }
            }
            else if (op == 'd'){
                value = value.replace(" ", "");
                if (value.length() == 1 && value.charAt(0) >= '1' && value.charAt(0) <= '9') {
                    for (String[] song : this.songs){
                        if (song[3] != null && song[3].equals(value)){
                            count++;
                        }
                    }
                }
                else{
                    count = -1;
                }
            }
            else if (op == 'r'){
                value = value.replace(" ", "");
                if (!(value.length()<1 || value.length() > 5)){
                    for (int j = 0; j < value.length(); j++) {
                        if (value.charAt(j) != '*') {
                            value = null;
                        }
                    }
                }
                else {
                    count = -1;
                }
                if (value != null){
                    for (String[] song : this.songs){
                        if (song[4] != null && song[4].equals(value)){
                            count++;
                        }
                    }
                }
            }
            else {
                count = -1;
            }
        }
        else{
            count= -1;
        }
        return count;
    }
    
    /**
     * Calcula la duración total de todas las canciones de la lista.
     *
     * Las canciones cuya duración es desconocida no se incluyen en el
     * cálculo.
     *
     * @return la suma de las duraciones conocidas de todas las canciones
     * de la lista
     */
    public int totalDuration(){
        int total = 0;
        for (String[] song : this.songs){
            if (song[3] != null){
                total += Integer.parseInt(song[3]); // Convertir  String a un int
            }
        }
        return total;
    }
    
    public String findSong(String nameSong){
        for (String[] song : this.songs){
            if (nameSong.equals(song[1])){
                return song[1];
            }
        }
        return null;
    }
}