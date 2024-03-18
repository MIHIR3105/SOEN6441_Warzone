package Views;

import Exceptions.InvalidMap;
import Models.GameState;
import Models.Map;
import Services.MapService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MapView.java
 * @author Yashesh Sorathia
 */
class MapViewTest {

    /**
     * Test to check the method showmap
     */
    @Test
    void showMap() throws InvalidMap {
        MapService l_service = new MapService();
        GameState l_gameState = new GameState();
        Map l_map = l_service.loadMap(l_gameState, l_service.getFilePath("europe.map"));
        MapView l_mapView = new MapView(l_gameState);

        l_mapView.showMap();
        assertEquals(4, l_mapView.d_continents.size());
    }
}