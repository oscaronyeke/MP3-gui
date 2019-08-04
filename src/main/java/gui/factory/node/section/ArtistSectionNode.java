package gui.factory.node.section;

import com.wrapper.spotify.models.Artist;
import gui.factory.node.SectionNode;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.section.ArtistSection;
import model.section.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * File: ArtistSectionNode.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 * @author Oscar Onyeke(oscaronyeke@yahoo.com)
 */
public class ArtistSectionNode extends SectionNode {

    @Override
    public Node makeNode(Section section) {
        assert section instanceof ArtistSection;
        ArtistSection artistSection = (ArtistSection) section;
        Artist artist = artistSection.getArtist();
        BorderPane borderPane = new BorderPane();

        VBox vbox = new VBox(20);

        ArrayList<Text> texts = new ArrayList<>();

        final Text title = new Text(artist.getName());
        texts.add(title);
        title.setStyle("-fx-font: 23 helvetica;");

        texts.add(new Text("Popularity: " + artist.getPopularity()));
        List<String> genres = artist.getGenres();
        Text genreText = new Text("Genre(s): ");
        if (genres.size() == 0){
            genreText.setText(genreText.getText() + "None");
        } else {
            genreText.setText(genreText.getText() + genres.get(0));
            for (int i = 1; i < genres.size(); i++) {
                genreText.setText(genreText.getText() + ", " + genres.get(i));
            }
        }
        genreText.setStyle("-fx-font: 12 helvetica;");
        texts.add(genreText);

        texts.forEach(text -> vbox.getChildren().add(text));

        borderPane.setCenter(vbox);
        return borderPane;
    }
}
