

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MiniTunesTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class MiniTunesTest
{
    /**
     * Default constructor for test class MiniTunesTest
     */
    
    private MiniTunes miniTunes;
    public MiniTunesTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
       miniTunes = new MiniTunes();
    }
    
    @Test
    public void shouldCreatePlaylist(){
        miniTunes.define("Rock");
        assertTrue(miniTunes.ok());
        assertEquals(0, miniTunes.size("Rock"));
    }
    
    @Test 
    public void shouldNotCreateInvalidPlaylist(){
        miniTunes.define("Rock");
        assertTrue(miniTunes.ok());
    
        miniTunes.define(" RoCK  ");
        assertFalse(miniTunes.ok());
    
        miniTunes.define("     ");
        assertFalse(miniTunes.ok());
    
        miniTunes.define(null);
        assertFalse(miniTunes.ok());
    }
    
    @Test
    public void shouldAssignPlaylist(){
        miniTunes.define("  pop   latino  ");
        String[][] popLatino = {
            {"Provenza", "Karol G", "Pop Latino", "3", "****"},
            {"Tusa", "Karol G", "Pop Latino", null, "**"},
            {"Robarte un Beso", "Carlos Vives", "Pop Latino", "3", null},
            {"Vivir Mi Vida", "Marc Anthony", null, "5", "*****"},
            {"La Bicicleta", "Carlos Vives", "Pop Latino", null, "***"},
            {"Échame la Culpa", "Luis Fonsi", null, "3", "****"},
            {"Me Enamora", "Juanes", "Pop Latino", null, "*****"}
        };
        miniTunes.assign("   pop latino", popLatino);
        assertTrue(miniTunes.ok());
    }
    
    @Test 
    public void shouldNotAssignInvalidPlaylist(){
        String[][] popLatino = {
            {"Provenza", "Karol G", "Pop Latino", "3", "****"},
            {"Tusa", "Karol G", "Pop Latino", null, "**"},
            {"Robarte un Beso", "Carlos Vives", "Pop Latino", "3", null},
            {"Vivir Mi Vida", "Marc Anthony", null, "5", "*****"},
            {"La Bicicleta", "Carlos Vives", "Pop Latino", null, "***"},
            {"Échame la Culpa", "Luis Fonsi", null, "3", "****"},
            {"Me Enamora", "Juanes", "Pop Latino", null, "*****"}
        };
        miniTunes.assign("POP LATINO", popLatino);
        assertFalse(miniTunes.ok());
        miniTunes.assign("POP LATINO", null);
        assertFalse(miniTunes.ok());        
    }
    
    @Test
    public void shouldReturnPlaylistSize(){
        assertEquals(-1, miniTunes.size("pop    latino"));
        miniTunes.define("  pop   latino  ");
        assertEquals(0, miniTunes.size("pop latino"));
        String[][] popLatino = {
            {"Provenza", "Karol G", "Pop Latino", "3", "****"},
            {"Tusa", "Karol G", "Pop Latino", null, "**"},
            {"Robarte un Beso", "Carlos Vives", "Pop Latino", "3", null},
            {"Vivir Mi Vida", "Marc Anthony", null, "5", "*****"},
            {"La Bicicleta", "Carlos Vives", "Pop Latino", null, "***"},
            {"Échame la Culpa", "Luis Fonsi", null, "3", "****"},
            {"Me Enamora", "Juanes", "Pop Latino", null, "*****"}
        };
        miniTunes.assign("POP LATINO", popLatino);
        assertEquals(7, miniTunes.size("    pop   latino"));
    }
    
    @Test
    public void shouldReturnPlaylistNamesAlphabetically(){
        miniTunes.define("POP   LATINO");
        miniTunes.define("musica   Llanera ");
        miniTunes.define("   Vallenato");
        String[][] popLatino = {
            {"Provenza", "Karol G", "Pop Latino", "3", "****"},
            {"Tusa", "Karol G", "Pop Latino", null, "**"},
            {"Robarte un Beso", "Carlos Vives", "Pop Latino", "3", null},
        };
        String[][] vallenatos = {
            {"La Gota Fría", "Carlos Vives", "Vallenato", "5", null},
            {"Obsesión", "Peter Manjarrés", "Vallenato", "4", "***"},
            {"Volví a Llorar", "Binomio de Oro", "Vallenato", null, "****"}
        };
        String[][] llanera = {
            {"Carmentea", "Miguel Ángel Martín", "Música Llanera", "4", "*"},
            {"El Llanero", "Juan Farfán", "Música Llanera", null, "****"},
            {"Llanura", "Reynaldo Armas", null, "5", "***"}
        };
        miniTunes.assign("POP LATINO", popLatino); 
        miniTunes.assign("musica LLANERA", llanera);
        miniTunes.assign("   vallenato   ", vallenatos);
        assertEquals("MUSICA LLANERA, POP LATINO, VALLENATO", miniTunes.toString());
    }
    
    @Test
    public void shouldReturnEmptyStringWhenThereAreNoPlaylists(){
        assertEquals("", miniTunes.toString());
    }
    
    @Test
    public void shouldReturnPlaylistSongs(){
        miniTunes.define("POP   LATINO");
    
        String[][] popLatino = {
            {"Provenza", "Karol G", "Pop Latino", "3", "****"},
            {"Tusa", "Karol G", "Pop Latino", null, "**"},
            {"Robarte un Beso", "Carlos Vives", "Pop Latino", "3", null},
        };
    
        miniTunes.assign("POP LAtiNO", popLatino);
        String expected =
            "TITLE             ARTIST         GENRE        DURATION   RATING   \n" +
            "PROVENZA          KAROL G        POP LATINO          3   ****     \n" +
            "TUSA              KAROL G        POP LATINO              **       \n" +
            "ROBARTE UN BESO   CARLOS VIVES   POP LATINO          3            \n";
        assertEquals(expected, miniTunes.toString("POP LATINO"));
        assertTrue(miniTunes.ok());
    }  
    
    @Test
    public void shouldNotReturnSongsForNonexistentPlaylist(){
        assertEquals("", miniTunes.toString("POP LATINO"));
        assertEquals("", miniTunes.toString(null));
    }
    
    @Test 
    public void shouldDeleteSongs(){
        miniTunes.define("POP   LATINO");
        miniTunes.define(" favoritas  ");
        
        String[][] popLatino = {
            {"Provenza", "Karol G", "Pop Latino", "3", "****"},
            {"Tusa", "Karol G", "Pop Latino", null, "**"},
            {"Robarte un Beso", "Carlos Vives", "Pop Latino", "3", null},
        };
        String[] values = {"Provenza", "Karol G"};
        miniTunes.assign("POP LAtiNO", popLatino);
        miniTunes.assignUnary("favoritas   ", "POP LAtiNO", 'd', values);
        assertEquals(3, miniTunes.size("POP LATINO"));
        assertEquals(2, miniTunes.size("favoritas"));
    }
    
    @Test
    public void shouldSelectSongs(){
        miniTunes.define("MusIca LLanera");
        miniTunes.define(" favoritas  ");
        
        String[][] llanera = {
            {"Carmentea", "Miguel Ángel Martín", "Música Llanera", "4", "*"},
            {"El Llanero", "Juan Farfán", "Música Llanera", null, "****"},
            {"Llanura", "Reynaldo Armas", null, "5", "***"},
            {"La Muerte del Rucio Moro", "Reynaldo Armas", "Música Llanera", "6", "*****"},
            {"Laguna Vieja", "Reynaldo Armas", "Música Llanera", null, "****"}
        };
        String[] values = {null, "Reynaldo Armas"};
        miniTunes.assign("MusIca LLanera", llanera);
        miniTunes.assignUnary("favoritas   ", "MusIca LLanera", 's', values);
        assertEquals(3, miniTunes.size("favoritas"));
    }
    
    @Test 
    public void shouldAddSongs(){
        miniTunes.define("vallenatos ");
        miniTunes.define("vallenatos completa  ");
        String[][] vallenatos = {
            {"La Gota Fría", "Carlos Vives", "Vallenato", "5", null},
            {"Obsesión", "Peter Manjarrés", "Vallenato", "4", "***"},
            {"Volví a Llorar", "Binomio de Oro", "Vallenato", null, "****"}
        };
        miniTunes.assign("vallenatos", vallenatos);
        String[] values = {"Hoja en Blanco", "Binomio de Oro", "Vallenato"};
        miniTunes.assignUnary("vallenatos completa", "vallenatos", 'a', values);
        assertEquals(4, miniTunes.size("vallenatos completa"));
    }
    
    @Test
    public void shouldimplementassignBinary(){
        miniTunes.define("   rock ");
        miniTunes.define("rockLatino   ");
        String[][] rock = {
            {"De Música Ligera", "Soda Stereo", "Rock", "4", "*****"},
            {"Persiana Americana", "Soda Stereo", "Rock", "5", "****"},
            {"Lamento Boliviano", "Enanitos Verdes", "Rock", null, "***"},
            {"Flaca", "Andrés Calamaro", "Rock", "4", "****"}
        };
        String[][] rockLatino = {
            {"De Música Ligera", "Soda Stereo", "Rock", "4", "*****"},
            {"Persiana Americana", "Soda Stereo", "Rock", "5", "****"},
            {"Eres", "Café Tacvba", "Rock", "4", "*****"},
            {"Rayando el Sol", "Maná", "Rock", null, "***"}
        };
        miniTunes.assign("rock", rock);
        miniTunes.assign("rockLatino", rockLatino);
        miniTunes.define("result"); 
        miniTunes.assignBinary("result", "rock", 'u', "rockLatino");
        assertEquals(6, miniTunes.size("result"));
        miniTunes.assignBinary("result", "rock", 'i', "rockLatino");
        assertEquals(2, miniTunes.size("result"));
        miniTunes.assignBinary("result", "rock", 'd', "rockLatino");
        assertEquals(2, miniTunes.size("result"));
    }
    
    @Test
    public void shouldNotUnionWithNonexistentPlaylist(){
        String[][] rock = {
            {"De Música Ligera", "Soda Stereo", "Rock", "4", "*****"},
            {"Persiana Americana", "Soda Stereo", "Rock", "5", "****"},
            {"Lamento Boliviano", "Enanitos Verdes", "Rock", null, "***"},
            {"Flaca", "Andrés Calamaro", "Rock", "4", "****"}
        };
        miniTunes.assign("rock", rock);
        miniTunes.define("result");
        miniTunes.assignBinary("result", "rock", 'u', "rockLatino");
        assertFalse(miniTunes.ok());
    }
    
    @Test 
    public void shouldOrderPlaylistByRating(){
        miniTunes.define("  pop   latino  ");
        String[][] popLatino = {
            {"Provenza", "Karol G", "Pop Latino", "3", "****"},
            {"Tusa", "Karol G", "Pop Latino", null, "**"},
            {"Robarte un Beso", "Carlos Vives", "Pop Latino", "3", null},
            {"Vivir Mi Vida", "Marc Anthony", null, "5", "*****"},
            {"La Bicicleta", "Carlos Vives", "Pop Latino", null, "***"},
            {"Échame la Culpa", "Luis Fonsi", null, "3", "****"},
            {"Me Enamora", "Juanes", "Pop Latino", null, "*****"}
        };
        miniTunes.assign("pop latino  ", popLatino);
        miniTunes.sort("pop latino", 'r');
        String expected =
            "TITLE             ARTIST         GENRE        DURATION   RATING   \n" +
            "VIVIR MI VIDA     MARC ANTHONY                       5   *****    \n" +
            "ME ENAMORA        JUANES         POP LATINO              *****    \n" +
            "PROVENZA          KAROL G        POP LATINO          3   ****     \n" +
            "ÉCHAME LA CULPA   LUIS FONSI                         3   ****     \n" +
            "LA BICICLETA      CARLOS VIVES   POP LATINO              ***      \n" +
            "TUSA              KAROL G        POP LATINO              **       \n" +
            "ROBARTE UN BESO   CARLOS VIVES   POP LATINO          3            \n";       
        assertEquals(expected, miniTunes.toString("pop latino"));
    }

    @Test
    public void shouldReturnMinusOneAndCeroForInvalidFrequencyOperation(){
        assertEquals(-1, miniTunes.frequency("Musica en ingles  ", "3", 'd'));
        miniTunes.define("musiCa EN INGLes ");
        assertEquals(0, miniTunes.frequency("Musica en ingles  ", "3", 'd')); 
    }
    
    @Test 
    public void shouldReturnTotalDuration(){
        miniTunes.define("  pop   latino  ");
        String[][] popLatino = {
            {"Provenza", "Karol G", "Pop Latino", "3", "****"},
            {"Tusa", "Karol G", "Pop Latino", null, "**"},
            {"Robarte un Beso", "Carlos Vives", "Pop Latino", "3", null},
            {"Vivir Mi Vida", "Marc Anthony", null, "5", "*****"},
            {"La Bicicleta", "Carlos Vives", "Pop Latino", null, "***"},
            {"Échame la Culpa", "Luis Fonsi", null, "3", "****"},
            {"Me Enamora", "Juanes", "Pop Latino", null, "*****"}
        };
        miniTunes.assign("pop latino  ", popLatino);
        assertEquals(14, miniTunes.totalDuration("pop latino"));
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