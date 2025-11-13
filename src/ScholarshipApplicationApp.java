import application.Application;
import io.ApplicationEvaluator;
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

        // Step 1: Read all applications from the CSV file
        ApplicationReader reader = new ApplicationReader(csvFilePath);
        ArrayList<Application> applications = reader.readApplications();

        // Step 2: Evaluate each application
        ApplicationEvaluator applicationEvaluator = new ApplicationEvaluator();
        applicationEvaluator.evaluateAll(applications);

        // Step 3: Create query object and print results
        Query query = new Query(applications);
        query.printAllApplications();
    }
}
