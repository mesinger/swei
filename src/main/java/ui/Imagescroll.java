package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class Imagescroll extends ScrollPane {

    @FXML
    private HBox imageBox;

    public Imagescroll() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "imagescroll.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // TODO: Remove after testing
        for (int i = 0; i < 10; i++) {
            //addImage(new Image("file:///home/karl/Data/Technikum/SEM4/SWEI/swei/procedurally.png"));
            addPlaceholderBox();
        }
    }

    private void addPlaceholderBox() {
        AnchorPane anchorPane = new AnchorPane();

        // Bind the width to the height of the Imagescroll to simulate images with 1:1 aspect ratio
        anchorPane.prefWidthProperty().bind(heightProperty());

        imageBox.getChildren().add(anchorPane);
    }

    public void addImage(Image img) {
        ImageView imageView = new ImageView(img);
        imageView.fitHeightProperty().bind(imageBox.heightProperty());
        imageView.setPreserveRatio(true);

        // TODO: This ignores the bottom scrollbar -> images are slightly too high, vertical scrollbar appears
        imageView.fitHeightProperty().bind(heightProperty());

        imageBox.getChildren().add(imageView);
    }
}