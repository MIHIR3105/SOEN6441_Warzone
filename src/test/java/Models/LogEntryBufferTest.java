/**
 * This class is used to test functionality of Log entry buffer.
 * @author Mihir Panchal
 */
package Models;

import Controllers.GameEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This class is used to test functionality of LogEntryBuffer class functions.
 *
 * @author Mihir Panchal
 */
class LogEntryBufferTest {

    @Test
    void getCurrentLogTest() throws Exception {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        l_phase.handleCommand("editmap canada.map");
        assert(l_phase.d_gameState.getRecentLog().contains("already exists and is loaded for editing"));
    }
}