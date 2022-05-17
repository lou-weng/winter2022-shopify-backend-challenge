package com.logistics.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.sql.SQLException;
import java.util.Arrays;

import com.logistics.demo.models.InventoryItem;
import com.logistics.demo.services.InventoryItemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryItemTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryItemService inventoryItemService;

    private InventoryItem item1;
    private InventoryItem item2;

    private final String ENDPOINT_ROUTE = "/inventory-items/";

    @BeforeEach
    public void setup() {
        item1 = new InventoryItem(0, "Hibiscus", 200, "2022-05-12");
        item2 = new InventoryItem(1, "Lily", 200, "2022-05-12");
    }

    @Test
    public void getAllInventoryItems_IfFound_ShouldReturnAllItems() throws Exception {
        Mockito.when(inventoryItemService.getAllInventoryItems()).thenReturn(Arrays.asList(item1, item2));

        mockMvc.perform(get(ENDPOINT_ROUTE + "getAll"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].itemId", is(0)))
            .andExpect(jsonPath("$[1].itemId", is(1)));
    }

    @Test
    public void getAllInventoryItems_IfNoItems_ShouldReturnEmpty() throws Exception {
        Mockito.when(inventoryItemService.getAllInventoryItems()).thenReturn(Arrays.asList());

        mockMvc.perform(get(ENDPOINT_ROUTE + "getAll"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void createInventoryItem_IfExists_ShouldFail() throws Exception {
        Mockito.doThrow(SQLException.class).when(inventoryItemService).createInventoryItem(item1);
    
        mockMvc.perform(get(ENDPOINT_ROUTE + "create"))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test 
    public void deleteInventoryItem_IfNotExists_ShouldFail() throws Exception {
        Mockito.doThrow(SQLException.class).when(inventoryItemService).deleteInventoryItem(item1.getItemId());

        mockMvc.perform(get(ENDPOINT_ROUTE + "delete/0"))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test 
    public void updateInventoryItem_IfNotExists_ShouldFail() throws Exception {
        Mockito.doThrow(SQLException.class).when(inventoryItemService).updateInventoryItem(item1.getItemId(), item1);

        mockMvc.perform(get(ENDPOINT_ROUTE + "update/0"))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void getOneSingleItem_IfExists_ShouldSucceed() throws Exception {
        Mockito.when(inventoryItemService.getInventoryItem(item1.getItemId())).thenReturn(item1);

        mockMvc.perform(get(ENDPOINT_ROUTE + "get/0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }
    
}
