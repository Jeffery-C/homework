package com.systex.homework.controller.dto;

import com.systex.homework.controller.dto.request.OrderRequest;
import com.systex.homework.controller.dto.response.MealResponse;
import com.systex.homework.controller.dto.response.OrderResponse;
import com.systex.homework.controller.dto.response.StatusResponse;
import com.systex.homework.model.entity.Meal;
import com.systex.homework.model.entity.Order;
import com.systex.homework.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    public OrderResponse orderToOrderResponse(Order order) {
        List<MealResponse> mealResponseList = new ArrayList<>();
        AtomicInteger totalPrice = new AtomicInteger(0);
        order.getMeals().stream().forEach(meal -> {
            mealResponseList.add(
                    MealResponse.builder()
                            .name(meal.getBaseMeal().getName())
                            .quantity(meal.getQuantity())
                            .unitPrice(meal.getBaseMeal().getPrice())
                            .build()
            );
            totalPrice.addAndGet(meal.getQuantity() * meal.getBaseMeal().getPrice());
        });
        return OrderResponse.builder()
                .id(order.getId())
                .waiter(order.getWaiter())
                .totalPrice(totalPrice.get())
                .mealList(mealResponseList)
                .build();
    }

    public Order orderRequestToOrder(OrderRequest orderRequest, Integer id) throws ResponseStatusException {

        AtomicInteger tempInt = new AtomicInteger();
        HashMap<String, Integer> tempMap = new HashMap<>();
        orderRequest.getMealList().stream().forEach(mealRequest -> {
            tempInt.set(mealRequest.getQuantity());
            if (tempMap.containsKey(mealRequest.getName())) {
                tempInt.addAndGet(tempMap.get(mealRequest.getName()));
                tempMap.replace(mealRequest.getName(), tempInt.intValue());
            } else {
                tempMap.put(mealRequest.getName(), mealRequest.getQuantity());
            }
        });

        for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
            String name = entry.getKey();
            Integer quantity = entry.getValue();
            this.orderService.getMealByNumberAndName(quantity, name);
        }

        Order order;
        if (null == id) {
            order = Order.builder()
                    .waiter(orderRequest.getWaiter())
                    .meals(new HashSet<>())
                    .build();
        } else {
            order = this.orderService.getOrderById(id);
            order.setWaiter(orderRequest.getWaiter());
        }

        for (Meal meal : order.getMeals()) {
            meal.getOrders().removeIf(
                    (Order currOrder) -> id == currOrder.getId()
            );
        }

        for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
            String name = entry.getKey();
            Integer quantity = entry.getValue();
            Meal meal = this.orderService.getMealByNumberAndName(quantity, name);
            order.getMeals().add(meal);
            meal.getOrders().add(order);
        }

        return order;
    }

    public Order orderRequestToOrder(OrderRequest orderRequest) throws ResponseStatusException {
        return this.orderRequestToOrder(orderRequest, null);
    }

    @GetMapping()
    public List<OrderResponse> getAllOrders() {
        List<Order> orderList = this.orderService.getAllOrders();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        orderList.stream().forEach(order -> {
            orderResponseList.add(this.orderToOrderResponse(order));
        });
        return orderResponseList;
    }

    @GetMapping("/{id}")
    public Object getOrderById(@PathVariable int id) {
        try {
            Order order = this.orderService.getOrderById(id);
            return this.orderToOrderResponse(order);
        } catch (ResponseStatusException e) {
            return new StatusResponse(e.getReason());
        }
    }

    @PostMapping()
    public StatusResponse createOrder(@Valid @RequestBody OrderRequest orderRequest,
                                      BindingResult bindingResult) {

        StringBuilder status = new StringBuilder();
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                status.append(objectError.getDefaultMessage()).append(", ");
            }
            return new StatusResponse(status.toString());
        }

        try {
            Order order = this.orderRequestToOrder(orderRequest);
            status = new StringBuilder(this.orderService.createOrder(order));
        } catch (ResponseStatusException e) {
            status = new StringBuilder(e.getReason());
        }
        return new StatusResponse(status.toString());
    }

    @PutMapping("/{id}")
    public StatusResponse updateOrderById(@PathVariable int id, @Valid @RequestBody OrderRequest orderRequest,
                                          BindingResult bindingResult) {
        StringBuilder status = new StringBuilder();
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                status.append(objectError.getDefaultMessage()).append(", ");
            }
            return new StatusResponse(status.toString());
        }

        try {
            Order order = this.orderRequestToOrder(orderRequest, id);
            status = new StringBuilder(this.orderService.updateOrderById(order.getId(), order));
        } catch (ResponseStatusException e) {
            status = new StringBuilder(e.getReason());
        }
        return new StatusResponse(status.toString());
    }

    @DeleteMapping("/{id}")
    public StatusResponse deleteOrderById(@PathVariable int id) {
        String status;
        try {
            status = this.orderService.deleteOrderById(id);
        } catch (ResponseStatusException e) {
            status = e.getReason();
        }
        return new StatusResponse(status);
    }

}
