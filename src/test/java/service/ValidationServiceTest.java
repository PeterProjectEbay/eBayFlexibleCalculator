package service;



import com.ebay.flexiblecalculator.controller.ChainedCalculationRequest;
import com.ebay.flexiblecalculator.exceptions.BadParameterException;
import com.ebay.flexiblecalculator.model.Operation;
import com.ebay.flexiblecalculator.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {

    private ValidationService validationService;

    @BeforeEach
    void setUp() {
        validationService = new ValidationService();
    }

    @Test
    void testValidateCalculateRequest_ValidInputs() {
        assertDoesNotThrow(() -> validationService.validateCalculateRequest(10, 5, "ADD"));
        assertDoesNotThrow(() -> validationService.validateCalculateRequest(10, 5, "SUBTRACT"));
        assertDoesNotThrow(() -> validationService.validateCalculateRequest(10, 5, "MULTIPLY"));
        assertDoesNotThrow(() -> validationService.validateCalculateRequest(10, 5, "DIVIDE"));
    }

    @Test
    void testValidateCalculateRequest_InvalidOperation() {
        Exception exception = assertThrows(BadParameterException.class, () ->
                validationService.validateCalculateRequest(10, 5, "INVALID")
        );
        assertTrue(exception.getMessage().contains("Invalid operation"));
    }

    @Test
    void testValidateCalculateRequest_DivideByZero() {
        Exception exception = assertThrows(BadParameterException.class, () ->
                validationService.validateCalculateRequest(10, 0, "DIVIDE")
        );
        assertTrue(exception.getMessage().contains("Divisor cannot be 0"));
    }

    @Test
    void testValidateCalculateRequest_NaNInputs() {
        Exception exception = assertThrows(BadParameterException.class, () ->
                validationService.validateCalculateRequest(Double.NaN, 10, "ADD")
        );
        assertTrue(exception.getMessage().contains("Numbers cannot be NaN"));

        exception = assertThrows(BadParameterException.class, () ->
                validationService.validateCalculateRequest(10, Double.NaN, "SUBTRACT")
        );
        assertTrue(exception.getMessage().contains("Numbers cannot be NaN"));
    }

    @Test
    void testValidateChainedRequest_DivideByZeroInStep() {
        // Create the ChainedCalculationRequest object
        ChainedCalculationRequest request = new ChainedCalculationRequest();
        request.setInitialValue(10.0);

        // Add steps, including a division by zero
        ChainedCalculationRequest.CalculationStep step1 = new ChainedCalculationRequest.CalculationStep();
        step1.setOperation(Operation.ADD);
        step1.setValue(5.0);

        ChainedCalculationRequest.CalculationStep step2 = new ChainedCalculationRequest.CalculationStep();
        step2.setOperation(Operation.DIVIDE);
        step2.setValue(0.0); // Division by zero step

        // Add the steps to the request
        List<ChainedCalculationRequest.CalculationStep> steps = new ArrayList<>();
        steps.add(step1);
        steps.add(step2);
        request.setSteps(steps);

        // Validate the request and check for the expected exception
        Exception exception = assertThrows(BadParameterException.class, () ->
                validationService.validateChainedRequest(request)
        );
        assertTrue(exception.getMessage().contains("Divisor cannot be 0"));
    }


}

