package domain;
import java.util.Random;
/**
 * Safari de 25x25 casillas donde viven las entidades (elefantes, arbustos y tormentas).
 * Guarda dónde está cada una y qué casillas están afectadas por una tormenta.
 *
 * @author MoralesS-RojasH
 */
public class EcoSafari{
 
    private static final int SIZE=25;
    private Random random = new Random();
    private Entity[][] cells;
    private boolean[][] affected;
    
    /**
     * Crea un safari nuevo con algunas entidades ya puestas.
     */
    public EcoSafari() {
        cells=new Entity[SIZE][SIZE];
        affected = new boolean[SIZE][SIZE];
        someEntities();
    }
    
    public EcoSafari(boolean empty) {
        cells = new Entity[SIZE][SIZE];
        affected = new boolean[SIZE][SIZE];
        if (!empty) {
            someEntities();
        }
    }
    
    /**
     * Llena el safari con todas las entidades de ejemplo (elefantes, arbustos,
     * tormentas, elefantes enfermos, saltlicks, cebras y leones). La zona de
     * tierra (pasto, cebras, leones) solo ocupa una región de la cuadrícula,
     * dejando el resto vacío para que elefantes y tormentas se puedan mover.
     */
    public void someEntities(){
        Elephant dumbo = new Elephant(this, 3, 5);
        Elephant babar = new Elephant(this, 10, 10);
        Bush mopane = new Bush(this, 2, 2);
        Bush acacia = new Bush(this, 12, 6);
        Storm thor = new Storm(this, 3, 4);
        Storm tempest = new Storm(this, 7, 8);
        SickElephant cesar = new SickElephant(this, 9, 10);
        SickElephant juan = new SickElephant(this, 12, 3);
        SickElephant rayo = new SickElephant(this, 15, 24);
        SickElephant casco = new SickElephant(this, 6, 4);
        SaltLick cesarS = new SaltLick(this, 12, 11);
        SaltLick juanS = new SaltLick(this, 14, 13);
    
        // Zona de tierra: filas 18 a 24 (las últimas 7 filas de la cuadrícula)
        int zonaTierraDesde = 18;
    
        Grass pastoA = new Grass(this, 18, 2);
        Grass pastoB = new Grass(this, 20, 6);
        Grass pastoC = new Grass(this, 22, 1);
    
        Zebra cebra1 = new Zebra(this, 19, 1);
        Zebra cebra2 = new Zebra(this, 21, 4);
    
        Lion leon1 = new Lion(this, 23, 8);
        Lion leon2 = new Lion(this, 20, 12);
    
        for (int r = zonaTierraDesde; r < getSize(); r++){
            for (int c = 0; c < getSize(); c++){
                if (get(r, c) == null){
                    new Soil(this, r, c);
                }
            }
        }
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
}