package org.iesmurgi.proyectolevidaviddam;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@ExtendWith(ApplicationExtension.class)

class GeneralTest {
    VBox mainroot;
    Stage mainstage;

    @Start
    public void start(Stage stage) throws IOException {
        mainroot = FXMLLoader.load(getClass().getResource("log_in.fxml"));
        mainstage = stage;
        stage.setScene(new Scene(mainroot, 1200, 900));
        stage.show();
        stage.toFront();
    }


    @Test
    void all_menu_buttons_works_correctly(FxRobot robot) throws InterruptedException {

        //Hacemos que el robot inicie sesión
        robot.clickOn("#textFieldUsuario");
        robot.write("test");
        robot.clickOn("#textFieldContrasena");
        robot.write("test");
        robot.clickOn("#btnIniciarSesion");

        //Comprobamos que funcionan todos los botones del menú superior y de la barra lateral

        //Comprueba el btn de perfil
        robot.clickOn("#topProfileButton");
        if(robot.lookup("#labelUsername").tryQuery().isPresent()) {
            FxAssert.verifyThat("#labelUsername", LabeledMatchers.hasText("test"));
        }

        //Comprueba el btn de contacts
        robot.clickOn("#labelTopMenu3");
        if(robot.lookup("#hboxContainer").tryQuery().isPresent()){
            FxAssert.verifyThat("#hboxContainer", Node::isVisible);
        }

        //Comprueba el slider y sus componentes
        if(robot.lookup("#chatSlider").tryQuery().isPresent()){
            robot.clickOn("#chatSlider");
            //Comprueba el upload
            robot.clickOn("#uploadButton");
            if(robot.lookup("#buttonTryUpload").tryQuery().isPresent()){
                FxAssert.verifyThat("#buttonTryUpload", Node::isVisible);
            }
            //Comprueba el btn de settings
            robot.clickOn("#settingsButton");
            if(robot.lookup("#changepdw").tryQuery().isPresent()){
                FxAssert.verifyThat("#changepdw", Node::isVisible);
            }
            //Comprueba el btn de logout
            robot.clickOn("#tileSettings3");
            if(robot.lookup("#btnIniciarSesion").tryQuery().isPresent()){
                FxAssert.verifyThat("#btnIniciarSesion", Node::isVisible);
            }
        }

        robot.sleep(2000);
    }

    @Test
    void check_home_items_and_player(FxRobot robot) throws InterruptedException {
        //Pillar los items por la clase de css que se añadio desde home con el id de la cancion
        //Hacemos que el robot inicie sesión
        robot.clickOn("#textFieldUsuario");
        robot.write("test");
        robot.clickOn("#textFieldContrasena");
        robot.write("test");
        robot.clickOn("#btnIniciarSesion");

        robot.sleep(2000);
        FxAssert.verifyThat(".20", Node::isVisible);
    }

}