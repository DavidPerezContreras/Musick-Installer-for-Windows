package org.iesmurgi.proyectolevidaviddam;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Controllers.SignUp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * By oussamaK
 */

@ExtendWith(ApplicationExtension.class)

class SignUpTest {
    VBox mainroot;
    Stage mainstage;

    @Start
    public void start(Stage stage) throws IOException {
        mainroot = FXMLLoader.load(getClass().getResource("sign_up.fxml"));
        mainstage = stage;
        stage.setScene(new Scene(mainroot, 800, 800));
        stage.show();
        stage.toFront();
    }


    @Test
    void labels_botonesExisten(FxRobot robot){

        FxAssert.verifyThat("#lblNombre1", LabeledMatchers.hasText("Nick:"));
        FxAssert.verifyThat("#lblNombre", LabeledMatchers.hasText("Nombre:"));
        FxAssert.verifyThat("#lblApellidos", LabeledMatchers.hasText("Apellidos:"));
        FxAssert.verifyThat("#lblCorreo", LabeledMatchers.hasText("Correo:"));
        FxAssert.verifyThat("#lblContraseña", LabeledMatchers.hasText("Contraseña:"));
        FxAssert.verifyThat("#lblRepetirContraseña", LabeledMatchers.hasText("Repetir contraseña:"));
        FxAssert.verifyThat("#textFieldNick", Node::isVisible);
        FxAssert.verifyThat("#textFieldNombre", Node::isVisible);
        FxAssert.verifyThat("#textFieldApellidos", Node::isVisible);
        FxAssert.verifyThat("#textFieldCorreo", Node::isVisible);
        FxAssert.verifyThat("#pwdContraseña", Node::isVisible);
        FxAssert.verifyThat("#pwdRepetirContraseña", Node::isVisible);
        FxAssert.verifyThat("#btnRegistrarse", Node::isVisible);

    }

    @Test
    void se_muestran_las_validaciones(FxRobot robot) throws InterruptedException {

        //Comprueba que cuando se pulse "crear cuenta" con campos vacios salte error
//        robot.clickOn("#btnRegistrarse").wait();
//        robot.sleep(5000);
//        FxAssert.verifyThat(robot.window("Campos vacios!"), WindowMatchers.isShowing());
//        robot.sleep(2000);
//        robot.press(KeyCode.ENTER);

        //Comprueba que se va a la ventana de login cuando le clickas
        robot.clickOn("#login");
        robot.sleep(2000);
        FxAssert.verifyThat("#btnRegistrarse", Node::isVisible);

    }




}