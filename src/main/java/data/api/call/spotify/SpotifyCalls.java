package data.api.call.spotify;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.*;
import com.wrapper.spotify.models.*;
import data.api.call.APICall;
import spotify.Spotify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * File: SpotifyCalls.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SpotifyCalls extends APICall {

    /**
     * Get a {@link Playlist} from the API
     * @param userId userId
     * @param playlistId playlistId
     * @return Optional of the Playlist
     */
    public static Optional<Playlist> getPlaylist(String userId, String playlistId){
        final Api api = Spotify.getAPI();
        final PlaylistRequest request = api.getPlaylist(userId, playlistId).build();
        try {
            return Optional.of(request.get());
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static List<Playlist> getUserPlaylists(String userId){
        final Api api = Spotify.getAPI();
        final UserPlaylistsRequest request = api.getPlaylistsForUser(userId).build();
        ArrayList<Playlist> playlists = new ArrayList<>();
        try {
            final Page<SimplePlaylist> playlistsPage = request.get();
            // TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playlists;
    }

    /**
     * Get a {@link Track} from the API
     * @param trackId trackId
     * @return Optional of the Track
     */
    public static Optional<Track> getTrack(String trackId){
        final Api api = Spotify.getAPI();
        final TrackRequest request = api.getTrack(trackId).build();
        try {
            return Optional.of(request.get());
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Get a {@link Artist} from the API
     * @param artistId artistId
     * @return Optional of the Artist
     */
    public static Optional<Artist> getArtist(String artistId){
        final Api api = Spotify.getAPI();
        final ArtistRequest request = api.getArtist(artistId).build();
        try {
            return Optional.of(request.get());
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Get a {@link Album} from the API
     * @param albumId albumId
     * @return Optional of the Album
     */
    public static Optional<Album> getAlbum(String albumId){
        final Api api = Spotify.getAPI();
        final AlbumRequest request = api.getAlbum(albumId).build();
        try {
            return Optional.of(request.get());
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
