package model.section;


import model.User;

/**
 * File: ProfileSection.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class ProfileSection extends Section {
    private User user;

    public ProfileSection(User user){
        this.user = user;
    }

    public User.Profile getProfile() {
        return this.user.getProfile();
    }
}
