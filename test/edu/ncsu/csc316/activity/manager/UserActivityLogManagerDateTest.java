package edu.ncsu.csc316.activity.manager;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.activity.data.LogEntry;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.SkipListMap;

/**
 * Tests UserActivityLogManager for getEntriesByDate()
 */
public class UserActivityLogManagerDateTest { 

	/**
	 * Instance for testing
	 */
    private UserActivityLogManager manager;

    /**
     * Set up for testing 
     * @throws FileNotFoundException exception for empty file path
     */
    @Before
    public void setUp() throws FileNotFoundException {
        // Load data from "input/testfile"
        manager = new UserActivityLogManager("input/testfile");
    }

    /**
     * Tests getEntriesByDate() for basic input
     */
    @Test
    public void testGetEntriesByDateBasic() {
        // Use the entries from `testfile` and validate they are grouped correctly
        @SuppressWarnings("unchecked")
        SkipListMap<String, List<LogEntry>> result = (SkipListMap<String, List<LogEntry>>) manager.getEntriesByDate();

        // Check the number of dates in the map
        assertEquals(16, result.size()); // Adjust this based on actual unique dates in `testfile`

        // Check entries for a specific date
        List<LogEntry> entriesOn01042016 = result.get("01/04/2016");
        assertEquals(2, entriesOn01042016.size());
        assertEquals("view", entriesOn01042016.get(0).getAction());
        assertEquals("sort", entriesOn01042016.get(1).getAction());
    }

    /*
    @Test
    public void testGetEntriesByDateEmptyLogList() throws FileNotFoundException {
        // Load an empty file (assuming there's a file named "emptyfile" in the `input` folder)
        manager = new UserActivityLogManager("input/emptyfile");
        @SuppressWarnings("unchecked")
        SkipListMap<String, List<LogEntry>> result = (SkipListMap<String, List<LogEntry>>) manager.getEntriesByDate();
        assertTrue(result.isEmpty());
    } */

    /**
     * Tests getEntriesByDate() with multiple entries having the same date
     */
    @Test
    public void testGetEntriesByDateMultipleEntriesSameDate() {
        @SuppressWarnings("unchecked")
        SkipListMap<String, List<LogEntry>> result = (SkipListMap<String, List<LogEntry>>) manager.getEntriesByDate();

        // Check that two entries exist for "01/04/2016"
        List<LogEntry> entriesOn01042016 = result.get("01/04/2016");
        assertEquals(2, entriesOn01042016.size());

        // Verify that the entries are correct
        assertEquals("view", entriesOn01042016.get(0).getAction()); 
        assertEquals("sort", entriesOn01042016.get(1).getAction());
    }

    /**
     * Tests getEntriesByDate() with different dates 
     */
    @Test
    public void testGetEntriesByDateDifferentDates() {

		SkipListMap<String, List<LogEntry>> result = (SkipListMap<String, List<LogEntry>>) manager.getEntriesByDate();

        // Check entries for another date
        List<LogEntry> entriesOn03022015 = result.get("03/02/2015");
        assertEquals(1, entriesOn03022015.size());
        assertEquals("print", entriesOn03022015.get(0).getAction());

        List<LogEntry> entriesOn10062016 = result.get("10/06/2016");
        assertEquals(1, entriesOn10062016.size());
        assertEquals("sort", entriesOn10062016.get(0).getAction());
    }

    /*
    @Test
    public void testDateFormatConsistency() {
        @SuppressWarnings("unchecked")
        SkipListMap<String, List<LogEntry>> result = (SkipListMap<String, List<LogEntry>>) manager.getEntriesByDate();

        // Verify that all keys are in MM/DD/YYYY format
        for (String date : result.keySet()) {
            assertTrue(date.matches("\\d{2}/\\d{2}/\\d{4}"));
        }
    } */
}
