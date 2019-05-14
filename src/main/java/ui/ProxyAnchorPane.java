package ui;

import javafx.scene.layout.AnchorPane;

public class ProxyAnchorPane extends AnchorPane {
    public ProxyAnchorPane(String imagepath) {
        this.imagepath = imagepath;
    }

    private String imagepath;

    public String getImagepath() {
        return imagepath;
    }
}
