package com.systex.homework.service;

import com.systex.homework.model.BaseMealRepository;
import com.systex.homework.model.MealRepository;
import com.systex.homework.model.OrderRepository;
import com.systex.homework.model.entity.BaseMeal;
import com.systex.homework.model.entity.Meal;
import com.systex.homework.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private BaseMealRepository baseMealRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Meal getMealByNumberAndName(int quantity, String name) throws ResponseStatusException {
        BaseMeal baseMeal = this.baseMealRepository.findByName(name);
        if (null == baseMeal)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal (name=" + name + ") not found.");
        Meal meal = this.mealRepository.findByQuantityAndBaseMeal(quantity, baseMeal);
        if (null == meal) {
            meal = Meal.builder().quantity(quantity).baseMeal(baseMeal).orders(new HashSet<>()).build();
            this.mealRepository.save(meal);
        }
        return this.mealRepository.findByQuantityAndBaseMeal(quantity, baseMeal);
    }

    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public Order getOrderById(int id) throws ResponseStatusException {
        Order order = this.orderRepository.findById(id);
        if (null == order)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order (id=" + id + ") not found");

        return order;
    }


    public String createOrder(Order order) throws ResponseStatusException {
        this.orderRepository.save(order);
        return "New order (waiter=" + order.getWaiter() + ") is created.";
    }

    public String updateOrderById(int id, Order order) throws ResponseStatusException {
        this.orderRepository.save(order);
        return "Order (id=" + order.getId() + ") successfully updated.";
    }

    public String deleteOrderById(int id) throws ResponseStatusException {
        Order order = this.getOrderById(id);
        this.orderRepository.deleteById(id);
        return "Order (id=" + id + ", waiter=" + order.getWaiter() + ") successfully deleted.";
    }
}
