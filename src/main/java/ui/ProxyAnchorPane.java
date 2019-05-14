package ui;

import image.IImageData;
import javafx.scene.layout.AnchorPane;

public class ProxyAnchorPane extends AnchorPane {
    public ProxyAnchorPane(IImageData image) {
        this.image = image;
    }

    public String getImagepath() {
        return "file:///" + image.getPath();
    }

    public String getIPTC() {
        return image.getKeywords();
    }

    public String getEXIF() {
        return String.valueOf(image.getIso());
    }

    private IImageData image;
}
