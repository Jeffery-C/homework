package com.systex.practice.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systex.practice.model.entity.MSTMB;
import com.systex.practice.model.entity.TCNUD;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.systex.practice.service.Tool.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StockDetailResult extends Result {

    private String tradeDate;
    private String docSeq;
    private String stock;
    private String stockName;
    private BigDecimal buyPrice;
    private BigDecimal nowPrice;
    private BigDecimal qty;
    private BigDecimal remainQty;
    private BigDecimal fee;
    private BigDecimal cost;
    private BigDecimal marketValue;
    private BigDecimal unrealProfit;
    private BigDecimal rateOfProfit;

    public String getRateOfProfit() {
        return new DecimalFormat("#.00%").format(this.rateOfProfit);
    }

    @JsonIgnore
    public BigDecimal getRateOfProfitWithBigDecimal() {
        return this.rateOfProfit;
    }

    public static class StockDetailResultBuilder {

        public StockDetailResultBuilder setAllField(TCNUD tcnud, MSTMB mstmb) {
            this.tradeDate = tcnud.getTradeDate();
            this.docSeq = tcnud.getDocSeq();
            this.stock = tcnud.getStock();
            this.stockName = mstmb.getStockName();
            this.buyPrice = tcnud.getPrice();
            this.nowPrice = mstmb.getCurPrice();
            this.qty = tcnud.getQty();
            this.remainQty = tcnud.getRemainQty();
            this.fee = tcnud.getFee();
            this.cost = tcnud.getCost();
            this.marketValue = calcMarketValue(this.nowPrice, this.remainQty);
            this.unrealProfit = calcUnrealProfit(this.cost, this.nowPrice, this.remainQty);
            this.rateOfProfit = calcRateOfProfit(this.cost, this.unrealProfit);
            return this;
        }

        public StockDetailResultBuilder setFieldFormat() {

            this.buyPrice = this.buyPrice.setScale(2, RoundingMode.DOWN);
            this.nowPrice = this.nowPrice.setScale(2, RoundingMode.DOWN);
            this.cost = this.cost.setScale(0, RoundingMode.DOWN);
            this.marketValue = this.marketValue.setScale(0, RoundingMode.DOWN);
            this.unrealProfit = this.unrealProfit.setScale(0, RoundingMode.DOWN);

            return this;
        }
    }


}
