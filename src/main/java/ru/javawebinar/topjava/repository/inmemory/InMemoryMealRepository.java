package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal.getUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            log.info("create userId={}, meal={}", userId, meal);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (repository.get(meal.getId()).getUserId() != userId) {
            log.info("This meal don't belong userId= {}", userId);
            return null;
        } else {
            log.info("update userId={}, meal={}", userId, meal);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int userId, int mealId) {
        if (repository.get(mealId).getUserId() != userId) {
            log.info("This meal don't belong userId= {}", userId);
            return false;
        } else {
            log.info("delete userId={}, mealId={}", userId, mealId);
            return repository.remove(mealId) != null;
        }
    }

    @Override
    public Meal get(int userId, int mealId) {
        Meal meal = repository.get(mealId);
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
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getALL userId={}", userId);
        return repository.values().stream()
                .filter(m -> m.getUserId() == userId)
                .filter(m -> DateTimeUtil.isBetweenClosed(m.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

