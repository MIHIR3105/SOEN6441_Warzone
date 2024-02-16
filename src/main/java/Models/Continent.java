package Models;

public class Continent {
    Integer d_continentID;
    String d_continentName;
    Integer d_continentValue;
    List<Country> d_countries;

    public Continent() {

    }
    public Continent(Integer d_continentID, String d_continentName, Integer d_continentValue) {
        this.d_continentID = d_continentID;
        this.d_continentName = d_continentName;
        this.d_continentValue = d_continentValue;
    }
    public Continent(String d_continentName) {
        this.d_continentName = d_continentName;
    }

    public Integer getD_continentID() {
        return d_continentID;
    }
    public String getD_continentName() {
        return d_continentName;
    }
    public Integer getD_continentValue() {
        return d_continentValue;
    }
    public List<Country> getD_countries() {
        return d_countries;
    }

    public void setD_continentID(int p_continentID) {
        this.d_continentID = p_continentID;
    }
    public void setD_continentName(String p_continentName) {
        this.d_continentName = p_continentName;
    }
    public void setD_continentValue(int p_continentValue) {
        this.d_continentValue = p_continentValue;
    }
    public void setD_countries(List<Country> p_countries) {
        this.d_countries = p_countries;
    }

    public void addCountry(Country p_country) {
        if (d_countries != null) {
            d_countries.add(p_country);
        } else {
            d_countries = new ArrayList<Country>();
            d_countries.add(p_country);
        }
    }

    public void removeCountries(Country p_country) {
        if (d_countries == null) {
            System.out.println("There are no countries to remove");
        } else {
            d_countries.remove(p_country);
        }
    }
}