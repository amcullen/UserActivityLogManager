package edu.ncsu.csc316.activity.manager;

import static org.junit.jupiter.api.Assertions.*;


import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * Tests ReportManager for getDateReport() 
 */
class ReportManagerDateTest {

	/*
    private ReportManager reportManager;

    @Before
    public void setUp() throws FileNotFoundException {
        // Initialize ReportManager with the provided test file
        reportManager = new ReportManager("input/testfile");
        
    }*/
    
	/*
    @Test
    public void testGetTopUserActivitiesReportBasic() {
        // Request the top 3 activities
    	try {
			ReportManager reportTest = new ReportManager("input/testfile");
			
			String reportString = reportTest.getDateReport("02/19/2017");
			
	        String expectedReport = 
	                "Log Entries for 02/19/2017:\r\n"
	                + "   hqcooney 06:16:58PM sort HL7 Code 422";
	        
			assertEquals(expectedReport, reportString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			fail(); 
		} 
    } */
    
	/**
	 * Tests getDateReport() with generic entries
	 */
    @Test
    public void testGetDateReportWithEntries() {
        // Request the top 3 activities
    	try {
    		ReportManager reportTest = new ReportManager("input/testfile");
            // Test with a date that has multiple entries
            String report = reportTest.getDateReport("01/04/2016");

            // Expected output for entries on 01/04/2016
            String expectedReport = 
                "Activities recorded on 01/04/2016 [\n" +
                "   hqcooney, 01/04/2016 01:12:22AM, view, HL7 Code 422\n" +
                "   gphsu, 01/04/2016 12:44:52PM, sort, HL7 Code 422\n" +
                "]";
            
            assertEquals(expectedReport, report);
		} catch (FileNotFoundException e) {
			fail(); 
		} 
    }

    /**
     * Tests getDateReport() with no entries
     */
    @Test
    public void testGetDateReportNoEntries() {
        // Request the top 3 activities
    	try {
    		ReportManager reportTest = new ReportManager("input/testfile"); 
    		
            // Test with a date that has no entries
            String report = reportTest.getDateReport("02/02/2020");

            // Expected message for a date with no activities
            String expectedReport = "No activities were recorded on 02/02/2020";
            
            assertEquals(expectedReport, report);
		} catch (FileNotFoundException e) {
			fail(); 
		} 
    }

    /**
     * Tests getDateReport with invalid format
     */
    @Test
    public void testGetDateReportInvalidDateFormat() {
        // Request the top 3 activities
    	try {
    		ReportManager reportTest = new ReportManager("input/testfile"); 
    		
            // Test with an incorrectly formatted date
            String report = reportTest.getDateReport("1/4/2016");

            // Expected error message for invalid date format
            String expectedReport = "Please enter a valid date in the format MM/DD/YYYY";
            
            assertEquals(expectedReport, report);
            
            assertEquals(expectedReport, report);
		} catch (FileNotFoundException e) {
			fail(); 
		} 
    }
    
    /**
     * Gets date report with same
     */
    @Test
    public void testGetDateReportWithEntriesSame() {
        // Request the top 3 activities
    	try {
    		ReportManager reportTest = new ReportManager("input/emptyFile");
            // Test with a date that has multiple entries
            String report = reportTest.getDateReport("01/04/2016");

            // Expected output for entries on 01/04/2016
            String expectedReport = 
                "Activities recorded on 01/04/2016 [\n" +
                "   hqcooney, 01/04/2016 01:12:22AM, unmerge, HL7 Code 422\n" +
                "   gphsu, 01/04/2016 12:44:52PM, sort, HL7 Code 422\n" +
                "]";
            
            assertEquals(expectedReport, report);
		} catch (FileNotFoundException e) {
			fail(); 
		} 
    }
    
    /**
     * Gets date report with same
     */
    @Test
    public void testGetDateReportWithEntriesSameTime() {
        // Request the top 3 activities
    	try {
    		ReportManager reportTest = new ReportManager("input/emptyFile");
            // Test with a date that has multiple entries
            String report = reportTest.getDateReport("09/15/2017");

            // Expected output for entries on 01/04/2016
            String expectedReport = 
                "Activities recorded on 09/15/2017 [\n" +
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
