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
            // user is not in session, redirect to the login page
            return "redirect:/";
        }
    }

    @PostMapping("/vote")
    public String vote(@RequestParam("fav_icecream") String selectedFlavor, HttpSession session) {
        // requesting paramater from vote.html for favorite ice cream

        // getting the username from the session
        String username = (String) session.getAttribute("user");

        // If the user is not in session redirect to the login page
        if (username == null) {
            return "redirect:/";
        }

        logger.info("Received vote request for user: " + username);

        // find the user by its entered username in the database
        Optional<User> optionalUser = userRepository.findByName(username);

        // if the user is not found in the database, handle the error
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found in the database"));

        logger.info("Retrieved user from the database: " + user);

        // checking if user has already voted
        if (user.getVote()) {
            logger.info("User has already voted or has a null vote, redirecting to results");
        } else {
            // if the user haven't voted before it sets the vote property to true
            user.setVote(true);
            // it saves what flavor the user has voted for
            user.setSelectedFlavor(selectedFlavor);
            logger.info("Updating vote for user: " + user);
            // updating the database have voted column and users favorite flavor
            userRepository.save(user);
        }

        logger.info("Redirecting to results page");
        return "redirect:/results";
    }

    @GetMapping("/results")
    public String showResultsPage(Model model) {

        logger.info("Inside showResultsPage method");

        // fetching vote counts for each flavor using the business logic defined in voteService
        long lemonVotes = voteService.countVotesByFlavor("lemon");
        long vanillaVotes = voteService.countVotesByFlavor("vanilla");
        long bananaVotes = voteService.countVotesByFlavor("banana");

        logger.info("Before fetching vote counts");
        // log vote counts for debugging
        logger.info("Lemon Votes: " + lemonVotes);
        logger.info("Vanilla Votes: " + vanillaVotes);
        logger.info("Banana Votes: " + bananaVotes);
        logger.info("After fetching vote counts");

        // add vote counts to the model so it can be used in results.html
        model.addAttribute("lemonVotes", lemonVotes);
        model.addAttribute("vanillaVotes", vanillaVotes);
        model.addAttribute("bananaVotes", bananaVotes);

        // logging for debugging
        List<User> allUsers = userRepository.findAll();
        allUsers.forEach(user -> logger.info("User: " + user.getName() + ", Flavor: " + user.getSelectedFlavor()));

        return "results";
    }

}
