package test;

import gui.factory.node.section.ArtistSectionNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.section.ArtistSection;
import util.DummyModelGenerator;

/**
 * File: ArtistSectionNodeTest.java
 *
 * @author Oscar Onyeke(oscaronyeke@yahoo.com)
 */
public class ArtistSectionNodeTest extends Application{

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        ArtistSection section = new ArtistSection(DummyModelGenerator.dummyArtist());
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new ArtistSectionNode().makeNode(section));
        Scene scene = new Scene(borderPane, 300, 300);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
