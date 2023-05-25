package com.ibdb.user.controller;

import com.ibdb.user.dtos.AddUserRequestDTO;
import com.ibdb.user.dtos.UpdateUserRequestDTO;
import com.ibdb.user.dtos.UserDTO;
import com.ibdb.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    public static final String USER_PATH = "/api/v1/users";
    public static final String USER_PATH_ID = USER_PATH + "/{userId}";

    private final UserService userService;

    @GetMapping(USER_PATH)
    public List<UserDTO> getUsers() {
        log.debug("Get all users - in controller");
        return userService.getUsers();
    }

    @GetMapping(USER_PATH_ID)
    public UserDTO getUserById(@PathVariable("userId") Integer userID) {
        log.debug("Get user by Id - in controller");
        return userService.getUserById(userID).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping(USER_PATH_ID)
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(USER_PATH)
    public ResponseEntity<HttpStatus> addUser(@RequestBody AddUserRequestDTO addUserRequestDTO) {
        userService.addUser(addUserRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(USER_PATH_ID)
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("userId") Integer userId,
            @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        userService.updateUser(userId, updateUserRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
