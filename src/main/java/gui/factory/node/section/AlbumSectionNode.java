package gui.factory.node.section;

import com.wrapper.spotify.models.*;
import gui.factory.node.SectionNode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.section.AlbumSection;
import model.section.Section;
import javafx.scene.Node;
import util.Util;

import java.util.ArrayList;
import java.util.List;

import static data.persistence.PreparedStatements.*;

/**
 * File: AlbumSectionNode.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class AlbumSectionNode extends SectionNode {

    @Override
    public Node makeNode(Section section) {
        assert section instanceof AlbumSection;

        AlbumSection albumSection = (AlbumSection) section;
        Album album = albumSection.getAlbum();
        BorderPane borderPane = new BorderPane();

        VBox vbox = new VBox(10);

        ArrayList<Text> texts = new ArrayList<>();

        final Text title = new Text(album.getName());
        texts.add(title);
        title.setStyle("-fx-font: 23 helvetica;");
        Text artistsText = new Text();
        List<SimpleArtist> artists = album.getArtists();
        artistsText.setText("Artist(s): " + getArtist(artists.get(0).getId()).get().getName());
        for (int i = 1; i < artists.size(); i++) {
            artistsText.setText(artistsText.getText() + ", " + getArtist(artists.get(i).getId()).get().getName());
        }
        texts.add(artistsText);
        List<Track> tracks = new ArrayList<>();

        // Add all the tracks
        album.getTracks().getItems().forEach(track -> tracks.add(Util.simpleTrackToTrack(track)));

        for (int i = 0; i < tracks.size(); i++) {
            final Text desc = new Text(" Song" + i + ": " + getTrack(tracks.get(i).getId()).get().getName());
            texts.add(desc);
            desc.setStyle("-fx-font: 12 helvetica;");
        }

        texts.forEach(text -> vbox.getChildren().add(text));

        borderPane.setCenter(vbox);
        return borderPane;
    }
}
