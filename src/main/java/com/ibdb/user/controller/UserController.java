package com.ibdb.user.controller;

import com.ibdb.user.Model.User;
import com.ibdb.user.dtos.AddUserRequestDTO;
import com.ibdb.user.dtos.UpdateUserRequestDTO;
import com.ibdb.user.dtos.UserDTO;
import com.ibdb.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("{userId}")
    public UserDTO getUserById(@PathVariable("userId") Integer userID) {
        return userService.getUserById(userID).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("{userID}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userID") Integer userID) {
        userService.deleteUser(userID);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addUser(@RequestBody AddUserRequestDTO addUserRequestDTO) {
        userService.addUser(addUserRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{userId}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("userId") Integer userId,
                                                 @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        userService.updateUser(userId, updateUserRequestDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
