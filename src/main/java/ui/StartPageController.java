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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {
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
    @FXML
    private Imagescroll imgscroll;

    private ImageModel model;
    private ImagePresentationModel pres;

    // TODO: We shouldn't need both of these
    private IImageDAL dal = new PicDatabaseAccess();
    private IDatabaseAccess dbaccess = new PicDatabaseAccess();

    private IImageBL bl = new PicDbBusinessLayer();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbaccess.open();
        bl.setDAL(dbaccess);

        PicDatabaseAccess db = new PicDatabaseAccess();

        if (db.open()) {
            System.out.println("connected to db");

            db.setup();

            // Get all images in folder
            List<String> imagePaths = new ArrayList<String>();
            File[] files = new File("img/").listFiles();

            for (File file : files) {
                if (file.isFile() && (file.getName().toLowerCase().endsWith("jpg") ||
                        file.getName().toLowerCase().endsWith("jpeg"))) {
                    imagePaths.add(file.getAbsolutePath());
                }
            }

            // Extract data from the found images
            JPEGImageDataExtractor dataExtractor = new JPEGImageDataExtractor();

            for (String image : imagePaths) {
                ImageModel data = dataExtractor.extractExifAndIPTC(image);

                // Add image if it's not already in the database
                if (bl.getByPath(data.getPath()).isEmpty()) {
                    db.addImage(data);
                }
            }

            for (ImageModel image : bl.getAllImages()) {
                imgscroll.addPlaceholderBox(image);
            }

            search.setOnAction(
                    e -> {
                        // Enter was pressed in the search field, load only the correct images
                        imgscroll.clear();

                        List<ImageModel> newImages = new LinkedList<>();

                        // If the text is empty, get all images
                        if (search.getText().isEmpty()) {
                            newImages = bl.getAllImages();
                        } else {
                            for (String keyword : search.getText().split(" ")) {
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

                                for (ImageModel image : imagesThisKeyword) {
                                    if (!newImages.contains(image)) {
                                        newImages.add(image);
                                    }
                                }
                            }
                        }

                        for (ImageModel image : newImages) {
                            imgscroll.addPlaceholderBox(image);
                        }
                    });

            // Add event handler for when an image is clicked
            imgscroll.addEventHandler(ImageClickedEvent.IMAGE_CLICKED_EVENT_TYPE, new

                    ImageClickedEventHandler() {
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
                    });

            iptcSave.setOnAction(actionEvent -> {
                pres.saveDataToModel();
                dal.editImage(model);
            });


            // Open photographer editing menu
            photographer_edit.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
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
                }
            });
        } else {
            System.out.println("error connecting to db");
        }
    }
}
