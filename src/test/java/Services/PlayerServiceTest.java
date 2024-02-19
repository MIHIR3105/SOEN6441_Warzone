package Services;

import Models.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Services class for Player to fetch the sepcific methods to be used for players in the game
 * @author Aashvi Zala
 */

public class PlayerServiceTest {

    /**
     * test to if the map is loaded or not
     */
    @Test
    public void isMapLoaded() {
        GameState l_gameState= new GameState();
        Player l_player1= new Player("Parth");
        Player l_player2= new Player("Jarvis");
        List l_listOfPlayers= new ArrayList<>();
        l_listOfPlayers.add(l_player1);
        l_listOfPlayers.add(l_player2);
        l_gameState.setD_players(l_listOfPlayers);
        PlayerService l_playerServices= new PlayerService();
        Boolean l_isMapload=l_playerServices.isMapLoaded(l_gameState);
        assertEquals(false,l_isMapload);

    }

    /**
     * test to check if there are unassigned Armies or not
     */
    @Test
    public void unassignedArmiesExists() {
        PlayerService l_playerServices= new PlayerService();
        Player l_player1= new Player();
        Player l_player2= new Player();
        Player l_player3= new Player();
        l_player1.setPlayerName("Mihir");
        l_player2.setPlayerName("Yashesh");
        l_player3.setPlayerName("Vaibhav");
        List<Player> l_listOfPlayers= new ArrayList<>();
        l_listOfPlayers.add(l_player1);
        l_listOfPlayers.add(l_player2);
        l_listOfPlayers.add(l_player3);
        l_player1.setD_noOfUnallocatedArmies(0);
        l_player2.setD_noOfUnallocatedArmies(0);
        l_player3.setD_noOfUnallocatedArmies(0);
        Boolean l_unassignedArmies= l_playerServices.unassignedArmiesExists(l_listOfPlayers);
        assertEquals(false,l_unassignedArmies);
    }

    /**
     * Test to validate that the name of the player is unique or not
     */
    @Test
    public void isPlayerNameUnique() {
        Player l_player1= new Player();
        Player l_player2= new Player();
        Player l_player3= new Player();
        Player l_player4= new Player();
        Player l_player5= new Player();
        Player l_player6= new Player();
        l_player1.setPlayerName("Prachi");
        l_player2.setPlayerName("Yashesh");
        l_player3.setPlayerName("Aashvi");
        l_player4.setPlayerName("Vaibhav");
        l_player5.setPlayerName("Mihir");
        List<Player> l_listOfPlayers= new ArrayList<>();
        l_listOfPlayers.add(l_player1);
        l_listOfPlayers.add(l_player2);
        l_listOfPlayers.add(l_player3);
        l_listOfPlayers.add(l_player4);
        l_listOfPlayers.add(l_player5);
        l_listOfPlayers.add(l_player6);
        PlayerService l_playerService = new PlayerService();
        Boolean l_isUnique=l_playerService.isPlayerNameUnique(l_listOfPlayers,"Prachi");
        assertEquals(false,l_isUnique);
    }

    /**
     * Test to check the availability of the players
     */
    @Test
    public void checkPlayersAvailability() {
        GameState l_gameState = new GameState();
        Player l_player1= new Player("Aashvi");
        Player l_player2= new Player("Prachi");
        List l_listOfPlayers= new ArrayList<>();
        l_listOfPlayers.add(l_player1);
        l_listOfPlayers.add(l_player2);
        l_gameState.setD_players(l_listOfPlayers);
        PlayerService l_playerServices= new PlayerService();
        Boolean l_isAvailable = l_playerServices.checkPlayersAvailability(l_gameState);
        assertEquals(true,l_isAvailable);
    }



}



