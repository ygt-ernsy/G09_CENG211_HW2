import java.util.ArrayList;

/**
 * Handles reading the CSV file and building the list of Application objects.
 * This implementation uses a "two-pass" reading strategy.
 */
public class ApplicationReader {

    /**
     * Reads the specified CSV file and returns a list of populated Application
     * objects.
     *
     * @param filename The relative path to the "ScholarshipApplications.csv" file.
     * @return An ArrayList of Application objects (Merit, Need, or Research).
     */
    public ArrayList<Application> readApplications(String filename) {
        ArrayList<Application> applicationsList = new ArrayList<>();
        FileIO fileIO = new FileIO(filename);

        // --- PASS 1: Find 'A' rows and create objects ---
        if (!fileIO.open()) {
            return applicationsList; // Return empty list if file can't be opened
        }

        String line;
        while ((line = fileIO.getNextLine()) != null) {
            String[] tokens = tokenizeLine(line);

            if (tokens.length > 0 && tokens[0].equals("A")) {
                String applicantID = tokens[1];
                String name = tokens[2];
                double gpa = Double.parseDouble(tokens[3]);
                double income = Double.parseDouble(tokens[4]);

                if (applicantID.startsWith("11")) {
                    applicationsList.add(new MeritApplication(applicantID, name, gpa, income));
                } else if (applicantID.startsWith("22")) {
                    applicationsList.add(new NeedApplication(applicantID, name, gpa, income));
                } else if (applicantID.startsWith("33")) {
                    applicationsList.add(new ResearchApplication(applicantID, name, gpa, income));
                }
            }
        }

        // --- PASS 2: Read T, I, D, P rows and populate objects ---
        fileIO.reset(); // Reset to beginning of file

        while ((line = fileIO.getNextLine()) != null) {
            String[] tokens = tokenizeLine(line);

            if (tokens.length == 0) {
                continue;
            }

            String prefix = tokens[0];

            if (prefix.equals("A")) {
                continue; // Skip 'A' rows
            }

            String applicantID = tokens[1];
            Application app = findAppById(applicationsList, applicantID);

            if (app == null) {
                continue;
            }

            switch (prefix) {
                case "T":
                    app.setTranscriptStatus(tokens[2].charAt(0));
                    break;
                case "I":
                    app.setFamilyIncome(Double.parseDouble(tokens[2]));
                    app.setDependants(Integer.parseInt(tokens[3]));
                    break;
                case "D":
                    String docType = tokens[2];
                    int duration = Integer.parseInt(tokens[3]);
                    app.addDocument(new Document(docType, duration));
                    break;
                case "P":
                    String title = tokens[2];
                    double impactFactor = Double.parseDouble(tokens[3]);
                    app.addPublication(new Publication(title, impactFactor));
                    break;
            }
        }

        fileIO.close();
        return applicationsList;
    }

    /**
     * Helper method to tokenize a CSV line using EnhancedStringTokenizer.
     */
    private String[] tokenizeLine(String line) {
        EnhancedStringTokenizer tokenizer = new EnhancedStringTokenizer(line, ",");

        while (tokenizer.hasMoreTokens()) {
            tokenizer.nextToken();
        }

        return tokenizer.tokensSoFar();
    }

    /**
     * A helper method to find an Application object in a list by its ID.
     */
    private Application findAppById(ArrayList<Application> appList, String applicantID) {
        for (Application app : appList) {
            if (app.getId().equals(applicantID)) {
                return app;
            }
        }
        return null;
    }
}
