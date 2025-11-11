import java.util.ArrayList;

/**
 * Query
 */
public class Query {
    private ArrayList<Application> applications;
    private ApplicationSorter sorter;

    public Query(ArrayList<Application> applications) {
        this.applications = applications;
        this.sorter = new ApplicationSorter(applications);
    }

    public void printAllApplications() {
        sorter.selectionSort();
        for (Application app : applications) {
            System.out.println(app.toString());
        }
    }
}
