package com.systex.homework.controller.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    @NotEmpty(message = "waiter cannot be empty")
    private String waiter;
    private List<MealRequest> mealList;
}
