package com.legacy.demoTEST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.legacy.demo.Issue;
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
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql", "classpath:issues.sql"})
public class IssueTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createIssue() throws Exception{
        Issue newIssue = new Issue(5, "new issue","Not from sql", "logged");
        String newItemAsJson = this.mapper.writeValueAsString(newIssue);

        RequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/Issues/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newItemAsJson);

        this.mvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.description").value("new issue"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.location").value("Not from sql"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.status").value("logged"));

    }




}
