package test.spotify;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.TrackRequest;
import com.wrapper.spotify.models.Track;
import spotify.Spotify;

/**
 * File: TrackLookUp.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class TrackLookUp {
    public static void main(String[] args) {
        Api api = Spotify.getAPI();
        final TrackRequest request = api.getTrack("0eGsygTp906u18L0Oimnem").build();

        try {
            final Track track = request.get();
            System.out.println("Retrieved track " + track.getName());
            System.out.println("Its popularity is " + track.getPopularity());

            if (track.isExplicit()) {
                System.out.println("This track is explicit!");
            } else {
                System.out.println("It's OK, this track isn't explicit.");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong!" + e.getMessage());
        }
    }
}
