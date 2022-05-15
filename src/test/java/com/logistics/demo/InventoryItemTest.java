package com.logistics.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;

import com.logistics.demo.models.InventoryItem;
import com.logistics.demo.services.InventoryItemService;

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

    @Test
    public void getAllInventoryItems_IfFound_ShouldReturnAllItems() throws Exception {
        InventoryItem item1 = new InventoryItem(0, "Hibiscus", 200, "2022-05-12");
        InventoryItem item2 = new InventoryItem(1, "Lily", 200, "2022-05-12");

        Mockito.when(inventoryItemService.getAllInventoryItems()).thenReturn(Arrays.asList(item1, item2));

        mockMvc.perform(get("/inventory-items/getAll"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].itemId", is(0)))
            .andExpect(jsonPath("$[1].itemId", is(1)));
    }

    @Test
    public void getAllInventoryItems_IfNoItems_ShouldReturnEmpty() throws Exception {
        Mockito.when(inventoryItemService.getAllInventoryItems()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/inventory-items/getAll"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }
    
}
