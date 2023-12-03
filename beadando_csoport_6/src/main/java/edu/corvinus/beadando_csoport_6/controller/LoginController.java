package edu.corvinus.beadando_csoport_6.controller;

import edu.corvinus.beadando_csoport_6.domain.User;
import edu.corvinus.beadando_csoport_6.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @GetMapping("/")
    public String showLoginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }


    @PostMapping("/")
    public String Login(User user, BindingResult bindingResult,
                        HttpServletRequest request, Model model) {

        String enteredUsername = user.getName();
        String enteredPassword = user.getPassword();

        Optional<User> userOptional = userRepository.findByName(enteredUsername);
        if("Admin".equals(enteredUsername)){
            HttpSession session = request.getSession();
            session.setAttribute("user", enteredUsername);
            return "redirect:/admin";
        } else{
            if (bindingResult.hasErrors()) {
                // If there are validation errors, return to the login page with error messages

                return "login";
            }

            //Optional<User> userOptional = userRepository.findByName(enteredUsername);
            if (userOptional.isPresent()) {
                User databaseUser = userOptional.get();
                if (enteredPassword.equals(databaseUser.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", enteredUsername);
                    return "vote";
                    }
                }
            }
            // Authentication failed, show login page again
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login";
        }
}


