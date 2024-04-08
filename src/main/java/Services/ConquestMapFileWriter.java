package Services;


import Constants.GameConstants;
import Models.Country;
import Models.Continent;
import Models.GameState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Writer class to read and create conquest map file.
 *
 * @author Prachi Patel
 */
public class ConquestMapFileWriter implements Serializable {

    /**
     * Reads conquest map, parses it and stores it in conquest type of map file.
     *
     * @param p_gameState current state of the game
     * @param p_writer    file writer
     * @param p_mapFormat format in which map file has to be saved
     * @throws IOException IOException
     */
    public void parseMapToFile(GameState p_gameState, FileWriter p_writer, String p_mapFormat) throws IOException {
        if (null != p_gameState.getD_map().getD_continents() && !p_gameState.getD_map().getD_continents().isEmpty()) {
            writeContinentMetadata(p_gameState, p_writer);
        }
        if (null != p_gameState.getD_map().getD_countries() && !p_gameState.getD_map().getD_countries().isEmpty()) {
            writeCountryAndBoarderMetaData(p_gameState, p_writer);
        }
    }

    /**
     * Retrieves country and boarder data from game state and writes it to file
     * writer.
     *
     * @param p_gameState Current GameState Object
     * @param p_writer    Writer object for file
     * @throws IOException handles I/0
     */
    private void writeCountryAndBoarderMetaData(GameState p_gameState, FileWriter p_writer) throws IOException {
        String l_countryMetaData = new String();

        // Writes Country Objects to File And Organizes Border Data for each of them
        p_writer.write(System.lineSeparator() + GameConstants.CONQUEST_TERRITORIES + System.lineSeparator());
        for (Country l_country : p_gameState.getD_map().getD_countries()) {
            l_countryMetaData = new String();
            l_countryMetaData = l_country.getD_countryName().concat(",dummy1,dummy2,")
                    .concat(p_gameState.getD_map().retrieveContinentById(l_country.getD_continentId()).getD_continentName());

            if (null != l_country.getD_neighbourCountryId() && !l_country.getD_neighbourCountryId().isEmpty()) {
                for (Integer l_adjCountry : l_country.getD_neighbourCountryId()) {
                    l_countryMetaData = l_countryMetaData.concat(",")
                            .concat(p_gameState.getD_map().retrieveCountry(l_adjCountry).getD_countryName());
                }
            }
            p_writer.write(l_countryMetaData + System.lineSeparator());
        }
    }

    /**
     * Retrieves continents' data from game state and writes it to file.
     *
     * @param p_gameState Current GameState
     * @param p_writer    Writer Object for file
     * @throws IOException handles I/O
     */
    private void writeContinentMetadata(GameState p_gameState, FileWriter p_writer) throws IOException, IOException {
        p_writer.write(System.lineSeparator() + GameConstants.CONQUEST_CONTINENTS + System.lineSeparator());
        for (Continent l_continent : p_gameState.getD_map().getD_continents()) {
            p_writer.write(
                    l_continent.getD_continentName().concat("=").concat(l_continent.getD_continentValue().toString())
                            + System.lineSeparator());
        }
    }

}
