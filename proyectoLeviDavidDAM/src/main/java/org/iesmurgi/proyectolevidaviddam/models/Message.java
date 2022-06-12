package org.iesmurgi.proyectolevidaviddam.models;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message  {

    String body;
    String emisor;
    String receptor;
    Date datetime;
    String date;

    public Message(){

    }

    public Message(String body, String emisor, String receptor, String date){
        this.body = body;
        this.emisor = emisor;
        this.receptor = receptor;
        this.date = date;
    }

    public void setEmisor(String username) {
        this.emisor = username;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getReceptor() {
        return receptor;
    }

    public String getEmisor() {
        return emisor;
    }

    public String getMessage() {
        return body;
    }

    public void setMessage(String message) {
        this.body = message;
    }

    public String getDatetime() {
        return date;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString(){
        return  "{"
                +"\"body\":\""+ body+ "\","
                +"\"emisor\":\""+ emisor + "\","
                +"\"receptor\":\""+ receptor + "\","
                +"\"date\":\""+ date + "\""
                +"}";
    }

//    private String parseDate() {
//        String formatedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(datetime);
//        return formatedDate;
//    }
}
