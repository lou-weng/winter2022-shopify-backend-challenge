package com.logistics.demo.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.logistics.demo.databaseConnectors.DatabaseConnectionHandler;
import com.logistics.demo.models.InventoryItem;

public class InventoryItemService extends Service{
    
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
                    rs.getInt("item_id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getString("last_update"));
                result.add(item);
            }

            rs.close();
            ps.close();
            return result;
        } catch (SQLException e) {
            throw e;
        }
    }

    public void updateInventoryItem(InventoryItem item) throws SQLException {
        try  {
            String itemQuery = "UPDATE inventory_items SET name = ?, quantity = ?, last_update = TO_DATE(?, 'YYYYMMDD') WHERE item_id = ?";
            PreparedStatement ps = this.dbHandler.getConnection().prepareStatement(itemQuery);
            ps.setString(1, item.getName());
            ps.setInt(2, item.getQuantity());
            ps.setString(3, item.getLastUpdated());
            ps.setInt(4, item.getItemId());
            
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
}
