package Models;

import Exceptions.InvalidMap;
import Utils.CommonUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/**
 * The methods to manipulate and validate map
 *
 * @author Vaibhav Chauhan
 */
public class Map {
    /**
     * Map file name
     */
    String d_mapFile;
    /**
     * List of Continents
     */
    List<Continent> d_continents;
    /**
     * List of Countries
     */
    List<Country> d_countries;
    /**
     * Hashmap of all countries reachable from existing position
     */
    HashMap<Integer, Boolean> d_countryConnectedStatus = new HashMap<Integer, Boolean>();

    /**
     * To get map file name
     *
     * @return map file name
     */
    public String getD_mapFile() {
        return d_mapFile;
    }

    /**
     * To set map file name
     *
     * @param p_mapFile map file name
     */
    public void setD_mapFile(String p_mapFile) {
        this.d_mapFile = p_mapFile;
    }

    /**
     * To get list of continents
     *
     * @return list of continents
     */
    public List<Continent> getD_continents() {
        return d_continents;
    }

    /**
     * To set list of continents
     *
     * @param d_continents list of continents
     */
    public void setD_continents(List<Continent> d_continents) {
        this.d_continents = d_continents;
    }

    /**
     * To get the list of countries
     *
     * @return List of countries
     */
    public List<Country> getD_countries() {
        return d_countries;
    }

    /**
     * To set the list of countries
     *
     * @param d_countries list of countries
     */
    public void setD_countries(List<Country> d_countries) {
        this.d_countries = d_countries;
    }

    /**
     * Adds country to the list of countries
     *
     * @param p_country country to append
     */
    public void appendCountry(Country p_country) {
        d_countries.add(p_country);
    }

    /**
     * Gives list of all country IDs in a List
     *
     * @return list of country IDs
     */
    public List<Integer> retrieveCountryIDs() {
        List<Integer> l_countryIDs = new ArrayList<>();
        if (!d_countries.isEmpty()) {
            for (Country country : d_countries) {
                l_countryIDs.add(country.getD_countryId());
            }
        }
        return l_countryIDs;
    }

    /**
     * Prints details of all countries
     */
    public void showCountriesInfo() {
        for (Country country : d_countries) {
            System.out.println("Country Id " + country.getD_countryId());
            System.out.println("Continent Id " + country.getD_continentId());
            System.out.println("Neighbours:");
            for (int i : country.getD_neighbourCountryIds()) {
                System.out.println(i);
            }
        }
    }

    /**
     * Adds continent to the list of continents
     *
     * @param p_continent continent to append
     */
    public void appendContinent(Continent p_continent) {
        d_continents.add(p_continent);
    }

    /**
     * Gives list of all continent IDs in play
     *
     * @return list of country IDs
     */
    public List<Integer> retrieveContinentIDs() {
        List<Integer> l_continentID = new ArrayList<>();
        if (!d_continents.isEmpty()) {
            for (Continent continent : d_continents) {
                l_continentID.add(continent.getD_continentID());
            }
        }
        return l_continentID;
    }

    /**
     * Prints every continent in plays
     */
    public void showContinentInfo() {
        for (Continent continent : d_continents) {
            System.out.println(continent.getD_continentID());
        }
    }

    /**
     * Checks if map is correct
     *
     * @return true if map is validated
     */
    public Boolean Validate() throws InvalidMap {
        return (!checkNullObjects() && isContinentsConnected() && isCountriesConnected());
    }

    /**
     * Checks if there are null objects in the map
     *
     * @return true if there are no null objects otherwise false
     */
    public Boolean checkNullObjects() throws InvalidMap {
        if (d_continents == null || d_continents.isEmpty()) {
            throw new InvalidMap("Map must possess atleast one continent!");
        }
        if (d_countries == null || d_countries.isEmpty()) {
            throw new InvalidMap("Map must possess atleast one country!");
        }
        for (Country c : d_countries) {
            if (c.getD_neighbourCountryIds().isEmpty()) {
                throw new InvalidMap(c.getD_countryName() + " does not possess any neighbour, hence isn't reachable!");
            }
        }
        return false;
    }

    /**
     * Checks if all continents are connected
     *
     * @return true if all continents are connected otherwise false
     */
    public boolean isContinentsConnected() {
        boolean l_flagConnected = true;
        for (Continent continent : d_continents) {
            if (continent.getD_countries() == null || continent.getD_countries().isEmpty()) {
                System.out.println("Continent " + continent.getD_continentName() + " has no countries");
            }
            if (!continentsGraphConnected(continent)) {
                l_flagConnected = false;
            }
        }
        return l_flagConnected;
    }

    /**
     * Checks if all countries are connected
     *
     * @return true if all countries are connected otherwise false
     */
    public boolean isCountriesConnected() throws InvalidMap {
        for (Country country : d_countries) {
            d_countryConnectedStatus.put(country.getD_countryId(), false);
        }
        dfsCountry(d_countries.get(0));

        for (java.util.Map.Entry<Integer, Boolean> entry : d_countryConnectedStatus.entrySet()) {
            if (!entry.getValue()) {
                System.out.println(retrieveCountry(entry.getKey()).getD_countryName() + " country is not accessible");
            }
        }
        return !d_countryConnectedStatus.containsValue(false);
    }

    /**
     * Checks if the continents are connected by their countries
     *
     * @param p_continent continent to check
     * @return true if all countries within the continent are connected otherwise false
     */
    private boolean continentsGraphConnected(Continent p_continent) {
        HashMap<Integer, Boolean> l_countriesInContinent = new HashMap<>();

        for (Country country : p_continent.getD_countries()) {
            l_countriesInContinent.put(country.getD_countryId(), false);
        }
        dfsContinents(p_continent.getD_countries().get(0), l_countriesInContinent, p_continent);

        for (java.util.Map.Entry<Integer, Boolean> entry : l_countriesInContinent.entrySet()) {
            if (!entry.getValue()) {
                Country l_country = retrieveCountry(entry.getKey());
                System.out.println(l_country.getD_countryName() + " is not connected");
            }
        }
        return !l_countriesInContinent.containsValue(false);
    }

    /**
     * DFS to check if countries in continents are connected
     *
     * @param p_country              country to check
     * @param p_countriesInContinent country ID with booleans if visited or not
     * @param p_continent            continent to check
     */
    private void dfsContinents(Country p_country, HashMap<Integer, Boolean> p_countriesInContinent, Continent p_continent) {
        p_countriesInContinent.put(p_country.getD_countryId(), true);
        for (Country country : p_continent.getD_countries()) {
            if (p_country.getD_neighbourCountryIds().contains(country.getD_countryId())) {
                if (!p_countriesInContinent.get(country.getD_countryId())) {
                    dfsContinents(country, p_countriesInContinent, p_continent);
                }
            }
        }
    }

    /**
     * Retrieve neighbour country list
     *
     * @param p_country country name to which neighbours to be found
     * @return list of neighbour country objects
     */
    public List<Country> getNeighbourCountry(Country p_country) throws InvalidMap {
        List<Country> l_neighbourCountries = new ArrayList<Country>();

        if (p_country.getD_neighbourCountryIds().size() > 0) {
            for (int i : p_country.getD_neighbourCountryIds()) {
                l_neighbourCountries.add(retrieveCountry(i));
            }
        } else {
            throw new InvalidMap(p_country.getD_countryName() + " doesn't have any adjacent countries");
        }
        return l_neighbourCountries;
    }

    /**
     * DFS applied to input
     *
     * @param p_country country to visit first
     */
    public void dfsCountry(Country p_country) throws InvalidMap {
        d_countryConnectedStatus.put(p_country.getD_countryId(), true);
        for (Country l_country : getNeighbourCountry(p_country)) {
            if (!d_countryConnectedStatus.get(l_country.getD_countryId())) {
                dfsCountry(l_country);
            }
        }
    }

    /**
     * Finds a country object from a given country ID
     *
     * @param p_countryId country ID
     * @return country Object
     */
    public Country retrieveCountry(Integer p_countryId) {
        return d_countries.stream().filter(l_country -> l_country.getD_countryId().equals(p_countryId)).findFirst().orElse(null);

    }

    /**
     * Finds country object by a country name
     *
     * @param p_countryName country name
     * @return country Object
     */
    public Country getCountryByName(String p_countryName) {
        return d_countries.stream().filter(l_country -> l_country.getD_countryName().equals(p_countryName)).findFirst().orElse(null);

    }

    /**
     * Adds countries to the map.
     *
     * @param p_countryName   country Name which is to be added
     * @param p_continentName Name of Continent in which given country to be added
     */
    public void addCountry(String p_countryName, String p_continentName) throws InvalidMap {
        int l_countryId;
        if (d_countries == null) {
            d_countries = new ArrayList<Country>();
        }
        if (CommonUtil.isNull(getCountryByName(p_countryName))) {
            l_countryId = d_countries.size() > 0 ? Collections.max(retrieveCountryIDs()) + 1 : 1;
            if (d_continents != null && retrieveContinent(p_continentName) != null && retrieveContinentIDs().contains(retrieveContinent(p_continentName).getD_continentID())) {
                Country l_country = new Country(l_countryId, p_countryName, retrieveContinent(p_continentName).getD_continentID());
                d_countries.add(l_country);
                for (Continent c : d_continents) {
                    if (c.getD_continentName().equals(p_continentName)) {
                        c.addCountry(l_country);
                    }
                }
            } else {
                throw new InvalidMap("Cannot add Country " + p_countryName + " to a Continent that doesn't exist!");
            }
        } else {
            throw new InvalidMap("Country with name " + p_countryName + " already Exists!");
        }
    }

    /**
     * Removes countries from the map.
     *
     * @param p_countryName country Name to be removed
     */
    public void removeCountry(String p_countryName) throws InvalidMap {
        if (d_countries != null && !CommonUtil.isNull(getCountryByName(p_countryName))) {
            for (Continent c : d_continents) {
                if (c.getD_continentID().equals(getCountryByName(p_countryName).getD_continentId())) {
                    c.removeCountry(getCountryByName(p_countryName));
                }
                c.removeCountryForAllNeighbours(getCountryByName(p_countryName).getD_countryId());
            }
            removeCountryNeighboursFromAll(getCountryByName(p_countryName).getD_countryId());
            d_countries.remove(getCountryByName(p_countryName));

        } else {
            throw new InvalidMap("Country: " + p_countryName + " does not exist!");
        }
    }

    /**
     * Adds continent to the map
     *
     * @param p_continentName         continent Name to be added
     * @param p_continentControlBonus Bonus armies given to players when control whole continent
     */
    public void addContinent(String p_continentName, int p_continentControlBonus) throws InvalidMap {
        int l_continentId;

        if (d_continents != null) {
            l_continentId = d_continents.size() > 0 ? Collections.max(retrieveContinentIDs()) + 1 : 1;
            if (CommonUtil.isNull(retrieveContinent(p_continentName))) {
                d_continents.add(new Continent(l_continentId, p_continentName, p_continentControlBonus));
            } else {
                throw new InvalidMap("Continent " + p_continentName + " cannot be added! It already exists!");
            }
        } else {
            d_continents = new ArrayList<Continent>();
            d_continents.add(new Continent(1, p_continentName, p_continentControlBonus));
        }
    }

    /**
     * Returns continent object that matches with continent name
     *
     * @param p_continentName continent Name to retrieve the object
     * @return Continent object
     */
    public Continent retrieveContinent(String p_continentName) {
        for (Continent continent : d_continents) {
            if (p_continentName.equals(continent.getD_continentName())) {
                return continent;
            }
        }
        return null;
    }

    /**
     * Removes Continent from map
     *
     * @param p_continentName continent Name to be removed
     */
    public void removeContinent(String p_continentName) throws InvalidMap {
        if (d_continents != null) {
            if (!CommonUtil.isNull(retrieveContinent(p_continentName))) {

                // Deletes the continent and updates neighbour as well as country objects
                if (retrieveContinent(p_continentName).getD_countries() != null) {
                    for (Country c : retrieveContinent(p_continentName).getD_countries()) {
                        removeCountryNeighboursFromAll(c.getD_countryId());
                        removeNeighboursFromContinents(c.getD_countryId());
                        d_countries.remove(c);
                    }
                }
                d_continents.remove(retrieveContinent(p_continentName));
            } else {
                throw new InvalidMap("No such Continent exists!");
            }
        } else {
            throw new InvalidMap("No continents in the Map to remove!");
        }
    }

    /**
     * Removes the country as neighbour from the continent object
     *
     * @param p_countryID country ID to be removed
     */
    private void removeNeighboursFromContinents(int p_countryID) {
        for (Continent l_continent : d_continents) {
            l_continent.removeCountryForAllNeighbours(p_countryID);
        }
    }

    /**
     * Removes the country as neighbour for all other countries
     *
     * @param p_countryID country ID to be removed
     */
    private void removeAllNeighbours(int p_countryID) {
        for (Country l_country : d_countries) {
            if (l_country.getD_neighbourCountryIds() != null) {
                if (l_country.getD_neighbourCountryIds().contains(p_countryID)) {
                    l_country.removeNeighbourFromCountry(p_countryID);
                }
            }
        }
    }

    /**
     * Adds neighbour country to given country name
     *
     * @param p_countryName   country Name for which neighbours will be updated
     * @param p_neighbourName neighbour country name
     */
    public void addCountryNeighbour(String p_countryName, String p_neighbourName) throws InvalidMap {
        if (d_countries != null) {
            if (!CommonUtil.isNull(getCountryByName(p_countryName)) && !CommonUtil.isNull(getCountryByName(p_neighbourName))) {
                d_countries.get(d_countries.indexOf(getCountryByName(p_countryName))).addNeighbourToCountry(getCountryByName(p_neighbourName).getD_countryId());
            } else {
                throw new InvalidMap("Invalid Neighbour Pair " + p_countryName + " " + p_neighbourName + "! Either of the Countries Doesn't exist!");
            }
        }
    }

    /**
     * Removes neighbor country from given country name
     *
     * @param p_countryName   country Name for which neighbours will be updated
     * @param p_neighbourName neighbour country name to be removed
     */
    public void removeCountryNeighbour(String p_countryName, String p_neighbourName) throws InvalidMap {
        if (d_countries != null) {
            if (!CommonUtil.isNull(getCountryByName(p_countryName)) && !CommonUtil.isNull(getCountryByName(p_neighbourName))) {
                d_countries.get(d_countries.indexOf(getCountryByName(p_countryName))).removeNeighbourFromCountry(getCountryByName(p_neighbourName).getD_countryId());
            } else {
                throw new InvalidMap("Invalid Neighbour Pair " + p_countryName + " " + p_neighbourName + "! Either of the Countries Doesn't exist!");
            }
        }
    }

    /**
     * Remove Particular Country as Neighbor from all associated countries (in Map CountryList)
     * Used while deletion of country object.
     *
     * @param p_countryID Country to be removed
     * @throws InvalidMap indicates Map Object Validation failure
     */
    public void removeCountryNeighboursFromAll(Integer p_countryID) throws InvalidMap {
        for (Country c : d_countries) {
            if (!CommonUtil.isNull(c.getD_neighbourCountryIds())) {
                if (c.getD_neighbourCountryIds().contains(p_countryID)) {
                    c.removeNeighbourFromCountry(p_countryID);
                }
            }
        }
    }
}