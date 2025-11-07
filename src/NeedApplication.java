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
            setEvaluationsStatus("Accepted");
            setScholarshipType("Full");
        }

        else if (familyIncome <= halfScholarshipThreshold){
            setEvaluationsStatus("Accepted");
            setScholarshipType("Half");
        }

        else{
            setEvaluationsStatus("Rejected");
            setRejectionReason("Financial Status unstable");
        }

        if ("Accepted".equals(getEvaluationsStatus())){
            setScholarshipDuration("1 year");
        }
    }
}
