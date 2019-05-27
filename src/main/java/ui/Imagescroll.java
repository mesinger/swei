package ui;

import javafx.fxml.Initializable;
import models.ImageModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Imagescroll extends ScrollPane implements Initializable {

    @FXML
    private HBox imageBox;

    private ObservableList<Node> visibleNodes = FXCollections.observableArrayList();

    public Imagescroll() {
        // Setup custom FXML element
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

    public void addPlaceholderBox(ImageModel image) {
        AnchorPane anchorPane = new ProxyAnchorPane(image);

        // Bind the width to the height of the Imagescroll to simulate images with 1:1 aspect ratio
        anchorPane.prefWidthProperty().bind(heightProperty());
        anchorPane.setFocusTraversable(true);

        // When an image is clicked, fire the custom ImageClickedEvent with the ImageModel
        anchorPane.setOnMouseClicked((EventHandler) -> {
            fireEvent(new ImageClickedEvent(image));
        });

        imageBox.getChildren().add(anchorPane);
    }

    private void addImage(Image img, Pane pane) {
        ImageView imageView = new ImageView(img);
        imageView.fitHeightProperty().bind(imageBox.heightProperty());
        imageView.setPreserveRatio(true);

        // TODO: This ignores the bottom scrollbar -> images are slightly too high, vertical scrollbar appears
        imageView.fitHeightProperty().bind(heightProperty());

        pane.getChildren().add(imageView);
    }

    private void checkVisible() {
        visibleNodes.setAll(getVisibleNodes());
    }

    private List<Node> getVisibleNodes() {
        List<Node> visibleNodes = new ArrayList<>();
        Bounds paneBounds = localToScene(getBoundsInParent());

        if (getContent() instanceof Parent) {
            for (Node n : ((Parent) getContent()).getChildrenUnmodifiable()) {
                Bounds nodeBounds = n.localToScene(n.getBoundsInLocal());
                if (paneBounds.intersects(nodeBounds)) {
                    visibleNodes.add(n);
                }
            }
        }

        return visibleNodes;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // When a ProxyAnchorPane becomes visible, the corresponding image is put into it
        // TODO: It would be nice if we could do this in the ProxyAnchorPane, not here!
        vvalueProperty().addListener((obs) -> checkVisible());
        hvalueProperty().addListener((obs) -> checkVisible());

        visibleNodes.addListener((ListChangeListener<Node>) c -> {
            for (Node node : visibleNodes) {
                Pane pane = (Pane) node;

                if ((pane).getChildren().size() == 0) {
                    String path = ((ProxyAnchorPane) pane).getImagepath();

                    // This is just the preview, so we can compress the image for better performance
                    addImage(new Image(path, 300, 300, true, false), pane);
                }

            }
        });
    }

    //zazcek hotfix for scrollview
    private boolean isInitialized = false;
    @Override
    public void requestLayout() {
        super.requestLayout();

        if(!isInitialized){
            isInitialized = true;
            checkVisible();
        }
    }
}

class ImageClickedEvent extends Event {

    private ImageModel image;

    public static final EventType<ImageClickedEvent> IMAGE_CLICKED_EVENT_TYPE = new EventType<>(ANY);

    public ImageClickedEvent(ImageModel image) {
        super(IMAGE_CLICKED_EVENT_TYPE);
        this.image = image;
    }

    public void invokeHandler(ImageClickedEventHandler handler) {
        handler.onClicked(image);
    }

}

abstract class ImageClickedEventHandler implements EventHandler<ImageClickedEvent> {

    public abstract void onClicked(ImageModel image);

    @Override
    public void handle(ImageClickedEvent event) {
        event.invokeHandler(this);
    }
}