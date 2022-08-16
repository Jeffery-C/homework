package com.systex.homework.controller.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private int id;
    private String waiter;
    private int totalPrice;
    private List<MealResponse> mealList;
}
