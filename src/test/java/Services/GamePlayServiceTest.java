package Services;

import Controllers.GameEngine;
import Models.GameState;
import Models.Phase;
import Models.StartUpPhase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamePlayServiceTest {

    @Test
    void saveGame() {
        GameState l_gameState = new GameState();
        GameEngine l_gameEngine = new GameEngine();
        Phase l_phase = new StartUpPhase(l_gameEngine,l_gameState);
        boolean l_isExcep = false;
        try {
            l_phase.handleCommand("loadmap canada.map");
            l_phase.handleCommand("gameplayer -add A -add B");
            GamePlayService.saveGame(l_phase, "check.txt");
            GamePlayService.loadGame("check.txt");
            l_phase.handleCommand("validatemap");
        } catch (Exception l_e){
            l_isExcep=true;
        }
        assertFalse(l_isExcep);
    }
}