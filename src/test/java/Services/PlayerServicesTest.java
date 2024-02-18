package Services;

import Models.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Services class for Player to fetch the sepcific methods to be used for players in the game
 * @author Darshan Kansara
 */

public class PlayerServicesTest {
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

}

