package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private Map<Integer, Meal> meals;
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal.getUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        meals = repository.get(userId) == null ? new HashMap<>() : repository.get(userId);
        if (meal.isNew()) {
            log.info("create userId={}, meal={}", userId, meal);
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            repository.put(userId, meals);
            return meal;
        }
        Meal savedMeal = null;
        if (get(userId, meal.getId()) != null) {
            log.info("update userId={}, meal={}", userId, meal);
            savedMeal = meals.computeIfPresent(meal.getId(), (id, m) -> meal);
            repository.put(userId, meals);
        }
        return savedMeal;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        meals = repository.get(userId);
        if (get(userId, mealId) == null) {
            return false;
        } else {
            log.info("delete userId={}, mealId={}", userId, mealId);
            boolean result = meals.remove(mealId) != null;
            repository.put(userId, meals);
            return result;
        }
    }

    @Override
    public Meal get(int userId, int mealId) {
        meals = repository.get(userId);
        Meal meal = meals.get(mealId);
        if (meal == null) {
            log.info("This meal don't exist");
            return null;
        }
        if (meal.getUserId() != userId) {
            log.info("This meal don't belong userId= {}", userId);
            return null;
        } else {
            log.info("get userId={}, mealId={}", userId, mealId);
            return meal;
        }
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getALL userId={}", userId);
        return filterByPredicate(userId, m -> DateTimeUtil.isBetweenClosed(m.getDate(), startDate, endDate));
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getALL userId={}", userId);
        return filterByPredicate(userId, m -> true);
    }

    public List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        return repository.get(userId).values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

}

