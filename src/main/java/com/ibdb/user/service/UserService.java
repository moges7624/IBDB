package com.ibdb.user.service;

import com.ibdb.user.dtos.AddUserRequestDTO;
import com.ibdb.user.dtos.UpdateUserRequestDTO;
import com.ibdb.user.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<UserDTO> getUsers();

    public Optional<UserDTO> getUserById(Integer id);

    public void deleteUser(Integer id);

    public UserDTO addUser(AddUserRequestDTO addUserRequestDTO);

    void updateUser(Integer userId, UpdateUserRequestDTO updateUserRequestDTO);
}
