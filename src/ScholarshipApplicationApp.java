import java.util.ArrayList;

/**
 * ScholarshipApplicationApp
 */
public class ScholarshipApplicationApp {
    public static void main(String[] args) {
        ApplicationReader applicationReader = new ApplicationReader();
        ArrayList<Application> applications = applicationReader
                .readApplications("resources/ScholarshipApplications.csv");
        ApplicationEvaluator applicationEvaluator = new ApplicationEvaluator();
        applicationEvaluator.evaluateApplications(applications);
        Query query = new Query(applications);

        query.printAllApplications();
    }
}
