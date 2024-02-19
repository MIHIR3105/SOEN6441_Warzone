package Models;

import Services.MapService;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Map.java
 * @ Aashvi Zala
 */
class MapTest {

    /**
     * Test to see if the countries are connected or not
     */
    @Test
    void isCountriesConnectedTest() {
        MapService l_serivce = new MapService();
        GameState l_game = new GameState();
        Map l_map = l_serivce.loadMap(l_game, l_serivce.getFilePath("europe.map"));
        assertEquals(true,l_map.isCountriesConnected());
    }


    /**
     * Test to see if the continents are connected or not
     */
    @Test
    void isContinentsConnectedTest() {
        MapService l_serivce = new MapService();
        GameState l_game = new GameState();
        Map l_map = l_serivce.loadMap(l_game, l_serivce.getFilePath("europe.map"));

        assertEquals(true,l_map.isContinentsConnected());
    }

}