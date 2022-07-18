package com.tkach.repositories;

import com.tkach.model.Ingress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngressRepository extends JpaRepository<Ingress, Integer> {
    List<Ingress> findByDateIngStartingWith(String dateIng);
}
