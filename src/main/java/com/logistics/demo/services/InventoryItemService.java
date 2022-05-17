package com.logistics.demo.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.logistics.demo.databaseConnectors.DatabaseConnectionHandler;
import com.logistics.demo.models.InventoryItem;

public class InventoryItemService extends Service{

    private final String ITEM_ID = "item_id";
    private final String NAME = "name";
    private final String QUANTITY = "quantity";
    private final String LAST_UPDATE = "last_update";
    
    public InventoryItemService(DatabaseConnectionHandler dbHandler) {
        super(dbHandler);
    }

    public void createInventoryItem(InventoryItem item) throws SQLException {
        try  {
            String itemQuery = "INSERT INTO inventory_items (name, quantity, last_update)" + 
                "VALUES (?, ?, TO_DATE(?, 'YYYYMMDD'))";
            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(itemQuery);
            ps.setString(1, item.getName());
            ps.setInt(2, item.getQuantity());
            ps.setString(3, item.getLastUpdated());
            ps.executeUpdate();

            this.dbHandler.getConnection().commit();
            ps.close();
        } catch (SQLException e) {
            this.dbHandler.rollbackConnection();
            throw e;
        }
    }

    public List<InventoryItem> getAllInventoryItems() throws SQLException {
        try {
            ArrayList<InventoryItem> result = new ArrayList<>();
            String itemQuery = "SELECT * FROM inventory_items";
            
            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(itemQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InventoryItem item = new InventoryItem(
                    rs.getInt(ITEM_ID),
                    rs.getString(NAME),
                    rs.getInt(QUANTITY),
                    rs.getString(LAST_UPDATE));
                result.add(item);
            }

            rs.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw e;
        }
    }

    public InventoryItem getInventoryItem(int itemId) throws SQLException {
        try {
            String itemQuery = "SELECT * FROM inventory_items AS i WHERE i.item_id = ?";
            
            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(itemQuery);
            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();

            rs.next();
            InventoryItem result = new InventoryItem(
                rs.getInt(ITEM_ID),
                rs.getString(NAME),
                rs.getInt(QUANTITY),
                rs.getString(LAST_UPDATE)
            );

            rs.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void updateInventoryItem(int itemId, InventoryItem item) throws SQLException {
        try  {
            String itemQuery = "UPDATE inventory_items SET name = ?, quantity = ?, last_update = TO_DATE(?, 'YYYYMMDD') WHERE item_id = ?";
            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(itemQuery);
            ps.setString(1, item.getName());
            ps.setInt(2, item.getQuantity());
            ps.setString(3, item.getLastUpdated());
            ps.setInt(4, itemId);
            
            ps.executeUpdate();
            this.dbHandler.getConnection().commit();

            ps.close();
        } catch (SQLException e) {
            this.dbHandler.rollbackConnection();
            throw e;
        }
    }

    public void deleteInventoryItem(int itemId) throws SQLException {
        try {
            String itemQuery = "DELETE FROM inventory_items WHERE item_id = ?";
            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(itemQuery);
            ps.setInt(1, itemId);

            ps.executeUpdate();
            this.dbHandler.getConnection().commit();

            ps.close();
        } catch (SQLException e) {
            this.dbHandler.rollbackConnection();
            throw e;
        }
    }

    public List<InventoryItem> getShipmentItems(int shipmentId) throws SQLException {
        try {
            ArrayList<InventoryItem> result = new ArrayList<>();
            String shipmentQuery = "SELECT s.shipment_id, i.item_id, i.name, s.quantity " + 
                                    "FROM inventory_items AS i, shipment_items AS s " + 
                                    "WHERE i.item_id = s.item_id and s.shipment_id = ?";
            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(shipmentQuery);
            ps.setInt(1, shipmentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InventoryItem item = new InventoryItem(
                    rs.getInt(ITEM_ID), 
                    rs.getString(NAME),
                    rs.getInt(QUANTITY),
                    ""
                );
                result.add(item);
            }

            rs.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw e;
        }
    }
    
}
