package gui.factory.node.result;

import data.search.result.Result;
import gui.factory.node.ResultNode;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import javax.swing.text.html.ImageView;

/**
 * File: SongResultNode.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SongResultNode extends ResultNode {

    private BorderPane borderPane;
    private ImageView artistImage;
    private Text description;

    public SongResultNode(){
        this.borderPane = new BorderPane();
        super.formatPane(this.borderPane);

    }

    @Override
    public Node makeNode(Result result) {
        return null;
    }
}