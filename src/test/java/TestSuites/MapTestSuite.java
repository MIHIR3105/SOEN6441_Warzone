package TestSuites;

import Models.MapTest;
import Services.MapServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit for running various map related tests such as load map, edit
 * country, continents, adjacency, map validations and save map
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ MapTest.class, MapServiceTest.class })
public class MapTestSuite {
}