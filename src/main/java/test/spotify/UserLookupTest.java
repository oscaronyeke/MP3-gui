package test.spotify;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.UserRequest;
import com.wrapper.spotify.models.User;
import spotify.Spotify;

/**
 * File: UserLookupTest.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class UserLookupTest {
    private static String clientId = "f26ec4669c08465c8920bcf802b22d96";
    private static String clientSecret = "f4181f6251994070883ec4d9b3850892";
    public static void main(String[] args) {
        final Api api = Spotify.getAPI();

        final UserRequest request = api.getUser("thecahyag").build();

        try {
            final User user = request.get();

            System.out.println("This user's Spotify URI is " + user.getUri());
        } catch (Exception e) {
            System.out.println("Something went wrong! " + e.getMessage());
        }
    }
}
