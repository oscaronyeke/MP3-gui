package gui;

import com.wrapper.spotify.models.Album;
import com.wrapper.spotify.models.Artist;
import com.wrapper.spotify.models.Playlist;
import data.api.call.spotify.SpotifyCalls;
import gui.factory.node.section.AlbumSectionNode;
import gui.factory.node.section.ArtistSectionNode;
import gui.factory.node.section.PlaylistSectionNode;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.section.AlbumSection;
import model.section.ArtistSection;
import model.section.PlaylistSection;
import data.persistence.PreparedStatements;

import java.util.*;

import static java.lang.System.exit;

/**
 * File: MusicSharingGUI.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class MusicSharingGUI extends Application implements Observer {
    // Again, class name to be renamed when we have a name TODO

    // High level JavaFX nodes
    private Stage window;
    private Scene scene;

    // Main window nodes
    private BorderPane  mainPane;
    private BorderPane  pagePane;
    private SplitPane   splitPane;
    private BorderPane  nagivationPane;

    // Controls
    private Button button;

    // Data
    private User currentUser;
    private static MusicSharingGUI instance;
    private LoginGUI loginGUI;

    @Override
    public void init() throws Exception {
        super.init();
        mainPane = new BorderPane();
        mainPane.setId("bp");
        mainPane.setPadding(new Insets(20, 20, 20, 0));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Music Application");


        instance = this;

        //Creating the login screen
        Stage stage = new Stage();
        loginGUI = new LoginGUI();
        loginGUI.start(stage);
        if(loginGUI.getspotifyUser()==null){
            exit(1);
        }

        mainPane.setCenter(createPlaylistBox());
        mainPane.setLeft(createNavigationPane());

        scene = new Scene(mainPane, 640, 480);
        window.setScene(scene);
        window.show();
    }

    private Node createPlaylistBox(){
        VBox playlistBox = new VBox();
        playlistBox.setSpacing(10);
        playlistBox.setPadding(new Insets(0, 20, 10, 20));

        ArrayList<Text> texts = new ArrayList<>();
        final Text title = new Text("Playlist List");
        texts.add(title);
        title.setStyle("-fx-font: 23 helvetica;");
        texts.forEach(text -> playlistBox.getChildren().add(text));
        List<Playlist> list = PreparedStatements.getAllPlaylistNameAndId();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getOwner().getId().equals(loginGUI.getspotifyUser().getId())) {
                Button btn = new Button(list.get(i).getName());
                btn.setPrefWidth(120);
                btn.setId(list.get(i).getId());
                btn.setOnAction(event -> {
                    scene.setCursor(Cursor.WAIT);
                    PlaylistSection section = new PlaylistSection(
                            SpotifyCalls.getPlaylist(PreparedStatements.getPlaylistOwner(btn.getId()).get(), btn.getId()).get()
                    );
                    mainPane.setCenter(new PlaylistSectionNode().makeNode(section));
                    scene.setCursor(Cursor.DEFAULT);
                });
                btn.setOnMouseEntered(event -> scene.setCursor(Cursor.HAND));
                btn.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));
                playlistBox.getChildren().addAll(btn);
            }
        }
        playlistBox.setPadding(new Insets(10, 0, 0, 0));
        return playlistBox;
    }

    private Node createAlbumBox(){
        VBox main = new VBox();
        VBox albumBox = new VBox();
        albumBox.setSpacing(10);

        ArrayList<Text> texts = new ArrayList<>();
        final Text title = new Text("Albums");
        texts.add(title);
        title.setStyle("-fx-font: 23 helvetica;");
        texts.forEach(text -> main.getChildren().add(text));
        List<Album> list = PreparedStatements.getAllAlbumNameAndId();

        for (int i = 0; i < list.size(); i++) {
            Button btn = new Button(list.get(i).getName());
            btn.setPrefWidth(120);
            btn.setId(list.get(i).getId());
            btn.setOnAction(event -> {
                AlbumSection section = new AlbumSection(
                        PreparedStatements.getAlbum(btn.getId()).get()
                );
                mainPane.setCenter(new AlbumSectionNode().makeNode(section));
            });
            albumBox.getChildren().addAll(btn);
        }
        albumBox.setPadding(new Insets(10, 0, 0, 10));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(albumBox);
        scrollPane.setStyle("-fx-background-color:transparent;");
        main.getChildren().add(scrollPane);
        return main;
    }

    private Node createArtistBox(){
        VBox main = new VBox();
        VBox artistBox = new VBox();
        artistBox.setSpacing(10);

        ArrayList<Text> texts = new ArrayList<>();
        final Text title = new Text("Artists");
        texts.add(title);
        title.setStyle("-fx-font: 23 helvetica;");
        texts.forEach(text -> main.getChildren().add(text));
        List<Artist> list = PreparedStatements.getAllArtistNameAndId();

        for (int i = 0; i < list.size(); i++) {
            Button btn = new Button(list.get(i).getName());
            btn.setPrefWidth(120);
            btn.setId(list.get(i).getId());
            btn.setOnAction(event -> {
                ArtistSection section = new ArtistSection(
                        PreparedStatements.getArtist(btn.getId()).get()
                );
                mainPane.setCenter(new ArtistSectionNode().makeNode(section));
            });
            artistBox.getChildren().addAll(btn);
        }
        artistBox.setPadding(new Insets(10, 0, 0, 10));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(artistBox);
        scrollPane.setStyle("-fx-background-color:transparent;");
        main.getChildren().add(scrollPane);
        return main;
    }

    private Node createNavigationPane(){
        Label playLists, albums, artists;
        playLists = new Label("Playlists");
        playLists.setFont(Font.font("helvetica", FontWeight.NORMAL, 18));
        albums = new Label("Albums");
        albums.setFont(Font.font("helvetica", FontWeight.NORMAL, 18));
        artists = new Label("Artists");
        artists.setFont(Font.font("helvetica", FontWeight.NORMAL, 18));

        playLists.setPrefSize(100,30);
        playLists.setMinSize(100,30);

        albums.setPrefSize(100,30);
        albums.setMinSize(100,30);

        artists.setPrefSize(100,30);
        artists.setMinSize(100,30);

        playLists.setOnMouseClicked(event -> {
            scene.setCursor(Cursor.WAIT);
            mainPane.setCenter(createPlaylistBox());
            scene.setCursor(Cursor.DEFAULT);
        });
        playLists.setOnMouseEntered(event -> scene.setCursor(Cursor.HAND));
        playLists.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));

        albums.setOnMouseClicked(event -> {
            scene.setCursor(Cursor.WAIT);
            mainPane.setCenter(createAlbumBox());
            scene.setCursor(Cursor.DEFAULT);

        });
        albums.setOnMouseEntered(event -> scene.setCursor(Cursor.HAND));
        albums.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));

        artists.setOnMouseClicked(event -> {
            scene.setCursor(Cursor.WAIT);
            mainPane.setCenter(createArtistBox());
            scene.setCursor(Cursor.DEFAULT);
        });
        artists.setOnMouseEntered(event -> scene.setCursor(Cursor.HAND));
        artists.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));

        VBox vBox = new VBox(0, playLists, albums, artists);
        //vBox.setStyle("-fx-border-color: black;");
        BorderPane tmp = new BorderPane(vBox);
        tmp.setPadding(new Insets(30,0,30,20));
        return tmp;
    }

    /* Start Getters and Settings */

    public synchronized static MusicSharingGUI getInstance(){
        return instance;
    }

    public Optional<User> getCurrentUser(){
        return Optional.ofNullable(this.currentUser);
    }

    /* End Getters and Settings */


    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
