package ui;

import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PhotographersController implements Initializable {
    public ListView photographerList;
    public Button addPhotographerButton;
    public TextField firstName;
    public TextField lastName;
    public DatePicker dateOfBirth;
    public TextField notes;
    public Button saveButton;

    private boolean allFieldsValid() {
        return (!lastName.getText().isEmpty() && dateOfBirth.getValue() != null && dateOfBirth.getValue().isBefore(LocalDate.now()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setDisable(true);

        ChangeListener textChangedListener = (observable, oldValue, newValue) -> saveButton.setDisable(!allFieldsValid());

        firstName.textProperty().addListener(textChangedListener);
        lastName.textProperty().addListener(textChangedListener);
        dateOfBirth.valueProperty().addListener(textChangedListener);
    }
}
