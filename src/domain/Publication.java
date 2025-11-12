package domain;

/**
 * Publication
 */
public class Publication {

    // --- Fields ---

    /**
     * The title of the publication.
     */
    private final String title;

    /**
     * The impact factor of the publication, used in evaluation.
     */
    private final double impactFactor;

    // --- Constructor ---

    /**
     * Constructs a new Publication object.
     *
     * @param title        The title of the publication.
     * @param impactFactor The impact factor associated with the publication.
     */
    public Publication(String title, double impactFactor) {
        this.title = title;
        this.impactFactor = impactFactor;
    }

    // --- Getters ---

    /**
     * Gets the title of the publication.
     * (This might not be used in evaluation, but is good practice to have).
     * 
     * @return The publication title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the impact factor of the publication.
     * This is required by the ResearchApplication's 'evaluate' method
     * to calculate the average impact.
     * 
     * @return The impact factor.
     */
    public double getImpactFactor() {
        return this.impactFactor;
    }
}
