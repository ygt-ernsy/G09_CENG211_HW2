package domain;

/**
 * ApplicantProfile
 */
public class ApplicantProfile implements Comparable<ApplicantProfile> {
    private final String id;
    private final String name;

    public ApplicantProfile(String id, String name) {
        // Validate required parameters
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        this.id = id;
        this.name = name;
    }

    public int getIdAsInteger() {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalStateException("ID is null or empty");
        }
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("ID cannot be converted to integer: " + id);
        }
    }

    @Override
    public int compareTo(ApplicantProfile applicantProfile) {
        // Null check for parameter
        if (applicantProfile == null) {
            return 1; // This object is "greater" than null
        }

        // Null check for IDs
        if (this.id == null && applicantProfile.getId() == null) {
            return 0;
        }
        if (this.id == null) {
            return -1;
        }
        if (applicantProfile.getId() == null) {
            return 1;
        }

        try {
            int thisId = this.getIdAsInteger();
            int otherId = applicantProfile.getIdAsInteger();

            // Simple comparison
            return Integer.compare(thisId, otherId);

        } catch (Exception e) {
            // Fallback to string comparison if integer conversion fails
            return this.id.compareTo(applicantProfile.getId());
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
