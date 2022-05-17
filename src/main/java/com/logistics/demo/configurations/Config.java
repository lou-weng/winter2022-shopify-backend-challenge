package com.logistics.demo.configurations;

import javax.xml.crypto.Data;

import com.logistics.demo.databaseConnectors.DatabaseConnectionHandler;
import com.logistics.demo.services.InventoryItemService;
import com.logistics.demo.services.ShipmentService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    // Sets the beans and variabled used for dependency injection into the program. 
    // Ensures that there is only one instance of Services for the different controllers

    // Database configurations pulled from applications.properties
    @Value("${database.url}")
    private String postgresURL;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;

    @Bean
    public DatabaseConnectionHandler getConnectionHandler() {
        return new DatabaseConnectionHandler(postgresURL, username, password);
    }

    @Bean
    public InventoryItemService getInventoryItemService(DatabaseConnectionHandler dbHandler) {
        return new InventoryItemService(dbHandler);
    }

    @Bean
    public ShipmentService getShipmentService(DatabaseConnectionHandler dbHandler) {
        return new ShipmentService(dbHandler);
    }
}
