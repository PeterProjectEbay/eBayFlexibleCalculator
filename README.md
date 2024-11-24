# eBay Flexible Calculator

## Overview

The **eBay Flexible Calculator** project is a flexible and extendable calculator that supports a variety of operations, including basic arithmetic (addition, subtraction, multiplication, and division), as well as chained and parallel calculations. This project is built using **Spring Boot** and utilizes both **synchronous** and **asynchronous** capabilities to demonstrate efficient and performant calculations, particularly for complex requests involving multiple chained calculations.

## Features

1. **Basic Calculation Service**
    - Handles fundamental arithmetic operations: **Addition, Subtraction, Multiplication, and Division**.
    - Implemented in the `BasicCalculatorService` class using a straightforward method that switches between different operations.

2. **Chained Calculation Requests**
    - Allows performing **chained calculations** starting from an initial value, with each step applying an arithmetic operation.
    - Implemented in the `CalculatorController` class using the `/calculate/chained` endpoint.
    - The `ChainedCalculationRequest` contains an initial value and a list of calculation steps to perform.

3. **Parallel Calculation Service**
    - Supports **parallel execution** of multiple chained calculation requests using the `ParallelCalculatorService`.
    - Implemented with `CompletableFuture` to ensure **asynchronous** handling of requests for improved performance.
    - Uses a custom executor (`Executors.newFixedThreadPool(10)`) to efficiently manage the number of concurrent threads.
    - The controller provides an endpoint `/calculateParallel` for clients to send multiple chained requests that can be processed concurrently.

4. **Validation Service**
    - The `ValidationService` provides input validation for all calculation requests to ensure their correctness before performing calculations.
    - **Validation Rules** include:
        - Arithmetic inputs cannot be **NaN**.
        - Division cannot be performed by **0**.
        - Operations must be **supported** (`ADD`, `SUBTRACT`, `MULTIPLY`, `DIVIDE`).
    - Ensures a robust user experience by catching and handling invalid input early.

5. **Exception Handling**
    - Custom exceptions (`BadParameterException`, `CalculatorException`) are defined to handle invalid user input or other errors.
    - The `CalculatorExceptionHandler` class uses `@RestControllerAdvice` to provide centralized exception handling.
    - Returns a well-structured `ErrorResponse` object that includes the HTTP status code and error message to aid debugging.

## Architecture

### Controllers
- **`CalculatorController`**: Handles incoming REST API requests for both basic and chained calculations. It delegates requests to respective services and ensures results are properly returned to clients.

### Services
- **`BasicCalculatorService`**: Provides basic arithmetic calculations (addition, subtraction, multiplication, and division).
- **`ParallelCalculatorService`**: Handles asynchronous calculation of multiple chained requests, improving performance for heavy workloads.
- **`ValidationService`**: Performs validation checks on all input data before it is processed by the calculation services.

### Exception Handling
- **`CalculatorExceptionHandler`**: Uses `@ExceptionHandler` annotations to catch and handle exceptions like `CalculatorException` and `BadParameterException`. This keeps error management consistent across the application.

## Endpoints

1. **Basic Calculation Endpoint**
    - **`POST /calculate`**: Accepts basic arithmetic operations to be performed on two numeric inputs.

2. **Chained Calculation Endpoint**
    - **`POST /calculate/chained`**: Accepts a `ChainedCalculationRequest` containing an initial value and a list of steps, allowing for multiple operations in a sequence.

3. **Parallel Calculation Endpoint**
    - **`POST /calculateParallel`**: Accepts a list of `ChainedCalculationRequest` objects, processes them in parallel using `CompletableFuture`, and returns the results after all are completed.

## How to Run

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/username/eBayFlexibleCalculator.git
   cd eBayFlexibleCalculator
   ```

2. **Build the Project**:
   ```sh
   ./mvnw install
   ```

3. **Run the Application**:
   ```sh
   ./mvnw spring-boot:run
   ```

## Usage Examples

- **Basic Calculation**:
  ```sh
  curl -X POST "http://localhost:8080/calculate?num1=10&num2=5&operation=ADD"
  ```
    - **Response**: `15.0`

- **Chained Calculation**:
  ```sh
  curl -X POST "http://localhost:8080/calculate/chained" -H "Content-Type: application/json" -d '{
      "initialValue": 10,
      "steps": [
          { "operation": "ADD", "value": 5 },
          { "operation": "MULTIPLY", "value": 2 }
      ]
  }'
  ```
    - **Response**: `30.0`

- **Parallel Calculation**:
  ```sh
  curl -X POST "http://localhost:8080/calculateParallel" -H "Content-Type: application/json" -d '[
      {
          "initialValue": 10,
          "steps": [
              { "operation": "ADD", "value": 5 },
              { "operation": "MULTIPLY", "value": 2 }
          ]
      },
      {
          "initialValue": 20,
          "steps": [
              { "operation": "SUBTRACT", "value": 5 },
              { "operation": "DIVIDE", "value": 3 }
          ]
      }
  ]'
  ```
    - **Response**: `[30.0, 5.0]`

## Technologies Used

- **Spring Boot**: For creating REST APIs and managing dependency injection.
- **Java CompletableFuture**: For handling **asynchronous parallel execution** of multiple requests.
- **Maven**: For project building and dependency management.
- **JUnit**: For unit testing the services and validation logic.

## Future Improvements
- **Advanced Operations**: Extend to support more complex mathematical functions like exponentiation, trigonometric functions, etc.
- **Detailed Logging**: Improve logging using more robust log management tools like **Log4j** or **SLF4J**.
- **Concurrency Handling**: Fine-tune thread pools to handle a larger number of concurrent requests effectively.

## TestResults
![img.png](img.png)
![img_1.png](img_1.png)


## Contact
If you have questions or suggestions, feel free to create an issue or submit a pull request. Contributions are always welcome!



