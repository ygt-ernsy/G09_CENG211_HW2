import java.util.ArrayList;

/**
 * Application
 */
public abstract class Application {
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

    public boolean runGeneralChecks() {
        boolean hasENR = hasDocument("ENR");

        if (!hasENR) {
            setRejectionReason("Missing Enrollment Certificate");
            return false;
        }

        if (transcriptStatus != 'Y') {
            setRejectionReason("Missing Transcript");
            return false;
        }
        if (gpa < 2.5) {
            setRejectionReason("GPA must be at least 2.5");
            return false;
        }

        return true;
    }

    public abstract void evaluate();

    public char getTranscriptStatus() {
        return transcriptStatus;
    }

    public ArrayList<Publication> getPublications() {
        return publications;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
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

    public void setEvaluationsStatus(String evaluationsStatus) {
        this.evaluationsStatus = evaluationsStatus;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTranscriptStatus(char transcriptStatus) {
        this.transcriptStatus = transcriptStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setFamilyIncome(double familyIncome) {
        this.familyIncome = familyIncome;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public void setScholarshipDuration(String scholarshipDuration) {
        this.scholarshipDuration = scholarshipDuration;
    }

    public void setPublications(ArrayList<Publication> publications) {
        this.publications = publications;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public void setScholarshipType(String scholarshipType) {
        this.scholarshipType = scholarshipType;
    }

    public void setDependants(int dependants) {
        this.dependants = dependants;
    }

    protected boolean hasDocument(String document) {
        for (Document doc : this.documents) {
            if (doc.getDocumentType().equals(document)) {
                return true;
            }
        }
        return false;
    }
}
