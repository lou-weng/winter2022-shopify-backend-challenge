package com.logistics.demo.models;

public class InventoryItem {

    protected int itemId;
    protected String name;
    protected int quantity;
    protected String lastUpdated;

    public InventoryItem(int itemId, String name, int quantity, String lastUpdated) {
        this.itemId = itemId;
        this.name = name;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
}
