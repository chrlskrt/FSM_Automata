package com.example.fsm_automata;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class CheckWordANDController {
    public VBox vboxResult;
    public TextField tfWordInput;
    public Button btnCheckWord;
    public Label lblResult;
    Alert a = new Alert(Alert.AlertType.NONE);

    public void onBtnCheckWordClick(ActionEvent actionEvent) {
        String input = tfWordInput.getText();

        if (input.isEmpty() || input.isBlank()){
            a.setAlertType(Alert.AlertType.WARNING);
            a.setHeaderText("No word detected.");
            a.setContentText("Please input a valid word.");
            a.show();
        } else {
            int result = validateWord(input);
            showResult(result, input);
        }
    }


    private int validateWord(String input){
        /*
        * Checking if the input word starts and ends with 'and'
        * Words that contain special characters are considered as invalid.
        * inputs: 'a/A' , 'n/N', 'd/D', other_letters, special_chars/numbers
        *
        * This function returns an integer.
        *        1 - true, if the word starts and ends with 'and'
        *        0 - false, if the word does starts and ends with 'and'
        *       -1 - if the word is invalid.
        */

        int[][] transitionTable = {
                {1, 8, 8, 8, 9}, // State 0
                {8, 2, 8, 8, 9}, // State 1
                {8, 8, 3, 8, 9}, // State 2
                {4, 7, 7, 7, 9}, // State 3 - accepting state
                {4, 5, 7, 7, 9}, // State 4
                {4, 7, 6, 7, 9}, // State 5
                {4, 7, 7, 7, 9}, // State 6 - accepting state
                {4, 7, 7, 7, 9}, // State 7
                {8, 8, 8, 8, 9}, // State 8 - dead state
                {9, 9, 9, 9, 9} // State 9 - invalid word
        };

        int state = 0; // initial state is 0
        CharacterIterator ci = new StringCharacterIterator(input);

        while(ci.current() !=  CharacterIterator.DONE){
            int input_type;
            char inputC = ci.current();
//            System.out.print(inputC + " ");

            if (Character.isAlphabetic(inputC)){
                inputC = Character.toLowerCase(inputC);

                input_type = switch (inputC) {
                    case 'a' -> 0;
                    case 'n' -> 1;
                    case 'd' -> 2;
                    default -> 3; // other letters
                };
            } else {
                input_type = 4;
            }

            state = transitionTable[state][input_type];
            ci.next();
        }

        if (state == 3 || state == 6){
            return 1;
        } else if (state == 9){
            return -1;
        }

        return 0;
    }

    public void showResult(int res, String input){
        String result = "The word [ " + input + " ] ";
        if (res == -1){
            result += "is not a valid word.";
        } else {
            if (res == 1){
                result += "PASSES ";
            } else {
                result += "DOES NOT PASS ";
            }

            result += "the validation.";
        }

        lblResult.setText(result);
        vboxResult.setVisible(true);
    }

    public void onBtnCheckAnotherWordClick(ActionEvent actionEvent) {
        tfWordInput.setText("");
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
