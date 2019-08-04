package data.persistence;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static data.persistence.SQLResources.*;

/**
 * File: SQLManager.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SQLManager {

    private static PGPoolingDataSource source = null;

    /**
     * SQLManager constructor
     */
    private SQLManager(){
        initSettings();
    }

    /**
     * Get a connection to the database with the pre-defined credentials
     * @return Resulting {@link Connection}
     */
    public static Connection getConnection() throws SQLException {
        if (source == null){
            source = new PGPoolingDataSource();
            source.setUrl("jdbc:postgresql://reddwarf.cs.rit.edu:5432/postgres");
            source.setUser("p32004j");
            source.setPassword("af1vai3leeWaer9eiT0E");
        }
        return source.getConnection();
    }

    /**
     * Create tables if they don't exist
     */
    private void initSettings(){
        try {
            PreparedStatement createUserTable = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "(" +
                            "username VARCHAR(20), " +
                            "password VARCHAR(20)," +
                            "firstName VARCHAR(20),"+
                            "lastName VARCHAR(20),"+
                            "href CHAR(20),"+
                            "id INT PRIMARY KEY,"+
                            "PRIMARY KEY(id)"+
                            ")");
            createUserTable.executeUpdate();
            PreparedStatement createArtistTable = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + ARTIST_TABLE + "(" +
                            "genres VARCHAR(20), " +
                            "href VARCHAR(20)," +
                            "name VARCHAR(20),"+
                            "lastName VARCHAR(20),"+
                            "popularity INT,"+
                            "uri VARCHAR(20),"+
                            "followers INT,"+
                            "EXTERNALURL VARCHAR(20),"+
                            "id INT PRIMARY KEY,"+
                            "PRIMARY KEY(id)"+
                            ")");
            createArtistTable.executeUpdate();
            PreparedStatement createTrackTable = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + TRACK_TABLE + "(" +
                            "href VARCHAR(20)," +
                            "name VARCHAR(20),"+
                            "popularity INT,"+
                            "uri VARCHAR(20),"+
                            "discNumber INT,"+
                            "trackNumber INT,"+
                            "explicit INT,"+
                            "duration TIME,"+
                            "previewurl VARCHAR(20),"+
                            "id INT PRIMARY KEY,"+
                            "PRIMARY KEY(id)"+
                            ")");
            createTrackTable.executeUpdate();
            PreparedStatement createAlbumTable = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + ALBUM_TABLE + "(" +
                            "href VARCHAR(20)," +
                            "name VARCHAR(20),"+
                            "popularity INT,"+
                            "uri VARCHAR(20),"+
                            "genre VARCHAR(20),"+
                            "images VARCHAR(20),"+
                            "Albumtype VARCHAR(20),"+
                            "releaseDate DATE,"+
                            "id INT PRIMARY KEY,"+
                            "PRIMARY KEY(id)"+
                            ")");
            createAlbumTable.executeUpdate();
            PreparedStatement createPlaylistTable = getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + PLAYLIST_TABLE + "(" +
                            "href VARCHAR(20)," +
                            "name VARCHAR(20),"+
                            "popularity INT,"+
                            "uri VARCHAR(20),"+
                            "collaboration VARCHAR(50),"+
                            "images VARCHAR(20),"+
                            "publicAccess VARCHAR(20),"+
                            "followers INT,"+
                            "description VARCHAR(100),"+
                            "id INT PRIMARY KEY,"+
                            "PRIMARY KEY(id)"+
                            ")");
            createPlaylistTable.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
