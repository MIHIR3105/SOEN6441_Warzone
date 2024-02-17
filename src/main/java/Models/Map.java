package Models;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

/**
 * The methods to manipulate and validate map
 */
public class Map{
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
     * @return map file name
     */
    public String getD_mapFile() {
        return d_mapFile;
    }

    /**
     * To set map file name
     * @param p_mapFile map file name
     */
    public void setD_mapFile(String p_mapFile) {
        this.d_mapFile = p_mapFile;
    }

    /**
     * To get list of continents
     * @return list of continents
     */
    public List<Continent> getD_continents() {
        return d_continents;
    }

    /**
     * To set list of continents
     * @param d_continents
     */
    public void setD_continents(List<Continent> d_continents) {
        this.d_continents = d_continents;
    }

    /**
     * To get the list of countries
     * @return List of countries
     */
    public List<Country> getD_countries() {
        return d_countries;
    }

    /**
     * To set the list of countries
     * @param d_countries list of countries
     */
    public void setD_countries(List<Country> d_countries) {
        this.d_countries = d_countries;
    }

    /**
     * Adds country to the list of countries
     * @param p_country country to append
     */
    public void appendCountry(Country p_country) {
        d_countries.add(p_country);
    }

    /**
     * Gives list of all country IDs in a List
     * @return list of country IDs
     */
    public List<Integer> retrieveCountryID() {
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
            for (int i : country.getD_neighbourCountryId()) {
                System.out.println(i);
            }
        }
    }

    /**
     * Adds continent to the list of continents
     * @param p_continent continent to append
     */
    public void appendContinent(Continent p_continent) {
        d_continents.add(p_continent);
    }

    /**
     * Gives list of all continent IDs in play
     * @return list of country IDs
     */
    public List<Integer> retrieveContinentID() {
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
     * @return true if map is validated
     */
    public Boolean Validate() {
        return (checkNullObjects() && isContinentsConnected() && isCountriesConnected());
    }

    /**
     * Checks if there are null objects in the map
     * @return true if there are no null objects otherwise false
     */
    public Boolean checkNullObjects() {
        if (d_continents == null || d_continents.isEmpty()) {
            System.out.println("Map must contain at least one continent!");
        }
        if (d_countries == null || d_countries.isEmpty()) {
            System.out.println("Map must contain at least one country!");
        }
        for (Country c : d_countries) {
            if (c.getD_neighbourCountryId().isEmpty()) {
                System.out.println(c.getD_countryName() + " does not possess any neighbour, hence isn't reachable!");
            }
        }
        return true;
    }

    /**
     * Checks if all continents are connected
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
     * @return true if all countries are connected otherwise false
     */
    public boolean isCountriesConnected() {
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
     * @param p_country country to check
     * @param p_countriesInContinent country ID with booleans if visited or not
     * @param p_continent continent to check
     */
    private void dfsContinents(Country p_country, HashMap<Integer, Boolean> p_countriesInContinent, Continent p_continent) {
        p_countriesInContinent.put(p_country.getD_countryId(), true);
        for (Country country : p_continent.getD_countries()) {
            if (p_country.getD_neighbourCountryId().contains(country.getD_countryId())) {
                if (!p_countriesInContinent.get(country.getD_countryId())) {
                    dfsContinents(country, p_countriesInContinent, p_continent);
                }
            }
        }
    }

    /**
     * Retrieve neighbour country list
     * @param p_country country name to which neighbours to be found
     * @return list of neighbour country objects
     */
    public List<Country> getNeighbourCountry(Country p_country) {
        List<Country> l_neighbourCountries = new ArrayList<Country>();

        if (p_country.getD_neighbourCountryId().size() > 0) {
            for (int i : p_country.getD_neighbourCountryId()) {
                l_neighbourCountries.add(retrieveCountry(i));
            }
        } else {
            System.out.println(p_country.getD_countryName() + " doesn't contain any neighbour countries");
        }
        return l_neighbourCountries;
    }

    /**
     * DFS applied to input
     * @param p_country country to visit first
     */
    public void dfsCountry(Country p_country) {
        d_countryConnectedStatus.put(p_country.getD_countryId(), true);
        for (Country l_country : getNeighbourCountry(p_country)) {
            if (!d_countryConnectedStatus.get(l_country.getD_countryId())) {
                dfsCountry(l_country);
            }
        }
    }

    /**
     * Finds a country object from a given country ID
     * @param p_countryId country ID
     * @return country Object
     */
    public Country retrieveCountry(Integer p_countryId) {
        for (Country country : d_countries) {
            if (p_countryId == country.getD_countryId()) {
                return country;
            }
        }
        return null;
    }


}