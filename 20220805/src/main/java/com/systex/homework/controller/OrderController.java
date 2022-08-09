package com.systex.homework.controller;

import com.systex.homework.model.Order;
import com.systex.homework.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public List<Order> getAllOrders() {
        return this.orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable int id) {
        return this.orderService.getOrderById(id);
    }

    @PostMapping()
    public void createOrder(@RequestBody Order order) {
        this.orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public void updateOrder(@PathVariable int id, @RequestBody Order order) {
        this.orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable int id) {
        this.orderService.deleteOrderById(id);
    }
}
