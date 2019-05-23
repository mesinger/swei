package ui;

import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import models.PhotographerModel;
import presentationModels.PhotographerPresentationModel;
import util.Binding;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PhotographersController implements Initializable {
    public GridPane photographerData;
    private PhotographerModel model;
    private PhotographerPresentationModel presModel;
    public ListView photographerList;
    public Button addPhotographerButton;
    public TextField firstName;
    public TextField lastName;
    public DatePicker dateOfBirth;
    public TextField notes;
    public Button saveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setDisable(true);

        saveButton.setOnAction(actionEvent -> {
            presModel.saveDataToModel();
        });

        model = new PhotographerModel();
        presModel = new PhotographerPresentationModel(model);

        Binding.applyBinding(photographerData, presModel);

        ChangeListener textChangedListener = (observable, oldValue, newValue) ->
                saveButton.setDisable(!presModel.isValid());

        firstName.textProperty().addListener(textChangedListener);
        lastName.textProperty().addListener(textChangedListener);
        dateOfBirth.valueProperty().addListener(textChangedListener);

    }
}
