package gui;

import com.wrapper.spotify.models.User;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static data.persistence.PreparedStatements.approveUserLogin;
import static data.persistence.PreparedStatements.getUser;

/**
 * File: LoginGUI.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 * @author Oscar Onyeke         (oscaronyeke@yahoo.com)
 */

public class LoginGUI {
    private Stage window;
    private Scene scene;
    private static LoginGUI instance;

    private String username;
    private String password;
    private User   S_user;
    private model.User user;


    public GridPane texts(GridPane grid, Stage windows){
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 70));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        btn.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                            @Override
                            public void handle(javafx.event.ActionEvent event) {
                                String username = userTextField.getText();
                                if(!userTextField.getText().isEmpty() && !pwBox.getText().isEmpty()){
                                    if(getUser(username).isPresent()){
                                        if(approveUserLogin(username,pwBox.getText())){

                                            setUsername(username);
                                            setPassword(pwBox.getText());
                                            setspotifyUser(getUser(username).get().getSpotifyUser().get());
                                            setUser(getUser(username).get());

                                            /* when the button is presses and all feilds are filled the program
                                             * the program goes to the next page
                                             */
                                            windows.hide();
                                        }
                                        else {
                                            //print that username/password are wrong
                                            btn.setTextFill(Color.FIREBRICK);
                                            btn.setText("Incorrect password");
                                        }
                                    }
                                    else {
                                        //print that the feilds need to be filled out
                                        btn.setTextFill(Color.FIREBRICK);
                                        btn.setText("Incorrect username");
                                    }
                                }
                                else{
                                    btn.setTextFill(Color.FIREBRICK);
                                    btn.setText("Fill out all fields");
                                }
                            }
                        }
        );
        HBox hbBtn1 = new HBox(5);
        hbBtn1.getChildren().add(btn);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);

        grid.add(hbBtn1,1,4);

        return grid;
    }

    public void start(Stage primaryStage) {
        this.window = primaryStage;
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(40,40,40,40));
        grid = texts(grid,this.window);

        this.scene= new Scene(grid,640,480);

        this.window.setScene(scene);
        this.window.setOnCloseRequest(event -> this.window.close());
        this.window.showAndWait();
    }

    private void setUsername(String username){
        this.username = username;
    }

    private void setPassword(String password){
        this.password = password;
    }

    private void setspotifyUser(User user){
        this.S_user = user;
    }

    private void setUser(model.User user){this.user = user;}

    public String getUsername(){
        return this.username ;
    }

    public String getPassword(){
        return this.password;
    }

    public User getspotifyUser(){
        return this.S_user ;
    }

    public model.User GetUser(){return this.user;}
    public synchronized static LoginGUI getInstance(){
        return instance;
    }
}
