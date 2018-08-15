package de.letsplaybar.discordbot.gui.aplication.playlist.delete;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import de.letsplaybar.discordbot.gui.GUIModule;
import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="msg"
    private Text msg; // Value injected by FXMLLoader

    private String name;

    @FXML
    void yes(ActionEvent event) {
        try {
            SQLModule.getInstance().deletePlaylist(name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        GUIModule.getInstance().getGui().getController().removePlaylist(name);
        ((Stage)msg.getScene().getWindow()).close();
    }

    @FXML
    void no(ActionEvent event) {
        ((Stage)msg.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert msg != null : "fx:id=\"msg\" was not injected: check your FXML file 'Delete.fxml'.";

    }

    public void setName(String name){
        this.name = name;
        msg.setText(msg.getText().replace("%n",name));

    }
}

