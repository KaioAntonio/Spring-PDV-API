package com.pdv.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;
    @NotBlank(message = " O campo nome é obrigatório")
    private String name;
    @NotBlank(message = "O campo Username obrigatorio!")
    private String username;
    @NotBlank(message = "O campo senha é obrigatorio!")
    private String password;
    private boolean isEnabled;

}
