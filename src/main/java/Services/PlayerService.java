package Services;


import Models.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has services for Player to find the specific methods to be used for players in the game.
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
     * @param p_playerNameAlreadyExists true if player to be added already exists
     */
    private void addPlayer(List<Player> p_updatedPlayers, String p_playerNameToBeAdded,
                               boolean p_playerNameAlreadyExists) {
        if (p_playerNameAlreadyExists) {
            System.out.print("Player with name : " + p_playerNameToBeAdded + "  exists already.");
        } else {
            Player l_addNewPlayer = new Player(p_playerNameToBeAdded);
            p_updatedPlayers.add(l_addNewPlayer);
            System.out.println("Player with name : " + p_playerNameToBeAdded + "  added successfully.");
        }
    }

    /**
     * the method removes the existing game player or shows the Player does not exist in the console
     * @param p_existingPlayerList list of existing players
     * @param p_updatedPlayers updated player list with newly added player
     * @param p_playerNameToBeRemoved new player name to be removed
     * @param p_playerNameAlreadyExists false if player to be removed does not exist
     */
    private void removePlayer(List<Player> p_existingPlayerList, List<Player> p_updatedPlayers,
                              String p_playerNameToBeRemoved, boolean p_playerNameAlreadyExists) {
        if (p_playerNameAlreadyExists) {
            for (Player l_player : p_existingPlayerList) {
                if (l_player.getPlayerName().equalsIgnoreCase(p_playerNameToBeRemoved)) {
                    p_updatedPlayers.remove(l_player);
                    System.out.println("Player with name : " + p_playerNameToBeRemoved + " has been removed successfully.");
                }
            }
        } else {
            System.out.print("Player with name : " + p_playerNameToBeRemoved + " does not Exist.");
        }
    }

    /**
     * The method can add and remove players at the same time
     * @param p_playerList list of existing players
     * @param p_operation type of operation to be performed
     * @param p_argument arguments which gives list of players
     * @return the updated list of players after performing add or delete operation
     */
    public List<Player> addOrRemovePlayers(List<Player> p_playerList, String p_operation, String p_argument) {
        List<Player> l_updatedPlayers = new ArrayList<>();
        if (!(p_playerList == null))
            l_updatedPlayers.addAll(p_playerList);
        String l_enteredPlayerName = p_argument.split(" ")[0];
        boolean l_playerNameAlreadyExist = !isPlayerNameUnique(p_playerList, l_enteredPlayerName);

        switch (p_operation.toLowerCase()) {
            case "add":
                addPlayer(l_updatedPlayers, l_enteredPlayerName, l_playerNameAlreadyExist);
                break;
            case "remove":
                removePlayer(p_playerList, l_updatedPlayers, l_enteredPlayerName, l_playerNameAlreadyExist);
                break;
            default:
                System.out.println("Invalid Operation on Player list");
        }
        return l_updatedPlayers;
    }

}
