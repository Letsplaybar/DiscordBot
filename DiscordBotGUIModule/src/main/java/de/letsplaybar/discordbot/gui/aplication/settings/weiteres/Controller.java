package de.letsplaybar.discordbot.gui.aplication.settings.weiteres;

import java.net.URL;
import java.util.ResourceBundle;

import de.letsplaybar.discordbot.command.CommandModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmd_field"
    private TextField cmd_field; // Value injected by FXMLLoader

    @FXML
    void ok(ActionEvent event) {
        CommandModule.getInstance().setCommand(cmd_field.getText());
        ((Stage)cmd_field.getScene().getWindow()).close();
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Stage)cmd_field.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmd_field != null : "fx:id=\"cmd_field\" was not injected: check your FXML file 'Weiteres.fxml'.";
        cmd_field.setText(CommandModule.getInstance().getCommand());
    }
}

