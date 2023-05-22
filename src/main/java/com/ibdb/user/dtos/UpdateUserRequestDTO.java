package com.ibdb.user.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserRequestDTO {
    private  String firstName;
    private  String lastName;
    private  String email;
}
