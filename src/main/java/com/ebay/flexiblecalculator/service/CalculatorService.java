package com.ebay.flexiblecalculator.service;

import com.ebay.flexiblecalculator.controller.ChainedCalculationRequest;
import com.ebay.flexiblecalculator.model.Operation;

import java.util.concurrent.CompletableFuture;

// CalculatorService Interface
public interface CalculatorService {
    double calculate(double num1, double num2, Operation operation);


}