package com.systex.practice.controller;

import com.systex.practice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ScheduledTasks {

    private static final long SECOND = 1000L;

    private static final long MINUTE = 60L * SECOND;

    @Autowired
    private StockService stockService;

    // @Scheduled(fixedDelay = 60*MINUTE)
    public void simulateUpdateStockPrice() {
        this.stockService.simulateUpdateStockPrice();
    }
}
