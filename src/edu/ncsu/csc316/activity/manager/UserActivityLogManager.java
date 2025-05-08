package edu.ncsu.csc316.activity.manager;

import java.io.FileNotFoundException;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Iterator;

import edu.ncsu.csc316.activity.data.LogEntry;
import edu.ncsu.csc316.activity.dsa.Algorithm;
import edu.ncsu.csc316.activity.dsa.DSAFactory;
import edu.ncsu.csc316.activity.dsa.DataStructure;
import edu.ncsu.csc316.activity.io.LogEntryReader;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
import edu.ncsu.csc316.dsa.sorter.Sorter; 

/** 
 * UserActivityLogManager manages user activities
 */
public class UserActivityLogManager {

    // You may add or remove fields, as needed
	/** logList for activities */ 
    private List<LogEntry> logList;
    /** date format */ 
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    //private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ssa");

    /**
     * UserActivityLogManager constructor with file path
     * @param pathToFile file path
     * @throws FileNotFoundException exception for empty file path
     */
    public UserActivityLogManager(String pathToFile) throws FileNotFoundException { 
        this(pathToFile, DataStructure.SEARCHTABLE);
    }
    
    /**
     * UserActivityLogManager constructor with file path and map type
     * @param pathToFile file path
     * @param mapType map type  
     * @throws FileNotFoundException exception for empty file path
     */
    public UserActivityLogManager(String pathToFile, DataStructure mapType) throws FileNotFoundException {
        // Set the list, map, and sorter types in DSAFactory
        DSAFactory.setListType(DataStructure.SINGLYLINKEDLIST);       // Set the list type explicitly
        DSAFactory.setComparisonSorterType(Algorithm.QUICKSORT);      // Set comparison-based sorter
        DSAFactory.setNonComparisonSorterType(Algorithm.COUNTING_SORT); // Set non-comparison sorter
        DSAFactory.setMapType(mapType);                               // Set the map type based on input
 
        // Initialize the list of log entries 
        this.logList = DSAFactory.getIndexedList(); 

        // Load entries from the specified file
        this.logList = LogEntryReader.loadLogEntries(pathToFile);
    }

    // Returns a List of the most frequently performed N user activities in the
    // input log entry file. 
    // Returns an empty List if the log contains no entries.
    /**
     * Gets Top activities based off frequency 
     * @param number specified number of activities to return 
     * @return List of top activities
     */
	public List<String> getTopActivities(int number) { 
        if(logList.isEmpty()) { 
        	return null; 
        }
        
        Map<String, Integer> actionCounts = DSAFactory.getMap(null);
          
/*
        
        Sorter<LogEntry> sort = DSAFactory.getComparisonSorter(new LogEntryComparator());
        
        LogEntry[] logArray = new LogEntry[logList.size()]; 
        
        int idx = 0; 
        
        for(LogEntry entry: logList) {
        	logArray[idx] = entry; 
        	idx++;  
        }
        
        System.out.println("SORTING STARTS"); 
        
        sort.sort(logArray);
        
        System.out.println("SORTING ENDS"); 
        
        List<LogEntry> inOrderLog = DSAFactory.getIndexedList();  
        
        for(int i = 0; i < logArray.length; i++) { 
        	inOrderLog.addLast(logArray[logArray.length - 1 - i]);   
        }  
        
        for(int i = 0; i < inOrderLog.size(); i++) {
        	//System.out.println(inOrderLog.get(i).getAction()); 
        }
        
        System.out.println(); 
        
*/
        
        for (LogEntry entry : logList) {
        	
        	StringBuilder activityBuilder = new StringBuilder();
        	activityBuilder.append(entry.getAction())
        	               .append(" ")
        	               .append(entry.getResource());
        	String activity = activityBuilder.toString();
            
            Integer counts = actionCounts.get(activity);

            
            if (counts == null) {
                actionCounts.put(activity, 1);
            } else {
                actionCounts.put(activity, counts + 1); 
            }
             
            /*
            int frequency;  
            if(actionCounts.get(activity) == null) {
            	frequency = 0; 
            } else {
            	frequency = (int)actionCounts.get(activity);
            }
           
            // If count is null, initialize it to 1; otherwise, increment it
            if (frequency == 0) {
                actionCounts.put(activity, 1);
            } else {
                actionCounts.put(activity, frequency + 1);
            } 
            */
        }     
        
        int index = 0;
        int size = actionCounts.size();
        
        @SuppressWarnings("unchecked")
        Entry<String, Integer>[] activitiesArray = new Entry[size];
        
        for (Map.Entry<String, Integer> entry : actionCounts.entrySet()) {
            activitiesArray[index] = entry;  // Specify index for each add
            index++; 
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
		Sorter<Map.Entry<String, Integer>> sorter = DSAFactory.getComparisonSorter(new EntryComparator());
        
        sorter.sort(activitiesArray);  
        
        /*List<Map.Entry<String, Integer>> sorted = DSAFactory.getIndexedList();
        
        for (int i = 0; i < activitiesArray.length; i++) {
            sorted.add(i, activitiesArray[i]); // Adding at specific index i
        }*/
        
        ///// Sorter<LogEntry> sorter = DSAFactory.getComparisonSorter(new LogEntryComparator());
        
        List<String> top = DSAFactory.getIndexedList();  
        
        int min = Math.min(activitiesArray.length, number); 
 
////////////////////////////////////////////
        /*
        for(int i = 0; i < min; i++) {
            Entry<String, Integer> entry = activitiesArray[i];
            StringBuilder entryBuilder = new StringBuilder();
            entryBuilder.append(entry.getValue())
                        .append(": ")
                        .append(entry.getKey());
            top.addLast(entryBuilder.toString()); 
        } */
        
        int i = 0; 
        for(Entry<String, Integer> entry : activitiesArray) {
        	if(i < min) {
                StringBuilder entryBuilder = new StringBuilder();
                entryBuilder.append(entry.getValue())
                            .append(": ")
                            .append(entry.getKey());
                top.addLast(entryBuilder.toString());
                i++; 
        	}
        } 
//////////////////////////////////////////////
        
        return top;   
    }
	
	/**
	 * Compares entries for getTopActivities
	 * @param <K> Key 
	 * @param <V> Value
	 */
	private class EntryComparator<K, V> implements Comparator<Entry<K, V>> { 
		
	       @Override
	       public int compare(Entry<K, V> o1, Entry<K, V> o2) {
	    	   
	           int value = Integer.compare((Integer) o2.getValue(), (Integer) o1.getValue());
	           
	       	if (value != 0) {
	       		
	               return value;
	           } else {
	        	   
	               return String.CASE_INSENSITIVE_ORDER.compare((String) o1.getKey(), (String) o2.getKey());
	           }
	       }
	} 
    
    // Returns a Map that represents the List of log entries performed on each
    // unique date. For the Map, the String key represents the date in the format
    // MM/DD/YYYY. 
    // Returns an empty Map if the log contains no entries.
	/**
	 * Gets entries by date
	 * @return map of entries by date
	 */
    public Map<String, List<LogEntry>> getEntriesByDate() {
        // Initialize an empty map to store log entries grouped by date 
        Map<String, List<LogEntry>> entriesByDate = DSAFactory.getMap(null);

        // Iterate through each log entry in logList
        for (LogEntry entry : logList) { 
        	
            // Format the date portion of the timestamp to "MM/DD/YYYY"
            String date = entry.getTimestamp().format(dateFormat);

        /*  if (entriesByDate.get(date) == null) {
                // If not, create a new list for this date and add it to the map
                entriesByDate.put(date, DSAFactory.getIndexedList());
            }

            // Add the current log entry to the list for this date
            List<LogEntry> dateEntries = entriesByDate.get(date);
            dateEntries.addFirst(entry); */
            
            if (entriesByDate.get(date) == null) {
                // If not, create a new list for this date and add it to the map
                entriesByDate.put(date, DSAFactory.getIndexedList());
                List<LogEntry> dateEntries = entriesByDate.get(date);
                dateEntries.addFirst(entry);
            } else {
            	
                List<LogEntry> dateEntries = entriesByDate.get(date);
                dateEntries.addLast(entry);
                
                Sorter<LogEntry> sorter = DSAFactory.getComparisonSorter(new LogEntryComparator());
                
                // Create a new array with the size of dateEntries
                LogEntry[] entriesArray = new LogEntry[dateEntries.size()];

                @SuppressWarnings("rawtypes")
				Iterator iterator = dateEntries.iterator(); 
                // Manually copy elements from dateEntries to entriesArray
                for (int i = 0; i < dateEntries.size(); i++) {
                    entriesArray[i] = (LogEntry) iterator.next(); 
                }
                
                sorter.sort(entriesArray);
                
             /*   for(int i = 0; i < entriesArray.length; i++) {
                	System.out.println(entriesArray[i]); 
                }
                
                System.out.println();  */
                
                List<LogEntry> sortedEntries = DSAFactory.getIndexedList(); 
                
                for(int i = 0; i < entriesArray.length; i++) {
                	sortedEntries.addLast(entriesArray[i]);
                }
                
                entriesByDate.put(date, sortedEntries); 
            }

        }   

        return entriesByDate;     
    }
    
    private class LogEntryComparator implements Comparator<LogEntry> {
        @Override
        public int compare(LogEntry entry1, LogEntry entry2) {
        	
        	/*
        	String one = entry1.getAction() + " " + entry1.getResource(); 
        	
        	String two = entry2.getAction() + " " + entry2.getResource(); 
        	*/
        	
        	StringBuilder oneBuilder = new StringBuilder();
        	oneBuilder.append(entry1.getAction())
        	          .append(" ")
        	          .append(entry1.getResource());
        	String one = oneBuilder.toString();

        	StringBuilder twoBuilder = new StringBuilder();
        	twoBuilder.append(entry2.getAction())
        	          .append(" ")
        	          .append(entry2.getResource());
        	String two = twoBuilder.toString();
        	
            return one.compareTo(two) * -1;
        }
    }

    // Returns a Map that represents the List of log entries performed during each
    // hour of the day. For the Map, the Integer key represents the hour of the day
    // (from 0-23, where 0=12AM-1AM; 1 = 1AM-2AM; etc.). 
    // Returns an empty Map if the log contains no entries.
    /**
     * Gets entries by hour they occurred 
     * @return Map of entries by hour
     */
    public Map<Integer, List<LogEntry>> getEntriesByHour() {
    	
        Map<Integer, List<LogEntry>> entriesByHour = DSAFactory.getMap(null);

        for (LogEntry entry : logList) {
        	//String time = entry.getTimestamp().format(timeFormat);
        	
        	int time = entry.getTimestamp().getHour(); 

            // Check if the hour already has a list in the map
          /*if (entriesByHour.get(time) == null) {
                entriesByHour.put(time, DSAFactory.getIndexedList());
            }

            List<LogEntry> hourEntries = entriesByHour.get(time);
            hourEntries.addFirst(entry); */
        	
        	if (entriesByHour.get(time) == null) {
                entriesByHour.put(time, DSAFactory.getIndexedList()); 
                List<LogEntry> hourEntries = entriesByHour.get(time);
                hourEntries.addFirst(entry);
            } else {
                List<LogEntry> hourEntries = entriesByHour.get(time);
                hourEntries.addLast(entry);
                
                Sorter<LogEntry> sorter = DSAFactory.getComparisonSorter(new LogEntryComparator()); 
                
                // Create a new array with the size of dateEntries
                LogEntry[] entriesArray = new LogEntry[hourEntries.size()];

                @SuppressWarnings("rawtypes")
				Iterator iterator = hourEntries.iterator(); 
                // Manually copy elements from dateEntries to entriesArray
                for (int i = 0; i < hourEntries.size(); i++) {
                    entriesArray[i] = (LogEntry) iterator.next(); 
                }
                
                sorter.sort(entriesArray);
                
                List<LogEntry> sortedEntries = DSAFactory.getIndexedList(); 
                
                for(int i = 0; i < entriesArray.length; i++) {
                	sortedEntries.addLast(entriesArray[i]);
                }
                
                entriesByHour.put(time, sortedEntries); //////
            }

        }

        return entriesByHour; 
    }
}
