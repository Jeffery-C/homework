package com.systex.practice.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import static com.systex.practice.service.Tool.calcRateOfProfit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockSummaryResult extends Result {

    private String stock;
    private String stockName;
    private BigDecimal nowPrice;
    private BigDecimal sumRemainQty;
    private BigDecimal sumFee;
    private BigDecimal sumCost;
    private BigDecimal sumMarketValue;
    private BigDecimal sumUnrealProfit;
    private BigDecimal sumRateOfProfit;
    private List<StockDetailResult> detailList;


    public String getSumRateOfProfit() {
        return new DecimalFormat("#.00%").format(this.sumRateOfProfit);
    }

    @JsonIgnore
    public BigDecimal getSumRateOfProfitWithBigDecimal() {
        return this.sumRateOfProfit;
    }

    public StockSummaryResult(List<StockDetailResult> detailList) {
        this.stock = detailList.get(0).getStock();
        this.stockName = detailList.get(0).getStockName();
        this.nowPrice = detailList.get(0).getNowPrice();
        this.sumRemainQty = BigDecimal.ZERO;
        this.sumFee = BigDecimal.ZERO;
        this.sumCost = BigDecimal.ZERO;
        this.sumMarketValue = BigDecimal.ZERO;
        this.sumUnrealProfit = BigDecimal.ZERO;
        this.detailList = detailList;

        this.detailList.stream().forEach(stockDetailResult -> {
            this.addSumRemainQty(stockDetailResult.getRemainQty());
            this.addSumFee(stockDetailResult.getFee());
            this.addSumCost(stockDetailResult.getCost());
            this.addSumMarketValue(stockDetailResult.getMarketValue());
            this.addSumUnrealProfit(stockDetailResult.getUnrealProfit());
        });

        this.sumRateOfProfit = calcRateOfProfit(this.sumCost, this.sumUnrealProfit);

    }

    public void addSumRemainQty(BigDecimal remainQty) {
        this.sumRemainQty = this.sumRemainQty.add(remainQty);
    }

    public void addSumFee(BigDecimal fee) {
        this.sumFee = this.sumFee.add(fee);
    }

    public void addSumCost(BigDecimal cost) {
        this.sumCost = this.sumCost.add(cost);
    }

    public void addSumMarketValue(BigDecimal marketValue) {
        this.sumMarketValue = this.sumMarketValue.add(marketValue);
    }

    public void addSumUnrealProfit(BigDecimal unrealProfit) {
        this.sumUnrealProfit = this.sumUnrealProfit.add(unrealProfit);
    }

}
