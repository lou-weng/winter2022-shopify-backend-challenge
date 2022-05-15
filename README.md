# winter2022-shopify-backend-challenge

## Technical Design Document

The scope of this intern project is to create a backend system to track inventory information from a logistics company. 

## Tools

The implementation and testing of the project will be done using Java and the Spring Boot framework. Much of Spring Boot's higher level abstract features (Spring Data JPA) will not be used to demonstrate my own comfortability establishing a database connection and handling SQL. 

The database used for this project will be a minimally configured PostgreSQL RDS instance hosted on AWS.

## Data Model

```
    CREATE TABLE inventory_item (
        item_id SERIAL PRIMARY KEY, 
        name VARCHAR(100) NOT NULL,
        quantity NUMERIC NOT NULL,
        last_update DATE NOT NULL
    );

    CREATE TABLE shipment (
        shipment_id SERIAL PRIMARY KEY,
        ship_date DATE NOT NULL, 
        destination VARCHAR(100)
    );

    CREATE TABLE shipment_items (
        shipment_id INT REFERENCES shipment(shipment_id) ON UPDATE CASCADE ON DELETE CASCADE,
        item_id INT REFERENCES inventory_item(item_id) ON UPDATE CASCADE,
        quantity NUMERIC NOT NULL DEFAULT 1, 
        PRIMARY KEY(shipment_id, item_id)
    );
```

## Testing

| Operation | Test Case | Expected Behaviour |
| --- | --- | --- |
| Create    | Create a new item that does not currently exist in database | Pass |
| Read      |  |
| Delete    | Delete a new item that exists in database | Pass |
| Delete    | Delete 