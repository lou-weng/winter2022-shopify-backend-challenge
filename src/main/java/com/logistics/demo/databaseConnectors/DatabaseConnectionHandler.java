package com.logistics.demo.databaseConnectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionHandler {
    
    private String postgresURL;
    private String username;
    private String password;

    private Connection connection;

    public DatabaseConnectionHandler(String postgresURL, String username, String password) {
        this.postgresURL = postgresURL;
        this.username = username;
        this.password = password;
    }

    public boolean login() {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(this.postgresURL, this.username, this.password);
            connection.setAutoCommit(false);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error logging into the RDS instance");
            return false;
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error with closing the Oracle server");
        }
    }

    public void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println("Error with rolling back connection");
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
