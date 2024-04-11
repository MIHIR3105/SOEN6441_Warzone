/**
 * This class is used to test functionality of issue order phase.
 * @author Mihir Panchal
 */
package Models;

import Controllers.GameEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This class is used to test functionality of Issue order phase class functions.
 *
 * @author Mihir Panchal
 */
class IssueOrderPhaseTest {

    @Test
    void performEditContinentTest() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("editmap canada.map");
        Map l_map = l_phase.getD_gameState().getD_map();
        int l_initCount = l_map.getD_continents().size();
        l_phase.handleCommand("editcontinent -add Asia 5");
        int l_finalCount = l_map.getD_continents().size();

        assertEquals(l_initCount+1,l_finalCount);
    }
}