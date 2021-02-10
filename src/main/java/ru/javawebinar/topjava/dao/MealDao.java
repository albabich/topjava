package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Map;

public interface MealDao {
    List<Meal> getAll();

    Meal getById(int id);

    Meal add(Meal meal);

    Meal update(Meal meal);

    void delete(int id);

}
