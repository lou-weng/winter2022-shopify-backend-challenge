package com.logistics.demo.services;

import com.logistics.demo.databaseConnectors.DatabaseConnectionHandler;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class Service {

    @Autowired
    protected final DatabaseConnectionHandler dbHandler;

    public Service(DatabaseConnectionHandler dbHandler) {
        this.dbHandler = dbHandler;
        this.dbHandler.login();
    }
}