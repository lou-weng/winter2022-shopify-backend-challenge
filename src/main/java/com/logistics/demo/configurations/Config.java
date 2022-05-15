package com.logistics.demo.configurations;

import com.logistics.demo.databaseConnectors.DatabaseConnectionHandler;
import com.logistics.demo.services.InventoryItemService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

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
    public InventoryItemService getInventoryItemConnectionHandler(DatabaseConnectionHandler dbHandler) {
        return new InventoryItemService(dbHandler);
    }
}
