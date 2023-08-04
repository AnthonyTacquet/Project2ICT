package com.empty.ui.dashboard;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;

import com.empty.global.NMBS.Connection;
import com.empty.ui.options.Bluetooth;
import com.empty.global.NMBS.Station;
import com.empty.logica.MainLogic;
import com.example.empty.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    String origin, destination;
    AutoCompleteTextView originInput;
    AutoCompleteTextView destinationInput;
    Button submitButton;
    TextView resultText;
    ListView stationList;
    private MainLogic logic;
    private ArrayList<String> stations;
    private ArrayList<String> nameStations;
    private ArrayList<String> arraylist = new ArrayList<>();
    private Station departureStation;
    private Station arrivalStation;
    private String result;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragview = inflater.inflate(R.layout.fragment_dashboard, container, false);

        resultText = fragview.findViewById(R.id.textView5);
        originInput = fragview.findViewById(R.id.autoComplete);
        destinationInput = fragview.findViewById(R.id.autoComplete2);
        submitButton = fragview.findViewById(R.id.submitButton);
        stationList = fragview.findViewById(R.id.listView);

        try {
            logic = new MainLogic();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        AutoCompleteTextView origenAuto = fragview.findViewById(R.id.autoComplete);
        AutoCompleteTextView destinationAuto = fragview.findViewById(R.id.autoComplete2);
        ArrayAdapter<String> adapter = null;
        try {
            adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, logic.GetNamesStations());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ArrayAdapter<String> listadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arraylist);
        stationList.setAdapter(listadapter);

        origenAuto.setThreshold(3);
        origenAuto.setAdapter(adapter);
        destinationAuto.setThreshold(3);
        destinationAuto.setAdapter(adapter);
        submitButton.setOnClickListener(
                view -> {
                    origin = originInput.getText().toString();
                    destination = destinationInput.getText().toString();
                    try {
                        List<String> newlist = logic.GetRoute(origin, destination).stream().map(Connection::shortString).collect(Collectors.toList());
                        arraylist.addAll(newlist);
                        listadapter.notifyDataSetChanged();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                    //result = logic.GetRoute(origin,destination);
                });

        originInput.setOnItemClickListener((parent, view, position, id) -> {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) ContextCompat.getSystemService(getContext(), InputMethodManager.class);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });


        return fragview;
    }
}