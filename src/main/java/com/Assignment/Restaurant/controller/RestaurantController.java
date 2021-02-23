package com.Assignment.Restaurant.controller;

import com.Assignment.Restaurant.RestaurantApplication;
import com.Assignment.Restaurant.config.CustomUserDetails;
import com.Assignment.Restaurant.entity.User;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RestaurantController {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(RestaurantApplication.class);

    private enum ROLE {
        ANONYMOUS,
        USER,
        ADMIN;
    }


    @GetMapping("/login")
    public String login(HttpServletRequest httpServletRequest, Model model) {
        setRole(httpServletRequest, model);
        return "login";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest httpServletRequest, Model model) {
        setRole(httpServletRequest, model);
        return "register";
    }

    @GetMapping("/")
    public String getHome(HttpServletRequest httpServletRequest, Model model) {
        setRole(httpServletRequest, model);
        return "home";
    }

    @GetMapping("/table")
    public String getTables(HttpServletRequest httpServletRequest, Model model) {
        setRole(httpServletRequest, model);
        return "table";
    }


    @GetMapping("/book")
    public String getBook(HttpServletRequest httpServletRequest, Model model) {
        setRole(httpServletRequest, model);
        return "book";

    }

    @GetMapping("/admin/table")
    public String getAllTables(HttpServletRequest httpServletRequest, Model model) {
        setRole(httpServletRequest, model);
        return "all-tables";
    }

    @GetMapping("/admin/reservation")
    public String getReservations(HttpServletRequest httpServletRequest, Model model) {
        setRole(httpServletRequest, model);
        return "all-reservations";
    }


    private void setRole(HttpServletRequest httpServletRequest, Model model) {
        logger.trace("@App-RestaurantController-setRole");

        ROLE userRole = (ROLE) model.getAttribute("role");

        if (userRole != null) {
            return;
        }

        User user = (User) (httpServletRequest.getSession().getAttribute("user"));

        if (user != null) {
            model.addAttribute("role", user.isAdmin() ? ROLE.ADMIN : ROLE.USER);
            return;
        }


        logger.debug("@App-RestaurantController-setRole: No user in env");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            logger.debug("@App-RestaurantController-setRole: Anonymous User");
            model.addAttribute("role", ROLE.ANONYMOUS);
            return;
        }

        user = ((CustomUserDetails) auth.getPrincipal()).getUser();

        httpServletRequest.getSession().setAttribute("user", user);
        logger.debug("@App-RestaurantController-setRole: User stored in env");

        model.addAttribute("role", user.isAdmin() ? ROLE.ADMIN : ROLE.USER);


    }


}
