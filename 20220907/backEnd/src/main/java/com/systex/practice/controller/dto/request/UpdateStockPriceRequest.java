package com.systex.practice.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UpdateStockPriceRequest {

    @NotBlank(message = "stock cannot be empty")
    @Length(max = 6, message = "The string length of stock must be less than 6")
    private String stock;

    @NotNull(message = "price cannot be null")
    @DecimalMin(value = "0", inclusive = false, message = "price must be greater than 0")
    @Digits(integer = 10, fraction = 4, message = "digits of price is not correct")
    private BigDecimal price;
}
