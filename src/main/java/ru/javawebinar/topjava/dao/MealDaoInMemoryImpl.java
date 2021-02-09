package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemoryImpl implements MealDao {
    private static AtomicInteger mealCount = new AtomicInteger(0);

    private List<Meal> mealList;

    {
      List<Meal>  list = new ArrayList<>();

        list.add(new Meal(mealCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        list.add(new Meal(mealCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        list.add(new Meal(mealCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        list.add(new Meal(mealCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        list.add(new Meal(mealCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        list.add(new Meal(mealCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        list.add(new Meal(mealCount.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

        mealList = new CopyOnWriteArrayList<>(list);
    }

    @Override
    public AtomicInteger getMealCount() {
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
        meal.setId(mealCount.incrementAndGet());
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
