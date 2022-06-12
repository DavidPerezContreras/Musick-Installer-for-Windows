package org.iesmurgi.proyectolevidaviddam;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Controllers.HelloController;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage mainStage;
    public static boolean session_started = false;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        mainStage = stage;
        stage.setOnCloseRequest(event -> {
            Requester<String> set_online = null;
            if(session_started){
                try {
                    System.out.println("Cerrando sesión ...");
                    set_online = new Requester<>("http://tux.iesmurgi.org:11230/set-online", Requester.Method.POST, String.class);
                    set_online.addParam("token", HelloController.log_out_token);
                    set_online.addParam("online", "false");
                    set_online.addParam("username", HelloController.log_out_username);
                    set_online.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.exit(0);
            Platform.exit();
        });

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("log_in.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MuSick");
        stage.setMaximized(true);
        stage.setMinWidth(1200);
        stage.setMinHeight(850);
        stage.setScene(scene);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.show();
        Thread.sleep(500);
        stage.toFront();


        ///
        /// Endpoint /users request
        ///
/*
        // Create a neat value object to hold the URL
        URL url = new URL("http://10.147.20.65:3000/users");

        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestMethod("POST");
        // This line makes the request
        InputStream responseStream = connection.getInputStream();
        Gson gson = new Gson();


        BufferedReader responseBR=new BufferedReader(new InputStreamReader(responseStream));
        String usersJSON="";
        int character=0;
        while((character=responseBR.read())!=-1){
            usersJSON+=(char)character;
        }
        System.out.println(usersJSON);
        User[] users=gson.fromJson(usersJSON,User[].class);

        for (User user:users) {
            System.out.println(user.toString());
        }


        Requester<User[]> requester=new Requester<>("http://10.147.20.65:3000/users", Requester.Method.POST,User[].class);
        User [] users =requester.execute();
        for(User user:users){
            System.out.println(user.toString());
        }
*/

        /*
        Requester<User[]> requester=new Requester<>("http://10.147.20.65:3000/", Requester.Method.POST,User[].class);
        requester.addParam("username","lewiscalewis");
        User[] users =requester.execute();
        for(User user:users){
            System.out.println(user.toString());
        }*/


        //STRING REQUESTER PARA LEVI
/*
        Requester<String> stringRequester=new Requester<>(
                "http://tux.iesmurgi.org:11230",
                Requester.Method.POST,
                String.class
        );
        //stringRequester.addParam("username","lewiscalewis");
        String[] stringRespuesta=new String[]{
                stringRequester.execute()
        };
        System.out.println(stringRespuesta[0]);
*/

        ////////////////////////////////////////////////////////////////////////////////
    }


    public static void main(String[] args) {
        launch();
    }
}