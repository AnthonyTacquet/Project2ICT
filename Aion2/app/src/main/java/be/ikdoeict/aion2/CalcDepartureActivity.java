package be.ikdoeict.aion2;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CalcDepartureActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_departure);

        NumberPicker numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(300);
        numberPicker.setValue(30);

        TextView arrivalTime;
        TextView departTime;
        TextView wakeUpTime;
        TextView routeTime;
        int readyUpTime;

        arrivalTime = findViewById(R.id.arrivalTime); //de tijd waarop je zou moeten toekomen o pde bestemming
        departTime = findViewById(R.id.departTime); //moment van vertrek thuis
        wakeUpTime = findViewById(R.id.wakeUpTime); //moment van opstaan
        routeTime = findViewById(R.id.routeTime); //tijd die de route duurt
        readyUpTime = numberPicker.getValue(); //tijd nodig om klaar te zijn voor vertrek

        //indien je hier iets mee wilt doen
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });

    }
}
