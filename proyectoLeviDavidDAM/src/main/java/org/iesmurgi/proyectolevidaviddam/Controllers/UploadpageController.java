package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.FileGetter;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//Dentro de contentRoot es donde se supone que va el contenido de nuestra página. Es para que el chatSlider se superponga encima de esta vista.
public class UploadpageController {

    @FXML
    private StackPane baseRoot;

    File selected_image;
    File default_image = new File("src/main/resources/org/iesmurgi/proyectolevidaviddam/images/cd-1293235_960_720.png");
    File file;
    boolean choosed_image = false;
    boolean choosed_music = false;

    @FXML
    private TextField textfieldNombre;
    @FXML
    private ChoiceBox<String> choiceboxLicenseType;
    @FXML
    private Button buttonChooseFile;
    @FXML
    private VBox vboxForm;
    @FXML
    private Button buttonTryUpload;
    @FXML
    private TextArea textareaDescription;
    @FXML
    private ImageView imageviewSongCover;
    @FXML
    private ChoiceBox<String> choiceboxGenere;


    public void initialize(){

        //Esto es porque para expandirse a todoo lo que ocupe la ventana, necesita indicarselo al padre del gridRoot, que en este caso
        //es el AnchorPane del hello-view.fxml. con fxid pageRoot
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot   .getParent()).setBottomAnchor(baseRoot,0.0);

        choiceboxGenere.getItems().add("rap");
        choiceboxGenere.getItems().add("rock");
        choiceboxGenere.getItems().add("drum and bass");
        choiceboxGenere.getItems().add("reggaeton");
        choiceboxGenere.getItems().add("reggae");
        choiceboxGenere.getItems().add("metal");
        choiceboxGenere.getItems().add("trap");
        choiceboxGenere.getItems().add("pop");
        choiceboxGenere.getItems().add("drill");

        choiceboxLicenseType.getItems().add("Uso libre");
        choiceboxLicenseType.getItems().add("Todos los derechos reservados");


    }

    @FXML
    void choose_image(MouseEvent event) throws IOException {

        Stage stage = (Stage) baseRoot.getScene().getWindow();
        FileChooser fileChooser;
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes PNG", "*.png"),
                new FileChooser.ExtensionFilter("Imágenes JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("Imágenes JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("Imágenes WEBP", "*.webp"),
                new FileChooser.ExtensionFilter("Imágenes ICO", "*.ico"),
                new FileChooser.ExtensionFilter("Imágenes SVG", "*.svg")
        );

        File selected = fileChooser.showOpenDialog(stage);

        if(selected.exists()){
            selected_image = selected;
            Image img = new Image(selected.getAbsolutePath());
            imageviewSongCover.setImage(img);
        }else{
            selected_image = default_image;
        }

        choosed_image = true;
    }

    @FXML
    private void upload_music() {

        if(choosed_music && !textfieldNombre.getText().equals("") && !textareaDescription.getText().equals("") && !choiceboxGenere.getValue().toString().equals("") && !choiceboxLicenseType.getValue().toString().equals("")){
            try{
                // EJEMPLO
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
                FileGetter multipart = new FileGetter();
                multipart.HttpPostMultipart(CONSTANT.URL.getUrl()+"/upload-item", "utf-8", headers);
                // Add form field
                multipart.addFormField("name", textfieldNombre.getText());
                multipart.addFormField("username", new GeneralDecoder().getUserFromToken());
                multipart.addFilePart("multiple-files", file);
                multipart.addFormField("genre", choiceboxGenere.getValue());
                multipart.addFormField("token", new TokenManager().getToken());
                multipart.addFormField("description", textareaDescription.getText());
                multipart.addFormField("copyright", String.valueOf(choiceboxLicenseType.getValue().equals("Todos los derechos reservados") ? "1" : "0"));
                if(choosed_image){
                    multipart.addFilePart("multiple-files", selected_image);
                }else{
                    multipart.addFilePart("multiple-files", default_image);
                }
                // Print result
                String response = multipart.finish();
                System.out.println(response);

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Canción subida!");
                a.setContentText("Su canción se ha subido correctamente");
                a.show();
            }catch (IOException e){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error en la carga");
                a.setContentText("Ha sucedido algo inesperado, inténtelo de nuevo...");
                a.show();
                e.printStackTrace();
            }
        }else{
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Campos vacios!");
            a.setContentText("Asegurese de rellenar todos los campos obligatorios");
            a.show();
        }
    }

    @FXML
    void choose_music(MouseEvent event) {

        Stage stage = (Stage) baseRoot.getScene().getWindow();

        FileChooser fileChooser;
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivo MP3", "*.mp3")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile.exists()){
            file = selectedFile;
            choosed_music = true;
        }else{
            choosed_music = false;
        }

    }

}
