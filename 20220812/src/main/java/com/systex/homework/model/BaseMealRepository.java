package com.systex.homework.model;

import com.systex.homework.model.entity.BaseMeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseMealRepository extends JpaRepository<BaseMeal, Integer> {

    BaseMeal findById(int id);

    BaseMeal findByName(String name);

    Long deleteById(int id);
}
