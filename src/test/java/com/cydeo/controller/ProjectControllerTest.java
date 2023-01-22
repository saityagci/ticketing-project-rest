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

        token="Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJIdTlVXzQtOVZHZEl2b0Q2V3Z0M2UwMlZIMFBmNXNwZ3dJS0xJQkNOcHo0In0.eyJleHAiOjE2NzQyMzMzOTAsImlhdCI6MTY3NDE5NzM5MCwianRpIjoiMjY3ODRmMzEtNzljOS00ZmZjLWI3MTQtMzU0N2QwYjk4NmFkIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2N5ZGVvLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI2OWFlMzM2Zi0wOGQ0LTQyMzctYmM5Mi1jMmI2OTY2MzkyMDMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjFlNDkwNDk2LTEwYWItNGRhZS1iMTZiLTFjZTk0MjJjNzYxMyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtY3lkZW8tZGV2IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIk1hbmFnZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiIxZTQ5MDQ5Ni0xMGFiLTRkYWUtYjE2Yi0xY2U5NDIyYzc2MTMiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Im1pa2Ugc21pdGgiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtaWtlIiwiZ2l2ZW5fbmFtZSI6Im1pa2UiLCJmYW1pbHlfbmFtZSI6InNtaXRoIn0.lvchYOG7t3i9Z3-OASfR2aOCZ5FSQOHxEhvs39MpLa3OU0K7qe-e8rlE5PrEcsTGJoqaylcnGrcQ9NT56WwJsCn1NSTPBnH00-QOrSuwAEDIlsalczxxDFF8bkV7qve6soqSOuS4od7dhGRAEYDhmGMZj-j6L3TOTo1DFTtjFj7ZvLr63bnj9t1VY-Qq9_vk2xQXA6NGl1J8D59WVx9nc9253ZWv23NtWIBXy_t2cMAtab1OSL4Ier0BncCE-EoH815pLJ7mA00R-_JPasXjtL2sMZa4kNoGYrODlt4A0Z_Jb0Iwqps_0_TLF0FX6jQm7_u2Byb9GkqWQXRCWw7M9w";
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