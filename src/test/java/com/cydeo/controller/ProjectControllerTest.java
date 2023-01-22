package com.cydeo.controller;

import com.cydeo.dto.*;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {
    @Autowired
    private MockMvc mvc;

    static UserDTO userDTO;
    static ProjectDTO projectDTO;
    static String token;


    @BeforeAll
     static void setup(){

        token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJIdTlVXzQtOVZHZEl2b0Q2V3Z0M2UwMlZIMFBmNXNwZ3dJS0xJQkNOcHo0In0.eyJleHAiOjE2NzQzOTg5MDksImlhdCI6MTY3NDM2MjkwOSwianRpIjoiM2IxNzYxNjEtNDBlMC00ZTQxLThkNWYtZmExZjE1YzY2MWIyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2N5ZGVvLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIzNzY5MGJlZi0xNzk5LTQ5N2QtYmQ2ZC0wOWJhOGJkYWJkZDQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6Ijg3ZGNjODc4LTI4YTMtNDkwMS05NjE4LTc2YjdjYmM2ZmNhMCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtY3lkZW8tZGV2IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIkFkbWluIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiODdkY2M4NzgtMjhhMy00OTAxLTk2MTgtNzZiN2NiYzZmY2EwIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJvenp5IG96enkiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJvenp5IiwiZ2l2ZW5fbmFtZSI6Im96enkiLCJmYW1pbHlfbmFtZSI6Im96enkifQ.OJ0IcVGjZw3I3JlywxR_8HGS1YmuPg_HmOcbgLRJ8OyiPFCL3574uZPPrxaWE2woWnbU2i0BMZORpwBsxT-XFxD9tGs87pLRDu8xNrBKmHtHGSm_rqlA_lKmwWsjDhJ9rbJ3wclSAdTv7xEXRUlhTG5bvO6HbwQ5Jg5tAjgbs7iRomZqDs2AXOgDmn6hBmAayR8zneyLi6VW6cO4WDtOZFZ-H3sDgRFnm_qHcsufCXwpJwE4PkdIYUEl_5gnZyrD-WjbgFY2alFaz9TSJvKyiwnZIP_pg8wfJToZ-8O56bc-H4KG_FCPA6OcBFo2-wgXHBmoU1BvoyRhNIx9f-wybA";
        userDTO= UserDTO.builder()
                .id(2L)
                .firstName("mike")
                .lastName("smith")
                .userName("mike")
                .passWord("abc1")
                .confirmPassWord("abc1")
                .role(new RoleDTO(2L,"Manager"))
                .gender(Gender.MALE)
                .build();
        projectDTO=ProjectDTO.builder()
                .projectCode("Api1")
                .projectName("Api-ozzy")
                .assignedManager(userDTO)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .projectDetail("Api test")
                .projectStatus(Status.OPEN)
                .build();

    }
    @Test
    public void givenNoToken_whenGetRequest() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/project"))
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void givenToken_whenGetRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/project")
                .header("Authorization",token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].projectCode")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].assignedManager.userName")
                        .isNotEmpty());
    }
    @Test
    public void givenToken_createProject() throws Exception {

      mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/project")
                .header("Authorization", token)
                .content(toJsonString(projectDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


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
    @Test
    public void givenToken_updateProject() throws Exception{
        projectDTO.setProjectName("Api-cydeo");
        mvc.perform(MockMvcRequestBuilders
                .put("/api/v1/project")
                .header("Authorization",token)
                .content(toJsonString(projectDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Project is successfully updated"));
    }
    @Test
    public void givenToken_deleteProject() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/project/"+ projectDTO.getProjectCode())
                .header("Authorization",token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("Project is successfully deleted"));

    }
    private static String makeRequest() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", "ticketing-app");
        map.add("client_secret", "QCWpIZZLsKWMKtiKemPMhS6lO4QJXiTd");
        map.add("username", "mike");
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



}