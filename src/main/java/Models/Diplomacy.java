package Models;

/**
=======
 * Handles diplomacy command.
 *
 * @author Prachi Patel
 */
public class Diplomacy implements Card{

    /**
     * Player issuing the negotiate order.
     */
    Player d_IssuingPlayer;
    /**
     * String with Player name to establish negotiation with.
     */
    String d_targetPlayer;

    /**
     * Records the execution log.
     */
    String d_orderExecutionLog;


    /**
     * Constructor to create diplomacy order.
     *
     * @param p_targetPlayer target player to negotiate with
     * @param p_IssuingPlayer negotiate issuing player.
     */
    public Diplomacy(String p_targetPlayer, Player p_IssuingPlayer){
        this.d_targetPlayer = p_targetPlayer;
        this.d_IssuingPlayer = p_IssuingPlayer;
    }

    @Override
    public Boolean checkValidOrder(GameState p_gameState) {
        return null;
    }

    @Override
    public void execute(GameState p_gameState) {

    }

    @Override
    public boolean valid(GameState p_gameState) {
        return false;
    }

    @Override
    public void printOrder() {

    }

    @Override
    public String orderExecutionLog() {
        return null;
    }

    @Override
    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType) {

    }

    @Override
    public String getOrderName() {
        return null;
    }
}
