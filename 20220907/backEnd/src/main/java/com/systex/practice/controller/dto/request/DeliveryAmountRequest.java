package com.systex.practice.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryAmountRequest {

    @NotEmpty(message = "branchNo cannot be empty")
    private String branchNo;

    @NotEmpty(message = "custSeq cannot be empty")
    private String custSeq;

}
