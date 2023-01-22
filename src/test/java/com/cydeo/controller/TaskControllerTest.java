package com.cydeo.controller;

import com.cydeo.dto.ResponseDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    @Autowired
    private MockMvc mvc;

    static TaskDTO taskDTO;
    static String token;

    static void setup() {
        token = "Bearer" + makeRequest();

    }
    @Test
    public void givenNoToken_whenGetRequest() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/task"))
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void givenToken_whenGetRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/task")
                        .header("Authorization",token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void givenToken_createProject() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/user")
                .header("Authorization",token)
                .content(toJsonString(taskDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }
//                .andExpect(MockMvcResultMatchers.jsonPath("message")
//                .value("User is successfully created"))
//                .andExpect(status().isOk());}


    private static String makeRequest() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", "ticketing-app");
        map.add("client_secret", "QCWpIZZLsKWMKtiKemPMhS6lO4QJXiTd");
        map.add("username", "ozzy");
        map.add("password", "abc1");
        map.add("scope", "openid");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<ResponseDTO> response =
                restTemplate.exchange("http://localhost:8080/auth/realms/cydeo-dev/protocol/openid-connect/token",
                        HttpMethod.POST,
                        entity,
                        ResponseDTO.class);

        if (response.getBody() != null) {
            return response.getBody().getAccess_token();
        }

        return "";

    }
    private static String toJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

