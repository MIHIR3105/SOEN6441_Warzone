package Models;

import Exceptions.InvalidMap;
import Services.MapService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Country.java
 * @author Vaibhav Chauhan
 */
class CountryTest {
    /**
     * Test to check if country ID is removed from the list of neighbours
     */
    @Test
    void removeNeighbourFromCountryTest() throws InvalidMap {
        MapService l_service = new MapService();
        GameState l_game = new GameState();
        Map l_map = l_service.loadMap(l_game, l_service.getFilePath("europe.map"));
        List<Country> l_countries = l_map.getD_countries();
        int l_initCount = l_countries.get(0).getD_neighbourCountryIds().size();
        l_countries.get(0).removeNeighbourFromCountry(8);
        int l_finalCount = l_countries.get(0).getD_neighbourCountryIds().size();
        assertEquals(l_initCount-1,l_finalCount);
    }
}