package edu.corvinus.beadando_csoport_6.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // if the user clicks logout invalidate the session and returns them to login.html
        session.invalidate();
        return "redirect:/";
    }
}
