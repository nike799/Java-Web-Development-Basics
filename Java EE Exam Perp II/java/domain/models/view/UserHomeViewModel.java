package casebook.domain.models.view;


import casebook.domain.enums.Gender;

import java.util.List;

public class UserHomeViewModel {
    private String username;
    private Gender gender;

    public UserHomeViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
