package Services;

import Models.Continent;
import Models.Country;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for ConquestMapFileReader
 */
class ConquestMapFileReaderTest {
    @Test
    /**
     * Test to check if the continents are parsed correctly
     */
    void parseCountriesMetaData() {
        ConquestMapFileReader l_reader = new ConquestMapFileReader();
        List<String> l_countryData = new ArrayList<>();
        l_countryData.add("Canada,1,2,North America");
        l_countryData.add("USA,2,3,North America");
        List<Continent> l_continentList = new ArrayList<>();
        l_continentList.add(new Continent(1, "North America", 5));

        List<Country> l_result = l_reader.parseCountriesMetaData(l_countryData, l_continentList);

        // Assert
        assertEquals(2, l_result.size());
        assertEquals("Canada", l_result.get(0).getD_countryName());
        assertEquals("USA", l_result.get(1).getD_countryName());
    }

    @Test
    /**
     * Test to check if the result is empty list when the data is not given
     */
    void parseCountriesMetaData_EmptyData() {
        ConquestMapFileReader l_reader = new ConquestMapFileReader();
        List<String> countryData = new ArrayList<>();
        List<Continent> continentList = new ArrayList<>();

        List<Country> result = l_reader.parseCountriesMetaData(countryData, continentList);

        // Assert
        assertTrue(result.isEmpty());
    }
}