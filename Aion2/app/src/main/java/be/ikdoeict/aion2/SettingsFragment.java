package be.ikdoeict.aion2;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;

    ListView devicesList;
    Button searchButton, pairedButton;
    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice activeDevice = null;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragview = inflater.inflate(R.layout.fragment_settings, container, false);
        //pairedText = findViewById(R.id.pairedText);
        searchButton = fragview.findViewById(R.id.searchButton);
        pairedButton = fragview.findViewById(R.id.pairedButton);
        devicesList = fragview.findViewById(R.id.listView);

        ArrayList<String> arraylist = new ArrayList<>();
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textView, arraylist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arraylist);

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
            arraylist.clear();
            adapter.notifyDataSetChanged();
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
                    //sendCommandBluetooth("START-SEQUENCE");
                }
                else
                    showToast("No device found");
            } else{
                showToast("Turn on bluetooth to get paired devices");
            }
        });
        return fragview;
    }

    private boolean checkPermission(){
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.BLUETOOTH,
                    android.Manifest.permission.BLUETOOTH_ADMIN, android.Manifest.permission.BLUETOOTH_SCAN, android.Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_ADVERTISE}, PERMISSION_REQUEST_CODE);
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == -1){
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}