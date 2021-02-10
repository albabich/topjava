package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemoryImpl implements MealDao {
    private final AtomicInteger mealCount = new AtomicInteger(0);

    private final Map<Integer, Meal> mealMap;

    {
        mealMap = new ConcurrentHashMap<>();
        mealMap.put(mealCount.incrementAndGet(), new Meal(mealCount.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealMap.put(mealCount.incrementAndGet(), new Meal(mealCount.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealMap.put(mealCount.incrementAndGet(), new Meal(mealCount.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealMap.put(mealCount.incrementAndGet(), new Meal(mealCount.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealMap.put(mealCount.incrementAndGet(), new Meal(mealCount.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealMap.put(mealCount.incrementAndGet(), new Meal(mealCount.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealMap.put(mealCount.incrementAndGet(), new Meal(mealCount.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal getById(int id) {
        return mealMap.get(id);
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(mealCount.incrementAndGet());
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal) {
        int id = meal.getId();
        Meal mealToBeUpdated = getById(id);
        mealToBeUpdated.setDateTime(meal.getDateTime());
        mealToBeUpdated.setDescription(meal.getDescription());
        mealToBeUpdated.setCalories(meal.getCalories());
        return mealToBeUpdated;
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }
}
