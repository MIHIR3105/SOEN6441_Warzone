package Services;


import Models.*;
import Utils.CommonUtil;

import java.util.*;

/**
 * This class has services for Player to find the specific methods to be used for players in the game.
 *
 * @author Prachi Patel
 */
public class PlayerService {

    /**
     * Log of Player operations in player methods.
     */
    String d_playerLog;

    /**
     * Country Assignment Log.
     */
    String d_assignmentLog = "Country/Continent Assignment:";


    /**
     * Method to check if the player is unique.
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

    /**
     * Method to add a player to the Game.
     *
     * @param p_updatedPlayers          updated player list with newly added player
     * @param p_playerNameToBeAdded     ew player name to be added
     * @param p_playerNameAlreadyExists true if player to be added already exists
     */
    private void addPlayer(List<Player> p_updatedPlayers, String p_playerNameToBeAdded,
                           boolean p_playerNameAlreadyExists) {
        if (p_playerNameAlreadyExists) {
            setD_playerLog("Player with name : " + p_playerNameToBeAdded + " already Exists. Changes are not made.");
        } else {
            Player l_addNewPlayer = new Player(p_playerNameToBeAdded);
            p_updatedPlayers.add(l_addNewPlayer);
            setD_playerLog("Player with name : " + p_playerNameToBeAdded + " has been added successfully.");
        }
    }

    /**
     * the method removes the existing game player or shows the Player does not exist in the console
     *
     * @param p_existingPlayerList      list of existing players
     * @param p_updatedPlayers          updated player list with newly added player
     * @param p_playerNameToBeRemoved   new player name to be removed
     * @param p_playerNameAlreadyExists false if player to be removed does not exist
     */
    private void removePlayer(List<Player> p_existingPlayerList, List<Player> p_updatedPlayers,
                              String p_playerNameToBeRemoved, boolean p_playerNameAlreadyExists) {
        if (p_playerNameAlreadyExists) {
            for (Player l_player : p_existingPlayerList) {
                if (l_player.getPlayerName().equalsIgnoreCase(p_playerNameToBeRemoved)) {
                    p_updatedPlayers.remove(l_player);
                    setD_playerLog("Player with name : " + p_playerNameToBeRemoved + " has been removed successfully.");
                }
            }
        } else {
            setD_playerLog("Player with name : " + p_playerNameToBeRemoved + " does not Exist. Changes are not made.");
        }
    }

    /**
     * Method to add or remove player at the same time.
     *
     * @param p_playerList list of existing players
     * @param p_operation  type of operation to be performed
     * @param p_argument   arguments which gives list of players
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
                setD_playerLog("Invalid Operation on Players list");
        }
        return l_updatedPlayers;
    }

    /**
     * Method to check the availability of players.
     *
     * @param p_gameState game state or phase of the current game
     * @return boolean if the players are available or not
     */
    public boolean checkPlayersAvailability(GameState p_gameState) {
        if (p_gameState.getD_players() == null || p_gameState.getD_players().isEmpty()) {
            setD_playerLog("Please add players before assigning countries");
            return false;
        }
        return true;
    }

    /**
     * Method to assign countries to the player.
     *
     * @param p_gameState game state or phase of the current game
     */
    public void assignCountries(GameState p_gameState) {
        if (!checkPlayersAvailability(p_gameState))
            return;

        List<Country> l_countries = p_gameState.getD_map().getD_countries();
        int l_countriesPerPlayer = Math.floorDiv(l_countries.size(), p_gameState.getD_players().size());

        this.doRandomCountryAssignment(l_countriesPerPlayer, l_countries, p_gameState.getD_players());
        this.doContinentAssignment(p_gameState.getD_players(), p_gameState.getD_map().getD_continents());
        System.out.println("Countries have been assigned to Players.");

    }
    /**
     * Performs random country assignment to all players.
     *
     * @param p_countriesPerPlayer countries which are to be assigned to each player
     * @param p_countries          list of all countries present in map
     * @param p_players            list of all available players
     * @param p_gameState		   current game state with map and player information
     */
    private void performRandomCountryAssignment(int p_countriesPerPlayer, List<Country> p_countries,
                                                List<Player> p_players, GameState p_gameState) {
        List<Country> l_unassignedCountries = new ArrayList<>(p_countries);
        for (Player l_pl : p_players) {
            if(!l_pl.getPlayerName().equalsIgnoreCase("Neutral")) {
                if (l_unassignedCountries.isEmpty())
                    break;
                // Based on number of countries to be assigned to player, it generates random
                // country and assigns to player
                for (int i = 0; i < p_countriesPerPlayer; i++) {
                    Random l_random = new Random();
                    int l_randomIndex = l_random.nextInt(l_unassignedCountries.size());
                    Country l_randomCountry = l_unassignedCountries.get(l_randomIndex);

                    if (l_pl.getD_coutriesOwned() == null)
                        l_pl.setD_coutriesOwned(new ArrayList<>());
                    l_pl.getD_coutriesOwned().add(p_gameState.getD_map().getCountryByName(l_randomCountry.getD_countryName()));
                    System.out.println("Player : " + l_pl.getPlayerName() + " is assigned with country : "
                            + l_randomCountry.getD_countryName());
                    d_assignmentLog += "\n Player : " + l_pl.getPlayerName() + " is assigned with country : "
                            + l_randomCountry.getD_countryName();
                    l_unassignedCountries.remove(l_randomCountry);
                }
            }
        }
        // If any countries are still left for assignment, it will redistribute those
        // among players
        if (!l_unassignedCountries.isEmpty()) {
            performRandomCountryAssignment(1, l_unassignedCountries, p_players, p_gameState);
        }
    }

    /**
     * Checks if player is having any continent as a result of random country
     * assignment.
     *
     * @param p_players    list of all available players
     * @param p_continents list of all available continents
     */
    public void performContinentAssignment(List<Player> p_players, List<Continent> p_continents) {
        for (Player l_pl : p_players) {
            List<String> l_countriesOwned = new ArrayList<>();
            if (!CommonUtil.isCollectionEmpty(l_pl.getD_coutriesOwned())) {
                l_pl.getD_coutriesOwned().forEach(l_country -> l_countriesOwned.add(l_country.getD_countryName()));

                for (Continent l_cont : p_continents) {
                    List<String> l_countriesOfContinent = new ArrayList<>();
                    l_cont.getD_countries().forEach(l_count -> l_countriesOfContinent.add(l_count.getD_countryName()));
                    if (l_countriesOwned.containsAll(l_countriesOfContinent)) {
                        if (l_pl.getD_continentsOwned() == null)
                            l_pl.setD_continentsOwned(new ArrayList<>());

                        l_pl.getD_continentsOwned().add(l_cont);
                        System.out.println("Player : " + l_pl.getPlayerName() + " is assigned with continent : "
                                + l_cont.getD_continentName());
                        d_assignmentLog += "\n Player : " + l_pl.getPlayerName() + " is assigned with continent : "
                                + l_cont.getD_continentName();
                    }
                }
            }
        }
    }
    /**
     * Method to randomly assign countries to the list of players.
     *
     * @param p_countriesPerPlayer number of countries per player to be assigned
     * @param p_countries          list of countries to be assigned
     * @param p_players            list of players
     */
    private void doRandomCountryAssignment(int p_countriesPerPlayer, List<Country> p_countries,
                                           List<Player> p_players) {
        List<Country> l_unassignedCountries = new ArrayList<>(p_countries);
        for (Player l_player : p_players) {
            if (l_unassignedCountries.isEmpty())
                break;
            // Based on number of countries to be assigned to player, it generates random
            // country and assigns to player
            for (int i = 0; i < p_countriesPerPlayer; i++) {
                Random l_random = new Random();
                int l_randomIndex = l_random.nextInt(l_unassignedCountries.size());
                Country l_randomCountry = l_unassignedCountries.get(l_randomIndex);

                if (l_player.getD_coutriesOwned() == null)
                    l_player.setD_coutriesOwned(new ArrayList<>());
                l_player.getD_coutriesOwned().add(l_randomCountry);
                System.out.println("Country : " + l_randomCountry.getD_countryName() + " is assigned to the player : "
                        + l_player.getPlayerName());
                //after assigning the country to the player
                //it is removed from the unassigned list.
                l_unassignedCountries.remove(l_randomCountry);
            }
        }
        // If any countries are still left for assignment, it will redistribute those
        // among players
        if (!l_unassignedCountries.isEmpty()) {
            doRandomCountryAssignment(1, l_unassignedCountries, p_players);
        }

    }

    /**
     * Method to assign the continent to the player.
     *
     * @param p_players    list of players
     * @param p_continents list of continents
     */
    private void doContinentAssignment(List<Player> p_players, List<Continent> p_continents) {
        for (Player l_player : p_players) {
            List<String> l_countriesOwned = new ArrayList<>();
            if (l_player.getD_coutriesOwned().size() != 0) {
                l_player.getD_coutriesOwned().forEach(l_country -> l_countriesOwned.add(l_country.getD_countryName()));

                for (Continent l_continent : p_continents) {
                    List<String> l_countriesOfContinent = new ArrayList<>();
                    l_continent.getD_countries().forEach(l_count -> l_countriesOfContinent.add(l_count.getD_countryName()));
                    if (l_countriesOwned.containsAll(l_countriesOfContinent)) {
                        if (l_player.getD_continentsOwned() == null)
                            l_player.setD_continentsOwned(new ArrayList<>());

                        l_player.getD_continentsOwned().add(l_continent);
                        System.out.println("The continent : " + l_continent.getD_continentName() + " is assigned with player : "
                                + l_player.getPlayerName());
                    }
                }
            }
        }
    }


    /**
     * Method to validate the armies that are deployed properly or not
     *
     * @param p_player     player object
     * @param p_noOfArmies number of armies
     * @return if the deployed armies are valid or invalid
     */
    public boolean validateDeployOrderArmies(Player p_player, String p_noOfArmies) {
        return p_player.getD_noOfUnallocatedArmies() < Integer.parseInt(p_noOfArmies);
    }

    public boolean validateOwnershipOfCountry(Player p_player, String p_country) {
        return p_player.getD_coutriesOwned().stream().noneMatch(x-> Objects.equals(x.getD_countryName(), p_country));
    }

    /**
     * Method calculates the army for a player who is currently available
     *
     * @param p_player player object
     * @return the calculated number of armies to the player
     */
    public int calculateArmiesForPlayer(Player p_player) {
        int l_armies = 0;
        if (p_player.getD_coutriesOwned().size() != 0) {
            l_armies = Math.max(3, Math.round((p_player.getD_coutriesOwned().size()) / 3));
        }
        if (p_player.getD_continentsOwned()!=null && p_player.getD_continentsOwned().size() != 0) {
            int l_continentCtrlValue = 0;
            for (Continent l_continent : p_player.getD_continentsOwned()) {
                l_continentCtrlValue = l_continentCtrlValue + l_continent.getD_continentValue();
            }
            l_armies = l_armies + l_continentCtrlValue;
        }
        return l_armies;
    }

    /**
     * Method to assign the armies in the game state and to the players.
     *
     * @param p_gameState game state or phase of the current game
     */
    public void assignArmies(GameState p_gameState) {
        for (Player l_player : p_gameState.getD_players()) {
            Integer l_armies = this.calculateArmiesForPlayer(l_player);
            System.out.println("Player : " + l_player.getPlayerName() + " has been assigned with " + l_armies + " armies");

            l_player.setD_noOfUnallocatedArmies(l_armies);
        }
    }


    /**
     * Method to check if ignored orders exists in the game.
     *
     * @param p_playerList players involved in game
     * @return boolean true if ignored orders exists with any of
     * the players or else false
     */
    public boolean ignoredOrdersExists(List<Player> p_playerList) {
        int l_totalIgnoredOrders = 0;
        for (Player l_player : p_playerList) {
            l_totalIgnoredOrders = l_totalIgnoredOrders + l_player.getD_orderList().size();
        }
        return l_totalIgnoredOrders != 0;
    }

    /**
     * Check if unexecuted orders exists in the game.
     *
     * @param p_playersList players involved in game
     * @return boolean true if unexecuted orders exists with any of the players or
     *         else false
     */
    public boolean unexecutedOrdersExists(List<Player> p_playersList) {
        int l_totalUnexecutedOrders = 0;
        for (Player l_player : p_playersList) {
            l_totalUnexecutedOrders = l_totalUnexecutedOrders + l_player.getD_orderList().size();
        }
        return l_totalUnexecutedOrders != 0;
    }

    /**
     * Method to check if there are any unassigned armies left or not
     *
     * @param p_playersList list of players available
     * @return true if there are any unassigned armies left or false
     */
    public boolean unassignedArmiesExists(List<Player> p_playersList) {
        int l_unassignedArmies = 0;
        for (Player l_player : p_playersList) {
            l_unassignedArmies = l_unassignedArmies + l_player.getD_noOfUnallocatedArmies();
        }
        return l_unassignedArmies != 0;
    }

    /**
     * Method to check if the map is loaded properly.
     *
     * @param p_gameState game state or phase of the current game
     * @return true if map is loaded or false
     */
    public boolean isMapLoaded(GameState p_gameState) {
        return !(p_gameState.getD_map() == null);
    }


    /**
     * Checks if any of the player in game wants to give further order or not.
     *
     * @param p_playersList players involved in game
     * @return boolean whether there are more orders to give or not
     */
    public boolean checkForMoreOrders(List<Player> p_playersList) {
        for (Player l_player : p_playersList) {
            if(l_player.getD_moreOrders())
                return true;
        }
        return false;
    }

    /**
     * Resets each players information for accepting further orders.
     *
     * @param p_playersList players involved in game
     */
    public void resetPlayersFlag(List<Player> p_playersList) {
        for (Player l_player : p_playersList) {
            if (!l_player.getPlayerName().equalsIgnoreCase("Neutral"))
                l_player.setD_moreOrders(true);
            l_player.setD_oneCardPerTurn(false);
            l_player.resetNegotiation();
        }
    }


    /**
     * Method to update the list of players.
     *
     * @param p_gameState game state or phase of the current game
     * @param p_operation operation to update the list
     * @param p_argument  arguments which gives list of players
     */
    public void updatePlayers(GameState p_gameState, String p_operation, String p_argument) {
        if (!isMapLoaded(p_gameState)) {
            System.out.println("Kindly load the map first to add player: " + p_argument);
            return;
        }
        List<Player> l_updatedPlayers = this.addOrRemovePlayers(p_gameState.getD_players(), p_operation, p_argument);

        if (l_updatedPlayers != null) {
            p_gameState.setD_players(l_updatedPlayers);
        }
    }

    /**
     * Sets the Player Log in player methods.
     *
     * @param p_playerLog Player Operation Log.
     */
    public void setD_playerLog(String p_playerLog) {
        this.d_playerLog = p_playerLog;
        System.out.println(p_playerLog);
    }


}
