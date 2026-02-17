package com.example.ratecs;

import com.example.ratecs.model.RoleType;
import com.example.ratecs.model.StaffRating;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class StaffRatingValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setupValidatorInstance() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidatorFactory() {
        validatorFactory.close();
    }

    @Test
    void invalidEmailShouldBeRejected() {
        StaffRating rating = validRating();
        rating.setEmail("invalid-email");

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void outOfRangeScoreShouldBeRejected() {
        StaffRating rating = validRating();
        rating.setClarity(11);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("clarity"));
    }

    @Test
    void missingRequiredFieldsShouldBeRejected() {
        StaffRating rating = new StaffRating();

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("roleType"));
    }

    private StaffRating validRating() {
        StaffRating rating = new StaffRating();
        rating.setName("Alice");
        rating.setEmail("alice@example.com");
        rating.setRoleType(RoleType.TA);
        rating.setClarity(7);
        rating.setNiceness(8);
        rating.setKnowledgeableScore(9);
        rating.setComment("Great support");
        return rating;
    }
}
