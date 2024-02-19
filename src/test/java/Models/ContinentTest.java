package Models;

import java.util.List;
import Services.MapService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Continent.java
 * @author Vaibhav Chauhan
 */
public class ContinentTest {
    /**
     * Test to check if country ID is removed from the list of neighbours for all the countries in the continent
     */
    @Test
    public void removeCountryForAllNeighboursTest() {
        MapService l_service = new MapService();
        GameState l_game = new GameState();
        Map l_map = l_service.loadMap(l_game, l_service.getFilePath("europe.map"));

        List<Continent> l_continentList = l_map.getD_continents();
        List<Country> l_countriesList = l_continentList.get(0).getD_countries();
        int l_initCount = l_countriesList.get(0).getD_neighbourCountryId().size();
        l_continentList.get(0).removeCountryForAllNeighbours(8);
        int l_finalCount = l_countriesList.get(0).getD_neighbourCountryId().size();
        assertEquals(l_initCount-1,l_finalCount);
    }
}