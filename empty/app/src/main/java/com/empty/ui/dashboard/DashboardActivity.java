package com.empty.ui.dashboard;

import com.empty.ui.options.Bluetooth;
import com.empty.global.NMBS.Station;
import com.empty.logica.MainLogic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.empty.R;

import org.json.JSONException;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    String origin, destination;
    AutoCompleteTextView originInput;
    AutoCompleteTextView destinationInput;
    Button submitButton;
    TextView resultText;
    private MainLogic logic;
    private ArrayList<Station> stations;
    private ArrayList<String> nameStations;
    private Station departureStation;
    private Station arrivalStation;
    private String result;

    public DashboardActivity() throws JSONException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard2);


        resultText = (TextView) findViewById(R.id.textView5);
        originInput = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        destinationInput = (AutoCompleteTextView) findViewById(R.id.autoComplete2);
        submitButton = (Button) findViewById(R.id.submitButton);
        try {
            logic = new MainLogic();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        AutoCompleteTextView origenAuto = findViewById(R.id.autoComplete);
        AutoCompleteTextView destinationAuto = findViewById(R.id.autoComplete2);
        ArrayAdapter<String> adapter = null;
        try {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, logic.GetNamesStations());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        origenAuto.setThreshold(3);
        origenAuto.setAdapter(adapter);
        destinationAuto.setThreshold(3);
        destinationAuto.setAdapter(adapter);
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        origin = originInput.getText().toString();
                        destination = destinationInput.getText().toString();
                        //try {
                            //result = logic.GetRoute(origin,destination);
                        //} catch (InterruptedException e) {
                        //    throw new RuntimeException(e);
                        //}
                        resultText.setText(result);
                    }
        });

        Button bluetoothButton = findViewById(R.id.bluetoothButton);
        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, Bluetooth.class);
                startActivity(intent);
            }
        });
    }
}
