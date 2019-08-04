package model;

import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.Track;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Optional;

import static data.persistence.PreparedStatements.*;

/**
 * File: User.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private com.wrapper.spotify.models.User spotifyUser;
    private User.Profile profile;
    private User.Settings settings;

    /**
     * Do nothing constructor does nothing
     */
    public User(){}

    /**
     * Copy constructor
     * @param user the {@link User} to copy from
     */
    public User(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.spotifyUser = user.getSpotifyUser().orElse(null);
        this.profile = user.getProfile();
        this.settings = user.getSettings();
    }

    /**
     * ONLY CALL THIS METHOD FOR NEW USERS
     * Inits default values for the {@link User.Profile} and {@link User.Settings}
     */
    public void init(){
        //this.profile = new User.Profile(this);
        this.settings = new User.Settings();
    }

    /* START GETTERS AND SETTERS */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Optional<com.wrapper.spotify.models.User> getSpotifyUser() {
        return Optional.ofNullable(spotifyUser);
    }

    public void setSpotifyUser(com.wrapper.spotify.models.User spotifyUser) {
        this.spotifyUser = spotifyUser;
    }

    public User.Profile getProfile(){
        return this.profile;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /* END GETTERS AND SETTERS */

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    /**
     * TODO
     */
    public class Profile {
        private String displayName;
        private Track favoriteTrackID;
        private Image profileImage;
        private List<Playlist> playlists;
        private String residingCountry;
        private int followers;

        /**
         * Profile constructor
         * @param user - {@link User} to display
         */
        public Profile(User user){
            this.displayName = user.getUsername();

            Optional<com.wrapper.spotify.models.User> userOptional = user.getSpotifyUser();
            if (userOptional.isPresent()){
                com.wrapper.spotify.models.User spotifyUser = userOptional.get();
                this.followers = spotifyUser.getFollowers().getTotal();
                this.residingCountry = spotifyUser.getCountry();
            } else {
                this.followers = 0;
                this.residingCountry = "";
            }
        }




    }

    /**
     * TODO
     */
    public class Settings {

        // User settings with defaults set
        private boolean displayLocation = true;
        private boolean spotifyAccountLinked = false;
        private boolean appleMusicAccountLinked = false;

        public void updateUsersSettings(User user, User.Settings settings){
            user.settings = settings;
            updateUser(user);
        }

        public boolean isDisplayLocation() {
            return displayLocation;
        }

        public void setDisplayLocation(boolean displayLocation) {
            this.displayLocation = displayLocation;
        }

        public boolean isSpotifyAccountLinked() {
            return spotifyAccountLinked;
        }

        public void setSpotifyAccountLinked(boolean spotifyAccountLinked) {
            this.spotifyAccountLinked = spotifyAccountLinked;
        }

        public boolean isAppleMusicAccountLinked() {
            return appleMusicAccountLinked;
        }

        public void setAppleMusicAccountLinked(boolean appleMusicAccountLinked) {
            this.appleMusicAccountLinked = appleMusicAccountLinked;
        }
    }
}
