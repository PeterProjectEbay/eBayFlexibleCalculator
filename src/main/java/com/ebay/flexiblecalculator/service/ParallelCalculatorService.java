package com.ebay.flexiblecalculator.service;



import com.ebay.flexiblecalculator.controller.ChainedCalculationRequest;
import com.ebay.flexiblecalculator.exceptions.BadParameterException;
import com.ebay.flexiblecalculator.model.Operation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service("parallelCalculatorService")
public class ParallelCalculatorService implements CalculatorService {

    private final ValidationService validationService;
    Executor customExecutor = Executors.newFixedThreadPool(10); // Limit the number of threads

    public ParallelCalculatorService(ValidationService validationService) {
        this.validationService = validationService;
    }

    // New async method to handle multiple chained calculation requests
    public CompletableFuture<List<Double>> calculateAsync(List<ChainedCalculationRequest> requests) {


        return CompletableFuture.runAsync(() -> {
            // Perform the calculations in parallel using the custom executor
            requests.parallelStream()
                    .forEach(request -> {
                        // Validate each request before processing
                        validationService.validateChainedRequest(request);
                    });
        }, customExecutor).thenApply(v ->
                requests.parallelStream()
                        .map(request -> calculateAsync(request).join()) // Reuse existing async calculation method
                        .collect(Collectors.toList())
        );
    }



    // calculation method
    public CompletableFuture<Double> calculateAsync(ChainedCalculationRequest request) {
        double result = request.getInitialValue();

        for (ChainedCalculationRequest.CalculationStep step : request.getSteps()) {

            Operation op = step.getOperation();
            result = calculate(result, step.getValue(), op);
        }

        return CompletableFuture.completedFuture(result);
    }

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
}

