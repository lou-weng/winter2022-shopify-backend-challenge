package com.logistics.demo.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.logistics.demo.databaseConnectors.DatabaseConnectionHandler;
import com.logistics.demo.models.Shipment;

public class ShipmentService extends Service {

    private final String SHIPMENT_ID = "shipment_id";
    private final String SHIP_DATE = "ship_date";
    private final String DESTINATION = "destination";

    public ShipmentService(DatabaseConnectionHandler dbHandler) {
        super(dbHandler);
    }

    public void createShipment(Shipment shipment) throws SQLException {
        try {
            String shipmentQuery = "INSERT INTO shipments (ship_date, destination) " + 
                "VALUES (TO_DATE(?, 'YYYYMMDD'), ?)";
            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(shipmentQuery);
            ps.setString(1, shipment.getShipDate());
            ps.setString(2, shipment.getDestination());
            ps.executeUpdate();

            this.dbHandler.getConnection().commit();
            ps.close();
        } catch (SQLException e) {
            this.dbHandler.rollbackConnection();
            throw e;
        }
    }

    public List<Shipment> getAllShipments() throws SQLException {
        try {
            ArrayList<Shipment> result = new ArrayList<>();
            String shipmentQuery = "SELECT * FROM shipments";

            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(shipmentQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Shipment shipment = new Shipment(
                    rs.getInt(SHIPMENT_ID), 
                    rs.getString(SHIP_DATE), 
                    rs.getString(DESTINATION)
                );
                result.add(shipment);
            } 

            rs.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void addToShipment(int shipmentId, int itemId, int quantity) throws SQLException {
        try {
            String shipmentItemQuery = "INSERT INTO shipment_items VALUES (?, ?, ?)";
            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(shipmentItemQuery);
            ps.setInt(1, shipmentId);
            ps.setInt(2, itemId);
            ps.setInt(3, quantity);
            ps.executeUpdate();

            String itemInventoryQuery = "UPDATE inventory_items SET quantity = quantity - ? WHERE item_id = ?";
            PreparedStatement ps2 = this.dbHandler.getConnection().prepareStatement(itemInventoryQuery);
            ps2.setInt(1, quantity);
            ps2.setInt(2, itemId);
            ps2.executeUpdate();

            this.dbHandler.getConnection().commit();
            ps.close();
            ps2.close();
        } catch (SQLException e) {
            this.dbHandler.rollbackConnection();
            throw e;
        }
    }

    public void deleteShipment(int shipmentId) throws SQLException {
        try {
            String shipmentQuery = "DELETE FROM shipments WHERE shipment_id = ?";
            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(shipmentQuery);
            ps.setInt(1, shipmentId);

            ps.executeUpdate();
            this.dbHandler.getConnection().commit();

            ps.close();
        } catch (SQLException e) {
            this.dbHandler.rollbackConnection();
            throw e;
        }
    }
    
}
