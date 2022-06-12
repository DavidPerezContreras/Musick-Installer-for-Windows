package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class AboutController {

    @javafx.fxml.FXML
    private StackPane baseRoot;
    @javafx.fxml.FXML
    private Hyperlink hyperlinkDavid;
    @javafx.fxml.FXML
    private Hyperlink hyperlinkLevi;

    public void initialize(){
        hyperlinkLevi.setOnAction((event -> {
            try {
                Desktop.getDesktop().browse(new URL("mailto:lewisvicna@gmail.com").toURI());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }));

        hyperlinkDavid.setOnAction((event -> {
            try {
                Desktop.getDesktop().browse(new URL("mailto:david.perez.dam2@gmail.com").toURI());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }));
    }
}
