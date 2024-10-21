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
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class RealNumberCheckerController {

    public TextField tfNumberInput;
    public VBox vboxResult;
    public Label lblResult;
    Alert a = new Alert(Alert.AlertType.NONE);
    public void onBtnCheckNumberClick(ActionEvent actionEvent) {
        String input = tfNumberInput.getText();

        if (input.isEmpty() || input.isBlank()){
            a.setAlertType(Alert.AlertType.WARNING);
            a.setHeaderText("No number detected.");
            a.setContentText("Please input a number.");
            a.show();
        } else {
            int result = checkNumber(input);
            showResult(result, input);
        }
    }
    public int checkNumber(String input){
        /*
        * Checks for real numbers.
        * Only covers up to negative/positive whole numbers and decimals.
        *
        * This function returns an integer.
        *   1 - real number
        *   0 - valid real number
        */

        int[][] transitionTable = {
//                digit | +/- | dot | others
                {   1   ,  2  ,  3  ,  6  }, // state 0
                {   1   ,  6  ,  4  ,  6  }, // state 1 - final state
                {   1   ,  6  ,  3  ,  6  }, // state 2
                {   5   ,  6  ,  6  ,  6  }, // state 3
                {   5   ,  6  ,  6  ,  6  }, // state 4
                {   5   ,  6  ,  6  ,  6  }, // state 5 - final state
                {   6   ,  6  ,  6  ,  6  }  // state 6 - dead state
        };

        int state = 0; // initial state

        CharacterIterator ci = new StringCharacterIterator(input);

        while(ci.current() !=  CharacterIterator.DONE){
            int input_type;
            char inputC = ci.current();

            if (Character.isDigit(inputC)){
                input_type = 0;
            } else if (inputC == '+' || inputC == '-'){
                input_type = 1;
            } else if (inputC == '.'){
                input_type = 2;
            } else {
                input_type = 3;
            }

            state = transitionTable[state][input_type];
            ci.next();
        }

        if (state == 1 || state == 5){
            return 1;
        }

        return 0;
    }
    public void showResult(int res, String number){
        String result = "The number [ " + number + " ] is ";

        if (res == 1){
            result += "a REAL number.";
        } else {
            result += "NOT a REAL number.";
        }

        lblResult.setText(result);
        vboxResult.setVisible(true);
    }
    public void onBtnCheckAnotherClick(ActionEvent actionEvent) {
        tfNumberInput.setText("");
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
