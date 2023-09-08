package org.example;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        URL url = null;
        HttpURLConnection connection = null;
        int responseCode = 0;
        String urlString = "https://api.zippopotam.us/us/33162";


        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println("problem in URL");
        }

        //connection

        try {
            connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            System.out.println("connection problem");
        }

        //extract information from the connection object:

        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder apiData = new StringBuilder();
            String readLine = null;

            while ((readLine = in.readLine()) != null) {
                apiData.append(readLine);
            }

            //
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println(apiData.toString());
            JSONObject jsonAPIResponse = new JSONObject(apiData.toString());
            System.out.println("post code = " + jsonAPIResponse.get("post code"));
            System.out.println("country = "+jsonAPIResponse.get("country"));
            System.out.println("country abbreviation = "+jsonAPIResponse.get("country abbreviation"));


            JSONArray son = jsonAPIResponse.getJSONArray("places");
            for (Object s:son) {
                JSONObject j = new JSONObject(s.toString());
                System.out.println("place name  = " + j.get("place name"));
                System.out.println("longitude  = " + j.get("longitude"));
                System.out.println("state  = " + j.get("state"));
                System.out.println("state abbreviation  = " + j.get("state abbreviation"));
                System.out.println("latitude  = " + j.get("latitude"));

                System.out.println("-----------------");
            }
        } else
            System.out.println("API call could not be made!!!");


    }
}