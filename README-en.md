Golden Raspberry Awards Project
===============================

(Não fala inglês? [Clique aqui](https://github.com/IsraelZG/goldenraspberry/blob/main/README.md) para ver essa página em português.)

This project is a Java Spring Boot application that manages information about movies and producers, including those who have received the infamous Golden Raspberry Award. 
There is only one endpoint available, which returns the producers who have received two consecutive awards, both in the longest and shortest time intervals. 
This guide will help you set up and run the project, as well as execute the integration tests.
Prerequisites
-------------

Before starting, make sure you have the following tools installed in your development environment:

-   [Java Development Kit (JDK) 22+](https://www.oracle.com/br/java/technologies/downloads/#java22)
-   [Git](https://git-scm.com/)

*Note: This project includes the Maven Wrapper (`mvnw`), so it is not necessary to have Maven installed separately.*

Cloning the Repository
----------------------

First, clone the repository to your local machine using the Git command:

```bash
git clone <https://github.com/IsraelZG/goldenraspberry.git> cd golden-raspberry-awards
``` 

Setting Up the Project
----------------------

This project uses an in-memory H2 database for ease of development and testing. There is no need to set up an external database.

Running the Application
-----------------------

To run the application locally, follow these steps:

1.  **Compile the Project**: Navigate to the project directory and execute the following command to compile the project and download the dependencies:

    ```bash
    ./mvnw clean install
    ``` 

2.  **Run the Application**: After a successful compilation, start the application with the following command:

    ```bash
    ./mvnw spring-boot:run
    ```

    The application will be available at `http://localhost:8082`.

3. **Making the Request**:
    - **In the browser**: Type `http://localhost:8082/api/producers/award-intervals` in the address bar and press 'Enter'.
    - **In Postman (or a similar tool)**: Make a GET request to the URL `http://localhost:8082/api/producers/award-intervals`.

Running the Tests
-----------------

To ensure everything is working as expected, it's important to run the integration tests included in the project. Follow these steps:

1.  **Execute Tests**: Use the Maven Wrapper to run all tests:

    ```bash    
    ./mvnw test
    ```

    This will execute all unit and integration tests defined in the project.


2.  **Check Results**: After execution, check the results in the console. All tests should pass without errors.

Accessing the H2 Console
------------------------

While the application is running, you can access the H2 database web console to view and interact with the data:

-   Go to `http://localhost:8082/h2-console`
-   Use the following credentials:
    -   **JDBC URL**: `jdbc:h2:mem:testdb`
    -   **User Name**: `sa`
    -   **Password**: *(leave blank)*