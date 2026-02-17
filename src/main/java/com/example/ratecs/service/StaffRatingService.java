package com.example.ratecs.service;

import com.example.ratecs.model.StaffRating;
import com.example.ratecs.repository.StaffRatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffRatingService {

    private final StaffRatingRepository staffRatingRepository;

    public StaffRatingService(StaffRatingRepository staffRatingRepository) {
        this.staffRatingRepository = staffRatingRepository;
    }

    public List<StaffRating> findAll() {
        return staffRatingRepository.findAll();
    }

    public StaffRating findById(Long id) {
        return staffRatingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Staff rating not found for id: " + id));
    }

    public StaffRating create(StaffRating staffRating) {
        if (staffRatingRepository.existsByEmailIgnoreCase(staffRating.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }
        return staffRatingRepository.save(staffRating);
    }

    public StaffRating update(Long id, StaffRating updated) {
        StaffRating existing = findById(id);

        if (staffRatingRepository.existsByEmailIgnoreCaseAndIdNot(updated.getEmail(), id)) {
            throw new DuplicateEmailException("Email already exists");
        }

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setRoleType(updated.getRoleType());
        existing.setClarity(updated.getClarity());
        existing.setNiceness(updated.getNiceness());
        existing.setKnowledgeableScore(updated.getKnowledgeableScore());
        existing.setComment(updated.getComment());

        return staffRatingRepository.save(existing);
    }

    public void delete(Long id) {
        if (!staffRatingRepository.existsById(id)) {
            throw new NotFoundException("Staff rating not found for id: " + id);
        }
        staffRatingRepository.deleteById(id);
    }
}
