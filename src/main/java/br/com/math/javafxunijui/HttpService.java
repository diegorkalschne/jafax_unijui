package br.com.math.javafxunijui;

import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpService {

    private final String urlString;

    public HttpService(String urlString) throws MalformedURLException {
        this.urlString = urlString;
    }

    public JSONObject getJSONObject(String query) {
        try {
            URL url = new URL(urlString + query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                try (Scanner scanner = new Scanner(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                    while (scanner.hasNext()) {
                        informationString.append(scanner.nextLine());
                    }
                }

                JSONParser parse = new JSONParser();

                return (JSONObject) parse.parse(String.valueOf(informationString));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
