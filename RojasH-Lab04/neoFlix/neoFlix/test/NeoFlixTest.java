package test;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;

/**
 * The test class NeoFlixTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class NeoFlixTest
{
    private NeoFlix n;

    /**
     * Default constructor for test class NeoFlixTest
     */
    public NeoFlixTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp(){
        n = new NeoFlix();
    }
    // Adicionar
    
    @Test
    public void shouldAddAnEpisode() throws NeoFlixException{
        n.addEpisode("prueba", "?", "100", "60", "50", "354");
        assertEquals(7, n.numberContents());
    }
    
    @Test
    public void shouldConsultAnAddedEpisode() throws NeoFlixException{
        n.addEpisode("prueba", "?", "100", "60", "50", "354");
        assertNotNull(n.consult("prueba"));
        assertEquals("prueba", n.consult("prueba").getTitle());
    }
    
    @Test
    public void shouldAddASeries() throws NeoFlixException{
        n.addSeries("Serie Nueva", "2021", "The Rival\nThe Tournament");
        assertEquals(7, n.numberContents());
    }
    
    @Test
    public void shouldConsultAnAddedSeries() throws NeoFlixException {
        n.addSeries("Serie Nueva", "2021", "The Rival\nThe Tournament");
        assertNotNull(n.consult("Serie Nueva"));
        assertEquals("Serie Nueva", n.consult("Serie Nueva").getTitle());
    }
    
    
    // Listar
    @Test
    public void shouldListTheNumberOfContents(){
        assertTrue(n.toString().startsWith("6 elementos"));
    }
        
    @Test
    public void shouldListAnInitialEpisode(){
        assertTrue(n.toString().contains(">The Hidden Village"));
    }
    
    @Test
    public void shouldListTheInitialSeriesWithItsYear(){
        assertTrue(n.toString().contains(">The Neo Ninja: 2020"));
    }
    
    @Test
    public void shouldListAnAddedEpisode() throws NeoFlixException{
        n.addEpisode("prueba", "?", "100", "60", "50", "354");
        assertTrue(n.toString().startsWith("7 elementos"));
        assertTrue(n.toString().contains(">prueba"));
    }
    
    @Test
    public void shouldNotShowIndicatorsWhenListing(){
        assertFalse(n.toString().contains("("));   // los indicadores van entre paréntesis
    }
    
    
    // Adicionar robusta
    @Test
    public void shouldAddAnEpisodeWithBoundaryValues() throws NeoFlixException {
        n.addEpisode("limite", "?", "10", "10", "5", "50");   // terminados=intentos, suma=10*votos
        assertEquals(7, n.numberContents());
    }

    // (i) Nombre repetido
    @Test
    public void shouldNotAddAnEpisodeWithAnExistingName() {
        try {
            n.addEpisode("The Hidden Village", "?", "100", "60", "50", "354");
            fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.NAME_EXISTS, e.getMessage());
        }
        assertEquals(6, n.numberContents());
    }

    @Test
    public void shouldNotAddASeriesWithAnExistingName() {
        try {
            n.addSeries("the neo ninja", "2021", "The Rival");
            fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.NAME_EXISTS, e.getMessage());
        }
        assertEquals(6, n.numberContents());
    }

    // (ii) Valores no numéricos
    @Test
    public void shouldNotAddAnEpisodeWhenAttemptsIsNotANumber() {
        try {
            n.addEpisode("Nuevo", "?", "abc", "60", "50", "354");
            fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.NUMBER_FORMAT, e.getMessage());
        }
        assertEquals(6, n.numberContents());
    }

    @Test
    public void shouldNotAddASeriesWhenYearIsNotANumber() {
        try {
            n.addSeries("Serie Nueva", "dos mil", "The Rival");
            fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.NUMBER_FORMAT, e.getMessage());
        }
        assertEquals(6, n.numberContents());
    }

    // (iii) Valores incoherentes
    @Test
    public void shouldNotAddAnEpisodeWithMoreCompletedThanAttempts() {
        try {
            n.addEpisode("Malo", "?", "10", "20", "2", "15");
            fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.DATA_ERROR, e.getMessage());
        }
        assertEquals(6, n.numberContents());
    }

    @Test
    public void shouldNotAddAnEpisodeWithSumOfVotesAboveTenTimesVotes() {
        try {
            n.addEpisode("Malo", "?", "10", "5", "2", "50");
            fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.DATA_ERROR, e.getMessage());
        }
        assertEquals(6, n.numberContents());
    }

    // (iv) Episodio inexistente
    @Test
    public void shouldNotAddASeriesWithAnUnknownEpisode() {
        try {
            n.addSeries("The Ninja Returns", "2021", "The Hidden Village\nEpisodio Fantasma");
            fail("Did not throw exception");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.EPISODE_NOT_FOUND, e.getMessage());
        }
        assertEquals(6, n.numberContents());
        assertNull(n.consult("The Ninja Returns"));
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