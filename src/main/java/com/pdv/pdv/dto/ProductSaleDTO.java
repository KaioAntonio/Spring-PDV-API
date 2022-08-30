package com.pdv.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleDTO {

    @NotNull(message = "O campo product id é obrigatório")
    private long productid;
    @NotNull(message = "O campo quantity é obrigatório")
    private int quantity;

}
