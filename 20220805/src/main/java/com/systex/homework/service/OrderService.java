package com.systex.homework.service;

import com.systex.homework.model.BaseDate;
import com.systex.homework.model.Guesthouse;
import com.systex.homework.model.GuesthouseDate;
import com.systex.homework.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final List<Order> orderList;

    private final List<GuesthouseDate> guesthouseDateList;

    public OrderService() {
        this.orderList = new ArrayList<>();
        this.guesthouseDateList = new ArrayList<>();

        // Create order 1
        Order tempOrder = new Order("Jack");
        List<Guesthouse> tempGuesthouseList = new ArrayList<>();
        Guesthouse tempGuesthouse = Guesthouse.createGuesthouseByNameRooms("凱維精品旅館", 2);
        tempGuesthouse.setDate(new BaseDate(2022, 8, 8),
                new BaseDate(2022, 8, 10));
        this.checkGuesthouseDate(tempGuesthouse);
        tempGuesthouseList.add(tempGuesthouse);
        tempOrder.setGuesthouseList(tempGuesthouseList);
        this.orderList.add(tempOrder);

        // Create order 2
        tempOrder = new Order("Jeffrey");
        tempGuesthouseList = new ArrayList<>();
        tempGuesthouse = Guesthouse.createGuesthouseByNameRooms("凱維精品旅館", 2);
        tempGuesthouse.setDate(new BaseDate(2022, 8, 11),
                new BaseDate(2022, 8, 12));
        this.checkGuesthouseDate(tempGuesthouse);
        tempGuesthouseList.add(tempGuesthouse);
        tempGuesthouse = Guesthouse.createGuesthouseByNameRooms("這一站幸福", 4);
        tempGuesthouse.setDate(new BaseDate(2022, 8, 11),
                new BaseDate(2022, 8, 12));
        this.checkGuesthouseDate(tempGuesthouse);
        tempGuesthouseList.add(tempGuesthouse);
        tempOrder.setGuesthouseList(tempGuesthouseList);
        this.orderList.add(tempOrder);

        // Create order 3
        tempOrder = new Order("Mary");
        tempGuesthouseList = new ArrayList<>();
        tempGuesthouse = Guesthouse.createGuesthouseByNameRooms("這一站幸福", 2);
        tempGuesthouse.setDate(new BaseDate(2022, 9, 9),
                new BaseDate(2022, 9, 13));
        this.checkGuesthouseDate(tempGuesthouse);
        tempGuesthouseList.add(tempGuesthouse);
        tempGuesthouse = Guesthouse.createGuesthouseByNameRooms("兒童樂園民宿", 4);
        tempGuesthouse.setDate(new BaseDate(2022, 10, 1),
                new BaseDate(2022, 10, 5));
        this.checkGuesthouseDate(tempGuesthouse);
        tempGuesthouseList.add(tempGuesthouse);
        tempOrder.setGuesthouseList(tempGuesthouseList);
        this.orderList.add(tempOrder);

    }

    public void checkGuesthouseDate(Guesthouse guesthouse) {
        for (GuesthouseDate guesthouseDate : this.guesthouseDateList) {
            if (guesthouseDate.getGuesthouseId() == guesthouse.getId()) {
                LocalDate tempLocalDate =
                        LocalDate.parse(guesthouse.getStartDate().toString());

                if (tempLocalDate.toString().equals(guesthouseDate.getDateString()))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request.");

                while (!tempLocalDate.toString().equals(guesthouse.getEndDate().toString())) {
                    tempLocalDate = tempLocalDate.plusDays(1);
                    if (tempLocalDate.toString().equals(guesthouseDate.getDateString()))
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request.");
                }
            }
        }

        LocalDate tempLocalDate =
                LocalDate.parse(guesthouse.getStartDate().toString());

        this.guesthouseDateList.add(
                new GuesthouseDate(guesthouse.getId(), tempLocalDate.toString()));
        while (!tempLocalDate.toString().equals(guesthouse.getEndDate().toString())) {
            tempLocalDate = tempLocalDate.plusDays(1);
            this.guesthouseDateList.add(
                    new GuesthouseDate(guesthouse.getId(), tempLocalDate.toString()));
        }
    }

    public List<Order> getAllOrders() {
        return this.orderList;
    }

    public Order getOrderById(int id) {
        Order returnOrder = null;
        for (Order order : this.orderList) {
            if (order.getId() == id) returnOrder = order;
        }
        if (returnOrder == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found.");

        return returnOrder;
    }

    public void createOrder(Order order) {

        if (order.getCustomerName() == null || "".equals(order.getCustomerName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request.");

        Order newOrder = new Order(order.getCustomerName());
        List<Guesthouse> newGuesthouseList = new ArrayList<>();
        Guesthouse tempGuesthouse;
        for (Guesthouse guesthouse : order.getGuesthouseList()) {
            tempGuesthouse = Guesthouse.createGuesthouseByNameRooms(
                    guesthouse.getName(), guesthouse.getRooms());
            tempGuesthouse.setDate(
                    new BaseDate(
                            guesthouse.getStartDate().getYear(),
                            guesthouse.getStartDate().getMonth(),
                            guesthouse.getStartDate().getDay()),
                    new BaseDate(
                            guesthouse.getEndDate().getYear(),
                            guesthouse.getEndDate().getMonth(),
                            guesthouse.getEndDate().getDay()));
            this.checkGuesthouseDate(tempGuesthouse);
            newGuesthouseList.add(tempGuesthouse);
        }
        newOrder.setGuesthouseList(newGuesthouseList);
        this.orderList.add(newOrder);
        throw new ResponseStatusException(HttpStatus.CREATED, "New order is created.");
    }

    public void updateOrder(int id, Order order) {
        if (id != order.getId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request.");

        Order tempOrder = getOrderById(id);

        try {
            this.deleteOrderById(id);
        }
        catch (ResponseStatusException rse) {
            if (rse.getStatus() != HttpStatus.OK) {
                throw rse;
            }
        }

        try {
            try {
                this.createOrder(order);
            }
            catch (ResponseStatusException rse) {
                if (rse.getStatus() != HttpStatus.CREATED) {
                    throw rse;
                }
            }

            Order updateOrder = this.orderList.get(this.orderList.size()-1);
            updateOrder.setId(id);
        }
        catch (ResponseStatusException rse) {
            try {
                this.createOrder(tempOrder);
            }
            catch (ResponseStatusException rse2) {
                if (rse.getStatus() != HttpStatus.CREATED) {
                    throw rse2;
                }
            }
            Order updateOrder = this.orderList.get(this.orderList.size()-1);
            updateOrder.setId(id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request.");
        }

        throw new ResponseStatusException(HttpStatus.OK, "Order successfully updated");
    }

    public void deleteOrderById(int id) {
        Order deleteOrder = this.getOrderById(id);
        for (Guesthouse guesthouse:deleteOrder.getGuesthouseList()) {
            this.removeGuesthouseDateListByGuesthouse(guesthouse);
        }

        this.orderList.remove(deleteOrder);

        throw new ResponseStatusException(HttpStatus.OK, "Order successfully deleted");
    }

    public void removeGuesthouseDateListByGuesthouse(Guesthouse guesthouse) {
        List<GuesthouseDate> tempGuesthouseDateList = new ArrayList<>();
        for (GuesthouseDate guesthouseDate : this.guesthouseDateList) {
            if (guesthouseDate.getGuesthouseId() == guesthouse.getId()) {
                LocalDate tempLocalDate =
                        LocalDate.parse(guesthouse.getStartDate().toString());

                if (tempLocalDate.toString().equals(guesthouseDate.getDateString()))
                    tempGuesthouseDateList.add(guesthouseDate);

                while (!tempLocalDate.toString().equals(guesthouse.getEndDate().toString())) {
                    tempLocalDate = tempLocalDate.plusDays(1);
                    if (tempLocalDate.toString().equals(guesthouseDate.getDateString()))
                        tempGuesthouseDateList.add(guesthouseDate);
                }
            }
        }

        for (GuesthouseDate guesthouseDate: tempGuesthouseDateList) {
            this.guesthouseDateList.remove(guesthouseDate);
        }
    }
}
