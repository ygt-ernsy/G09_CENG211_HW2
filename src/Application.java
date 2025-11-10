import java.util.ArrayList;

/**
 * Application
 */
public abstract class Application implements Comparable<Application> {
    private String id;
    private String name;
    private double gpa;
    private double income;
    private double familyIncome;
    private char transcriptStatus;
    private ArrayList<Document> documents;
    private ArrayList<Publication> publications;
    private String evaluationsStatus;
    private int dependants;
    private String scholarshipType;
    private String scholarshipDuration;
    private String rejectionReason;

    public Application(String id, String name, double gpa, double income) {
        // Validate required parameters
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.income = income;
        this.documents = new ArrayList<>();
        this.publications = new ArrayList<>();
        this.transcriptStatus = 'N'; // Default value
        this.dependants = 0; // Default value
        // None initialy
        this.scholarshipType = "None";
    }

    public Application(Application application) {
        // Null check for the application parameter
        if (application == null) {
            throw new IllegalArgumentException("Application cannot be null");
        }

        this.id = application.getId();
        this.name = application.getName();
        this.gpa = application.getGpa();
        this.income = application.getIncome();
        this.documents = new ArrayList<>();
        this.publications = new ArrayList<>();
        this.transcriptStatus = 'N'; // Default value
        this.dependants = 0; // Default value
        // None initialy
        this.scholarshipType = "None";
    }

    public boolean runGeneralChecks() {
        boolean hasENR = hasDocument("ENR");

        if (!hasENR) {
            setRejectionReason("Missing Enrollment Certificate");
            setEvaluationsStatus("Rejected");
            return false;
        }

        if (transcriptStatus != 'Y') {
            setRejectionReason("Missing Transcript");
            setEvaluationsStatus("Rejected");
            return false;
        }
        if (gpa < 2.5) {
            setRejectionReason("GPA must be at least 2.5");
            setEvaluationsStatus("Rejected");
            return false;
        }

        return true;
    }

    public abstract void evaluate();

    public char getTranscriptStatus() {
        return transcriptStatus;
    }

    public ArrayList<Publication> getPublications() {
        // Return defensive copy or at least ensure not null
        return publications != null ? publications : new ArrayList<>();
    }

    public ArrayList<Document> getDocuments() {
        // Return defensive copy or at least ensure not null
        return documents != null ? documents : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getIncome() {
        return income;
    }

    public String getId() {
        return id;
    }

    public double getGpa() {
        return gpa;
    }

    public String getEvaluationsStatus() {
        return evaluationsStatus;
    }

    public int getDependants() {
        return dependants;
    }

    public double getFamilyIncome() {
        return familyIncome;
    }

    public String getScholarshipType() {
        return scholarshipType;
    }

    public String getScholarshipDuration() {
        return scholarshipDuration;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public int getIdAsInteger() {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalStateException("ID is null or empty");
        }
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("ID cannot be converted to integer: " + id);
        }
    }

    public abstract String getProgramType();

    public void setEvaluationsStatus(String evaluationsStatus) {
        this.evaluationsStatus = evaluationsStatus;
    }

    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.gpa = gpa;
    }

    public void setId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        this.id = id;
    }

    public void setTranscriptStatus(char transcriptStatus) {
        this.transcriptStatus = transcriptStatus;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setIncome(double income) {
        if (income < 0) {
            throw new IllegalArgumentException("Income cannot be negative");
        }
        this.income = income;
    }

    public void setFamilyIncome(double familyIncome) {
        if (familyIncome < 0) {
            throw new IllegalArgumentException("Family income cannot be negative");
        }
        this.familyIncome = familyIncome;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public void setScholarshipDuration(String scholarshipDuration) {
        this.scholarshipDuration = scholarshipDuration;
    }

    public void setPublications(ArrayList<Publication> publications) {
        if (publications == null) {
            this.publications = new ArrayList<>();
        } else {
            this.publications = publications;
        }
    }

    public void setDocuments(ArrayList<Document> documents) {
        if (documents == null) {
            this.documents = new ArrayList<>();
        } else {
            this.documents = documents;
        }
    }

    public void setScholarshipType(String scholarshipType) {
        this.scholarshipType = scholarshipType;
    }

    public void setDependants(int dependants) {
        if (dependants < 0) {
            throw new IllegalArgumentException("Dependants cannot be negative");
        }
        this.dependants = dependants;
    }

    protected boolean hasDocument(String documentType) {
        // Null check for parameter
        if (documentType == null || documentType.trim().isEmpty()) {
            return false;
        }

        // Null check for documents list
        if (documents == null) {
            return false;
        }

        for (Document doc : documents) {
            // Null check for each document
            if (doc != null && doc.getDocumentType() != null
                    && doc.getDocumentType().equals(documentType)) {
                return true;
            }
        }
        return false;
    }

    public void addDocument(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Cannot add null document");
        }

        // Ensure documents list is initialized
        if (documents == null) {
            documents = new ArrayList<>();
        }

        documents.add(document);
    }

    public void addPublication(Publication publication) {
        if (publication == null) {
            throw new IllegalArgumentException("Cannot add null publication");
        }

        // Ensure publications list is initialized
        if (publications == null) {
            publications = new ArrayList<>();
        }

        publications.add(publication);
    }

    @Override
    public int compareTo(Application application) {
        // Null check for parameter
        if (application == null) {
            return 1; // This object is "greater" than null
        }

        // Null check for IDs
        if (this.id == null && application.getId() == null) {
            return 0;
        }
        if (this.id == null) {
            return -1;
        }
        if (application.getId() == null) {
            return 1;
        }

        try {
            int thisId = this.getIdAsInteger();
            int otherId = application.getIdAsInteger();

            // Simple comparison
            return Integer.compare(thisId, otherId);

        } catch (Exception e) {
            // Fallback to string comparison if integer conversion fails
            return this.id.compareTo(application.getId());
        }
    }

    /**
     * Generates a string representation of the Application, matching the
     * format used in the Query.printApplication() method.
     *
     * @return A formatted string (e.g., "Applicant ID: 1101, Name: ...")
     */
    @Override
    public String toString() {
        // Get the required values
        String status = getEvaluationsStatus();

        // Start the base string.
        // We check for null status to avoid printing "null"
        String output = "Applicant ID: " + getId() +
                ", Name: " + getName() +
                ", Scholarship: " + getProgramType() +
                ", Status: " + (status != null ? status : "");

        // Use null-safe "literal".equals(variable) comparison
        if ("Accepted".equals(status)) {
            // Append the "Accepted" details
            output += ", Type: " + getScholarshipType() +
                    ", Duration: " + getScholarshipDuration();

        } else if ("Rejected".equals(status)) {
            // Append the "Rejected" details
            output += ", Reason: " + getRejectionReason();
        }

        return output;
    }
}
