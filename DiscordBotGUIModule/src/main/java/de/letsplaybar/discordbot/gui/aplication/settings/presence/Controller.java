package de.letsplaybar.discordbot.gui.aplication.settings.presence;

import de.letsplaybar.discordbot.main.utils.Spielst;
import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.dv8tion.jda.api.entities.Activity;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="limage_field"
    private TextField limage_field; // Value injected by FXMLLoader

    @FXML // fx:id="simage_field"
    private TextField simage_field; // Value injected by FXMLLoader

    @FXML // fx:id="stext_field"
    private TextField stext_field; // Value injected by FXMLLoader

    @FXML // fx:id="state_field"
    private TextField state_field; // Value injected by FXMLLoader

    @FXML // fx:id="appid_field"
    private TextField appid_field; // Value injected by FXMLLoader

    @FXML // fx:id="ltext_field"
    private TextField ltext_field; // Value injected by FXMLLoader

    @FXML // fx:id="details_field"
    private TextField details_field; // Value injected by FXMLLoader

    @FXML
    void ok(ActionEvent event) {
        SQLModule sql = SQLModule.getInstance();
        sql.setLI(limage_field.getText());
        sql.setSI(simage_field.getText());
        sql.setST(stext_field.getText());
        sql.setState(state_field.getText());
        sql.setAID(appid_field.getText());
        sql.setLT(ltext_field.getText());
        sql.setDetails(details_field.getText());
        Spielst.getSpielt(sql.getSpielt(),"https://twitch.tv/letsplaybar", Activity.ActivityType.valueOf(sql.getStreamt())).setPresence(sql.getOnline(),sql.getState(),sql.getDetails(),sql.getAID(),sql.getLI(),sql.getSI(),sql.getLT(),sql.getST(),System.currentTimeMillis(),System.currentTimeMillis());
        ((Stage)details_field.getScene().getWindow()).close();
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Stage)details_field.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert limage_field != null : "fx:id=\"limage_field\" was not injected: check your FXML file 'Presence.fxml'.";
        assert simage_field != null : "fx:id=\"simage_field\" was not injected: check your FXML file 'Presence.fxml'.";
        assert stext_field != null : "fx:id=\"stext_field\" was not injected: check your FXML file 'Presence.fxml'.";
        assert state_field != null : "fx:id=\"state_field\" was not injected: check your FXML file 'Presence.fxml'.";
        assert appid_field != null : "fx:id=\"appid_field\" was not injected: check your FXML file 'Presence.fxml'.";
        assert ltext_field != null : "fx:id=\"ltext_field\" was not injected: check your FXML file 'Presence.fxml'.";
        assert details_field != null : "fx:id=\"details_field\" was not injected: check your FXML file 'Presence.fxml'.";
        SQLModule sql = SQLModule.getInstance();
        limage_field.setText(sql.getLI());
        simage_field.setText(sql.getSI());
        stext_field.setText(sql.getST());
        state_field.setText(sql.getState());
        appid_field.setText(sql.getAID());
        ltext_field.setText(sql.getLT());
        details_field.setText(sql.getDetails());
    }
}
