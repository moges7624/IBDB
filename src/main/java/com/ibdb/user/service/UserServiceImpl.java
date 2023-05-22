package com.ibdb.user.service;

import com.ibdb.user.dtos.AddUserRequestDTO;
import com.ibdb.user.dtos.UpdateUserRequestDTO;
import com.ibdb.user.dtos.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final Map<Integer, UserDTO> userMap;
    public  UserServiceImpl() {
        this.userMap = new HashMap<>();

        UserDTO user1 = UserDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .password("123456")
                .build();

        UserDTO user2 = UserDTO.builder()
                .id(2)
                .firstName("Janet")
                .lastName("Jensen")
                .email("janet@gmail.com")
                .password("123456")
                .build();

        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
    }
    @Override
    public List<UserDTO> getUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public Optional<UserDTO> getUserById(Integer userId) {
        return Optional.of(userMap.get(userId));
    }

    @Override
    public void deleteUser(Integer id) {
        userMap.remove(id);

    }

    @Override
    public UserDTO addUser(AddUserRequestDTO addUserRequestDTO) {
        UserDTO savedUser = UserDTO.builder()
                .id(addUserRequestDTO.getId())
                .firstName(addUserRequestDTO.getFirstName())
                .lastName(addUserRequestDTO.getLastName())
                .email(addUserRequestDTO.getEmail())
                .password(addUserRequestDTO.getPassword())
                .build();

        userMap.put(savedUser.getId(), savedUser);
        return  savedUser;
    }

    @Override
    public void updateUser(Integer userId, UpdateUserRequestDTO updateUserRequestDTO) {
        UserDTO existingUser = userMap.get(userId);

        if (StringUtils.hasText(updateUserRequestDTO.getFirstName())) {
            existingUser.setFirstName(updateUserRequestDTO.getFirstName());
        }

        if (StringUtils.hasText(updateUserRequestDTO.getLastName())) {
            existingUser.setLastName(updateUserRequestDTO.getLastName());
        }

        if (StringUtils.hasText(updateUserRequestDTO.getEmail())) {
            existingUser.setEmail(updateUserRequestDTO.getEmail());
        }
    }
}
