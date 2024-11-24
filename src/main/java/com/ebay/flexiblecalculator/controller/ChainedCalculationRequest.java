package com.ebay.flexiblecalculator.controller;

import com.ebay.flexiblecalculator.model.Operation;
import java.util.List;

public class ChainedCalculationRequest {
    private double initialValue;
    private List<CalculationStep> steps;

    // Getters and setters
    public double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(double initialValue) {
        this.initialValue = initialValue;
    }

    public List<CalculationStep> getSteps() {
        return steps;
    }

    public void setSteps(List<CalculationStep> steps) {
        this.steps = steps;
    }

    // Inner class representing a single calculation step
    public static class CalculationStep {
        private Operation operation;
        private Double value;

        public Operation getOperation() {
            return operation;
        }

        public void setOperation(Operation operation) {
            this.operation = operation;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }
    }
}
