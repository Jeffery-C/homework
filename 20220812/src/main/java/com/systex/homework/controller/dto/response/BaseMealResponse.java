package com.systex.homework.controller.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseMealResponse {
    private int id;
    private String name;
    private int price;
    private String description;
}
