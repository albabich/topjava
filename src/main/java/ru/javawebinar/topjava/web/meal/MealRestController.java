package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

//    public MealRestController(MealService service) {
//        this.service = service;
//    }

    public Meal create(Meal meal) {
        return service.create(authUserId(), meal);
    }

    public Meal get(int mealId) {
        return service.get(authUserId(), mealId);
    }

    public List<MealTo> getAll() {
        return service.getAll(authUserId());
    }
    public List<MealTo> getAll(LocalDate startDate, LocalTime startTime,LocalDate endDate,LocalTime endTime) {
        return service.getAll(authUserId(),startDate,startTime,endDate,endTime);
    }

    public void delete(int mealId) {
        service.delete(authUserId(), mealId);
    }

    public void update(Meal meal) {
        service.update(authUserId(), meal);
    }
}