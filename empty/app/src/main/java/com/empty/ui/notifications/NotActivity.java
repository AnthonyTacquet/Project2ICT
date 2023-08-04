package com.empty.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.empty.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class NotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notifications);

        LinearLayout buttonContainer = findViewById(R.id.buttonContainer);


        JsonObject json = getJSONThing(this, "alarms.json");

        Gson gson = new Gson();
        for (String key : json.keySet()) {
            JsonElement inhoud = json.get(key);
            String alarmTime = gson.fromJson(inhoud, String.class);
            Button button = new Button(this);
            button.setText(alarmTime);
            buttonContainer.addView(button);
        }

        String alarm1 = json.get("alarm1").getAsString();
    }



    String jsonString;

    private JsonObject getJSONThing(Context context, String filename) {
        try {
            InputStream inputStream = getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();

            return  jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





}



