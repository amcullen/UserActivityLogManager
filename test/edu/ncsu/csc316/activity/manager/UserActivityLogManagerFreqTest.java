package edu.ncsu.csc316.activity.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc316.dsa.list.List;

/**
 * Tests UserActivityLogManager for getTopActivities(int)
 */
public class UserActivityLogManagerFreqTest {

	/**
	 * Instance for testing
	 */
    private UserActivityLogManager manager;

    /**
     * Instance for testing
     */
    private UserActivityLogManager management; 
    
    /**
     * Set up for tests
     * @throws FileNotFoundException exception if file path is empty
     */
    @BeforeEach
    public void setUp() throws FileNotFoundException {
        // Initialize the UserActivityLogManager with the "testfile"
        manager = new UserActivityLogManager("input/testfile"); 
        management = new UserActivityLogManager("input/emptyFile");
    }

    /**
     * tests getTopActivities(int) for basic input
     */
    @Test
    public void testGetTopActivitiesSingleTopActivity() {
        // Test for a single top activity
        List<String> topActivities = manager.getTopActivities(1);
        assertEquals(1, topActivities.size());
        assertEquals("13: sort HL7 Code 422", topActivities.get(0)); // Expected top activity based on frequency
    }

    /**
     * Tests getTopActivities(int) for top three activities
     */
    @Test
    public void testGetTopActivitiesTopThreeActivities() {
        // Test for the top 3 activities
        List<String> topActivities = manager.getTopActivities(3); 
        assertEquals(3, topActivities.size());
        
        // Verify the top activities in order
        assertEquals("13: sort HL7 Code 422", topActivities.get(0));     // Most frequent
        assertEquals("2: print office visit OV02132", topActivities.get(1));    // Second most frequent
        assertEquals("1: unmerge notification NX1115", topActivities.get(2));  // Third most frequent 
    }

    /**
     * Tests getTopActivities(int) for getting all activities
     */
    @Test
    public void testGetTopActivitiesAllActivities() {
        // Request more activities than available to ensure it handles the limit correctly
        List<String> topActivities = manager.getTopActivities(10);
        
        // Verify that it returns all unique activities sorted by frequency
        assertEquals(4, topActivities.size()); // Expect 4 unique actions: "sort", "print", "unmerge", "view"
        
        assertEquals("13: sort HL7 Code 422", topActivities.get(0));     // Most frequent
        assertEquals("2: print office visit OV02132", topActivities.get(1));    // Second most frequent
        assertEquals("1: unmerge notification NX1115", topActivities.get(2));  // Third most frequent
        assertEquals("1: view HL7 Code 422", topActivities.get(3));     // Fourth most frequent
    }

    /**
     * tests getTopActivities(int) with no activities
     */
    @Test
    public void testGetTopActivitiesNoActivities() { 
        // If there are no activities in the log file (or an empty log), expect an empty list
        List<String> topActivities = manager.getTopActivities(0);
        assertTrue(topActivities.isEmpty());
    }
    
    /**
     * Tests getTopActivities(int) for getting all activities
     */
    @Test
    public void testGetTopActivitiesAllActivitiesSame() {
        // Request more activities than available to ensure it handles the limit correctly
    	
    	List<String> topActivities = management.getTopActivities(10); 
        
        // Verify that it returns all unique activities sorted by frequency
        assertEquals(5, topActivities.size()); // Expect 4 unique actions: "sort", "print", "unmerge", "view"
        
        assertEquals("14: sort HL7 Code 422", topActivities.get(0));     // Most frequent
        assertEquals("2: print office visit OV02132", topActivities.get(1));    // Second most frequent
        assertEquals("1: sort abc", topActivities.get(2)); 
        assertEquals("1: unmerge HL7 Code 422", topActivities.get(3));  // Third most frequent
        assertEquals("1: unmerge notification NX1115", topActivities.get(4));     // Fourth most frequent 
    }
    
}
