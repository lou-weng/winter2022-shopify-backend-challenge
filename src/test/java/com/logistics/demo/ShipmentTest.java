package com.logistics.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.Arrays;

import com.logistics.demo.models.InventoryItem;
import com.logistics.demo.models.Shipment;
import com.logistics.demo.services.InventoryItemService;
import com.logistics.demo.services.ShipmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ShipmentTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipmentService shipmentService;

    @MockBean
    private InventoryItemService inventoryItemService;

    private Shipment shipment1;
    private Shipment shipment2;
    private InventoryItem item;

    private final String ENDPOINT_ROUTE = "/shipments/";

    @BeforeEach
    public void setup() {
        shipment1 = new Shipment(0, "2022-05-12", "China");
        shipment2 = new Shipment(1, "2022-06-12", "USA");
        item = new InventoryItem(0, "Hibiscus", 200, "2022-05-12");
    }

    @Test
    public void getAllShipments_IfFound_ShouldReturnAllShipments() throws Exception {
        Mockito.when(shipmentService.getAllShipments()).thenReturn(Arrays.asList(shipment1, shipment2));

        mockMvc.perform(get(ENDPOINT_ROUTE + "getAll"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].shipmentId", is(0)))
            .andExpect(jsonPath("$[1].shipmentId", is(1)));
    }

    @Test
    public void getAllShipments_IfNoItems_ShouldReturnEmpty() throws Exception {
        Mockito.when(shipmentService.getAllShipments()).thenReturn(Arrays.asList());

        mockMvc.perform(get(ENDPOINT_ROUTE + "getAll"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createShipment_IfExists_ShouldFail() throws Exception {
        Mockito.doThrow(new SQLException()).when(shipmentService).createShipment(shipment1);
    
        mockMvc.perform(get(ENDPOINT_ROUTE + "create"))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deleteShipment_IfExists_ShouldFail() throws Exception {
        Mockito.doThrow(new SQLException()).when(shipmentService).deleteShipment(shipment1.getShipmentId());
    
        mockMvc.perform(get(ENDPOINT_ROUTE + "delete/0"))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void addQuantityTooLow_ShouldFail() throws Exception {
        Mockito.when(inventoryItemService.getInventoryItem(item.getItemId())).thenReturn(new InventoryItem(0, "", 5, ""));

        mockMvc.perform(get(ENDPOINT_ROUTE + "addToShipment/0"))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void addQuantityHigh_ShouldSucceed() throws Exception {
        Mockito.when(inventoryItemService.getInventoryItem(item.getItemId())).thenReturn(new InventoryItem(0, "", 5000, ""));

        mockMvc.perform(get(ENDPOINT_ROUTE + "addToShipment/0"))
            .andExpect(status().isMethodNotAllowed());
    }
}
