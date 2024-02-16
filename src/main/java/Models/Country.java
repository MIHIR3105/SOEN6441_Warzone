package Models;

import java.util.ArrayList;
import java.util.List;

public class Country {
    int d_countryId;
    String d_countryName;
    int d_continentId;
    int d_armies;
    List<Integer> d_neighbourCountryId = new ArrayList<Integer>();
    public Country(int p_countryId, String p_countryName, int p_continentId) {
        this.d_countryId = p_countryId;
        this.d_countryName = p_countryName;
        this.d_continentId = p_continentId;
    }
    public Country(int p_countryId, int p_continentId) {
        this.d_countryId = p_countryId;
        this.d_continentId = p_continentId;
    }
    public Country(String p_countryName) {
        this.d_countryName = p_countryName;
    }

    public int getD_countryId() {
        return d_countryId;
    }
    public String getD_countryName() {
        return d_countryName;
    }
    public int getD_continentId() {
        return d_continentId;
    }
    public int getD_armies() {
        return d_armies;
    }
    public List<Integer> getD_neighbourCountryId() {
        return d_neighbourCountryId;
    }

    public void setD_countryId(int p_countryId) {
        this.d_countryId = p_countryId;
    }
    public void setD_countryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }
    public void setD_continentId(int p_continentId) {
        this.d_continentId = p_continentId;
    }
    public void setD_armies(int p_armies) {
        this.d_armies = p_armies;
    }
    public void setD_neighbourCountryId(List<Integer> p_neighbourCountryId) {
        this.d_neighbourCountryId = p_neighbourCountryId;
    }

    public void addNeighbourToCountry(int p_countryId){
        if(!d_neighbourCountryId.contains(p_countryId))
            d_neighbourCountryId.add(p_countryId);
    }
    public void removeNeighbourFromCountry(Integer p_countryId){
        if(!d_neighbourCountryId.contains(p_countryId)){
            System.out.println("Neighbour does not Exists");

        }else{
            d_neighbourCountryId.remove(d_neighbourCountryId.indexOf(p_countryId));
        }
    }
}