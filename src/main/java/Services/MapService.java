package Services;

import Constants.GameConstants;
import Exceptions.InvalidCommand;
import Exceptions.InvalidMap;
import Models.Continent;
import Models.Country;
import Models.GameState;
import Models.Map;
import Utils.CommonUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class performs all the Map Operations.
 *
 * @author Yashesh Sorathia
 */
public class MapService {

    /**
     * The loadmap method process map file.
     *
     * @param p_gameState    current state of game.
     * @param p_loadFileName map file name.
     * @return Map object after processing map file.
     * @throws InvalidMap indicates Map Object Validation failure
     */
    public Map loadMap(GameState p_gameState, String p_loadFileName) throws InvalidMap {
        Map l_map = new Map();
        List<String> l_linesOfFile = loadFile(p_loadFileName);

        if (null != l_linesOfFile && !l_linesOfFile.isEmpty()) {

            // Parses the file and stores information in objects
            List<String> l_continentData = getFileData(l_linesOfFile, "continent");
            List<Continent> l_continentObjects = parseContinentsData(l_continentData);
            List<String> l_countryData = getFileData(l_linesOfFile, "country");
            List<String> l_bordersMetaData = getFileData(l_linesOfFile, "neighbour");

            List<Country> l_countryObjects = parseCountriesData(l_countryData);
            // Updates the neighbour of countries in Objects
            l_countryObjects = parseNeighbourData(l_countryObjects, l_bordersMetaData);

            l_continentObjects = linkCountryContinents(l_countryObjects, l_continentObjects);

            l_map.setD_continents(l_continentObjects);
            l_map.setD_countries(l_countryObjects);

            p_gameState.setD_map(l_map);
        }
        return l_map;
    }


    /**
     * This method is used to modify the map.
     *
     * @param p_gameState Present state of the game
     * @param p_editFile  File name
     */
    public void editMap(GameState p_gameState, String p_editFile) throws IOException, InvalidMap {

        String l_filePath = getFilePath(p_editFile);
        File l_fileToBeEdited = new File(l_filePath);

        if (l_fileToBeEdited.createNewFile()) {
            Map l_map = new Map();
            l_map.setD_mapFile(p_editFile);
            p_gameState.setD_map(l_map);
            System.out.println("New map file has been created.");
        } else {
            System.out.println("Map File is present");
            this.loadMap(p_gameState, l_filePath);
            p_gameState.setD_map(new Map());
            p_gameState.getD_map().setD_mapFile(p_editFile);
        }
    }

    /**
     * This method is used to modify the continent.
     *
     * @param p_gameState Current state of the game
     * @param p_argument  Arguments in the command
     * @param p_operation Operation of the command
     */
    public void editContinent(GameState p_gameState, String p_argument, String p_operation) throws IOException, InvalidMap {
        String l_fileName = p_gameState.getD_map().getD_mapFile();
        Map l_map = p_gameState.getD_map();

        if (l_map != null) {
            Map l_updatedMap = continentsToMap(l_map, p_argument, p_operation);
            p_gameState.setD_map(l_updatedMap);
        }
    }

    /**
     * This method is used to link continents to map.
     *
     * @param p_map       Map object
     * @param p_argument  Arguments in the command
     * @param p_operation Operation of the command
     * @return Map Object
     */
    public Map continentsToMap(Map p_map, String p_argument, String p_operation) throws InvalidMap {
        String[] l_arguments = p_argument.split(" ");
        if (p_operation.equalsIgnoreCase("add") && l_arguments.length == 2) {
            p_map.addContinent(l_arguments[0], Integer.parseInt(l_arguments[1]));
        } else if (p_operation.equalsIgnoreCase("remove") && l_arguments.length == 1) {
            p_map.removeContinent(l_arguments[0]);
        } else {
            System.out.println("Continents cannot change!");
        }

        return p_map;
    }

    /**
     * This method is used to modify the countries.
     *
     * @param p_gameState Current state of the game
     * @param p_argument  Arguments in the command
     * @param p_operation Operation of the command
     */
    public void editCountry(GameState p_gameState, String p_argument, String p_operation) throws InvalidMap {
        String l_fileName = p_gameState.getD_map().getD_mapFile();
        Map l_map = p_gameState.getD_map();

        if (l_map != null) {
            Map l_updatedMap = countryToMap(l_map, p_argument, p_operation);
            p_gameState.setD_map(l_updatedMap);
        }
    }

    /**
     * This method is used to link countries to map.
     *
     * @param p_map       Map object
     * @param p_argument  Arguments in the command
     * @param p_operation Operation of the command
     * @return Map Object
     */
    public Map countryToMap(Map p_map, String p_argument, String p_operation) throws InvalidMap {
        String[] l_arguments = p_argument.split(" ");
        if (p_operation.equalsIgnoreCase("add") && l_arguments.length == 2) {
            p_map.addCountry(l_arguments[0], l_arguments[1]);
        } else if (p_operation.equalsIgnoreCase("remove") && l_arguments.length == 1) {
            p_map.removeCountry(l_arguments[0]);
        } else {
            System.out.println("Countries cannot change!");
        }
        return p_map;
    }

    /**
     * This method is used to modify neighbouring countries.
     *
     * @param p_gameState Current state of the game
     * @param p_argument  Arguments in the command
     * @param p_operation Operation of the command
     */
    public void editNeighbour(GameState p_gameState, String p_argument, String p_operation) throws InvalidMap {
        String l_fileName = p_gameState.getD_map().getD_mapFile();
        Map l_map = p_gameState.getD_map();

        if (l_map != null) {
            Map l_updatedMap = neighbourToMap(l_map, p_argument, p_operation);
            p_gameState.setD_map(l_updatedMap);
        }
    }

    /**
     * This method is used to link neighbours to map.
     *
     * @param p_map       Map object
     * @param p_argument  Arguments in the command
     * @param p_operation Operation of the command
     * @return Map Object
     */
    public Map neighbourToMap(Map p_map, String p_argument, String p_operation) throws InvalidMap {
        String[] l_arguments = p_argument.split(" ");
        if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length == 2) {
            p_map.addCountryNeighbour(l_arguments[0], l_arguments[1]);
        } else if (p_operation.equalsIgnoreCase("remove") && p_argument.split(" ").length == 2) {
            p_map.removeCountryNeighbour(l_arguments[0], l_arguments[1]);
        } else {
            System.out.println("Neighbors cannot change!");
        }

        return p_map;
    }

    /**
     * This method is used to link country to continents.
     *
     * @param p_countries  List of Countries
     * @param p_continents List of Continents
     * @return List of Continents
     */
    public List<Continent> countryToContinents(List<Country> p_countries, List<Continent> p_continents) {
        for (Country l_country : p_countries) {
            for (Continent l_continent : p_continents) {
                if (l_continent.getD_continentID().equals(l_country.getD_continentId())) {
                    l_continent.addCountry(l_country);
                }
            }
        }
        return p_continents;
    }

    /**
     * Method to construct updated Continents list based on passed operations - Add/Remove
     * and Arguments.
     *
     * @param p_gameState      Current GameState Object
     * @param p_mapToBeUpdated Map Object to be Updated
     * @param p_operation      Operation to perform on Continents
     * @param p_argument       Arguments pertaining to the operations
     * @return List of updated continents
     * @throws InvalidMap invalidmap exception
     */
    public Map addOrRemoveContinents(GameState p_gameState, Map p_mapToBeUpdated, String p_operation, String p_argument) throws InvalidMap {

        try {
            if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length == 2) {
                p_mapToBeUpdated.addContinent(p_argument.split(" ")[0], Integer.parseInt(p_argument.split(" ")[1]));
                this.setD_MapServiceLog("Continent " + p_argument.split(" ")[0] + " added successfully!", p_gameState);
            } else if (p_operation.equalsIgnoreCase("remove") && p_argument.split(" ").length == 1) {
                p_mapToBeUpdated.removeContinent(p_argument.split(" ")[0]);
                this.setD_MapServiceLog("Continent " + p_argument.split(" ")[0] + " removed successfully!", p_gameState);
            } else {
                throw new InvalidMap("Continent " + p_argument.split(" ")[0] + " couldn't be added/removed. Changes are not made due to Invalid Command Passed.");
            }
        } catch (InvalidMap | NumberFormatException l_e) {
            this.setD_MapServiceLog(l_e.getMessage(), p_gameState);
        }
        return p_mapToBeUpdated;
    }

    /**
     * Method to perform the add/remove operation on the countries in map.
     *
     * @param p_gameState      Current GameState Object
     * @param p_mapToBeUpdated The Map to be updated
     * @param p_operation      Operation to be performed
     * @param p_argument       Arguments for the pertaining command operation
     * @return Updated Map Object
     * @throws InvalidMap invalidmap exception
     */
    public Map addOrRemoveCountry(GameState p_gameState, Map p_mapToBeUpdated, String p_argument, String p_operation) throws InvalidMap {

        try {
            if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length == 2) {
                p_mapToBeUpdated.addCountry(p_argument.split(" ")[0], p_argument.split(" ")[1]);
                this.setD_MapServiceLog("Country " + p_argument.split(" ")[0] + " added successfully!", p_gameState);
            } else if (p_operation.equalsIgnoreCase("remove") && p_argument.split(" ").length == 1) {
                p_mapToBeUpdated.removeCountry(p_argument.split(" ")[0]);
                this.setD_MapServiceLog("Country " + p_argument.split(" ")[0] + " removed successfully!", p_gameState);
            } else {
                throw new InvalidMap("Country " + p_argument.split(" ")[0] + " could not be " + p_operation + "ed!");
            }
        } catch (InvalidMap l_e) {
            this.setD_MapServiceLog(l_e.getMessage(), p_gameState);
        }
        return p_mapToBeUpdated;
    }

    /**
     * Method to perform the add/remove operation on Map Object.
     *
     * @param p_gameState      Current GameState Object
     * @param p_mapToBeUpdated The Map to be updated
     * @param p_operation      Add/Remove operation to be performed
     * @param p_argument       Arguments for the pertaining command operation
     * @return map to be updated
     * @throws InvalidMap invalidmap exception
     */
    public Map addOrRemoveNeighbour(GameState p_gameState, Map p_mapToBeUpdated, String p_argument, String p_operation) throws InvalidMap {

        try {
            if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length == 2) {
                p_mapToBeUpdated.addCountryNeighbour(p_argument.split(" ")[0], p_argument.split(" ")[1]);
                this.setD_MapServiceLog("Neighbour Pair " + p_argument.split(" ")[0] + " " + p_argument.split(" ")[1] + " added successfully!", p_gameState);
            } else if (p_operation.equalsIgnoreCase("remove") && p_argument.split(" ").length == 2) {
                p_mapToBeUpdated.removeCountryNeighbour(p_argument.split(" ")[0], p_argument.split(" ")[1]);
                this.setD_MapServiceLog("Neighbour Pair " + p_argument.split(" ")[0] + " " + p_argument.split(" ")[1] + " removed successfully!", p_gameState);
            } else {
                throw new InvalidMap("Neighbour could not be " + p_operation + "ed!");
            }
        } catch (InvalidMap l_e) {
            this.setD_MapServiceLog(l_e.getMessage(), p_gameState);
        }
        return p_mapToBeUpdated;
    }


    /**
     * Controls the Flow of Edit Operations: editcontinent, editcountry, editneighbor.
     *
     * @param p_gameState       Current GameState Object.
     * @param p_argument        Arguments for the pertaining command operation.
     * @param p_operation       Add/Remove operation to be performed.
     * @param p_switchParameter Type of Edit Operation to be performed.
     * @throws IOException    Exception.
     * @throws InvalidMap     invalidmap exception.
     * @throws InvalidCommand invalid command exception
     */
    public void editFunctions(GameState p_gameState, String p_argument, String p_operation, Integer p_switchParameter) throws IOException, InvalidMap, InvalidCommand {
        Map l_updatedMap;
        String l_mapFileName = p_gameState.getD_map().getD_mapFile();
        Map l_mapToBeUpdated = (CommonUtil.isNull(p_gameState.getD_map().getD_continents()) && CommonUtil.isNull(p_gameState.getD_map().getD_countries())) ? this.loadMap(p_gameState, l_mapFileName) : p_gameState.getD_map();

        // Edit Control Logic for Continent, Country & Neighbor
        if (!CommonUtil.isNull(l_mapToBeUpdated)) {
            switch (p_switchParameter) {
                case 1:
                    l_updatedMap = addOrRemoveContinents(p_gameState, l_mapToBeUpdated, p_operation, p_argument);
                    break;
                case 2:
                    l_updatedMap = addOrRemoveCountry(p_gameState, l_mapToBeUpdated, p_operation, p_argument);
                    break;
                case 3:
                    l_updatedMap = addOrRemoveNeighbour(p_gameState, l_mapToBeUpdated, p_operation, p_argument);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + p_switchParameter);
            }
            p_gameState.setD_map(l_updatedMap);
            p_gameState.getD_map().setD_mapFile(l_mapFileName);
        }
    }

    /**
     * This method is used to save the map.
     *
     * @param p_gameState Current Game State
     * @param p_fileName  Name of the file
     * @return boolean true if map save was successful else false
     */
    public boolean saveMap(GameState p_gameState, String p_fileName) throws InvalidMap{
        boolean l_flagValidate = false;
        try {

            // Verifies if the file linked to savemap and edited by user are same
            if (!p_fileName.equalsIgnoreCase(p_gameState.getD_map().getD_mapFile())) {
                p_gameState.setError("Kindly provide same file name to save which you have given for edit");
                return false;
            } else {
                if (null != p_gameState.getD_map()) {
                    Models.Map l_currentMap = p_gameState.getD_map();

                    // Proceeds to save the map if it passes the validation check
                    this.setD_MapServiceLog("Validating Map......", p_gameState);
                    //boolean l_mapValidationStatus = l_currentMap.Validate();
                    if (l_currentMap.Validate()) {
                        Files.deleteIfExists(Paths.get(CommonUtil.getMapFilePath(p_fileName)));
                        FileWriter l_writer = new FileWriter(CommonUtil.getMapFilePath(p_fileName));

                        if (null != p_gameState.getD_map().getD_continents()
                                && !p_gameState.getD_map().getD_continents().isEmpty()) {
                            writeContinentdata(p_gameState, l_writer);
                        }
                        if (null != p_gameState.getD_map().getD_countries()
                                && !p_gameState.getD_map().getD_countries().isEmpty()) {
                            writeCountryAndNeighbourData(p_gameState, l_writer);
                        }
                        p_gameState.updateLog("Map Saved Successfully", "effect");
                        l_writer.close();
                    }
                } else {
                    p_gameState.updateLog("Validation failed! Cannot Save the Map file!", "effect");
                    p_gameState.setError("Validation Failed");
                    return false;
                }
            }
            return true;
        } catch (IOException | InvalidMap l_e) {
            this.setD_MapServiceLog(l_e.getMessage(), p_gameState);
            p_gameState.updateLog("Couldn't save the changes in map file!", "effect");
            p_gameState.setError("Error in saving map file");
            return false;
        }
    }

    /**
     * This method is used to write country and neighbour into file.
     *
     * @param p_gameState Current State of the Game
     * @param p_writer    File Writer
     */
    private void writeCountryAndNeighbourData(GameState p_gameState, FileWriter p_writer) throws IOException {
        String l_countryMetaData = new String();
        String l_bordersMetaData = new String();
        List<String> l_bordersList = new ArrayList<>();

        // Writes Country Objects to File And Organizes Border Data for each of them
        p_writer.write(System.lineSeparator() + GameConstants.COUNTRIES + System.lineSeparator());
        for (Country l_country : p_gameState.getD_map().getD_countries()) {
            l_countryMetaData = new String();
            l_countryMetaData = l_country.getD_countryId().toString().concat(" ").concat(l_country.getD_countryName())
                    .concat(" ").concat(String.valueOf(l_country.getD_continentId()));
            p_writer.write(l_countryMetaData + System.lineSeparator());

            if (null != l_country.getD_neighbourCountryIds() && !l_country.getD_neighbourCountryIds().isEmpty()) {
                l_bordersMetaData = new String();
                l_bordersMetaData = l_country.getD_countryId().toString();
                for (Integer l_adjCountry : l_country.getD_neighbourCountryIds()) {
                    l_bordersMetaData = l_bordersMetaData.concat(" ").concat(l_adjCountry.toString());
                }
                l_bordersList.add(l_bordersMetaData);
            }
        }

        // Writes Border data to the File
        if (null != l_bordersList && !l_bordersList.isEmpty()) {
            p_writer.write(System.lineSeparator() + GameConstants.BORDERS + System.lineSeparator());
            for (String l_borderStr : l_bordersList) {
                p_writer.write(l_borderStr + System.lineSeparator());
            }
        }
    }

    /**
     * This method is used to write continents into file.
     *
     * @param p_gameState Current State of the Game
     * @param p_writer    File Writer
     */
    private void writeContinentdata(GameState p_gameState, FileWriter p_writer) throws IOException {
        p_writer.write(System.lineSeparator() + GameConstants.CONTINENTS + System.lineSeparator());
        for (Continent l_continent : p_gameState.getD_map().getD_continents()) {
            p_writer.write(
                    l_continent.getD_continentName().concat(" ").concat(l_continent.getD_continentValue().toString())
                            + System.lineSeparator());
        }
    }


    /**
     * This method is used to extract contents of the file.
     *
     * @param p_filePath Path of the filename provided
     * @return Lines of file
     */
    public List<String> loadFile(String p_filePath) {
        String l_filePath = CommonUtil.getMapFilePath(p_filePath);
        List<String> l_fileLines = new ArrayList<>();
        BufferedReader l_reader;
        try {
            l_reader = new BufferedReader(new FileReader(l_filePath));
            l_fileLines = l_reader.lines().collect(Collectors.toList());
            l_reader.close();
        } catch (IOException l_e1) {
            System.out.println("File not Found!");
        }
        return l_fileLines;
    }

    /**
     * This method is used to retrieve contents of the file lines.
     *
     * @param p_linesOfFile Line of File
     * @param p_case        Switch Case
     * @return List of String with particular lines
     */
    public List<String> getFileData(List<String> p_linesOfFile, String p_case) {
        switch (p_case) {
            case "continent":
                return p_linesOfFile.subList(
                        p_linesOfFile.indexOf(GameConstants.CONTINENTS) + 1,
                        p_linesOfFile.indexOf(GameConstants.COUNTRIES) - 1);
            case "country":
                return p_linesOfFile.subList(p_linesOfFile.indexOf(GameConstants.COUNTRIES) + 1,
                        p_linesOfFile.indexOf(GameConstants.BORDERS) - 1);
            case "neighbour":
                return p_linesOfFile.subList(p_linesOfFile.indexOf(GameConstants.BORDERS) + 1,
                        p_linesOfFile.size());
            default:
                return null;
        }
    }

    /**
     * This method is used to retrieve formatted continents data from line of files.
     *
     * @param p_continentList Unformatted Continents data
     * @return Formatted Continents data
     */
    public List<Continent> parseContinentsData(List<String> p_continentList) {
        List<Continent> l_continents = new ArrayList<Continent>();
        int l_continentId = 1;

        for (String l_continent : p_continentList) {
            String[] l_data = l_continent.split(" ");
            l_continents.add(new Continent(l_continentId, l_data[0], Integer.parseInt(l_data[1])));
            l_continentId++;
        }
        return l_continents;
    }

    /**
     * This method is used to retrieve formatted countries data from line of files.
     *
     * @param p_countriesList Unformatted Countries data
     * @return Formatted Countries data
     */
    public List<Country> parseCountriesData(List<String> p_countriesList) {
        List<Country> l_countriesList = new ArrayList<Country>();
        LinkedHashMap<Integer, List<Integer>> l_countryNeighbors = new LinkedHashMap<Integer, List<Integer>>();

        for (String country : p_countriesList) {
            String[] l_data = country.split(" ");
            l_countriesList.add(new Country(Integer.parseInt(l_data[0]), l_data[1],
                    Integer.parseInt(l_data[2])));
        }
        return l_countriesList;
    }

    /**
     * This method is used to retrieve formatted neighbour data from line of files.
     *
     * @param p_countriesList Countries data without neighbours
     * @param p_neighbourList Unformatted neighbours data
     * @return Formatted Countries data with Neighbour
     */
    public List<Country> parseNeighbourData(List<Country> p_countriesList, List<String> p_neighbourList) {
        LinkedHashMap<Integer, List<Integer>> l_countryNeighbors = new LinkedHashMap<Integer, List<Integer>>();

        for (String l_neighbour : p_neighbourList) {
            if (null != l_neighbour && !l_neighbour.isEmpty()) {
                ArrayList<Integer> l_neighbours = new ArrayList<Integer>();
                String[] l_splitString = l_neighbour.split(" ");
                for (int i = 1; i <= l_splitString.length - 1; i++) {
                    l_neighbours.add(Integer.parseInt(l_splitString[i]));
                }
                l_countryNeighbors.put(Integer.parseInt(l_splitString[0]), l_neighbours);
            }
        }
        for (Country l_country : p_countriesList) {
            List<Integer> l_neighbourCountries = l_countryNeighbors.get(l_country.getD_countryId());
            l_country.setD_neighbourCountryIds(l_neighbourCountries);
        }

        return p_countriesList;
    }

    /**
     * Links countries to corresponding continents and sets them in object of
     * continent.
     *
     * @param p_countries  Total Country Objects
     * @param p_continents Total Continent Objects
     * @return List of updated continents
     */
    public List<Continent> linkCountryContinents(List<Country> p_countries, List<Continent> p_continents) {
        for (Country c : p_countries) {
            for (Continent cont : p_continents) {
                if (cont.getD_continentID().equals(c.getD_continentId())) {
                    cont.addCountry(c);
                }
            }
        }
        return p_continents;
    }


    /**
     * This method is used to fetch the file path.
     *
     * @param p_fileName Name of file.
     * @return Path of file
     */
    public String getFilePath(String p_fileName) {
        return new File("").getAbsolutePath() + File.separator + "src/main/resources" + File.separator + p_fileName;
    }

    /**
     * This method is used to reset the current state of the game.
     *
     * @param p_gameState Current Game state
     */
    public void resetState(GameState p_gameState, String p_fileToLoad) {
        System.out.println("Map cannot be loaded, as it is invalid. Kindly provide valid map");
        p_gameState.updateLog(p_fileToLoad + " map could not be loaded as it is invalid!", "effect");
        p_gameState.setD_map(new Models.Map());
    }

    /**
     * Method to set the log of map editor methods.
     *
     * @param p_MapServiceLog String containing log
     * @param p_gameState     current gamestate instance
     */
    public void setD_MapServiceLog(String p_MapServiceLog, GameState p_gameState) {
        System.out.println(p_MapServiceLog);
        p_gameState.updateLog(p_MapServiceLog, "effect");
    }

}
