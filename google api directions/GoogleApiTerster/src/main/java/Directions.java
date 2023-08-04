import java.io.*;
import java.net.*;
import com.google.gson.*;

/**
 * GoogleApiTerster : Directions
 *
 * @author Jens Desmyter
 * @version 11/05/2023
 */
public class Directions {
    public Directions() throws MalformedURLException {
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String origin = "Bargiekaai+7+9000+Gent";
        String destination = "Warestraat+2a+Diksmuide";
        String key = "AIzaSyBmVYbOKk_WQ6qaUnYJk-Zp5Y7mOtF45h4";
        String mode = "transit"; // transit , walking, driving,
        String urlString = "https://maps.googleapis.com/maps/api/directions/json?origin="+ origin +"&destination=" + destination + "&mode=" + mode +"&key=" + key;

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        InputStream inputStream = con.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder responseBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        String response = responseBuilder.toString();

        System.out.println(response);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(response);
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
    }
}
