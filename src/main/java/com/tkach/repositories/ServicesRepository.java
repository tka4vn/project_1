package com.tkach.repositories;

import com.tkach.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer> {
        Optional<Services> findByName(String name);
        }
