/**
 * This class is used to test functionality of Human Player.
 * @author Mihir Panchal
 */

package Models;

import Controllers.GameEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumanPlayerTest {

    @Test
    void createOrder() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("editmap canada.map");
        Map l_map = l_phase.getD_gameState().getD_map();
        assertEquals("canada.map", l_phase.getD_gameState().getD_map().getD_mapFile());
    }
}