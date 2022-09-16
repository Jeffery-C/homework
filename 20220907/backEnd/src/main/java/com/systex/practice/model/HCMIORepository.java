package com.systex.practice.model;

import com.systex.practice.model.entity.HCMIO;
import com.systex.practice.model.entity.HCMIOAndTCNUDCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Repository
public interface HCMIORepository extends JpaRepository<HCMIO, HCMIOAndTCNUDCompositeKey> {

    List<HCMIO> findByCustSeqAndStock(String custSeq, String stock);

    List<HCMIO> findByTradeDateAndBranchNoAndCustSeq(String tradeDate, String branchNo, String custSeq);


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO hcmio (" +
            "TradeDate, BranchNo, CustSeq, DocSeq, Stock, BsType, " +
            "Price, Qty, Amt, Fee, Tax, StinTax, NetAmt, " +
            "ModDate, ModTime, ModUser) VALUES (" +
            ":#{#hcmio.getTradeDate()}, :#{#hcmio.getBranchNo()}, :#{#hcmio.getCustSeq()}, :#{#hcmio.getDocSeq()}, " +
            ":#{#hcmio.getStock()}, :#{#hcmio.getBsType().name()}, " +
            ":#{#hcmio.getPrice()}, :#{#hcmio.getQty()}, :#{#hcmio.getAmt()}, :#{#hcmio.getFee()}, " +
            ":#{#hcmio.getTax()}, :#{#hcmio.getStinTax()}, :#{#hcmio.getNetAmt()}, " +
            ":#{#hcmio.getModDate()}, :#{#hcmio.getModTime()}, :#{#hcmio.getModUser()})",
            nativeQuery = true)
    int insertUsingQuery(@Param(value = "hcmio") HCMIO hcmio);
}
