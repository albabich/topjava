package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.concurrent.atomic.AtomicInteger;

public interface MealDao {
    Object getAllMeals();
    Meal getMealById(int mealId);
    void addMeal(Meal meal);
    void updateMeal(Meal meal);
    void deleteMeal(int mealId);
    AtomicInteger getMealCount();
}
