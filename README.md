# Shopping Platform Hexagonal Kotlin

A simple example of a shopping platform implemented in Kotlin with hexagonal architecture.

### Features
* REST API Endpoints:
  * Retrieve Product Information: Fetch details of a product using its UUID.
  * Calculate Total Price: Compute the total price for a given product and quantity, applying applicable discounts.
* Configurable Discount Policies.
* Hexagonal Architecture.
* In-Memory Database: For simplicity, the service uses an in-memory database to store product information.

### Assumptions
* If multiple discounts are applicable to a product, the service will apply the discount that results in the lowest total price for the customer.
* Percentage-based discounts are only applicable to specific products. There is a predefined list of product IDs for which each percentage-based discount can be applied. Products not on this list will not receive the percentage-based discount.
* The product data is fixed and hardcoded in the in-memory repository implementation.
* Discount policies are defined and configurable through the application.yaml file.
* The maximum number of products that can be processed in a single price calculation request is limited to 100

## Building the Application

### Step 1: Clean and Build the Application

Run the following command to clean and build the project:

```bash
./gradlew clean build
```

### Step 2: Package the Application into a Fat JAR
```bash
./gradlew bootJar
```

## Running the Application in Docker

### Step 1: Build the Docker Image

Use the following command to build the Docker image:
```bash
docker build -t shopping-platform .
```

### Step 2: Run the Docker Container

```bash
docker run -p 8080:8080 shopping-platform
```

### Predefined Products

The following products are preloaded into the in-memory database:

| ID                                     | Price (PLN) |
|----------------------------------------|-------------|
| aac7d817-93f0-4f6f-92c4-6752c95d23b0   | 599.99      |
| aac7d817-93f0-4f6f-92c4-6752c95d23b1   | 11999.99    |
| aac7d817-93f0-4f6f-92c4-6752c95d23b2   | 5299.99     |
| aac7d817-93f0-4f6f-92c4-6752c95d23b3   | 1299.00     |
| aac7d817-93f0-4f6f-92c4-6752c95d23b4   | 7699.99     |

### Configuration of Discounts

The discounts applied to the products are configured in the `application.yaml` file, located in the [src/main/resources](src/main/resources/application.yaml).

Here’s an example configuration:

```yaml
discounts:
  amount-based:
    - amount: 50
      threshold: 10
    - amount: 30
      threshold: 5
    - amount: 10
      threshold: 2
  percentage-based:
    - productIds:
        - "aac7d817-93f0-4f6f-92c4-6752c95d23b0"
        - "aac7d817-93f0-4f6f-92c4-6752c95d23b1"
      percentage: 10
    - productIds:
        - "aac7d817-93f0-4f6f-92c4-6752c95d23b2"
      percentage: 15
    - productIds:
        - "aac7d817-93f0-4f6f-92c4-6752c95d23b3"
      percentage: 20
```

## Calling the API Endpoints
### 1. Retrieve Product Information

To retrieve product information by UUID, you can use the following `curl` command.

```bash
curl -X GET "http://localhost:8080/products/aac7d817-93f0-4f6f-92c4-6752c95d23b0" -H "Accept: application/json"
```

### 2. Calling the Price Calculator API

To calculate total price of the product, you can use the following `curl` command.
```bash
curl -X POST "http://localhost:8080/products/aac7d817-93f0-4f6f-92c4-6752c95d23b0/calculate-price" \
-H "Content-Type: application/json" \
-d '{"quantity": 2}'
```
