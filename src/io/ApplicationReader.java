package io;

import application.Application;
import domain.Document;
import domain.Publication;
import java.util.ArrayList;

/**
 * ApplicationReader - Reads CSV file and creates Application objects
 * Uses multi-pass approach to handle unordered data
 */
public class ApplicationReader {
    private String filePath;
    private ArrayList<Application> applications;

    public ApplicationReader(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        this.filePath = filePath;
        this.applications = new ArrayList<>();
    }

    /**
     * Reads the CSV file and creates all applications
     * 
     * @return ArrayList of Application objects
     */
    public ArrayList<Application> readApplications() {
        applications.clear();

        // Pass 1: Read applicant info lines (A) and create Application objects
        FileIO fileIO = new FileIO(filePath);
        if (!fileIO.open()) {
            System.out.println("Failed to open file: " + filePath);
            return applications;
        }

        String line;
        while ((line = fileIO.getNextLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            EnhancedStringTokenizer tokenizer = new EnhancedStringTokenizer(line, ",");

            // Read all tokens
            while (tokenizer.hasMoreTokens()) {
                tokenizer.nextToken();
            }

            String[] tokens = tokenizer.tokensSoFar();

            if (tokens.length == 0) {
                continue;
            }

            // Trim all tokens
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
            }

            String prefix = tokens[0];

            // Process A (Applicant) lines to create applications
            if ("A".equals(prefix) && tokens.length >= 5) {
                try {
                    Application app = ApplicationFactory.createApplication(tokens);
                    if (app != null) {
                        applications.add(app);
                    }
                } catch (Exception e) {
                    System.out.println("Error creating application: " + e.getMessage());
                }
            }
        }

        fileIO.close();

        // Pass 2: Read remaining lines and populate application data
        fileIO.reset();

        while ((line = fileIO.getNextLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            EnhancedStringTokenizer tokenizer = new EnhancedStringTokenizer(line, ",");

            // Read all tokens
            while (tokenizer.hasMoreTokens()) {
                tokenizer.nextToken();
            }

            String[] tokens = tokenizer.tokensSoFar();

            if (tokens.length == 0) {
                continue;
            }

            // Trim all tokens
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
            }

            String prefix = tokens[0];

            if (tokens.length < 2) {
                continue;
            }

            String applicantId = tokens[1];
            Application app = findApplicationById(applicantId);

            if (app == null) {
                continue;
            }

            try {
                switch (prefix) {
                    case "T": // Transcript
                        if (tokens.length >= 3) {
                            char transcriptStatus = tokens[2].charAt(0);
                            app.getAcademics().setTranscriptStatus(transcriptStatus);
                        }
                        break;

                    case "I": // Family Info
                        if (tokens.length >= 4) {
                            double familyIncome = Double.parseDouble(tokens[2]);
                            int dependents = Integer.parseInt(tokens[3]);
                            app.getFinancials().setFamilyIncome(familyIncome);
                            app.getFinancials().setDependants(dependents);
                        }
                        break;

                    case "D": // Document
                        if (tokens.length >= 4) {
                            String documentType = tokens[2];
                            int durationInMonths = Integer.parseInt(tokens[3]);
                            Document doc = new Document(documentType, durationInMonths);
                            app.getAcademics().getDocuments().add(doc);
                        }
                        break;

                    case "P": // Publication
                        if (tokens.length >= 4) {
                            String title = tokens[2];
                            double impactFactor = Double.parseDouble(tokens[3]);
                            Publication pub = new Publication(title, impactFactor);
                            app.getAcademics().getPublications().add(pub);
                        }
                        break;

                    default:
                        // Skip A lines (already processed) and unknown prefixes
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Error parsing number in line: " + line);
            } catch (Exception e) {
                System.err.println("Error processing line: " + line + " - " + e.getMessage());
            }
        }

        fileIO.close();

        return applications;
    }

    /**
     * Finds an application by applicant ID
     * 
     * @param applicantId The ID to search for
     * @return Application object or null if not found
     */
    private Application findApplicationById(String applicantId) {
        if (applicantId == null || applications == null) {
            return null;
        }

        for (Application app : applications) {
            if (app != null && applicantId.equals(app.getId())) {
                return app;
            }
        }

        return null;
    }

    /**
     * Gets the list of applications
     * 
     * @return ArrayList of applications
     */
    public ArrayList<Application> getApplications() {
        return applications;
    }
}
