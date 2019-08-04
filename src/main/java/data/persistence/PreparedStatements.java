package data.persistence;

import com.wrapper.spotify.models.*;
import data.api.call.spotify.SpotifyCalls;
import model.User;

import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static data.persistence.SQLResources.*;

/**
 * File: PreparedStatements.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 * @author Oscar Onyeke (oscaronyeke@yahoo.com)
 */
public class PreparedStatements {

    /* Start SQL User Operations */

    public static void createUser(User user){
        String spotifyId = "";
        Optional<com.wrapper.spotify.models.User> userOptional = user.getSpotifyUser();
        if (userOptional.isPresent()){
            spotifyId = userOptional.get().getId();
        }
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("INSERT INTO " + USER_TABLE + " VALUES (?, ?, ?, ?, ?) ON CONFLICT DO NOTHING ");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, spotifyId);
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());

            // Need to save the Users Settings and Profile data TODO
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateUser(User user){
        String spotifyId = "";
        Optional<com.wrapper.spotify.models.User> userOptional = user.getSpotifyUser();
        if (userOptional.isPresent()){
            spotifyId = userOptional.get().getId();
        }
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("UPDATE " + USER_TABLE + " WHERE username = " + user.getUsername() +
                            " SET password = ?, fk_spotifyuser = ?, firstname = ?, lastname = ?");
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, spotifyId);
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());

            // Need to update the Users Settings and Profile data TODO
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Optional<User> getUser(String username){
        try (Connection conn = SQLManager.getConnection()) {
            PreparedStatement preparedStatement = conn
                    .prepareStatement("SELECT * FROM "+ USER_TABLE + " WHERE username = ?");

            // Execute query and obtain result
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                // ResultSet had an entry
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                // Query for the Spotify User
                user.setSpotifyUser(getSpotifyUser(rs.getString("fk_spotifyuser")).orElse(null));
                return Optional.of(user);
            } else {
                // No entry
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static boolean approveUserLogin(String username, String password){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("SELECT password FROM " + USER_TABLE + " WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next() && rs.getString("password").equals(password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* End SQL User Operations */

    /* Start SQL Spotify User Operations */

    public static void createSpotifyUser(com.wrapper.spotify.models.User user){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("INSERT INTO " + SPOTIFY_USER_TABLE + " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING");
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getDisplayName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getHref());
            preparedStatement.setString(5, user.getCountry());
            // Images
            List<Image> images = user.getImages();
            ArrayList<String> imgList = new ArrayList<>();
            images.forEach(image -> imgList.add(image.getUrl())); // Put all image urls in a list
            Array array = conn.createArrayOf("VARCHAR", imgList.toArray());
            preparedStatement.setArray(6, array);
            preparedStatement.setString(7, user.getUri());
            preparedStatement.setInt(8, user.getFollowers().getTotal());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateSpotifyUser(com.wrapper.spotify.models.User user){
        String id = user.getId();
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("UPDATE " + SPOTIFY_USER_TABLE + " SET id = ? WHERE id ="+id);
            preparedStatement.setString(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Optional<com.wrapper.spotify.models.User> getSpotifyUser(String id){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("SELECT * FROM "+ SPOTIFY_USER_TABLE + " WHERE id = ?");

            // Execute query and obtain result
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                // goes here if there is a valid entry
                com.wrapper.spotify.models.User spotifyUser = new com.wrapper.spotify.models.User();
                spotifyUser.setId(rs.getString("id"));
                spotifyUser.setDisplayName(rs.getString("displayname"));
                spotifyUser.setEmail(rs.getString("email"));
                spotifyUser.setHref(rs.getString("href"));
                spotifyUser.setCountry(rs.getString("country"));
                // Images
                ArrayList<Image> images = new ArrayList<>();
                String[] imageURLs = ((String[]) rs.getArray("images").getArray());
                for (String url :
                        imageURLs) {
                    Image tmp = new Image();
                    tmp.setUrl(url);
                    images.add(tmp);
                }
                spotifyUser.setImages(images);
                spotifyUser.setUri(rs.getString("uri"));
                // Followers
                Followers followers = new Followers();
                followers.setTotal(rs.getInt("followers"));
                spotifyUser.setFollowers(followers);

                return Optional.of(spotifyUser);
            } else {
                // No entry
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /* End SQL Spotify User Operations */

    /* Start SQL Albums Operations */

    public static void createAlbum(Album album){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("INSERT INTO " + ALBUM_TABLE + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING");
            preparedStatement.setString(1, album.getId());
            preparedStatement.setString(2, album.getName());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                java.util.Date tmpDate = df.parse(album.getReleaseDate());
                date = new Date(tmpDate.getTime());
            } catch (ParseException e){
                e.printStackTrace();
            }
            preparedStatement.setDate(3, date);
            preparedStatement.setArray(4, conn.createArrayOf("VARCHAR", album.getGenres().toArray()));
            preparedStatement.setString(5, album.getHref());
            preparedStatement.setString(6, album.getUri());
            preparedStatement.setInt(7, album.getPopularity());
            // Images
            List<Image> images = album.getImages();
            ArrayList<String> imgList = new ArrayList<>();
            images.forEach(image -> imgList.add(image.getUrl())); // Put all image urls in a list
            preparedStatement.setArray(8, conn.createArrayOf("VARCHAR", imgList.toArray()));
            // Artists
            List<SimpleArtist> artists = album.getArtists();
            ArrayList<String> artistIds = new ArrayList<>();
            for (SimpleArtist artist :
                    artists) {
                artistIds.add(artist.getId());
                Optional<Artist> artistOptional = SpotifyCalls.getArtist(artist.getId());
                artistOptional.ifPresent(PreparedStatements::createArtist);
            }
            preparedStatement.setArray(9, conn.createArrayOf("VARCHAR", artistIds.toArray()));
            // Tracks
            List<SimpleTrack> tracks = album.getTracks().getItems();
            ArrayList<String> trackIds = new ArrayList<>();
            for (SimpleTrack track :
                    tracks) {
                trackIds.add(track.getId());
                Optional<Track> trackOptional = SpotifyCalls.getTrack(track.getId());
                trackOptional.ifPresent(PreparedStatements::createTrack);
            }
            preparedStatement.setArray(10, conn.createArrayOf("VARCHAR", trackIds.toArray()));
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Optional<Album> getAlbum(String id){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("SELECT * FROM "+ ALBUM_TABLE + " WHERE id = ?");

            // Execute query and obtain result
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                // ResultSet had an entry
                Album album = new Album();
                album.setId(rs.getString("id"));
                album.setName(rs.getString("name"));
                album.setReleaseDate(rs.getString("release_date"));
                //adding the genres
                ArrayList<String> genres = new ArrayList<>();
                genres.add(rs.getString("genres"));
                album.setGenres(genres);
                album.setHref(rs.getString("href"));
                album.setUri(rs.getString("uri"));
                album.setPopularity(rs.getInt("popularity"));
                //setting Images

                ArrayList<Image> images = new ArrayList<>();
                String[]imageURLs = ((String[])rs.getArray("images").getArray());
                for(String url : imageURLs){
                    Image temp = new Image();
                    temp.setUrl(url);
                    images.add(temp);
                }
                album.setImages(images);

                //setting artists
                List<SimpleArtist> artists = new ArrayList<>();
                String[] artistsId = ((String[])rs.getArray("fks_artists").getArray());
                for(String art: artistsId){
                    SimpleArtist temp = new SimpleArtist();
                    temp.setId(art);
                    artists.add(temp);
                }
                album.setArtists(artists);

                //setting Tracks
                Page<SimpleTrack> trackss = new Page<>();
                List<SimpleTrack> tracks = new ArrayList<>();
                String[] trackids = ((String[])rs.getArray("fks_tracks").getArray());
                for(String track: trackids){
                    SimpleTrack temp = new SimpleTrack();
                    temp.setId(track);
                    tracks.add(temp);
                }
                trackss.setItems(tracks);
                album.setTracks(trackss);
                // Query for the Spotify User
                return Optional.of(album);
            } else {
                // No entry
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static List<Album> getAllAlbumNameAndId(){
        ArrayList<Album> albums = new ArrayList<>();
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, name FROM " + ALBUM_TABLE);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Album album = new Album();
                album.setId(rs.getString("id"));
                album.setName(rs.getString("name"));
                albums.add(album);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return albums;
    }

    // No update method because we are getting data from Spotify and shouldn't ever need to be updated
    // No delete method because that there will be no functionality that requires this (ATM)

    /* End SQL Albums Operations */

    /* Start SQL Artists Operations */

    public static void createArtist(Artist artist){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("INSERT INTO " + ARTIST_TABLE + " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING");
            preparedStatement.setString(1, artist.getId());
            preparedStatement.setInt(2, artist.getPopularity());
            preparedStatement.setString(3, artist.getName());
            String genre = artist.getGenres().size() == 0 ? "None" : artist.getGenres().get(0);
            preparedStatement.setString(4, genre); // Only storing one genre
            preparedStatement.setString(5, artist.getHref());

            // Images
            List<Image> images = artist.getImages();
            ArrayList<String> imgList = new ArrayList<>();
            images.forEach(image -> imgList.add(image.getUrl())); // Put all image urls in a list
            preparedStatement.setArray(6, conn.createArrayOf("VARCHAR", imgList.toArray()));
            preparedStatement.setString(7, ""); // No external URL
            preparedStatement.setString(8, artist.getUri());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Optional<Artist> getArtist(String id){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("SELECT * FROM "+ ARTIST_TABLE + " WHERE id = ?");

            // Execute query and obtain result
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){

                // ResultSet had an entry
                Artist artist = new Artist();
                artist.setId(rs.getString("id"));
                artist.setName(rs.getString("name"));
                artist.setPopularity(Integer.valueOf(rs.getString("popularity")));

                //list of Genres
                ArrayList<String> genres = new ArrayList<>();
                genres.add(rs.getString("genre"));
                artist.setGenres(genres);
                artist.setHref(rs.getString("href"));
                artist.setUri(rs.getString("uri"));

                // Images
                ArrayList<Image> images = new ArrayList<>();
                String[]imageURLs = ((String[])rs.getArray("images").getArray());
                for(String url : imageURLs){
                    Image temp = new Image();
                    temp.setUrl(url);
                    images.add(temp);
                }
                artist.setImages(images);

                //setting externalurls
                ExternalUrls url = new ExternalUrls();
                artist.setExternalUrls(url);
                // Query for the Spotify User
                return Optional.of(artist);
            } else {
                // No entry
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static List<Artist> getAllArtistNameAndId(){
        ArrayList<Artist> artists = new ArrayList<>();
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, name FROM " + ARTIST_TABLE);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Artist artist = new Artist();
                artist.setId(rs.getString("id"));
                artist.setName(rs.getString("name"));
                artists.add(artist);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return artists;
    }

    /* End SQL Artists Operations */

    /* Start SQL Playlists Operations */

    public static void createPlaylist(Playlist playlist){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("INSERT INTO " + PLAYLIST_TABLE+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING");
            preparedStatement.setString(1, playlist.getId());
            preparedStatement.setString(2, playlist.getName());
            preparedStatement.setBoolean(3, playlist.isPublicAccess());
            preparedStatement.setInt(4, playlist.getFollowers().getTotal());
            preparedStatement.setString(5, playlist.getHref());
            // Images
            List<Image> images = playlist.getImages();
            ArrayList<String> imgList = new ArrayList<>();
            //images.forEach(image -> imgList.add(image.getUrl())); // Put all image urls in a list
            preparedStatement.setArray(6, conn.createArrayOf("VARCHAR", imgList.toArray()));
            preparedStatement.setString(7, playlist.getUri());
            preparedStatement.setString(8,playlist.getDescription());
            preparedStatement.setString(9, playlist.getOwner().getId());
            // Tracks
            List<PlaylistTrack> tracks = playlist.getTracks().getItems();
            ArrayList<String> trackIds = new ArrayList<>();
            for (PlaylistTrack track :
                    tracks) {
                // Create the Album that the track is associated with the track
                createAlbum(SpotifyCalls.getAlbum(track.getTrack().getAlbum().getId()).get());
                // Create the Artists that the track is associated with
                List<SimpleArtist> artists = track.getTrack().getArtists();
                for (SimpleArtist artist : artists) {
                    Optional<Artist> artistOptional = SpotifyCalls.getArtist(artist.getId());
                    artistOptional.ifPresent(PreparedStatements::createArtist);
                }
                trackIds.add(track.getTrack().getId());
            }
            preparedStatement.setArray(10, conn.createArrayOf("VARCHAR", trackIds.toArray()));
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Optional<Playlist> getPlaylist(String id){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("SELECT * FROM "+ PLAYLIST_TABLE + " WHERE id = ?");

            // Execute query and obtain result
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                // ResultSet had an entry
                Playlist playlist = new Playlist();
                playlist.setId(rs.getString("id"));
                playlist.setName(rs.getString("name"));
                playlist.setHref(rs.getString("href"));
                playlist.setUri(rs.getString("uri"));
                playlist.setPublicAccess(rs.getBoolean("public_access"));
                Followers followers = new Followers();
                followers.setTotal(rs.getInt("followers"));
                playlist.setFollowers(followers);
                //setting user
                com.wrapper.spotify.models.User user = new com.wrapper.spotify.models.User();
                user.setId(rs.getString("owner"));
                playlist.setOwner(user);
                //setting images
                ArrayList<Image> images = new ArrayList<>();
                String[] imageURLs = ((String[])rs.getArray("images").getArray());
                for(String url : imageURLs){
                    Image temp = new Image();
                    temp.setUrl(url);
                    images.add(temp);
                }
                playlist.setImages(images);
                playlist.setDescription(rs.getString("description"));
                //setting Tracks
                Page<PlaylistTrack> trackss = new Page<>();
                List<PlaylistTrack> tracks = new ArrayList<>();
                String[] trackids = ((String[])rs.getArray("fks_tracks").getArray());
                for(String track: trackids){
                    PlaylistTrack temp = new PlaylistTrack();
                    Optional<Track> theTrack = getTrack(track);
                    if (theTrack.isPresent()){
                        temp.setTrack(theTrack.get());
                        tracks.add(temp);
                    }
                }
                trackss.setItems(tracks);
                playlist.setTracks(trackss);
                // Query for the Spotify User

                return Optional.of(playlist);
            } else {
                // No entry
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static List<Playlist> getAllPlaylistNameAndId(){
        ArrayList<Playlist> playlists = new ArrayList<>();
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, name, owner FROM " + PLAYLIST_TABLE);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Playlist playlist = new Playlist();
                playlist.setId(rs.getString("id"));
                playlist.setName(rs.getString("name"));
                com.wrapper.spotify.models.User user = new com.wrapper.spotify.models.User();
                user.setId(rs.getString("owner"));
                playlist.setOwner(user);
                playlists.add(playlist);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return playlists; // Empty array list
    }

    public static List<Playlist> getAllPlaylists(){
        ArrayList<Playlist> playlists = new ArrayList<>();
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM " + PLAYLIST_TABLE);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String id = rs.getString("id");
                Optional<Playlist> playlistOptional = getPlaylist(id);
                playlistOptional.ifPresent(playlists::add);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return playlists; // Empty array list
    }

    public static Optional<String> getPlaylistOwner(String id){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT owner FROM " + PLAYLIST_TABLE + " WHERE id = ?");
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return Optional.of(rs.getString("owner"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Might need a updatePlaylist method

    /* End SQL Playlists Operations */

    /* Start SQL Track Operations */

    public static void createTrack(Track track){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("INSERT INTO " + TRACK_TABLE + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING");
            preparedStatement.setString(1, track.getHref());
            preparedStatement.setInt(2, track.getPopularity());
            preparedStatement.setString(3, track.getName());
            preparedStatement.setString(4,track.getUri());
            preparedStatement.setInt(5, track.getDiscNumber());
            preparedStatement.setInt(6, track.getTrackNumber());
            preparedStatement.setBoolean(7, track.isExplicit());
            preparedStatement.setInt(8, track.getDuration()); // Only storing one genre
            preparedStatement.setString(9, track.getPreviewUrl()); // No external URL
            preparedStatement.setString(10, track.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Optional<Track> getTrack(String id){
        try (Connection conn = SQLManager.getConnection()){
            PreparedStatement preparedStatement = conn
                    .prepareStatement("SELECT * FROM "+ TRACK_TABLE+ " WHERE id = ?");

            // Execute query and obtain result
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                // ResultSet had an entry
                Track track = new Track();
                track.setId(rs.getString("id"));
                track.setName(rs.getString("name"));
                track.setPopularity(Integer.valueOf(rs.getString("popularity")));
                track.setDiscNumber(Integer.valueOf(rs.getString("discnumber")));
                track.setHref(rs.getString("href"));
                track.setUri(rs.getString("uri"));
                track.setTrackNumber(Integer.valueOf(rs.getString("tracknumber")));
                track.setExplicit(Boolean.valueOf(rs.getString("explicit")));
                track.setDuration(Integer.valueOf(rs.getString("duration")));
                track.setPreviewUrl(rs.getString("previewurl"));

                // Query for the Spotify User
                return Optional.of(track);
            } else {
                // No entry
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /* End SQL Track Operations */

    /**
     * Delete all content from tables, but not the tables
     * @param areYouSure - confirmation that you want to delete all content
     */
    public static void truncateTables(boolean areYouSure){
        if (!areYouSure){
            return;
        }
        try (Connection conn = SQLManager.getConnection()){
            StringBuilder sqlStatement = new StringBuilder("TRUNCATE TABLE ");
            String[] tables = SQLResources.TABLES;
            sqlStatement.append(tables[0]);
            for (int i = 1; i < tables.length; i++) {
                sqlStatement.append(", ").append(tables[i]);
            }
            sqlStatement.append(" CASCADE;");
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement.toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
