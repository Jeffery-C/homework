package com.systex.homework.controller.dto.request;

import com.systex.homework.controller.dto.request.validation.ValidateInteger;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealRequest {

    @NotEmpty(message = "name cannot be empty")
    private String name;

    @ValidateInteger(message = "quantity must be a integer")
    @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be a positive integer")
    private Double quantity;

    public int getQuantity() {
        return this.quantity.intValue();
    }
}
