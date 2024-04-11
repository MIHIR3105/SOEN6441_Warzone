package Models;

import Constants.GameConstants;
import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Services.MapService;
import Services.PlayerService;
import Utils.Command;

import java.io.IOException;
import java.io.Serializable;


/**
 * This Interface enforces the method requirement for Each Game Phase.
 *
 * @author Prachi Patel
 */
public abstract class Phase implements Serializable {

    /**
     * Stores the information about the current GamePlay.
     */
    GameState d_gameState;

    /**
     * Stores the information about the current GamePlay.
     */
    GameEngine d_gameEngine;

    /**
     * Flag to determine if the map is loaded.
     */
    boolean l_isMapLoaded;

    /**
     * Manages the map file, handling operations such as loading, reading, parsing, editing, and saving the map.
     */
    MapService d_mapService = new MapService();

    /**
     * Manages player-related operations, including editing players and issuing orders.
     */
    PlayerService d_playerService = new PlayerService();

    /**
     * Manages the tournament mode.
     */
    Tournament d_tournament = new Tournament();

    /**
     * Constructor for initializing the current game engine and game state.
     *
     * @param p_gameEngine Instance of the game engine to update the state
     * @param p_gameState  Instance of the game state
     */
    public Phase(GameEngine p_gameEngine, GameState p_gameState) {
        d_gameEngine = p_gameEngine;
        d_gameState = p_gameState;
    }

    /**
     * Retrieves the current game state.
     *
     * @return The current game state
     */
    public GameState getD_gameState() {
        return d_gameState;
    }

    /**
     * Setter method for the current game state.
     *
     * @param p_gameState The game state instance to set for the phase
     */
    public void setD_gameState(GameState p_gameState) {
        d_gameState = p_gameState;
    }


    /**
     * Handles the commands specific to the state entered by the user.
     *
     * @param p_enteredCommand Command entered by the user in the Command Line Interface (CLI)
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     */
    public void handleCommand(String p_enteredCommand) throws IOException, InvalidCommand, InvalidMap {
        commandHandler(p_enteredCommand, null);
    }

    /**
     * Handles state-specific commands entered by the user.
     *
     * @param p_enteredCommand Command entered by the user in the Command Line Interface (CLI)
     * @param p_player         Player instance
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     */
    public void handleCommand(String p_enteredCommand, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        commandHandler(p_enteredCommand, p_player);
    }

    /**
     * Redirect to specific phase implementations the command which were entered  by user.
     *
     * @param p_enteredCommand Command entered by the user in the Command Line Interface (CLI)
     * @param p_player         Player instance
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     */
    private void commandHandler(String p_enteredCommand, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        Command l_command = new Command(p_enteredCommand);
        String l_rootCommand = l_command.getMainCommand();
        l_isMapLoaded = d_gameState.getD_map() != null;

        d_gameState.updateLog(l_command.getcommand(), GameConstants.COMMAND);
        switch (l_rootCommand) {
            case "editmap": {
                doMapEdit(l_command, p_player);
                break;
            }
            case "editcontinent": {
                doEditContinent(l_command, p_player);
                break;
            }
            case "deploy": {
                doCreateDeploy(p_enteredCommand, p_player);
                break;
            }
            case "gameplayer": {
                createPlayers(l_command, p_player);
                break;
            }
            case "savemap": {
                doSaveMap(l_command, p_player);
                break;
            }
            case "loadmap": {
                doLoadMap(l_command, p_player);
                break;
            }
            case "validatemap": {
                doValidateMap(l_command, p_player);
                break;
            }
            case "advance": {
                doAdvance(p_enteredCommand, p_player);
                break;
            }
            case "editcountry": {
                doEditCountry(l_command, p_player);
                break;
            }
            case "editneighbor": {
                doEditNeighbour(l_command, p_player);
                break;
            }
            case "savegame": {
                doSaveGame(l_command, p_player);
                break;
            }
            case "loadgame": {
                doLoadGame(l_command, p_player);
                break;
            }
            case "airlift":
            case "blockade":
            case "negotiate":
            case "bomb": {
                doCardHandle(p_enteredCommand, p_player);
                break;
            }
            case "assigncountries": {
                doAssignCountries(l_command, p_player, false, d_gameState);
                break;
            }
            case "showmap": {
                doShowMap(l_command, p_player);
                break;
            }
            case "tournament": {
                tournamentGamePlay(l_command);
                break;
            }

            case "nocommand": {
                break;
            }
            case "exit": {
                d_gameEngine.setD_gameEngineLog("Exit Command Entered, Game Ends!", GameConstants.OUTCOME);
                System.exit(0);
                break;
            }
            default: {
                d_gameEngine.setD_gameEngineLog(GameConstants.INVALIDCOMMAND, GameConstants.OUTCOME);
                break;
            }
        }
    }

    /**
     * Conducting essential validation of the 'validatemap' command to ensure necessary arguments are provided,
     * and then transferring control to the model for actual processing.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void doValidateMap(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap;

    /**
     * Method to validate map.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void doEditNeighbour(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap;

    /**
     * Performing initial validation of the 'editcountry' command to confirm the presence of mandatory arguments,
     * followed by transferring control to the model for execution.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void doEditCountry(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap;

    /**
     * Conducting primary validation on the 'loadmap' command to ensure necessary arguments are present,
     * and then transferring control to the model for processing.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void doLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Performing fundamental validation on the 'savemap' command to verify essential arguments
     * and then directing control to the model for processing.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void doSaveMap(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap;


    /**
     * Preliminary validation of the 'editmap' command to ensure necessary arguments are provided,
     * followed by transferring control to the model for processing.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void doMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap;

    /**
     * Initial validation of the 'editcontinent' command to verify the presence of essential arguments,
     * followed by directing control to the model for processing.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void doEditContinent(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap;

    /**
     * Basic validation of gameplayer command for checking required arguments and redirecting control to the model for adding or removing players.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void createPlayers(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Handles the deployment order in the gameplay.
     *
     * @param p_command Command entered by the user
     * @param p_player  Instance of the player object
     * @throws IOException Indicates a failure
     */
    protected abstract void doCreateDeploy(String p_command, Player p_player) throws IOException;


    /**
     * Handles the advance order in the gameplay.
     * @throws IOException Indicates a failure
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void doAdvance(String p_command, Player p_player) throws IOException;

    /**
     * Handels the tournament gameplay.
     * @throws InvalidMap Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @param p_command command parameter
     */
    protected abstract void tournamentGamePlay(Command p_command) throws InvalidCommand, InvalidMap;

    /**
     * Handles Game Load Feature.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void doLoadGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Handles Game Save Feature
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_command command parameter
     * @param p_player player parameter
     */
    protected abstract void doSaveGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException;


    /**
     * Handles the card commands.
     * @throws IOException Indicates a failure
     * @param p_player player parameter
     * @param p_enteredCommand param for enteredcommand
     */
    protected abstract void doCardHandle(String p_enteredCommand, Player p_player) throws IOException;

    /**
     * Performing preliminary validation on the assigncountries function to ensure all necessary arguments are present,
     * and then transferring control to the model responsible for allocating countries to players.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_player param for player
     * @param p_command param for command
     * @param p_isTournamentMode param for tournament mode
     * @param p_gameState param for game state
     */
    protected abstract void doAssignCountries(Command p_command, Player p_player, boolean p_isTournamentMode, GameState p_gameState) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Handles the 'show map' command.
     * @throws IOException Indicates a failure
     * @throws InvalidCommand Indicates a failure
     * @throws InvalidMap Indicates a failure
     * @param p_command param for command
     * @param p_player param for player
     */
    protected abstract void doShowMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Method to log and print if the command can't be executed in the current phase.
     */
    public void printInvalidCommandInState() {
        d_gameEngine.setD_gameEngineLog("Invalid Command in Current State", GameConstants.OUTCOME);
    }

    /**
     * This method signifies the main functionality executed on phase change.
     * @param p_isTournamentMode param for tournament mode
     */
    public abstract void initPhase(boolean p_isTournamentMode);

}
