package be.ikdoeict.aion2;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.List;

import be.ikdoeict.aion2.global.Main.DirectionsResponse;
import be.ikdoeict.aion2.global.Main.DirectionsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsFragment extends Fragment {

    private MapView mapView;
    private GoogleMap googleMap;
    private LatLng origin;
    private LatLng destination;
    private String originString;
    private String destinationString;
    private boolean update = true;
    private String apiKey = "AIzaSyBmVYbOKk_WQ6qaUnYJk-Zp5Y7mOtF45h4";
    private String mode = "transit";
    private String responseString = "";

    public MapsFragment(String origin, String destination, String mode) {
        this.originString = origin;
        this.destinationString = destination;
        if (mode != null)
            this.mode = mode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_maps);

        supportMapFragment.getMapAsync(map -> {
            googleMap = map;

            if (originString != null || destinationString != null && update == true) {
                update = false;
                origin = getLatLngFromAddress(originString);
                destination = getLatLngFromAddress(destinationString);

                getDirectionsAndDrawRoute();
            }
        });
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

    private void getDirectionsAndDrawRoute() {
        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the DirectionsService interface
        DirectionsService directionsService = retrofit.create(DirectionsService.class);

        // Make the API call to get directions
        Call<DirectionsResponse> call = directionsService.getDirections(
                origin.latitude + "," + origin.longitude,
                destination.latitude + "," + destination.longitude,
                apiKey, mode
        );

        // Execute the API call asynchronously
        call.enqueue(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {

                if (response.isSuccessful()) {
                    DirectionsResponse directionsResponse = response.body();
                    Gson gson = new Gson();
                    String jsonResponse = gson.toJson(directionsResponse);

                    if (directionsResponse != null && directionsResponse.routes.size() > 0) {
                        // Extract the polyline coordinates from the response
                        String polyline = directionsResponse.routes.get(0).overview_polyline.points;
                        List<LatLng> decodedPolyline = PolyUtil.decode(polyline);


                        // Create a PolylineOptions object and set its properties
                        PolylineOptions polylineOptions = new PolylineOptions()
                                .addAll(decodedPolyline)
                                .color(Color.RED)
                                .width(5);

                        // Add the polyline to the map
                        googleMap.addPolyline(polylineOptions);

                        // Adjust the camera to fit the bounds of the route
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(origin);
                        builder.include(destination);
                        LatLngBounds bounds = builder.build();
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
                    }
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }






}

