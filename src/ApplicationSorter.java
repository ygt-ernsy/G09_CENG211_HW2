import java.util.ArrayList;

/**
 * CustomSellectionSort
 */
public class ApplicationSorter {
    private ArrayList<Application> applications;

    public ApplicationSorter(ArrayList<Application> applications) {
        this.applications = applications;
    }

    public void selectionSort() {
        if (applications == null)
            return;

        for (int i = 0; i < applications.size(); i++) {
            int minIndex = findMinIndex(i);
            if (minIndex != i) {
                switchIndexOfApplications(i, minIndex);
            }
        }
    }

    public int findMinIndex(int startIndex) {
        int minIndex = startIndex;

        for (int i = 1; i < applications.size(); i++) {

            if (applications.get(i).compareTo(applications.get(minIndex)) < 0) {
                minIndex = i;
            }
        }

        return minIndex;
    }

    private void switchIndexOfApplications(int firstIndex, int secondIndex) {
        Application secondApplication = applications.get(secondIndex);
        applications.set(secondIndex, applications.get(firstIndex));
        applications.set(firstIndex, secondApplication);
    }
}
