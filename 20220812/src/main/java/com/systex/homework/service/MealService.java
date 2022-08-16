package com.systex.homework.service;

import com.systex.homework.model.BaseMealRepository;
import com.systex.homework.model.MealRepository;
import com.systex.homework.model.entity.BaseMeal;
import com.systex.homework.model.entity.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MealService {

    @Autowired
    private BaseMealRepository baseMealRepository;

    @Autowired
    private MealRepository mealRepository;

    public List<BaseMeal> getAllBaseMeals() {
        return this.baseMealRepository.findAll();
    }

    public BaseMeal getBaseMealById(int id) throws ResponseStatusException {
        BaseMeal baseMeal = this.baseMealRepository.findById(id);
        if (null == baseMeal)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BaseMeal (id=" + id + ") not found");

        return baseMeal;
    }

    public BaseMeal getBaseMealByName(String name) throws ResponseStatusException {
        BaseMeal baseMeal = this.baseMealRepository.findByName(name);
        if (null == baseMeal)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BaseMeal (name=" + name + ") not found");

        return baseMeal;
    }

    public Meal getMealByNumberAndName(int quantity, String name) throws ResponseStatusException {
        BaseMeal baseMeal = this.baseMealRepository.findByName(name);
        if (null == baseMeal)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BaseMeal (name=" + name + ") not found.");
        Meal meal = this.mealRepository.findByQuantityAndBaseMeal(quantity, baseMeal);
        if (null == meal) {
            meal = Meal.builder().quantity(quantity).baseMeal(baseMeal).build();
            this.mealRepository.save(meal);
        }
        return meal;
    }

    public String createBaseMeal(BaseMeal createBaseMeal) throws ResponseStatusException {
        BaseMeal baseMeal = this.baseMealRepository.findByName(createBaseMeal.getName());
        if (null != baseMeal && baseMeal.getName().equals(createBaseMeal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This name (" + createBaseMeal.getName() + ") of baseMeal already exists.");

        this.baseMealRepository.save(createBaseMeal);
        return "New baseMeal (name=" + createBaseMeal.getName() + ") is created.";
    }

    public String updateBaseMealById(int id, BaseMeal updateBaseMeal) throws ResponseStatusException {
        BaseMeal baseMeal = this.getBaseMealById(id);
        baseMeal.setName(updateBaseMeal.getName());
        baseMeal.setPrice(updateBaseMeal.getPrice());
        baseMeal.setDescription(updateBaseMeal.getDescription());

        this.baseMealRepository.save(baseMeal);
        return "BaseMeal (name=" + baseMeal.getName() + ") successfully updated";
    }

    public String deleteBaseMealById(int id) throws ResponseStatusException {
        BaseMeal baseMeal = this.getBaseMealById(id);

        this.baseMealRepository.deleteById(id);
        return "BaseMeal (id=" + id + ", name=" + baseMeal.getName() + ") successfully deleted.";
    }

}
