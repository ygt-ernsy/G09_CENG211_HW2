package domain;

/**
 * EvaluationResult
 */
public class EvaluationResult {
    private final String status; // "Accepted" or "Rejected"
    private final String rejectionReason;
    private final String scholarshipType;
    private final String scholarshipDuration;

    public EvaluationResult(String scholarshipType, String scholarshipDuration) {
        this.status = "Accepted";
        this.scholarshipType = scholarshipType;
        this.scholarshipDuration = scholarshipDuration;
        this.rejectionReason = null;
    }

    public EvaluationResult(String rejectionReason) {
        this.status = "Rejected";
        this.rejectionReason = rejectionReason;
        this.scholarshipType = null;
        this.scholarshipDuration = null;
    }

    public String getStatus() {
        return status;
    }

    public String getScholarshipType() {
        if (this.scholarshipType == null && status.equals("Rejected"))
            System.out.println("This application is rejected so there is no scholarship type");
        if (this.scholarshipType == null && this.status == null)
            throw new NullPointerException("Scholarship type is null make sure EvaluationResult object is initialized");
        return scholarshipType;
    }

    public String getScholarshipDuration() {
        if (this.scholarshipDuration == null && status.equals("Rejected"))
            System.out.println("This application is rejected so there is no scholarship durations");
        if (this.scholarshipDuration == null && this.status == null)
            throw new NullPointerException(
                    "Scholarship duration is null make sure EvaluationResult object is initialized");
        return scholarshipDuration;
    }

    public String getRejectionReason() {
        if (this.rejectionReason == null && this.status.equals("Accepted"))
            System.out.println("This application is accepted there is no rejection reason");
        if (this.rejectionReason == null && this.status.equals("Rejected"))
            throw new NullPointerException("Rejection is null make sure EvaluationResult object is initialized");
        return rejectionReason;
    }
}
