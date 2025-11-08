/**
 * NeedApplication
 */
public class NeedApplication extends Application {

    public NeedApplication(String applicantID, String name, double gpa, double income) {
        super(applicantID, name, gpa, income);
    }

    @Override
    public void evaluate(){
        if (!super.runGeneralChecks()){
            return;
        }

        double fullScholarshipThreshold = 10000.0;
        double halfScholarshipThreshold = 15000.0;

        if(super.hasDocument("SAV")){
            fullScholarshipThreshold = fullScholarshipThreshold * 6/5;
            halfScholarshipThreshold = halfScholarshipThreshold * 6/5;
        }


        if(super.getDependants() >= 3 ){
            fullScholarshipThreshold = fullScholarshipThreshold * 11/10;
            halfScholarshipThreshold = halfScholarshipThreshold * 11/10;
        }

        double familyIncome = getFamilyIncome();

        if(familyIncome <= fullScholarshipThreshold){
            super.setEvaluationsStatus("Accepted");
            super.setScholarshipType("Full");
        }

        else if (familyIncome <= halfScholarshipThreshold){
            super.setEvaluationsStatus("Accepted");
            super.setScholarshipType("Half");
        }

        else{
            super.setEvaluationsStatus("Rejected");
            super.setRejectionReason("Financial Status unstable");
        }

        if ("Accepted".equals(super.getEvaluationsStatus())){
            super.setScholarshipDuration("1 year");
        }
    }
}
