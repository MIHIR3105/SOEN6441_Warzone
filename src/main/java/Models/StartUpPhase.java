package Models;

import Constants.GameConstants;
import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Utils.Command;
import Utils.CommonUtil;
import Utils.ExceptionLogHandler;
import Views.MapView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


/**
 * Start Up Phase implementation for GamePlay using State Pattern.
 *
 * @author Prachi Patel
 */
public class StartUpPhase extends Phase {

    /**
     * It's a constructor that init the GameEngine context in Phase class.
     *
     * @param p_gameEngine GameEngine Context
     * @param p_gameState  current Game State
     */
    public StartUpPhase(GameEngine p_gameEngine, GameState p_gameState) {
        super(p_gameEngine, p_gameState);
    }


    @Override
    protected void doCardHandle(String p_enteredCommand, Player p_player) throws IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doShowMap(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap {
        MapView l_mapView = new MapView(d_gameState);
        l_mapView.showMap();
    }


    @Override
    protected void doAdvance(String p_command, Player p_player) throws IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initPhase() {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

        while (d_gameEngine.getD_CurrentPhase() instanceof StartUpPhase) {
            try {
                System.out.println("Enter Game Commands or type 'exit' for quitting");
                String l_commandEntered = l_reader.readLine();

                handleCommand(l_commandEntered);
            } catch (InvalidCommand | InvalidMap | IOException l_exception) {
                d_gameEngine.setD_gameEngineLog(l_exception.getMessage(), "effect");
            }
        }
    }

    @Override
    protected void doCreateDeploy(String p_command, Player p_player) throws IOException {
        printInvalidCommandInState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void assignCountries(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap {
        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
        if (CommonUtil.isCollectionEmpty(l_operations_list)) {
            d_playerService.assignCountries(d_gameState);
            d_playerService.assignArmies(d_gameState);
            d_gameEngine.setIssueOrderPhase();
        } else {
            throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_ASSIGNCOUNTRIES);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createPlayers(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("No map found, Please `loadmap` before adding game players", "effect");
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
        if (CommonUtil.isCollectionEmpty(l_operations_list)) {
            throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_GAMEPLAYER);
        } else {
            if (d_gameState.getD_loadCommand()) {
                for (Map<String, String> l_map : l_operations_list) {
                    if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)
                            && p_command.checkRequiredKeysPresent(GameConstants.OPERATION, l_map)) {
                        d_playerService.updatePlayers(d_gameState, l_map.get(GameConstants.OPERATION),
                                l_map.get(GameConstants.ARGUMENTS));
                    } else {
                        throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_GAMEPLAYER);
                    }
                }
            } else {
                d_gameEngine.setD_gameEngineLog("Please load a valid map first via loadmap command!", "effect");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doEditNeighbour(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {

        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("Can not Edit Neighbors, please perform `editmap` first", "effect");
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)
                        && p_command.checkRequiredKeysPresent(GameConstants.OPERATION, l_map)) {
                    d_mapService.editFunctions(d_gameState, l_map.get(GameConstants.OPERATION),
                            l_map.get(GameConstants.ARGUMENTS), 3);
                } else {
                    throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doEditCountry(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {

        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("Can not Edit Country, please perform `editmap` first", "effect");
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)
                        && p_command.checkRequiredKeysPresent(GameConstants.OPERATION, l_map)) {
                    d_mapService.editFunctions(d_gameState, l_map.get(GameConstants.OPERATION),
                            l_map.get(GameConstants.ARGUMENTS), 2);
                } else {
                    throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doValidateMap(Command p_command, Player p_player) throws InvalidMap, InvalidCommand, IOException {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("No map found to validate, Please `loadmap` & `editmap` first", "effect");
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            Models.Map l_currentMap = d_gameState.getD_map();
            if (l_currentMap == null) {
                throw new InvalidMap(GameConstants.INVALID_MAP_ERROR_EMPTY);
            } else {
                if (l_currentMap.Validate()) {
                    d_gameEngine.setD_gameEngineLog(GameConstants.VALID_MAP, "effect");
                } else {
                    throw new InvalidMap("Failed to Validate map!");
                }
            }
        } else {
            throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_VALIDATEMAP);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();
        boolean l_flagValidate = false;

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_LOADMAP);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)) {
                    // Loads the map if it is valid or resets the game state
                    Models.Map l_mapToLoad = d_mapService.loadMap(d_gameState,
                            l_map.get(GameConstants.ARGUMENTS));
                    if (l_mapToLoad.Validate()) {
                        l_flagValidate = true;
                        d_gameState.setD_loadCommand();
                        d_gameEngine.setD_gameEngineLog(l_map.get(GameConstants.ARGUMENTS) + " has been loaded to start the game", "effect");
                    } else {
                        d_mapService.resetState(d_gameState, l_map.get(GameConstants.ARGUMENTS));
                    }
                    if (!l_flagValidate) {
                        d_mapService.resetState(d_gameState, l_map.get(GameConstants.ARGUMENTS));
                    }
                } else {
                    throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_LOADMAP);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSaveMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("No map found to save, Please `editmap` first", "effect");
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_SAVEMAP);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)) {
                    boolean l_fileUpdateStatus = d_mapService.saveMap(d_gameState,
                            l_map.get(GameConstants.ARGUMENTS));
                    if (l_fileUpdateStatus) {
                        d_gameEngine.setD_gameEngineLog("Required changes have been made in map file", "effect");
                    } else
                        System.out.println(d_gameState.getError());
                } else {
                    throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_SAVEMAP);
                }
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void doEditContinent(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("Can not Edit Continent, please perform `editmap` first", "effect");
            return;
        }

        List<Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));
        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)
                        && p_command.checkRequiredKeysPresent(GameConstants.OPERATION, l_map)) {
                    d_mapService.editFunctions(d_gameState, l_map.get(GameConstants.ARGUMENTS),
                            l_map.get(GameConstants.OPERATION), 1);
                } else {
                    throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_EDITCONTINENT);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        List<java.util.Map<String, String>> l_operations_list = p_command.getTaskandArguments();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionLogHandler(d_gameState));

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_EDITMAP);
        } else {
            for (Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(GameConstants.ARGUMENTS, l_map)) {
                    d_mapService.editMap(d_gameState, l_map.get(GameConstants.ARGUMENTS));
                } else {
                    throw new InvalidCommand(GameConstants.INVALID_COMMAND_ERROR_EDITMAP);
                }
            }
        }
    }
}
