package model.section;

import com.wrapper.spotify.models.Album;

/**
 * File: AlbumSection.java
 * Model for the album section view. Contains data relevant for displaying an Album on the GUI.
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class AlbumSection extends Section {
    private Album album;

    public AlbumSection(Album album){
        this.album = album;
    }

    public Album getAlbum(){
        return this.album;
    }
}
