package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.FileGetter;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SettingsController {

    TokenManager tk = new TokenManager();
    GeneralDecoder gd = new GeneralDecoder();

    @FXML
    private StackPane baseRoot;

    @FXML
    private ImageView profileImage;

    @FXML
    private PasswordField changepdw;

    @FXML
    private PasswordField rechangepwd;

    @FXML
    private TextField state;


    @FXML
    void initialize() throws IOException {
        Platform.runLater(()->{
            String url = CONSTANT.URL.getUrl()+"/download-image";
            FileGetter fileGetter = null;
            try {
                fileGetter = new FileGetter(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            fileGetter.addParam("username", gd.getUserFromToken());
            fileGetter.addParam("token", tk.getToken());
            try {
                profileImage.imageProperty().bind(fileGetter.getImage().imageProperty());
            } catch (IOException e) {
                e.printStackTrace();
            }
            profileImage.setFitHeight(200);
            profileImage.setFitWidth(200);
        });
    }

    @FXML
    void sendData(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        if(!changepdw.getText().equals("") && !rechangepwd.getText().equals("") && changepdw.getText().equals(rechangepwd.getText())){
            //si el usuario escribió bien los campos de contraseña, esta se cambia al darle al botón de aplicar
            Requester<String> req = new Requester<>(CONSTANT.URL.getUrl()+"/reset-password-settings", Requester.Method.POST, String.class);
            req.addParam("username", gd.getUserFromToken());
            req.addParam("password", gd.encodeMD5(changepdw.getText()));
            req.addParam("token", tk.getToken());
            req.execute();
            changepdw.clear();
            rechangepwd.clear();
        }

        if(!state.getText().equals("")){
            //si el usuario escribio algo en estado este se cambia al darle al botón de aplicar
            Requester<String> req = new Requester<>(CONSTANT.URL.getUrl()+"/update-state", Requester.Method.POST, String.class);
            req.addParam("username", gd.getUserFromToken());
            System.out.println(state.getText());
            req.addParam("state", state.getText());
            req.addParam("token", tk.getToken());
            req.execute();
            state.clear();
        }
    }

    @FXML
    void changeProfileImage(MouseEvent event) throws IOException {

        Platform.runLater(()->{
            Stage stage = (Stage) baseRoot.getScene().getWindow();

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imágenes PNG", "*.png"),
                    new FileChooser.ExtensionFilter("Imágenes JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("Imágenes JPEG", "*.jpeg"),
                    new FileChooser.ExtensionFilter("Imágenes WEBP", "*.webp"),
                    new FileChooser.ExtensionFilter("Imágenes ICO", "*.ico"),
                    new FileChooser.ExtensionFilter("Imágenes SVG", "*.svg")
            );

            File selectedFile = fileChooser.showOpenDialog(stage);

            if(selectedFile.exists()){
                // EJEMPLO
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
                FileGetter multipart = new FileGetter();
                try {
                    multipart.HttpPostMultipart(CONSTANT.URL.getUrl()+"/upload-image", "utf-8", headers);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Add form field
                multipart.addFormField("username", new GeneralDecoder().getUserFromToken());
                multipart.addFormField("token", new TokenManager().getToken());
                // Add file
                try {
                    multipart.addFilePart("image", selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Print result
                String response = null;
                try {
                    response = multipart.finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
            }

            try {
                initialize();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Requester<User[]> userRequester = null;
            User me = null;
            try {
                userRequester = new Requester<>("http://tux.iesmurgi.org:11230/user", Requester.Method.POST, User[].class);
                userRequester.addParam("username", new GeneralDecoder().getUserFromToken());
                userRequester.addParam("token", new TokenManager().getToken());
                helloController.loadUserData(userRequester.execute()[0]);
                me = userRequester.execute()[0];

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                helloController.loadUserData(me);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setTitle("Imagen Actualizada!");
            a.setContentText("La imagen se ha cambiado correctamente!");
            a.show();
        });

    }

    HelloController helloController;
    public void loadImageView(HelloController helloController) {
        this.helloController = helloController;
    }
}
