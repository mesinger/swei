package ui;

import business.IImageBL;
import business.PicDbBusinessLayer;
import database.IDatabaseAccess;
import database.IImageDAL;
import database.PicDatabaseAccess;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import models.ImageModel;
import image.JPEGImageDataExtractor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import presentationModels.ImagePresentationModel;
import util.Binding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StartPageController extends IController implements Initializable {
    public ImageView imageView;
    public Label iso;
    public Label camname;
    public Label exposure_time;
    public Label aperture;
    public Label focal_length;
    public Label exposure_program;
    public MenuItem photographer_edit;
    public GridPane imageData;
    public Button iptcSave;
    public TextField search;
    public MenuItem reportList;
    public MenuItem reportImage;
    @FXML
    private Imagescroll imgscroll;

    private ImageModel model;
    private ImagePresentationModel pres;

    private FileInputStream propertiesFile;
    private Properties properties = new Properties();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (loadProperties() && initializeDatabaseConnection()) {

            System.out.println("connected to db");

            loadImagesInImageScroll();

            initializeEventHandlers(resources);
        } else {
            System.out.println("error connecting to db");
        }
    }

    private boolean loadProperties() {

        try {
            propertiesFile = new FileInputStream(new File("config.txt"));
            properties.load(propertiesFile);
        } catch (FileNotFoundException e) {
            System.out.println("Configuration file not found!");
        } catch (IOException e) {
            System.out.println("Invalid configuration file, check the syntax!");
        }

        return properties != null;
    }

    private List<String> getImagePathsFromConfiguredFolder() {

        Function<String, Predicate<File>> checkForFileExtension = extension -> file -> file.getName().toLowerCase().endsWith(extension);

        var imagePaths =
                List.of(new File(properties.getProperty("IMG_DIR")).listFiles()).stream()
                        .filter(file -> file.isFile())
                        .filter(checkForFileExtension.apply("jpeg"))
                        .map(file -> file.getAbsolutePath())
                        .collect(Collectors.toList());

        imagePaths.addAll(List.of(new File(properties.getProperty("IMG_DIR")).listFiles()).stream()
                .filter(file -> file.isFile())
                .filter(checkForFileExtension.apply("jpg"))
                .map(file -> file.getAbsolutePath())
                .collect(Collectors.toList()));

        return imagePaths;
    }

    protected void loadImagesInImageScroll() {
        // Get all images in folder
        var imagePaths = getImagePathsFromConfiguredFolder();

        // Extract data from the found images
        var dataExtractor = new JPEGImageDataExtractor();

        imagePaths.stream()
                .map(path -> dataExtractor.extractExifAndIPTC(path))
                .filter(data -> bl.getByPath(data.getPath()).isEmpty())
                .forEach(data -> bl.addImage(data));

        bl.getAllImages().stream()
                .filter(image -> imagePaths.contains(image.getPath()))
                .forEach(image -> imgscroll.addPlaceholderBox(image));
    }

    private EventHandler<ActionEvent> onSearchEnteredAction() {
        return event -> {
            imgscroll.clear();

            if (search.getText().isEmpty()) {
                bl.getAllImages().stream()
                        .forEach(image -> imgscroll.addPlaceholderBox(image));
            } else {

                final List<ImageModel> toLoadingImages = new LinkedList<>();

                List.of(search.getText().split(" ")).stream()
                        .forEach(keyword -> {

                            List<ImageModel> imagesThisKeyword = bl.getByKeyword(keyword);

                            if (keyword.contains(":")) {
                                // This is a special keyword (e.g. iso)
                                String[] keyValue = keyword.split(":");

                                if (keyValue[0].equals("iso")) {
                                    imagesThisKeyword = bl.getByIso(keyValue[1]);
                                } else if (keyValue[0].equals("photographer")) {
                                    imagesThisKeyword = bl.getByPhotographer(Integer.valueOf(keyValue[1]));
                                } else if (keyValue[0].equals("title")) {
                                    imagesThisKeyword = bl.getByTitle(keyValue[1]);
                                }
                            }

                            toLoadingImages.addAll(imagesThisKeyword);
                        });

                toLoadingImages.stream()
                        .forEach(image -> imgscroll.addPlaceholderBox(image));
            }
        };
    }

    private ImageClickedEventHandler onImageScrollClicked() {
        return new ImageClickedEventHandler() {

            @Override
            public void onClicked(ImageModel image) {
                model = image;
                pres = new ImagePresentationModel(model);
                pres.loadDataFromModel();

                Binding.applyBinding(imageData, pres);

                imageView.setImage(new Image("file:///" + image.getPath())); // TODO: Move this 'file:///'
                camname.setText("Camera model: " + image.getModel());
                iso.setText("ISO: " + image.getIso());
                exposure_time.setText("Exposure time: " + image.getExposure());
                focal_length.setText("Focal length: " + image.getFocalLength());
                aperture.setText("Aperture: " + image.getAperture());
            }
        };
    }

    private EventHandler<ActionEvent> onIptcSave() {
        return event -> {
            if (pres != null && pres.getPhotographerID() != null && (pres.getPhotographerID().equals("0")
                    || bl.getByPhotographer(Integer.valueOf(pres.getPhotographerID())) != null)) {
                pres.saveDataToModel();
                bl.editImage(model);
            }
        };
    }

    private EventHandler<ActionEvent> onPhotographerEdited(ResourceBundle resources) {
        return actionEvent -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("photographers.fxml"), resources);
                Stage stage = new Stage();
                stage.setTitle("Photographers");
                stage.setScene(new Scene(root, 600, 600));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private void initializeEventHandlers(ResourceBundle resources) {
        search.setOnAction(onSearchEnteredAction());

        // Add event handler for when an image is clicked
        imgscroll.addEventHandler(ImageClickedEvent.IMAGE_CLICKED_EVENT_TYPE, onImageScrollClicked());

        iptcSave.setOnAction(onIptcSave());

        // Open photographer editing menu
        photographer_edit.setOnAction(onPhotographerEdited(resources));
    }
}
