package com.systex.homework.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String waiter;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "orders")
    private Set<Meal> meals;

}
