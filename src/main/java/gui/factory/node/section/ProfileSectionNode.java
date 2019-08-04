package gui.factory.node.section;

import gui.factory.node.SectionNode;
import model.section.ProfileSection;
import model.section.Section;
import javafx.scene.Node;

/**
 * File: ProfileSectionNode.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class ProfileSectionNode extends SectionNode {
    @Override
    public Node makeNode(Section section) {
        assert section instanceof ProfileSection;
        ProfileSection profileSection = (ProfileSection) section;


        return null;
    }
}
