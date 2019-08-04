package test.spotify;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.PlaylistRequest;
import com.wrapper.spotify.models.Playlist;
import spotify.Spotify;

/**
 * File: PlaylistLookupTest.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class PlaylistLookupTest {

    public static void main(String[] args) {
        Api api = Spotify.getAPI();
        final PlaylistRequest request = api.getPlaylist("12132515666", "3rEuXjOsAcMAZohLM0yGlp").build();

        try {
            final Playlist playlist = request.get();

            System.out.println("Retrieved playlist " + playlist.getName());
            System.out.println(playlist.getDescription());
            System.out.println("It contains " + playlist.getTracks().getTotal() + " tracks");

        } catch (Exception e) {
            System.out.println("Something went wrong!" + e.getMessage());
            e.printStackTrace();
        }
    }
}
