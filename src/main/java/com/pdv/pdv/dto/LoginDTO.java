package com.pdv.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "O campo login é obrigatório")
    private String username;
    @NotBlank(message = "O campo senha é obrigatório")
    private String password;

}
