package test;

import gui.factory.node.section.PlaylistSectionNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.section.PlaylistSection;
import data.persistence.PreparedStatements;

/**
 * File name: PlaylistSectionNodeTest.java
 *
 * @author Luke Heary (lukeheary@gmail.com)
 */
public class PlaylistSectionNodeTest extends Application{

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        PlaylistSection section = new PlaylistSection(PreparedStatements.getPlaylist("00wDVJPcXtafCGtm8eSkvz").get());
//        PlaylistSection section = new PlaylistSection(DummyModelGenerator.dummyPlaylist());

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new PlaylistSectionNode().makeNode(section));
        Scene scene = new Scene(borderPane, 300, 400);
        window.setScene(scene);

        window.show();
    }

    public static void main(String[] args) {

        Application.launch(args);
    }


}
