package com.logistics.demo.models;

public class ShipmentItem {

    protected int shipmentId;
    protected int itemId;
    protected int quantity;

    public ShipmentItem(int shipmentId, int itemId, int quantity) {
        this.shipmentId = shipmentId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getShipmentId() {
        return this.shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
