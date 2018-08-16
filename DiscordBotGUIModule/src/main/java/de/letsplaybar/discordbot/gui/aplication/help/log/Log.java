package de.letsplaybar.discordbot.gui.aplication.help.log;

import de.letsplaybar.discordbot.log.LogModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

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
        if(new File(".data/Style.css").exists()){
            try {
                scene.getStylesheets().add(new File(".data/","Style.css").toURL().toExternalForm());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
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
