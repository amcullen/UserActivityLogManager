package edu.ncsu.csc316.activity.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * Tests ReportManager for getTopUserActivitiesReport()
 */
public class ReportManagerFreqTest {

	/*
    private ReportManager reportManager;

    @Before
    public void setUp() throws FileNotFoundException {
        // Initialize ReportManager with the provided test file
        reportManager = new ReportManager("input/testfile");
    } */

	
    /**
     * Tests getTopUserActivitiesReport() with generic entries
     */
    @Test
    public void testGetTopUserActivitiesReportBasic() {
        // Request the top 3 activities
    	try {
			ReportManager reportTest = new ReportManager("input/testfile");
			
	        String report = reportTest.getTopUserActivitiesReport(4);

	        // Expected output for top activities in the correct format
	        String expectedReport = 
	            "Top User Activities Report [\n" +
	            "   13: sort HL7 Code 422\n" +
	            "   2: print office visit OV02132\n" +
	            "   1: unmerge notification NX1115\n" +
	            "   1: view HL7 Code 422\n" +
	            "]";
	        
	        assertEquals(expectedReport, report);
		} catch (FileNotFoundException e) {
			fail(); 
		}         
    }

    /**
     * Tests getTopUserActivitiesReport() with more than are available 
     */
    @Test
    public void testGetTopUserActivitiesReportMoreThanAvailable() {
    	
    	try { 
    		ReportManager reportTest = new ReportManager("input/testfile");
    		
    		String report = reportTest.getTopUserActivitiesReport(10);
    		
            String expectedReport = 
                "Top User Activities Report [\n" +
                "   13: sort HL7 Code 422\n" +
                "   2: print office visit OV02132\n" +
                "   1: unmerge notification NX1115\n" +
                "   1: view HL7 Code 422\n" +
                "]";

    			assertEquals(expectedReport, report);  
    		
    	} catch (FileNotFoundException e) {
    		fail();
    	} 
    }
    
    /**
     * Tests getTopUserActivitiesReport() for fewer than are available 
     */
    @Test
    public void testGetTopUserActivitiesReportFewerThanAvailable() {
    	
    	ReportManager reportTest;
		try {
			reportTest = new ReportManager("input/testfile");
			
	        String report = reportTest.getTopUserActivitiesReport(2);

	        String expectedReport = 
	                "Top User Activities Report [\n" +
	                "   13: sort HL7 Code 422\n" +
	                "   2: print office visit OV02132\n" +
	                "]";

	        assertEquals(expectedReport, report);
	        
		} catch (FileNotFoundException e) {
			fail(); 
		} 
    }
    
    /*
    @Test
    public void testGetTopUserActivitiesReportNoActivities() throws FileNotFoundException {
    	
		try {
	        // Load an empty file for the activity log manager
	        ReportManager emptyReportManager = new ReportManager("input/emptyFile");

	        // Call the report method with an empty log
	        String report = emptyReportManager.getTopUserActivitiesReport(3);
	        
	        assertEquals("No activities found.", report);
	        
		} catch (FileNotFoundException e) { 
			fail(); 
		} 
    } */
    
}
