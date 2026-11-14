package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import org.junit.jupiter.api.Assumptions;
import java.util.ArrayList;
import java.util.List;

import domain.EcoSafari;
import domain.EcoSafariException;
import domain.Elephant;
import domain.Bush;
import domain.Storm;
import domain.SickElephant;
import domain.Entity;
import domain.SaltLick;
import domain.Grass;
import domain.Soil;
import domain.Lion;
import domain.Organism;
import domain.Zebra;

/**
 * Pruebas unitarias del EcoSafari: elefantes, arbustos, tormentas,
 * elefante enfermo, SaltLick, tierra/pasto, leones y cebras.
 *
 * @author MoralesS-RojasH
 */
public class EcoSafariTest
{
    private EcoSafari safari;
    
    /**
     * Crea un EcoSafari nuevo antes de cada prueba.
     */
    @BeforeEach
    public void setUp(){
        safari = new EcoSafari(); 
    }
    
    //Pruenas de los elefantes
    
    /**
     * Prueba que el elefante cambie de posición al hacer ticTac.
     */
    @Test
    public void testTicTacMuevePosicionDelElephant()
    {
        Elephant dumbo = new Elephant(safari, 5, 5);

        safari.ticTac();

        int[] posicion = safari.find(dumbo);
        assertEquals(6, posicion[0]); // fila
        assertEquals(6, posicion[1]); // columna
    }
    
    /**
     * Prueba que el elefante pierda energía al hacer ticTac.
     */
    @Test
    public void shouldLessEnergy()
    {
        Elephant dumbo = new Elephant(safari, 5, 5);
        safari.ticTac();
        assertEquals(90, dumbo.getEnergy());
    }
    
    //Pruenas de los arbusto
    /**
     * Prueba que el arbusto cambie de color con el paso del tiempo.
     */
    @Test
    public void shouldChangeColor()
    {
        Bush arbustin = new Bush (safari, 1, 1);
        assertEquals(Color.GREEN, arbustin.getColor());
        safari.ticTac();
        safari.ticTac();
        safari.ticTac();
        safari.ticTac();
        assertEquals(Color.YELLOW, arbustin.getColor());
    }
    
    /**
     * Prueba que el arbusto desaparezca cuando hay un elefante cerca.
     */
    @Test
    public void shouldDisappearWhenElephantIsNearby()
    {
        Elephant dumbo = new Elephant(safari, 5, 5);
        Bush arbustin = new Bush (safari, 5, 6);
        safari.ticTac();
        assertNull(safari.find(arbustin));
    }
    
    /**
     * Prueba que el arbusto se reproduzca con el paso del tiempo.
     */
    @Test
    public void shouldReproduce()
    {
        Bush arbustin = new Bush (safari, 2, 2);
        safari.ticTac();
        safari.ticTac();
        assertTrue(safari.get(1, 2) instanceof Bush);
        safari.ticTac();
        assertTrue(safari.get(3, 2) instanceof Bush);
    }
    
    //Pruebas de la tormenta
    /**
     * Prueba que la tormenta se mueva hacia el noreste.
     */
    @Test
    public void shouldMoveCenterNortheast()
    {
        Storm tormenta = new Storm(safari, 1, 1);
        safari.ticTac();
        assertTrue(safari.get(0, 2)instanceof Storm);
        
    }
    
    /**
     * Prueba que la tormenta sea de color negro.
     */
    @Test
    public void shouldbeBlack(){
        Storm tormenta = new Storm(safari, 1, 1);
        assertEquals(Color.BLACK, tormenta.getColor());
    }

    //Pruebas del Elefante Enfermo 
    
    /**
     * Prueba que el elefante enfermo se mueva lentamente (una casilla en dos ticTacs).
     */
    @Test 
    public void shouldMoveSlowly(){
        SickElephant rayo = new SickElephant(safari, 2, 3);
        safari.ticTac();
        safari.ticTac();
        assertTrue(safari.get(3, 4) instanceof SickElephant);
    }    

    /**
     * Prueba que el elefante enfermo pierda energía más rápido que uno sano.
     */
    @Test
    public void shouldLoseHealthFaster(){
        SickElephant rayo = new SickElephant(safari, 2, 3);
        Elephant dumbo = new Elephant(safari, 4, 6);
        safari.ticTac();
        assertEquals(80, rayo.getEnergy());
        assertTrue(rayo.getEnergy() < dumbo.getEnergy()); 
    }
    
    /**
     * Prueba que cada elefante enfermo mantenga su propia energía de forma independiente.
     */
    @Test
    public void shouldMaintainTheEnergyAndTheEnergySourcesAreIndependent(){
        SickElephant rayo = new SickElephant(safari, 15, 24);
        SickElephant casco = new SickElephant(safari, 6, 4);
        safari.ticTac();
        assertEquals(100, rayo.getEnergy());
        assertTrue(safari.get(15, 24) instanceof SickElephant);
        safari.ticTac();
        assertEquals(70, casco.getEnergy());
    }
    
    // Pruebas del SaltLick
    /**
     * Prueba que el SaltLick restaure la energía de un elefante vecino en su vecindad.
     */
    @Test
    public void shouldRestoreEnergyOfNeighborElephant()
    {
        Elephant dumbo = new Elephant(safari, 20, 4);
        dumbo.changeEnergy(-50);
        SaltLick lick = new SaltLick(safari, 20, 5);
        lick.tic();
        assertEquals(60, dumbo.getEnergy());
    }
    
    /**
     * Prueba que el SaltLick no afecte a un elefante que se encuentra fuera de su vecindad.
     */
    @Test
    public void shouldNotAffectElephantThatIsNotNeighbor()
    {
        Elephant dumbo = new Elephant(safari, 20, 4);
        dumbo.changeEnergy(-50);
        SaltLick lick = new SaltLick(safari, 20, 6);
        lick.tic();
        assertEquals(50, dumbo.getEnergy());
    }
 
    /**
     * Prueba que la restauración de energía del SaltLick no supere el límite máximo de energía del organismo (100).
     */
    @Test
    public void shouldNotExceedMaximumEnergy()
    {
        Elephant dumbo = new Elephant(safari, 20, 4);
        dumbo.changeEnergy(-5);
        SaltLick lick = new SaltLick(safari, 21, 4);
        lick.tic();
        assertEquals(100, dumbo.getEnergy());
    }
 
    /**
     * Prueba que el SaltLick permanezca estático en su posición original tras varios ciclos de simulación.
     */
    @Test
    public void shouldStayInPlace()
    {
        SaltLick lick = new SaltLick(safari, 20, 5);
        safari.ticTac();
        safari.ticTac();
        safari.ticTac();
        assertSame(lick, safari.get(20, 5));
    }
 
    /**
     * Prueba que el SaltLick devuelva el color rosa, la forma cuadrada y valide que no es un organismo vivo.
     */
    @Test
    public void shouldBePinkSquareAndNotOrganism()
    {
        SaltLick lick = new SaltLick(safari, 20, 5);
        assertEquals(Color.PINK, lick.getColor());
        assertEquals(Entity.SQUARE, lick.shape());
        assertFalse(lick.isOrganism());
    }
 
    /**
     * Prueba significativa (de aceptación): un par de SaltLicks, cesar y Juan,
     * colocados a lo largo del camino diagonal de un elefante lo mantienen con la energía al máximo
     * durante cuatro tic-tacs, mientras que un elefante idéntico sin SaltLicks (control)
     * pierde 10 puntos de energía por paso.
     */
    @Test
    public void shouldKeepElephantHealthyBetweenPairOfSaltLicks()
    {
        Elephant babar   = new Elephant(safari, 12, 1);
        Elephant control = new Elephant(safari, 11, 15);
        SaltLick cesar     = new SaltLick(safari, 14, 2);
        SaltLick juan = new SaltLick(safari, 16, 4);
        for (int i = 0; i < 4; i++){
            safari.ticTac();
        }
        assertEquals(100, babar.getEnergy());
        assertEquals(Color.DARK_GRAY, babar.getColor());
        assertEquals(60, control.getEnergy());
        assertEquals(Color.LIGHT_GRAY, control.getColor());
    }
    
    //pruebas de la tierra y el pasto
    /**
     * Pruebaque la tierra genere pasto cuando la probabilidad aleatoria es menor o igual
     * al límite permitido (en este caso, 0.05 es menor que el 10%).
     */
    @Test
    public void shouldGrowGrassWhenProbabilityAllows()
    {
        Soil soil = new Soil(safari, 20, 15){
            @Override protected boolean shouldGrowGrass(){ return true; }
        };
        soil.tic();
        assertTrue(safari.get(20, 15) instanceof Grass);
    }
 
    /**
     * Prueba que la tierra no genere pasto cuando la probabilidad aleatoria supera el límite
     * establecido (en este caso, 0.5 es mayor que el 10%).
     */
    @Test
    public void shouldNotGrowGrassWhenProbabilityDoesNotAllow()
    {
        Soil soil = new Soil(safari, 20, 15){
            @Override protected boolean shouldGrowGrass(){ return false; }
        };
        soil.tic();
        assertSame(soil, safari.get(20, 15));
    }
 
    /**
     * Prueba que al desaparecer el pasto (por ejemplo, al ser comida), la celda sea
     * reemplazada correctamente por una instancia de suelo.
     */
    @Test
    public void shouldLeaveSoilWhenGrassDisappears()
    {
        Grass grass = new Grass(safari, 20, 15);
        assertTrue(grass.disappear());
        assertTrue(safari.get(20, 15) instanceof Soil);
    }
 
    /**
     * Prueba de integración que verifica que el crecimiento de el pasto ocurra de forma normal
     * durante la ejecución de un ciclo completo de la simulación (ticTac).
     */
    @Test
    public void shouldGrowGrassDuringTicTac()
    {
        Soil soil = new Soil(safari, 20, 15){
            @Override protected boolean shouldGrowGrass(){ return true; }
        };
        safari.ticTac();
        assertTrue(safari.get(20, 15) instanceof Grass);
    }
    
    //Pruebas de leon 
    /**
     * Prueba que el león reconozca una cebra como comida.
     */
    @Test
    public void shouldRecognizeZebraAsFood()
    {
        Lion simba = new Lion(safari, 10, 10);
        Zebra rayas = new Zebra(safari, 10, 11);
        assertTrue(simba.isFood(rayas));
    }
    
    /**
     * Prueba que el león no reconozca al pasto como comida.
     */
    @Test
    public void shouldNotRecognizeGrassAsFood()
    {
        Lion simba = new Lion(safari, 10, 10);
        Grass pasto = new Grass(safari, 10, 11);
        assertFalse(simba.isFood(pasto));
    }
    
    /**
     * Prueba que el león avance una sola casilla por turno.
     */
    @Test
    public void shouldHaveSpeedOne()
    {
        Lion simba = new Lion(safari, 10, 10);
        assertEquals(1, simba.speed());
    }
    
    
    /**
     * Prueba que el león se mueva a la única tierra vecina disponible
     * y pierda 10% de energía al hacerlo.
     */
    @Test
    public void shouldMoveToOnlyLandAvailable()
    {
        Lion simba = new Lion(safari, 10, 10);
        Soil unicaTierra = new Soil(safari, 10, 11);
        // El resto de vecinas se llenan con algo que no es tierra
        Grass g1 = new Grass(safari, 9, 9);
        Grass g2 = new Grass(safari, 9, 10);
        Grass g3 = new Grass(safari, 9, 11);
        Grass g4 = new Grass(safari, 10, 9);
        Grass g5 = new Grass(safari, 11, 9);
        Grass g6 = new Grass(safari, 11, 10);
        Grass g7 = new Grass(safari, 11, 11);

        safari.ticTac();

        assertTrue(safari.get(10, 11) instanceof Lion);
        assertEquals(90, simba.getEnergy());
    }

    /**
     * Prueba que el león no se mueva si no tiene ninguna tierra vecina.
     */
    @Test
    public void shouldNotMoveWithoutLand()
    {
        Lion simba = new Lion(safari, 10, 10);
        Grass g1 = new Grass(safari, 9, 9);
        Grass g2 = new Grass(safari, 9, 10);
        Grass g3 = new Grass(safari, 9, 11);
        Grass g4 = new Grass(safari, 10, 9);
        Grass g5 = new Grass(safari, 10, 11);
        Grass g6 = new Grass(safari, 11, 9);
        Grass g7 = new Grass(safari, 11, 10);
        Grass g8 = new Grass(safari, 11, 11);

        safari.ticTac();

        assertTrue(safari.get(10, 10) instanceof Lion);
        assertEquals(100, simba.getEnergy());
    }
    
    /**
     * Prueba que el león muera y deje tierra en su lugar cuando
     * se queda sin energía.
     */
    @Test
    public void shouldDieAndLeaveSoil()
    {
        Lion simba = new Lion(safari, 10, 10);
        simba.changeEnergy(-100);
        safari.ticTac();

        assertFalse(simba.isAlive());
        assertTrue(safari.get(10, 10) instanceof Soil);
    }
    
    /**
     * Prueba que dos leones se reproduzcan cuando queda una tierra
     * a una casilla de distancia de cada uno.
     */
    @Test
    public void shouldReproduceWhenLandBetweenTwoLions()
    {
        Lion simba = new Lion(safari, 10, 10);
        Lion mufasa = new Lion(safari, 10, 12);
        Soil tierraNacimiento = new Soil(safari, 10, 11);

        simba.reproduce(10, 10);

        assertTrue(safari.get(10, 11) instanceof Lion);
    }
    
    //Pruebas de Zebra
    /**
     * Prueba que la cebra reconozca al pasto como comida.
     */
    @Test
    public void shouldRecognizeGrassAsFood()
    {
        Zebra rayas = new Zebra(safari, 10, 10);
        Grass pasto = new Grass(safari, 10, 11);
        assertTrue(rayas.isFood(pasto));
    }
    
    /**
     * Prueba que la cebra no reconozca a un león como comida.
     */
    @Test
    public void shouldNotRecognizeLionAsFood()
    {
        Zebra rayas = new Zebra(safari, 10, 10);
        Lion simba = new Lion(safari, 10, 11);
        assertFalse(rayas.isFood(simba));
    }
    
    /**
     * Prueba que la cebra avance dos casillas por turno.
     */
    @Test
    public void shouldHaveSpeedTwo()
    {
        Zebra rayas = new Zebra(safari, 10, 10);
        assertEquals(2, rayas.speed());
    }
    
    /**
     * Prueba que la cebra gane 25% de energía al comer.
     */
    @Test
    public void shouldGainTwentyFivePercentEnergy()
    {
        Zebra rayas = new Zebra(safari, 10, 10);
        assertEquals(0.25f, rayas.energyGain());
    }    

    /**
     * Prueba que la cebra no se mueva si no tiene ninguna tierra vecina.
     */
    @Test
    public void shouldNotMoveWithoutLandZebra()
    {
        Zebra rayas = new Zebra(safari, 10, 10);
        Grass g1 = new Grass(safari, 9, 9);
        Grass g2 = new Grass(safari, 9, 10);
        Grass g3 = new Grass(safari, 9, 11);
        Grass g4 = new Grass(safari, 10, 9);
        Grass g5 = new Grass(safari, 10, 11);
        Grass g6 = new Grass(safari, 11, 9);
        Grass g7 = new Grass(safari, 11, 10);
        Grass g8 = new Grass(safari, 11, 11);
    
        safari.ticTac();
    
        assertTrue(safari.get(10, 10) instanceof Zebra);
        assertEquals(100, rayas.getEnergy());
    }
    
    /**
     * Prueba que la cebra muera y deje tierra en su lugar cuando
     * se queda sin energía.
     */
    @Test
    public void shouldDieAndLeaveSoilZebra()
    {
        Zebra rayas = new Zebra(safari, 10, 10);
        rayas.changeEnergy(-100);
        safari.ticTac();

        assertFalse(rayas.isAlive());
        assertTrue(safari.get(10, 10) instanceof Soil);
    }
    
    /**
     * Prueba que dos cebras se reproduzcan cuando queda una tierra
     * a una casilla de distancia de cada una.
     */
    @Test
    public void shouldReproduceWhenLandBetweenTwoZebras()
    {
        Zebra rayas = new Zebra(safari, 10, 10);
        Zebra tigresa = new Zebra(safari, 10, 12);
        Soil tierraNacimiento = new Soil(safari, 10, 11);

        rayas.reproduce(10, 10);

        assertTrue(safari.get(10, 11) instanceof Zebra);
    }
    
    
    //Prueba de Leon
    
    /**
     * Prueba que un león recién nacido no actúe (no se mueva ni pierda
     * energía) en el mismo ticTac en que nace.
     */
    @Test
    public void shouldNotActOnBirthTicTac()
    {
        Lion simba = new Lion(safari, 10, 10);
        Lion mufasa = new Lion(safari, 10, 12);
        Soil tierraNacimiento = new Soil(safari, 10, 11);
    
        simba.reproduce(10, 10);
        Lion baby = (Lion) safari.get(10, 11);
    
        safari.ticTac();
    
        assertTrue(safari.get(10, 11) instanceof Lion);
        assertEquals(100, baby.getEnergy());
    }
    
    /**
     * Prueba que el león gane 50% de energía al comer una cebra
     * y que la cebra sea reemplazada por tierra.
     */
    @Test
    public void shouldGainFiftyPercentEnergyWhenEatingZebra()
    {
        Lion simba = new Lion(safari, 10, 10);
        simba.changeEnergy(-50); // energía en 50
        Zebra rayas = new Zebra(safari, 10, 11);
    
        simba.eat(10, 11);
    
        assertEquals(75, simba.getEnergy());
        assertTrue(safari.get(10, 11) instanceof Soil);
    }
    
    //Prueba de Zebra

    /**
     * Prueba que una cebra recién nacida no actúe (no se mueva ni pierda
     * energía) en el mismo ticTac en que nace.
     */
    @Test
    public void shouldNotActOnBirthTicTacZebra()
    {
        Zebra rayas = new Zebra(safari, 10, 10);
        Zebra tigresa = new Zebra(safari, 10, 12);
        Soil tierraNacimiento = new Soil(safari, 10, 11);
    
        rayas.reproduce(10, 10);
        Zebra baby = (Zebra) safari.get(10, 11);
    
        safari.ticTac();
    
        assertTrue(safari.get(10, 11) instanceof Zebra);
        assertEquals(100, baby.getEnergy());
    }
    
    /**
     * Prueba que la cebra gane 25% de energía al comer pasto
     * y que el pasto sea reemplazado por tierra.
     */
    @Test
    public void shouldGainTwentyFivePercentEnergyWhenEatingGrass()
    {
        Zebra rayas = new Zebra(safari, 10, 10);
        rayas.changeEnergy(-50); // energía en 50
        Grass pasto = new Grass(safari, 10, 11);
    
        rayas.eat(10, 11);
    
        assertEquals(63, rayas.getEnergy());
        assertTrue(safari.get(10, 11) instanceof Soil);
    }

    // Pruebas de saveAs

    /**
     * Entidad que no se puede serializar (tiene un atributo que no es Serializable).
     */
    private static class BadEntity implements Entity {
        private final Object noSerializable = new Object();
        public void tic() { }
        public Color getColor() { return Color.PINK; }
        public EcoSafari getHabitat() { return null; }
    }

    /**
     * Verifica que guardar en el archivo lance la excepción con el mensaje dado.
     *
     * @param file el archivo (puede ser null)
     * @param message el mensaje esperado
     */
    private void assertSaveFails(File file, String message) {
        try {
            safari.saveAs(file);
            fail("Debía lanzar EcoSafariException");
        } catch (EcoSafariException e) {
            assertEquals(message, e.getMessage());
        }
    }

    /**
     * Verifica que abrir el archivo lance la excepción con el mensaje dado.
     *
     * @param file el archivo (puede ser null)
     * @param message el mensaje esperado
     */
    private void assertOpenFails(File file, String message) {
        try {
            safari.open(file);
            fail("Debía lanzar EcoSafariException");
        } catch (EcoSafariException e) {
            assertEquals(message, e.getMessage());
        }
    }

    /** Guardar crea el archivo en el disco y no queda vacío. */
    @Test
    public void shouldSaveSafariInFile(){
        try {
            File file = File.createTempFile("safariSave", ".dat");
            file.deleteOnExit();
            safari.saveAs(file);
            assertTrue(file.exists());
            assertTrue(file.length() > 0);
        } catch (Exception e) {
            fail("No debía lanzar excepción: " + e.getMessage());
        }
    }

    /** Si no se indica archivo, no se puede guardar. */
    @Test
    public void shouldNotSaveWithNullFile(){
        assertSaveFails(null, EcoSafariException.SAVE_NULL_FILE);
    }

    /** Si el nombre corresponde a una carpeta, no se puede guardar. */
    @Test
    public void shouldNotSaveInDirectory() throws Exception {
        File folder = Files.createTempDirectory("safariFolder").toFile();
        folder.deleteOnExit();
        assertSaveFails(folder, EcoSafariException.SAVE_IS_DIRECTORY);
    }

    /** Si el archivo no tiene extensión .dat, no se puede guardar. */
    @Test
    public void shouldNotSaveWithoutDatExtension() throws Exception {
        File file = File.createTempFile("safariSave", ".txt");
        file.deleteOnExit();
        assertSaveFails(file, EcoSafariException.SAVE_BAD_EXTENSION);
    }

    /** Si la carpeta no existe, no se puede guardar. */
    @Test
    public void shouldNotSaveInMissingFolder(){
        assertSaveFails(new File("carpetaQueNoExiste/safariOne.dat"), EcoSafariException.SAVE_MISSING_FOLDER);
    }

    /** Si el archivo es de solo lectura, no se puede guardar. */
    @Test
    public void shouldNotSaveReadOnlyFile() throws Exception {
        File file = File.createTempFile("safariReadOnly", ".dat");
        file.deleteOnExit();
        file.setReadOnly();
        Assumptions.assumeFalse(file.canWrite()); // algunos entornos (administrador) ignoran el solo lectura
        try {
            assertSaveFails(file, EcoSafariException.SAVE_NO_PERMISSION);
        } finally {
            file.setWritable(true);
        }
    }

    /** Si el safari tiene un elemento que no se puede serializar, no se puede guardar. */
    @Test
    public void shouldNotSaveNotSerializableElement() throws Exception {
        safari.set(new BadEntity(), 3, 3);
        File file = File.createTempFile("safariBad", ".dat");
        file.deleteOnExit();
        assertSaveFails(file, EcoSafariException.SAVE_NOT_SERIALIZABLE);
    }

    // Pruebas de open

    /** Abrir devuelve un safari con las mismas entidades que se guardaron. */
    @Test
    public void shouldOpenSavedSafari(){
        new Elephant(safari, 10, 10);
        new Bush(safari, 15, 15);
        try {
            File file = File.createTempFile("safariOpen", ".dat");
            file.deleteOnExit();
            safari.saveAs(file);
            EcoSafari opened = safari.open(file);
            assertNotSame(safari, opened);
            assertTrue(opened.get(10, 10) instanceof Elephant);
            assertTrue(opened.get(15, 15) instanceof Bush);
            assertNull(opened.get(0, 0));
        } catch (Exception e) {
            fail("No debía lanzar excepción: " + e.getMessage());
        }
    }

    /** Abrir conserva la energía de los organismos. */
    @Test
    public void shouldOpenKeepingEnergy(){
        Elephant dumbo = new Elephant(safari, 10, 10);
        try {
            File file = File.createTempFile("safariEnergy", ".dat");
            file.deleteOnExit();
            safari.saveAs(file);
            EcoSafari opened = safari.open(file);
            Organism original = (Organism) safari.get(10, 10);
            Organism leido = (Organism) opened.get(10, 10);
            assertEquals(original.getEnergy(), leido.getEnergy());
        } catch (Exception e) {
            fail("No debía lanzar excepción: " + e.getMessage());
        }
    }

    /** Abrir conserva las casillas afectadas por tormenta. */
    @Test
    public void shouldOpenKeepingAffectedCells(){
        safari.setAffected(5, 5);
        try {
            File file = File.createTempFile("safariAffected", ".dat");
            file.deleteOnExit();
            safari.saveAs(file);
            EcoSafari opened = safari.open(file);
            assertTrue(opened.isAffected(5, 5));
            assertFalse(opened.isAffected(6, 6));
        } catch (Exception e) {
            fail("No debía lanzar excepción: " + e.getMessage());
        }
    }

    /** Si no se indica archivo, no se puede abrir. */
    @Test
    public void shouldNotOpenNullFile(){
        assertOpenFails(null, EcoSafariException.OPEN_NULL_FILE);
    }

    /** Si el nombre corresponde a una carpeta, no se puede abrir. */
    @Test
    public void shouldNotOpenDirectory() throws Exception {
        File folder = Files.createTempDirectory("safariFolder").toFile();
        folder.deleteOnExit();
        assertOpenFails(folder, EcoSafariException.OPEN_IS_DIRECTORY);
    }

    /** Si el archivo no existe, no se puede abrir. */
    @Test
    public void shouldNotOpenMissingFile(){
        assertOpenFails(new File("noExiste.dat"), EcoSafariException.OPEN_NOT_FOUND);
    }

    /** Si el archivo no tiene extensión .dat, no se puede abrir. */
    @Test
    public void shouldNotOpenWithoutDatExtension() throws Exception {
        File file = File.createTempFile("safariOpen", ".txt");
        file.deleteOnExit();
        assertOpenFails(file, EcoSafariException.OPEN_BAD_EXTENSION);
    }

    /** Si no hay permiso de lectura, no se puede abrir. */
    @Test
    public void shouldNotOpenUnreadableFile() throws Exception {
        File file = File.createTempFile("safariUnreadable", ".dat");
        file.deleteOnExit();
        file.setReadable(false);
        Assumptions.assumeFalse(file.canRead()); // algunos entornos (administrador) ignoran el permiso
        try {
            assertOpenFails(file, EcoSafariException.OPEN_NO_PERMISSION);
        } finally {
            file.setReadable(true);
        }
    }

    /** Si el archivo es de texto cualquiera, no contiene un safari. */
    @Test
    public void shouldNotOpenInvalidFile() throws Exception {
        File file = File.createTempFile("safariInvalid", ".dat");
        file.deleteOnExit();
        FileWriter writer = new FileWriter(file);
        writer.write("esto no es un safari");
        writer.close();
        assertOpenFails(file, EcoSafariException.OPEN_NOT_SAFARI);
    }

    /** Si el archivo está vacío, no contiene un safari. */
    @Test
    public void shouldNotOpenEmptyFile() throws Exception {
        File file = File.createTempFile("safariEmpty", ".dat");
        file.deleteOnExit();
        assertOpenFails(file, EcoSafariException.OPEN_NOT_SAFARI);
    }

    /** Si el archivo guarda otro tipo de objeto, no contiene un safari. */
    @Test
    public void shouldNotOpenFileWithOtherObject() throws Exception {
        File file = File.createTempFile("safariOther", ".dat");
        file.deleteOnExit();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject("soy un texto, no un safari");
        out.close();
        assertOpenFails(file, EcoSafariException.OPEN_NOT_SAFARI);
    }

    // Pruebas de exportAs

    /**
     * Lee todas las líneas de un archivo de texto.
     *
     * @param file el archivo a leer
     * @return las líneas del archivo
     */
    private List<String> readLines(File file) throws Exception {
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        reader.close();
        return lines;
    }

    /** Exportar escribe una línea por entidad con su clase y su posición. */
    @Test
    public void shouldExportEntitiesWithTypeAndPosition(){
        new Elephant(safari, 10, 20);
        new Bush(safari, 15, 15);
        try {
            File file = File.createTempFile("safariExport", ".txt");
            file.deleteOnExit();
            safari.exportAs(file);
            List<String> lines = readLines(file);
            assertEquals(2, lines.size());
            assertEquals("Elephant 10, 20", lines.get(0));
            assertEquals("Bush 15, 15", lines.get(1));
        } catch (Exception e) {
            fail("No debía lanzar excepción: " + e.getMessage());
        }
    }

    /** Un safari vacío se exporta como un archivo sin líneas. */
    @Test
    public void shouldExportEmptySafariWithoutLines(){
        try {
            File file = File.createTempFile("safariEmpty", ".txt");
            file.deleteOnExit();
            safari.exportAs(file);
            assertEquals(0, readLines(file).size());
        } catch (Exception e) {
            fail("No debía lanzar excepción: " + e.getMessage());
        }
    }

    /**
     * Verifica que exportar al archivo lance la excepción con el mensaje dado.
     *
     * @param file el archivo (puede ser null)
     * @param message el mensaje esperado
     */
    private void assertExportFails(File file, String message) {
        try {
            safari.exportAs(file);
            fail("Debía lanzar EcoSafariException");
        } catch (EcoSafariException e) {
            assertEquals(message, e.getMessage());
        }
    }

    /** Si no se indica archivo, no se puede exportar. */
    @Test
    public void shouldNotExportNullFile(){
        assertExportFails(null, EcoSafariException.EXPORT_NULL_FILE);
    }

    /** Si el nombre corresponde a una carpeta, no se puede exportar. */
    @Test
    public void shouldNotExportInDirectory() throws Exception {
        File folder = Files.createTempDirectory("safariFolder").toFile();
        folder.deleteOnExit();
        assertExportFails(folder, EcoSafariException.EXPORT_IS_DIRECTORY);
    }

    /** Si el archivo no tiene extensión .txt, no se puede exportar. */
    @Test
    public void shouldNotExportWithoutTxtExtension() throws Exception {
        File file = File.createTempFile("safariExport", ".dat");
        file.deleteOnExit();
        assertExportFails(file, EcoSafariException.EXPORT_BAD_EXTENSION);
    }

    /** Si la carpeta no existe, no se puede exportar. */
    @Test
    public void shouldNotExportInMissingFolder(){
        assertExportFails(new File("carpetaQueNoExiste/safariOne.txt"), EcoSafariException.EXPORT_MISSING_FOLDER);
    }

    /** Si el archivo es de solo lectura, no se puede exportar. */
    @Test
    public void shouldNotExportReadOnlyFile() throws Exception {
        File file = File.createTempFile("safariReadOnly", ".txt");
        file.deleteOnExit();
        file.setReadOnly();
        Assumptions.assumeFalse(file.canWrite()); // algunos entornos (administrador) ignoran el solo lectura
        try {
            assertExportFails(file, EcoSafariException.EXPORT_NO_PERMISSION);
        } finally {
            file.setWritable(true);
        }
    }

    /**
     * Crea un archivo de texto temporal con un contenido.
     *
     * @param content el texto del archivo
     * @return el archivo creado
     */
    private File writeTextFile(String content) throws Exception {
        File file = File.createTempFile("safariText", ".txt");
        file.deleteOnExit();
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
        return file;
    }

    // Pruebas de import_

    /** Importar crea cada entidad en su posición. */
    @Test
    public void shouldImportEntitiesFromFile(){
        try {
            File file = writeTextFile("Elephant 10, 20\nBush 15, 15\nStorm 5, 5\n");
            safari.import_(file);
            assertTrue(safari.get(10, 20) instanceof Elephant);
            assertTrue(safari.get(15, 15) instanceof Bush);
            assertTrue(safari.get(5, 5) instanceof Storm);
        } catch (Exception e) {
            fail("No debía lanzar excepción: " + e.getMessage());
        }
    }

    /** Importar ignora espacios de más y líneas en blanco. */
    @Test
    public void shouldImportIgnoringSpacesAndBlankLines(){
        try {
            File file = writeTextFile("  Elephant   10 ,20  \n\n   \nBush 15,15\n");
            safari.import_(file);
            assertTrue(safari.get(10, 20) instanceof Elephant);
            assertTrue(safari.get(15, 15) instanceof Bush);
        } catch (Exception e) {
            fail("No debía lanzar excepción: " + e.getMessage());
        }
    }

    /** Lo exportado se puede importar en otro safari. */
    @Test
    public void shouldImportWhatWasExported(){
        new Elephant(safari, 10, 20);
        new Bush(safari, 15, 15);
        try {
            File file = File.createTempFile("safariRound", ".txt");
            file.deleteOnExit();
            safari.exportAs(file);
            EcoSafari other = new EcoSafari();
            other.import_(file);
            assertTrue(other.get(10, 20) instanceof Elephant);
            assertTrue(other.get(15, 15) instanceof Bush);
            assertNull(other.get(0, 0));
        } catch (Exception e) {
            fail("No debía lanzar excepción: " + e.getMessage());
        }
    }

    /**
     * Verifica que importar el archivo lance la excepción con el mensaje dado.
     *
     * @param file el archivo (puede ser null)
     * @param message el mensaje esperado
     */
    private void assertImportFails(File file, String message) {
        try {
            safari.import_(file);
            fail("Debía lanzar EcoSafariException");
        } catch (EcoSafariException e) {
            assertTrue(e.getMessage().contains(message));
        }
    }

    /** Si no se indica archivo, no se puede importar. */
    @Test
    public void shouldNotImportNullFile(){
        assertImportFails(null, EcoSafariException.IMPORT_NULL_FILE);
    }

    /** Si el nombre corresponde a una carpeta, no se puede importar. */
    @Test
    public void shouldNotImportDirectory() throws Exception {
        File folder = Files.createTempDirectory("safariFolder").toFile();
        folder.deleteOnExit();
        assertImportFails(folder, EcoSafariException.IMPORT_IS_DIRECTORY);
    }

    /** Si el archivo no existe, no se puede importar. */
    @Test
    public void shouldNotImportMissingFile(){
        assertImportFails(new File("noExiste.txt"), EcoSafariException.IMPORT_NOT_FOUND);
    }

    /** Si el archivo no tiene extensión .txt, no se puede importar. */
    @Test
    public void shouldNotImportWithoutTxtExtension() throws Exception {
        File file = File.createTempFile("safariImport", ".dat");
        file.deleteOnExit();
        assertImportFails(file, EcoSafariException.IMPORT_BAD_EXTENSION);
    }

    /** Si no hay permiso de lectura, no se puede importar. */
    @Test
    public void shouldNotImportUnreadableFile() throws Exception {
        File file = writeTextFile("Elephant 10, 20\n");
        file.setReadable(false);
        Assumptions.assumeFalse(file.canRead()); // algunos entornos (administrador) ignoran el permiso
        try {
            assertImportFails(file, EcoSafariException.IMPORT_NO_PERMISSION);
        } finally {
            file.setReadable(true);
        }
    }

    /** Una clase que no existe en el safari no se puede importar. */
    @Test
    public void shouldNotImportUnknownClass() throws Exception {
        assertImportFails(writeTextFile("Dragon 1, 1\n"), EcoSafariException.IMPORT_UNKNOWN_CLASS);
    }

    /** Una posición que no es un número no se puede importar. */
    @Test
    public void shouldNotImportInvalidNumber() throws Exception {
        assertImportFails(writeTextFile("Elephant 1, x\n"), EcoSafariException.IMPORT_INVALID_NUMBER);
    }

    /** Una línea sin la columna tiene formato incorrecto. */
    @Test
    public void shouldNotImportMissingColumn() throws Exception {
        assertImportFails(writeTextFile("Elephant 10\n"), EcoSafariException.IMPORT_BAD_FORMAT);
    }

    /** Una línea sin posición tiene formato incorrecto. */
    @Test
    public void shouldNotImportMissingPosition() throws Exception {
        assertImportFails(writeTextFile("Elephant\n"), EcoSafariException.IMPORT_BAD_FORMAT);
    }

    /** Una línea con más de dos números tiene formato incorrecto. */
    @Test
    public void shouldNotImportTooManyNumbers() throws Exception {
        assertImportFails(writeTextFile("Elephant 1, 2, 3\n"), EcoSafariException.IMPORT_BAD_FORMAT);
    }

    /** Una posición fuera del safari no se puede importar. */
    @Test
    public void shouldNotImportPositionOutsideSafari() throws Exception {
        assertImportFails(writeTextFile("Elephant 30, 30\n"), EcoSafariException.IMPORT_OUT_OF_SAFARI);
    }

    /** Importar informa la línea, la palabra y la causa de cada error, y no importa nada. */
    @Test
    public void shouldReportLineAndWordOfEachError() throws Exception {
        File file = writeTextFile("Elephant 1, 1\nDragon 2, 2\nBush 3, x\nStorm 40, 40\n");
        try {
            safari.import_(file);
            fail("Debía lanzar EcoSafariException");
        } catch (EcoSafariException e) {
            String m = e.getMessage();
            assertTrue(m.contains("3 errores"));
            assertTrue(m.contains("Línea 2: \"Dragon\""));
            assertTrue(m.contains("Línea 3: \"x\""));
            assertTrue(m.contains("Línea 4: \"40, 40\""));
            assertFalse(m.contains("Línea 1"));
        }
        assertNull(safari.get(1, 1));
    }

    /**
     * Entidad nueva, creada solo para probar que importar sirve con cualquier clase.
     */
    public static class Hippo implements Entity {
        private final EcoSafari habitat;
        public Hippo(EcoSafari habitat, int row, int column) {
            this.habitat = habitat;
            habitat.set(this, row, column);
        }
        public void tic() { }
        public Color getColor() { return Color.PINK; }
        public EcoSafari getHabitat() { return habitat; }
    }

    /** Importar sirve con una clase nueva, sin cambiar import_ (nombre completo). */
    @Test
    public void shouldImportNewEntityClass() throws Exception {
        safari.import_(writeTextFile("test.EcoSafariTest$Hippo 4, 4\n"));
        assertTrue(safari.get(4, 4) instanceof Hippo);
    }

    /** Importar sirve con todas las clases actuales. */
    @Test
    public void shouldImportAllCurrentClasses() throws Exception {
        String[] names = {"Elephant", "SickElephant", "Bush", "Storm", "SaltLick", "Grass", "Soil", "Zebra", "Lion"};
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            text.append(names[i] + " " + i + ", " + i + "\n");
        }
        safari.import_(writeTextFile(text.toString()));
        for (int i = 0; i < names.length; i++) {
            assertEquals(names[i], safari.get(i, i).getClass().getSimpleName());
        }
    }

    /** Una clase que existe pero no es una entidad no se puede importar. */
    @Test
    public void shouldNotImportClassThatIsNotEntity() throws Exception {
        assertImportFails(writeTextFile("EcoSafariException 1, 1\n"), EcoSafariException.IMPORT_NOT_ENTITY);
        assertImportFails(writeTextFile("Organism 1, 1\n"), EcoSafariException.IMPORT_NOT_ENTITY);
    }

    /** Una clase abstracta o una interfaz no se puede importar. */
    @Test
    public void shouldNotImportAbstractClass() throws Exception {
        assertImportFails(writeTextFile("Animal 1, 1\n"), EcoSafariException.IMPORT_CANNOT_CREATE);
        assertImportFails(writeTextFile("Entity 1, 1\n"), EcoSafariException.IMPORT_CANNOT_CREATE);
    }

    /** El nombre de la clase distingue mayúsculas de minúsculas. */
    @Test
    public void shouldNotImportClassWithWrongCase() throws Exception {
        assertImportFails(writeTextFile("elephant 1, 1\n"), EcoSafariException.IMPORT_UNKNOWN_CLASS);
    }


    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}