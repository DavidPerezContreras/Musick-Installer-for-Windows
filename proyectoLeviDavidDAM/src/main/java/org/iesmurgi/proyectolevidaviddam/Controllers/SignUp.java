package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.*;
import org.iesmurgi.proyectolevidaviddam.models.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

public class SignUp {
    @FXML
    private Button btnRegistrarse;

    @FXML
    private Label lblApellidos;

    @FXML
    private Label lblContraseña;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblNombre1;

    @FXML
    private Label lblRepetirContraseña;

    @FXML
    private Hyperlink login;

    @FXML
    private PasswordField pwdContraseña;

    @FXML
    private PasswordField pwdRepetirContraseña;

    @FXML
    private TextField textFieldApellidos;

    @FXML
    private TextField textFieldCorreo;

    @FXML
    private TextField textFieldNick;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private VBox vBoxCard;

    @FXML
    private VBox vBoxSignUp;

    boolean mail = false;
    boolean username = false;

    Stage stage;
    Scene scene;
    @FXML
    private Hyperlink hyperlinkLicencia;
    @FXML
    private Hyperlink hyperlinkPrivacidad;


    public void initialize(){
//        lblNombre.getStyleClass().setAll("lbl","lbl-primary");
//        lblApellidos.getStyleClass().setAll("lbl", "lbl-primary");
//        lblCorreo.getStyleClass().setAll("lbl", "lbl-primary");
//        lblContraseña.getStyleClass().setAll("lbl", "lbl-primary");
//        lblRepetirContraseña.getStyleClass().setAll("lbl", "lbl-primary");
        //btnRegistrarse.getStyleClass().setAll("btn", "btn-success");

        hyperlinkLicencia.setOnAction((event -> {
            try {
                Desktop.getDesktop().browse(new URL("https://www.gnu.org/licenses/gpl-3.0.en.html").toURI());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }));

        hyperlinkPrivacidad.setOnAction((event -> {
            try {
                Desktop.getDesktop().browse(new URL("https://www.privacypolicies.com/live/d5656c62-1743-44ef-93fa-0d0e6cb9c1ce").toURI());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }));



    }

    @FXML
    void login(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("log_in.fxml"));
            vBoxSignUp.getChildren().clear();
            vBoxSignUp.getChildren().add(fxmlLoader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signup(ActionEvent event) throws IOException, NoSuchAlgorithmException, InterruptedException {
        Platform.runLater(()->{
            try {
                String url3 = CONSTANT.URL.getUrl()+"/signup";

                if(!pwdContraseña.getText().equals(pwdRepetirContraseña.getText())){
                    textFieldCorreo.getStyleClass().add("text-field");
                    textFieldNick.getStyleClass().add("text-field");
                    pwdContraseña.getStyleClass().add("text-field-error");
                    pwdRepetirContraseña.getStyleClass().add("text-field-error");
                    Alert a = new Alert(Alert.AlertType.NONE);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setTitle("Error!");
                    a.setContentText("Las contraseñas no coinciden");
                    a.show();
                }else{
                    if(!textFieldNick.getText().equals("") && !textFieldCorreo.getText().equals("") && !pwdContraseña.getText().equals("")){
                        if(checkMail() && checkUsername()){
                            Requester<String> requester = new Requester<>(url3, Requester.Method.POST,String.class);
                            requester.addParam("username", textFieldNick.getText());
                            requester.addParam("email", textFieldCorreo.getText());
                            GeneralDecoder md5 = new GeneralDecoder();
                            requester.addParam("password", md5.encodeMD5(pwdContraseña.getText()));
                            requester.addParam("name", textFieldNombre.getText());
                            requester.addParam("surname", textFieldApellidos.getText());
                            requester.execute();
                            //Llamamos al gestor del token para que guarde localmente el token
                            new Auth().logIn(textFieldNick.getText(), pwdContraseña.getText(), btnRegistrarse);
                        }
                    }else{
                        Alert a = new Alert(Alert.AlertType.NONE);
                        a.setAlertType(Alert.AlertType.ERROR);
                        a.setTitle("Campos vacios!");
                        a.setContentText("Asegurese de rellenar todos los campos obligatorios");
                        a.show();
                        //AQUI DEBO AÑADIR ADEMÁS QUE SE APLIQUEN CLASES CSS ESPECÍFICAS PARA TEXTFIELD CON ERROR -> PENDIENTE
                        pwdContraseña.getStyleClass().add("text-field-error");
                        pwdContraseña.getStyleClass().add("text-field-error");
                        pwdRepetirContraseña.getStyleClass().add("text-field-error");
                        textFieldCorreo.getStyleClass().add("text-field-error");
                        textFieldNick.getStyleClass().add("text-field-error");
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    private synchronized boolean checkMail() throws IOException, InterruptedException {

        boolean mail = false;

       if(!isValidEmailAddress(textFieldCorreo.getText())){
           Alert a = new Alert(Alert.AlertType.NONE);
           a.setAlertType(Alert.AlertType.ERROR);
           a.setTitle("Error!!");
           a.setContentText("El correo electrónico no cumple con \n" +
                   "el siguiente formato: xxxxxx@xxxxxxxx.xxx ");
           a.show();
           return false;
       }else{



        String url1 = CONSTANT.URL.getUrl()+"/check-email";
        Requester<String> stringRequester1 = new Requester<>(url1, Requester.Method.POST,String.class);
        stringRequester1.addParam("email", textFieldCorreo.getText());
        String[] stringRespuesta1 = new String[]{stringRequester1.execute()};
        String result1 = stringRespuesta1[0];

        if(result1.equals("mail_error")) {
            textFieldNick.getStyleClass().add("text-field");
            textFieldCorreo.getStyleClass().add("text-field-error");
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error!!");
            a.setContentText("El correo introducido ya está en uso");
            a.show();
        }else {
            System.out.println("Email validado");
            mail = true;
        }
        System.out.println("Comprobando username");

        return mail;}
    }

    private synchronized boolean checkUsername() throws IOException, InterruptedException {

        boolean username = false;

        String url2 = CONSTANT.URL.getUrl()+"/check-username";
        Requester<String> stringRequester2 = new Requester<>(url2, Requester.Method.POST,String.class);
        stringRequester2.addParam("username", textFieldNick.getText());
        String[] stringRespuesta2 = new String[]{stringRequester2.execute()};
        String result2 = stringRespuesta2[0];

        if(result2.equals("username_error")) {
            textFieldNick.getStyleClass().add("text-field-error");
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setContentText("El nick introducido ya está en uso");
            a.show();
        }else{
            System.out.println("Nick validado");
            username = true;
        }

        return username;
    }
}
