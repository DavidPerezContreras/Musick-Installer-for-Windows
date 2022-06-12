package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.FileGetter;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HelloController {


    @FXML
    private VBox pageRoot;
    @FXML
    private Hyperlink hyperlinkUser;                //HyperLink que se encuentra arriba a la derecha junto a la imagen del usuario
    @FXML
    private VBox chatSlider;
    @FXML
    private VBox mainContainer;
    @FXML
    private ImageView imageviewProfileImage;
    @FXML
    private HBox hboxTopMenu;
    @FXML
    private VBox vboxPlayer;
    @FXML
    private HBox settingsButton;
    @FXML
    private HBox profileButton;
    @FXML
    private HBox topProfileButton;
    @FXML
    private HBox uploadButton;
    @FXML
    private Label labelSongNamePlayer;
    @FXML
    private Hyperlink hyperlinkUsernamePlayer;
    @FXML
    private ImageView imageviewPlayer;

    public static String log_out_username;
    public static String log_out_token = new TokenManager().getToken();
    @FXML
    private Label labelTopMenu1;
    @FXML
    private Label labelTopMenu3;
    @FXML
    private Label labelTopMenu2;
    @FXML
    private StackPane baseRoot;
    @FXML
    private ColumnConstraints columnConstraints3;
    @FXML
    private Label tileSettings;
    @FXML
    private Label tileSettings3;
    @FXML
    private GridPane contentRoot;
    @FXML
    private Label tileSettings2;
    @FXML
    private Label tileSettings1;
    @FXML
    private Label tileSettings4;
    @FXML
    private HBox aboutButton;

    public void initialize() throws IOException {

        log_out_username = new GeneralDecoder().getUserFromToken();
        HelloApplication.session_started = true;
        chatSlider.setTranslateX(265);

        webView=new WebView();
        webEngine=webView.getEngine();

        uploadButton.setOnMouseClicked(Event->{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide.setToX(6000);
            slide.play();
            slide.setOnFinished((event -> {

                pageRoot.setTranslateX(-6000);
                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(0.4));
                slide2.setNode(pageRoot);
                //((HBox) event.getTarget()).setTranslateY(-6);


                slide2.setToX(0);

                try {
                    pageRoot.setAlignment(Pos.TOP_LEFT);
                    pageRoot.getChildren().clear();
                    rootFxmlLoader=new FXMLLoader(
                            HelloApplication.class.getResource(
                                    "uploadpage.fxml"
                            )
                    );
                    Pane root = rootFxmlLoader.load();
                    pageRoot.getChildren().add(root);




                    //((Stage)root.getScene().getWindow()).setMinWidth(900);
                    //((Stage)root.getScene().getWindow()).setMinHeight(850);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                slide2.play();
                slide2.setOnFinished((event2)->{
                    //UploadpageController uploadpageController= rootFxmlLoader.getController();
                    //uploadpageController.openFileChooser();
                });

            }));
        });

        topProfileButton.setOnMouseClicked(Event->{
            loadProfile(new GeneralDecoder().getUserFromToken());
        });

        profileButton.setOnMouseClicked(Event->{
            loadProfile(new GeneralDecoder().getUserFromToken());
        });

        settingsButton.setOnMouseClicked(Event ->{

            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide.setToX(6000);
            slide.play();
            slide.setOnFinished((event -> {

                pageRoot.setTranslateX(-6000);
                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(0.4));
                slide2.setNode(pageRoot);
                //((HBox) event.getTarget()).setTranslateY(-6);


                slide2.setToX(0);

                try {
                    pageRoot.setAlignment(Pos.TOP_CENTER);
                    pageRoot.getChildren().clear();
                    FXMLLoader rootFxmlLoader=new FXMLLoader(
                            HelloApplication.class.getResource(
                                    "settings-view.fxml"
                            )
                    );

                    Pane root = rootFxmlLoader.load();
                    SettingsController sc = rootFxmlLoader.getController();
                    sc.loadImageView(this);

                    pageRoot.getChildren().add(root);

                    //ProfilepageController profilepageController =rootFxmlLoader.getController();
                    //profilepageController.loadUserData();
                    //((Stage)root.getScene().getWindow()).setMinWidth(900);
                    //((Stage)root.getScene().getWindow()).setMinHeight(850);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                slide2.play();
                slide2.setOnFinished((event2)->{

                });

            }));
        });


        aboutButton.setOnMouseClicked(Event ->{

            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide.setToX(6000);
            slide.play();
            slide.setOnFinished((event -> {

                pageRoot.setTranslateX(-6000);
                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(0.4));
                slide2.setNode(pageRoot);
                //((HBox) event.getTarget()).setTranslateY(-6);


                slide2.setToX(0);

                try {
                    pageRoot.setAlignment(Pos.TOP_CENTER);
                    pageRoot.getChildren().clear();
                    FXMLLoader rootFxmlLoader=new FXMLLoader(
                            HelloApplication.class.getResource(
                                    "about-view.fxml"
                            )
                    );

                    Pane root = rootFxmlLoader.load();
                    AboutController sc = rootFxmlLoader.getController();


                    pageRoot.getChildren().add(root);

                    //ProfilepageController profilepageController =rootFxmlLoader.getController();
                    //profilepageController.loadUserData();
                    //((Stage)root.getScene().getWindow()).setMinWidth(900);
                    //((Stage)root.getScene().getWindow()).setMinHeight(850);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                slide2.play();
                slide2.setOnFinished((event2)->{

                });

            }));
        });


    }

    private void loadProfile(String username){

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pageRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {

            pageRoot.setTranslateX(-6000);
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide2.setToX(0);

            try {
                pageRoot.setAlignment(Pos.TOP_LEFT);
                pageRoot.getChildren().clear();
                FXMLLoader rootFxmlLoader=new FXMLLoader(
                        HelloApplication.class.getResource(
                                "profilepage.fxml"
                        )
                );
                Pane root = rootFxmlLoader.load();
                ProfilepageController profilepageController = rootFxmlLoader.getController();
                profilepageController.loadUserData(username);
                pageRoot.getChildren().add(root);

                //ProfilepageController profilepageController =rootFxmlLoader.getController();
                //profilepageController.loadUserData();
                //((Stage)root.getScene().getWindow()).setMinWidth(1000);
                //((Stage)root.getScene().getWindow()).setMinHeight(850);

            } catch (IOException e) {
                e.printStackTrace();
            }

            slide2.play();
            slide2.setOnFinished((event2)->{

            });

        }));
    }

    private void loadProfile(){

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pageRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {

            pageRoot.setTranslateX(-6000);
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide2.setToX(0);

            try {
                pageRoot.setAlignment(Pos.TOP_LEFT);
                pageRoot.getChildren().clear();
                FXMLLoader rootFxmlLoader=new FXMLLoader(
                        HelloApplication.class.getResource(
                                "profilepage.fxml"
                        )
                );
                Pane root = rootFxmlLoader.load();

                pageRoot.getChildren().add(root);

                //ProfilepageController profilepageController =rootFxmlLoader.getController();
                //profilepageController.loadUserData();
                //((Stage)root.getScene().getWindow()).setMinWidth(1000);
                //((Stage)root.getScene().getWindow()).setMinHeight(850);

            } catch (IOException e) {
                e.printStackTrace();
            }

            slide2.play();
            slide2.setOnFinished((event2)->{

            });

        }));
    }

    public void loadUserData(User user) throws IOException {
        hyperlinkUser.setText(user.getName());
        imageviewProfileImage.setImage(new Image(requestProfileImage(new GeneralDecoder().getUserFromToken())));
        //imageviewProfileImage.setFitWidth(55);
        //imageviewProfileImage.setFitHeight(55);
        //imageviewProfileImage.maxWidth(55);
        //imageviewProfileImage.maxHeight(55);
        //imageviewProfileImage.setPreserveRatio(false);
        hyperlinkUser.setOnAction(event->{
            loadProfile(user.getUsername());
        });

    }
    boolean chatOpen=true;


    InputStream requestProfileImage(String username) throws IOException {
        String URL= "http://tux.iesmurgi.org:11230/download-image";
        java.net.URL server = new java.net.URL(URL);
        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) server.openConnection();




        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/x-www-form-urlencoded");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        Map<String,String> params= new HashMap<>();
        params.put("username", new GeneralDecoder().getUserFromToken());                  //La petición no llega al servidor cuando le pongo parámetros
        params.put("token", new TokenManager().getToken());
        //ADD PARAMETERS
        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());

            String urlParameters  = (i > 0 ? "&" : "") + entry.getKey()+"="+entry.getValue();
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            System.out.println("Escribiendo parámetros: key -> "+entry.getKey()+"|| value -> "+entry.getValue());
            connection.getOutputStream().write(postData, 0, postDataLength);
            i++;
        }





        InputStream responseStream= connection.getInputStream();

        return responseStream;


    }



    //Hace una petición y obtiene una imagen. NO MODIFICAR. ES DE EJEMPLO.
    InputStream requestBinary() throws IOException {

        String URL= "http://tux.iesmurgi.org:11230/download-image";
        java.net.URL server = new java.net.URL(URL);
        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) server.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/x-www-form-urlencoded");
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        InputStream responseStream = connection.getInputStream();


        return responseStream;
    }





    boolean first=true;
    WebView webView;
    WebEngine webEngine;
    @FXML
    public void loadHomePage() throws IOException {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pageRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {

            pageRoot.setTranslateX(-6000);
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide2.setToX(0);

            try {
                pageRoot.setAlignment(Pos.TOP_LEFT);
                pageRoot.getChildren().clear();
                FXMLLoader rootFxmlLoader=new FXMLLoader(
                        HelloApplication.class.getResource(
                                "homepage.fxml"
                        )
                );
                Pane root = rootFxmlLoader.load();
                pageRoot.getChildren().add(root);

                if(first){
                    vboxPlayer.setAlignment(Pos.CENTER);

                    HomepageController homepageController= rootFxmlLoader.getController();
                    homepageController.testHomepageController();
                    homepageController.setVboxPlayer(vboxPlayer);
                    homepageController.setItemsFromFXML(labelSongNamePlayer, hyperlinkUsernamePlayer, imageviewPlayer);

                    first=false;}
                //((Stage)root.getScene().getWindow()).setMinWidth(1000);
                //((Stage)root.getScene().getWindow()).setMinHeight(850);

            } catch (IOException e) {
                e.printStackTrace();
            }


            slide2.play();
            slide2.setOnFinished((event2)->{

            });

        }));

    }



    public void firstLoadHomePage()  {


        try {
            pageRoot.setAlignment(Pos.TOP_LEFT);
            pageRoot.getChildren().clear();
            FXMLLoader rootFxmlLoader=new FXMLLoader(
                    HelloApplication.class.getResource(
                            "homepage.fxml"
                    )
            );
            Pane root = rootFxmlLoader.load();
            pageRoot.getChildren().add(root);


            //((Stage)pageRoot.getScene().getWindow()).setMinWidth(1000);
            //((Stage)pageRoot.getScene().getWindow()).setMinHeight(850);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    FXMLLoader rootFxmlLoader;

    @FXML
    public void logout(Event e) throws IOException {
        try{
            HomepageController.player.stop_music();
        }catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        Requester<String> set_online = null;
        try {
            set_online = new Requester<>("http://tux.iesmurgi.org:11230/set-online", Requester.Method.POST, String.class);
            set_online.addParam("token", new TokenManager().getToken());
            set_online.addParam("online", "false");
            set_online.addParam("username", new GeneralDecoder().getUserFromToken());
            set_online.execute();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        webEngine.load(null);
        mainContainer.getChildren().clear();

        TokenManager tk = new TokenManager();
        tk.deleteToken();
        try {
            mainContainer.setAlignment(Pos.CENTER);



            //ANIMACION DE TRANSICION DE VENTANA
            Platform.runLater(()->{
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(1));
                slide.setNode(pageRoot);
                //((HBox) event.getTarget()).setTranslateY(-6);


                slide.setToY(-5000);

                slide.setOnFinished((event)->{
                    mainContainer.getChildren().clear();
                    FXMLLoader rootFxmlLoader=new FXMLLoader(
                            HelloApplication.class.getResource(
                                    "log_in.fxml"
                            )
                    );
                    Pane root = null;
                    try {
                        root = rootFxmlLoader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    mainContainer.getChildren().add(root);
                    pageRoot.setTranslateY(0);
                });
                slide.play();
                hboxTopMenu.setVisible(false);



            });

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @FXML
    public void loadContactsPage() {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pageRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {

            pageRoot.setTranslateX(-6000);
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide2.setToX(0);

            try {
                pageRoot.setAlignment(Pos.TOP_LEFT);
                pageRoot.getChildren().clear();
                FXMLLoader rootFxmlLoader=new FXMLLoader(
                        HelloApplication.class.getResource(
                                "contactspage.fxml"
                        )
                );
                Pane root = rootFxmlLoader.load();
                pageRoot.getChildren().add(root);


//                ((Stage)root.getScene().getWindow()).setMinWidth(1000);
//                ((Stage)root.getScene().getWindow()).setMinHeight(850);

            } catch (IOException e) {
                e.printStackTrace();
            }
            slide2.play();
            slide2.setOnFinished((event2)->{
            });
        }));
    }

    private void animateTransition() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.8));
        slide.setNode(pageRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {
            pageRoot.setTranslateX(0);
        }));
    }

    @FXML
    public void slideChatSlider(Event event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode((VBox) event.getTarget());
        //((HBox) event.getTarget()).setTranslateY(-6);
        slide.setToX(10);
        slide.play();
    }

    @FXML
    public void slideChatSliderExited(Event event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode((VBox) event.getTarget());
        slide.setToX(265);
        slide.play();
    }

    @FXML
    public void onSlideHoverExited(Event event) {
        ((HBox) event.getTarget()).setStyle("-fx-background-color:#1c3787; ");
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.1));
        slide.setNode((HBox) event.getTarget());
        slide.setToX(0);
        slide.setToY(0);
        slide.play();


    }

    @FXML
    public void onSlideHoverEnter(Event event) {
        ((HBox) event.getTarget()).setStyle("-fx-background-color:#4436cc; ");

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.1));
        slide.setNode((HBox) event.getTarget());

        slide.setToX(-10);
        slide.setToY(-2);
        slide.play();

        //((HBox) event.getTarget()).setTranslateY(-6);
    }

    @FXML
    public void onMenuItemEnter(Event event) {
        ((HBox) event.getTarget()).setStyle("-fx-background-color:#4436cc; ");



        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.1));
        slide.setNode((HBox) event.getTarget());

        slide.setToY(0);
        //slide.setToX(2);
        slide.play();

    }

    @FXML
    public void onMenuItemExited(Event event) {
        ((HBox) event.getTarget()).setStyle("-fx-background-color: #1c3787;");
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.1));
        slide.setNode((HBox) event.getTarget());
        //((HBox) event.getTarget()).setTranslateY(-6);
        slide.setToY(0);
        //slide.setToX(0);
        slide.play();


    }

    @FXML
    public void onSliderPressed(Event event) {/*
        System.out.println(event.getSource().getClass().getName());

        if(event.getSource().getClass()==HBox.class){
            ((HBox) event.getTarget()).setStyle("-fx-background-color:#4046c1; ");
        }else {
            ((Node) event.getTarget()).getParent().getParent().setStyle("-fx-background-color:#4046c1; ");
            event.consume();
        }*/
    }

    @FXML
    public void onSliderReleased(Event event) {/*

        if(event.getSource().getClass()==HBox.class){
            ((HBox) event.getTarget()).setStyle("-fx-background-color:#4436cc; ");
        }else {
            ((Node) event.getTarget()).getParent().getParent().setStyle("-fx-background-color:#4436cc; ");
            event.consume();
        }*/
    }


    @Deprecated
    public void loadProfilePage(Event event) {
    }
}