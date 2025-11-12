package application;

import domain.AcademicRecord;
import domain.ApplicantProfile;
import domain.EvaluationResult;
import domain.FinancialProfile;
import domain.Publication;
import java.util.ArrayList;

/**
 * ResearchApplication - Handles research grant evaluation
 * Evaluation based on publications and impact factors
 */
public class ResearchApplication extends Application {

    public ResearchApplication(ApplicantProfile profile, AcademicRecord academics, FinancialProfile financials) {
        super(profile, academics, financials);
    }

    @Override
    public String getProgramType() {
        return "Research";
    }

    @Override
    public void evaluate() {
        // Step 1: Perform general validation (ENR, Transcript, GPA >= 2.5)
        String generalRejection = performGeneralValidation();
        if (generalRejection != null) {
            setResult(new EvaluationResult(generalRejection));
            return;
        }

        // Step 2: Check for at least one Publication or Grant Proposal (GRP)
        ArrayList<Publication> publications = getAcademics().getPublications();
        boolean hasGrantProposal = hasDocument("GRP");

        if ((publications == null || publications.isEmpty()) && !hasGrantProposal) {
            setResult(new EvaluationResult("Missing publication or proposal"));
            return;
        }

        // Step 3: Calculate average impact factor (only if publications exist)
        double averageImpact = 0.0;

        if (publications != null && !publications.isEmpty()) {
            double totalImpact = 0.0;
            int validPublications = 0;

            for (Publication pub : publications) {
                if (pub != null) {
                    totalImpact += pub.getImpactFactor();
                    validPublications++;
                }
            }

            if (validPublications > 0) {
                averageImpact = totalImpact / validPublications;
            }
        }

        // Step 4: Determine scholarship type based on average impact
        String scholarshipType;
        int baseDurationMonths;

        if (averageImpact >= 1.50) {
            scholarshipType = "Full";
            baseDurationMonths = 12; // 1 year
        } else if (averageImpact >= 1.00) {
            scholarshipType = "Half";
            baseDurationMonths = 6; // 6 months
        } else {
            // avg impact < 1.00 → Rejected
            setResult(new EvaluationResult("Publication impact too low"));
            return;
        }

        // Step 5: Extend duration if Research Supervisor Approval (RSV) exists
        if (hasDocument("RSV")) {
            baseDurationMonths += 12; // +1 year
        }

        // Step 6: Format duration string
        String duration = formatDuration(baseDurationMonths);

        // Step 7: Accept the application
        setResult(new EvaluationResult(scholarshipType, duration));
    }

    /**
     * Formats duration in months to a readable string
     * 
     * @param months Total months
     * @return Formatted duration string (e.g., "1 year", "18 months", "1 year 6
     *         months")
     */
    private String formatDuration(int months) {
        if (months == 12) {
            return "1 year";
        } else if (months == 24) {
            return "2 years";
        } else if (months == 6) {
            return "6 months";
        } else if (months == 18) {
            return "1 year 6 months";
        } else if (months % 12 == 0) {
            return (months / 12) + " years";
        } else if (months < 12) {
            return months + " months";
        } else {
            int years = months / 12;
            int remainingMonths = months % 12;
            return years + " year" + (years > 1 ? "s" : "") + " " + remainingMonths + " months";
        }
    }
}
