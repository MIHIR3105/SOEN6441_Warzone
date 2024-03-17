package Models;

import java.io.IOException;

import Controllers.GameEngine;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Services.MapService;
import Services.PlayerService;
import Utils.Command;

/**
 * This Interface enforces the method requirement for Each Game Phase.
 */
public abstract class Phase {
    /**
     * d_gameState stores the information about current GamePlay.
     */
    GameState d_gameState;

    /**
     * d_gameState stores the information about current GamePlay.
     */
    GameEngine d_gameEngine;

    /**
     * d_mapService instance is used to handle load, read, parse, edit, and save map
     * file.
     */
    MapService d_mapService = new MapService();

    /**
     * Player Service instance to edit players and issue orders.
     */
    PlayerService d_playerService = new PlayerService();

    /**
     * it is a flag to check if map is loaded.
     */
    boolean l_isMapLoaded;

    /**
     * Constructor to initialize the value of current game engine.
     *
     * @param p_gameEngine game engine instance to update state
     * @param p_gameState  game engine instance to game state
     */
    public Phase(GameEngine p_gameEngine, GameState p_gameState) {
        d_gameEngine = p_gameEngine;
        d_gameState = p_gameState;
    }

    /**
     * getD_gameState is a getter method to get current game state.
     *
     * @return the current game state
     */
    public GameState getD_gameState() {
        return d_gameState;
    }

    /**
     * setD_gameState is a setter method for current game state.
     *
     * @param p_gameState game state instance to set for phase
     */
    public void setD_gameState(GameState p_gameState) {
        d_gameState = p_gameState;
    }

    /**
     * Method to handle command methods handles all state specific commands that can be entered by user.
     *
     * @param p_enteredCommand command entered by the user in CLI
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap     indicates map is invalid
     * @throws IOException    indicates failure in I/O operation
     */
    public void handleCommand(String p_enteredCommand) throws InvalidMap, InvalidCommand, IOException {
        commandHandler(p_enteredCommand, null);
    }

    /**
     * Method to handle command methods handles all state specific commands that can be entered by user.
     *
     * @param p_enteredCommand command entered by the user in CLI
     * @param p_player         instance to Player Object
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap     indicates map is invalid
     * @throws IOException    indicates failure in I/O operation
     */
    public void handleCommand(String p_enteredCommand, Player p_player) throws InvalidMap, InvalidCommand, IOException {
        commandHandler(p_enteredCommand, p_player);
    }

    /**
     * Method that processes the command entered by user and redirects them to specific phase implementations.
     *
     * @param p_enteredCommand command entered by the user in CLI
     * @param p_player         instance to Player Object
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap     indicates map is invalid
     * @throws IOException    indicates failure in I/O operation
     */
    private void commandHandler(String p_enteredCommand, Player p_player) throws InvalidMap, InvalidCommand, IOException {
        Command l_command = new Command(p_enteredCommand);
        String l_rootCommand = l_command.getMainCommand();
        l_isMapLoaded = d_gameState.getD_map() != null;

        d_gameState.updateLog(l_command.getD_command(), "command");

        switch (l_rootCommand) {
            case "editmap": {
                doMapEdit(l_command, p_player);
                break;
            }
            case "editcontinent": {
                doEditContinent(l_command, p_player);
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
            case "editcountry": {
                doEditCountry(l_command, p_player);
                break;
            }
            case "editneighbor": {
                doEditNeighbour(l_command, p_player);
                break;
            }
            case "gameplayer": {
                createPlayers(l_command, p_player);
                break;
            }
            case "assigncountries": {
                assignCountries(l_command, p_player);
                break;
            }
            case "showmap": {
                doShowMap(l_command, p_player);
                break;
            }
            case "deploy": {
                doCreateDeploy(p_enteredCommand, p_player);
                break;
            }
            case "advance": {
                doAdvance(p_enteredCommand, p_player);
                break;
            }
            case "airlift":
            case "blockade":
            case "negotiate":
            case "bomb": {
                doCardHandle(p_enteredCommand, p_player);
                break;
            }

            case "exit": {
                d_gameEngine.setD_gameEngineLog("Exit Command Entered, Game Ends!", "effect");
                System.exit(0);
                break;
            }
            default: {
                d_gameEngine.setD_gameEngineLog("Invalid Command", "effect");
                break;
            }
        }
    }

    /**
     * Method to handle the Card Commands.
     *
     * @param p_enteredCommand String of entered Command
     * @param p_player         player instance
     * @throws IOException Io exception
     */
    protected abstract void doCardHandle(String p_enteredCommand, Player p_player) throws IOException;

    /**
     * Method to handle the show map command.
     *
     * @param p_command command entered by user
     * @param p_player  instance of player object
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap     indicates map is invalid
     * @throws IOException    indicates failure in I/O operation
     */
    protected abstract void doShowMap(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap;

    /**
     * Method to handle the advance order in game play.
     *
     * @param p_command command entered by user
     * @param p_player  instance of player object
     * @throws IOException indicates failure in I/O operation
     */
    protected abstract void doAdvance(String p_command, Player p_player) throws IOException;

    /**
     * This is the main method executed on phase change.
     */
    public abstract void initPhase();

    /**
     * Method to handle the deploy order in gameplay.
     *
     * @param p_command command entered by user
     * @param p_player  instance of player object
     * @throws IOException indicates failure in I/O operation
     */
    protected abstract void doCreateDeploy(String p_command, Player p_player) throws IOException;

    /**
     * Method to Log and Print if the command can't be executed in current phase.
     */
    public void printInvalidCommandInState() {
        d_gameEngine.setD_gameEngineLog("Invalid Command in Current State", "effect");
    }

    /**
     * Method to execute the logic in loop to play the
     * actual game and track the execution of the army
     * and assignment of the countries.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap     indicates map is invalid
     * @throws IOException    indicates failure in I/O operation
     */
    protected abstract void assignCountries(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap;


    /**
     * Method to add players in the game.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap     indicates map is invalid
     * @throws IOException    indicates failure in I/O operation
     */
    protected abstract void createPlayers(Command p_command, Player p_player) throws InvalidCommand, IOException, InvalidMap;


    /**
     * Basic validation of <strong>editneighbor</strong> command for checking
     * required arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap     indicates map is invalid
     * @throws IOException    handles File I/O Exception
     */
    protected abstract void doEditNeighbour(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Basic validation of <strong>editcountry</strong> command for checking
     * required arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap     indicates map is invalid
     * @throws IOException    handles File I/O Exception
     * @throws InvalidMap     indicates map is invalid
     */
    protected abstract void doEditCountry(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Basic validation of <strong>validatemap</strong> command for checking
     * required arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws InvalidCommand indicates when command is invalid
     * @throws InvalidMap     indicates when map is invalid
     * @throws IOException    indicates failure in I/O operation
     */
    protected abstract void doValidateMap(Command p_command, Player p_player) throws InvalidMap, InvalidCommand, IOException;

    /**
     * Basic validation of <strong>loadmap</strong> command for checking required
     * arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws InvalidMap     indicates Map Object Validation failure
     * @throws InvalidCommand indicates when command is invalid
     * @throws IOException    indicates failure in I/O operation
     */
    protected abstract void doLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Basic validation of <strong>savemap</strong> command for checking required
     * arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws InvalidMap     indicates when map is invalid
     * @throws InvalidCommand indicates when command is invalid
     * @throws IOException    indicates failure in I/O operation
     */
    protected abstract void doSaveMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException;

    /**
     * Basic validation of <strong>editcontinent</strong> command for checking
     * required arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws IOException    indicates failure in I/O operation
     * @throws InvalidCommand indicates command is invalid
     * @throws InvalidMap     indicates map is invalid
     */
    protected abstract void doEditContinent(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap;


    /**
     * Basic validation of <strong>editmap</strong> command for checking required
     * arguments and redirecting control to model for actual processing.
     *
     * @param p_command command entered by the user on CLI
     * @param p_player  instance of Player Object
     * @throws IOException    indicates when failure in I/O operation
     * @throws InvalidMap     indicates Map Object Validation failure
     * @throws InvalidCommand indicates when command is invalid
     */
    protected abstract void doMapEdit(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap;

}
