package com.systex.practice.model.entity;

import com.systex.practice.model.entity.type.BsType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(HCMIOAndTCNUDCompositeKey.class)
@Entity
public class HCMIO {

    @Id
    @Column(name = "TradeDate", columnDefinition = "char(8)", nullable = false)
    private String tradeDate;

    @Id
    @Column(name = "BranchNo", columnDefinition = "char(4)", nullable = false)
    private String branchNo;

    @Id
    @Column(name = "CustSeq", columnDefinition = "char(7)", nullable = false)
    private String custSeq;

    @Id
    @Column(name = "DocSeq", columnDefinition = "char(5)", nullable = false)
    private String docSeq;

    @Column(name = "Stock", columnDefinition = "char(6)", nullable = false)
    private String stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "BsType", columnDefinition = "char(1)", nullable = false)
    private BsType bsType;

    @Column(name = "Price", precision = 10, scale = 4)
    private BigDecimal price;

    @Column(name = "Qty", precision = 9, scale = 0)
    private BigDecimal qty;

    @Column(name = "Amt", precision = 16, scale = 2)
    private BigDecimal amt;

    @Column(name = "Fee", precision = 8, scale = 0)
    private BigDecimal fee;

    @Column(name = "Tax", precision = 8, scale = 0)
    private BigDecimal tax;

    @Column(name = "StinTax", precision = 8, scale = 0)
    private BigDecimal stinTax;

    @Column(name = "NetAmt", precision = 16, scale = 2)
    private BigDecimal netAmt;

    @Column(name = "ModDate", columnDefinition = "char(8)")
    private String modDate;

    @Column(name = "ModTime", columnDefinition = "char(6)")
    private String modTime;

    @Column(name = "ModUser", columnDefinition = "char(10)")
    private String modUser;

}
