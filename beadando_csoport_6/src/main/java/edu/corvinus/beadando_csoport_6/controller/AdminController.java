package edu.corvinus.beadando_csoport_6.controller;

import edu.corvinus.beadando_csoport_6.domain.User;
import edu.corvinus.beadando_csoport_6.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;


@Controller
public class AdminController {

    private final UserRepository userRepository;

    @Autowired
    // annotation is used for automatic dependency injection
    public AdminController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        // finding all the users from the dabase and display their property values in admin.html
        List<User> users = userRepository.findAll();
        model.addAttribute("userList", users);
        return "admin";
    }



}
