package com.systex.homework.controller.dto.request;

import com.systex.homework.controller.dto.request.validation.ValidateInteger;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseMealRequest {
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @ValidateInteger(message = "price must be a integer")
    @NotNull(message = "price cannot be null")
    @Min(value = 0, message = "price must be a positive integer")
    private Double price;

    @NotEmpty(message = "description cannot be empty")
    private String description;

    public int getPrice() {
        return this.price.intValue();
    }
}
