package edu.corvinus.beadando_csoport_6.repository;

import edu.corvinus.beadando_csoport_6.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    long countBySelectedFlavor(String flavor);


    List<User> findAll();
}


