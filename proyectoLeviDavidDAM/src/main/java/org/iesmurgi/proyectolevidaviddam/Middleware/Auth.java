package org.iesmurgi.proyectolevidaviddam.Middleware;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Controllers.HelloController;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Auth {

    Stage stage;
    Scene scene;
    GeneralDecoder md5 = new GeneralDecoder();

    public void logIn(String username, String password, Button button) throws IOException {
        String response = "";

        String url = CONSTANT.URL.getUrl() + "/login";

        try {
            Requester<String> t = new Requester<String>(url,  Requester.Method.POST, String.class);
            t.addParam("username", username);
            t.addParam("password", md5.encodeMD5(password));
            response = t.execute();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        TokenManager tkm = new TokenManager();
        tkm.tokenStorage(response);
        System.out.println("Token de usuario: " + response);

        scene = button.getScene();
        stage = (Stage) scene.getWindow();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent helloView = fxmlLoader.load();
            HelloController helloController = fxmlLoader.getController();


            Platform.runLater(() -> {
                Requester<User[]> userRequester = null;
                try {

                    userRequestLogin(username, tkm, helloView, helloController, scene, stage);
                    //Loads Home page

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void userRequestLogin(String username, TokenManager tkm, Parent helloView, HelloController helloController, Scene scene, Stage stage) throws IOException {
        Requester<User[]> userRequester;
        userRequester = new Requester<>("http://tux.iesmurgi.org:11230/user", Requester.Method.POST, User[].class);
        userRequester.addParam("username", username);
        userRequester.addParam("token", tkm.getToken());
        User[] user = userRequester.execute();
        user[0].setOnline(1);
        helloController.loadUserData(user[0]);
        helloController.loadHomePage();

        Requester<String> set_online = new Requester<>("http://tux.iesmurgi.org:11230/set-online", Requester.Method.POST, String.class);
        set_online.addParam("token", tkm.getToken());
        set_online.addParam("online", "true");
        set_online.addParam("username", username);
        set_online.execute();

        Scene s = new Scene(helloView, scene.getWidth(), stage.getHeight() - 34, Color.BLACK);
        stage.setScene(s);
        stage.show();
    }
}
