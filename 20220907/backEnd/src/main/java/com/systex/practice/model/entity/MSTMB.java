package com.systex.practice.model.entity;

import com.systex.practice.model.entity.type.Currency;
import com.systex.practice.model.entity.type.MarketType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MSTMB {

    @Id
    @Column(name = "Stock", columnDefinition = "char(6)", nullable = false)
    private String stock;

    @Column(name = "StockName", length = 20, nullable = false)
    private String stockName;

    @Enumerated(EnumType.STRING)
    @Column(name = "MarketType", columnDefinition = "char(1)")
    private MarketType marketType;

    @Column(name = "CurPrice", precision = 10, scale = 4)
    private BigDecimal curPrice;

    @Column(name = "RefPrice", precision = 10, scale = 4)
    private BigDecimal refPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "Currency", columnDefinition = "char(3)")
    private Currency currency;

    @Column(name = "ModDate", columnDefinition = "char(8)")
    private String modDate;

    @Column(name = "ModTime", columnDefinition = "char(6)")
    private String modTime;

    @Column(name = "ModUser", columnDefinition = "char(10)")
    private String modUser;

}
