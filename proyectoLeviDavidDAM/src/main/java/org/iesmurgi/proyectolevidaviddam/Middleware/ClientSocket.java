package org.iesmurgi.proyectolevidaviddam.Middleware;

import com.google.gson.Gson;
import io.socket.client.IO;
import io.socket.client.Socket;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.iesmurgi.proyectolevidaviddam.Controllers.ChatController;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.models.Message;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;


public class ClientSocket {

    Socket socket;
    Socket socketReceiver;
    ArrayList<Message> messages = new ArrayList<>();
    Message[] message;
    String room;
    ChatController controller;
    User receptor;
    int contador_notificaciones = 0;

    public ClientSocket(String room){
        this.room = room;
    }

    public ClientSocket(){

    }

    public ClientSocket(ChatController controller){
        this.controller = controller;
    }

    public void load_receptor_data(User receptor){
        this.receptor = receptor;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void init() throws IOException, URISyntaxException {

        TokenManager tk = new TokenManager();
        GeneralDecoder gd = new GeneralDecoder();

        IO.Options options = IO.Options.builder()
                .setForceNew(false)
                .build();

        socketReceiver = IO.socket(URI.create(CONSTANT.SOCKET.getUrl()), options);
        socketReceiver.emit("start-room", room);

        socketReceiver.on ("message", objetos -> {
            //CONVERTIR TIPO CON GSON PARA PROBAR
            Gson gson = new Gson();
            System.out.println("MENSAJE RECIBIDO->" + Arrays.toString(objetos));
            message = gson.fromJson(Arrays.toString(objetos), Message[].class);
            System.out.println("El valor para message es: "+message[0]);
            setMessages(message[0]);

            if(!message[0].getEmisor().equals(gd.getUserFromToken())){
                controller.printMessages(message[0]);
                controller.chatContainer.layout();
                controller.scrollDown();
            }
        });
        socketReceiver.connect();

    }

    private void setMessages(Message message1){
        messages.add(message1);
    }

    public ArrayList<Message> getMessagesAsArray(){
        return messages;
    }

    public void sendMessage(Message message) throws IOException {
        IO.Options options = IO.Options.builder()
                //.setPath("/chat message")
                .setForceNew(false)
                .build();

        socket = IO.socket(URI.create(CONSTANT.SOCKET.getUrl()), options);
        socket.emit("message", message);

        socket.connect();
    }
}
