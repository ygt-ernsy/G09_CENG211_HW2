package domain;

/**
 * FinancialProfile
 */
public class FinancialProfile {
    private final double income;
    private double familyIncome;
    private int dependants;

    public FinancialProfile(double income) {
        this.income = income;
        this.familyIncome = 0.0; // default value
        this.dependants = 0; // default value
    }

    public double getIncome() {
        return income;
    }

    public double getFamilyIncome() {
        return familyIncome;
    }

    public int getDependants() {
        return dependants;
    }

    public void setFamilyIncome(double familyIncome) {
        if (familyIncome < 0)
            throw new IllegalArgumentException("Family income cannot be negative");
        this.familyIncome = familyIncome;
    }

    public void setDependants(int dependants) {
        if (dependants < 0)
            throw new IllegalArgumentException("Dependants cannot be negative");
        this.dependants = dependants;
    }
}
