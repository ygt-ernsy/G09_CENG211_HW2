import application.Application;
import io.ApplicationReader;
import reporting.Query;
import java.util.ArrayList;

/**
 * ScholarshipApplicationApp - Main entry point for the Scholarship Evaluation
 * System
 * 
 * This program reads scholarship applications from a CSV file, evaluates them
 * according to different criteria (Merit, Need, Research), and outputs the
 * results
 * sorted by applicant ID.
 */
public class ScholarshipApplicationApp {

    public static void main(String[] args) {
        // Define the relative path to the CSV file
        String csvFilePath = "resources/ScholarshipApplications.csv";

        try {
            // Step 1: Read all applications from the CSV file
            ApplicationReader reader = new ApplicationReader(csvFilePath);
            ArrayList<Application> applications = reader.readApplications();

            if (applications == null || applications.isEmpty()) {
                System.err.println("No applications found or error reading file.");
                return;
            }

            // Step 2: Evaluate each application
            for (Application app : applications) {
                if (app != null) {
                    app.evaluate();
                }
            }

            // Step 3: Create query object and print results
            Query query = new Query(applications);
            query.printAllApplications();

        } catch (Exception e) {
            System.err.println("An error occurred during execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
