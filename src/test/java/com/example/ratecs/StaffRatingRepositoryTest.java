package com.example.ratecs;

import com.example.ratecs.model.RoleType;
import com.example.ratecs.model.StaffRating;
import com.example.ratecs.repository.StaffRatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StaffRatingRepositoryTest {

    @Autowired
    private StaffRatingRepository staffRatingRepository;

    @Test
    void saveAndRetrieveShouldWork() {
        StaffRating saved = staffRatingRepository.save(sample("bob@example.com"));

        Optional<StaffRating> found = staffRatingRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Bob");
    }

    @Test
    void deleteShouldRemoveEntry() {
        StaffRating saved = staffRatingRepository.save(sample("charlie@example.com"));
        Long id = saved.getId();

        staffRatingRepository.deleteById(id);

        assertThat(staffRatingRepository.findById(id)).isEmpty();
    }

    private StaffRating sample(String email) {
        StaffRating rating = new StaffRating();
        rating.setName("Bob");
        rating.setEmail(email);
        rating.setRoleType(RoleType.PROF);
        rating.setClarity(8);
        rating.setNiceness(8);
        rating.setKnowledgeableScore(9);
        rating.setComment("Clear and fair");
        return rating;
    }
}
