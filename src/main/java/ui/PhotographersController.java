package ui;

import database.IPhotographerDAL;
import database.MockDAL;
import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.PhotographerModel;
import presentationModels.PhotographerPresentationModel;
import util.Binding;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PhotographersController implements Initializable {
    public GridPane photographerData;
    public ListView photographerList;
    public Button addPhotographerButton;
    public TextField firstName;
    public TextField lastName;
    public DatePicker dateOfBirth;
    public TextField notes;
    public Button saveButton;

    private IPhotographerDAL dal = new MockDAL();
    private PhotographerModel model = new PhotographerModel();
    private PhotographerPresentationModel presModel = new PhotographerPresentationModel(model);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Button is disabled when input is invalid
        // FIXME: Button only gets enabled after field has been modified twice!
        ChangeListener textChangedListener = (observable, oldValue, newValue) ->
                saveButton.setDisable(!presModel.isValid());

        presModel.firstNameProperty().addListener(textChangedListener);
        presModel.surNameProperty().addListener(textChangedListener);
        presModel.birthDateProperty().addListener(textChangedListener);

        saveButton.setDisable(true);

        // When save button is clicked, the data is saved to the list and it is reloaded
        saveButton.setOnAction(actionEvent -> {
            presModel.saveDataToModel();
            if (model.getId() == 0) {
                dal.addPhotographer(model);
            } else {
                dal.editPhotographer(model);
            }
            reloadList();
            model = new PhotographerModel();
            presModel = new PhotographerPresentationModel(model);
        });

        // When a new photographer is added, a new model and presentation model are created and bound to the fields
        addPhotographerButton.setOnAction(actionEvent -> {
            model = new PhotographerModel();
            presModel = new PhotographerPresentationModel(model);
            Binding.applyBinding(photographerData, presModel);
        });

        // Initialize empty model and binding once
        Binding.applyBinding(photographerData, presModel);

        // Items display the full name of their photographer model
        photographerList.setCellFactory(lv -> new ListCell<PhotographerModel>() {
            public void updateItem(PhotographerModel photographer, boolean empty) {
                if (!empty) {
                    var presentation = new PhotographerPresentationModel(photographer);
                    presentation.loadDataFromModel();
                    super.updateItem(photographer, empty);
                    setText(photographer.getId() + ": " + presentation.getFullName());
                }
            }
        });

        // When an item is clicked, a presentation model is created for it and bound to the fields
        photographerList.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<PhotographerModel>) (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        presModel = new PhotographerPresentationModel(newValue);
                        presModel.loadDataFromModel();
                        Binding.applyBinding(photographerData, presModel);
                    }
        });
    }

    // Get all photographers from the DAL and insert them into the photographer list
    private void reloadList() {
        List<PhotographerModel> photographers = dal.getAllPhotographers();
        photographerList.getItems().clear();

        for (var photographer : photographers) {
            photographerList.getItems().add(photographer);
        }
    }
}
