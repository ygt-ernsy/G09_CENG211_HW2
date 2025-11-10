import java.util.ArrayList;

/**
 * CustomSellectionSort
 */
public class ApplicationSorter {
    private ArrayList<Application> applications;

    public void sellectionSort() {

        if (applications == null) {
            return;
        }

        for (int i = 0; i < applications.size(); i++) {
            int minIndex = i;

            for (int j = 0; j < applications.size(); j++) {
                Application minApplication = applications.get(minIndex);
                Application current = applications.get(j);

                if (current.compareTo(minApplication) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                switchIndexOfApplications(i, minIndex, applications);
            }
        }
    }

    private void switchIndexOfApplications(int firstIndex, int secondIndex, ArrayList<Application> arrayList) {
        Application secondApplication = arrayList.get(secondIndex);
        arrayList.set(secondIndex, arrayList.get(firstIndex));
        arrayList.set(firstIndex, secondApplication);
    }

}
