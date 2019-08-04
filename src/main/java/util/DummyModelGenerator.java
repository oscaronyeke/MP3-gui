package util;

import com.wrapper.spotify.models.*;
import com.wrapper.spotify.models.Album;
import com.wrapper.spotify.models.SimpleArtist;
import model.User;
import sun.java2d.cmm.Profile;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * File: util.DummyModelGenerator.java
 * Creates an object of a certain type with attributes. This is only used for testing purpose.
 * In production the object would be obtained from a database query (which would be obtained
 * from the Spotify API).
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 * @author Oscar Onyeke (oscaronyeke@yahoo.com)
 * @author Luke Heary (lukeheary@gmail.com)
 */
public class DummyModelGenerator {

    public static Album dummyAlbum(){
        Album album = new Album();
        album.setHref("sample.com");
        album.setId("Swifty");
        SimpleArtist artist = new SimpleArtist();
        artist.setName("Taylor");
        ArrayList<SimpleArtist> artists = new ArrayList<>();
        artists.add(artist);
        album.setArtists(artists);
        album.setReleaseDate("mon 26 004 1997 12:12:09 pst");
        LinkedList<String> gen = new LinkedList<>();
        gen.addFirst("pop");
        album.setGenres(gen);
        LinkedList<Image> imalist = new LinkedList<>();
        Image image = new Image();
        image.setUrl("beeep");
        imalist.add(image);
        album.setImages(imalist);
        Page<SimpleTrack> page =new Page<>();
        LinkedList<SimpleTrack> list =new LinkedList<>();
        SimpleTrack track = new SimpleTrack();
        list.add(track);
        page.setItems(list);
        album.setTracks(page);
        return album;
    }


    public static Artist dummyArtist(){
        Artist artist = new Artist();
        artist.setId("Bill");
        artist.setHref("sample.com");
        artist.setName("Billy Joel");
        ArrayList<String> gens = new ArrayList<>();
        gens.add("rock");
        gens.add("soft rock");
        gens.add("pop");
        artist.setGenres(gens);
        LinkedList<Image> a =new LinkedList<>();
        Image image = new Image();
        image.setUrl("adasdc");
        a.addFirst(image);
        Followers followers = new Followers();
        followers.setTotal(123334);
        artist.setFollowers(followers);
        artist.setImages(a);
        artist.setPopularity(4);
        return artist;
    }

    public static PlaylistTrack dummyPlaylistTrackOne(){
        PlaylistTrack track = new PlaylistTrack();
        track.setTrack(dummyTrackOne());
        return track;
    }

    public static PlaylistTrack dummyPlaylistTrackTwo(){
        PlaylistTrack track = new PlaylistTrack();
        track.setTrack(dummyTrackTwo());
        return track;
    }

    public static Playlist dummyPlaylist(){
        Playlist playlist = new Playlist();
        Page<PlaylistTrack> playlistTracks = new Page<>();
        ArrayList<PlaylistTrack> playlistTracks1 = new ArrayList<>();
        playlistTracks1.add(dummyPlaylistTrackOne());
        playlistTracks1.add(dummyPlaylistTrackTwo());
        playlistTracks.setItems(playlistTracks1);
        playlist.setTracks(playlistTracks);
        playlist.setDescription("Test description for playlist");
        playlist.setHref("sample href");
        playlist.setId("sample id");
        playlist.setName("Playlist Name");
        playlist.setPublicAccess(true);
        playlist.setUri("sample uri");
        playlist.setOwner(dummySpotifyUser());
        Followers followers =new Followers();
        followers.setTotal(123);
        playlist.setFollowers(followers);
        Page<PlaylistTrack> trackPage = new Page<>();
        playlist.setTracks(trackPage);
        return playlist;
    }

    public static SimpleArtist dummySimpleArtist(){
        SimpleArtist artist = new SimpleArtist();
        artist.setId("sample id");
        artist.setName("sample name");
        artist.setHref("sample href");
        artist.setUri("sample uri");
        return artist;
    }

    public static Track dummyTrackOne(){
        Track track = new Track();
        ArrayList<SimpleArtist> artistList = new ArrayList<>();
        artistList.add(dummySimpleArtist());
        track.setArtists(artistList);
        track.setName("track one name");
        track.setPreviewUrl("asd");
        track.setDuration(133);
        track.setExplicit(false);
        track.setHref("hv");
        track.setTrackNumber(5);
        track.setPopularity(4);
        SimpleAlbum album = new SimpleAlbum();
        track.setAlbum(album);
        track.setId("12331");
        return track;
    }

    public static Track dummyTrackTwo(){
        Track track = new Track();
        ArrayList<SimpleArtist> artistList = new ArrayList<>();
        artistList.add(dummySimpleArtist());
        track.setArtists(artistList);
        track.setName("track two name");
        return track;
    }

    public static model.User dummyUser(){
        model.User user = new model.User();
        user.setUsername("TheCahyag");
        user.setPassword("password");
        user.setSpotifyUser(dummySpotifyUser());
        user.setFirstName("Brandon");
        user.setLastName("Bires-Navel");
        user.init();
        return user;
    }

    public static com.wrapper.spotify.models.User dummySpotifyUser(){
        com.wrapper.spotify.models.User user = new com.wrapper.spotify.models.User();
        user.setId("12132515666"); // ID for Brandon Navel
        user.setDisplayName("TheCahyag");
        user.setEmail("brandonsemail@gmail.com");
        user.setHref("brandonsurl.com");
        user.setCountry("USA");
        ArrayList<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setUrl("www.sampleimageurl.com");
        images.add(image);
        user.setImages(images);
        user.setUri("brandonsuri:wewoow");
        Followers followers = new Followers();
        followers.setTotal(5);
        user.setFollowers(followers);
        return user;
    }
}
