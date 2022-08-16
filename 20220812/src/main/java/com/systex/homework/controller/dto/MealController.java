package com.systex.homework.controller.dto;

import com.systex.homework.controller.dto.request.BaseMealRequest;
import com.systex.homework.controller.dto.response.BaseMealResponse;
import com.systex.homework.controller.dto.response.MealResponse;
import com.systex.homework.controller.dto.response.StatusResponse;
import com.systex.homework.model.entity.BaseMeal;
import com.systex.homework.model.entity.Meal;
import com.systex.homework.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1")
public class MealController {

    @Autowired
    private MealService mealService;
    private ObjectError objectError;

    public BaseMealResponse baseMealToBaseMealResponse(BaseMeal baseMeal) {
        return BaseMealResponse.builder()
                .id(baseMeal.getId())
                .name(baseMeal.getName())
                .price(baseMeal.getPrice())
                .description(baseMeal.getDescription())
                .build();
    }

    public BaseMeal baseMealRequestToBaseMeal(BaseMealRequest baseMealRequest) {
        return BaseMeal.builder()
                .name(baseMealRequest.getName())
                .price(baseMealRequest.getPrice())
                .description(baseMealRequest.getDescription())
                .build();
    }

    public MealResponse mealToMealResponse(Meal meal) {
        return MealResponse.builder()
                .name(meal.getBaseMeal().getName())
                .quantity(meal.getQuantity())
                .unitPrice(meal.getBaseMeal().getPrice())
                .build();
    }

    public List<BaseMealResponse> getAllBaseMeals() {
        List<BaseMeal> baseMealList = this.mealService.getAllBaseMeals();
        List<BaseMealResponse> baseMealResponseList = new ArrayList<>();
        baseMealList.stream().forEach(baseMeal
                -> baseMealResponseList.add(this.baseMealToBaseMealResponse(baseMeal)));
        return baseMealResponseList;
    }

    @GetMapping("/baseMeal/{id}")
    public Object getBaseMealById(@PathVariable int id) {
        try {
            BaseMeal baseMeal = this.mealService.getBaseMealById(id);
            return this.baseMealToBaseMealResponse(baseMeal);
        } catch (ResponseStatusException e) {
            return new StatusResponse(e.getReason());
        }
    }

    @GetMapping("/baseMeal")
    public Object getBaseMealByName(@RequestParam(required = false) String name) {
        if (null == name) return this.getAllBaseMeals();

        try {
            BaseMeal baseMeal = this.mealService.getBaseMealByName(name);
            return this.baseMealToBaseMealResponse(baseMeal);
        } catch (ResponseStatusException e) {
            return new StatusResponse(e.getReason());
        }
    }

    @GetMapping("/meal")
    public Object getMealByNumberAndName(@Positive(message = "quantity must be a positive integer")
                                             @RequestParam int quantity, @RequestParam String name) {

        try {
            Meal meal = this.mealService.getMealByNumberAndName(quantity, name);
            return this.mealToMealResponse(meal);
        } catch (ResponseStatusException e) {
            return new StatusResponse(e.getReason());
        }
    }

    @PostMapping("/baseMeal")
    public StatusResponse createBaseMeal(@Valid @RequestBody BaseMealRequest baseMealRequest,
                                         BindingResult bindingResult) {
        StringBuilder statusSB = new StringBuilder();
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                statusSB.append(objectError.getDefaultMessage()).append(", ");
            }
            return new StatusResponse(statusSB.toString());
        }

        BaseMeal baseMeal = this.baseMealRequestToBaseMeal(baseMealRequest);
        try {
            statusSB.append(this.mealService.createBaseMeal(baseMeal));
        } catch (ResponseStatusException e) {
            statusSB.append(e.getReason());
        }
        return new StatusResponse(statusSB.toString());
    }

    @PutMapping("/baseMeal/{id}")
    public StatusResponse updateBaseMealById(@PathVariable int id, @Valid @RequestBody BaseMealRequest baseMealRequest,
                                             BindingResult bindingResult) {
        StringBuilder statusSB = new StringBuilder();
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                statusSB.append(objectError.getDefaultMessage()).append(", ");
            }
            return new StatusResponse(statusSB.toString());
        }

        BaseMeal baseMeal = this.baseMealRequestToBaseMeal(baseMealRequest);
        try {
            statusSB = new StringBuilder(this.mealService.updateBaseMealById(id, baseMeal));
        } catch (ResponseStatusException e) {
            statusSB = new StringBuilder(e.getReason());
        }
        return new StatusResponse(statusSB.toString());
    }

    @DeleteMapping("/baseMeal/{id}")
    public StatusResponse deleteBaseMealById(@PathVariable int id) {
        String status = "";
        try {
            status = this.mealService.deleteBaseMealById(id);
        } catch (ResponseStatusException e) {
            status = e.getReason();
        }
        return new StatusResponse(status);
    }
}
