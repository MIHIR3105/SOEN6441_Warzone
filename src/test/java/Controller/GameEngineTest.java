package Controller;

import Controllers.GameEngine;
import Models.GameState;
import Models.Map;
import Services.MapService;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GameEngine.java
 */
class GameEngineTest {

    /**
     * Method to Test Invalid Command input
     * @throws Exception indicates Exception
     */
    @Test
    void performEditContinentInvalidTest() throws Exception {
        GameEngine l_gameEngine = new GameEngine();
        l_gameEngine.d_mapService = new MapService();
        l_gameEngine.d_gameState = new GameState();
        Map l_map = l_gameEngine.d_mapService.loadMap(l_gameEngine.d_gameState, l_gameEngine.d_mapService.getFilePath("canada.map"));
        int l_initCount = l_map.getD_continents().size();
        Command l_command = new Command("editcontinent - add Asia");
        l_gameEngine.doEditContinent(l_command);
        int l_finalCount = l_map.getD_continents().size();

        assertEquals(l_initCount,l_finalCount);
    }

    /**
     * Method to test Valid command input
     * @throws Exception indicates Exception
     */
    @Test
    void performEditContinentValidTest() throws Exception {
        GameEngine l_gameEngine = new GameEngine();
        l_gameEngine.d_mapService = new MapService();
        l_gameEngine.d_gameState = new GameState();
        Map l_map = l_gameEngine.d_mapService.loadMap(l_gameEngine.d_gameState, l_gameEngine.d_mapService.getFilePath("canada.map"));
        int l_initCount = l_map.getD_continents().size();
        Command l_command = new Command("editcontinent -add Asia 5");
        l_gameEngine.doEditContinent(l_command);
        int l_finalCount = l_map.getD_continents().size();

        assertEquals(l_initCount+1,l_finalCount);
    }

    /**
     * Method to try loading a file that is not present
     */
    @Test
    void invalidFileLoadTest(){
        GameEngine l_gameEngine = new GameEngine();
        l_gameEngine.d_mapService = new MapService();
        l_gameEngine.d_gameState = new GameState();
        boolean l_isExcep = false;
        try {
            Command l_command = new Command("loadmap nonexistant.map");
            l_gameEngine.doLoadMap(l_command);
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertTrue(l_isExcep);
    }
}