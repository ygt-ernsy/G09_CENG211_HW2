import java.util.ArrayList;

/**
 * ApplicationEvaluator
 */
public class ApplicationEvaluator {

    public void evaluateApplication(Application application) {
        application.evaluate();
    }

    public void evaluateApplications(ArrayList<Application> applicationList) {
        for (Application application : applicationList) {
            application.evaluate();
        }
    }
}
