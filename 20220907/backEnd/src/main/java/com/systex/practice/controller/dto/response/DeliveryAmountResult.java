package com.systex.practice.controller.dto.response;

import com.systex.practice.model.entity.HCMIO;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAmountResult extends Result{

    private BigDecimal Amount;

    public DeliveryAmountResult(List<HCMIO> hcmioList) {
        this.Amount = BigDecimal.ZERO;
        hcmioList.forEach(hcmio -> this.addAmount(hcmio.getNetAmt()));
        this.Amount = this.Amount.negate().setScale(0, RoundingMode.DOWN);
    }

    public void addAmount(BigDecimal amount) {
        this.Amount = this.Amount.add(amount);
    }
}
