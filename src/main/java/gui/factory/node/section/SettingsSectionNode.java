package gui.factory.node.section;

import gui.MusicSharingGUI;
import gui.factory.node.SectionNode;
import gui.javafx.ToggleSwitch;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.User;
import model.section.Section;
import model.section.SettingsSection;

import java.util.List;
import java.util.Optional;

/**
 * File: SettingsSectionNode.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SettingsSectionNode extends SectionNode {

    @Override
    public Node makeNode(Section section) {
        assert section instanceof SettingsSection;
        SettingsSection settingsSection = ((SettingsSection) section);
        User.Settings settings = settingsSection.getSettings();

        BorderPane bp = ((BorderPane) formatPane(new BorderPane()));

        ToggleSwitch displayLocation = new ToggleSwitch();


        VBox vBox = new VBox(10, displayLocation);

        Button saveSettings = new Button("Save Settings");
        // Save the user's settings if they click the "Save Settings" button
        saveSettings.setOnAction(event -> settings.updateUsersSettings(settingsSection.getUser(), parseNodeForSettings(vBox)));

        bp.setTop(vBox);
        bp.setBottom(saveSettings);
        return bp;
    }

    /**
     * Parse a VBox for all the nodes it has (which are defined above ^) and create a new {@link User.Settings} object
     * @param node {@link VBox} to parse
     * @return new {@link User.Settings}
     */
    public static User.Settings parseNodeForSettings(VBox node){
        List<Node> nodes = node.getChildren();
        User.Settings newSettings = new User().new Settings();

        // Display Location setting
        newSettings.setDisplayLocation(((ToggleSwitch) nodes.get(0)).switchOnProperty().get());

        // add more as the SettingsSection contains more...

        return newSettings;
    }
}
