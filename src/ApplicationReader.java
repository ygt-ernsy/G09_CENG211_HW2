/**
 * ApplicationReader
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Handles reading the CSV file and building the list of Application objects.
 * This implementation uses a "two-pass" reading strategy as per the plan
 * to first create all Application objects, and then populate them.
 */
public class ApplicationReader {

    /**
     * Reads the specified CSV file and returns a list of populated Application objects.
     *
     * @param filename The relative path to the "ScholarshipApplications.csv" file.
     * @return An ArrayList of Application objects (Merit, Need, or Research).
     */
    public ArrayList<Application> readApplications(String filename) {

        // 1. Create the list to hold all applications.
        ArrayList<Application> applicationsList = new ArrayList<>();

        // --- PASS 1: Find 'A' rows and create objects ---
        // This pass reads the file to find all 'A' (Applicant Info) rows.
        // It creates the correct object type (Merit, Need, Research)
        // and adds the base object to our list.
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            String[] tokens;
            StringTokenizer stringTokenizer;
            while ((line = bufferedReader.readLine()) != null) {
                stringTokenizer = new StringTokenizer(line,",");
                tokens = new String[stringTokenizer.countTokens()];
                for (int i = 0; i < tokens.length; i++) {
                    tokens[i] = stringTokenizer.nextToken();
                }
                // Only process Applicant Info ('A') rows in this pass 
                if (tokens[0].equals("A")) {
                    String applicantID = tokens[1];
                    String name = tokens[2];
                    double gpa = Double.parseDouble(tokens[3]);
                    double income = Double.parseDouble(tokens[4]);

                    // Instantiate the correct subclass based on the ID prefix
                    if (applicantID.startsWith("11")) { // Merit-based 
                        applicationsList.add(new MeritApplication(applicantID, name, gpa, income));
                    } else if (applicantID.startsWith("22")) { // Need-based 
                        applicationsList.add(new NeedApplication(applicantID, name, gpa, income));
                    } else if (applicantID.startsWith("33")) { // Research-grant 
                        applicationsList.add(new ResearchApplication(applicantID, name, gpa, income));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error during Pass 1 reading file: " + e.getMessage());
        }

        // 3. The file is auto-closed here by try-with-resources.

        // --- PASS 2: Read T, I, D, P rows and populate objects ---
        // This pass re-reads the entire file. This time, it skips 'A' rows
        // and uses the applicantID to find the existing object from Pass 1,
        // then adds the new data (Transcript, Family, Document, Publication) to it.
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            String[] tokens;
            StringTokenizer stringTokenizer;
            while ((line = bufferedReader.readLine()) != null) {
                stringTokenizer = new StringTokenizer(line,",");
                tokens = new String[stringTokenizer.countTokens()];
                for (int i = 0; i < tokens.length; i++) {
                    tokens[i] = stringTokenizer.nextToken();
                }
                String prefix=tokens[0]; 

                // Skip 'A' rows, we already handled them
                if (prefix.equals("A")) {
                    continue;
                }

                // 5. Find the correct application object from our list
                String applicantID = tokens[1];
                Application app = findAppById(applicationsList, applicantID);

                // If app is not found (e.g., bad data), skip this row
                if (app == null) {
                    continue;
                }

                // 6. Add the data to the existing object based on the row prefix
                switch (prefix) {
                    case "T": // Transcript
                        app.setTranscriptStatus(tokens[2].charAt(0));
                        break;
                    case "I": // Family Info
                        // Note: The 'income' from 'A' row is personal, 'I' row is family.
                        // We must set both as they are used by different rules.
                        app.setFamilyIncome(Double.parseDouble(tokens[2]));
                        app.setDependants(Integer.parseInt(tokens[3]));
                        break;
                    case "D": // Document
                        String docType = tokens[2];
                        int duration = Integer.parseInt(tokens[3]);
                        app.addDocument(new Document(docType, duration));
                        break;
                    case "P": // Publication
                        String title = tokens[2];
                        double impactFactor = Double.parseDouble(tokens[3]);
                        app.addPublication(new Publication(title, impactFactor));
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error during Pass 2 reading file: " + e.getMessage());
        }

        // 7. Close file (auto-closed) and return the completed list.
        return applicationsList;
    }

    /**
     * A helper method to find an Application object in a list by its ID.
     *
     * @param appList The list of applications to search.
     * @param applicantID The ID to search for.
     * @return The matching Application object, or null if not found.
     */
    private Application findAppById(ArrayList<Application> appList, String applicantID) {
        // 1. Loop through the list
        for (Application app : appList) {
            // 2. Return the matching object
            if (app.getId().equals(applicantID)) {
                return app;
            }
        }
        // Not found
        return null;
    }
}
