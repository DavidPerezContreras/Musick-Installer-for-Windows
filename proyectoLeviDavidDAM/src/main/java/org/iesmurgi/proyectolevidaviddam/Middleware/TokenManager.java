package org.iesmurgi.proyectolevidaviddam.Middleware;

import java.io.*;

public class TokenManager {

    private String token;

    public String getToken() {
        String t = "";
        try{
            BufferedReader r = new BufferedReader(new FileReader("src/main/java/org/iesmurgi/proyectolevidaviddam/LocalStorage/UserData.txt"));
            String l = "";
            while ((l = r.readLine()) != null){
                t = l;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        token = t;

        return token;
    }

    public void tokenStorage(String token) {
        try{
            BufferedWriter w = new BufferedWriter(new FileWriter("src/main/java/org/iesmurgi/proyectolevidaviddam/LocalStorage/UserData.txt"));
            w.write(token);
            w.flush();
            w.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deleteToken() throws IOException {
        BufferedWriter w = new BufferedWriter(new FileWriter("src/main/java/org/iesmurgi/proyectolevidaviddam/LocalStorage/UserData.txt", false));
        w.write("");
        w.flush();
        w.close();
    }
}
