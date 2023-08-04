package com.empty.ui.options;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empty.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Bluetooth extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;

    ListView devicesList;
    Button searchButton, pairedButton;
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice activeDevice = null;
    private BluetoothSocket socket;
    private OutputStream outputStream;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        //pairedText = findViewById(R.id.pairedText);
        searchButton = findViewById(R.id.searchButton);
        pairedButton = findViewById(R.id.pairedButton);
        devicesList = findViewById(R.id.listView);

        ArrayList<String> arraylist = new ArrayList<>();
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView, arraylist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);

        devicesList.setAdapter(adapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Permission is granted, proceed with the API call

        searchButton.setOnClickListener(v -> {
            if(!checkPermission()){
                showToast("You don't have sufficient bluetooth permissions");
                return;
            }
            if (!bluetoothAdapter.isDiscovering()) {
                showToast("Making your device discoverable");
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(intent, REQUEST_DISCOVER_BT);
            }
        });

        pairedButton.setOnClickListener(v -> {
            if(!checkPermission()){
                showToast("You don't have sufficient bluetooth permissions");
                return;
            }
            if (bluetoothAdapter.isEnabled()){
                Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                for (BluetoothDevice device : devices){
                    arraylist.add(device.getName());
                    adapter.notifyDataSetChanged();
                }
            } else{
                showToast("Turn on bluetooth to get paired devices");
            }
        });

        devicesList.setOnItemClickListener((parent, view, position, id) -> {
            if(!checkPermission()){
                showToast("You don't have sufficient bluetooth permissions");
                return;
            }
            if (bluetoothAdapter.isEnabled()){
                Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                List<BluetoothDevice> list = new ArrayList<>(devices);
                activeDevice = list.get(position);
                if (activeDevice != null){
                    showToast("Selected device: " + activeDevice.getName());
                    sendCommandBluetooth("START-SEQUENCE");
                }
                else
                    showToast("No device found");
            } else{
                showToast("Turn on bluetooth to get paired devices");
            }
        });


    }

    private void sendCommandBluetooth(String command){
        Thread thread = new Thread(() -> {
            if (activeDevice == null)
                return;
            final UUID MY_UUID = UUID.randomUUID(); // Replace with your UUID
            try {
                if(!checkPermission()){
                    showToast("You don't have sufficient bluetooth permissions");
                    return;
                }
                socket = activeDevice.createRfcommSocketToServiceRecord(MY_UUID);
                socket.connect();
                outputStream = socket.getOutputStream();

                // Send a message to the Bluetooth device
                outputStream.write(command.getBytes());

                showToast("Message sent successfully.");
            } catch (IOException e) {
                showToast("Error: " + e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();

    }

    private boolean checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_ADVERTISE}, PERMISSION_REQUEST_CODE);
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK){
                    // bluetooth is on
                    showToast("Bluetooth is on");
                } else {
                    //user denied to turn bluetooth on
                    showToast("Couldn't enable bluetooth");
                } break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}