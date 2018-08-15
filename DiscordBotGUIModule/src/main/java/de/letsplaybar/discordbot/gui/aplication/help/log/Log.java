package de.letsplaybar.discordbot.gui.aplication.help.log;

import de.letsplaybar.discordbot.log.LogModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Log  extends Stage {


    public Log(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Log.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(pane);
        Controller controller = loader.getController();
        this.setTitle("Log");
        this.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/img/Logo.png")));
        this.setScene(scene);
        this.centerOnScreen();
        this.show();
    }

    @Override
    public void close() {
        super.close();
        LogModule.getInstance().reset();
    }
}
