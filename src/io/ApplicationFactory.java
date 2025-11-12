package io;

import domain.AcademicRecord;
import domain.ApplicantProfile;
import domain.FinancialProfile;
import application.Application;
import application.MeritApplication;
import application.NeedApplication;
import application.ResearchApplication;

/**
 * ApplicationFactory
 */
public class ApplicationFactory {
    public static Application createApplication(String[] tokens) {
        String id = tokens[1];
        String prefix = id.substring(0, 2);
        String name = tokens[2];
        double gpa = Double.parseDouble(tokens[3]);
        double income = Double.parseDouble(tokens[4]);

        // 1. Create the new "POJO" data objects from Part I
        ApplicantProfile profile = new ApplicantProfile(id, name);
        AcademicRecord academics = new AcademicRecord(gpa);
        FinancialProfile financials = new FinancialProfile(income);

        // 2. Use Factory logic to build the right Application
        // and INJECT the corresponding Strategy.
        switch (prefix) {
            case "11":
                return new MeritApplication(profile, academics, financials);
            case "22":
                return new NeedApplication(profile, academics, financials);
            case "33":
                return new ResearchApplication(profile, academics, financials);
            default:
                return null;
        }
    }
}
