package de.letsplaybar.discordbot.gui.aplication.settings.token;

import java.net.URL;
import java.util.ResourceBundle;

import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="weather_token"
    private TextField weather_token; // Value injected by FXMLLoader

    @FXML // fx:id="giphy_key"
    private TextField giphy_key; // Value injected by FXMLLoader

    @FXML // fx:id="user_token"
    private TextField user_token; // Value injected by FXMLLoader

    @FXML // fx:id="bing_key"
    private TextField bing_key; // Value injected by FXMLLoader

    @FXML // fx:id="lizenz_key"
    private TextField lizenz_key; // Value injected by FXMLLoader

    @FXML
    void ok(ActionEvent event) {
        SQLModule sql = SQLModule.getInstance();
        sql.setKey(weather_token.getText());
        sql.setGiphy(giphy_key.getText());
        sql.setToken(user_token.getText());
        sql.setBingAPI(bing_key.getText());
        sql.setLizenz(lizenz_key.getText());
        ((Stage)lizenz_key.getScene().getWindow()).close();
    }

    @FXML
    void Cancel(ActionEvent event) {
        ((Stage)lizenz_key.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert weather_token != null : "fx:id=\"weather_token\" was not injected: check your FXML file 'Token.fxml'.";
        assert giphy_key != null : "fx:id=\"giphy_key\" was not injected: check your FXML file 'Token.fxml'.";
        assert user_token != null : "fx:id=\"user_token\" was not injected: check your FXML file 'Token.fxml'.";
        assert bing_key != null : "fx:id=\"bing_key\" was not injected: check your FXML file 'Token.fxml'.";
        assert lizenz_key != null : "fx:id=\"lizenz_key\" was not injected: check your FXML file 'Token.fxml'.";

        SQLModule sql = SQLModule.getInstance();
        weather_token.setText(sql.getKey());
        giphy_key.setText(sql.getGiphy());
        user_token.setText(sql.getToken());
        bing_key.setText(sql.getBingAPI());
        lizenz_key.setText(sql.getLizenz());

    }
}
