package com.pdv.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    @NotBlank(message = "O campo descrição é obrigatório")
    private String description;
    @NotNull(message = "O campo preço é obrigatório")
    private BigDecimal price;
    @NotNull(message = "O campo quantity é obrigatório")
    private int quantity;

}
