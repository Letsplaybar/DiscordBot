package de.letsplaybar.discordbot.gui.aplication.settings.perms;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.letsplaybar.discordbot.gui.aplication.settings.perms.add.ADD;
import de.letsplaybar.discordbot.gui.aplication.settings.perms.edit.Edit;
import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="perm"
    private TableColumn<SQLModule.User, String> perm; // Value injected by FXMLLoader

    @FXML // fx:id="permissions"
    private TableView<SQLModule.User> permissions; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TableColumn<SQLModule.User, String> name; // Value injected by FXMLLoader

    @FXML // fx:id="id"
    private TableColumn<SQLModule.User, String> id; // Value injected by FXMLLoader

    @FXML
    void add(ActionEvent event) {
        new ADD();
    }

    @FXML
    void edit(ActionEvent event) {
        if(!permissions.getSelectionModel().isEmpty()) {
            SQLModule.User u = permissions.getSelectionModel().getSelectedItem();
            new Edit(u.getId(),u.getName());
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        try {
            String id = permissions.getSelectionModel().getSelectedItem().getId();
            permissions.getItems().remove(permissions.getSelectionModel().getSelectedItem());
            permissions.refresh();
            SQLModule.getInstance().removeUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ok(ActionEvent event) {
        ((Stage)permissions.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert perm != null : "fx:id=\"perm\" was not injected: check your FXML file 'Permissions.fxml'.";
        assert permissions != null : "fx:id=\"permissions\" was not injected: check your FXML file 'Permissions.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'Permissions.fxml'.";
        assert id != null : "fx:id=\"id\" was not injected: check your FXML file 'Permissions.fxml'.";
        refrash();
    }

    public void refrash(){
        try {
            ObservableList<SQLModule.User> users = FXCollections.observableArrayList();
            users.addAll(SQLModule.getInstance().getUser());
            permissions.setItems(users);
            name.setCellValueFactory(new PropertyValueFactory<SQLModule.User,String>("name"));
            id.setCellValueFactory(new PropertyValueFactory<SQLModule.User,String>("id"));
            perm.setCellValueFactory(new PropertyValueFactory<SQLModule.User,String>("permissions"));
            permissions.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

