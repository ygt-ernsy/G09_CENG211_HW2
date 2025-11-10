
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
    ApplicationSorter applicationSorter;

    public Query(ApplicationSorter applicationSorter) {
        this.applicationSorter = applicationSorter;
    }

    /**
     * Prints all applications in the required format, sorted by ID.
     * 
     * @param applications The list of applications to print
     */
    public void printAllApplications(ArrayList<Application> applications) {
        // Sort applications by ID
        applicationSorter.sellectionSort();

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
     * @param application The application to print
     */
    private void printApplication(Application application) {
        application.toString();
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
