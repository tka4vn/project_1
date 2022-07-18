package com.tkach.repositories;

import com.tkach.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
        Optional<Position> findByName(String name);
        }
