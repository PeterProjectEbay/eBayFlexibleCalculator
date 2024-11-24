package service;


import com.ebay.flexiblecalculator.exceptions.BadParameterException;
import com.ebay.flexiblecalculator.model.Operation;
import com.ebay.flexiblecalculator.service.BasicCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicCalculatorServiceTest {

    private BasicCalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new BasicCalculatorService();
    }

    @Test
    void testAddition() {
        double result = calculatorService.calculate(10.0, 5.0, Operation.ADD);
        assertEquals(15.0, result);
    }

    @Test
    void testSubtraction() {
        double result = calculatorService.calculate(10.0, 5.0, Operation.SUBTRACT);
        assertEquals(5.0, result);
    }

    @Test
    void testMultiplication() {
        double result = calculatorService.calculate(10.0, 5.0, Operation.MULTIPLY);
        assertEquals(50.0, result);
    }

    @Test
    void testDivision() {
        double result = calculatorService.calculate(10.0, 5.0, Operation.DIVIDE);
        assertEquals(2.0, result);
    }

    @Test
    void testInvalidOperation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                calculatorService.calculate(10.0, 5.0, Operation.valueOf("INVALID"))
        );
        assertTrue(exception.getMessage().contains("INVALID"));
    }
}
