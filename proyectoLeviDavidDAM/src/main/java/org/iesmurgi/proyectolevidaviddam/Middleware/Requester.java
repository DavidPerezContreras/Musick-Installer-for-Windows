package org.iesmurgi.proyectolevidaviddam.Middleware;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Requester <T>{
    final Class<T> typeParameterClass;

    T result;
    URL URL;
    String method;
    Map<String,String> params;

    public enum Method{
        POST,
        GET
    }

    public Requester(String URL, Method method, Class<T> typeParameterClass) throws MalformedURLException {
        this.URL=new URL(URL);
        this.typeParameterClass = typeParameterClass;
        params= new HashMap<>();

        switch (method){
            case POST -> this.method="POST";
            case GET -> this.method="GET";
        }
    }


    Map<String,String> getParams(){
        return params;
    }
    public void addParam(String key, String value){
        params.put(key,value);
    }


    public synchronized T execute() throws IOException {


        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) this.URL.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/x-www-form-urlencoded");
        connection.setRequestMethod(this.method);
        connection.setDoOutput(true);

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


        // This line makes the request
        T vuelta;
        T res = (T)"";
        if (!connection.getHeaderField("Content-Length").equals("0") && typeParameterClass != null) {
            InputStream responseStream = connection.getInputStream();


            Gson gson = new Gson();


            BufferedReader responseBR = new BufferedReader(new InputStreamReader(responseStream));
            String response = "";
            String linea = "";
            while ((linea = responseBR.readLine()) != null) {
                response += linea;
            }
            //System.out.println(response);

            if (typeParameterClass == String.class) {
                vuelta = (T) response;
                System.out.println("Resultado: " + vuelta);
            } else {
                vuelta = gson.fromJson(response, typeParameterClass);
                System.out.println("Resultado: " + vuelta.toString());

            }

            return vuelta;
        } else {
            System.out.println("Resultado vacio" + res);
            return res;
        }
    }
}
