package model.section;

import com.wrapper.spotify.models.Playlist;

/**
 * File: PlaylistSection.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class PlaylistSection extends Section {
    private Playlist playlist;

    public PlaylistSection(Playlist playlist){
        this.playlist = playlist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }
}
