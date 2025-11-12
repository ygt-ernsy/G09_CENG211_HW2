package application;

import domain.AcademicRecord;
import domain.ApplicantProfile;
import domain.EvaluationResult;
import domain.FinancialProfile;

/**
 * NeedApplication - Handles need-based scholarship evaluation
 * Evaluation based on family income and dependents
 */
public class NeedApplication extends Application {

    public NeedApplication(ApplicantProfile profile, AcademicRecord academics, FinancialProfile financials) {
        super(profile, academics, financials);
    }

    @Override
    public String getProgramType() {
        return "Need";
    }

    @Override
    public void evaluate() {
        // Step 1: Perform general validation (ENR, Transcript, GPA >= 2.5)
        String generalRejection = performGeneralValidation();
        if (generalRejection != null) {
            setResult(new EvaluationResult(generalRejection));
            return;
        }

        // Step 2: Get base income thresholds
        double fullScholarshipThreshold = 10000.0;
        double halfScholarshipThreshold = 15000.0;

        // Step 3: Adjust thresholds based on savings document (SAV)
        if (hasDocument("SAV")) {
            // Increase thresholds by 20%
            fullScholarshipThreshold *= 1.20;
            halfScholarshipThreshold *= 1.20;
        }

        // Step 4: Adjust thresholds based on dependents
        int dependents = getFinancials().getDependants();
        if (dependents >= 3) {
            // Increase thresholds by additional 10%
            fullScholarshipThreshold *= 1.10;
            halfScholarshipThreshold *= 1.10;
        }

        // Step 5: Evaluate based on family income
        // Use familyIncome if set, otherwise use income from applicant info
        double incomeToEvaluate = getFinancials().getFamilyIncome();
        if (incomeToEvaluate == 0.0) {
            incomeToEvaluate = getFinancials().getIncome();
        }

        String scholarshipType;

        if (incomeToEvaluate <= fullScholarshipThreshold) {
            scholarshipType = "Full";
        } else if (incomeToEvaluate <= halfScholarshipThreshold) {
            scholarshipType = "Half";
        } else {
            // Income > threshold → Rejected
            setResult(new EvaluationResult("Financial status unstable"));
            return;
        }

        // Step 6: Duration is always 1 year for need-based
        String duration = "1 year";

        // Step 7: Accept the application
        setResult(new EvaluationResult(scholarshipType, duration));
    }
}
