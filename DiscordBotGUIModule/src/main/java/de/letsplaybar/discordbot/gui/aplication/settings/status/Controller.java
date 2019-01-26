package de.letsplaybar.discordbot.gui.aplication.settings.status;

import de.letsplaybar.discordbot.main.utils.Spielst;
import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="online_box"
    private ChoiceBox<String> online_box; // Value injected by FXMLLoader

    @FXML // fx:id="stream_box"
    private ChoiceBox<String> stream_box; // Value injected by FXMLLoader

    @FXML // fx:id="spielt_field"
    private TextField spielt_field; // Value injected by FXMLLoader

    @FXML
    void ok(ActionEvent event) {
        SQLModule sql = SQLModule.getInstance();
        sql.setOnline(online_box.getValue());
        sql.setStreamt(stream_box.getValue());
        sql.setSpielt(spielt_field.getText());
        Spielst.getSpielt(sql.getSpielt(),"https://twitch.tv/letsplaybar", Activity.ActivityType.valueOf(sql.getStreamt())).setPresence(sql.getOnline(),sql.getState(),sql.getDetails(),sql.getAID(),sql.getLI(),sql.getSI(),sql.getLT(),sql.getST(),System.currentTimeMillis(),System.currentTimeMillis());
        ((Stage)spielt_field.getScene().getWindow()).close();
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Stage)spielt_field.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert online_box != null : "fx:id=\"online_box\" was not injected: check your FXML file 'Status.fxml'.";
        assert stream_box != null : "fx:id=\"stream_box\" was not injected: check your FXML file 'Status.fxml'.";
        assert spielt_field != null : "fx:id=\"spielt_field\" was not injected: check your FXML file 'Status.fxml'.";
        SQLModule sql = SQLModule.getInstance();
        Arrays.asList(OnlineStatus.values()).stream().forEach(status -> {
            if(!status.equals(OnlineStatus.UNKNOWN)){
                online_box.getItems().add(status.name());
            }
        });
        Arrays.asList(Activity.ActivityType.values()).stream().forEach(type ->{
            stream_box.getItems().add(type.name());
        });
        online_box.setValue(sql.getOnline().name());
        stream_box.setValue(sql.getStreamt());
        spielt_field.setText(sql.getSpielt());
    }
}

