package com.ebay.flexiblecalculator.service;

import com.ebay.flexiblecalculator.controller.CalculatorController;
import com.ebay.flexiblecalculator.exceptions.BadParameterException;
import com.ebay.flexiblecalculator.model.Operation;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service("basicCalculatorService")
public class BasicCalculatorService implements CalculatorService {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);




    @Override
    public double calculate(double num1, double num2, Operation operation) {
        switch (operation) {
            case ADD:
                return num1 + num2;
            case SUBTRACT:
                return num1 - num2;
            case MULTIPLY:
                return num1 * num2;
            case DIVIDE:
                return num1 / num2;
            default:
                throw new BadParameterException(operation.name());
        }
    }
    // if later need an advance service just create a new service @Service("advanceCalculate")
}