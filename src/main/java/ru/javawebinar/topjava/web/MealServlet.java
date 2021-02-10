package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEAL = "/meals.jsp";
    private MealDao dao;

    @Override
    public void init() {
        dao = new MealDaoInMemoryImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            forward = LIST_MEAL;
            List<MealTo> mealToList = MealsUtil.filteredByStreams((List<Meal>) dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DATE);
            request.setAttribute("meals", mealToList);
        } else {
            if (action.equalsIgnoreCase("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                log.debug("redirect to meals from doGet");
                response.sendRedirect("meals");
                return;
            } else if (action.equalsIgnoreCase("edit")) {
                forward = INSERT_OR_EDIT;
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = dao.getById(id);
                request.setAttribute("meal", meal);
            } else if (action.equalsIgnoreCase("meals")) {
                forward = LIST_MEAL;
                List<MealTo> mealToList = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DATE);
                request.setAttribute("meals", mealToList);
            }
        }
        log.debug("forward to " + forward + " from doGet");
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            dao.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            dao.update(meal);
        }
        log.debug("redirect to meals from doPost");
        response.sendRedirect("meals");
    }
}

