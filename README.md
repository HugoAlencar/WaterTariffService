# Water Tariff Service

## 1. Overview

A simple sample RESTful service created with Java and Spring Boot as a test. Its purpose is managing water tariff pricing tables (they are the tariff calculation parameters) and calculating the tariff for a given water consumption.
<br>The water tariffs are based on the consumption tiers based on volume in m³ and different client categories.

### 1.1. Concepts

#### 1.1.1. Client categories

Clients of different categories have different pricing tables and each category has only one pricing table. 
The current client categories are:
- COMERCIAL: For retailers.
- INDUSTRIAL: For industries.
- PARTICULAR: For regular private, personal use.
- PÚBLICO: For the public sector.

#### 1.1.2. Water tariff tables

A tariff table is the basis of tariff calculation. Each table has one or more pricing ranges based on the water volume consumption, and each range has its own base price. All ranges' limits are inclusive, which means their exact value are considered within the range defined by them. 
<br>Below is a tariff table example:
<br>


| Range start | Range end | Range price (R\$/m³) |
| ------------|-----------|--------------------- |
|         0m³ |      10m³ |        R\$ 6.17 / m³ |
|        11m³ |      20m³ |        R\$ 4.19 / m³ |
|        21m³ |      30m³ |        R\$ 4.50 / m³ |
|        31m³ |  no limit |        R\$ 5.00 / m³ |


The first range must start at 0m³ and the last range must not have an end. Also, they must be contiguous, i.e. they can never overlap and neither have gaps between them.

<i>Note: As only one tariff table is permitted for each category, when a new tariff table is created for a category, the older table is soft deleted, i.e. the older table is kept in the database for reference and for auditing but isn't used for further tariff calculations.</i>

#### 1.1.3. Tariff calculation

The table used as the basis for the water tariff calculation is the one targeted at the client's category. The calculation is also done based on the consumed volume (in m³) and the consumption ranges. The calculation is done for each range and their prices are summed in order to get the final charging price.

##### 1.1.3.1. Example:

Consider the following table as the tariff table for a client's category:

| Range start | Range end | Range price (R\$/m³) |
| ------------|-----------|--------------------- |
|         0m³ |      10m³ |        R\$ 4.00 / m³ |
|        11m³ |      20m³ |        R\$ 4.50 / m³ |
|        21m³ |      30m³ |        R\$ 5.50 / m³ |
|        31m³ |  no limit |        R\$ 7.00 / m³ |

If that client consumed 35 m³ of water, the calculation for each range will be:

> PriceRange1 = 10 m³ x R\$ 4.00/m³ = R\$ 40.00

> PriceRange2 = 10 m³ x R\$ 4.50/m³ = R\$ 45.00

> PriceRange3 = 10 m³ x R\$ 5.50/m³ = R\$ 55.00

> PriceRange4 = 5 m³ x R\$ 7.00/m³ = R\$ 35.00

> FinalPrice = PriceRange1 + PriceRange2 + PriceRange3 + PriceRange4<br>
> = R\$ 40.00 + R\$ 45.00 + R\$ 55.00 + R\$ 35.00<br>
> = R\$ 175.00 

## 2. Dependencies

<i>Note: The service may work with OpenJDK, as well as with older versions of the dependencies below, but it's untested.</i>

### 2.1. Mandatory
- Oracle JDK 26 ([link](https://www.oracle.com/java/technologies/downloads/))
- Apache Maven 3.9 ([link](https://maven.apache.org/download.cgi))
- PostgreSQL 18 ([link](https://www.postgresql.org/download/))
- A platform for API testing
  - Suggestion: Postman ([link](https://www.postman.com))

### 2.2. Optional
- A database management system with GUI
  - Suggestions:
    - pgAdmin 4 ([link](https://www.pgadmin.org/download/))
    - dbeaver 26.1 ([link](https://dbeaver.io))
- An IDE 
  - Suggestion: IntelliJ Idea 2026.1 ([link](https://www.jetbrains.com/idea/download/))

## 3. Service setup

### 3.1. Property files

Some of the service's settings are set in property files located in <i>src/main/resources</i>:
- application.properties: Contains the service's default properties; used for the development environment.
- application-homolog.properties: Contains the homologation environment properties.
- application-prod.properties: Contains the production environment properties.

Set the following properties in each of those files according to your environments:
- server.port: The service's listening port.
- logging.file: The log file path.
- spring.datasource.url: The database URL.
- spring.datasource.username: The database username (user must have read-write permissions).
- spring.datasource.password: The database user password.

### 3.2. Database initialization

The database initialization scripts are located in the project's <i>src/main/resources/db</i> folder and must be run in order: 
- schema.sql: Creates all tables and indexes; it runs automatically during build.
- data.sql: Populates the tables with all required data; it runs automatically during build.
- sample.sql: Optional; run it manually to populate the tables with additional data for testing purposes (requires the project to be built at least once).


## 4. Building

<i>Note: The building runs the database scripts for creating tables and inserting required data.</i>

Execute the following command at the project's root folder to build the service's jar file:
> mvn clean install

The built jar file will be in the project's <i>target</i> folder (<i>\<VERSION></i> will be the correct, current version number as defined in <i>pom.xml</i>):
> target/WaterTariffService-\<VERSION>.jar

## 5. Starting the service

Execute the following command at the project's <i>target</i> folder to start the service with its default settings (for development environment):
> java -jar WaterTariffService-\<VERSION>.jar

For homologation environment, pass the <i>homolog</i> active profile in the command: 
> java -jar WaterTariffService-\<VERSION>.jar --spring.profiles.active=homolog

For production environment, pass the <i>prod</i> active profile in the command:
> java -jar WaterTariffService-\<VERSION>.jar --spring.profiles.active=prod

## 6. Testing

### 6.1. Steps for service startup

1. Set up all mandatory dependencies (listed in item 2.1).
2. Set the service's property files following instructions from item 3.1.
3. Build the project as instructed in item 4.
4. Run the script <i>src/main/resources/db/sample.sql</i> in the PostgreSQL console (or your preferred database management system) to insert initial test data.
5. Start the service as instructed in item 5.

### 6.2. Testing with Postman

#### 6.2.1. Importing the Postman requests collection

Theres a file named <i>WaterTariffService.postman_collection</i> in the project's root folder containing a collection of requests for testing. To import it, follow these steps:
1. Open Postman.
2. Click on the three dots (...) to the right of Postman's filter field (it's in the left-side of the app).
3. Click on "Import" to open the import file selection wizard.
4. Drag and drop the file in the wizard to import it.

#### 6.2.2. The requests

Each request is made for testing a specific endpoint. Each endpoint URL is in the format <i>[HOST_URL]:[PORT]/api/[PATH]</i> and they are currently set for the development environment (the <i>[HOST_URL]</i> is <i>localhost</i> and the <i>[PORT]</i> is <i>8080</i>).
<br>Each request and their relevant parameters are described next.

##### 6.2.2.1. Get all category tariff tables

This request tests the tariff table fetching, which returns <i>only the currently active</i> tariff tables. It has no parameters and its URL is:
> GET localhost:8080/api/tabelas-tarifarias

Its return is in the following format:
```javascript
{
    "tabelas": [
        {
            "categoria": "COMERCIAL",
            "tabela": [
                {
                    "faixa": {
                        "inicio": 0,
                        "fim": 10
                    },
                    "valorUnitario": 1.11
                },
                {
                    "faixa": {
                        "inicio": 11,
                        "fim": 20
                    },
                    "valorUnitario": 2.22
                },
                {
                    "faixa": {
                        "inicio": 21,
                        "fim": 30
                    },
                    "valorUnitario": 3.33
                },
                {
                    "faixa": {
                    "inicio": 31,
                    "fim": null
                    },
                    "valorUnitario": 4.44
                }
            ]
        },
        {
          "categoria": "INDUSTRIAL",
          // continues ...
        }
    ]
}
```

##### 6.2.2.2. Delete water tariff table test

This tests deleting a single tariff table and its only parameter is the table's id and it's included in the URL. Change the end of the URL to the target table's id. The tables are only soft deleted and remain in the database, but they are no longer used for calculations.
<br>Endpoint URL (for table id 1):
> DELETE localhost:8080/api/tabelas-tarifarias/1

##### 6.2.2.3. Create all category tariff tables

This requests tests the endpoint for creating a batch of tariff tables. At most one table is permitted for each valid client category.
<br>The endpoint's URL:
> POST localhost:8080/api/tabelas-tarifarias

The tariff tables must be passed as JSON in the request's body in the following format (change the values and quantity of tables to test different scenarios):
```javascript
{
    "tabelas": [
        {
            "categoria": "COMERCIAL",
            "tabela": [
                {
                    "faixa": {
                        "inicio": 0,
                        "fim": 10
                    },
                    "valorUnitario": 1.11
                },
                {
                    "faixa": {
                        "inicio": 11,
                        "fim": 20
                    },
                    "valorUnitario": 2.22
                },
                {
                    "faixa": {
                        "inicio": 21,
                        "fim": 30
                    },
                    "valorUnitario": 3.33
                },
                {
                    "faixa": {
                    "inicio": 31,
                    "fim": null
                    },
                    "valorUnitario": 4.44
                }
            ]
        },
        {
            // continues ...
        }
    ]
}
```

##### 6.2.2.4. Upload category tariff tables file

This endpoint functions similar to the one that creates a batch of tariff tables, except by sending them in a JSON file.
<br>The endpoint's URL is:
>POST localhost:8080/api/tabelas-tarifarias/upload

There's a sample JSON file named <i>tabelas_tarifarias.json</i> in the project's root folder.

To include the file in the Postman request, follow these steps:
1. Open the request in Postman.
2. Click in its <i>Body</i>.
3. Change the body's type to <i>form-data</i>.
4. Create a single kay-value pair with the <i>Key</i> column as <i>arquivo</i> (of type <i>File</i>) and attach the JSON file in the <i>Value</i> column.

The JSON file's content must follow the format below (change the values and quantity of tables to test different scenarios):
```javascript
{
    "tabelas": [
        {
            "categoria": "COMERCIAL",
            "tabela": [
                {
                    "faixa": {
                        "inicio": 0,
                        "fim": 10
                    },
                    "valorUnitario": 1.11
                },
                {
                    "faixa": {
                        "inicio": 11,
                        "fim": 20
                    },
                    "valorUnitario": 2.22
                },
                {
                    "faixa": {
                        "inicio": 21,
                        "fim": 30
                    },
                    "valorUnitario": 3.33
                },
                {
                    "faixa": {
                    "inicio": 31,
                    "fim": null
                    },
                    "valorUnitario": 4.44
                }
            ]
        },
        {
            // continues ...
        }
    ]
}
```

##### 6.2.2.5. Calculate tariff

This endpoints calculates the water tariff based on the client's category and the consumed volume of water (in m³).
<br>The endpoint's URL is:
> POST localhost:8080/api/tarifa

The parameters are passed as JSON in the request's body, following this format:
```javascript
{
    "categoria": "INDUSTRIAL", // The category as informed in item 1.1.1
    "consumo": 45 // Consumed volume in m³
}
```
Change the category and the consumed volume to test different scenarios.

Its return is a JSON with calculation details for each price range from the table corresponding to the client's category as well as the total price to be charged.
<br>The result follows this format:
```javascript
{
    "categoria": "INDUSTRIAL",
    "consumoTotal": 45,
    "valorTotal": 138.60,
    "detalhamento": [
        {
            "faixa": {
                "inicio": 0,
                "fim": 10
            },
            "m3Cobrados": 10,
            "valorUnitario": 1.23,
            "subtotal": 12.30
        },
        {
            "faixa": {
                "inicio": 11,
                "fim": 20
            },
            "m3Cobrados": 10,
            "valorUnitario": 2.34,
            "subtotal": 23.40
        },
        {
            "faixa": {
                "inicio": 21,
                "fim": 30
            },
            "m3Cobrados": 10,
            "valorUnitario": 3.45,
            "subtotal": 34.50
        },
        {
            "faixa": {
                "inicio": 31,
                "fim": null
            },
            "m3Cobrados": 15,
            "valorUnitario": 4.56,
            "subtotal": 68.40
        }
    ]
}
```

#### 6.2.3. Sending the request

To send a request, open it (by double-clicking it) in Postman and then click on "Send" (it's the blue button on the right side of its window).
<br>Freely change the requests' parameters and body in order to test the endpoints' in different scenarios.

## 7. Future improvements

There is much room for improvements as this is a simple project made with a 3-day time limit. Possible future improvements include:
- Adding log entries in different levels.
- Adding more unit tests.
- Using Spring Data JPA annotations in DAO classes to map the tables' relations (like <i>@ManyToOne</i>, <i>@JoinColumn</i> and <i>@ForeignKey</i>.
- Change package structure to package the code by domain's entities or features.
  - It's mostly important for larger projects in order to keep them easier to maintain (smaller packages, packages divided by domain entities, etc). This project is fine as it is since it's small and it's not going to grow much. 
- Making the service require authentication, which is needed in real-life scenarios.
  - Alternatively, it could be not exposed on the internet and be behind an API gateway that handles authentication, which could include other benefits (like load balancing).
- Moving some of the service's settings out of the property files.
  - Specially properties that are environment-specific or contain sensitive data, like the database connection parameters.
  - Possible solutions include using Azure Key Vault or Spring Boot's cloud configuration server to get the settings at runtime.
- Running (or not) specific SQL scripts for different environments.
  - Migrations could be used with Flyway, for example. 
- Metrics and observability
    - For example, Spring Boot's Actuator and Admin server, or
    - Insert metrics into Elasticseach and create dashboards in Kibana or Grafana.
