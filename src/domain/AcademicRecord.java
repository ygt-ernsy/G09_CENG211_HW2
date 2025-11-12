package domain;

import java.util.ArrayList;

/**
 * AcademicRecord
 */
public class AcademicRecord {
    private double gpa;
    private char transcriptStatus;
    private final ArrayList<Document> documents;
    private final ArrayList<Publication> publications;

    public AcademicRecord(double gpa) {
        this.gpa = gpa;
        this.transcriptStatus = 'N'; // Default
        this.documents = new ArrayList<Document>();
        this.publications = new ArrayList<Publication>();
    }

    public boolean hasDocument(String documentType) {
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

    public double getGpa() {
        return gpa;
    }

    public char getTranscriptStatus() {
        return transcriptStatus;
    }

    public ArrayList<Publication> getPublications() {
        return publications;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void setTranscriptStatus(char transcriptStatus) {
        this.transcriptStatus = transcriptStatus;
    }
}
