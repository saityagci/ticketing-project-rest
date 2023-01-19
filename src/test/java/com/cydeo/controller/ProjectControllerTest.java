package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        token="Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJIdTlVXzQtOVZHZEl2b0Q2V3Z0M2UwMlZIMFBmNXNwZ3dJS0xJQkNOcHo0In0.eyJleHAiOjE2NzQxNjYxMjMsImlhdCI6MTY3NDE2NTgyMywianRpIjoiMjBiNDdjOTYtY2YzNS00MTgzLWIzMWYtNDdkMjZiZDY3MTdlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2N5ZGVvLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIzNzY5MGJlZi0xNzk5LTQ5N2QtYmQ2ZC0wOWJhOGJkYWJkZDQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjhiNzg5M2E2LTg2NTMtNDcxMC04ZjQ2LTA5M2E5MDA1OTgzZiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtY3lkZW8tZGV2IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIkFkbWluIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiOGI3ODkzYTYtODY1My00NzEwLThmNDYtMDkzYTkwMDU5ODNmIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJvenp5IG96enkiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJvenp5IiwiZ2l2ZW5fbmFtZSI6Im96enkiLCJmYW1pbHlfbmFtZSI6Im96enkifQ.kU_Up_mrzw92CDQpxRG6ahWSeywSiNl3aYBWGERrBLsUYBC63FxpBFUmbDzwWawthvousNmSJPYX8_hCcbz5ofhHMvtMzpq68tq74pUuimQovAJeLIOBbmbXF_9Fe_auLq1-n31-CPPbpYAVw0uxtvh4BlPhBhJkJCdOUlbmrWszgv29_S4uOdawQyHkjUznc5TSQBAp11kKKdCwKMiQmgZMUAmWnc4iE0SZFOuO-h8AkBBGTfH3Gguyec6a9v5UAW3ib9g1ncerr_ptrXebrNbfZMEV4GqxLs2Sc00mixwVCKQ_A_7mD9Yh2M2YJZgwL6KmZ8ktikZJQKS44VOlDA";
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
                .andExpect(status().isOk());

    }



}