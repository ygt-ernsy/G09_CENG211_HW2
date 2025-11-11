
/**
 * ResearchApplication
 */
import java.util.ArrayList;

public class ResearchApplication extends Application {

    public ResearchApplication(String applicantID, String name, double gpa, double income) {
        super(applicantID, name, gpa, income);
    }

    @Override
    public String getProgramType() {
        return "Research";
    }

    @Override
    public void evaluate() {
        if (!super.runGeneralChecks()) {
            return;
        }

        if (super.getPublications().isEmpty() && !super.hasDocument("GRP")) {
            reject("Missing publication or proposal");
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
            accept("Full");
        } else if (averageImpactFactor >= 1.0) {
            accept("Half");
        } else {
            reject("Publication impact too low");
        }

        if ("Accepted".equals(super.getEvaluationsStatus())) {
            int scholarshipDurationInMonths = calculateScholarshipDurationInMonths();
            setScholarshipDurationInYears(scholarshipDurationInMonths);
        }
    }

    private int calculateScholarshipDurationInMonths() {
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

        return scholarshipDurationInMonths;
    }

    private void setScholarshipDurationInYears(int scholarshipDurationInMonths) {

        switch (scholarshipDurationInMonths) {
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
