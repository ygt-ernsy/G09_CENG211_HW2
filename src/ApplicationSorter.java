import java.util.ArrayList;

/**
 * ApplicationSorter
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
            int minIndex = findMinimumIndex(i);
            if (minIndex != i) {
                swap(i, minIndex);
            }
        }
    }

    private int findMinimumIndex(int startIndex) {
        int minIndex = startIndex;
        for (int j = startIndex + 1; j < applications.size(); j++) {
            if (applications.get(j).compareTo(applications.get(minIndex)) < 0) {
                minIndex = j;
            }
        }
        return minIndex;
    }

    private void swap(int i, int j) {
        Application temp = applications.get(i);
        applications.set(i, applications.get(j));
        applications.set(j, temp);
    }
}
