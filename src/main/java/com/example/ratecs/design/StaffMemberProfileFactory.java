package com.example.ratecs.design;

import com.example.ratecs.model.RoleType;
import org.springframework.stereotype.Component;

@Component
public class StaffMemberProfileFactory {

    public StaffMemberProfile create(RoleType roleType) {
        if (roleType == null) {
            return new StaffProfile();
        }

        return switch (roleType) {
            case TA -> new TaProfile();
            case PROF -> new ProfessorProfile();
            case INSTRUCTOR -> new InstructorProfile();
            case STAFF -> new StaffProfile();
        };
    }
}
