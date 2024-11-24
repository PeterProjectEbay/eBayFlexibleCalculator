package com.ebay.flexiblecalculator.controller;

import com.ebay.flexiblecalculator.model.Operation;
import com.ebay.flexiblecalculator.service.BasicCalculatorService;

import com.ebay.flexiblecalculator.service.ParallelCalculatorService;
import com.ebay.flexiblecalculator.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    @Autowired
    private BasicCalculatorService calculatorService;

    @Autowired
    private ParallelCalculatorService parallelCalculatorService;



    @Autowired
    private ValidationService validationService;
    // Endpoint to perform a basic calculation
    @PostMapping("/calculate")
    public ResponseEntity<Double> calculate(@RequestParam double num1,
                                            @RequestParam double num2,
                                            @RequestParam String operation) {

            Operation op = Operation.valueOf(operation.toUpperCase());
            validationService.validateCalculateRequest(num1, num2, operation);
            double result = calculatorService.calculate(num1, num2, op);
            return ResponseEntity.ok(result);

    }

    @PostMapping("/calculate/chained")
    public ResponseEntity<Double> calculateChained(@RequestBody ChainedCalculationRequest request) {
        double result = request.getInitialValue();
        for (ChainedCalculationRequest.CalculationStep step : request.getSteps()) {
            Operation op = step.getOperation();
            validationService.validateChainedRequest(request);
            result = calculatorService.calculate(result, step.getValue(), op);
        }
        return ResponseEntity.ok(result);
    }



    // if a use need to send multiple chained calculation requests  this method will handle the Parallelization scenario
    @PostMapping("/calculateParallel")
    public ResponseEntity<List<Double>> calculateParallel(@RequestBody List<ChainedCalculationRequest> requests) {
        try {
            // Call the async calculation service and wait for the result to complete
            List<Double> results = parallelCalculatorService.calculateAsync(requests).get();
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}




