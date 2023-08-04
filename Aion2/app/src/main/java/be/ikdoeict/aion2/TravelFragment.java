package be.ikdoeict.aion2;

import static android.content.ContentValues.TAG;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class TravelFragment extends Fragment {

    private LatLng origin;
    private LatLng destination;
    private String originString;
    private String destinationString;
    private String mode = "transit";
    private String apiKey = "AIzaSyBmVYbOKk_WQ6qaUnYJk-Zp5Y7mOtF45h4";

    private TableLayout tableLayout;

    public TravelFragment(String origin, String destination, String mode){
        this.originString = origin;
        this.destinationString = destination;
        if (mode != null)
            this.mode = mode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_travel, container, false);

        tableLayout = fragView.findViewById(R.id.table_layout);

        if (originString != null || destinationString != null) {
            origin = getLatLngFromAddress(originString);
            destination = getLatLngFromAddress(destinationString);
        }
        getResponseBody();


        return fragView;
    }

    private LatLng getLatLngFromAddress(String address) {
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void getResponseBody(){
        Thread thread = new Thread(() -> {
            // Create the request
            String responseBody = "";
            String urls = "https://maps.googleapis.com/maps/api/directions/json?";
            String originString = origin.latitude + "," + origin.longitude; // Replace with your origin coordinates
            String destinationString = destination.latitude + "," + destination.longitude; // Replace with your destination coordinates

            String requestUrl = urls +
                    "origin=" + originString +
                    "&destination=" + destinationString +
                    "&key=" + apiKey +
                    "&mode=" + mode;

            try {
                // Create the URL object
                URL url = new URL(requestUrl);

                // Open a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set the request method
                connection.setRequestMethod("GET");

                // Set any headers if needed
                // connection.setRequestProperty("headerKey", "headerValue");

                // Get the response code
                int responseCode = connection.getResponseCode();

                // Check if the request was successful
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response from the input stream
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();

                    // Process the response
                    responseBody = response.toString();
                    onPostExecute(responseBody);

                } else {
                    // Handle the unsuccessful response
                }
                Log.d(TAG, "Line Name: " + responseBody);
                System.out.println("");
                // Close the connection
                connection.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                // Handle the exception
            }
        });
        thread.start();


    }

    protected void onPostExecute(String response) throws JSONException {

        if (response != null) {
            try {
                JSONObject json = new JSONObject(response);
                JSONArray routes = json.getJSONArray("routes");

                if (routes.length() > 0) {
                    JSONObject route = routes.getJSONObject(0);
                    JSONArray legs = route.getJSONArray("legs");

                    if (legs.length() > 0) {
                        JSONObject leg = legs.getJSONObject(0);
                        JSONArray steps = leg.getJSONArray("steps");

                        for (int i = 0; i < steps.length(); i++) {
                            JSONObject step = steps.getJSONObject(i);

                            String travelMode = step.getString("travel_mode");
                            addRow("TravelMode: ", travelMode);

                            if (travelMode.equals("TRANSIT")) {
                                JSONObject transitDetails = step.getJSONObject("transit_details");
                                String lineName = transitDetails.getJSONObject("line").getString("name");
                                String lineNumber = transitDetails.getJSONObject("line").getString("short_name");
                                String departureStop = transitDetails.getJSONObject("departure_stop").getString("name");
                                String arrivalStop = transitDetails.getJSONObject("arrival_stop").getString("name");
                                //String distance = step.getJSONObject("distance").getString("text");
                                //String duration = step.getJSONObject("duration").getString("text");


                                //addRow("Distance: ", distance);
                                //addRow("Duration: ", duration);
                                addRow("Line Name: ", lineName);
                                addRow("Line Number: ", lineNumber);
                                addRow("Departure Stop: ", departureStop);
                                addRow("Arrival Stop: ", arrivalStop);

                            } else {
                                String distance = step.getJSONObject("distance").getString("text");
                                String duration = step.getJSONObject("duration").getString("text");

                                addRow("Distance: ", distance);
                                addRow("Duration: ", duration);

                            }

                        }
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "API response is null");
        }
    }

    private void addRow(String key, String value){
        requireActivity().runOnUiThread(() -> {
            TableRow tableRow = new TableRow(requireContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView column1 = new TextView(requireContext());
            column1.setText(key);
            tableRow.addView(column1);
            column1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

            TextView column2 = new TextView(requireContext());
            column2.setText(value);
            tableRow.addView(column2);
            column2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

            tableLayout.addView(tableRow);
            tableLayout.requestLayout();
        });


    }
}