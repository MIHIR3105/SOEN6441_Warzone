package Services;


import Models.Player;

import java.util.List;

/**
 * This class has services for Player to find the sepcific methods to be used for players in the game.
 * @author Darshan Kansara
 */
public class PlayerService {

    /**
     *
     * @param p_playerList list of total players in the game.
     * @param p_playerName player name to be checked.
     * @return true if the player is unique or false.
     */
    public boolean isPlayerNameUnique(List<Player> p_playerList, String p_playerName) {
        boolean l_isUnique = true;
        if (!(p_playerList == null)) {
            for (Player l_player : p_playerList) {
                if (l_player.getPlayerName().equalsIgnoreCase(p_playerName)) {
                    l_isUnique = false;
                    break;
                }
            }
        }
        return l_isUnique;
    }

}
