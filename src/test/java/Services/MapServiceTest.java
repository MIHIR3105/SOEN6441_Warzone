package Services;

import Controllers.GameEngine;
import Models.GameState;
import Utils.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Class for MapService Class
 * @author Mihir Panchal
 */
class MapServiceTest {

    /**
     * Test Method to test the editmap method
     */
    @Test
    void editMapTest() {
        GameEngine l_gameEngine = new GameEngine();
        GameState d_gameState = new GameState();
        int l_initCount=0,l_finalCount=0;
        boolean l_isExcep = false;
        try {
            Command l_command = new Command("editmap canada.map");
            l_gameEngine.doLoadMap(l_command);
            l_initCount = l_gameEngine.getD_gameState().getD_map().getD_continents().size();
            Command l_command1 = new Command("editcontinent -add Asia 5");
            l_gameEngine.doEditContinent(l_command1);
            l_finalCount = l_gameEngine.getD_gameState().getD_map().getD_continents().size();
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertEquals(l_initCount+1,l_finalCount);
    }

    /**
     * Test Method to test the editcountry method
     */
    @Test
    void removeCountryTest() {
        GameEngine l_gameEngine = new GameEngine();
        GameState d_gameState = new GameState();
        int l_initCount=0,l_finalCount=0;
        boolean l_isExcep = false;
        try {
            Command l_command = new Command("editmap canada.map");
            l_gameEngine.doLoadMap(l_command);
            l_initCount = l_gameEngine.getD_gameState().getD_map().getD_countries().size();
            Command l_command1 = new Command("editcountry -remove New_Brunswick");
            l_gameEngine.doEditCountry(l_command1);
            l_finalCount = l_gameEngine.getD_gameState().getD_map().getD_countries().size();
        } catch (Exception l_e){
            l_isExcep=true;
        }

        assertEquals(l_initCount-1,l_finalCount);
    }
}