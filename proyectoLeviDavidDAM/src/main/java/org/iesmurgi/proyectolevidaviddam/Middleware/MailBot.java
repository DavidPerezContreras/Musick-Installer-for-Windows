package org.iesmurgi.proyectolevidaviddam.Middleware;

import javafx.scene.control.Alert;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;

public class MailBot {
    final String username = "javafxuserDAM@gmail.com";
    final String password = "mocednmnpuxlqlyr";

    public void sendMail(String mail){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        javax.mail.Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(mail)
            );
            message.setSubject("Password recovery");
            /*Aquí vamos a enviar una petición para actualizar la contraseña a una aleatoria que se enviará por email
             * una vez que el usuario abra su email, podrá iniciar sesión con la contraseña proporcionada y posteriormente
             * cambiarla desde su perfil*/
            Date date = new Date();
            long time = date.getTime();
            String newPassword = mail+time;
            GeneralDecoder gd = new GeneralDecoder();

            Requester<String> r = new Requester<>(CONSTANT.URL.getUrl()+"/reset-password", Requester.Method.POST, String.class);
            r.addParam("email", mail);
            r.addParam("password", gd.encodeMD5(newPassword));
            r.execute();

            message.setText("A continuación se le proporcionará una contraseña provisional para iniciar sesión, por favor sustitúyala por una nueva cuando inicie sesión, desde sus ajustes de usuario.\n" +
                    "La contraseña es: "+newPassword);
            Transport.send(message);

            System.out.println("Done");
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setTitle("Correo enviado!");
            a.setContentText("Se le ha enviado un correo con las instrucciones");
            a.show();
        } catch (MessagingException | IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            /*Mostrar un dialog de error en la petición!!!*/
        }
    }
}