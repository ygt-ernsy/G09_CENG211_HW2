import java.util.ArrayList;

/**
 * ApplicationReader
 */
public class ApplicationReader {

    public ArrayList<Application> readApplications(String filename) {
        ArrayList<Application> applications = new ArrayList<>();
        FileIO fileIO = new FileIO(filename);

        if (!fileIO.open()) {
            return applications;
        }

        // Pass 1: Create application objects
        String line;
        while ((line = fileIO.getNextLine()) != null) {
            String[] tokens = tokenizeLine(line);
            if (tokens.length > 0 && tokens[0].equals("A")) {
                Application app = createApplication(tokens);
                if (app != null) {
                    applications.add(app);
                }
            }
        }

        // Pass 2: Add documents, publications, and other info
        fileIO.reset();
        while ((line = fileIO.getNextLine()) != null) {
            String[] tokens = tokenizeLine(line);
            if (tokens.length > 0 && !tokens[0].equals("A")) {
                processDataRow(applications, tokens);
            }
        }

        fileIO.close();
        return applications;
    }

    private Application createApplication(String[] tokens) {
        String id = tokens[1];
        String name = tokens[2];
        double gpa = Double.parseDouble(tokens[3]);
        double income = Double.parseDouble(tokens[4]);

        if (id.startsWith("11"))
            return new MeritApplication(id, name, gpa, income);
        if (id.startsWith("22"))
            return new NeedApplication(id, name, gpa, income);
        if (id.startsWith("33"))
            return new ResearchApplication(id, name, gpa, income);

        return null;
    }

    private void processDataRow(ArrayList<Application> applications, String[] tokens) {
        String prefix = tokens[0];
        String id = tokens[1];
        Application app = findApplicationById(applications, id);

        if (app == null)
            return;

        switch (prefix) {
            case "T":
                app.setTranscriptStatus(tokens[2].charAt(0));
                break;
            case "I":
                app.setFamilyIncome(Double.parseDouble(tokens[2]));
                app.setDependants(Integer.parseInt(tokens[3]));
                break;
            case "D":
                app.addDocument(new Document(tokens[2], Integer.parseInt(tokens[3])));
                break;
            case "P":
                app.addPublication(new Publication(tokens[2], Double.parseDouble(tokens[3])));
                break;
        }
    }

    private Application findApplicationById(ArrayList<Application> applications, String id) {
        for (Application app : applications) {
            if (app.getId().equals(id)) {
                return app;
            }
        }
        return null;
    }

    private String[] tokenizeLine(String line) {
        EnhancedStringTokenizer tokenizer = new EnhancedStringTokenizer(line, ",");
        while (tokenizer.hasMoreTokens()) {
            tokenizer.nextToken();
        }
        return tokenizer.tokensSoFar();
    }
}
