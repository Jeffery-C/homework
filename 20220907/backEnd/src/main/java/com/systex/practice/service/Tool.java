package com.systex.practice.service;

import com.systex.practice.model.entity.type.BsType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Tool {

    private static final BigDecimal FEE_RATE = BigDecimal.valueOf(0.001425);

    private static final BigDecimal TAX_RATE = BigDecimal.valueOf(0.003);


    public static BigDecimal calcAmt(BigDecimal price, BigDecimal qty) {
        return price.multiply(qty).setScale(2, RoundingMode.DOWN);
    }

    public static BigDecimal calcFee(BigDecimal price, BigDecimal qty) {
        return calcAmt(price, qty).multiply(FEE_RATE).setScale(0, RoundingMode.DOWN);
    }

    public static BigDecimal calcTax(BsType bsType, BigDecimal price, BigDecimal qty) {
        BigDecimal result = BigDecimal.ZERO;

        if (BsType.S == bsType) result = calcAmt(price, qty).multiply(TAX_RATE).setScale(0, RoundingMode.DOWN);

        return result;
    }

    public static BigDecimal calcStinTax(BsType bsType, BigDecimal price, BigDecimal qty) {
        return BigDecimal.ZERO;
    }

    public static BigDecimal calcTotalFee(BsType bsType, BigDecimal price, BigDecimal qty) {
        BigDecimal fee = calcFee(price, qty);
        BigDecimal tax = calcTax(bsType, price, qty);
        BigDecimal stinTax = calcStinTax(bsType, price, qty);
        return fee.add(tax).add(stinTax);
    }

    public static BigDecimal calcNetAmt(BsType bsType, BigDecimal price, BigDecimal qty) {
        BigDecimal amt = calcAmt(price, qty);
        BigDecimal totalFee = calcTotalFee(bsType, price, qty);
        if (BsType.B == bsType) amt = amt.multiply(BigDecimal.valueOf(-1));
        return amt.subtract(totalFee);
    }

    public static BigDecimal calcUnrealProfit(BigDecimal cost, BigDecimal sellPrice, BigDecimal sellQty) {
        BigDecimal profit = calcNetAmt(BsType.S, sellPrice, sellQty);
        return profit.subtract(cost);
    }

    public static BigDecimal calcMarketValue(BigDecimal price, BigDecimal qty) {
        return price.multiply(qty);
    }


    public static BigDecimal calcRateOfProfit(BigDecimal cost, BigDecimal unrealProfit) {
        return unrealProfit.divide(cost, 4, RoundingMode.DOWN);
    }


    /**
     *
     * @param date
     * @return dateFormat: yyyyMMdd, i.e. 20200912
     */
    public static String generateDate8(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    /**
     *
     * @param date
     * @return dateFormat: HHmmss, i.e. 223039
     */
    public static String generateTime6(Date date) {
        return new SimpleDateFormat("HHmmss").format(date);
    }

    public static String generateUser() {
        return User.Jeff.name();
    }

    public enum User {
        Jeff, Jack
    }

    public static int getWeekday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static boolean isHoliday(Date date) {
        int weekday = getWeekday(date);
        return weekday == 1 || weekday == 7;
    }

    public static boolean isOpen(List<Date> closeDateList, List<Date> openDateList, Date date) {

        if (closeDateList.isEmpty() && openDateList.isEmpty()) {
            return !isHoliday(date);
        }

        if (closeDateList.isEmpty()) {
            if (openDateList.contains(date)) return true;
            else return !isHoliday(date);
        }

        if (openDateList.isEmpty()) {
            if (closeDateList.contains(date)) return false;
            else return !isHoliday(date);
        }

        if (openDateList.contains(date)) return true;
        if (closeDateList.contains(date)) return false;
        return !isHoliday(date);
    }

    public static Date plusDay(Date date, int numberOfDay) {
        long DAY = 1000 * 60 * 60 * 24;
        return new Date(date.getTime() + numberOfDay*DAY);
    }


}
