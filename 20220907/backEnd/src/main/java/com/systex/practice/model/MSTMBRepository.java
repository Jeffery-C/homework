package com.systex.practice.model;

import com.systex.practice.model.entity.MSTMB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public interface MSTMBRepository extends JpaRepository<MSTMB, String> {

    MSTMB findByStock(String stock);

    @Transactional
    @Modifying
    @Query(value = "UPDATE mstmb SET CurPrice = :price WHERE stock = :stock", nativeQuery = true)
    int updateByStock(@Param(value = "stock") String stock, @Param(value = "price") BigDecimal price);

}
