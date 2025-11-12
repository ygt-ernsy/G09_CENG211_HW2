package reporting;

import java.util.ArrayList;
import application.Application;

/**
 * Query - Handles querying and displaying application results
 * Fixed to use ApplicationReporter for proper formatting
 */
public class Query {
    private ArrayList<Application> applications;
    private ApplicationSorter sorter;

    public Query(ArrayList<Application> applications) {
        if (applications == null) {
            throw new IllegalArgumentException("Applications list cannot be null");
        }
        this.applications = applications;
        this.sorter = new ApplicationSorter(applications);
    }

    /**
     * Prints all applications in sorted order with proper formatting
     */
    public void printAllApplications() {
        // Sort applications by ID
        sorter.selectionSort();

        // Print each application using the proper formatter
        for (Application app : applications) {
            if (app != null) {
                String formattedOutput = ApplicationReporter.formatForQuery(app);
                System.out.println(formattedOutput);
            }
        }
    }
}
