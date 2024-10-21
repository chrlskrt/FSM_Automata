module com.example.fsm_automata {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fsm_automata to javafx.fxml;
    exports com.example.fsm_automata;
}