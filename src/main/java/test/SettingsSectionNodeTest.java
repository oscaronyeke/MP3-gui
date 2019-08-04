package test;

import gui.factory.node.section.SettingsSectionNode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.section.SettingsSection;
import util.DummyModelGenerator;

/**
 * File: SettingsSectionNodeTest.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SettingsSectionNodeTest extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        SettingsSection section = new SettingsSection(DummyModelGenerator.dummyUser());
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new SettingsSectionNode().makeNode(section));
        Scene scene = new Scene(borderPane, 300, 300);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
