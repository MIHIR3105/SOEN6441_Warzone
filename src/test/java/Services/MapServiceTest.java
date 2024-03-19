package Services;

import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Map;
import Utils.CommonUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is used to test functionality of MapService class functions.
 *
 * @author Prachi Patel
 */
public class MapServiceTest {

    /**
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
     */
    @Test
    public void testEditMap() throws IOException, InvalidMap {
        d_mapservice.editMap(d_state, "test.map");
        File l_file = new File(CommonUtil.getMapFilePath("test.map"));

        assertTrue(l_file.exists());
    }

    /**
     * tests addition of continent via editcontinent operation
     * @throws IOException Exceptions
     * @throws InvalidMap Exception
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
     * @throws IOException Exceptions
     * @throws InvalidMap Exception
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
        }

        assertEquals(l_expectedContinentIdList, l_actualContinentIdList);
        assertEquals(l_expectedContinentValueList, l_actualContinentValueList);
    }



    /**
     * Tests the add country operation via editCountry
     * @throws IOException Exception
     * @throws InvalidMap Exception
     * @throws InvalidCommand Exception
     */
    @Test
    public void testEditCountryAdd() throws IOException, InvalidMap, InvalidCommand {
        d_mapservice.loadMap(d_state, "test.map");
        d_mapservice.editFunctions(d_state, "add", "China Asia", 2);

        assertEquals(d_state.getD_map().getCountryByName("China").getD_countryName(), "China");
    }

    /**
     * Tests the Remove Country Operation via editcountry
     * @throws InvalidMap Exception
     * @throws InvalidCommand Exception
     * @throws IOException handles input output exception
     */
    @Test
    public void testEditCountryRemove() throws InvalidMap, IOException, InvalidCommand {
        d_mapservice.loadMap(d_state, "test.map");
        d_mapservice.editFunctions(d_state, "remove", "Ukraine", 2);
        assertEquals("Log: Country: Ukraine does not exist!"+System.lineSeparator(), d_state.getRecentLog());
    }

    /**
     * Tests the add neighbor operation via editneighbor
     * @throws InvalidMap Exception
     * @throws IOException Exception
     * @throws InvalidCommand Exception
     */
    @Test
    public void testEditNeighborAdd() throws InvalidMap, IOException, InvalidCommand {
        d_mapservice.loadMap(d_state, "test.map");
        d_mapservice.editFunctions(d_state, "Northern-America 10", "add", 1 );
        d_mapservice.editFunctions(d_state, "add", "Canada Northern-America",  2);
        d_mapservice.editFunctions(d_state, "add","Alaska Northern-America", 2);
        d_mapservice.editFunctions(d_state, "add", "Canada Alaska", 3);

        assertEquals(d_state.getD_map().getCountryByName("Canada").getD_neighbourCountryIds().get(0), d_state.getD_map().getCountryByName("Alaska").getD_countryId());
    }


}
