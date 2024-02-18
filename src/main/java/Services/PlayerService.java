package Services;


import Models.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * This class has services for Player to find the specific methods to be used for players in the game.
 *
 * @author Prachi Patel
 */
public class PlayerService {

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
            System.out.print("Player with name : " + p_playerNameToBeAdded + "  exists already.");
        } else {
            Player l_addNewPlayer = new Player(p_playerNameToBeAdded);
            p_updatedPlayers.add(l_addNewPlayer);
            System.out.println("Player with name : " + p_playerNameToBeAdded + "  added successfully.");
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
                    System.out.println("Player with name : " + p_playerNameToBeRemoved + " is removed successfully.");
                }
            }
        } else {
            System.out.print("Player with name : " + p_playerNameToBeRemoved + " does not Exist.");
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
                System.out.println("Invalid Operation on Player list");
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
            System.out.println("Please add players before assigning countries");
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
            if (!l_player.getD_coutriesOwned().isEmpty()) {
                l_player.getD_coutriesOwned().forEach(l_country -> l_countriesOwned.add(l_country.getD_countryName()));

                for (Continent l_continent : p_continents) {
                    List<String> l_countriesOfContinent = new ArrayList<>();
                    l_continent.getD_countries().forEach(l_count -> l_countriesOfContinent.add(l_count.getD_countryName()));
                    if (new HashSet<>(l_countriesOwned).containsAll(l_countriesOfContinent)) {
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
     * Method to create and deploy order of the game.
     *
     * @param p_commandEntered get the parameter of the command given by the user
     * @param p_player         object of the player
     */
    public void createAndDeployOrder(String p_commandEntered, Player p_player) {
        List<Order> l_orders = p_player.getD_ordersToExecute().isEmpty() ? new ArrayList<>()
                : p_player.getD_ordersToExecute();
        String l_countryName = p_commandEntered.split(" ")[1];
        String l_noOfArmies = p_commandEntered.split(" ")[2];
        if (p_player.getD_noOfUnallocatedArmies() < Integer.parseInt(l_noOfArmies)) {
            System.out.println(
                    "Given [deploy] order cant be executed as armies in order exceeds player's unallocated armies");
        } else {
            Order l_orderObject = new Order(p_commandEntered.split(" ")[0], l_countryName,
                    Integer.parseInt(l_noOfArmies));
            l_orders.add(l_orderObject);
            p_player.setD_ordersToExecute(l_orders);
            Integer l_unallocatedArmies = p_player.getD_noOfUnallocatedArmies() - Integer.parseInt(l_noOfArmies);
            p_player.setD_noOfUnallocatedArmies(l_unallocatedArmies);
            System.out.println("Order has been added to queue for execution.");
        }
    }

    /**
     * Method to assign the armies in the game state and to the players.
     *
     * @param p_gameState game state or phase of the current game
     */
    public void assignArmies(GameState p_gameState) {
        for (Player l_player : p_gameState.getD_players()) {
            int l_armies = 0;
            if (!l_player.getD_coutriesOwned().isEmpty()) {
                l_armies = Math.max(3, Math.round((float) (l_player.getD_coutriesOwned().size()) / 3));
            }
            //if the player owns the continent add the continent control bonus.
            if (l_player.getD_continentsOwned() != null && !l_player.getD_continentsOwned().isEmpty()) {
                int l_continentControlBonusValue = 0;
                for (Continent l_continent : l_player.getD_continentsOwned()) {
                    l_continentControlBonusValue = l_continentControlBonusValue + l_continent.getD_continentValue();
                }
                l_armies = l_armies + l_continentControlBonusValue;
            }
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
            l_totalIgnoredOrders = l_totalIgnoredOrders + l_player.getD_ordersToExecute().size();
        }
        return l_totalIgnoredOrders != 0;
    }

    /**
     * Method to check if there are any unassigned armies left or not
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

}
