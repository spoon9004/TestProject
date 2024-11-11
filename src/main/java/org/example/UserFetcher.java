package org.example;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UserFetcher {
    public static JSONArray getUsers() throws Exception {
        String urlString = "https://randomuser.me/api?results=50";
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        StringBuilder response = new StringBuilder();
        while ((input = in.readLine()) != null) {
            response.append(input);
        }
        in.close();
        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getJSONArray("results");
    }

    public static void showUsersByTimezone(JSONArray users, String timezone) {
        ArrayList<String> usersInTimezone = new ArrayList<>();
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            String userTimezone = user.getJSONObject("location").getJSONObject("timezone").getString("offset");
            if (timezone == null || userTimezone.equals(timezone)) {
                String fullName = user.getJSONObject("name").getString("title") + " "
                        + user.getJSONObject("name").getString("first") + " "
                        + user.getJSONObject("name").getString("last");
                usersInTimezone.add(fullName);
            }
        }
        if (timezone != null) {
            System.out.println(usersInTimezone.size() + "/" + users.length() + " users in timezone " + timezone);
        }
        for (String user : usersInTimezone) {
            System.out.println(user);
        }
    }

    public static void main(String[] args) {
        try {
            String timezone = null;
            if (args.length > 1 && args[0].equals("-t")) {
                timezone = args[1];
            }
            JSONArray users = getUsers();
            showUsersByTimezone(users, timezone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}