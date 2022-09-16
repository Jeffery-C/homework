package com.systex.practice.service;

import com.systex.practice.controller.dto.request.DeliveryAmountRequest;
import com.systex.practice.controller.dto.request.FindStockRequest;
import com.systex.practice.controller.dto.request.InsertStockRequest;
import com.systex.practice.controller.dto.request.UpdateStockPriceRequest;
import com.systex.practice.controller.dto.response.DeliveryAmountResult;
import com.systex.practice.controller.dto.response.Result;
import com.systex.practice.controller.dto.response.StockDetailResult;
import com.systex.practice.controller.dto.response.StockSummaryResult;
import com.systex.practice.controller.exception.StatusCode;
import com.systex.practice.controller.exception.StockWebHttpException;
import com.systex.practice.controller.validation.ValidList;
import com.systex.practice.model.HCMIORepository;
import com.systex.practice.model.HolidayScheduleRepository;
import com.systex.practice.model.MSTMBRepository;
import com.systex.practice.model.TCNUDRepository;
import com.systex.practice.model.entity.HCMIO;
import com.systex.practice.model.entity.HolidaySchedule;
import com.systex.practice.model.entity.MSTMB;
import com.systex.practice.model.entity.TCNUD;
import com.systex.practice.model.entity.type.BsType;
import com.systex.practice.model.entity.type.HolidayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.systex.practice.service.Tool.*;
import static java.lang.Math.sqrt;

@Service
public class StockService {

    @Autowired
    private HCMIORepository hcmioRepository;

    @Autowired
    private TCNUDRepository tcnudRepository;

    @Autowired
    private MSTMBRepository mstmbRepository;

    @Autowired
    private HolidayScheduleRepository holidayScheduleRepository;

    @Autowired
    private ApplicationContext applicationContext;


    private StockService getStockService() {
        return applicationContext.getBean(StockService.class);
    }

    @Cacheable(value = "stock_price_cache", key = "#stock")
    public MSTMB getMSTMBByStock(String stock) {
        System.out.println("Say Hi~");
        return this.mstmbRepository.findByStock(stock);
    }

    @CacheEvict(value = "stock_price_cache", key = "#stock")
    public void deleteStockPriceCache(String stock) {
    }

    public List<TCNUD> getTCNUDList(FindStockRequest findStockRequest) throws StockWebHttpException {

        Optional<String> optionalStock = Optional.ofNullable(findStockRequest.getStock());
        List<TCNUD> tcnudList = optionalStock.map(stock ->
                this.tcnudRepository.findByBranchNoAndCustSeqAndStock(
                        findStockRequest.getBranchNo(),
                        findStockRequest.getCustSeq(),
                        stock
                )
        ).orElse(
                this.tcnudRepository.findByBranchNoAndCustSeq(
                        findStockRequest.getBranchNo(),
                        findStockRequest.getCustSeq()
                )
        );

        if (tcnudList.isEmpty()) throw new StockWebHttpException(StatusCode.NO_FOUND, "tcnudList not found");

        return tcnudList;
    }

    public List<StockDetailResult> tcnudListToStockDetailResultList(List<TCNUD> tcnudList) {

        return tcnudList.stream().collect(Collectors.groupingBy(TCNUD::getStock)).entrySet()
                .stream().map(entry -> {
                    String stock = entry.getKey();
                    MSTMB mstmb = this.getStockService().getMSTMBByStock(stock);
                    return entry.getValue().stream().map(tcnud ->
                            StockDetailResult.builder()
                                    .setAllField(tcnud, mstmb)
                                    .setFieldFormat()
                                    .build()
                    ).collect(Collectors.toList());
                }).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public boolean isInProfitInterval(FindStockRequest.ProfitInterval profitInterval,
                                      BigDecimal RateOfProfit) {

        if (profitInterval == null) return true;

        if (profitInterval.getIsPercentage() == null) profitInterval.setIsPercentage(true);

        BigDecimal min = profitInterval.getProfitMin();
        BigDecimal max = profitInterval.getProfitMax();

        if (min == null && max == null) return true;

        if (profitInterval.getIsPercentage()) {
            min = min == null ? null : min.multiply(BigDecimal.valueOf(0.01));
            max = max == null ? null : max.multiply(BigDecimal.valueOf(0.01));
        }

        if (min == null) {
            return max.compareTo(RateOfProfit) > -1;
        }

        if (max == null) {
            return min.compareTo(RateOfProfit) < 1;
        }

        return max.compareTo(RateOfProfit) > -1 && min.compareTo(RateOfProfit) < 1;
    }

    public List<Result> getStockDetail(FindStockRequest findStockRequest) throws StockWebHttpException {

        List<TCNUD> tcnudList = this.getTCNUDList(findStockRequest);

        List<StockDetailResult> stockDetailResultList = this.tcnudListToStockDetailResultList(tcnudList);

        List<StockDetailResult> filterStockDetailResultList =
                stockDetailResultList.stream()
                        .filter(stockDetailResult ->
                                this.isInProfitInterval(findStockRequest.getProfitInterval(),
                                        stockDetailResult.getRateOfProfitWithBigDecimal())
                        ).collect(Collectors.toList());
        if (filterStockDetailResultList.isEmpty()) {
            throw new StockWebHttpException(
                    StatusCode.NO_FOUND, "after filter stockDetailResultList has no data");
        }

        return new ArrayList<>(filterStockDetailResultList);
    }

    public List<Result> getStockSummary(FindStockRequest findStockRequest) throws StockWebHttpException {

        List<TCNUD> tcnudList = this.getTCNUDList(findStockRequest);

        List<StockSummaryResult> stockSummaryResultList =
                tcnudList.stream().collect(Collectors.groupingBy(TCNUD::getStock)).entrySet()
                        .stream().map(entry -> {
                            String stock = entry.getKey();
                            MSTMB mstmb = this.getStockService().getMSTMBByStock(stock);
                            List<StockDetailResult> stockDetailResultList = entry.getValue().stream().map(tcnud ->
                                    StockDetailResult.builder()
                                            .setAllField(tcnud, mstmb)
                                            .setFieldFormat()
                                            .build()
                            ).collect(Collectors.toList());
                            return new StockSummaryResult(stockDetailResultList);
                        }).collect(Collectors.toList());

        List<StockSummaryResult> filterStockSummaryResultList =
                stockSummaryResultList.stream().filter(stockSummaryResult ->
                        this.isInProfitInterval(
                                findStockRequest.getProfitInterval(),
                                stockSummaryResult.getSumRateOfProfitWithBigDecimal()
                        )
                ).collect(Collectors.toList());

        if (filterStockSummaryResultList.isEmpty()) {
            throw new StockWebHttpException(
                    StatusCode.NO_FOUND, "after filter stockDetailResultList has no data");
        }

        return new ArrayList<>(filterStockSummaryResultList);

    }


    @Transactional
    public List<Result> insertStock(InsertStockRequest insertStockRequest) {

        BsType bsType = BsType.B;
        BigDecimal price = insertStockRequest.getPrice();
        BigDecimal qty = insertStockRequest.getQty();
        BigDecimal amt = calcAmt(price, qty);
        BigDecimal fee = calcFee(price, qty);
        BigDecimal tax = calcTax(bsType, price, qty);
        BigDecimal stinTax = calcStinTax(bsType, price, qty);
        BigDecimal netAmt = calcNetAmt(bsType, price, qty);

        Date now = new Date();

        String modDate = generateDate8(now);
        String modTime = generateTime6(now);
        String modUser = generateUser();

        HCMIO hcmio = HCMIO.builder()
                .tradeDate(insertStockRequest.getTradeDate())
                .branchNo(insertStockRequest.getBranchNo())
                .custSeq(insertStockRequest.getCustSeq())
                .docSeq(insertStockRequest.getDocSeq())
                .stock(insertStockRequest.getStock())
                .bsType(bsType)
                .price(price)
                .qty(qty)
                .amt(amt)
                .fee(fee)
                .tax(tax)
                .stinTax(stinTax)
                .netAmt(netAmt)
                .modDate(modDate)
                .modTime(modTime)
                .modUser(modUser)
                .build();

        int transactionCountOfHCMIO = this.hcmioRepository.insertUsingQuery(hcmio);
        System.out.println("transactionCountOfHCMIO=" + transactionCountOfHCMIO);

        TCNUD tcnud = TCNUD.builder()
                .tradeDate(hcmio.getTradeDate())
                .branchNo(hcmio.getBranchNo())
                .custSeq(hcmio.getCustSeq())
                .docSeq(hcmio.getDocSeq())
                .stock(hcmio.getStock())
                .price(hcmio.getPrice())
                .qty(hcmio.getQty())
                .remainQty(hcmio.getQty())
                .fee(hcmio.getFee())
                .cost(hcmio.getNetAmt().abs())
                .modDate(hcmio.getModDate())
                .modTime(hcmio.getModTime())
                .modUser(hcmio.getModUser())
                .build();

        int transactionCountOfTCNUD = this.tcnudRepository.insertUsingQuery(tcnud);

        System.out.println("transactionCountOfTCNUD=" + transactionCountOfTCNUD);

        return new ArrayList<>(this.tcnudListToStockDetailResultList(List.of(tcnud)));

    }

    public void simulateUpdateStockPrice() {
        Random random = new Random();
        List<MSTMB> mstmbList = this.mstmbRepository.findAll();
        mstmbList = mstmbList.stream().peek(mstmb -> {
            double mean = 0.015;
            double variance = 0.001;
            double error = 1 + random.nextGaussian() * sqrt(variance) + mean;
            System.out.println(error * 100);
            BigDecimal nextPrice = mstmb.getCurPrice().multiply(BigDecimal.valueOf(error))
                    .setScale(2, RoundingMode.DOWN);
            mstmb.setCurPrice(nextPrice);
        }).collect(Collectors.toList());
        this.mstmbRepository.saveAllAndFlush(mstmbList);
    }

    @Transactional(rollbackFor = {StockWebHttpException.class})
    public void updateStockPrice(ValidList<UpdateStockPriceRequest> updateStockPriceRequestList)
            throws StockWebHttpException {

        for (UpdateStockPriceRequest updateStockPriceRequest : updateStockPriceRequestList) {
            String stock = updateStockPriceRequest.getStock();
            BigDecimal price = updateStockPriceRequest.getPrice();
            int transactionCountOfMSTMB = this.mstmbRepository.updateByStock(stock, price);
            if (transactionCountOfMSTMB == 0)
                throw new StockWebHttpException(StatusCode.BAD_REQUEST,
                        "the stock (" + stock + ") not found in MSTMB table");
            this.getStockService().deleteStockPriceCache(stock);
        }

    }

    public List<Result> getDeliveryAmount(DeliveryAmountRequest deliveryAmountRequest)
            throws StockWebHttpException {

        Date now = new Date();

        List<HolidaySchedule> holidayScheduleList = this.holidayScheduleRepository.findAll();

        List<Date> closeDateList = holidayScheduleList.stream()
                .filter(holidaySchedule -> holidaySchedule.getHolidayType() == HolidayType.close)
                .map(HolidaySchedule::getDate).collect(Collectors.toList());

        List<Date> openDateList = holidayScheduleList.stream()
                .filter(holidaySchedule -> holidaySchedule.getHolidayType() == HolidayType.open)
                .map(HolidaySchedule::getDate).collect(Collectors.toList());

        if (!isOpen(closeDateList, openDateList, now)) {
            throw new StockWebHttpException(StatusCode.NO_FOUND, "today is holiday");
        }

        int count = 0;
        Date nextDay = now;
        do {
            nextDay = plusDay(nextDay, -1);
            if (isOpen(closeDateList, openDateList, nextDay)) count++;
        } while (count < 2);

        String tradeDate = generateDate8(nextDay);

        List<HCMIO> hcmioList = this.hcmioRepository.findByTradeDateAndBranchNoAndCustSeq(
                tradeDate,
                deliveryAmountRequest.getBranchNo(),
                deliveryAmountRequest.getCustSeq()
        );

        if (hcmioList.isEmpty())
            throw new StockWebHttpException(StatusCode.NO_FOUND, "branchNo or custSeq not found");

        List<DeliveryAmountResult> deliveryAmountResultList = new ArrayList<>();
        deliveryAmountResultList.add(new DeliveryAmountResult(hcmioList));

        return new ArrayList<>(deliveryAmountResultList);

    }
}
