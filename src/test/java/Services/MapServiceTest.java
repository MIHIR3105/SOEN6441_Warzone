package Services;

import Controllers.GameEngine;
import Models.*;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for MapService Class
 */
public class MapServiceTest {

    /**
<<<<<<< HEAD
     * MapService reference to store its object.
     */
    MapService d_mapservice;

    /**
     * Map reference to store its object.
     */
    Map d_map;

    /**
     * GameState reference to store its object.
     */
    GameState d_state;

    /**
     * Setup before each MapService Operations
     *
     * @throws InvalidMap Invalid map exception
     */
    @Before
    public void setup() throws InvalidMap {
        d_mapservice = new MapService();
        d_map = new Map();
        d_state = new GameState();
        d_map = d_mapservice.loadMap(d_state, "europe.map");
    }

    /**
     * This test case is used to test the functionality of EditMap function.
     *
     * @throws IOException throws IOException
     * @throws InvalidMap  Invalid map exception
=======
     * Test Method to test the editmap method
>>>>>>> 632436d8b4a66a40ae3a7f0dcb51f98065caeab7
     */
    @Test
    void editMapTest() {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);

<<<<<<< HEAD
        assertTrue(l_file.exists());
    }

    /**
     * tests addition of continent via editcontinent operation
     *
     * @throws IOException    Exceptions
     * @throws InvalidMap     Exception
     * @throws InvalidCommand Exception
     */
    @Test
    public void testEditContinentAdd() throws IOException, InvalidMap, InvalidCommand {
        d_state.setD_map(new Map());
        Map l_updatedContinents = d_mapservice.addOrRemoveContinents(d_state, d_state.getD_map(), "Add", "Asia 10");

        assertEquals(l_updatedContinents.getD_continents().size(), 1);
        assertEquals(l_updatedContinents.getD_continents().get(0).getD_continentName(), "Asia");
        assertEquals(l_updatedContinents.getD_continents().get(0).getD_continentValue().toString(), "10");
    }

    /**
     * tests removal of continent via editcontinent operation
     *
     * @throws IOException    Exceptions
     * @throws InvalidMap     Exception
     * @throws InvalidCommand Exception
     */
    @Test
    public void testEditContinentRemove() throws IOException, InvalidMap, InvalidCommand {
        List<Continent> l_continents = new ArrayList<>();
        Continent l_c1 = new Continent();
        l_c1.setD_continentID(1);
        l_c1.setD_continentName("Asia");
        l_c1.setD_continentValue(10);

        Continent l_c2 = new Continent();
        l_c2.setD_continentID(2);
        l_c2.setD_continentName("Europe");
        l_c2.setD_continentValue(20);

        l_continents.add(l_c1);
        l_continents.add(l_c2);

        Map l_map = new Map();
        l_map.setD_continents(l_continents);
        d_state.setD_map(l_map);
        Map l_updatedContinents = d_mapservice.addOrRemoveContinents(d_state, d_state.getD_map(), "Remove", "Asia");

        assertEquals(l_updatedContinents.getD_continents().size(), 1);
        assertEquals(l_updatedContinents.getD_continents().get(0).getD_continentName(), "Europe");
        assertEquals(l_updatedContinents.getD_continents().get(0).getD_continentValue().toString(), "20");
    }

    /**
     * This test case is used to test functionality of Load map continent Id and
     * values.
     */
    @Test
    public void testContinentIdAndValues() {
        List<Integer> l_actualContinentIdList = new ArrayList<Integer>();
        List<Integer> l_actualContinentValueList = new ArrayList<Integer>();

        List<Integer> l_expectedContinentIdList = new ArrayList<Integer>();
        l_expectedContinentIdList.addAll(Arrays.asList(1, 2, 3, 4));

        List<Integer> l_expectedContinentValueList = new ArrayList<Integer>();
        l_expectedContinentValueList.addAll(Arrays.asList(5, 4, 5, 3));

        for (Continent l_continent : d_map.getD_continents()) {
            l_actualContinentIdList.add(l_continent.getD_continentID());
            l_actualContinentValueList.add(l_continent.getD_continentValue());
=======
        int l_initCount=0,l_finalCount=0;
        boolean l_isExcep = false;
        try {
            l_phase.handleCommand("editmap canada.map");
            Map l_map = l_phase.getD_gameState().getD_map();
            l_initCount = l_map.getD_continents().size();
            l_phase.handleCommand("editcontinent -add Asia 5");
            l_finalCount = l_map.getD_continents().size();
        } catch (Exception l_e){
            l_isExcep=true;
>>>>>>> 632436d8b4a66a40ae3a7f0dcb51f98065caeab7
        }

        assertEquals(l_initCount+1,l_finalCount);
    }

    /**
     * Test Method to test the editcountry method
     */
    @Test
    void removeCountryTest() {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);

<<<<<<< HEAD
        List<Integer> l_expectedCountryIdList = new ArrayList<Integer>();
        l_expectedCountryIdList.addAll(Arrays.asList(1, 2, 3, 4, 5));

        LinkedHashMap<Integer, List<Integer>> l_expectedCountryNeighbors = new LinkedHashMap<Integer, List<Integer>>() {
            {
                put(1, new ArrayList<Integer>(Arrays.asList(8, 21, 6, 7, 5, 2, 3, 4)));
                put(2, new ArrayList<Integer>(Arrays.asList(8, 1, 3)));
                put(3, new ArrayList<Integer>(Arrays.asList(1, 2)));
                put(4, new ArrayList<Integer>(Arrays.asList(22, 1, 5)));
                put(5, new ArrayList<Integer>(Arrays.asList(1, 4)));
            }
        };

        for (Country l_country : d_map.getD_countries()) {
            ArrayList<Integer> l_neighbours = new ArrayList<Integer>();
            l_actualCountryIdList.add(l_country.getD_countryId());
            l_neighbours.addAll(l_country.getD_neighbourCountryId());
            l_actualCountryNeighbors.put(l_country.getD_countryId(), l_neighbours);
        }
        System.out.println(l_actualCountryIdList + " " + l_expectedCountryIdList);
        assertEquals(l_expectedCountryIdList, l_actualCountryIdList);
        assertEquals(l_expectedCountryNeighbors, l_actualCountryNeighbors);
    }

    /**
     * Tests the savemap operation on an Invalid Map
     *
     * @throws InvalidMap Exception
     */
    @Test
    public void testSaveInvalidMap() throws InvalidMap {
        d_map.setD_mapFile("europe.map");
        d_state.setD_map(d_map);
        d_mapservice.saveMap(d_state, "europe.map");
        assertEquals("Log: Couldn't save the changes in map file!" + System.lineSeparator(), d_state.getRecentLog());
    }

    /**
     * Tests the add country operation via editCountry
     *
     * @throws IOException    Exception
     * @throws InvalidMap     Exception
     * @throws InvalidCommand Exception
     */
    @Test
    public void testEditCountryAdd() throws IOException, InvalidMap, InvalidCommand {
        d_mapservice.loadMap(d_state, "test.map");
        d_mapservice.editFunctions(d_state, "China Asia", "add", 2);

        assertEquals(d_state.getD_map().getCountryByName("China").getD_countryName(), "China");
    }

    /**
     * Tests the Remove Country Operation via editcountry
     *
     * @throws InvalidMap     Exception
     * @throws InvalidCommand Exception
     * @throws IOException    handles input output exception
     */
    @Test
    public void testEditCountryRemove() throws InvalidMap, IOException, InvalidCommand {
        d_mapservice.loadMap(d_state, "test.map");
        d_mapservice.editFunctions(d_state, "Ukraine", "remove", 2);
        assertEquals("Log: Country: Ukraine does not exist!" + System.lineSeparator(), d_state.getRecentLog());
    }

    /**
     * Tests the add neighbor operation via editneighbor
     *
     * @throws InvalidMap     Exception
     * @throws IOException    Exception
     * @throws InvalidCommand Exception
     */
    @Test
    public void testEditNeighborAdd() throws InvalidMap, IOException, InvalidCommand {
        d_mapservice.loadMap(d_state, "test.map");
        d_mapservice.editFunctions(d_state, "Northern-America 10","add",  1);
        d_mapservice.editFunctions(d_state, "Canada Northern-America", "add", 2);
        d_mapservice.editFunctions(d_state, "Alaska Northern-America", "add", 2);
        d_mapservice.editFunctions(d_state, "Canada Alaska", "add", 3);

        assertEquals(d_state.getD_map().getCountryByName("Canada").getD_neighbourCountryId().get(0), d_state.getD_map().getCountryByName("Alaska").getD_countryId());
    }

    /**
     * Tests the remove neighbor operation via editneighbor
     *
     * @throws InvalidMap     Exception
     * @throws IOException    Exception
     * @throws InvalidCommand Exception
     */
    @Test
    public void testEditNeighborRemove() throws InvalidMap, IOException, InvalidCommand {
        d_mapservice.editMap(d_state, "testedit.map");
        d_mapservice.editFunctions(d_state, "Asia 9", "add", 1);
        d_mapservice.editFunctions(d_state, "Maldives Asia","add",  2);
        d_mapservice.editFunctions(d_state, "Singapore Asia","add",  2);
        d_mapservice.editFunctions(d_state, "Singapore Maldives","add",  3);
        d_mapservice.editFunctions(d_state, "Maldives Singapore","remove",  3);
        assertEquals("Log: No Such Neighbour Exists" + System.lineSeparator(), d_state.getRecentLog());
    }
}
=======
        int l_initCount=0,l_finalCount=0;
        boolean l_isExcep = false;
        try {
            l_phase.handleCommand("editmap canada.map");
            Map l_map = l_phase.getD_gameState().getD_map();
            l_initCount = l_map.getD_countries().size();
            l_phase.handleCommand("editcountry -remove New_Brunswick");
            l_finalCount = l_map.getD_countries().size();
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertEquals(l_initCount-1,l_finalCount);
    }
}
>>>>>>> 632436d8b4a66a40ae3a7f0dcb51f98065caeab7
