package com.example.ratecs;

import com.example.ratecs.controller.StaffRatingController;
import com.example.ratecs.design.StaffMemberProfile;
import com.example.ratecs.design.StaffMemberProfileFactory;
import com.example.ratecs.model.RoleType;
import com.example.ratecs.model.StaffRating;
import com.example.ratecs.service.StaffRatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = StaffRatingController.class)
@Import(com.example.ratecs.controller.GlobalExceptionHandler.class)
class StaffRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffRatingService staffRatingService;

    @MockBean
    private StaffMemberProfileFactory profileFactory;

    @Test
    void getIndexShouldReturn200AndModel() throws Exception {
        StaffRating rating = sample(1L, "alice@example.com");
        when(staffRatingService.findAll()).thenReturn(List.of(rating));
        when(profileFactory.create(RoleType.TA)).thenReturn(title("Teaching Assistant"));

        mockMvc.perform(get("/ratings"))
                .andExpect(status().isOk())
                .andExpect(view().name("ratings/index"))
                .andExpect(model().attributeExists("ratings"));
    }

    @Test
    void getDetailShouldReturn200() throws Exception {
        StaffRating rating = sample(2L, "prof@example.com");
        rating.setRoleType(RoleType.PROF);
        when(staffRatingService.findById(2L)).thenReturn(rating);
        when(profileFactory.create(RoleType.PROF)).thenReturn(title("Professor"));

        mockMvc.perform(get("/ratings/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("ratings/detail"))
                .andExpect(model().attributeExists("ratingView"));
    }

    @Test
    void postCreateSuccessShouldRedirect() throws Exception {
        StaffRating saved = sample(10L, "new@example.com");
        when(staffRatingService.create(any(StaffRating.class))).thenReturn(saved);

        mockMvc.perform(post("/ratings")
                        .param("name", "New TA")
                        .param("email", "new@example.com")
                        .param("roleType", "TA")
                        .param("clarity", "8")
                        .param("niceness", "8")
                        .param("knowledgeableScore", "9")
                        .param("comment", "Helpful"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings/10"));
    }

    @Test
    void postCreateFailureShouldReturnFormWithErrors() throws Exception {
        mockMvc.perform(post("/ratings")
                        .param("name", "")
                        .param("email", "bad-email")
                        .param("roleType", "TA")
                        .param("clarity", "99")
                        .param("niceness", "8")
                        .param("knowledgeableScore", "9")
                        .param("comment", "Helpful"))
                .andExpect(status().isOk())
                .andExpect(view().name("ratings/form"))
                .andExpect(model().attributeHasFieldErrors("staffRating", "name", "email", "clarity"));
    }

    @Test
    void postUpdateSuccessShouldRedirect() throws Exception {
        StaffRating updated = sample(3L, "updated@example.com");
        when(staffRatingService.update(eq(3L), any(StaffRating.class))).thenReturn(updated);

        mockMvc.perform(post("/ratings/3")
                        .param("name", "Updated Name")
                        .param("email", "updated@example.com")
                        .param("roleType", "TA")
                        .param("clarity", "8")
                        .param("niceness", "8")
                        .param("knowledgeableScore", "9")
                        .param("comment", "Updated"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings/3"));
    }

    @Test
    void postDeleteShouldRedirectToIndex() throws Exception {
        mockMvc.perform(post("/ratings/7/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings"));
    }

    private StaffRating sample(Long id, String email) {
        StaffRating rating = new StaffRating();
        rating.setId(id);
        rating.setName("Alice");
        rating.setEmail(email);
        rating.setRoleType(RoleType.TA);
        rating.setClarity(8);
        rating.setNiceness(8);
        rating.setKnowledgeableScore(9);
        rating.setComment("Great");
        return rating;
    }

    private StaffMemberProfile title(String value) {
        return () -> value;
    }
}
