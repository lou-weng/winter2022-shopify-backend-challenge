package com.logistics.demo.models;

import java.util.List;

public class Shipment {

    protected int shipmentId;
    protected String shipDate;
    protected String destination;
    protected List<ShipmentItem> shipmentItems;

    public Shipment(int shipmentId, String ship_date, String destination) {
        this.shipmentId = shipmentId;
        this.shipDate = ship_date;
        this.destination = destination;
    }

    public Shipment(int shipmentId, String ship_date, String destination, List<ShipmentItem> shipmentItems) {
        this.shipmentId = shipmentId;
        this.shipDate = ship_date;
        this.destination = destination;
        this.shipmentItems = shipmentItems;
    }

    public int getShipmentId() {
        return this.shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getShipDate() {
        return this.shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public List<ShipmentItem> getShipmentItems() {
        return this.shipmentItems;
    }

    public void setShipmentItems(List<ShipmentItem> shipmentItems) {
        this.shipmentItems = shipmentItems;
    }

}
