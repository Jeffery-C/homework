package com.systex.homework.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.time.LocalDate;

@Getter
public class BaseDate {
    private int year;
    private int month;
    private int day;

    public BaseDate() {
    }

    public BaseDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString() {
        return LocalDate.of(year, month, day).toString();
    }


}
