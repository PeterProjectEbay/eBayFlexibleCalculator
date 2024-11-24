package com.ebay.flexiblecalculator.service;


import com.ebay.flexiblecalculator.controller.ChainedCalculationRequest;
import com.ebay.flexiblecalculator.exceptions.BadParameterException;
import com.ebay.flexiblecalculator.model.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service

public class ValidationService {
    private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);
    // Validate the basic calculation request
    public void validateCalculateRequest(double num1, double num2, String operation) {
        validateOperation(operation);
        validateInputs(num1, num2);

        if (num2 == 0.0 && operation.toUpperCase().equals("DIVIDE")) {
            logger.error("Invalid input: Divisor cannot be 0");
            throw new BadParameterException("Invalid input: Divisor cannot be 0");
        }
    }
    // Validate the chained calculation request
    public void validateChainedRequest(ChainedCalculationRequest request) {
        if (request == null || request.getSteps() == null || request.getSteps().isEmpty()) {
            logger.error("Invalid chained calculation request: Steps cannot be null or empty");
            throw new BadParameterException("Invalid chained calculation request: Steps cannot be null or empty");
        }
        for (ChainedCalculationRequest.CalculationStep step : request.getSteps()) {
            validateOperation(step.getOperation().name());
            if (step.getValue() == null) {
                logger.error("Invalid operation step: Value cannot be null");
                throw new BadParameterException("Invalid operation step: Value cannot be null");
            }
            if (step.getValue() == 0.0 && step.getOperation().name().equals("DIVIDE")) {
                logger.error("Invalid input: Divisor cannot be 0");
                throw new BadParameterException("Invalid input: Divisor cannot be 0");
            }
        }
    }
    // Validate if the operation is supported
    public void validateOperation(String operation) {
        try {
            Operation.valueOf(operation.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid operation: {}. Supported operations are: ADD, SUBTRACT, MULTIPLY, DIVIDE.", operation);
            throw new BadParameterException("Invalid operation: " + operation + ". Supported operations are: ADD, SUBTRACT, MULTIPLY, DIVIDE.");
        }
    }
    // Validate the inputs for basic calculation
    public void validateInputs(double num1, double num2) {
        if (Double.isNaN(num1) || Double.isNaN(num2)) {
            logger.error("Invalid input: Numbers cannot be NaN");
            throw new BadParameterException("Invalid input: Numbers cannot be NaN");
        }

    }
}
