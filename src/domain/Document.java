package domain;

/**
 * Document
 */
public class Document {
    private final String documentType;
    private final int durationInMonths;

    public Document(Document otherDocument) {
        this.documentType = otherDocument.getDocumentType();
        this.durationInMonths = otherDocument.getDurationInMonths();
    }

    public Document(String documentType, int durationInMonths) {
        this.documentType = documentType;
        this.durationInMonths = durationInMonths;
    }

    public int getDurationInMonths() {
        return durationInMonths;
    }

    public String getDocumentType() {
        return documentType;
    }
}
