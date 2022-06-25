# winter2022-shopify-backend-challenge

# Note: RDS instance deactivated right now

## How to Run
The entire project is runnable from a single jar file located in target. 

Build an executable jar file using ```mvn package```
Please use the command ```java -jar target/demo-0.0.1-SNAPSHOT.jar``` from the root project directory

The project frontend will run on localhost:8080/

The API endpoints are specified [here](#api-overview)

## Callouts of Bad Practice
Sorry I made some no-nos while trying to push out this challenge. Here are a few callouts of things I would avoid doing in a production environment:

- not securely storing database access credentials
- using select * in a database query
- making a crap ton of assumptions without gathering requirements from end users
- first time learning a new testing framework. Tests are not as comprehensize as I would have liked

## Overview

The scope of this intern project is to create a backend system to track inventory information from a logistics company. The application has a simple front end and is able to perform simple CRUD operations on a catalogue of inventory items. Moreover, it is able to create shipments and assign quantities of inventory items to said shipments. 

## Tools

The implementation and testing of the project will be done using Java and the Spring Boot framework. Much of Spring Boot's higher level abstract features (Spring Data JPA) will not be used to demonstrate comfortability establishing a database connection and handling basic SQL. 

The database used for this project is a PostgreSQL RDS instance hosted on AWS.

## Data Model

Note: most of the fields included are relevant to show the connection between the entities in the database. For simplicity's sake during implementation, some fields were left out. In an actual client facing application, fields such as supplier information or normalized location fields would be included in the shipments table.

```
    CREATE TABLE inventory_items (
        item_id SERIAL PRIMARY KEY, 
        name VARCHAR(100) NOT NULL,
        quantity NUMERIC NOT NULL,
        last_update DATE NOT NULL
    );

    CREATE TABLE shipments (
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

## API Overview
The following is an overview of the API implemented by the application:

### Inventory Item
Route: /inventory-items/...
| Endpoint | Type | Description | Requirements |
| --- | --- | --- | --- | 
| /getAll | GET | Get a list of all inventory items |
| /get/{itemId} | GET | Get the inventory item with the given id |
| /create | POST | Creates a new inventory item | Pass an [inventory item object](#inventory-item-object) into the request body with the information of the item you want to create in the database
| /delete/{itemId} | POST | Deletes an item with the given id | Pass an ID of an existing item into the URL
| /update/{itemId} | POST | Updates an item with the given id | Pass an [inventory item object](#inventory-item-object) into the request body with the information you want to update an item with

### Shipment 
Route: /shipments/
| Endpoint | Type | Description | Requirements |
| --- | --- | --- | --- |
| /getAll | GET | Get a list of all shipments | |
| /create | POST | Creates a new shipment | Pass a [shipment object](#shipment-object) into the request body with the information of the shipment you want to create |
| /delete/{shipmentId} | POST | Delete a shipment with the given id | Pass the ID of an existing shipment into the URL
| /addToShipment/{shipmentId} | POST | Add an inventory item into a shipment. This will decrease the amount of inventory on hand. | Pass an [inventory item object](#inventory-item-object) into the request body that you want to add to the shipment. Ensure to include item id and quantity for shipment. This operation will fail if there is not inventory for the shipment.

#### Inventory Item Object
```
{
  itemId: <int>
  name: <String>
  quantity: <int>
  lastUpdate: <String in form YYYYMMDD> 
}
```

Shipment Object
```
{
  shipmentId: <int>
  shipDate: <String in form YYYYMMDD>
  destination: <String>
}
```


## TODO
- [x] inventory_item
  - [x] create
  - [x] update
  - [x] delete
  - [x] read
- [x] shipment
  - [x] create
  - [x] add to
  - [x] delete
  - [x] read
