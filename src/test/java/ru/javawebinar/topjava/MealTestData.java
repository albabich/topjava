package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int MEAL2_ID = START_SEQ + 3;
    public static final int MEAL3_ID = START_SEQ + 4;
    public static final int MEAL4_ID = START_SEQ + 5;
    public static final int MEAL5_ID = START_SEQ + 6;

    public static final Meal userMeal1 = new Meal(MEAL1_ID, LocalDateTime.parse("2020-01-30T10:00:00"), "Завтрак", 510);
    public static final Meal userMeal2 = new Meal(MEAL2_ID, LocalDateTime.parse("2020-01-30T14:00:00"), "Обед", 1000);
    public static final Meal adminMeal3 = new Meal(MEAL3_ID, LocalDateTime.parse("2020-01-30T13:00:00"), "Обед", 1200);
    public static final Meal userMeal4 = new Meal(MEAL4_ID, LocalDateTime.parse("2020-01-30T20:00:00"), "Ужин", 500);
    public static final Meal userMeal5 = new Meal(MEAL5_ID, LocalDateTime.parse("2020-01-31T10:00:00"), "Завтрак", 550);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "Новая еда", 700);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal2);
        updated.setDateTime(LocalDateTime.parse("2020-01-30T14:30:00"));
        updated.setCalories(1100);
        updated.setDescription("Обед_updated");
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
