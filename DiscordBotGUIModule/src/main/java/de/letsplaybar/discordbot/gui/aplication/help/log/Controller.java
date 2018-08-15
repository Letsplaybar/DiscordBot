package de.letsplaybar.discordbot.gui.aplication.help.log;

import java.net.URL;
import java.util.ResourceBundle;

import de.letsplaybar.discordbot.log.LogModule;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="log_field"
    private TextArea log_field; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert log_field != null : "fx:id=\"log_field\" was not injected: check your FXML file 'Log.fxml'.";
        log_field.setText(LogModule.getInstance().getLog());
        LogModule.getInstance().registerGUI(log_field);
    }
}

