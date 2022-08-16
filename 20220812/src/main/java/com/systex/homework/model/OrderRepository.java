package com.systex.homework.model;

import com.systex.homework.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findById(int id);
}
