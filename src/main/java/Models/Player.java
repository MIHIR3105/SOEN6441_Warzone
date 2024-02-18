package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has the data members and functions of player.
 * @author Prachi Patel
 */
public class Player {

    /**
     * Name of the player.
     */
    private String d_name;

    /**
     * List of countries under the ownership of the player.
     */
    List<Country> d_countryList;

    /**
     * List of Continents under the ownership of the player.
     */
    List<Continent> d_continentList;

    /**
     * List of orders given by the player.
     */
    List<Order> d_orderList;

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
    public List<Order> getD_ordersToExecute() {
        return d_orderList;
    }


    /**
     * This setter is used to set execute orders player.
     *
     * @param p_ordersToExecute set execute orders.
     */
    public void setD_ordersToExecute(List<Order> p_ordersToExecute) {
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
    public List<String> getCountryNames(){
        List<String> l_countryNames=new ArrayList<String>();
        for(Country c: d_countryList){
            l_countryNames.add(c.d_countryName);
        }
        return l_countryNames;
    }



    /**
     * Retrieves the list of continent names owned by the player.
     *
     * @return list of continent names
     */
    public List<String> getContinentNames(){
        List<String> l_continentNames = new ArrayList<String>();
        if (d_continentList != null) {
            for(Continent c: d_continentList){
                l_continentNames.add(c.d_continentName);
            }
            return l_continentNames;
        }
        return null;
    }



}
