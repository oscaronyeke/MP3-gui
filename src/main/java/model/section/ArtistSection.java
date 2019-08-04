package model.section;

import com.wrapper.spotify.models.Artist;
/**
 * File: ArtistSection.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 * @author Oscar Onyeke(oscaronyeke@yahoo.com)
 */
public class ArtistSection extends Section {
    private Artist artist;

    public ArtistSection(Artist artist){
        this.artist = artist;
    }

    public Artist getArtist(){
        return  this.artist;
    }
}
