package com.example.fsm_automata;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ValidateEmailController {
    public Label lblResult;
    public VBox vboxResult;
    public TextField tfEmailInput;
    Alert a = new Alert(Alert.AlertType.NONE);
    public void onBtnValidateEmailClick(ActionEvent actionEvent) {
        String input = tfEmailInput.getText();

        if (input.isEmpty() || input.isBlank()){
            a.setAlertType(Alert.AlertType.WARNING);
            a.setHeaderText("No email detected.");
            a.setContentText("Please input an email.");
            a.show();
        } else {
            int result = validateEmail(input);
            showResult(result, input);
        }
    }

    public int validateEmail(String input){
        /*
        * Reference: https://clearout.io/blog/valid-email-address-format/
        * To say an email is valid, it has to contain 3 parts : local / username, the @ symbol, and the domain.
        *
        * Local / Username : any combination of letters, numbers, and special characters. as long as it does not start and ends with a '.'
        * Domain : one or more parts separated with a dot ('.')
        */
        return 0;
    }

    public void showResult(int res, String email){

    }
    public void onBtnCheckAnotherClick(ActionEvent actionEvent) {
        tfEmailInput.setText("");
        vboxResult.setVisible(false);
        lblResult.setText("");
    }

    public void onBtnHomeClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fsmMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("FSM");
    }


}
