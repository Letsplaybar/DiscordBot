package de.letsplaybar.discordbot.gui.aplication.help.about;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="version"
    private Text version; // Value injected by FXMLLoader

    @FXML
    void openSIte(ActionEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Desktop.getDesktop().browse(URI.create("https://Letsplaybar.de"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert version != null : "fx:id=\"version\" was not injected: check your FXML file 'About.fxml'.";
        try {
            String ver = new String(ByteStreams.toByteArray(getClass().getResourceAsStream("/version.txt")), Charsets.UTF_8);
            version.setText(version.getText().replace("%d",ver));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

