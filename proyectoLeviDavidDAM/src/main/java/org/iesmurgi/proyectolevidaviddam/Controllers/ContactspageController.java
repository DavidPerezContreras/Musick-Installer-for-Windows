package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.FileGetter;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.FriendRequest;
import org.iesmurgi.proyectolevidaviddam.models.Notifications;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContactspageController {

    @FXML
    private StackPane baseRoot;

    @FXML
    private FlowPane container;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField textfieldBrowser;

    @FXML
    private HBox hboxContainer;

    TokenManager tk = new TokenManager();

    GeneralDecoder d = new GeneralDecoder();
    String me = d.getUserFromToken();

    public ContactspageController() throws IOException {
    }

    @FXML
    void initialize() throws IOException, InterruptedException {
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setBottomAnchor(baseRoot,0.0);

        baseRoot.setTranslateZ(-10);
        baseRoot.setAlignment(Pos.CENTER);


        container.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        hboxContainer.setSpacing(50);

        loadUsers();

        loadPetitions();

    }

    void loadPetitions() throws IOException {
        Platform.runLater(()->{
            try {
                FriendRequest[] petitions;
                String url = CONSTANT.URL.getUrl()+"/get-friend-requests";
                Requester<FriendRequest[]> r = new Requester(url, Requester.Method.POST, FriendRequest[].class);
                r.addParam("username", me);
                r.addParam("token", tk.getToken());
                petitions = r.execute();

                VBox petitions_box = new VBox();

                Arrays.stream(petitions).forEach(u->{
                    Button accept = new Button("Aceptar");
                    accept.getStyleClass().add("button-default");
                    Button decline = new Button("Rechazar");
                    decline.getStyleClass().add("decline-button");

                    HBox buttons_container = new HBox();

                    buttons_container.getChildren().addAll(accept, decline);
                    buttons_container.setSpacing(8);
                    buttons_container.setAlignment(Pos.CENTER);

                    Label petition = new Label("Petición de amistad de "+u.getEmisor());
                    petition.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 300; -fx-alignment: center");

                    VBox petitions_container = new VBox();
                    petitions_container.getChildren().addAll(petition, buttons_container);
                    petitions_container.setSpacing(5);
                    petitions_container.setMaxHeight(60);

                    petitions_box.getChildren().add(petitions_container);
                    petitions_box.setSpacing(15);
                    petitions_box.setStyle("" +
                            "-fx-border-color: white; " +
                            "-fx-alignment: top;" +
                            "-fx-max-width: 400");
                    petitions_box.setAlignment(Pos.CENTER);

                    accept.setOnAction(event -> {
                        String accept_url = CONSTANT.URL.getUrl()+"/add-friend";
                        try {
                            Requester<FriendRequest[]> accept_action = new Requester(accept_url, Requester.Method.POST, FriendRequest[].class);
                            accept_action.addParam("username1", u.getReceptor());
                            accept_action.addParam("username2", u.getEmisor());
                            accept_action.addParam("token", tk.getToken());
                            accept_action.execute();

                            decline_action_event(u, petitions_container);

                            petitions_container.getChildren().clear();
                            reload();
                        }catch (Exception exc){
                            exc.printStackTrace();
                        }
                    });

                    decline.setOnAction(event -> {
                        decline_action_event(u, petitions_container);
                    });

                    hboxContainer.getChildren().add(petitions_box);

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void decline_action_event(FriendRequest u, VBox petitions_container) {
        try {
            String decline_url = CONSTANT.URL.getUrl()+"/decline-request";
            Requester<FriendRequest[]> decline_action = new Requester(decline_url, Requester.Method.POST, FriendRequest[].class);
            decline_action.addParam("emisor", u.getEmisor());
            decline_action.addParam("receptor", u.getReceptor());
            decline_action.addParam("token", tk.getToken());
            decline_action.execute();
            petitions_container.getChildren().clear();
            reload();
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }

    void reload(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("contactspage.fxml"));
        baseRoot.getChildren().clear();
        Pane root = null;

        try {
            root = (fxmlLoader.load());
            baseRoot.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addFriend(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-friend-view.fxml"));
        baseRoot.getChildren().clear();
        Pane root = null;
        VBox pageRoot = (VBox) baseRoot.getParent();
        pageRoot.setAlignment(Pos.CENTER);

        try {
            root = (fxmlLoader.load());
            baseRoot.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    synchronized void filterByName(KeyEvent event) {
        Platform.runLater(()->{
            try {
                container.getChildren().clear();
                loadUsersBySearch();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    synchronized void search(ActionEvent event) {
        Platform.runLater(()->{
            try {
                container.getChildren().clear();
                loadUsersBySearch();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    private synchronized void loadUsersBySearch() throws IOException, InterruptedException {
        container.getChildren().clear();
        if(!textfieldBrowser.getText().equals("")){

            String url = CONSTANT.URL.getUrl()+"/get-contacts-filter";
            Requester<User[]> r = new Requester(url, Requester.Method.POST, User[].class);
            r.addParam("username", me);
            r.addParam("friend", textfieldBrowser.getText());
            r.addParam("token", tk.getToken());
            User[] users;
            users = r.execute();

            iterateUsers(users);
        }else {
            loadUsers();
        }
    }

    private void iterateUsers(User[] users) throws IOException {
        Platform.runLater(()->{
            container.getChildren().clear();
            Arrays.stream(users).filter(user -> !Objects.equals(user.getUsername(), new GeneralDecoder().getUserFromToken())).forEach((u)->{
                String url1 = CONSTANT.URL.getUrl()+"/download-image";
                FileGetter fileGetter = null;
                try {
                    fileGetter = new FileGetter(url1);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                fileGetter.addParam("username", u.getUsername());
                fileGetter.addParam("token", tk.getToken());

                System.out.println(u.toString());
                VBox userCard = new VBox();
                userCard.setAlignment(Pos.CENTER);
                userCard.maxWidth(300);
                userCard.prefWidth(300);
                userCard.maxHeight(300);
                userCard.getStyleClass().add("userCard2");
                //.setStyle("" +
//                        "-fx-border-color: white; " +
//                        "-fx-border-radius: 5; " +
//                        "-fx-background-radius: 5; " +
//                        "-fx-border-width: 2; " +
//                        "-fx-background-color: white;" +
//                        "-fx-padding: 20");
                ImageView imgView = null;
                try {
                    imgView = fileGetter.getImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgView.setFitHeight(60);
                imgView.setFitWidth(60);
                Label usernameLabel = new Label("Usuario: "+u.getUsername());
                usernameLabel.setStyle("" +
                        "-fx-text-fill: black; " +
                        "-fx-fill: black; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 15;");
                Text nameLabel = new Text("Nombre: "+u.getName());
                Text surnameLabel = new Text("Apellidos: "+u.getSurname());
                Text state = new Text("Estado: "+u.getState());
                Text online = new Text(u.isOnline() == 1 ? "En línea" : "Desconectado");
                online.setStyle(u.isOnline() == 1 ? "-fx-fill: lightgreen; -fx-font-weight: bold" : "-fx-fill: red; -fx-font-weight: bold");
                Requester<Notifications[]> req = null;
                try {
                    req = new Requester<>("http://tux.iesmurgi.org:11230/get-notifications", Requester.Method.POST, Notifications[].class);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                req.addParam("token", new TokenManager().getToken());
                req.addParam("emisor", u.getUsername());
                req.addParam("receptor", me);
                Notifications[] notifications = new Notifications[0];
                try {
                    notifications = req.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Text not_readed;
                if(notifications.length > 0){
                    not_readed = new Text(notifications[0].getNotification()+" mensajes sin leer");
                    not_readed.setStyle("-fx-fill: red; -fx-font-weight: bold");
                    userCard.getChildren().addAll(imgView, usernameLabel, nameLabel, surnameLabel, state, online, not_readed);
                }else{
                    userCard.getChildren().addAll(imgView, usernameLabel, nameLabel, surnameLabel, state, online);
                }
                userCard.setSpacing(5);
                userCard.setPadding(new Insets(5, 5, 5, 5));
                container.getChildren().add(userCard);
                container.setAlignment(Pos.CENTER);
                container.setPadding(new Insets(10,10,10,10));
                openChat(userCard, u);
            });
        });
    }

    void loadUsers() throws IOException, InterruptedException {
        GeneralDecoder d = new GeneralDecoder();
        String me = d.getUserFromToken();

        container.getChildren().clear();

        String url = CONSTANT.URL.getUrl()+"/get-contacts";
        Requester<User[]> r = new Requester(url, Requester.Method.POST, User[].class);
        r.addParam("username", me);
        r.addParam("token", tk.getToken());
        User[] users;
        users = r.execute();

        System.out.println("Ejecutando get contacts: ");
        iterateUsers(users);

    }

    void openChat(VBox container, User contact){
        container.setOnMouseClicked((event)->{
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(baseRoot);
                baseRoot.setAlignment(Pos.CENTER);
                //((HBox) event.getTarget()).setTranslateY(-6);


                slide.setToX(6000);
                slide.play();
                slide.setOnFinished((ev -> {

                baseRoot.setTranslateX(-6000);
                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(0.4));
                slide2.setNode(baseRoot);
                //((HBox) event.getTarget()).setTranslateY(-6);


                slide2.setToX(0);

                try {
                    baseRoot.setAlignment(Pos.CENTER);
                    baseRoot.getChildren().clear();
                    FXMLLoader rootFxmlLoader=new FXMLLoader(
                            HelloApplication.class.getResource(
                                    "chat-view.fxml"
                            )
                    );
                    VBox pageRoot = (VBox) baseRoot.getParent();
                    pageRoot.setAlignment(Pos.CENTER);
                    Pane root = rootFxmlLoader.load();
                    baseRoot.getChildren().add(root);

                    ChatController controlador = rootFxmlLoader.getController();
                    controlador.setContactData(contact);


                    //((Stage)root.getScene().getWindow()).setMinWidth(900);
                    // ((Stage)root.getScene().getWindow()).setMinHeight(850);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                slide2.play();
                slide2.setOnFinished((event2)->{
                });
            }));
        });
    }

}
