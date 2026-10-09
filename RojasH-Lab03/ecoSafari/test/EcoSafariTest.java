package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import domain.EcoSafari;
import domain.Elephant;
import domain.Bush;
import domain.Storm;
import domain.SickElephant;
import domain.Entity;
import domain.SaltLick;
import domain.Grass;
import domain.Soil;
import domain.Lion;
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
        safari = new EcoSafari(true); 
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