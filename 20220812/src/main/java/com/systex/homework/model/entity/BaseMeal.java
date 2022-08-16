package com.systex.homework.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class BaseMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int price;

    @Column
    private String description;

    //@JsonManagedReference
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "baseMeal")
    @EqualsAndHashCode.Exclude
    private Set<Meal> mealSet;

}
