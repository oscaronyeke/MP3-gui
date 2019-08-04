package util;

import com.wrapper.spotify.models.*;
import data.LoadData;
import data.api.call.spotify.SpotifyCalls;

import java.util.Optional;

/**
 * File: Util.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class Util {
    private static String USER_ID = "12132515666";
    private static String[] PLAYLIST_IDS = {"3rEuXjOsAcMAZohLM0yGlp", "084hTRtXdHcPu6Xdp7ffsx", "00wDVJPcXtafCGtm8eSkvz", "38fKLnd2rWKb7clNdAww9R"};
    private static String[] ALBUM_IDS = {"6DEjYFkNZh67HP7R9PSZvv", "1gMxiQQSg5zeu4htBosASY", "2QJmrSgbdM35R67eoGQo4j", "52kvZcbEDm0v2kWZQXjuuA"};

    /**
     * Fill database with sample data
     */
    public static void fillDatabase(){
        for (int i = 0; i < PLAYLIST_IDS.length; i++) {
            Optional<Playlist> playlistOptional = SpotifyCalls.getPlaylist(USER_ID, PLAYLIST_IDS[i]);
            if (playlistOptional.isPresent()){
                LoadData.loadPlaylist(playlistOptional.get());
            } else {
                System.err.println("Playlist '" + PLAYLIST_IDS[i] + "' couldn't be " +
                        "found, or encounter an error in the request.");
            }
        }
//        Optional<Playlist> playlistOptional = SpotifyCalls.getPlaylist("spotify", "37i9dQZEVXcKdAD20DYzFs");
//        playlistOptional.ifPresent(LoadData::loadPlaylist);

//        // Artists
//        Optional<Artist> artistOptional = SpotifyCalls.getArtist("06HL4z0CvFAxyc27GXpf02");
//        artistOptional.ifPresent(PreparedStatements::createArtist);
//
//        Optional<Artist> artistOptional1 = SpotifyCalls.getArtist("4AK6F7OLvEQ5QYCBNiQWHq");
//        artistOptional1.ifPresent(PreparedStatements::createArtist);
//
//        // Albums
//        for (int i = 0; i < ALBUM_IDS.length; i++) {
//            Optional<Album> albumOptional = SpotifyCalls.getAlbum(ALBUM_IDS[i]);
//            albumOptional.ifPresent(PreparedStatements::createAlbum);
//
//        }
    }

    /**
     * Convert a {@link SimpleTrack} to a {@link Track}
     * @param oldTrack the simple track
     * @return new Track that contains all information from the simple track
     */
    public static Track simpleTrackToTrack(SimpleTrack oldTrack){
        Track track = new Track();
        track.setAvailableMarkets(oldTrack.getAvailableMarkets());
        track.setDiscNumber(oldTrack.getDiscNumber());
        track.setDuration(oldTrack.getDuration());
        track.setExplicit(oldTrack.isExplicit());
        track.setExternalUrls(oldTrack.getExternalUrls());
        track.setHref(oldTrack.getHref());
        track.setId(oldTrack.getId());
        track.setName(oldTrack.getName());
        track.setPreviewUrl(oldTrack.getPreviewUrl());
        track.setTrackNumber(oldTrack.getTrackNumber());
        track.setType(oldTrack.getType());
        track.setUri(oldTrack.getUri());
        return track;
    }

}
