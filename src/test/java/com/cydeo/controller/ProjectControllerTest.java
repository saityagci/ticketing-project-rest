package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        token="Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJIdTlVXzQtOVZHZEl2b0Q2V3Z0M2UwMlZIMFBmNXNwZ3dJS0xJQkNOcHo0In0.eyJleHAiOjE2NzQyMDg3NjEsImlhdCI6MTY3NDE3Mjc2MSwianRpIjoiNWViYzUzOTUtNTZjNS00YzM1LTg0ZDMtZGU1MjVjYmQwNTc4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2N5ZGVvLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI2OWFlMzM2Zi0wOGQ0LTQyMzctYmM5Mi1jMmI2OTY2MzkyMDMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6Ijg2ZDc2MzM1LWM1MWItNGMzZS1hNmMyLTcwYTNkYjY4NmFjZiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtY3lkZW8tZGV2IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIk1hbmFnZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiI4NmQ3NjMzNS1jNTFiLTRjM2UtYTZjMi03MGEzZGI2ODZhY2YiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Im1pa2Ugc21pdGgiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtaWtlIiwiZ2l2ZW5fbmFtZSI6Im1pa2UiLCJmYW1pbHlfbmFtZSI6InNtaXRoIn0.IWE_GsACWuM9Kz1xohqcNFgTp_6TSUuwTjoczil1q0-nrWAFN3ArN0Lrtm5zozIZzUY0OQ6H65dxV5f78wq8yfgNB9YRJdUoG9erEDKnL8CiTiTIt86EL-7t6RxxSLo-hKKNv2f0k3RmK8uY0STmWZf2A0kRcL27SyOIYAeGJgXnFWCUM9zCCfPPEVnAytOQl-qizEKUDdBD3yZQl7UNSN22JqZuK6ImlhPyk6jqtgjcIZfeq6b4wcwOvFCnJnzfLRSVRXPG1TA9WtrJ7Xu-_PaxQ2AF7GYzdBnNXKarf2SCV4b-NVqAdhY-q-lnjiZ4uJAf9TCyGbNl3_yajstmHw";
        userDTO= UserDTO.builder()
                .id(2L)
                .firstName("ozzy")
                .lastName("ozzy")
                .userName("ozzy")
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
    public void givenToken_createProject() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/project")
                .header("Authorization",token)
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



}