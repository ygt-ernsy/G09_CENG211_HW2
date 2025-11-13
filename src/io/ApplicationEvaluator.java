package io;

import application.Application;
import java.util.ArrayList;

/**
 * ApplicationEvaluator - Evaluates all applications in a list
 * 
 * This class encapsulates the evaluation logic, separating it from
 * the main application flow for better code organization and reusability.
 */
public class ApplicationEvaluator {

    /**
     * Evaluates all applications in the provided list
     * 
     * @param applications ArrayList of applications to evaluate
     * @throws IllegalArgumentException if applications list is null
     */
    public void evaluateAll(ArrayList<Application> applications) {
        // Defensive programming: validate input
        if (applications == null) {
            throw new IllegalArgumentException("Applications list cannot be null");
        }

        // Evaluate each application
        for (Application app : applications) {
            if (app != null) {
                app.evaluate();
            }
        }
    }

    /**
     * Evaluates all applications and returns count of evaluated applications
     * 
     * @param applications ArrayList of applications to evaluate
     * @return Number of applications successfully evaluated
     */
    public int evaluateAllWithCount(ArrayList<Application> applications) {
        if (applications == null) {
            return 0;
        }

        int evaluatedCount = 0;

        for (Application app : applications) {
            if (app != null) {
                app.evaluate();
                evaluatedCount++;
            }
        }

        return evaluatedCount;
    }
}
