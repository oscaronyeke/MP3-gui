package data.persistence;

/**
 * File: SQLResources.java
 * Holds all the table names in a final static string
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SQLResources {
    public static final String USER_TABLE = "dataknights_users";
    public static final String SPOTIFY_USER_TABLE = "dataknights_spotifyuser";
    public static final String ARTIST_TABLE = "dataknights_artists";
    public static final String ALBUM_TABLE = "dataknights_albums";
    public static final String PLAYLIST_TABLE = "dataknights_playlists";
    public static final String TRACK_TABLE = "dataknights_tracks";
    public static final String[] TABLES = {USER_TABLE, SPOTIFY_USER_TABLE,
            ARTIST_TABLE, ALBUM_TABLE, PLAYLIST_TABLE, TRACK_TABLE};
}
