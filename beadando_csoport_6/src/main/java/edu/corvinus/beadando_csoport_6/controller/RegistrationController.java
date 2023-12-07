package edu.corvinus.beadando_csoport_6.controller;

import edu.corvinus.beadando_csoport_6.domain.User;
import edu.corvinus.beadando_csoport_6.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {
    private final UserRepository userRepository;

    @Autowired
    // annotation is used for automatic dependency injection
    public RegistrationController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    // using the validations defined in User
    public String register(@Valid User user, BindingResult bindingResult, Model model,
                           HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // finding out if the user is already registered with that username
        final boolean userIsRegistered = userRepository.findByName(user.getName()).isPresent();

        if (!userIsRegistered) {
            // if the user with the username does not exists it saves it as a new record in the database with JPA through the UserDepository
            // this is where it sets their role property to 'USER' and their vote proptery to false which means they havent voted yet
            userRepository.save(new User(user.getName(), user.getPassword(), "USER", false));
            String enteredUsername = user.getName();
            //session for freshly registered user
            HttpSession session = request.getSession();
            session.setAttribute("user", enteredUsername);
            return "vote";
        } else {
            // if the user tries to register with a username which already exists
            model.addAttribute("errorMessage", "User with name " + user.getName() + " is already registered.");
            return "register";
        }
    }
}
