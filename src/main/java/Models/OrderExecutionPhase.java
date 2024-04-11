package Models;

import Constants.GameConstants;
import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Utils.Command;
import Utils.UncaughtExceptionHandler;
import Views.MapView;
import Services.GamePlayService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * Order Execution Phase implementation for GamePlay using State Pattern.
 *
 * @author Prachi Patel
 */
public class OrderExecutionPhase extends Phase {

    /**
     * Constructor for initializing the current game engine and game state.
     *
     * @param p_gameEngine Instance of the game engine to update the state
     * @param p_gameState  Instance of the game state
     */
    public OrderExecutionPhase(GameEngine p_gameEngine, GameState p_gameState) {
        super(p_gameEngine, p_gameState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoadGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doValidateMap(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doEditNeighbour(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doEditCountry(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSaveMap(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doEditContinent(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createPlayers(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doCreateDeploy(String p_command, Player p_player) throws IOException {
        printInvalidCommandInState();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAdvance(String p_command, Player p_player) throws IOException {
        printInvalidCommandInState();
    }

    /**
     * Handels the tournament gameplay.
     *
     * @param p_command Command entered by the user
     * @throws InvalidCommand exception is thrown
     * @throws InvalidMap invalid map exception is thrown
     */
    @Override
    protected void tournamentGamePlay(Command p_command) throws InvalidCommand, InvalidMap {
//        d_gameEngine.setD_gameEngineLog("\nStarting Execution Of Tournament Mode.....", "start");
//        d_tournament.executeTournamentMode();
//        d_tournament.printTournamentModeResult();
    }




    /**
     * {@inheritDoc}
     */
    @Override
    protected void doCardHandle(String p_enteredCommand, Player p_player) throws IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAssignCountries(Command p_command, Player p_player, boolean isTournamentMode, GameState p_gameState) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doShowMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        MapView l_mapView = new MapView(d_gameState);
        l_mapView.showMap();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSaveGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        List<java.util.Map<String, String>> l_operations_list = p_command.getTaskAndArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALIDCOMMANDERRORSAVEGAME);
        }

        for (Map<String, String> l_map : l_operations_list) {
            if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)) {
                String l_filename = l_map.get(GameConstants.ARGUMENTS);
                GamePlayService.saveGame(this, l_filename);
                d_gameEngine.setD_gameEngineLog("Game Saved Successfully to "+l_filename, "effect");
            } else {
                throw new InvalidCommand(GameConstants.INVALIDCOMMANDERRORSAVEGAME);
            }
        }
    }

    /**
     * Invokes the execution logic for all unexecuted orders of players in the game.
     */
    protected void executeOrders() {
        addNeutralPlayer(d_gameState);
        // Executing orders
        d_gameEngine.setD_gameEngineLog("\nStarting Execution Of Orders.....", "start");
        while (d_playerService.unexecutedOrdersExists(d_gameState.getD_players())) {
            for (Player l_player : d_gameState.getD_players()) {
                Order l_order = l_player.next_order();
                if (l_order != null) {
                    l_order.printOrder();
                    d_gameState.updateLog(l_order.orderExecutionLog(), "effect");
                    l_order.execute(d_gameState);
                }
            }
        }
        d_playerService.resetPlayersFlag(d_gameState.getD_players());
    }

    /**
     * Adds a neutral player to the game state.
     *
     * @param p_gameState The GameState to which the neutral player is added
     */
    public void addNeutralPlayer(GameState p_gameState) {

        Player l_player = p_gameState.getD_players().stream()
                .filter(l_pl -> l_pl.getPlayerName().equalsIgnoreCase("Neutral")).findFirst().orElse(null);
        if (l_player == null) {
            Player l_neutralPlayer = new Player("Neutral");
            l_neutralPlayer.setD_moreOrders(false);
            p_gameState.getD_players().add(l_neutralPlayer);
        } else {
            return;
        }
    }

    /**
     * Checks if a single player has conquered all countries of the map, indicating the end of the game.
     *
     * @param p_gameState Current state of the game
     * @return true if a player has conquered all countries and the game should end; otherwise, returns false
     */
    protected Boolean checkEndOftheGame(GameState p_gameState) {
        Integer l_totalCountries = p_gameState.getD_map().getD_countries().size();
        d_playerService.updatePlayersInGame(p_gameState);
        int maxCountriesConquered = Integer.MIN_VALUE;
        if(p_gameState.getD_numberOfTurnsLeft()==1){
            for (Player l_player : p_gameState.getD_players()) {
                if (l_player.getD_coutriesOwned().size() > maxCountriesConquered) {
                    maxCountriesConquered = l_player.getD_coutriesOwned().size();
                }
                if (maxCountriesConquered == l_totalCountries) {
                    break;
                }
            }
        }
        for (Player l_player : p_gameState.getD_players()) {
            if (l_player.getD_coutriesOwned().size() == (maxCountriesConquered == Integer.MIN_VALUE ? l_totalCountries : maxCountriesConquered)) {
                d_gameState.setD_winner(l_player);
                d_gameEngine.setD_gameEngineLog("Player : " + l_player.getPlayerName()
                        + " has won the Game by conquering maximum countries. Exiting the Game .....", "end");
                return true;
            }
        }
        return false;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void initPhase(boolean isTournamentMode) {
        executeOrders();

        MapView l_map_view = new MapView(d_gameState);
        l_map_view.showMap();

        if (this.checkEndOftheGame(d_gameState))
            return;


        try {
            String l_continue = this.continueForNextTurn(isTournamentMode);
            if (l_continue.equalsIgnoreCase("N") && isTournamentMode) {
                d_gameEngine.setD_gameEngineLog("Start Up Phase", "phase");
                d_gameEngine.setD_CurrentPhase(new StartUpPhase(d_gameEngine, d_gameState));
            } else if (l_continue.equalsIgnoreCase("N") && !isTournamentMode) {
                d_gameEngine.setStartUpPhase();

            } else if (l_continue.equalsIgnoreCase("Y")) {
                System.out.println("\n" + d_gameState.getD_numberOfTurnsLeft() + " Turns are left for this game. Continuing for next Turn.\n");
                d_playerService.assignArmies(d_gameState);
                d_gameEngine.setIssueOrderPhase(isTournamentMode);
            } else {
                System.out.println("Invalid Input");
            }
        }  catch (IOException l_e) {
            System.out.println("Invalid Input");
        }

    }
    /**
     * Checks if next turn has to be played or not.
     *
     * @param isTournamentMode if tournament is being played
     * @return Yes or no based on user input or tournament turns left
     * @throws IOException indicates failure in I/O operation
     */
    private String continueForNextTurn(boolean isTournamentMode) throws IOException {
        String l_continue = new String();
        if (isTournamentMode) {
            d_gameState.setD_numberOfTurnsLeft(d_gameState.getD_numberOfTurnsLeft() - 1);
            l_continue = d_gameState.getD_numberOfTurnsLeft() == 0 ? "N" : "Y";
        } else {
            System.out.println("Press Y/y if you want to continue for next turn or else press N/n");
            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
            l_continue = l_reader.readLine();
        }
        return l_continue;
    }
}
