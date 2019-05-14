package ui;

import database.PicDatabaseAccess;
import image.IImageData;
import image.JPEGImageDataExtractor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {

    public Label iptclabel;
    public Label exiflabel;
    @FXML
    private Imagescroll imgscroll;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

            JPEGImageDataExtractor dataExtractor = new JPEGImageDataExtractor();

            for (String image : imagePaths) {
                IImageData data = dataExtractor.extractExifAndIPTC(image);

                db.addImage(data);

                imgscroll.addPlaceholderBox(data);
            }

            imgscroll.addEventHandler(ImageClickedEvent.IMAGE_CLICKED_EVENT_TYPE, new ImageClickedEventHandler() {
                @Override
                public void onClicked(IImageData image) {
                    iptclabel.setText(image.getPath());
                }
            });
        } else {
            System.out.println("error");
        }
    }
}
