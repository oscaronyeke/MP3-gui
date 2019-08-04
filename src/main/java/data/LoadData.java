package data;

import com.wrapper.spotify.models.*;
import data.persistence.PreparedStatements;
import util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * File: LoadData.java
 * Load data from a request to the database (not loading data for the application)
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class LoadData {

    public static void loadPlaylist(Playlist playlist){
        // Add tracks first
        List<PlaylistTrack> playlistTracks = playlist.getTracks().getItems();
        List<Track> tracks = new ArrayList<>();
        playlistTracks.forEach(track -> tracks.add(track.getTrack()));
        tracks.forEach(LoadData::loadTrack);

        PreparedStatements.createPlaylist(playlist);
    }

    public static void loadTrack(Track track){
        PreparedStatements.createTrack(track);
    }

    public static void loadAlbum(Album album){
        // Add tracks first
        List<SimpleTrack> playlistTracks = album.getTracks().getItems();
        List<Track> tracks = new ArrayList<>();
        playlistTracks.forEach(track -> tracks.add(Util.simpleTrackToTrack(track)));
        tracks.forEach(LoadData::loadTrack);

        PreparedStatements.createAlbum(album);
    }

    public static void loadArtist(Artist artist){
        PreparedStatements.createArtist(artist);
    }

}
