package application;

import domain.AcademicRecord;
import domain.ApplicantProfile;
import domain.EvaluationResult;
import domain.FinancialProfile;

/**
 * Application - Base class for all scholarship applications
 * Fixed: Made result mutable by removing final and adding setter
 */
public abstract class Application implements Comparable<Application> {
    private final ApplicantProfile profile;
    private final AcademicRecord academics;
    private final FinancialProfile financials;
    private EvaluationResult result; // FIXED: Removed final to allow setting after evaluation

    public abstract void evaluate();

    public Application(ApplicantProfile profile,
            AcademicRecord academics,
            FinancialProfile financials) {
        // Defensive programming: Validate inputs
        if (profile == null) {
            throw new IllegalArgumentException("ApplicantProfile cannot be null");
        }
        if (academics == null) {
            throw new IllegalArgumentException("AcademicRecord cannot be null");
        }
        if (financials == null) {
            throw new IllegalArgumentException("FinancialProfile cannot be null");
        }

        this.profile = profile;
        this.academics = academics;
        this.financials = financials;
        this.result = null; // Not evaluated yet
    }

    // --- Getters (Delegation) ---
    public ApplicantProfile getProfile() {
        return profile;
    }

    public AcademicRecord getAcademics() {
        return academics;
    }

    public FinancialProfile getFinancials() {
        return financials;
    }

    public EvaluationResult getResult() {
        return result;
    }

    // ADDED: Setter for result (needed after evaluation)
    protected void setResult(EvaluationResult result) {
        this.result = result;
    }

    // Convenience getters
    public String getId() {
        return profile.getId();
    }

    public String getName() {
        return profile.getName();
    }

    public int getIdAsInteger() {
        return profile.getIdAsInteger();
    }

    public boolean hasDocument(String documentType) {
        return academics.hasDocument(documentType);
    }

    // --- Comparison (Delegation) ---
    @Override
    public int compareTo(Application other) {
        if (other == null)
            return 1;
        // Delegate comparison to the profile
        return Integer.compare(this.getIdAsInteger(), other.getIdAsInteger());
    }

    /**
     * Common validation checks for all application types
     * Returns rejection reason if validation fails, null if passes
     */
    protected String performGeneralValidation() {
        // Priority 1: Missing Enrollment Certificate
        if (!hasDocument("ENR")) {
            return "Missing Enrollment Certificate";
        }

        // Priority 2: Missing Transcript
        if (academics.getTranscriptStatus() != 'Y') {
            return "Missing Transcript";
        }

        // Priority 3: GPA below 2.5
        if (academics.getGpa() < 2.50) {
            return "GPA below 2.5";
        }

        return null; // Validation passed
    }

    @Override
    public String toString() {
        return "Application [ID=" + getId() + ", Name=" + getName() + "]";
    }

    // Abstract method for application type
    public abstract String getProgramType();
}
