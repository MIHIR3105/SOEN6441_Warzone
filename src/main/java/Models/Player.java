package Models;

import Constants.GameConstants;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Utils.CommonUtil;

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
     * color to show details with on map.
     */
    private String d_color;

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
     * More orders to be accepted for player.
     */
    boolean d_moreOrders;


    /**
     * The constructor has param used to create player
     * with name and be deafault armies.
     *
     * @param p_playerName name of the player.
     */
    public Player(String p_playerName) {
        this.d_name = p_playerName;
        this.d_noOfArmies = 0;
        this.d_countryList = new ArrayList<Country>();
        this.d_orderList = new ArrayList<Order>();
        this.d_moreOrders = true;
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
     * This getter is used to get color code for player.
     *
     * @return Color
     */
    public String getD_color() {
        return d_color;
    }

    /**
     * This setter is used to set color code for player.
     *
     * @param p_color ANSI color code.
     */
    public void setD_color(String p_color) {
        d_color = p_color;
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
     * Countries player cannot issue an order on.
     *
     * @param p_playerNegotiation player to negotiate with.
     */
    public void addPlayerNegotiation(Player p_playerNegotiation) {
        this.d_negotiatedWith.add(p_playerNegotiation);
    }

    /**
     * Gets info about more orders from player are to be accepted or not.
     *
     * @return boolean true if player wants to give more order or else false
     */
    public boolean getD_moreOrders() {
        return d_moreOrders;
    }

    /**
     * Sets info about more orders from player are to be accepted or not.
     *
     * @param p_moreOrders Boolean true if player wants to give more order or else
     *                     false
     */
    public void setD_moreOrders(boolean p_moreOrders) {
        this.d_moreOrders = p_moreOrders;
    }

    /**
     * Returns the List of cards owned by the player.
     *
     * @return List of Strings with cards
     */
    public List<String> getD_cardsOwnedByPlayer() {
        return this.d_cardsOwnedByPlayer;
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
     * @param p_typeLog   Type of log : error, or log
     */
    public void setD_playerLog(String p_playerLog, String p_typeLog) {
        this.d_playerLog = p_playerLog;
        if (p_typeLog.equals("error"))
            System.err.println(p_playerLog);
        else if (p_typeLog.equals("log"))
            System.out.println(p_playerLog);
    }

    /**
     * Returns the player log string.
     *
     * @return String of log message.
     */
    public String getD_playerLog() {
        return this.d_playerLog;
    }


    /**
     * Sets the Per Turn Card allocated bool.
     *
     * @param p_value Bool to Set.
     */
    public void setD_oneCardPerTurn(Boolean p_value) {
        this.d_oneCardPerTurn = p_value;
    }

    /**
     * Checks if there are more order to be accepted for player in next turn or not.
     *
     * @throws IOException exception in reading inputs from user
     */
    void checkForMoreOrders() throws IOException {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nDo you still want to give order for player : " + this.getPlayerName()
                + " in next turn ? \nPress Y for Yes or N for No");
        String l_nextOrderCheck = l_reader.readLine();
        if (l_nextOrderCheck.equalsIgnoreCase("Y") || l_nextOrderCheck.equalsIgnoreCase("N")) {
            this.setD_moreOrders(l_nextOrderCheck.equalsIgnoreCase("Y") ? true : false);
        } else {
            System.err.println("Invalid Input Passed.");
            this.checkForMoreOrders();
        }
    }


    /**
     * Creates the deploy order on the commands entered by the player.
     *
     * @param p_commandEntered command entered by the user
     */
    public void createDeployOrder(String p_commandEntered) {
        String l_targetCountry;
        String l_noOfArmies;
        try {
            l_targetCountry = p_commandEntered.split(" ")[1];
            l_noOfArmies = p_commandEntered.split(" ")[2];
            if (validateDeployOrderArmies(this, l_noOfArmies)) {
                this.setD_playerLog(
                        "Given deploy order cant be executed as armies in deploy order exceeds player's unallocated armies.", "error");
            } else {
                this.d_orderList.add(new Deploy(this, l_targetCountry, Integer.parseInt(l_noOfArmies)));
                Integer l_unallocatedarmies = this.getD_noOfUnallocatedArmies() - Integer.parseInt(l_noOfArmies);
                this.setD_noOfUnallocatedArmies(l_unallocatedarmies);
                this.setD_playerLog("Deploy order has been added to queue for execution. For player: " + this.d_name, "log");

            }
        } catch (Exception l_e) {
            this.setD_playerLog("Invalid deploy order entered", "error");
        }

    }

    /**
     * Used to test number of armies entered in deploy command to check that player
     * cannot deploy more armies that there is in their reinforcement pool.
     *
     * @param p_player     player to create deploy order
     * @param p_noOfArmies number of armies to deploy
     * @return boolean to validate armies to deploy
     */
    public boolean validateDeployOrderArmies(Player p_player, String p_noOfArmies) {
        return p_player.getD_noOfUnallocatedArmies() < Integer.parseInt(p_noOfArmies) ? true : false;
    }

    /**
     * Issues order for player.
     *
     * @param p_issueOrderPhase current phase of the game
     * @throws InvalidCommand exception if command is invalid
     * @throws IOException    indicates failure in I/O operation
     * @throws InvalidMap     indicates failure in using the invalid map
     */
    public void issue_order(IssueOrderPhase p_issueOrderPhase) throws InvalidCommand, IOException, InvalidMap {
        p_issueOrderPhase.askForOrder(this);
    }

    /**
     * Checks if source and target countries given in advance order exists in the
     * map or not.
     *
     * @param p_countryName country name which needs to be checked in map
     * @param p_gameState   current state of the map
     * @return true if country exists in map or else false
     */
    private Boolean checkCountryExists(String p_countryName, GameState p_gameState) {
        if (p_gameState.getD_map().getCountryByName(p_countryName) == null) {
            this.setD_playerLog("Country : " + p_countryName
                    + " given in advance order doesnt exists in map. Order given is ignored.", "error");
            return false;
        }
        return true;
    }

    /**
     * Checks if given advance order has zero armies to move.
     *
     * @param p_noOfArmies number of armies given in order
     * @return true if given order has zero armies or else false
     */
    private Boolean checkZeroArmiesInOrder(String p_noOfArmies) {
        if (Integer.parseInt(p_noOfArmies) == 0) {
            this.setD_playerLog("Advance order with 0 armies to move cant be issued.", "error");
            return true;
        }
        return false;
    }

    /**
     * Checks if countries given advance order are adjacent or not.
     *
     * @param p_gameState         current state of the game
     * @param p_sourceCountryName source country name
     * @param p_targetCountryName target country name
     * @return boolean true if countries are adjacent or else false
     */
    @SuppressWarnings("unlikely-arg-type")
    public boolean checkAdjacency(GameState p_gameState, String p_sourceCountryName, String p_targetCountryName) {
        Country l_sourceCountry = p_gameState.getD_map().getCountryByName(p_sourceCountryName);
        Country l_targetCountry = p_gameState.getD_map().getCountryByName(p_targetCountryName);
        Integer l_targetCountryId = l_sourceCountry.getD_neighbourCountryIds().stream()
                .filter(l_adjCountry -> l_adjCountry == l_targetCountry.getD_countryId()).findFirst().orElse(null);
        if (l_targetCountryId == null) {
            this.setD_playerLog("Advance order cant be issued since target country : " + p_targetCountryName
                    + " is not adjacent to source country : " + p_sourceCountryName, "error");
            return false;
        }
        return true;
    }

    /**
     * Creates the advance order on the commands entered by the player.
     *
     * @param p_commandEntered command entered by the user
     * @param p_gameState      current state of the game
     */
    public void createAdvanceOrder(String p_commandEntered, GameState p_gameState) {
        try {
            if (p_commandEntered.split(" ").length == 4) {
                String l_sourceCountry = p_commandEntered.split(" ")[1];
                String l_targetCountry = p_commandEntered.split(" ")[2];
                String l_noOfArmies = p_commandEntered.split(" ")[3];
                if (this.checkCountryExists(l_sourceCountry, p_gameState)
                        && this.checkCountryExists(l_targetCountry, p_gameState)
                        && !checkZeroArmiesInOrder(l_noOfArmies)
                        && checkAdjacency(p_gameState, l_sourceCountry, l_targetCountry)) {
                    this.d_orderList
                            .add(new Advance(this, l_sourceCountry, l_targetCountry, Integer.parseInt(l_noOfArmies)));
                    this.setD_playerLog("Advance order has been added to queue for execution. For player: " + this.d_name, "log");
                }
            } else {
                this.setD_playerLog("Invalid Arguments Passed For Advance Order", "error");
            }

        } catch (Exception l_e) {
            this.setD_playerLog("Invalid Advance Order Given", "error");
        }
    }


    /**
     * This method will assign any random card from the set of available cards to
     * the player once he conquers a territory.
     */
    public void assignCard() {
        if (!d_oneCardPerTurn) {
            Random l_random = new Random();
            this.d_cardsOwnedByPlayer.add(GameConstants.CARDS.get(l_random.nextInt(GameConstants.SIZE)));
            this.setD_playerLog("Player: " + this.d_name + " has earned card as reward for the successful conquest- " + this.d_cardsOwnedByPlayer.get(this.d_cardsOwnedByPlayer.size() - 1), "log");
            this.setD_oneCardPerTurn(true);
        } else {
            this.setD_playerLog("Player: " + this.d_name + " has already earned maximum cards that can be allotted in a turn", "error");
        }
    }

    /**
     * Remove the card which is used.
     *
     * @param p_cardName name of the card to remove.
     */
    public void removeCard(String p_cardName) {
        this.d_cardsOwnedByPlayer.remove(p_cardName);
    }

    /**
     * Checks if the order issued on country is possible or not.
     *
     * @param p_targetCountryName country to attack
     * @return bool if it can attack
     */
    public boolean negotiationValidation(String p_targetCountryName) {
        boolean l_canAttack = true;
        for (Player p : d_negotiatedWith) {
            if (p.getCountryNames().contains(p_targetCountryName))
                l_canAttack = false;
        }
        return l_canAttack;
    }

    /**
     * Clears all negotiation from the previous turn.
     */
    public void resetNegotiation() {
        d_negotiatedWith.clear();
    }


    /**
     * Validates the card arguments.
     *
     * @param p_commandEntered command of card
     * @return bool if valid
     */
    public boolean checkCardArguments(String p_commandEntered) {
        if (p_commandEntered.split(" ")[0].equalsIgnoreCase("airlift")) {
            return p_commandEntered.split(" ").length == 4;
        } else if (p_commandEntered.split(" ")[0].equalsIgnoreCase("blockade")
                || p_commandEntered.split(" ")[0].equalsIgnoreCase("bomb")
                || p_commandEntered.split(" ")[0].equalsIgnoreCase("negotiate")) {
            return p_commandEntered.split(" ").length == 2;
        } else {
            return false;
        }
    }


    /**
     * Gives the first order in the players list of orders, then removes it from the
     * list.
     *
     * @return Order first order from the list of player's order
     */
    public Order next_order() {
        if (CommonUtil.isCollectionEmpty(this.d_orderList)) {
            return null;
        }
        Order l_order = this.d_orderList.get(0);
        this.d_orderList.remove(l_order);
        return l_order;
    }


    /**
     * Handles the Card Commands: creates order and adds them to the list.
     *
     * @param p_commandEntered command entered
     * @param p_gameState      gamestate instance
     */
    public void handleCardCommands(String p_commandEntered, GameState p_gameState) {
        if (checkCardArguments(p_commandEntered)) {
            switch (p_commandEntered.split(" ")[0]) {
                case "airlift":
                    Card l_newOrder = new Airlift(p_commandEntered.split(" ")[1], p_commandEntered.split(" ")[2],
                            Integer.parseInt(p_commandEntered.split(" ")[3]), this);
                    if (l_newOrder.checkValidOrder(p_gameState)) {
                        this.d_orderList.add(l_newOrder);
                        this.setD_playerLog("Card Command Added to Queue for Execution Successfully!", "log");
                        p_gameState.updateLog(getD_playerLog(), "effect");
                    }
                    break;
                case "blockade":
                    Card l_blockadeOrder = new Blockade(this, p_commandEntered.split(" ")[1]);
                    if (l_blockadeOrder.checkValidOrder(p_gameState)) {
                        this.d_orderList.add(l_blockadeOrder);
                        this.setD_playerLog("Card Command Added to Queue for Execution Successfully!", "log");
                        p_gameState.updateLog(getD_playerLog(), "effect");
                    }
                    break;
                case "bomb":
                    Card l_bombOrder = new Bomb(this, p_commandEntered.split(" ")[1]);
                    if (l_bombOrder.checkValidOrder(p_gameState)) {
                        this.d_orderList.add(l_bombOrder);
                        this.setD_playerLog("Card Command Added to Queue for Execution Successfully!", "log");
                        p_gameState.updateLog(getD_playerLog(), "effect");
                    }
                    break;
                case "negotiate":
                    Card l_negotiateOrder = new Diplomacy(p_commandEntered.split(" ")[1], this);
                    if (l_negotiateOrder.checkValidOrder(p_gameState)) {
                        this.d_orderList.add(l_negotiateOrder);
                        this.setD_playerLog("Card Command Added to Queue for Execution Successfully!", "log");
                        p_gameState.updateLog(getD_playerLog(), "effect");
                    }
                    break;
                default:
                    this.setD_playerLog("Invalid Command!", "error");
                    p_gameState.updateLog(getD_playerLog(), "effect");
                    break;
            }
        } else {
            this.setD_playerLog("Invalid Card Command Passed! Check Arguments!", "error");
        }
    }
}
