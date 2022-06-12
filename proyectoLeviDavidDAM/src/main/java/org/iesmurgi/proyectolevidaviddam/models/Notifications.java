package org.iesmurgi.proyectolevidaviddam.models;

public class Notifications {
    int id;
    String emisor;
    String receptor;
    String notification;

    public Notifications(int id, String emisor, String receptor, String notification){
        this.id = id;
        this.emisor = emisor;
        this.receptor = receptor;
        this.notification = notification;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getEmisor() {
        return emisor;
    }

    public int getId() {
        return id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

}
