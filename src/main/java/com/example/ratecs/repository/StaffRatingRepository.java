package com.example.ratecs.repository;

import com.example.ratecs.model.StaffRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRatingRepository extends JpaRepository<StaffRating, Long> {
    Optional<StaffRating> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
