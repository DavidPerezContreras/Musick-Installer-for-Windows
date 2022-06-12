package org.iesmurgi.proyectolevidaviddam.Controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.Auth;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LogIn {

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private VBox profileRoot;

    @FXML
    private TextField textFieldUsuario;

    Stage stage;
    Scene scene;
    @FXML
    private PasswordField textFieldContrasena;

    public void initialize() {
        /*textFieldUsuario.setOnAction((event -> {
            try {
                login(event);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        textFieldContrasena.setOnAction((event -> {
            try {
                login(event);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));*/



        btnRegistrarse.setOnAction(actionEvent -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign_up.fxml"));
                profileRoot.getChildren().clear();
                profileRoot.getChildren().add(fxmlLoader.load());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnIniciarSesion.setOnAction(actionEvent -> {
            finalLogin();
        });
        textFieldUsuario.setOnAction(event -> {
            finalLogin();
        });
        textFieldContrasena.setOnAction(event -> {
            finalLogin();
        });
    }

    boolean finalLogin(){

        String response = "";

        String url = CONSTANT.URL.getUrl()+"/login";
        GeneralDecoder md5 = new GeneralDecoder();

        try {
            Requester<String> t = new Requester<String>(url,  Requester.Method.POST, String.class);
            t.addParam("username", textFieldUsuario.getText());
            t.addParam("password", md5.encodeMD5(textFieldContrasena.getText()));
            response = t.execute();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }





        Platform.setImplicitExit(true);
        String finalResponse = response;
        boolean vuelta;

        Platform.runLater(()->{

            System.out.println("Respuesta: "+ finalResponse);
            if(finalResponse !=null) {//////////////////////////////////////////////////////SALE SI LA RESPUESTA ES NULL
                if (finalResponse.equals("login_error") || finalResponse.equals("")) {
                    Alert a = new Alert(Alert.AlertType.NONE);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setTitle("Error de Autentificación");
                    a.setContentText("El usuario o la contraseña son incorrectos");
                    a.show();

                } else {
                    //Llamamos al gestor del token para que guarde localmente el token
                    TokenManager tkm = new TokenManager();
                    tkm.tokenStorage(finalResponse);
                    System.out.println("Token de usuario: " + finalResponse);

                    scene = btnIniciarSesion.getScene();
                    stage = (Stage) scene.getWindow();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                        Parent helloView = fxmlLoader.load();
                        HelloController helloController = fxmlLoader.getController();


                        GeneralDecoder gd = new GeneralDecoder();


                        String username = gd.getUserFromToken();


                        Platform.runLater(() -> {
                            Requester<User[]> userRequester = null;
                            try {

                                Auth.userRequestLogin(username, tkm, helloView, helloController, scene, stage);
                                //Loads Home page

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if(finalResponse==null){
            System.out.println("LOGIN SUCCESSFUL");
            return false;
        }

        if(finalResponse.equals("login_error")){
            System.out.println("LOGIN ERROR");
            return false;

        }else{
            System.out.println("LOGIN SUCCESSFUL");
            return true;
        }//SI DEVUELVES UN BOOLEANO PUEDES HACER PRUEBA UNITARIA.

    }

    @FXML
    void openPasswordRecovery(){
        try {

            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("recovery-view.fxml"));

            // Cargo la ventana
            Parent root = loader.load();

            // Creo el Scene
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
/*
    @Deprecated
    void login(ActionEvent event) throws NoSuchAlgorithmException, IOException {
//        String response = "";
//
//        String url = CONSTANT.URL.getUrl()+"/login";
//        GeneralDecoder md5 = new GeneralDecoder();
//
//        try {
//            ArrayList<String[]> params = new ArrayList<>();
//            params.add(new String[]{"username", textFieldUsuario.getText()});
//            params.add(new String[]{"password", md5.encodeMD5(textFieldContrasena.getText())});
//            OpenThread<String> t = new OpenThread<>(url, params, "POST", String.class);
//            response = t.getResult();
//
//        } catch (IOException | InterruptedException | NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Respuesta: "+response);
//        if(response.equals("login_error") || response.equals("")){
//            Alert a = new Alert(Alert.AlertType.NONE);
//            a.setAlertType(Alert.AlertType.ERROR);
//            a.setTitle("Error de Autentificación");
//            a.setContentText("El usuario o la contraseña son incorrectos");
//            a.show();
//        }else{
//            //Llamamos al gestor del token para que guarde localmente el token
//            TokenManager tkm = new TokenManager();
//            tkm.tokenStorage(response);
//            System.out.println("Token de usuario: "+response);
//
//            scene =btnIniciarSesion.getScene();
//            stage = (Stage) scene.getWindow();
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//                Parent helloView = fxmlLoader.load() ;
//                HelloController helloController = fxmlLoader.getController();
//                helloController.loadHomePage(); //Loads Home page
//
//
//
//                GeneralDecoder gd = new GeneralDecoder();
//
//
//                String username= gd.getUserFromToken();
//
//
//                Requester<User[]> userRequester = new Requester<>("http://tux.iesmurgi.org:11230/user",Requester.Method.POST,User[].class);
//                userRequester.addParam("username",username);
//                helloController.loadUserData(userRequester.execute()[0]);
//
//                Scene s = new Scene(helloView, scene.getWidth(), stage.getHeight()-34, Color.BLACK);
//                stage.setScene(s);
//                stage.show();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }*/

}
