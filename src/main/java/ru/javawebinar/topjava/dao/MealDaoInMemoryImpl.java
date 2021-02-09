package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MealDaoInMemoryImpl implements MealDao {
    private static int mealCount = 0;

    private List<Meal> mealList;

    {
        mealList = new ArrayList<>();

        mealList.add(new Meal(++mealCount, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealList.add(new Meal(++mealCount, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealList.add(new Meal(++mealCount, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealList.add(new Meal(++mealCount, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealList.add(new Meal(++mealCount, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealList.add(new Meal(++mealCount, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealList.add(new Meal(++mealCount, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public int getMealCount() {
        return mealCount;
    }

    @Override
    public Object getAllMeals() {
        return mealList;
    }

    @Override
    public Meal getMealById(int id) {
        return mealList.stream().filter(meal -> meal.getId() == id).findAny().orElse(null);
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setId(++mealCount);
        mealList.add(meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        int id = meal.getId();
        Meal mealToBeUpdated = getMealById(id);
        mealToBeUpdated.setDateTime(meal.getDateTime());
        mealToBeUpdated.setDescription(meal.getDescription());
        mealToBeUpdated.setCalories(meal.getCalories());
    }

    @Override
    public void deleteMeal(int id) {
        mealList.removeIf(m -> m.getId() == id);
    }
}
