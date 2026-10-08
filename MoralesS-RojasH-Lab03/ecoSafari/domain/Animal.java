package domain;
import java.util.ArrayList;

/**
 * Animal de la zona depredador-presa (cebra o león). Tiene energía,
 * puede comer, moverse sobre tierra y reproducirse.
 *
 * @author MoralesS-RojasH
 */
public abstract class Animal extends Organism implements Entity{
    
    private EcoSafari habitat;
    protected boolean acted;
    /**
     * Crea un animal y lo pone en el safari.
     *
     * @param habitat el safari donde vive
     * @param row la fila donde nace
     * @param col la columna donde nace
     */
    public Animal(EcoSafari habitat, int row, int col){
        this.habitat = habitat;
        habitat.set((Entity)this, row, col);
        acted = false;
    }
    
    /**
     * Devuelve el safari donde está el animal.
     *
     * @return el safari del animal
     */
    public EcoSafari getHabitat(){
        return this.habitat;
    }
    
    /**
     * Dice si el animal sigue vivo.
     *
     * @return true si tiene energía, false si se quedó sin ella
     */
    public boolean isAlive(){
        if (this.getEnergy()<=0){
            return false;
        }
        return true;
    }
    
    /**
     * Devuelve las posiciones de las casillas vecinas que están dentro del safari.
     *
     * @param row la fila
     * @param col la columna
     * @return lista con las posiciones {fila, columna} de las vecinas
     */
    public ArrayList<int[]> neighborsNearby(int row, int col){
        ArrayList<int[]> vecinos = new ArrayList<int[]>();
        int[][] direcciones = {{-1, 0}, {-1, 1}, {0, 1}, {1,1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        for (int[] pos : direcciones){
            int r = row + pos[0];
            int c = col + pos[1];
            if (habitat.isInside(r, c)){
                vecinos.add(new int[]{r, c});
            }
        }
        return vecinos;
    }
    
    /**
     * Busca una comida en las casillas vecinas.
     *
     * @param row la fila desde donde se mira
     * @param col la columna desde donde se mira
     * @return la posición {fila, columna} de la primera comida, o null si no hay
     */
    public int[] findFood(int row, int col){
        ArrayList<int[]> vecinos = neighborsNearby(row, col);
        for (int[] v : vecinos){
            Entity e = habitat.get(v[0], v[1]);
            if (e != null && isFood(e)){
                return v;
            }
        }
        return null;
    }
    
    /**
     * Busca una casilla de tierra entre las vecinas. Si hay varias,
     * escoge una al azar.
     *
     * @param row la fila desde donde se mira
     * @param col la columna desde donde se mira
     * @return la posición {fila, columna} de una tierra, o null si no hay
     */
    public int[] findLand(int row, int col){
        ArrayList<int[]> vecinos = neighborsNearby(row, col);
        ArrayList<int[]> tierras = new ArrayList<int[]>();
        for (int[] v : vecinos){
            if (habitat.get(v[0], v[1]) instanceof Soil){
                tierras.add(v);
            }
        }
        if (tierras.isEmpty()){
            return null;
        }
        int elegida = (int)(Math.random() * tierras.size());
        return tierras.get(elegida);
    }
    
    /**
     * Baja la energía un 10%. Siempre baja al menos 1 punto mientras
     * el animal tenga energía, para que termine muriendo.
     */
    public void loseEnergy(){
        int before = getEnergy();
        changeEnergy(-0.1f);
        if (getEnergy() == before && before > 0){
            changeEnergy(-1);
        }
    }
    
    /**
     * Se come lo que hay en una casilla: la deja como tierra y
     * el animal gana energía.
     *
     * @param row la fila de la comida
     * @param col la columna de la comida
     */
    public void eat(int row, int col){
        new Soil(habitat, row, col);
        changeEnergy(energyGain());
    }
    
    /**
     * Mueve al animal a una casilla de tierra. Intercambian posición:
     * la tierra queda donde estaba el animal.
     *
     * @param row la fila destino
     * @param col la columna destino
     * @return true si se pudo mover, false si el destino no es tierra
     */
    public boolean moveTo(int row, int col){
        int[] position = habitat.find(this);
        Entity target = habitat.get(row, col);
        boolean ok = false;
        if (position != null && target instanceof Soil){
            habitat.set(target, position[0], position[1]);
            habitat.set(this, row, col);
            ok = true;
        }
        return ok;
    }
    
    /**
     * Quita al animal del safari y deja tierra en su lugar.
     *
     * @return true si estaba en el safari, false si no
     */
    public boolean disappear(){
        int[] position = habitat.find(this);
        boolean ok = false;
        if (position != null){
            new Soil(habitat, position[0], position[1]);
            ok = true;
        }
        return ok;
    }
    
    /**
     * Intenta reproducirse. Si hay una casilla de tierra vecina que también
     * queda al lado de otro animal de la misma especie, ahí nace uno nuevo.
     *
     * @param row la fila del animal
     * @param col la columna del animal
     * @return true si nació un animal, false si no
     */
    public boolean reproduce(int row, int col){
        boolean born = false;
        ArrayList<int[]> vecinos = neighborsNearby(row, col);
        for (int i = 0; i < vecinos.size() && !born; i++){
            int[] v = vecinos.get(i);
            if (habitat.get(v[0], v[1]) instanceof Soil && hasMateNear(v[0], v[1])){
                createOffspring(v[0], v[1]);
                born = true;
            }
        }
        return born;
    }
    
    /**
     * Dice si al lado de una casilla hay otro animal de la misma especie.
     *
     * @param row la fila de la casilla
     * @param col la columna de la casilla
     * @return true si hay otro animal de la misma especie, false si no
     */
    private boolean hasMateNear(int row, int col){
        boolean found = false;
        ArrayList<int[]> vecinos = neighborsNearby(row, col);
        for (int i = 0; i < vecinos.size() && !found; i++){
            int[] v = vecinos.get(i);
            Entity e = habitat.get(v[0], v[1]);
            found = (e != null && e != this && e.getClass() == this.getClass()); //getClass Ia generativa es un metodo de todos los objetos permite saber a qué clase pertenece un objeto.
        }
        return found;
    }
    
    /**
     * Dice si una entidad es comida para este animal.
     *
     * @param e la entidad a revisar
     * @return true si es comida, false si no
     */
    public abstract boolean isFood(Entity e);
    
    /**
     * Dice cuántas casillas avanza el animal en un turno.
     *
     * @return el número de casillas
     */
    public abstract int speed();
    
    /**
     * Dice cuánta energía gana el animal al comer, como porcentaje decimal.
     *
     * @return el porcentaje (por ejemplo 0.25f)
     */
    public abstract float energyGain();
    
    /**
     * Crea un animal nuevo de la misma especie en una posición.
     *
     * @param row la fila donde nace
     * @param col la columna donde nace
     */
    public abstract void createOffspring(int row, int col);
    
    /**
     * Turno del animal: se mueve, come, muere si se quedó sin energía
     * y se reproduce. Solo actúa una vez por turno.
     */
    @Override
    public void tic(){
        if (acted){
            return;
        }
        acted = true;
        
        for (int i = 0; i < speed(); i++){
            int[] position = habitat.find(this);
            int[] land = null;
            if (position != null){
                land = findLand(position[0], position[1]);
            }
            if (land == null){
                break;
            }
            moveTo(land[0], land[1]);
            loseEnergy();
        }
        
        int[] position = habitat.find(this);
        if (position != null){
            int[] food = findFood(position[0], position[1]);
            if (food != null){
                eat(food[0], food[1]);
            }
        }
        
        if (!isAlive()){
            disappear();
            return;
        }
        
        if (position != null){
            reproduce(position[0], position[1]);
        }
    }
    
    /**
     * Deja al animal listo para actuar en el siguiente turno.
     */
    @Override
    public void tac(){
        acted = false;
    }
}