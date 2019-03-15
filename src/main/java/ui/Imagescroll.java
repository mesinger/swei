package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import java.io.IOException;

public class Imagescroll extends ScrollPane {
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
    }
}