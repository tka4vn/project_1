package com.tkach.repositories;

import com.tkach.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    List<Users> findByNameStartingWith(String title);
    Optional<Users> findByName(String name);
}
