package hs.f.forschungsprojektss2019.activities;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import hs.f.forschungsprojektss2019.R;


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

public class SchrittzaehlerActivity extends Activity implements SensorEventListener{

    private static final String PREFS = SchrittzaehlerActivity.class.getName();
    private static final String PREFS_KEY = "last";
    private ProgressBar pb;
    private TextView steps;
    private Button reset;
    private Switch onOff;
    private SensorManager m;
    private Sensor s;
    private int last;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schrittzaehler);
        createButtons();
        pb = (ProgressBar) findViewById(R.id.pb);
        steps = (TextView) findViewById(R.id.steps);
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener((event) -> {
            updateSharedPrefs(this, last);
            updateUI();
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            m = getSystemService(SensorManager.class);
        } else{
            try{
                throw new Exception("TODO");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        s = m.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        onOff = (Switch) findViewById(R.id.on_off);
        onOff.setOnCheckedChangeListener((buttonView, isChecked) -> updateUI());
        onOff.setChecked(s != null);
        updateUI();
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        float[] values = sensorEvent.values;
        int _steps = (int) values[0];
        last = _steps;
        SharedPreferences prefs = getSharedPreferences(SchrittzaehlerActivity.PREFS, Context.MODE_PRIVATE);
        _steps -= prefs.getInt(PREFS_KEY, 0);
        this.steps.setText(String.format(Locale.US, "%d", _steps));
        if (pb.getVisibility() == View.VISIBLE){
            pb.setVisibility(View.GONE);
            this.steps.setVisibility(View.VISIBLE);
            reset.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){
    }

    public static void updateSharedPrefs(Context context, int last){
        SharedPreferences prefs = context.getSharedPreferences(SchrittzaehlerActivity.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(SchrittzaehlerActivity.PREFS_KEY, last);
        edit.apply();
    }

    private void updateUI(){
        reset.setVisibility(View.GONE);
        onOff.setEnabled(s != null);
        if (s != null){
            steps.setVisibility(View.GONE);
            if (onOff.isChecked()){
                m.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
                pb.setVisibility(View.VISIBLE);
            } else{
                m.unregisterListener(this);
                pb.setVisibility(View.GONE);
            }
        } else{
            steps.setVisibility(View.VISIBLE);
            //steps.setText(R.string.no_sensor);
            pb.setVisibility(View.GONE);
        }
    }

    private void createButtons(){
        //Buttons
        final Button schrittverlaufButton = findViewById(R.id.schrittverlauf);
        schrittverlaufButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Läd Seite des Schrittverlaufs
                setContentView(R.layout.activity_schrittverlauf);
            }
        });

        final Button synchronisierenButton = findViewById(R.id.synchronisieren);
        synchronisierenButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Läd Seite "Synchronisieren"
                setContentView(R.layout.activity_synchronisieren);
            }
        });

        final Button beendenButton = findViewById(R.id.beenden);
        beendenButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Beendet App
                finish();
                System.exit(0);
            }
        });
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

