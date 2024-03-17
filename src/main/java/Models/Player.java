package Models;

import Constants.ApplicationConstants;
import Services.PlayerService;
import Utils.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class has the data members and functions of player.
 *
 * @author Prachi Patel
 */
public class Player {

    /**
     * Name of the player.
     */
    private String d_name;
    /**
     * String holding Log for individual Player methods.
     */
    String d_playerLog;

    /**
     * List of countries under the ownership of the player.
     */
    List<Country> d_countryList;
    /**
     * List of players to not attack if negotiated with.
     */
    List<Player> d_negotiatedWith = new ArrayList<Player>();

    /**
     * Name of the card Player owns.
     */
    List<String> d_cardsOwnedByPlayer = new ArrayList<String>();
    /**
     * List of Continents under the ownership of the player.
     */
    List<Continent> d_continentList;


    /**
     * List of orders given by the player.
     */
    List<Order> d_orderList;
    /**
     * If the per turn card is assigned already.
     */
    boolean d_oneCardPerTurn = false;
    /**
     * Number of armies allocated to the player.
     */
    Integer d_noOfArmies;

    /**
     * The constructor has param used to create player
     * with name and be deafault armies.
     *
     * @param p_playerName name of the player.
     */
    public Player(String p_playerName) {
        this.d_name = p_playerName;
        this.d_noOfArmies = 0;
        this.d_orderList = new ArrayList<>();
    }


    /**
     * This is no arg constructor.
     */
    public Player() {

    }

    /**
     * This getter is used to get player's name.
     *
     * @return return player name.
     */
    public String getPlayerName() {
        return d_name;
    }

    /**
     * This setter is used to set player's p_name.
     *
     * @param p_name set player name.
     */
    public void setPlayerName(String p_name) {
        this.d_name = p_name;
    }


    /**
     * This getter is used to get list of countries owned by player.
     *
     * @return return countries owned by player.
     */
    public List<Country> getD_coutriesOwned() {
        return d_countryList;
    }


    /**
     * This setter is used to set list of countries owned by player.
     *
     * @param p_coutriesOwned set countries owned by player.
     */
    public void setD_coutriesOwned(List<Country> p_coutriesOwned) {
        this.d_countryList = p_coutriesOwned;
    }


    /**
     * This getter is used to get list of continents owned by player.
     *
     * @return return list of continents owned by player.
     */
    public List<Continent> getD_continentsOwned() {
        return d_continentList;
    }


    /**
     * This setter is used to set list of continents owned by player.
     *
     * @param p_continentsOwned set continents owned by player.
     */
    public void setD_continentsOwned(List<Continent> p_continentsOwned) {
        this.d_continentList = p_continentsOwned;
    }

    /**
     * This getter is used to get execute orders of player.
     *
     * @return return execute orders.
     */
    public List<Order> getD_orderList() {
        return d_orderList;
    }


    /**
     * This setter is used to set execute orders player.
     *
     * @param p_ordersToExecute set execute orders.
     */
    public void setD_orderList(List<Order> p_ordersToExecute) {
        this.d_orderList = p_ordersToExecute;
    }


    /**
     * This getter is used to get allocated armies of player.
     *
     * @return return allocated armies of player.
     */

    public Integer getD_noOfUnallocatedArmies() {
        return d_noOfArmies;
    }


    /**
     * This setter is used to set number of allocated armies to player.
     *
     * @param p_numberOfArmies set number of armies to player.
     */
    public void setD_noOfUnallocatedArmies(Integer p_numberOfArmies) {
        this.d_noOfArmies = p_numberOfArmies;
    }


    /**
     * Extracts the list of names of countries owned by the player.
     *
     * @return list of country names
     */
    public List<String> getCountryNames() {
        List<String> l_countryNames = new ArrayList<String>();
        for (Country c : d_countryList) {
            l_countryNames.add(c.d_countryName);
        }
        return l_countryNames;
    }


    /**
     * Method to retrieve the list of continent names owned by the player.
     *
     * @return list of continent names
     */
    public List<String> getContinentNames() {
        List<String> l_continentNames = new ArrayList<String>();
        if (d_continentList != null) {
            for (Continent c : d_continentList) {
                l_continentNames.add(c.d_continentName);
            }
            return l_continentNames;
        }
        return null;
    }
    /**
     * Prints and writes the player log.
     *
     * @param p_playerLog String as log message
     * @param p_typeLog Type of log : error, or log
     */
    public void setD_playerLog(String p_playerLog, String p_typeLog) {
        this.d_playerLog = p_playerLog;
        if(p_typeLog.equals("error"))
            System.err.println(p_playerLog);
        else if(p_typeLog.equals("log"))
            System.out.println(p_playerLog);
    }

    /**
     * Sets the Per Turn Card allocated bool.
     *
     * @param p_value Bool to Set.
     */
    public void setD_oneCardPerTurn(Boolean p_value){
        this.d_oneCardPerTurn = p_value;
    }
    /**
     * This method will assign any random card from the set of available cards to
     * the player once he conquers a territory.
     *
     */
    public void assignCard() {
        if (!d_oneCardPerTurn) {
            Random l_random = new Random();
            this.d_cardsOwnedByPlayer.add(ApplicationConstants.CARDS.get(l_random.nextInt(ApplicationConstants.SIZE)));
            this.setD_playerLog("Player: "+ this.d_name+ " has earned card as reward for the successful conquest- " + this.d_cardsOwnedByPlayer.get(this.d_cardsOwnedByPlayer.size()-1), "log");
            this.setD_oneCardPerTurn(true);
        }else{
            this.setD_playerLog("Player: "+this.d_name+ " has already earned maximum cards that can be allotted in a turn", "error");
        }
    }
    /**
     * Remove the card which is used.
     *
     * @param p_cardName name of the card to remove.
     */
    public void removeCard(String p_cardName){
        this.d_cardsOwnedByPlayer.remove(p_cardName);
    }

    /**
     * Checks if the order issued on country is possible or not.
     *
     * @param p_targetCountryName country to attack
     * @return bool if it can attack
     */
    public boolean negotiationValidation(String p_targetCountryName){
        boolean l_canAttack = true;
        for(Player p: d_negotiatedWith){
            if (p.getCountryNames().contains(p_targetCountryName))
                l_canAttack = false;
        }
        return l_canAttack;
    }

    /**
     * Method to issue order which takes order as an input and
     * add it to players unassigned orders pool.
     *
     * @throws IOException exception in reading inputs from user
     */
//    public void issue_order() throws IOException {
//        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
//        PlayerService l_playerService = new PlayerService();
//        System.out.println("\nPlease enter command to deploy reinforcement armies on the map for player : " + this.getPlayerName());
//        String l_commandEntered = l_reader.readLine();
//        Command l_command = new Command(l_commandEntered);
//
//        if (l_command.getMainCommand().equalsIgnoreCase("deploy") && l_commandEntered.split(" ").length == 3) {
//            l_playerService.createAndDeployOrder(l_commandEntered, this);
//        } else {
//            System.out.println("Invalid command encountered");
//        }
//    }

    /**
     * Gives the first order in the players list of orders, then removes it from the
     * list.
     *
     * @return Order first order from the list of player's order
     */
    public Order next_order() {
        if (this.d_orderList == null || this.d_orderList.isEmpty()) {
            return null;
        }
        Order l_order = this.d_orderList.get(0);
        this.d_orderList.remove(l_order);
        return l_order;
    }
}
