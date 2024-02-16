package Models;

public class Country {
    int d_countryId;
    String d_countryName;

    public Country(int p_countryId, String p_countryName) {
        this.d_countryId = p_countryId;
        this.d_countryName = p_countryName;
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

    public void setD_countryId(int p_countryId) {
        this.d_countryId = p_countryId;
    }
    public void setD_countryName(String p_countryName) {
        this.d_countryName = p_countryName;
    }
}