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

public class ValidatePhoneNumberController {
    public Label lblResult;
    public VBox vboxResult;
    public TextField tfPhoneNumberInput;
    Alert a = new Alert(Alert.AlertType.NONE);
    public void onBtnValidatePhoneNumberClick(ActionEvent actionEvent) {
        String input = tfPhoneNumberInput.getText();

        if (input.isEmpty() || input.isBlank()){
            a.setAlertType(Alert.AlertType.WARNING);
            a.setHeaderText("No phone number detected.");
            a.setContentText("Please input a phone number.");
            a.show();
        } else {
            int result = validatePhoneNumber(input);
            showResult(result, input);
        }
    }

    public int validatePhoneNumber(String input){
        /*
        * A valid PH phone number has 11 digit that starts with 09, or it starts with +639 followed by 9 digits.
        * +63xxxxxxxxx || 09xxxxxxxxx || 09xx-xxx-xxxx || +639xx-xxx-xxxx
        * This function returns an integer.
        *   1 - valid PH phone number
        *   0 - invalid PH phone number
        *
        */

        int[][] transitionTable = {
//                 0  |  9  |  6  |  3  |  +  |  -  | other_numbers | other_char
                {  1  ,  15 ,  15 ,  15 ,   2 ,  15 ,  15 , 15 }, // State 0
                { 15  ,   5 ,  15 ,  15 ,  15 ,  15 ,  15 , 15 }, // State 1 - state upon receiving 0
                { 15  ,  15 ,   3 ,  15 ,  15 ,  15 ,  15 , 15 }, // State 2 - state upon receiving +
                { 15  ,  15 ,  15 ,   4 ,  15 ,  15 ,  15 , 15 }, // State 3
                { 15  ,   5 ,  15 ,  15 ,  15 ,  15 ,  15 , 15 }, // State 4
                {  6  ,   6 ,   6 ,   6 ,  15 ,  15 ,   6 , 15 }, // State 5 - 3
                {  7  ,   7 ,   7 ,   7 ,  15 ,  15 ,   7 , 15 }, // State 6
                {  8  ,   8 ,   8 ,   8 ,  15 ,  16 ,   8 , 15 }, // State 7
                {  9  ,   9 ,   9 ,   9 ,  15 ,  15 ,   9 , 15 }, // State 8
                { 10  ,  10 ,  10 ,  10 ,  15 ,  15 ,  10 , 15 }, // State 9
                { 11  ,  11 ,  11 ,  11 ,  15 ,  17 ,  11 , 15 }, // State 10
                { 12  ,  12 ,  12 ,  12 ,  15 ,  15 ,  12 , 15 }, // State 11
                { 13  ,  13 ,  13 ,  13 ,  15 ,  15 ,  13 , 15 }, // State 12
                { 14  ,  14 ,  14 ,  14 ,  15 ,  15 ,  14 , 15 }, // State 13
                { 15  ,  15 ,  15 ,  15 ,  15 ,  15 ,  15 , 15 }, // State 14 - final state, so any other transition on this state will go to the dead state
                { 15  ,  15 ,  15 ,  15 ,  15 ,  15 ,  15 , 15 }, // State 15 - dead state
                {  8  ,   8 ,   8 ,   8 ,  15 ,  15 ,   8 , 15 }, // State 16
                { 11  ,  11 ,  11 ,  11 ,  15 ,  15 ,  11 , 15 } // State 17
        };

        int state = 0; // initial state

        CharacterIterator ci = new StringCharacterIterator(input);

        while(ci.current() !=  CharacterIterator.DONE){
            int input_type;
            char inputC = ci.current();

            if (Character.isDigit(inputC)){
                inputC = Character.toLowerCase(inputC);

                input_type = switch (inputC) {
                    case '0' -> 0;
                    case '9' -> 1;
                    case '6' -> 2;
                    case '3' -> 3;
                    default -> 6; // other numbers
                };
            } else if (inputC == '+'){
                input_type = 4;
            } else if (inputC == '-'){
                input_type = 5;
            } else {
                input_type = 7; // other characters
            }

            state = transitionTable[state][input_type];
            ci.next();
        }

        if (state == 14){
            return 1;
        }

        return 0;
    }

    public void showResult(int res, String number){
        String result = "The phone number [ " + number + " ] is ";

        if (res == 1){
            result += "a VALID ";
        } else {
            result += "NOT a VALID ";
        }

        result += " Philippine phone number";

        lblResult.setText(result);
        vboxResult.setVisible(true);
    }
    public void onBtnCheckAnotherClick(ActionEvent actionEvent) {
        tfPhoneNumberInput.setText("");
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
