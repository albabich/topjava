package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal getByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserId(Sort sortDatetime, int userId);

    @Transactional
    int deleteByIdAndUserId(int id, int userId);

    List<Meal> findByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserId(Sort sortDatetime, LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}
