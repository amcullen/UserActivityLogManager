package edu.ncsu.csc316.activity.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * Tests ReportManager for getHourReport()
 */
class ReportManagerHourTest {

	/**
	 * Tests getHourReport() with generic entries
	 */
    @Test
    public void testGetHourReportWithEntries() {
    	
		try {
			ReportManager reportTest = new ReportManager("input/testfile");
			
	        // Test with an hour that has multiple entries (5AM)
	        String report = reportTest.getHourReport(5);

	        // Expected output for entries during 5AM
	        String expectedReport = 
	            "Activities recorded during hour 5 [\n" +
	            "   gphsu, 02/27/2017 05:26:50AM, sort, HL7 Code 422\n" +
	            "   hqcooney, 09/15/2017 05:44:15AM, unmerge, notification NX1115\n" +
	            "]";

	        assertEquals(expectedReport, report);
	        
		} catch (FileNotFoundException e) {
			fail(); 
		}
    }
    
    /**
     * Tests getHourReport with no entries
     */
    @Test
    public void testGetHourReportNoEntries() {
		try {
			ReportManager reportTest = new ReportManager("input/testfile");
			
	        // Test with an hour that has no entries (2AM)
	        String report = reportTest.getHourReport(2);

	        // Expected message for an hour with no activities
	        String expectedReport = "No activities were recorded during hour 2";

	        assertEquals(expectedReport, report);
	        
		} catch (FileNotFoundException e) {
			fail(); 
		}
    }

    /**
     * Tests getHourReport with an invalid hour
     */
    @Test
    public void testGetHourReportInvalidHour() {
		try {
			ReportManager reportTest = new ReportManager("input/testfile");
			
	        String report = reportTest.getHourReport(25);
			
	        // Expected error message for invalid hour
	        String expectedReport = "Please enter a valid hour between 0 (12AM) and 23 (11PM)";

	        assertEquals(expectedReport, report);
	        
		} catch (FileNotFoundException e) {
			fail(); 
		}
    }
    
	/**
	 * Tests getHourReport() same time
	 */
    @Test
    public void testGetHourReportWithEntriesSame() {
    	
		try {
			ReportManager reportTest = new ReportManager("input/emptyFile");
			
	        // Test with an hour that has multiple entries (5AM)
	        String report = reportTest.getHourReport(12);

	        // Expected output for entries during 5AM
	        String expectedReport = 
	            "Activities recorded during hour 12 [\n" +
	            "   gphsu, 01/04/2016 12:44:52PM, sort, HL7 Code 422\n" +
	            "   hqcooney, 09/15/2017 12:44:51PM, sort, HL7 Code 422\n" +
	            "   hqcooney, 09/15/2017 12:44:51PM, sort, abc\n" +
	            "   hqcooney, 09/15/2017 12:44:52PM, unmerge, notification NX1115\n" + 
	            "]";

	        assertEquals(expectedReport, report);
	        
		} catch (FileNotFoundException e) {
			fail(); 
		}
    }
    
    

}
