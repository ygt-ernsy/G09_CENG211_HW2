
/**
 * Query
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * ApplicationPrinter
 * Handles formatting and printing application results.
 */
public class Query {

    /**
     * Prints all applications in the required format, sorted by ID.
     * 
     * @param applications The list of applications to print
     */
    public void printAllApplications(ArrayList<Application> applications) {
        // Sort applications by ID
        sortApplicationsById(applications);

        // Print each application
        for (Application app : applications) {
            printApplication(app);
        }
    }

    /**
     * Prints a single application in the required format.
     * Format: Applicant ID: 1101, Name: Liam Smith, Scholarship: Merit, Status:
     * Accepted, Type: Full, Duration: 2 years
     * 
     * @param app The application to print
     */
    private void printApplication(Application app) {
        String scholarshipType = getScholarshipTypeFromId(app.getId());
        String status = app.getEvaluationsStatus();

        StringBuilder output = new StringBuilder();
        output.append("Applicant ID: ").append(app.getId());
        output.append(", Name: ").append(app.getName());
        output.append(", Scholarship: ").append(scholarshipType);
        output.append(", Status: ").append(status);

        if ("Accepted".equals(status)) {
            output.append(", Type: ").append(app.getScholarshipType());
            output.append(", Duration: ").append(app.getScholarshipDuration());
        } else if ("Rejected".equals(status)) {
            output.append(", Reason: ").append(app.getRejectionReason());
        }

        System.out.println(output.toString());
    }

    /**
     * Sorts applications by their ID in ascending order.
     * 
     * @param applications The list to sort
     */
    private void sortApplicationsById(ArrayList<Application> applications) {
    }

    /**
     * Determines scholarship type based on ID prefix.
     * 
     * @param id The applicant ID
     * @return The scholarship type name
     */
    private String getScholarshipTypeFromId(String id) {
        if (id.startsWith("11")) {
            return "Merit";
        } else if (id.startsWith("22")) {
            return "Need";
        } else if (id.startsWith("33")) {
            return "Research";
        }
        return "Unknown";
    }
}
