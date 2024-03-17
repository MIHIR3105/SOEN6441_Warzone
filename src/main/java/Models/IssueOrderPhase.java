package Models;

import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Utils.Command;

import java.io.IOException;

/**
 * Issue Order Phase implementation for GamePlay using State Pattern.
 *
 * @author Prachi Patel
 */
public class IssueOrderPhase extends Phase{

    /**
     * Constructor to initialize the value of current game engine.
     *
     * @param p_gameEngine game engine instance to update state
     * @param p_gameState instance of current game state in GameEngine
     */
    public IssueOrderPhase(GameEngine p_gameEngine, GameState p_gameState) {
        super(p_gameEngine, p_gameState);
    }

    @Override
    protected void doCardHandle(String p_enteredCommand, Player p_player) throws IOException {

    }

    @Override
    protected void doShowMap(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap {

    }

    @Override
    protected void doAdvance(String p_command, Player p_player) throws IOException {

    }

    @Override
    public void initPhase() {

    }

    @Override
    protected void doCreateDeploy(String p_command, Player p_player) throws IOException {

    }

    @Override
    protected void assignCountries(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap {

    }

    @Override
    protected void createPlayers(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap {

    }

    @Override
    protected void doEditNeighbour(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {

    }

    @Override
    protected void doEditCountry(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {

    }

    @Override
    protected void doValidateMap(Command p_command, Player p_player) throws InvalidMap, InvalidCommand, IOException {

    }

    @Override
    protected void doLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {

    }

    @Override
    protected void doSaveMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {

    }

    @Override
    protected void doEditContinent(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {

    }

    @Override
    protected void doMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {

    }

}
