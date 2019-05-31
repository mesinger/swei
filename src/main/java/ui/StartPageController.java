package ui;

import database.PicDatabaseAccess;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    private Imagescroll imgscroll;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PicDatabaseAccess db = new PicDatabaseAccess();

        if (db.open()) {
            System.out.println("connected to db");

            db.setup();

            // TODO: This shouldn't be done on initialize, but only when the images need to be synced with the database
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
                db.addImage(data);

                // TODO: Don't use the data directly from the image file, get it from the database!
                imgscroll.addPlaceholderBox(data);
            }

            // Add event handler for when an image is clicked
            imgscroll.addEventHandler(ImageClickedEvent.IMAGE_CLICKED_EVENT_TYPE, new ImageClickedEventHandler() {
                @Override
                public void onClicked(ImageModel image) {
                    imageView.setImage(new Image("file:///" + image.getPath())); // TODO: Move this 'file:///'
                    camname.setText("Camera model: " + image.getModel());
                    iso.setText("ISO: " + image.getIso());
                    exposure_time.setText("Exposure time: " + image.getExposure());
                    focal_length.setText("Focal length: " + image.getFocalLength());
                    aperture.setText("Aperture: " + image.getAperture());

                    // TODO: Bindings for IPTC like in photographer controller
                }
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
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            System.out.println("error connecting to db");
        }
    }
}
