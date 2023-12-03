package edu.corvinus.beadando_csoport_6.controller;

import edu.corvinus.beadando_csoport_6.domain.User;
import edu.corvinus.beadando_csoport_6.repository.UserRepository;
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
    //private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            //model.addAttribute("user", user);
            return "register";
        }


        final boolean userIsRegistered = userRepository.findByName(user.getName()).isPresent();
        if (!userIsRegistered) {
            //userRepository.save(user);
            userRepository.save(new User(user.getName(), user.getPassword(), "USER", false));
            return "vote";
        } else {
            // if the user tries to register with a username which already exists
            model.addAttribute("errorMessage", "User with name " + user.getName() + " is already registered.");
            return "register";
        }
    }
}
