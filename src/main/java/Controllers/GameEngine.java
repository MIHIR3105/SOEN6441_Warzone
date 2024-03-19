package Controllers;


import Models.*;


/**
 * Entry point of the Game which keeps the track of current Game State.*
 *
 * @author Prachi Patel
 */
public class GameEngine {

    /**
     * d_gameState stores the information about current Game.
     */
    public GameState d_gameState = new GameState();

    /**
     * d_currentPhase is the current game play phase as per state pattern.
     */
    Phase d_currentPhase = new StartUpPhase(this, d_gameState);


    /**
     * Method that is used to update context.
     *
     * @param p_phase new Phase to set in Game context
     */
    private void setD_CurrentPhase(Phase p_phase) {
        d_currentPhase = p_phase;
    }


    /**
     * Method to get the current Phase of Game Context.
     *
     * @return current Phase of Game Context
     */
    public Phase getD_CurrentPhase() {
        return d_currentPhase;
    }


    /**
     * Method to update the current phase to Issue Order Phase as per State Pattern.
     */
    public void setIssueOrderPhase() {
        this.setD_gameEngineLog("Issue Order Phase", "phase");
        setD_CurrentPhase(new IssueOrderPhase(this, d_gameState));
        getD_CurrentPhase().initPhase();
    }

    /**
     * this methods updates the current phase to Order Execution Phase as per State Pattern.
     */
    public void setOrderExecutionPhase(){
        this.setD_gameEngineLog("Order Execution Phase", "phase");
        setD_CurrentPhase(new OrderExecutionPhase(this, d_gameState));
        getD_CurrentPhase().initPhase();
    }


    /**
     * Method that Shows and Writes GameEngine Logs.
     *
     * @param p_gameEngineLog String of Log message.
     * @param p_logType       Type of Log.
     */
    public void setD_gameEngineLog(String p_gameEngineLog, String p_logType) {
        d_currentPhase.getD_gameState().updateLog(p_gameEngineLog, p_logType);
        String l_consoleLogger = p_logType.toLowerCase().equals("phase")
                ? "\n=+=+=+=+=+=+=+=+= " + p_gameEngineLog + " =+=+=+=+=+=+=+=+=\n"
                : p_gameEngineLog;
        System.out.println(l_consoleLogger);
    }

    /**
     * The main method responsible for accepting command from users and redirecting
     * those to corresponding logical flows.
     *
     * @param p_args the program doesn't use default command line arguments
     */
    public static void main(String[] p_args) {
        GameEngine l_game = new GameEngine();

        l_game.getD_CurrentPhase().getD_gameState().updateLog("Initializing the Game ......" + System.lineSeparator(), "start");
        l_game.setD_gameEngineLog("Game Startup Phase", "phase");
        l_game.getD_CurrentPhase().initPhase();

    }


}
