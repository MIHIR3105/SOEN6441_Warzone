package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * The methods to manage the countries are in this class
 * @author Vaibhav Chauhan
 */
public class Country {
    /**
     * country ID
     */
    int d_countryId;

    /**
     * country Name
     */
    String d_countryName;

    /**
     * continent ID
     */
    int d_continentId;

    /**
     * number of armies for a country
     */
    int d_armies;

    /**
     * Neighbour list for the given country
     */
    List<Integer> d_neighbourCountryId = new ArrayList<Integer>();

    /**
     * Constructor with 3 parameters
     * @param p_countryId country ID
     * @param p_countryName country Name
     * @param p_continentId continent ID
     */
    public Country(int p_countryId, String p_countryName, int p_continentId) {
        this.d_countryId = p_countryId;
        this.d_countryName = p_countryName;
        this.d_continentId = p_continentId;
    }

    /**
     * Constructor with 2 parameters
     * @param p_countryId country ID
     * @param p_continentId continent ID
     */
    public Country(int p_countryId, int p_continentId) {
        this.d_countryId = p_countryId;
        this.d_continentId = p_continentId;
    }

    /**
     * Constructor with 1 parameter
     * @param p_countryName country Name
     */
    public Country(String p_countryName) {
        this.d_countryName = p_countryName;
    }

    /**
     * To get country ID
     * @return country ID
     */
    public int getD_countryId() {
        return d_countryId;
    }

    /**
     * To get country Name
     * @return country Name
     */
    public String getD_countryName() {
        return d_countryName;
    }

    /**
     * To get continent ID
     * @return continent ID
     */
    public int getD_continentId() {
        return d_continentId;
    }

    /**
     * To get armies
     * @return armies
     */
    public int getD_armies() {
        return d_armies;
    }

    /**
     * To get neighbour list for the given country
     * @return neighbour Country IDs
     */
    public List<Integer> getD_neighbourCountryId() {
        return d_neighbourCountryId;
    }

    /**
     * To set country ID
     * @param p_countryId country ID
     */
    public void setD_countryId(int p_countryId) {
        this.d_countryId = p_countryId;
    }

    /**
     * To set country Name
     * @param p_countryName country Name
     */
    public void setD_countryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }

    /**
     * To set continent ID
     * @param p_continentId continent ID
     */
    public void setD_continentId(int p_continentId) {
        this.d_continentId = p_continentId;
    }

    /**
     * To set armies
     * @param p_armies armies
     */
    public void setD_armies(int p_armies) {
        this.d_armies = p_armies;
    }

    /**
     * To set neighbour list for the given country
     * @param p_neighbourCountryId ID of neighbour country
     */
    public void setD_neighbourCountryId(List<Integer> p_neighbourCountryId) {
        this.d_neighbourCountryId = p_neighbourCountryId;
    }

    /**
     * Adds country ID to neighbour country list
     * @param p_countryId country ID which is to be added
     */
    public void addNeighbourToCountry(int p_countryId){
        if(!d_neighbourCountryId.contains(p_countryId))
            d_neighbourCountryId.add(p_countryId);
    }

    /**
     * Removes country ID from neighbour country list
     * @param p_countryId country ID which is to be removed
     */
    public void removeNeighbourFromCountry(Integer p_countryId){
        if(!d_neighbourCountryId.contains(p_countryId)){
            System.out.println("Neighbour does not Exists");
        } else {
            d_neighbourCountryId.remove(d_neighbourCountryId.indexOf(p_countryId));
        }
    }
}