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
        token="Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJIdTlVXzQtOVZHZEl2b0Q2V3Z0M2UwMlZIMFBmNXNwZ3dJS0xJQkNOcHo0In0.eyJleHAiOjE2NzQxNzEzMDksImlhdCI6MTY3NDE3MTAwOSwianRpIjoiN2ZlYTdlOWMtODhlOC00OWQxLWE0MWUtNWRkMmY0YTdhNmI1IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2N5ZGVvLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI2OWFlMzM2Zi0wOGQ0LTQyMzctYmM5Mi1jMmI2OTY2MzkyMDMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6IjY1OWMzMzYxLTBlYzgtNDlmYS04NzczLTM0OThhMTIyZTg1ZSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsImRlZmF1bHQtcm9sZXMtY3lkZW8tZGV2IiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIk1hbmFnZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiI2NTljMzM2MS0wZWM4LTQ5ZmEtODc3My0zNDk4YTEyMmU4NWUiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Im1pa2Ugc21pdGgiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtaWtlIiwiZ2l2ZW5fbmFtZSI6Im1pa2UiLCJmYW1pbHlfbmFtZSI6InNtaXRoIn0.igzAHA3p_nTtRktFz_RfGmIiVmS-brRQLSJaLAyFssk7uWGJU3ZozudF9LAYRiLoFk6FUVFHOaeJjWZkjuSBhFrsgo9YuVS3fUZpU-HH0D64MLDp1r2tGh6eereWKlUSaWgJeQhEpFBUdWCBN6W04I6rv-DtuylYtBhjp0wqOvpxBkln1CvRCP91DJdAsqiKDj9Waxdx_L05l6gEYihqvIh-y4QZol7FM8DjnGJLTF_rLowQ0a81ZMz8DPZF3_fUHq_mplCA5trUIEVXOtMaxTuVt8NlyadxIqLdsvZ94ZWwD_aubiIvlDZpC8MaPsiHgF7n1l2exgy_Q_VJnNXJdQ";
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



}