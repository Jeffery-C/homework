package com.systex.homework.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private int id;
    private String customerName;
    private int totalPrice;
    private List<Guesthouse> guesthouseList;

    public static int nextId = 1;

    public Order() {
    }

    public Order(String customerName) {
        setId();
        this.customerName = customerName;
    }

    public void setId() {
        this.id = Order.nextId++;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGuesthouseList(List<Guesthouse> guesthousesList) {
        this.totalPrice = 0;
        for (Guesthouse guesthouse: guesthousesList) {
            this.totalPrice += guesthouse.getPrice();
        }
        this.guesthouseList = guesthousesList;
    }

}
