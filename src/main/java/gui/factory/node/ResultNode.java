package gui.factory.node;

import data.search.result.Result;
import data.search.result.Results;
import gui.factory.node.result.SongResultNode;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * File: ResultNode.java
 * Purpose: Define the properties for all ResultNodes.
 * By having it in this file all other ResultNodes will have the
 * same style. There are likely other properties that will be added here.
 * See {@link SongResultNode#SongResultNode()} for an example implementation of this class
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 * @author Luke Heary (lukeheary@gmail.com)
 */
public abstract class ResultNode {

    /**
     * Responsible for formatting the pane, which includes but is not limited to
     *      setting the max/min/pref size
     *      setting the background
     *      setting css files
     * @param pane {@link Pane} to format
     * @return an object that is a {@link Pane} usually of type {@link BorderPane},
     *      but certainly can be other {@link Pane}s
     */
    protected Pane formatPane(Pane pane){
        pane.minWidth(200);
        pane.minHeight(50);
        pane.maxHeight(100);
        pane.maxWidth(400);
        pane.prefHeight(75);
        pane.prefWidth(300);
        return pane;
    }

    /**
     * Makes a {@link Node} representation of a single {@link Result} in a given search.
     * This method gets called for every {@link Result} in a {@link Results} object (which
     * just contains a {@link Result} list).
     * @param result one of the {@link Result} that results from a search
     * @return the resulting {@link Node} that will likely be added to a {@link VBox}
     *      which will be displayed on the GUI.
     */
    public abstract Node makeNode(Result result);



}
