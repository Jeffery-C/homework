package com.systex.practice.controller.dto.request;

import com.systex.practice.controller.validation.CheckProfitInterval;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class FindStockRequest {

    @NotEmpty(message = "branchNo cannot be empty")
    private String branchNo;

    @NotEmpty(message = "custSeq cannot be empty")
    private String custSeq;

    private String stock;

    @CheckProfitInterval
    private ProfitInterval profitInterval;

    @Getter
    @Setter
    @NoArgsConstructor
    public class ProfitInterval {

        private Boolean isPercentage;

        @Digits(integer = 10, fraction = 4, message = "digits of price is not correct")
        private BigDecimal profitMin;

        @Digits(integer = 10, fraction = 4, message = "digits of price is not correct")
        private BigDecimal profitMax;
    }
}
