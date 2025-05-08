package edu.ncsu.csc316.activity.ui;

import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc316.activity.manager.ReportManager;

/**
 * UserActivityLogManagerUI class
 */
public class UserActivityLogManagerUI {

    /**
     * The main method that starts the user interface.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        ReportManager reportManager = null;

        // Prompt user to load the input file
        while (reportManager == null) {
            System.out.print("Enter the path to the log file: ");
            String filePath = scanner.nextLine();
            try {
                reportManager = new ReportManager(filePath);
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please enter a valid file path.");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. View Top User Activities Report");
            System.out.println("2. View Log Entries by Date");
            System.out.println("3. View Log Entries by Hour");
            System.out.println("4. Quit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (choice) {
                case 1:
                    viewTopActivitiesReport(scanner, reportManager);
                    break;
                case 2:
                    viewEntriesByDate(scanner, reportManager);
                    break;
                case 3:
                    viewEntriesByHour(scanner, reportManager);
                    break;
                case 4:
                    running = false;
                    System.out.println("Quitting UserActivityLogManager...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        }
        scanner.close();
    }

    /**
     * Handles displaying the top user activities report.
     * @param scanner Scanner object for user input
     * @param reportManager ReportManager instance
     */
    private static void viewTopActivitiesReport(Scanner scanner, ReportManager reportManager) {
        System.out.print("Enter the number of top activities to view: ");
        int number = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (number <= 0) {
            System.out.println("Please enter a number > 0");
            return;
        }

        //String report = reportManager.getTopUserActivitiesReport(number);
        //System.out.println(report);
        
        /*
capture START time first
long start = ...

// Now construct the manager and call the appropriate method
ReportManager manager = new ReportManager(...);
manager.getTopUserActivitiesReport(10000000);

// capture END time now
long end = ...

// calculate ELASPED TIME
long duration = stop - start;
         * 
         */
        
        //int num = (int) Math.pow(2, 20); 
        
        long start = System.currentTimeMillis(); 
        
        String report = reportManager.getTopUserActivitiesReport(10000000); 
        System.out.println(report); 
        
        long stop = System.currentTimeMillis(); 
        
        long duration = stop - start;  
        
        System.out.println("time: " + duration); 
       
    }

    /**
     * Handles displaying log entries by a specific date.
     * @param scanner Scanner object for user input
     * @param reportManager ReportManager instance
     */
    private static void viewEntriesByDate(Scanner scanner, ReportManager reportManager) {
        System.out.print("Enter a date (MM/DD/YYYY): ");
        String date = scanner.nextLine();

        String report = reportManager.getDateReport(date);
        System.out.println(report);
    }

    /**
     * Handles displaying log entries by a specific hour.
     * @param scanner Scanner object for user input
     * @param reportManager ReportManager instance
     */
    private static void viewEntriesByHour(Scanner scanner, ReportManager reportManager) {
        System.out.print("Enter an hour (0-23): ");
        int hour = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (hour < 0 || hour > 23) {
            System.out.println("Please enter a valid hour between 0 (12AM) and 23 (11PM)");
            return;
        }

        String report = reportManager.getHourReport(hour);
        System.out.println(report);
    }
}
