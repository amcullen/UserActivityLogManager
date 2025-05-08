package edu.ncsu.csc316.activity.manager;

import java.io.FileNotFoundException;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

import edu.ncsu.csc316.activity.data.LogEntry;
import edu.ncsu.csc316.activity.dsa.Algorithm;
import edu.ncsu.csc316.activity.dsa.DSAFactory;
import edu.ncsu.csc316.activity.dsa.DataStructure;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.sorter.Sorter;

/**
 * ReportManager class, manages activity reports 
 */
public class ReportManager {

    // You may add or remove fields, as needed
	/** activityLogManager field */ 
    private UserActivityLogManager activityLogManager;
    /** date format */ 
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    /** time format */ 
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm:ssa");
    //private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ssa");
    /** Indent for StringBuilder */ 
    private static final String INDENT = "   "; 
    
    /**
     * Constructs ReportManager with file path parameter
     * @param pathToFile path to file
     * @throws FileNotFoundException exception if file path is empty
     */
    public ReportManager(String pathToFile) throws FileNotFoundException { 
        this(pathToFile, DataStructure.SEARCHTABLE);
    }

    /**
     * Constructs ReportManager with file path parameter and mapType
     * @param pathToFile file path
     * @param mapType map type 
     * @throws FileNotFoundException exception if file path is empty
     */
    public ReportManager(String pathToFile, DataStructure mapType) throws FileNotFoundException {
    	DSAFactory.setListType(DataStructure.SINGLYLINKEDLIST);    
        DSAFactory.setComparisonSorterType(Algorithm.QUICKSORT);      
        DSAFactory.setNonComparisonSorterType(Algorithm.COUNTING_SORT); 
        DSAFactory.setMapType(mapType);  
        activityLogManager = new UserActivityLogManager(pathToFile);   
    } 

    /**
     * Gets report of top activities 
     * @param number number of activities to report 
     * @return String with top activities information
     */
    public String getTopUserActivitiesReport(int number) {
        // Use DSAFactory for the List type
        List<String> topActivities = activityLogManager.getTopActivities(number); 
        
        
        if (topActivities.isEmpty()) { 
            return "Please enter a number > 0";
        }
        
        // Initialize the report with StringBuilder
        StringBuilder report = new StringBuilder("Top User Activities Report [\n");
        
        // Iterate over the top activities and add each to the report
        for (String activity : topActivities) {
            report.append(INDENT).append(activity).append("\n");
        }
         
        report.append("]"); 
        return report.toString(); 
    }

    /**
     * Gets report of activities by date
     * @param date specified date
     * @return String with activities on that date
     */
    public String getDateReport(String date) {
        // Step 1: Validate date format
        try {
            LocalDate.parse(date, dateFormat);
        } catch (DateTimeParseException e) {
            return "Please enter a valid date in the format MM/DD/YYYY";
        }

        // Use DSAFactory for the Map type
        Map<String, List<LogEntry>> entriesByDate = activityLogManager.getEntriesByDate();
        List<LogEntry> entries = entriesByDate.get(date);
 

        if (entries == null || entries.isEmpty()) {
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("No activities were recorded on ")
                          .append(date);
            return messageBuilder.toString();
        }
        
        LogEntry[] entriesArray = new LogEntry[entries.size()];
        
        for(int i = 0; i < entries.size(); i++) {
        	entriesArray[i] = entries.get(i); 
        }
        
        
        Sorter<LogEntry> sorter = DSAFactory.getComparisonSorter(new LogEntryComparator());
        
        sorter.sort(entriesArray); 

        StringBuilder report = new StringBuilder("Activities recorded on " + date + " [\n");
        
        for(int i = 0; i < entries.size(); i++) {
            report.append(INDENT)
            .append(entriesArray[i].getUsername()).append(", ")
            .append(date).append(" ")
            .append(entriesArray[i].getTimestamp().format(timeFormat)).append(", ")
            .append(entriesArray[i].getAction()).append(", ")
            .append(entriesArray[i].getResource()).append("\n"); 
        }
        report.append("]"); 
        
        return report.toString(); 

    }
    
    /**
     * Comparator for sorting
     */
    private class LogEntryComparator implements Comparator<LogEntry> {

    	@Override
        public int compare(LogEntry entry1, LogEntry entry2) {
            // First, compare by timestamp in ascending order
            int timeComparison = entry1.getTimestamp().compareTo(entry2.getTimestamp());
            //System.out.println(entry1.getTimestamp()); 
            
            //System.out.println(timeComparison); 
            
            /*
            int year1 = entry1.getTimestamp().getYear();
            int month1 = entry1.getTimestamp().getMonthValue(); 
            int day1 = entry1.getTimestamp().getDayOfYear(); 
            int one =  */
            
            // If timestamps are equal, compare by action in alphabetical order
            if (timeComparison == 0) {
            	StringBuilder builder = new StringBuilder();

            	builder.append(entry1.getAction())
            	       .append(" ")
            	       .append(entry1.getResource());
            	String one = builder.toString();

            	// Reset the builder to reuse it for the second string
            	builder.setLength(0);

            	builder.append(entry2.getAction())
            	       .append(" ")
            	       .append(entry2.getResource());
            	String two = builder.toString();

            	return one.compareTo(two); 
                //return entry1.getAction().compareTo(entry2.getAction()); 
            }

            return timeComparison;  
        }
    }

    /**
     * Gets report of activities by hour
     * @param hour specified hour
     * @return String of activities during given hour
     */
    public String getHourReport(int hour) {
    	
    	if(hour < 0 || hour > 23) {
    		return "Please enter a valid hour between 0 (12AM) and 23 (11PM)"; 
    	}
    	
    	Map<Integer, List<LogEntry>> entriesByHour = activityLogManager.getEntriesByHour();
    	List<LogEntry> entries = entriesByHour.get(hour);
    	
        if (entries == null || entries.isEmpty()) {
            return "No activities were recorded during hour " + hour;
        } 
        
        
        ////// 
        
        //String date1 = entries.get(0).getTimeStamp();   //.format(dateFormat);
        

        //////
        
        LogEntry[] entriesArray = new LogEntry[entries.size()];
        
        for(int i = 0; i < entries.size(); i++) {
        	entriesArray[i] = entries.get(i); 
        }
        
        
        Sorter<LogEntry> sorter = DSAFactory.getComparisonSorter(new SameComparator());
        
        sorter.sort(entriesArray); 
        
        /*LogEntry[] sortedArray = new LogEntry[entries.size()];
        
        
        for(int i = 0; i < entriesArray.length; i++) {
        	sortedArray[i] = entriesArray[entriesArray.length - i - 1]; 
        }*/
        
        System.out.println("????????"); 
        
        for(int i = 0; i < entriesArray.length; i++) {
        	System.out.println(entriesArray[i]); 
        }
        
        System.out.println("?????????"); 
        
        /*
        System.out.println("////////"); 
       
        for(int i = 0; i < sortedArray.length; i++) {
        	System.out.println(sortedArray[i]); 
        }
        
        System.out.println("////////"); */
    	
        StringBuilder report = new StringBuilder("Activities recorded during hour " + hour + " [\n");
        
        /*
        for(int i = 0; i < entries.size(); i++) {
            report.append(INDENT)
            .append(entries.get(i).getUsername()).append(", ")
            .append(entries.get(i).getTimestamp().format(dateFormat)).append(" ")
            .append(entries.get(i).getTimestamp().format(timeFormat)).append(", ")
            .append(entries.get(i).getAction()).append(", ")
            .append(entries.get(i).getResource()).append("\n");
        }
        report.append("]"); 
        //.append(entries.get(i).getResource()).append("\n");
        return report.toString(); 
        */
        
        for(int i = 0; i < entries.size(); i++) {
            report.append(INDENT)
            .append(entriesArray[i].getUsername()).append(", ")
            .append(entriesArray[i].getTimestamp().format(dateFormat)).append(" ")
            .append(entriesArray[i].getTimestamp().format(timeFormat)).append(", ")
            .append(entriesArray[i].getAction()).append(", ")
            .append(entriesArray[i].getResource()).append("\n");
        }
        report.append("]"); 
        //.append(entries.get(i).getResource()).append("\n");
        return report.toString(); 
    }
    
    private class SameComparator implements Comparator<LogEntry> {

		@Override
		public int compare(LogEntry entry1, LogEntry entry2) {
			
			System.out.println(entry1); 
			System.out.println(entry2);
			int timeComparison = entry1.getTimestamp().compareTo(entry2.getTimestamp());
			System.out.println(timeComparison); 
			System.out.println(); 
			
			if(timeComparison == 0) { 
            	StringBuilder builder = new StringBuilder();

            	builder.append(entry1.getAction())
            	       .append(" ")
            	       .append(entry1.getResource());
            	String one = builder.toString();

            	// Reset the builder to reuse it for the second string
            	builder.setLength(0);

            	builder.append(entry2.getAction())
            	       .append(" ")
            	       .append(entry2.getResource());
            	String two = builder.toString();
            	
            	//System.out.println(); 
            	System.out.println(one); 
            	System.out.println(two); 
            	System.out.println(-1 * one.compareTo(two)); 
            	System.out.println(); 

            	return one.compareTo(two); 
                //return entry1.getAction().compareTo(entry2.getAction());
			}
			
			/*
            if (timeComparison == 0) {
            	StringBuilder builder = new StringBuilder();

            	builder.append(entry1.getAction())
            	       .append(" ")
            	       .append(entry1.getResource());
            	String one = builder.toString();

            	// Reset the builder to reuse it for the second string
            	builder.setLength(0);

            	builder.append(entry2.getAction())
            	       .append(" ")
            	       .append(entry2.getResource());
            	String two = builder.toString();

            	return one.compareTo(two) * -1; 
                //return entry1.getAction().compareTo(entry2.getAction()); 
            } */

            return timeComparison;   
			
		}
    	
    }
    
    // Add other private helper methods, inner classes, etc. as needed  
    
    /*
    private class HourComparator implements Comparator<LogEntry> {

    	@Override
        public int compare(LogEntry entry1, LogEntry entry2) {
            // First, compare by timestamp in ascending order
            //int timeComparison = entry1.getTimestamp().compareTo(entry2.getTimestamp());
            int time1 = entry1.getTimestamp().getMinute(); 
            int time2 = entry2.getTimestamp().getMinute(); 
            
            if(time1 == time2) {
            	int sec1 = entry1.getTimestamp().getSecond(); 
            	int sec2 = entry2.getTimestamp().getSecond(); 
            	
            	if(sec1 == sec2) {
            		String act1 = entry1.getAction() + " " + entry1.getResource(); 
            		String act2 = entry2.getAction() + " " + entry2.getResource(); 
            		
            		//System.out.println(act1); 
            		//System.out.println(act2); 
            		//System.out.println(-1 * act1.compareTo(act2)); 
            		
            		return -1 * act1.compareTo(act2); 
            	}
            	
            	return Integer.compare(sec1, sec2); 
            }
            
            return Integer.compare(time1, time2); 
           
        }
    }*/
    
    
}
