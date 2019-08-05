package hs.f.forschungsprojektss2019.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import hs.f.forschungsprojektss2019.R;
import util.Schrittzaehler;
import util.StepDetector;
import util.StepListener;


/*
 *
 * Wichtige Links:
 *
 * ALARMCLOCK
 * https://stackoverflow.com/questions/4459058/alarm-manager-example/8801990#8801990
 *https://developer.android.com/reference/android/provider/AlarmClock
 *
 * SHARED PREFERENCES
 * https://developer.android.com/training/data-storage/shared-preferences#java
 *
 * ROOM DATABASE
 *https://developer.android.com/training/data-storage/room#java
 *
 *
 * Bluethooth-Low-Energy
 *
 *https://developer.android.com/guide/topics/connectivity/bluetooth-le#java
 *
 * Info unterschied Activiy und Service (wir brauchen also auch einen Service/Code muss nochma rumgeschubst werden:
 * An activity is implemented as a subclass of Activity and you can learn more about it in the Activities developer
 * guide. A service is a component that runs in the background to perform long-running operations or to perform work
 * for remote processes. A service does not provide a user interface.
 * */

public class SchrittzaehlerActivity extends Activity implements SensorEventListener, StepListener {

    private static final String PREFS = SchrittzaehlerActivity.class.getName();
    private static final String PREFS_KEY = "last";
    private TextView steps;
    private SensorManager m;
    private Sensor s;
    private int last;

    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private TextView TvSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schrittzaehler);


        createButtons();
        steps = (TextView) findViewById(R.id.steps);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            m = getSystemService(SensorManager.class);
        } else {
            try {
                throw new Exception("TODO");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        s = m.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public static void updateSharedPrefs(Context context, int last) {
        SharedPreferences prefs = context.getSharedPreferences(SchrittzaehlerActivity.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(SchrittzaehlerActivity.PREFS_KEY, last);
        edit.apply();
    }

    private void createButtons() {
        //Buttons
        final Button schrittverlaufButton = findViewById(R.id.schrittverlauf);
        schrittverlaufButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Läd Seite des Schrittverlaufs
                startActivity(new Intent(getApplicationContext(), SchrittverlaufActivity.class));
                setContentView(R.layout.activity_schrittverlauf);
            }
        });

        final Button synchronisierenButton = findViewById(R.id.synchronisieren);
        synchronisierenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Läd Seite "Synchronisieren"
                startActivity(new Intent(getApplicationContext(), SynchronisierenActivity.class));
                setContentView(R.layout.activity_synchronisieren);
            }
        });

        final Button beendenButton = findViewById(R.id.beenden);
        beendenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Beendet App
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }
}

//
//    private boolean isRunning;
//    private TextView schrittanzahl;
//    //Wird benötigt um Schritt täglich zu resetten
//    //private AlarmClock resetClock;
//
//    //Listener
//    SensorEventListener sensorEventListenerCounter = new SensorEventListener(){
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy){
//        }
//
//        @Override
//        public void onSensorChanged(SensorEvent event){
//            aktuelleSchritte = event.values[0];
//            schrittanzahl.setText((int) aktuelleSchritte);
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        //Startbildschrim
//        setContentView(R.layout.activity_schrittzaehler);
//        //Textfields
//        // final TextView schritteText = findViewById(R.id.schritte);
//        schrittanzahl = findViewById(R.id.schrittanzahl);
//        // schrittanzahl.setText("0");
//        createButtons();
//    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//        isRunning = true;
//        createStepCounter();
//    }
//
//    @Override
//    protected void onPause(){
//        super.onPause();
//        isRunning = false;
//    }
//
//    // von Marius geschrieben
//    private void createStepCounter(){
//        //von Marius
//        /* final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        final Sensor sensorStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        //final Sensor sensorStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
//
//        if (sensorStepCounter != null){
//            sensorManager.registerListener(sensorEventListenerCounter, sensorStepCounter,
//                                           SensorManager.SENSOR_DELAY_UI);
//        } else{
//            Toast.makeText(this, "Sensor Sensor.TYPE_STEP_COUNTER wurde nicht gefunden", Toast.LENGTH_SHORT).show();
//        }*/
//
//    }
//
//    }

