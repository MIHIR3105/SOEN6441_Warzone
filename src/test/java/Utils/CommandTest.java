package Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * This class is used to test functionality of Command class functions.
 */
public class CommandTest {
	
	/**
	 * Testing if the command entered is valid or not
	 */
    @Test
    public void test_validCommand_getMainCommand(){
        Command l_command = new Command("editcontinent -add continentID continentvalue");
        String l_rootCommand = l_command.getMainCommand();

        assertEquals("editcontinent",l_rootCommand);
    }

    /**
     * Testing if the command entered is invalid or not
     */
    @Test
    public void test_inValidCommand_getMainCommand(){
        Command l_command = new Command("");
        String l_rootCommand = l_command.getMainCommand();

        assertEquals("", l_rootCommand);
    }

    /**
     * Testing the single word commands
     */
    @Test
    public void test_singleWord_getMainCommand(){
        Command l_command = new Command("validatemap");
        String l_rootCommand = l_command.getMainCommand();

        assertEquals("validatemap", l_rootCommand);
    }

    /**
     * testing the commands
     */
    @Test
    public void test_noFlagCommand_getMainCommand(){
        Command l_command = new Command("loadmap abc.txt");
        String l_rootCommand = l_command.getMainCommand();

        assertEquals("loadmap", l_rootCommand);
    }

    /**
     * testing the single operation commands
     */
    @Test
    public void test_singleCommand_getOperationsAndArguments(){
        Command l_command = new Command("editcontinent -remove continentID");
        List<Map<String , String>> l_actualOperationsAndValues = l_command.getTaskAndArguments();

        // Preparing Expected Value
        List<Map<String , String>> l_expectedOperationsAndValues = new ArrayList<Map<String, String>>();

        Map<String, String> l_expectedCommandTwo = new HashMap<String, String>() {{
            put("arguments", "continentID");
            put("operation", "remove");
        }};
        l_expectedOperationsAndValues.add(l_expectedCommandTwo);

        assertEquals(l_expectedOperationsAndValues, l_actualOperationsAndValues);
    }

    /**
     * testing if more than one spaces in between the command and its parameters is acceptable or not
     */
    @Test
    public void test_singleCommandWithExtraSpaces_getOperationsAndArguments(){
        Command l_command = new Command("editcontinent      -remove continentID");
        List<Map<String , String>> l_actualOperationsAndValues = l_command.getTaskAndArguments();

        // Preparing Expected Value
        List<Map<String , String>> l_expectedOperationsAndValues = new ArrayList<Map<String, String>>();

        Map<String, String> l_expectedCommandTwo = new HashMap<String, String>() {{
            put("arguments", "continentID");
            put("operation", "remove");
        }};
        l_expectedOperationsAndValues.add(l_expectedCommandTwo);

        assertEquals(l_expectedOperationsAndValues, l_actualOperationsAndValues);
    }

    /**
     * testing the multiple commands in a single line are working or not
     */
    @Test
    public void test_multiCommand_getOperationsAndArguments(){
        Command l_command = new Command("editcontinent -add continentID continentValue  -remove continentID");
        List<Map<String , String>> l_actualOperationsAndValues = l_command.getTaskAndArguments();

        // Preparing Expected Value
        List<Map<String , String>> l_expectedOperationsAndValues = new ArrayList<Map<String, String>>();

        Map<String, String> l_expectedCommandOne = new HashMap<String, String>() {{
            put("arguments", "continentID continentValue");
            put("operation", "add");
        }};
        Map<String, String> l_expectedCommandTwo = new HashMap<String, String>() {{
            put("arguments", "continentID");
            put("operation", "remove");
        }};
        l_expectedOperationsAndValues.add(l_expectedCommandOne);
        l_expectedOperationsAndValues.add(l_expectedCommandTwo);

        assertEquals(l_expectedOperationsAndValues, l_actualOperationsAndValues);
    }

    /**
     * testing commands
     */
    @Test
    public void test_noFlagCommand_getOperationsAndArguments(){
        Command l_command = new Command("loadmap abc.txt");
        List<Map<String , String>> l_actualOperationsAndValues = l_command.getTaskAndArguments();

        // Preparing Expected Value
        List<Map<String , String>> l_expectedOperationsAndValues = new ArrayList<Map<String, String>>();

        Map<String, String> l_expectedCommandOne = new HashMap<String, String>() {{
            put("arguments", "abc.txt");
            put("operation", "filename");
        }};
        l_expectedOperationsAndValues.add(l_expectedCommandOne);

        assertEquals(l_expectedOperationsAndValues, l_actualOperationsAndValues);
    }

    /**
     * testing if more than one spaces in between the command and its parameters is acceptable or not
     */
    @Test
    public void test_noFlagCommandWithExtraSpaces_getOperationsAndArguments(){
        Command l_command = new Command("loadmap         abc.txt");
        List<Map<String , String>> l_actualOperationsAndValues = l_command.getTaskAndArguments();

        // Preparing Expected Value
        List<Map<String , String>> l_expectedOperationsAndValues = new ArrayList<Map<String, String>>();

        Map<String, String> l_expectedCommandOne = new HashMap<String, String>() {{
            put("arguments", "abc.txt");
            put("operation", "filename");
        }};
        l_expectedOperationsAndValues.add(l_expectedCommandOne);

        assertEquals(l_expectedOperationsAndValues, l_actualOperationsAndValues);
    }
}
