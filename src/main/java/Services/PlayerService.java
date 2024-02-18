package Services;


import Models.Player;

import java.util.List;

/**
 * This class has services for Player to find the sepcific methods to be used for players in the game.
 *
 * @author Prachi Patel
 */
public class PlayerService {

    /**
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

    /**
     * Method to add a player to the Game.
     *
     * @param p_updatedPlayers         updated player list with newly added player
     * @param p_playerNameToBeAdded    new player name to be added
     * @param p_playerNameAlreadyExist true if player to be added already exists
     */
    private void addPlayer(List<Player> p_updatedPlayers, String p_playerNameToBeAdded,
                               boolean p_playerNameAlreadyExist) {
        if (p_playerNameAlreadyExist) {
            System.out.print("Player with name : " + p_playerNameToBeAdded + "  exists already.");
        } else {
            Player l_addNewPlayer = new Player(p_playerNameToBeAdded);
            p_updatedPlayers.add(l_addNewPlayer);
            System.out.println("Player with name : " + p_playerNameToBeAdded + "  added successfully.");
        }
    }
}
