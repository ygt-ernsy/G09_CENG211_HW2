package application;

import domain.AcademicRecord;
import domain.ApplicantProfile;
import domain.Document;
import domain.EvaluationResult;
import domain.FinancialProfile;

/**
 * MeritApplication - Handles merit-based scholarship evaluation
 * Evaluation based on GPA and recommendation letters
 */
public class MeritApplication extends Application {

    public MeritApplication(ApplicantProfile profile, AcademicRecord academics, FinancialProfile financials) {
        super(profile, academics, financials);
    }

    @Override
    public String getProgramType() {
        return "Merit";
    }

    @Override
    public void evaluate() {
        // Step 1: Perform general validation (ENR, Transcript, GPA >= 2.5)
        String generalRejection = performGeneralValidation();
        if (generalRejection != null) {
            setResult(new EvaluationResult(generalRejection));
            return;
        }

        // Step 2: Check for mandatory document (Recommendation Letter for merit)
        // According to the spec, REC affects duration but is not mandatory for
        // acceptance
        // The rejection reason "Missing mandatory document" applies when other
        // mandatory docs are missing
        // For merit, only ENR is mandatory (already checked in general validation)

        // Step 3: Evaluate based on GPA
        double gpa = getAcademics().getGpa();
        String scholarshipType;

        if (gpa >= 3.20) {
            scholarshipType = "Full";
        } else if (gpa >= 3.00) {
            scholarshipType = "Half";
        } else {
            // GPA < 3.00 → Rejected
            setResult(new EvaluationResult("GPA below 3.0"));
            return;
        }

        // Step 4: Determine duration based on Recommendation Letter
        String duration;
        if (hasDocument("REC")) {
            duration = "2 years";
        } else {
            duration = "1 year";
        }

        // Step 5: Accept the application
        setResult(new EvaluationResult(scholarshipType, duration));
    }
}
