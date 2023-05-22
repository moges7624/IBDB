package com.ibdb.user.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private  Integer id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private String password;
}
