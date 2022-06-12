package org.iesmurgi.proyectolevidaviddam.models;

public class FriendRequest {

    int id;
    String emisor, receptor, state;

    public FriendRequest(int id, String emisor, String receptor, String state){
        this.id = id;
        this.emisor = emisor;
        this.receptor = receptor;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getEmisor() {
        return emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
