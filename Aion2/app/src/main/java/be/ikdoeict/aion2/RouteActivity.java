package be.ikdoeict.aion2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;

import be.ikdoeict.aion2.global.Main.Event;

public class RouteActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private TextInputEditText originInput, destinationInput;
    private TabLayout tabLayout;
    private Spinner transportSpinner;
    private Button submitButton;
    private boolean updateRequired = true;
    private String mode = "transit";
    private String[] items = {"walking", "bicycling", "driving", "transit"};

    private Fragment mapsFragment = new MapsFragment(null, null, null);
    private Fragment travelFragment = new TravelFragment(null, null, null);

    private int tabId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Intent intent = getIntent();
        Event receivedObject = intent.getParcelableExtra("Event");

        submitButton = findViewById(R.id.submit_button);
        originInput = findViewById(R.id.edit_origin_text);
        destinationInput = findViewById(R.id.edit_destination_text);
        transportSpinner = findViewById(R.id.transport_spinner);
        tabLayout = findViewById(R.id.tab_layout);

        submitButton.setOnClickListener(v -> updateRequired = true);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transportSpinner.setAdapter(adapter);
        transportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (items.length < (int) id + 1)
                    return;
                mode = items[(int) id];
                updateRequired = true;
                requestLocationUpdates();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Switch the fragment based on the selected tab
                changeTab(tab.getPosition());
                tabId = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // No action needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No action needed
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && updateRequired == true) {
                    updateRequired = false;
                    Location location = locationResult.getLastLocation();
                    be.ikdoeict.aion2.global.Main.Location myLocation = null;

                    if (location != null){
                        try {
                            myLocation = fromAddressToLocation(getAddress(location.getLatitude(), location.getLongitude()));
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                        if (myLocation != null)
                            originInput.setText(myLocation.toString());
                    }

                    if (receivedObject != null)
                        destinationInput.setText(receivedObject.getLocation().toString());

                    // Use the location object here
                    if (myLocation != null){
                        mapsFragment = new MapsFragment(myLocation.toString(), receivedObject.getLocation().toString(), mode);
                        travelFragment = new TravelFragment(myLocation.toString(), receivedObject.getLocation().toString(), mode);
                    }
                    changeTab(tabId);

                }
            }
        };
    }

    private void changeTab(int id){
        switch (id) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, mapsFragment)
                        .commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, travelFragment)
                        .commit();
                break;
        }
    }


    public Address getAddress(double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses = null;
        addresses = geocoder.getFromLocation(latitude, longitude,1);
        if(addresses != null && addresses.size() > 0 ){
            Address address = addresses.get(0);
            return address;
        }
        return null;

    }

    public be.ikdoeict.aion2.global.Main.Location fromAddressToLocation(Address address){
        String street = address.getThoroughfare();
        String city = address.getLocality();
        int postalCode = Integer.parseInt(address.getPostalCode());
        String streetNumber = address.getSubThoroughfare();
        return new be.ikdoeict.aion2.global.Main.Location(street, streetNumber, postalCode, city);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates();
            } else {
                // Handle the case when the user denies the permission
            }
        }
    }

    // Request location updates
    private void requestLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000); // 5 seconds
        locationRequest.setFastestInterval(2000); // 2 seconds

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    // Stop receiving location updates when the activity stops
    @Override
    public void onStop() {
        super.onStop();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

}