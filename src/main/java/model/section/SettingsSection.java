package model.section;

import model.User;

/**
 * File: SettingsSection.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SettingsSection extends Section {

    private User user;
    private User.Settings settings;

    public SettingsSection(User user){
        this.user = user;
        this.settings = user.getSettings();
    }

    public User getUser() {
        return user;
    }

    public User.Settings getSettings() {
        return settings;
    }
}
