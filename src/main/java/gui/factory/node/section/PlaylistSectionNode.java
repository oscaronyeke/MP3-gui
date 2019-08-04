package gui.factory.node.section;

import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.PlaylistTrack;
import com.wrapper.spotify.models.Track;
import gui.factory.node.SectionNode;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.section.PlaylistSection;
import model.section.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * File: PlaylistSectionNode.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class PlaylistSectionNode extends SectionNode {
    @Override
    public Node makeNode(Section section) {

        PlaylistSection playlistSection = (PlaylistSection) section;
        Playlist playlist = playlistSection.getPlaylist();
        BorderPane borderPane = new BorderPane();

        borderPane.setPadding(new Insets(20, 20, 20, 20));

        VBox vbox = new VBox(10);

        ArrayList<Text> texts = new ArrayList<>();

        final Text title = new Text(playlist.getName());
        texts.add(title);
        title.setStyle("-fx-font: 23 helvetica;");


        if(playlist.getDescription() != null) {
            final Text desc = new Text(playlist.getDescription());
            texts.add(desc);
            desc.setStyle("-fx-font: 16 helvetica;");
        }

        List<PlaylistTrack> playlistTracks = playlist.getTracks().getItems();
        List<Track> tracks = new ArrayList<>();
        playlistTracks.forEach(track -> tracks.add(track.getTrack()));

        for (int i = 0; i < playlistTracks.size(); i++) {

            final Text desc = new Text(" Song " + i +": " + tracks.get(i).getName());
            texts.add(desc);
            desc.setStyle("-fx-font: 12 helvetica;");
        }

        texts.forEach(text -> vbox.getChildren().add(text));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color:transparent;");
        scrollPane.setContent(vbox);
        borderPane.setCenter(scrollPane);
        return borderPane;
    }
}
