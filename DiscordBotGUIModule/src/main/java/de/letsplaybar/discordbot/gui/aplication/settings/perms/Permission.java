package de.letsplaybar.discordbot.gui.aplication.settings.perms;

import de.letsplaybar.discordbot.gui.GUIModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

public class Permission extends Stage {

    private @Getter Controller controller;

    public Permission(){
        GUIModule.getInstance().getGui().getController().setPermission(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Permissions.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(pane);
        controller = loader.getController();
        this.setTitle("Permissions");
        this.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/Logo.png")));
        this.setScene(scene);
        this.centerOnScreen();
        this.show();
    }
}
