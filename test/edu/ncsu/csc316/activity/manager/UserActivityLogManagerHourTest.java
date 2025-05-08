package edu.ncsu.csc316.activity.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.activity.data.LogEntry;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.SkipListMap;

/**
 * Tests UserActivityLogManager for getEntriesByHour()
 */
public class UserActivityLogManagerHourTest {
 
	/**
	 * Instance to use for testing
	 */
    private UserActivityLogManager manager;

    /**
     * Set up for tests
     * @throws FileNotFoundException exception for empty file path
     */
    @Before
    public void setUp() throws FileNotFoundException {
        // Load data from "input/testfile"
        manager = new UserActivityLogManager("input/testfile");
    }

    /**
     * tests getEntriesByHour with basic input
     */
    @Test
    public void testGetEntriesByHourBasic() {
    	
        // Retrieve entries grouped by hour
    	SkipListMap<Integer, List<LogEntry>> entriesByHour = (SkipListMap<Integer, List<LogEntry>>) manager.getEntriesByHour();
    	
        // Check entries for a specific hour, e.g., 5AM
        List<LogEntry> entriesAt5AM = entriesByHour.get(5);
        assertEquals(2, entriesAt5AM.size()); // Expecting 2 entries at 5AM

        // Verify that the entries are in the expected order
        assertEquals("unmerge", entriesAt5AM.get(0).getAction());
        assertEquals("sort", entriesAt5AM.get(1).getAction());

    }
    

    /**
     * tests getEntriesByHour() with multiple entries
     */
    @Test
    public void testGetEntriesByHourWithMultipleEntries() {
    	SkipListMap<Integer, List<LogEntry>> entriesByHour = (SkipListMap<Integer, List<LogEntry>>) manager.getEntriesByHour();

        // Check that two entries exist for "6AM"
        List<LogEntry> entriesAt6AM = entriesByHour.get(6);
        assertEquals(1, entriesAt6AM.size());

        // Check order of actions for 6AM
        assertEquals("sort", entriesAt6AM.get(0).getAction());
    }

    /**
     * tests getEntriesByHour() with different hours
     */
    @Test
    public void testGetEntriesByHourWithDifferentHours() {
    	SkipListMap<Integer, List<LogEntry>> entriesByHour = (SkipListMap<Integer, List<LogEntry>>) manager.getEntriesByHour();

        // Check entries for other specific hours
        List<LogEntry> entriesAt12PM = entriesByHour.get(12);
        assertEquals(1, entriesAt12PM.size());
        assertEquals("sort", entriesAt12PM.get(0).getAction());

        List<LogEntry> entriesAt11PM = entriesByHour.get(23);
        assertEquals(1, entriesAt11PM.size());
        assertEquals("sort", entriesAt11PM.get(0).getAction());
    }

    /**
     * Tests getEntriesByHour with emptyHour
     */
    @Test
    public void testGetEntriesByHourEmptyHour() {
    	SkipListMap<Integer, List<LogEntry>> entriesByHour = (SkipListMap<Integer, List<LogEntry>>) manager.getEntriesByHour();

        // Test for hour with no entries, e.g., 2AM
        assertTrue(entriesByHour.get(2) == null || entriesByHour.get(2).isEmpty());
    }

}
