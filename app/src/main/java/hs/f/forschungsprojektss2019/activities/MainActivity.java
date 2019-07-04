/*
 * MainActivity.java
 *
 * Created on 2019-07-04
 *
 * Copyright (C) 2019 Volkswagen AG, All rights reserved.
 */

/*
 * MainActivity.java
 *
 */

package hs.f.forschungsprojektss2019.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import hs.f.forschungsprojektss2019.R;
import static util.Schrittzaehler.aktuelleSchritte;



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

public class MainActivity extends AppCompatActivity{

    private boolean isRunning;
    private TextView schrittanzahl;
    //Wird benötigt um Schritt täglich zu resetten
    //private AlarmClock resetClock;

    //Listener
    SensorEventListener sensorEventListenerCounter = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy){
        }

        @Override
        public void onSensorChanged(SensorEvent event){
            aktuelleSchritte = event.values[0];
            schrittanzahl.setText((int) aktuelleSchritte);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Startbildschrim
        setContentView(R.layout.activity_main);
        //Textfields
        // final TextView schritteText = findViewById(R.id.schritte);
        schrittanzahl = findViewById(R.id.schrittanzahl);
        // schrittanzahl.setText("0");
        createButtons();
    }

    @Override
    protected void onResume(){
        super.onResume();
        isRunning = true;
        createStepCounter();
    }

    @Override
    protected void onPause(){
        super.onPause();
        isRunning = false;
    }

    private void createStepCounter(){
        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor sensorStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        //final Sensor sensorStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (sensorStepCounter != null){
            sensorManager.registerListener(sensorEventListenerCounter, sensorStepCounter,
                                           SensorManager.SENSOR_DELAY_UI);
        } else{
            Toast.makeText(this, "Sensor Sensor.TYPE_STEP_COUNTER wurde nicht gefunden", Toast.LENGTH_SHORT).show();
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

        final Button laufStartenButton = findViewById(R.id.laufStarten);
        laufStartenButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Läd Seite "Lauf Starten"
                setContentView(R.layout.activity_lauf_starten);
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
