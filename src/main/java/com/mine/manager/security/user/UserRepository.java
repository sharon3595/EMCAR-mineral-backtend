package com.mine.manager.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsernameAndStateTrue(String username);

    boolean existsByUsernameAndStateTrue(String username);

  boolean existsByUsernameAndIdNot( String username, Integer id);
}
