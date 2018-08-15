package de.letsplaybar.discordbot.gui.aplication.playlist.edit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Playlistedit extends Stage {

    public Playlistedit(String name) throws SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Playlist_edit.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(pane);
        Controller controller = loader.getController();
        controller.load(name);
        this.setTitle("Edit  Playlist");
        this.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/Logo.png")));
        this.setScene(scene);
        this.centerOnScreen();
        this.show();
    }

}
