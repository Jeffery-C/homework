package com.systex.practice.model;

import com.systex.practice.model.entity.HCMIO;
import com.systex.practice.model.entity.HCMIOAndTCNUDCompositeKey;
import com.systex.practice.model.entity.TCNUD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Repository
public interface TCNUDRepository extends JpaRepository<TCNUD, HCMIOAndTCNUDCompositeKey> {

    List<TCNUD> findByCustSeqAndStock(String custSeq, String stock);

    List<TCNUD> findByBranchNoAndCustSeqAndStock(String branchNo, String custSeq, String stock);

    List<TCNUD> findByBranchNoAndCustSeq(String branchNo, String custSeq);

    List<TCNUD> findByCustSeq(String custSeq);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tcnud (" +
            "TradeDate, BranchNo, CustSeq, DocSeq, Stock, " +
            "Price, Qty, RemainQty, Fee, Cost, " +
            "ModDate, ModTime, ModUser) VALUES (" +
            ":#{#tcnud.getTradeDate()}, :#{#tcnud.getBranchNo()}, :#{#tcnud.getCustSeq()}, :#{#tcnud.getDocSeq()}, " +
            ":#{#tcnud.getStock()}, " +
            ":#{#tcnud.getPrice()}, :#{#tcnud.getQty()}, :#{#tcnud.getRemainQty()}, :#{#tcnud.getFee()}, " +
            ":#{#tcnud.getCost()}, " +
            ":#{#tcnud.getModDate()}, :#{#tcnud.getModTime()}, :#{#tcnud.getModUser()})",
            nativeQuery = true)
    int insertUsingQuery(@Param(value = "tcnud") TCNUD tcnud);


}
