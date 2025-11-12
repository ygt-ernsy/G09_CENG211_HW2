package reporting;

import application.Application;
import domain.EvaluationResult;

/**
 * ApplicationReporter
 */
public class ApplicationReporter {

    /**
     * Formats an Application object for the final query output,
     * matching the "Expected Output Format" from.
     */
    public static String formatForQuery(Application app) {
        // Get the result from the (now clean) Application object
        EvaluationResult result = app.getResult();

        if (result == null) {
            return "Applicant ID: " + app.getId() +
                    ", Name: " + app.getName() +
                    ", Status: Not Evaluated";
        }

        String status = result.getStatus();
        String output = "Applicant ID: " + app.getId() +
                ", Name: " + app.getName() +
                ", Scholarship: " + app.getProgramType() +
                ", Status: " + status;

        // Use null-safe "literal".equals(variable)
        if ("Accepted".equals(status)) {
            output += ", Type: " + result.getScholarshipType() +
                    ", Duration: " + result.getScholarshipDuration();
        } else if ("Rejected".equals(status)) {
            output += ", Reason: " + result.getRejectionReason();
        }

        return output;
    }
}
