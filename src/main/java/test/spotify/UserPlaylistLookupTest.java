package test.spotify;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.UserPlaylistsRequest;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.SimplePlaylist;
import spotify.Spotify;

/**
 * File: UserPlaylistLookupTest.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class UserPlaylistLookupTest {
    public static void main(String[] args) {
        final Api api = Spotify.getAPI();
        final UserPlaylistsRequest request = api.getPlaylistsForUser("12132515666").build();

        try {
            final Page<SimplePlaylist> playlistsPage = request.get();

            for (SimplePlaylist playlist : playlistsPage.getItems()) {
                System.out.println(playlist.getName() + ", " + playlist.getTracks().getTotal() + " tracks.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong!" + e.getMessage());
        }
    }
}
