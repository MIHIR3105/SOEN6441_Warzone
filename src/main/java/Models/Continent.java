package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * The methods to manage the continents are in this class
 */
public class Continent {
    /**
     * continent ID
     */
    Integer d_continentID;
    /**
     * continent Name
     */
    String d_continentName;
    /**
     * continent Value
     */
    Integer d_continentValue;
    /**
     * List of Countries in a Continent
     */
    List<Country> d_countries;

    /**
     * Default constructor
     */
    public Continent() {

    }

    /**
     * Constructor with 3 parameters
     * @param d_continentID continent ID
     * @param d_continentName continent Name
     * @param d_continentValue continent Value
     */
    public Continent(Integer d_continentID, String d_continentName, Integer d_continentValue) {
        this.d_continentID = d_continentID;
        this.d_continentName = d_continentName;
        this.d_continentValue = d_continentValue;
    }

    /**
     * Constructor with 1 parameter
     * @param d_continentName continent Name
     */
    public Continent(String d_continentName) {
        this.d_continentName = d_continentName;
    }

    /**
     * To get continent ID
     * @return continent ID
     */
    public Integer getD_continentID() {
        return d_continentID;
    }

    /**
     * To get continent Name
     * @return continent Name
     */
    public String getD_continentName() {
        return d_continentName;
    }

    /**
     * To get continent Value
     * @return continent Value
     */
    public Integer getD_continentValue() {
        return d_continentValue;
    }

    /**
     * To get the list of countries in the continent
     * @return list of countries
     */
    public List<Country> getD_countries() {
        return d_countries;
    }

    /**
     * To set continent ID
     * @param p_continentID continent ID
     */
    public void setD_continentID(int p_continentID) {
        this.d_continentID = p_continentID;
    }

    /**
     * To set continent Name
     * @param p_continentName continent Name
     */
    public void setD_continentName(String p_continentName) {
        this.d_continentName = p_continentName;
    }

    /**
     * To set continent Value
     * @param p_continentValue continent Value
     */
    public void setD_continentValue(int p_continentValue) {
        this.d_continentValue = p_continentValue;
    }

    /**
     * To set the list of countries in the continent
     * @param p_countries list of the countries in the continent
     */
    public void setD_countries(List<Country> p_countries) {
        this.d_countries = p_countries;
    }

    /**
     * Adds a country to the continent
     * @param p_country country to be added
     */
    public void addCountry(Country p_country) {
        if (d_countries != null) {
            d_countries.add(p_country);
        } else {
            d_countries = new ArrayList<Country>();
            d_countries.add(p_country);
        }
    }

    /**
     * Removes a country from the continent
     * @param p_country country to be removed
     */
    public void removeCountries(Country p_country) {
        if (d_countries == null) {
            System.out.println("There are no countries to remove");
        } else {
            d_countries.remove(p_country);
        }
    }

    /**
     * Removes the country ID from the list of neighbours for all the countries in the continent
     * @param p_countryID country ID which is to be removed
     */
    public void removeCountryForAllNeighbours(Integer p_countryID) {
        if (d_countries != null && !d_countries.isEmpty()) {
            for (Country country : d_countries) {
                if (country.d_neighbourCountryId != null) {
                    if (country.getD_neighbourCountryId().contains(p_countryID)) {
                        country.removeNeighbourFromCountry(p_countryID);
                    }
                }
            }
        }
    }
}