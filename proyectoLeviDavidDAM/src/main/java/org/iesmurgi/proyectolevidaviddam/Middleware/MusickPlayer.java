package org.iesmurgi.proyectolevidaviddam.Middleware;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URL;

public class MusickPlayer {

    private Media media;
    private MediaPlayer player;
    private String url;
    private MediaView mediaView;

    public MusickPlayer(String url_mp3){
        this.url = url_mp3;
        this.media = new Media(url);
        this.player = new MediaPlayer(media);
        this.player.setAutoPlay(true);
    }

    public MusickPlayer(){

    }

    public Node getControl(){
        MediaControl mediaControl = new MediaControl(this.player);
        mediaControl.setStyle("-fx-background-color:  #1b1b1b; -fx-max-width: 600; -fx-min-width: 400; -fx-min-height: 80");
        player.setVolume(0.55);
        player.play();
        return mediaControl;
    }

    public void setPlayer(String url){
        this.url = url;
        this.media = new Media(url);
        this.player = new MediaPlayer(media);
        this.player.setAutoPlay(true);
        player.stop();
    }

    public void stop_music(){
        player.stop();
    }

}
