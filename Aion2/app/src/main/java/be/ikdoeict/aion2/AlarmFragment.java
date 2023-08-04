package be.ikdoeict.aion2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;

import be.ikdoeict.aion2.global.Main.Event;
import be.ikdoeict.aion2.global.Main.Location;

public class AlarmFragment extends Fragment {

    public AlarmFragment() {
    }
    public static AlarmFragment newInstance() {
        AlarmFragment fragment = new AlarmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragview = inflater.inflate(R.layout.fragment_alarms, container, false);

        LinearLayout buttonContainer = fragview.findViewById(R.id.buttonContainer);

        /*
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

             */

        return fragview;
    }

    String jsonString;

    /*
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
    }*/
    }

