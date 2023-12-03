package edu.corvinus.beadando_csoport_6.service;


import edu.corvinus.beadando_csoport_6.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final UserRepository userRepository;

    @Autowired
    public VoteService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public long countVotesByFlavor(String flavor) {

        return userRepository.countBySelectedFlavor(flavor);
    }
}
