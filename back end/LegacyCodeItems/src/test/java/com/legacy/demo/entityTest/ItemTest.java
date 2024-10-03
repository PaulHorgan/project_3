package com.legacy.demo.entityTest;

import com.legacy.demo.entities.Item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql", "classpath:items.sql"})
public class ItemTest{

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testGetItemById() throws Exception {
        Item outcome = new Item (3, "other purple pen", 1.23, 12, "Broken URL");
        String resbody = this.mapper.writeValueAsString(outcome);
        ResultMatcher checkStatus = MockMvcResultMatchers.status().isOk();
        ResultMatcher checkBody = MockMvcResultMatchers.content().json(resbody);
        mvc.perform(MockMvcRequestBuilders.get("/item/get/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(checkStatus)
                .andExpect(checkBody);
    }

    @Test
    void createItem() throws Exception {
        Item newItem = new Item(4, "Blue Pen", 1.02, 105, "uselessURL");
        String newItemAsJson = this.mapper.writeValueAsString(newItem);

        RequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/item/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newItemAsJson);

        this.mvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.name").value("Blue Pen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.price").value(1.02))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.quantity").value(105))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.imageUrl").value("uselessURL"));
    }

   @Test
    void deleteKnownItem() throws Exception {


        mvc.perform(MockMvcRequestBuilders.delete("/item/remove/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());



}

    @Test
    void testItemUpdate() throws Exception {
        String updateJson = "{"
                + "\"id\": 1,"
                + "\"name\": \"purple pencil\","
                + "\"price\": 1.99,"
                + "\"imageUrl\": \"nonsense\","
                + "\"quantity\": 2"
                + "}";
        mvc.perform(MockMvcRequestBuilders.patch("/item/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("purple pencil"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1.99))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl").value("nonsense"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(2));
    }
}
