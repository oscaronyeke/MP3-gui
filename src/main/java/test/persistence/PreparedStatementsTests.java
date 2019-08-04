package test.persistence;


import com.wrapper.spotify.models.Playlist;

import static util.DummyModelGenerator.*;

import static data.persistence.PreparedStatements.*;

/**
 * File: PreparedStatementsTests.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 * @author Oscar Onyeke        (oscaronyeke@yahoo.com)
 */
public class PreparedStatementsTests {
    public static void main(String[] args) {
        /* Test create statements */

        // Users (Spotify user needs to be added before the regular user)
        testCreateSpotifyUser();
        testCreateUser();

        getSpotifyUser(dummySpotifyUser().getId());


        // testing getArtist
        testgetArtist();

        //testing getAlbum
        testgetAlbum();

        //testing getPLaylist
        testgetPLaylist();

        //testing getTrack
        testgetTrack();

        //testing createPlaylist
        testcreatePlaylist();

        //testing createTrack
        testcreateTrack();

        //testing create Artist
        testcreateArtist();

        //testing createAlbum
        testcreateAlbum();
        /* Test update statements */

        /* Test delete statements */
    }

    private static void testCreateUser(){
        createUser(dummyUser());
    }

    private static void testcreatePlaylist(){createPlaylist(dummyPlaylist());}

    private static void testcreateTrack(){createTrack(dummyTrackOne());}

    private static void testcreateAlbum(){createAlbum(dummyAlbum());}

    private static void testcreateArtist(){createArtist(dummyArtist());}

    private static void testCreateSpotifyUser(){
        createSpotifyUser(dummySpotifyUser());
    }

    private static void testgetArtist(){
        getArtist(dummyArtist().getId());
    }

    private static void testgetAlbum(){
        getAlbum(dummyAlbum().getId());
    }

    private static void testgetTrack(){
        getTrack(dummyTrackOne().getId());
    }

    private static void testgetPLaylist(){
        Playlist playlist= new Playlist();
        playlist = getPlaylist("084hTRtXdHcPu6Xdp7ffsx").get();
        System.out.println(playlist.getName());
    }

}
