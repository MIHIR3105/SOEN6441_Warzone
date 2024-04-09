package Services;

import Controllers.GameEngine;
import Models.GameState;
import Models.Phase;
import Models.StartUpPhase;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GamePlayService
 */
class GamePlayServiceTest {

    @Test
    /**
     * Test to check if game is saved and loaded successfully
     */
    void saveGame() {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase phase = new StartUpPhase(l_gameEngine, l_gameState);
        String filename = "check.txt";

        try {
            phase.handleCommand("loadmap canada.map");
            phase.handleCommand("gameplayer -add A -add B");
            GamePlayService.saveGame(phase, filename);
            Phase l_loadedPhase = GamePlayService.loadGame(filename);
            phase.handleCommand("validatemap");

            assertTrue(new File("src/main/resources/" + filename).exists());

            assertEquals(phase.getClass(), l_loadedPhase.getClass());

            assertTrue(l_gameState.getD_map().Validate());
        } catch (Exception e) {
            fail("Exception thrown during save and load: " + e.getMessage());
        }
    }
}