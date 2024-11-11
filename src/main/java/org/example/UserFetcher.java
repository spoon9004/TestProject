package org.example;

import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;

public class UserFetcher {
    public static JSONArray getUsers() throws Exception {
        String urlString = "https://randomuser.me/api?results=50";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
    }
}