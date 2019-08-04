package test;

import gui.factory.node.section.AlbumSectionNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.section.AlbumSection;
import util.DummyModelGenerator;

/**
 * File: test.AlbumSectionNodeTest.java
 * Quickly see what the view looks like independent of other parts of the application
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class AlbumSectionNodeTest extends Application {
    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        AlbumSection section = new AlbumSection(DummyModelGenerator.dummyAlbum());
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new AlbumSectionNode().makeNode(section));
        Scene scene = new Scene(borderPane, 300, 300);
        window.setScene(scene);

        window.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
