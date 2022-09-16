package com.systex.practice.controller.dto.request;

import com.systex.practice.controller.validation.CheckDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InsertStockRequest {

    @NotBlank(message = "tradeDate cannot be empty")
    @CheckDate(dateFormat = "yyyyMMdd")
    private String tradeDate;

    @NotBlank(message = "branchNo cannot be empty")
    @Length(max = 4, message = "The string length of branchNo must be less than 4")
    private String branchNo;

    @NotBlank(message = "custSeq cannot be empty")
    @Length(max = 7, message = "The string length of custSeq must be less than 7")
    private String custSeq;

    @NotBlank(message = "docSeq cannot be empty")
    @Length(max = 5, message = "The string length of docSeq must be less than 5")
    private String docSeq;

    @NotBlank(message = "stock cannot be empty")
    @Length(max = 6, message = "The string length of stock must be less than 6")
    private String stock;

    @NotNull(message = "price cannot be null")
    @DecimalMin(value = "0", inclusive = false, message = "price must be greater than 0")
    @Digits(integer = 10, fraction = 4, message = "digits of price is not correct")
    private BigDecimal price;

    @NotNull(message = "qty cannot be null")
    @DecimalMin(value = "0", inclusive = false, message = "qty must be greater than 0")
    @Digits(integer = 9, fraction = 0, message = "digits of qty is not correct")
    private BigDecimal qty;
}
