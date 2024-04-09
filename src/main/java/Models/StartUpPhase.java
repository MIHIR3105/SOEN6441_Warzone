package Models;

import Constants.GameConstants;
import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Services.GamePlayService;
import Utils.Command;
import Utils.UncaughtExceptionHandler;
import Views.MapView;
import Views.TournamentView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


/**
 * Start Up Phase implementation for GamePlay using State Pattern.
 *
 *
 * @author Prachi Patel
 */
public class StartUpPhase extends Phase {

    /**
     * Constructor for initializing the current game engine and game state.
     *
     * @param p_gameEngine Instance of the game engine to update the state
     * @param p_gameState  Instance of the game state
     */
    public StartUpPhase(GameEngine p_gameEngine, GameState p_gameState) {
        super(p_gameEngine, p_gameState);
    }

    /**
     * {@inheritDoc}
     */
    public void doValidateMap(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("No map found to validate, Please `loadmap` & `editmap` first", GameConstants.OUTCOME);
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            Models.Map l_currentMap = d_gameState.getD_map();
            if (l_currentMap == null) {
                throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
            } else {
                if (l_currentMap.Validate()) {
                    d_gameEngine.setD_gameEngineLog("The loaded map is valid!", GameConstants.OUTCOME);
                } else {
                    System.out.println("Failed to Validate map!");
                }
            }
        } else {
            throw new InvalidCommand(GameConstants.INVALIDCOMMAND);

        }
    }

    /**
     * {@inheritDoc}
     */
    public void doEditNeighbour(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("Can not Edit Neighbors, please perform `editmap` first", GameConstants.OUTCOME);
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)
                        && p_command.checkRequiredKeysPresent(GameConstants.OPERATIONS, l_map)) {
                    d_mapService.editFunctions(d_gameState, l_map.get(GameConstants.ARGUMENTS),
                            l_map.get(GameConstants.OPERATIONS), 3);
                } else {
                    throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void doEditCountry(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("Can not Edit Country, please perform `editmap` first", GameConstants.OUTCOME);
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)
                        && p_command.checkRequiredKeysPresent(GameConstants.OPERATIONS, l_map)) {
                    d_mapService.editFunctions(d_gameState, l_map.get(GameConstants.ARGUMENTS),
                            l_map.get(GameConstants.OPERATIONS), 2);
                } else {
                    throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void doLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();
        boolean l_flagValidate = false;

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)) {
                    // Loads the map if it is valid or resets the game state
                    Models.Map l_mapToLoad = d_mapService.loadMap(d_gameState,
                            l_map.get(GameConstants.ARGUMENTS));
                    if (l_mapToLoad.Validate()) {
                        l_flagValidate = true;
                        d_gameState.setD_loadCommand();
                        d_gameEngine.setD_gameEngineLog(l_map.get(GameConstants.ARGUMENTS) + " has been loaded to start the game", GameConstants.OUTCOME);
                    } else {
                        d_mapService.resetMap(d_gameState, l_map.get(GameConstants.ARGUMENTS));
                    }
                    if (!l_flagValidate) {
                        d_mapService.resetMap(d_gameState, l_map.get(GameConstants.ARGUMENTS));
                    }
                } else {
                    throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void doEditContinent(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("Can not Edit Continent, please perform `editmap` first", GameConstants.OUTCOME);
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));
        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidMap(GameConstants.INVALIDCOMMAND);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)
                        && p_command.checkRequiredKeysPresent(GameConstants.OPERATIONS, l_map)) {
                    d_mapService.editFunctions(d_gameState, l_map.get(GameConstants.ARGUMENTS),
                            l_map.get(GameConstants.OPERATIONS), 1);
                } else {
                    throw new InvalidMap(GameConstants.INVALIDCOMMAND);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void createPlayers(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));
        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)
                        && p_command.checkRequiredKeysPresent(GameConstants.OPERATIONS, l_map)) {
                    d_playerService.updatePlayers(d_gameState, l_map.get(GameConstants.OPERATIONS),
                            l_map.get(GameConstants.ARGUMENTS));
                } else {
                    throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
                }
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void doCreateDeploy(String p_command, Player p_player) {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAdvance(String p_command, Player p_player) {
        printInvalidCommandInState();
    }

    /**
     * Handels the tournament gameplay.
     *
     * @param p_command Command entered by the user
     * @throws Exception Exception
     */
    @Override
    protected void tournamentGamePlay(Command p_command) throws InvalidCommand, InvalidMap {
        if (d_gameState.getD_players() != null && d_gameState.getD_players().size() > 1) {
            List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();
            boolean l_parsingSuccessful = false;
            Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));

            if ((l_operations_list == null || l_operations_list.isEmpty()) && !d_tournament.requiredTournamentArgPresent(l_operations_list, p_command)) {
                throw new InvalidCommand(GameConstants.INVALIDCOMMANDTOURNAMENTMODE);
            } else {
                for (Map<String, String> l_map : l_operations_list) {
                    if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)
                            && p_command.checkRequiredKeysPresent(GameConstants.OPERATIONS, l_map)) {
                        l_parsingSuccessful = d_tournament.parseTournamentCommand(d_gameState,
                                l_map.get(GameConstants.OPERATIONS), l_map.get(GameConstants.ARGUMENTS),
                                d_gameEngine);
                        if (!l_parsingSuccessful)
                            break;

                    } else {
                        throw new InvalidCommand(GameConstants.INVALIDCOMMANDTOURNAMENTMODE);
                    }
                }
            }
            if (l_parsingSuccessful) {
                for (GameState l_gameState : d_tournament.getD_gameStateList()) {
                    d_gameEngine.setD_gameEngineLog(
                            "\nStarting New Game on map : " + l_gameState.getD_map().getD_mapFile() + " .........\n",
                            "effect");
                    doAssignCountries(new Command("assigncountries"), null, true, l_gameState);

                    d_gameEngine.setD_gameEngineLog(
                            "\nGame Completed on map : " + l_gameState.getD_map().getD_mapFile() + " .........\n",
                            "effect");
                }
                d_gameEngine.setD_gameEngineLog("************ Tournament Completed ************", "effect");
                TournamentView l_tournamentView = new TournamentView(d_tournament);
                l_tournamentView.viewTournament();
            }
        } else {
            d_gameEngine.setD_gameEngineLog("Please add 2 or more players first in the game.", "effect");
        }
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
        List<java.util.Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALIDCOMMANDERRORLOADGAME);
        }

        for (Map<String, String> l_map : l_operations_list) {
            if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)) {
                String l_filename = l_map.get(GameConstants.ARGUMENTS);

                try {
                    Phase l_phase = GamePlayService.loadGame(l_filename);

                    this.d_gameEngine.loadPhase(l_phase);
                } catch (ClassNotFoundException l_e) {
                    l_e.printStackTrace();
                }
            }
        }
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
     * {@inheritDoc}
     */
    @Override
    protected void doCardHandle(String p_enteredCommand, Player p_player) {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    public void doAssignCountries(Command p_command, Player p_player, boolean p_isTournamentMode, GameState p_gameState) throws InvalidCommand {
        if (p_gameState.getD_loadCommand()) {
            List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();
            Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));
            if ((l_operations_list == null || l_operations_list.isEmpty()) || p_isTournamentMode) {
                d_gameEngine.setD_gameState(p_gameState);
                d_gameEngine.setD_isTournamentMode(p_isTournamentMode);
                d_playerService.assignCountries(p_gameState);
                d_playerService.assignArmies(p_gameState);
                d_gameEngine.setIssueOrderPhase(p_isTournamentMode);
            } else {
                throw new InvalidCommand(GameConstants.INVALIDCOMMANDERRORASSIGNCOUNTRIES);
            }
        } else {
            d_gameEngine.setD_gameEngineLog("Please load a valid map first via loadmap command!", "effect");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doShowMap(Command p_command, Player p_player) {
        MapView l_mapView = new MapView(d_gameState);
        l_mapView.showMap();
    }

    /**
     * {@inheritDoc}
     */
    public void initPhase(boolean p_isTournamentMode) {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

        while (d_gameEngine.getD_CurrentPhase() instanceof StartUpPhase) {
            try {
                System.out.println("Enter Game Commands or type 'exit' for quitting");
                String l_commandEntered = l_reader.readLine();
                handleCommand(l_commandEntered);
            } catch (Exception l_exception) {
                d_gameEngine.setD_gameEngineLog(l_exception.getMessage(), GameConstants.OUTCOME);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void doMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        List<java.util.Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
        } else {
            for (java.util.Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)) {
                    d_mapService.editMap(d_gameState, l_map.get(GameConstants.ARGUMENTS));
                } else {
                    throw new InvalidMap(GameConstants.INVALIDCOMMAND);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void doSaveMap(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("No map found to save, Please `editmap` first", GameConstants.OUTCOME);
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)) {
                    boolean l_fileUpdateStatus = d_mapService.saveMap(d_gameState,
                            l_map.get(GameConstants.ARGUMENTS));
                    if (l_fileUpdateStatus) {
                        d_gameEngine.setD_gameEngineLog("Required changes have been made in map file", GameConstants.OUTCOME);
                    } else
                        System.out.println(d_gameState.getError());
                } else {
                    throw new InvalidCommand(GameConstants.INVALIDCOMMAND);
                }
            }
        }
    }
}
