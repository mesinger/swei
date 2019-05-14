package ui;

import image.IImageData;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.imageio.metadata.IIOMetadata;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Imagescroll extends ScrollPane {

    @FXML
    private HBox imageBox;

    private ObservableList<Node> visibleNodes = FXCollections.observableArrayList();

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

        vvalueProperty().addListener((obs) -> checkVisible());
        hvalueProperty().addListener((obs) -> checkVisible());

        visibleNodes.addListener((ListChangeListener<Node>) c -> {

            for (Node node : visibleNodes) {

                Pane pane = (Pane)node;

                if ((pane).getChildren().size() == 0) {
                    String path = ((ProxyAnchorPane)pane).getImagepath();
                    addImage(new Image(path), pane);
                }

            }
        });
    }

    public void addPlaceholderBox(IImageData image) {
        AnchorPane anchorPane = new ProxyAnchorPane(image);

        // Bind the width to the height of the Imagescroll to simulate images with 1:1 aspect ratio
        anchorPane.prefWidthProperty().bind(heightProperty());
        anchorPane.setFocusTraversable(true);

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
}

class ImageClickedEvent extends Event {

    private IImageData image;

    public static final EventType<ImageClickedEvent> IMAGE_CLICKED_EVENT_TYPE = new EventType(ANY);

    public ImageClickedEvent(IImageData image) {
        super(IMAGE_CLICKED_EVENT_TYPE);
        this.image = image;
    }

    public void invokeHandler(ImageClickedEventHandler handler) {
        handler.onClicked(image);
    }

}

abstract class ImageClickedEventHandler implements EventHandler<ImageClickedEvent> {

    public abstract void onClicked(IImageData image);

    @Override
    public void handle(ImageClickedEvent event) {
        event.invokeHandler(this);
    }
}