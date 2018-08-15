package de.letsplaybar.discordbot.gui.aplication.settings.perms.edit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Edit extends Stage {
    public Edit(String id, String name){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Edit.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(pane);
        Controller controller = loader.getController();
        controller.load(id,name);
        this.setTitle("Bearbeite Userpermissions");
        this.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/Logo.png")));
        this.setScene(scene);
        this.centerOnScreen();
        this.show();
    }
}
