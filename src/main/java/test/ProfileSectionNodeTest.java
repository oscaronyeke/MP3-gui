package test;

import gui.factory.node.section.AlbumSectionNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.section.AlbumSection;
import model.section.ProfileSection;
import util.DummyModelGenerator;

/**
 * File name: ProfileSectionNodeTest.java
 *
 * @author Luke Heary (lukeheary@gmail.com)
 */
public class ProfileSectionNodeTest extends Application {
    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        ProfileSection section = new ProfileSection(DummyModelGenerator.dummyUser());
        BorderPane borderPane = new BorderPane();
        //borderPane.setCenter(new ProfileSectionNode.makeNode(section));

        Scene scene = new Scene(borderPane, 500, 500);

        window.setScene(scene);

        window.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
