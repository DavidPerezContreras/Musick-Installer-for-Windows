package org.iesmurgi.proyectolevidaviddam.Middleware;

import com.google.gson.Gson;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class GeneralDecoder {

    TokenManager tk = new TokenManager();
    String token = tk.getToken();

    public GeneralDecoder(){

    }

    public String encodeMD5(String plainText) throws NoSuchAlgorithmException {

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(plainText.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }

        return hashtext;
    }

    public String decodeToken() {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        return payload;
    }

    public String getUserFromToken(){
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        Gson gson = new Gson();
        User user = gson.fromJson(payload, User.class);

        return user.getUsername();
    }

/*
    public String decodeMD5(String hashText) throws NoSuchAlgorithmException {
        String plainText;


        return plainText;
    }
*/


}
