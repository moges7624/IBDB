package com.ibdb.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.hamcrest.core.Is.is;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibdb.user.dtos.AddUserRequestDTO;
import com.ibdb.user.dtos.UpdateUserRequestDTO;
import com.ibdb.user.dtos.UserDTO;
import com.ibdb.user.service.UserService;
import com.ibdb.user.service.UserServiceImpl;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  UserService userService;

  UserServiceImpl userServiceImpl;

  @Captor
  ArgumentCaptor<Integer> intArgumentCaptor;

  @BeforeEach
  void setUp() {
    userServiceImpl = new UserServiceImpl();
  }

  @Test
  void testAddUser() throws Exception {
    UserDTO userDTO = userServiceImpl.getUsers().get(0);
    userDTO.setId(null);

    given(userService.addUser(any(AddUserRequestDTO.class))).willReturn(userServiceImpl.getUsers().get(0));

    mockMvc.perform(
        post(UserController.USER_PATH)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO)))
        .andExpect(status().isCreated());
  }

  @Test
  void testgetUsers() throws Exception {
    given(userService.getUsers()).willReturn(userServiceImpl.getUsers());

    mockMvc.perform(
        get(UserController.USER_PATH)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()", is(userService.getUsers().size())));
  }

  @Test
  void getUserById() throws Exception {
    UserDTO testUser = userServiceImpl.getUsers().get(0);

    given(userService.getUserById(testUser.getId())).willReturn(Optional.of(testUser));

    mockMvc.perform(
        get(UserController.USER_PATH_ID, testUser.getId())
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(testUser.getId())))
        .andExpect(jsonPath("$.firstName", is(testUser.getFirstName())));
  }

  @Test
  void testUpdateUser() throws Exception {
    UserDTO userDTO = userServiceImpl.getUsers().get(0);

    Map<String, Object> userMap = new HashMap<>();
    userMap.put("firstName", "Johnyy");

    mockMvc.perform(
        put(UserController.USER_PATH_ID, userDTO.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userMap)))
        .andExpect(status().isNoContent());

    ArgumentCaptor<UpdateUserRequestDTO> updateUserArgumentCaptor = ArgumentCaptor.forClass(UpdateUserRequestDTO.class);
    verify(userService).updateUser(intArgumentCaptor.capture(), updateUserArgumentCaptor.capture());
    assertThat(userDTO.getId()).isEqualTo(intArgumentCaptor.getValue());
    assertThat(userMap.get("firstName")).isEqualTo(updateUserArgumentCaptor.getValue().getFirstName());
  }

  @Test
  void testDeleteUser() throws Exception {
    UserDTO userDTO = userServiceImpl.getUsers().get(0);

    mockMvc.perform(
        delete(UserController.USER_PATH_ID, userDTO.getId())
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    verify(userService).deleteUser(intArgumentCaptor.capture());

    assertThat(userDTO.getId()).isEqualTo(intArgumentCaptor.getValue());
  }
}
