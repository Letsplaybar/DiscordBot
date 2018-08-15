package de.letsplaybar.discordbot.gui.aplication.settings.perms.edit;

import de.letsplaybar.discordbot.sql.SQLModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="perm_field"
    private TextField perm_field; // Value injected by FXMLLoader

    @FXML // fx:id="id_field"
    private TextField id_field; // Value injected by FXMLLoader

    @FXML // fx:id="name_field"
    private TextField name_field; // Value injected by FXMLLoader

    @FXML // fx:id="permview"
    private ListView<String> permview; // Value injected by FXMLLoader

    @FXML
    void add(ActionEvent event) {
        permview.getItems().add(perm_field.getText());
        perm_field.setText("");
    }

    @FXML
    void ok(ActionEvent event) {
        SQLModule sql = SQLModule.getInstance();
        try {
            sql.addUser(name_field.getText(),id_field.getText());
            permview.getItems().stream().forEach(perm -> {
                try {
                    sql.addPermisson(id_field.getText(),perm);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            ((Stage)permview.getScene().getWindow()).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Stage)permview.getScene().getWindow()).close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert perm_field != null : "fx:id=\"perm_field\" was not injected: check your FXML file 'Edit.fxml'.";
        assert id_field != null : "fx:id=\"id_field\" was not injected: check your FXML file 'Edit.fxml'.";
        assert name_field != null : "fx:id=\"name_field\" was not injected: check your FXML file 'Edit.fxml'.";
        assert permview != null : "fx:id=\"permview\" was not injected: check your FXML file 'Edit.fxml'.";

    }

    @FXML
    void remove(ActionEvent event){
        if(!permview.getSelectionModel().isEmpty())
            permview.getItems().remove(permview.getSelectionModel().getSelectedItem());
    }

    public void load(String id, String name) {
        name_field.setText(name);
        id_field.setText(id);

        try {
            SQLModule.getInstance().getPermissions(id).stream().forEach(perm -> permview.getItems().add((String) perm));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

