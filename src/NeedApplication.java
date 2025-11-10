/**
 * NeedApplication
 */
public class NeedApplication extends Application {

    public NeedApplication(String applicantID, String name, double gpa, double income) {
        super(applicantID, name, gpa, income);
    }

    @Override
    public String getProgramType() {
        return "Need";
    }

    @Override
    public void evaluate() {
        if (!super.runGeneralChecks()) {
            return;
        }

        double fullScholarshipThreshold = 10000.0;
        double halfScholarshipThreshold = 15000.0;

        if (super.hasDocument("SAV")) {
            fullScholarshipThreshold = fullScholarshipThreshold * 1.2;
            halfScholarshipThreshold = halfScholarshipThreshold * 1.2;
        }

        if (super.getDependants() >= 3) {
            fullScholarshipThreshold = fullScholarshipThreshold * 1.1;
            halfScholarshipThreshold = halfScholarshipThreshold * 1.1;
        }

        double familyIncome = getFamilyIncome();

        if (familyIncome <= fullScholarshipThreshold) {
            super.setEvaluationsStatus("Accepted");
            super.setScholarshipType("Full");
        } else if (familyIncome <= halfScholarshipThreshold) {
            super.setEvaluationsStatus("Accepted");
            super.setScholarshipType("Half");
        } else {
            super.setEvaluationsStatus("Rejected");
            super.setRejectionReason("Financial Status unstable");
        }

        if ("Accepted".equals(super.getEvaluationsStatus())) {
            super.setScholarshipDuration("1 year");
        }
    }
}
