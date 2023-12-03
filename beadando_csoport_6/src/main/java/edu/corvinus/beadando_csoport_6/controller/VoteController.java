package edu.corvinus.beadando_csoport_6.controller;


import edu.corvinus.beadando_csoport_6.domain.User;
import edu.corvinus.beadando_csoport_6.repository.UserRepository;
import edu.corvinus.beadando_csoport_6.service.VoteService;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class VoteController {

    private final UserRepository userRepository;
    private final VoteService voteService;
    private final Logger logger = Logger.getLogger(VoteController.class.getName());

    public VoteController(UserRepository userRepository, VoteService voteService) {
        this.userRepository = userRepository;
        this.voteService = voteService;
    }

    @GetMapping("/vote")
    public String showVotePage(Model model, HttpSession session) {
        // Check if the user is in session
        String username = (String) session.getAttribute("user");
        if (username != null) {
            model.addAttribute("user", new User());
            return "vote";
        } else {
            // User is not in session, redirect to the login page
            return "redirect:/";
        }
    }

    @PostMapping("/vote")
    public String vote(@RequestParam("fav_icecream") String selectedFlavor,
                       HttpSession session, RedirectAttributes redirectAttributes) {
        // Check if the user is in session
        String username = (String) session.getAttribute("user");

        if (username != null) {
            logger.info("Received vote request for user: " + username);

            Optional<User> optionalUser = userRepository.findByName(username);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                logger.info("Retrieved user from the database: " + user);

                if (user.getVote()) {
                    logger.info("User has already voted or has a null vote, redirecting to results");
                    return "redirect:/results";
                } else {
                    user.setVote(true);
                    user.setSelectedFlavor(selectedFlavor); // Set the selected flavor
                    logger.info("Updating vote for user: " + user);

                    userRepository.save(user);

                    logger.info("Redirecting to results page");
                    return "redirect:/results";
                }
            } else {
                logger.warning("User not found in the database");
                return "error";
            }
        } else {
            // User is not in session, redirect to the login page
            return "redirect:/";
        }
    }

    @GetMapping("/results")
    public String showResultsPage(Model model) {
        logger.info("Inside showResultsPage method");
        // Fetch vote counts for each flavor
        long lemonVotes = voteService.countVotesByFlavor("lemon");
        long vanillaVotes = voteService.countVotesByFlavor("vanilla");
        long bananaVotes = voteService.countVotesByFlavor("banana");

        logger.info("Before fetching vote counts");
        // Log vote counts for debugging
        logger.info("Lemon Votes: " + lemonVotes);
        logger.info("Vanilla Votes: " + vanillaVotes);
        logger.info("Banana Votes: " + bananaVotes);
        logger.info("After fetching vote counts");

        // Add vote counts to the model
        model.addAttribute("lemonVotes", lemonVotes);
        model.addAttribute("vanillaVotes", vanillaVotes);
        model.addAttribute("bananaVotes", bananaVotes);

        List<User> allUsers = userRepository.findAll();
        allUsers.forEach(user -> logger.info("User: " + user.getName() + ", Flavor: " + user.getSelectedFlavor()));

        return "results";
    }

}
