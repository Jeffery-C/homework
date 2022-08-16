package com.systex.homework.model;

import com.systex.homework.model.entity.BaseMeal;
import com.systex.homework.model.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Integer> {

    Meal findById(int id);

    Meal findByQuantityAndBaseMeal(int quantity, BaseMeal baseMeal);

}
