package gui.factory.node;

import gui.factory.node.section.AlbumSectionNode;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.section.ProfileSection;
import model.section.Section;

/**
 * File: SectionNode.java
 * Purpose: Define the properties that all SectionNodes will have.
 * By having it in this file all other SectionNodes will have the
 * same style. There are likely other properties that will be added here.
 * See {@link test.AlbumSectionNodeTest} for an example implementation of this class
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 * @author Luke Heary (lukeheary@gmail.com)
 */
public abstract class SectionNode {

    /**
     * Responsible for formatting the pane, which includes but is not limited to
     *      setting the max/min/pref size
     *      setting the background
     *      setting css files
     * @param pane {@link Pane} to format
     * @return an object that is a {@link Pane} usually is a more specific object
     *      This could be a {@link BorderPane}, but other options may prevail as a superior pane
     */
    protected Pane formatPane(Pane pane) {
        //pane.setBackground();
        // TODO pane style
        return pane;
    }

    /**
     * Makes a {@link Node} that, in other parts of the program, will be put inside of a
     * {@link BorderPane} on the main screen of the GUI. The {@link Section} will be of a
     * more specific type. See {@link model.section} for the possible types.
     *
     * A {@link Section} represents one page in the GUI application. A {@link ProfileSection}
     * will contain data relevant to creating a visual representation of a User's profile.
     * @param section an object that extends {@link Section} which also contains the relevant data for
     * @return the resulting {@link Node} that will be displayed in the GUI. Typically of type
     *      {@link Pane}, but {@link Node} is used as a more generic option
     */
    public abstract Node makeNode(Section section);



}
