/**
 * ResearchApplication
 */
import java.util.ArrayList;
public class ResearchApplication extends Application {

    public ResearchApplication(String applicantID, String name, double gpa, double income) {
        super(applicantID, name, gpa, income);
    }

    @Override
    public void evaluate() {
        if (!super.runGeneralChecks()) {
            return;
        }

        if (super.getPublications().isEmpty() && !super.hasDocument("GRP")) {
            super.setEvaluationsStatus("Rejected");
            super.setRejectionReason("Missing publication or proposal");
            return;
        }

        double totalImpactFactor = 0.0;
        double averageImpactFactor = 0.0;
        ArrayList<Publication> publicationList = super.getPublications();
        for (Publication publication : publicationList) {
            totalImpactFactor += publication.getImpactFactor();
        }

        if (!publicationList.isEmpty()) {
            averageImpactFactor = totalImpactFactor / publicationList.size();
        }


        if (averageImpactFactor >= 1.5) {
            super.setEvaluationsStatus("Accepted");
            super.setScholarshipType("Full");
        } else if (averageImpactFactor >= 1.0) {
            super.setEvaluationsStatus("Accepted");
            super.setScholarshipType("Half");
        } else {
            super.setEvaluationsStatus("Rejected");
            super.setRejectionReason("Publication impact too low");
        }

        if ("Accepted".equals(super.getEvaluationsStatus())) {
            int scholarshipDurationInMonths = 0;

            switch (super.getScholarshipType()) {
                case "Full":
                    scholarshipDurationInMonths = 12;
                    break;
                case "Half":
                    scholarshipDurationInMonths = 6;
                    break;
            }

            if (super.hasDocument("RSV")) {
                scholarshipDurationInMonths += 12;
            }

            switch(scholarshipDurationInMonths){
                case 24:
                    super.setScholarshipDuration("2 years");
                    break;
                case 18:
                    super.setScholarshipDuration("1 year 6 months");
                    break;
                case 12:
                    super.setScholarshipDuration("1 year");
                    break;
                case 6:
                    super.setScholarshipDuration("6 months");
                    break;
            }
        }
    }
}
