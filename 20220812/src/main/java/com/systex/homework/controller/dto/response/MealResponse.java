package com.systex.homework.controller.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealResponse {
    private String name;
    private int quantity;
    private int unitPrice;
}
