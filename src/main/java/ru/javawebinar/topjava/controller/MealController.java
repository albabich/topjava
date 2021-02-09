package ru.javawebinar.topjava.controller;

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

public class MealController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEAL = "/listMeal.jsp";
    private MealDao dao;

    public MealController() {
        super();
        dao = new MealDaoInMemoryImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteMeal(id);
            forward = LIST_MEAL;
            List<MealTo> mealToList = MealsUtil.filteredByStreams((List<Meal>) dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DATE);
            request.setAttribute("meals", mealToList);
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = dao.getMealById(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listmeal")){
            forward = LIST_MEAL;
            List<MealTo> mealToList = MealsUtil.filteredByStreams((List<Meal>) dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DATE);
            request.setAttribute("meals", mealToList);
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime"),dtf),request.getParameter("description"),Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if(id == null || id.isEmpty())
        {
            dao.addMeal(meal);
        }
        else
        {
            meal.setId(Integer.parseInt(id));
            dao.updateMeal(meal);
        }
        List<MealTo> mealToList = MealsUtil.filteredByStreams((List<Meal>) dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DATE);
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        request.setAttribute("meals", mealToList);
        view.forward(request, response);
    }
}

