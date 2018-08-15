package de.letsplaybar.discordbot.gui.aplication.playlist.edit;

import de.letsplaybar.discordbot.gui.GUIModule;
import de.letsplaybar.discordbot.gui.aplication.GUI;
import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="playlist_item_adder_name"
    private TextField playlist_item_adder_name; // Value injected by FXMLLoader

    @FXML // fx:id="playlist_item_adder_url"
    private TextField playlist_item_adder_url; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML // fx:id="playlist_items"
    private ListView<Song> playlist_items; // Value injected by FXMLLoader

    @FXML
    void playlist_item_add(ActionEvent event) {
        String name = playlist_item_adder_name.getText();
        playlist_items.getItems().add(new Song(name.length() == 0?
                getTitle(playlist_item_adder_url.getText()) :
                name,playlist_item_adder_url.getText()));
        playlist_item_adder_name.setText("");
        playlist_item_adder_url.setText("");
    }

    @FXML
    void playlist_item_remove(ActionEvent event) {
        playlist_items.getItems().remove(playlist_items.getSelectionModel().getSelectedItem());
    }

    @FXML
    void playlist_create(ActionEvent event) {
        SQLModule sql = SQLModule.getInstance();
        String name = this.name.getText();
        playlist_items.getItems().stream().forEach( s -> {
            try {
                sql.addSong(name,s.getName(),s.getUrl());
                GUI gui = GUIModule.getInstance().getGui();
                if(gui.getController().getCurr_play().equals(name) && !gui.getController().getCurrent_playlist().getItems().contains(s)){
                    gui.getController().getCurrent_playlist().getItems().add(s.getName());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        ((Stage)playlist_items.getScene().getWindow()).close();
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Stage)playlist_items.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert playlist_item_adder_name != null : "fx:id=\"playlist_item_adder_name\" was not injected: check your FXML file 'Playlist_Create.fxml'.";
        assert playlist_item_adder_url != null : "fx:id=\"playlist_item_adder_url\" was not injected: check your FXML file 'Playlist_Create.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'Playlist_Create.fxml'.";
        assert playlist_items != null : "fx:id=\"playlist_items\" was not injected: check your FXML file 'Playlist_Create.fxml'.";
        playlist_items.setCellFactory(s-> new SongView());

    }

    public void load(String name) throws SQLException {
        this.name.setText(name);
        SQLModule.getInstance().getPlaylistTitel(name).stream().forEach(song -> {
            try {
                playlist_items.getItems().add(new Song(song,SQLModule.getInstance().getSongURL(name,song)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public static class Song{
        private StringProperty name;
        private StringProperty url;

        public Song(String name, String url){
            this.name = new SimpleStringProperty(name);
            this.url = new SimpleStringProperty(url);
        }

        public void setName(String name){
            this.name.set(name);
        }

        public String getName(){
            return name.get();
        }

        public StringProperty namePropertiy(){
            return name;
        }

        public void setUrl(String id){
            this.url.set(id);
        }

        public String getUrl(){
            return url.get();
        }

        public StringProperty urlPropertiy(){
            return url;
        }

    }

    public static class SongView extends ListCell<Song> {
        /**
         * method to set The ListView to human readable text and icon
         * @param item the item to display
         * @param empty is the item empty?
         */
        @Override
        protected void updateItem(Song item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.getName());
            }
        }
    }

    private String getTitle(String url){
        InputStream response = null;
        try {
            response = new URL(url).openStream();
            if(response == null){
                throw new NullPointerException("No Response");
            }

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            return (responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>")));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        throw new NullPointerException();
    }
}
