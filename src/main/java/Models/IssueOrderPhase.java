package Models;

import Constants.GameConstants;
import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Services.GamePlayService;
import Utils.Command;
import Utils.UncaughtExceptionHandler;
import Views.MapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Issue Order Phase implementation for GamePlay using State Pattern.
 *
 * @author Prachi Patel
 */

public class IssueOrderPhase extends Phase {

    /**
     * Constructor for initializing the current game engine and game state.
     *
     * @param p_gameEngine Instance of the game engine to update the state
     * @param p_gameState  Instance of the game state
     */
    public IssueOrderPhase(GameEngine p_gameEngine, GameState p_gameState) {
        super(p_gameEngine, p_gameState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doValidateMap(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
        askForOrder(p_player);
    }

    /**
     * Asks the specified player for an order command and processes it.
     *
     * @param p_player The player for whom the order is requested.
     * @throws Exception Indicates a failure
     */
    public void askForOrder(Player p_player) throws IOException, InvalidCommand, InvalidMap {
        String l_commandEntered = p_player.getPlayerOrder(d_gameState);

        if (l_commandEntered == null) return;

        d_gameState.updateLog("(Player: " + p_player.getPlayerName() + ") " + l_commandEntered, "order");
        handleCommand(l_commandEntered, p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doEditNeighbour(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
        askForOrder(p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doEditCountry(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
        askForOrder(p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
        askForOrder(p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSaveMap(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
        askForOrder(p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
        askForOrder(p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doEditContinent(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
        askForOrder(p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createPlayers(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
        askForOrder(p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doCreateDeploy(String p_command, Player p_player) throws IOException {
        p_player.createDeployOrder(p_command);
        d_gameState.updateLog(p_player.getD_playerLog(), GameConstants.OUTCOME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAdvance(String p_command, Player p_player) throws IOException {
        p_player.createAdvanceOrder(p_command, d_gameState);
        d_gameState.updateLog(p_player.getD_playerLog(), GameConstants.OUTCOME);
    }

    /**
     * Handles the tournament gameplay.
     *
     * @param p_command Command entered by the user
     * @throws Exception Exception
     */
    @Override
    protected void tournamentGamePlay(Command p_command) throws InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * Handles Game Load Feature.
     *
     * @param p_command command entered by user
     * @param p_player  player instance
     * @throws IOException indicates failure in I/O operation
     */
    @Override
    protected void doLoadGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
        askForOrder(p_player);
    }

    /**
     * Handles Game Save Feature.
     *
     * @param p_command command entered by user
     * @param p_player  player instance
     * @throws IOException indicates failure in I/O operation
     */
    @Override
    protected void doSaveGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        List<java.util.Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALIDCOMMANDERRORSAVEGAME);
        }

        for (java.util.Map<String, String> l_map : l_operations_list) {
            if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)) {
                String l_filename = l_map.get(GameConstants.ARGUMENTS);
                GamePlayService.saveGame(this, l_filename);
                d_gameEngine.setD_gameEngineLog("Game Saved Successfully to " + l_filename, "effect");

            } else {
                throw new InvalidCommand(GameConstants.INVALIDCOMMANDERRORSAVEGAME);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doCardHandle(String p_enteredCommand, Player p_player) throws IOException {
        if (p_player.getD_cardsOwnedByPlayer().contains(p_enteredCommand.split(" ")[0])) {
            p_player.handleCardCommands(p_enteredCommand, d_gameState);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAssignCountries(Command p_command, Player p_player, boolean isTournamentMode, GameState p_gameState) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
        askForOrder(p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doShowMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        MapView l_mapView = new MapView(d_gameState);
        l_mapView.showMap();

        askForOrder(p_player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initPhase(boolean p_isTournamentMode) {
        while (d_gameEngine.getD_CurrentPhase() instanceof IssueOrderPhase) {
            issueOrders(p_isTournamentMode);
        }
    }

    /**
     * Accepts orders from players.
     *
     * @param p_isTournamentMode if game is being played in tournament mode
     */
    private void issueOrders(boolean p_isTournamentMode) {
        do {
            for (Player l_player : d_gameState.getD_players()) {
                if (l_player.getD_coutriesOwned().size() == 0) {
                    l_player.setD_moreOrders(false);
                }
                if (l_player.getD_moreOrders() && !l_player.getPlayerName().equals("Neutral")) {
                    try {
                        l_player.issue_order(this);
                        l_player.checkForMoreOrders(p_isTournamentMode);
                    } catch (Exception l_exception) {
                        d_gameEngine.setD_gameEngineLog(l_exception.getMessage(), GameConstants.OUTCOME);
                    }
                }
            }
        } while (d_playerService.checkForMoreOrders(d_gameState.getD_players()));

        d_gameEngine.setOrderExecutionPhase();
    }

}
