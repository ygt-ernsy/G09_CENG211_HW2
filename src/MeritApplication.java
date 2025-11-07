/**
 * MeritApplication
 */
import java.util.ArrayList; // For access to ArrayList (used in the parent class)

public class MeritApplication extends Application {

    /**
     * Constructor for the MeritApplication object.
     *
     * @param applicantID The applicant's ID
     * @param name The applicant's name
     * @param gpa The applicant's Grade Point Average (GPA)
     * @param income The applicant's income (passed to parent, though not used here)
     */
    public MeritApplication(String applicantID, String name, double gpa, double income) {
        // Sends the basic information to the 'Application' (parent class) constructor.
        super(applicantID, name, gpa, income);
    }

    /**
     * Applies the specific evaluation rules for a Merit-Based Scholarship.
     * This method overrides the abstract 'evaluate' method from the 'Application' class.
     *
     */
    @Override
    public void evaluate() {

        // --- STEP 1: General Checks ---
        // Run the general checks defined in the parent (Application) class.
        // According to your plan, this method checks for ENR, Transcript 'Y', and GPA >= 2.5
        // If this method returns 'false', the 'evaluationStatus' and 'rejectionReason'
        // have already been set by the parent method.
        if (!super.runGeneralChecks()) {
            return; // General checks failed, stop evaluation.
        }

        // --- STEP 2: Merit-Based Specific GPA Rules ---
        // If we are here, the general checks (including GPA >= 2.5) have passed.
        // Now, apply the Merit rules.

        double applicantGpa = super.getGpa(); // Get GPA info from the parent class.

        if (applicantGpa >= 3.20) {
            // Rule: GPA >= 3.20 -> Full Scholarship
            super.setEvaluationsStatus("Accepted");
            super.setScholarshipType("Full");

        } else if (applicantGpa >= 3.00) {
            // Rule: 3.00 <= GPA < 3.20 -> Half Scholarship
            super.setEvaluationsStatus("Accepted");
            super.setScholarshipType("Half");

        } else {
            // Rule: GPA < 3.00 -> Rejected
            super.setEvaluationsStatus("Rejected");
            // Set rejection reason
            super.setRejectionReason("GPA below 3.0");
            return;
        }

        // --- STEP 3: Determine Scholarship Duration (Only if Accepted) ---
        // Rule: If Recommendation Letter (REC) is present, 2 years; otherwise 1 year

        // Assuming the 'Application' class has a helper method
        // (e.g., hasDocument("REC")) that checks the 'documents' list.
        if (super.hasDocument("REC")) {
            super.setScholarshipDuration("2 years");
        } else {
            super.setScholarshipDuration("1 year");
        }
    }
}
