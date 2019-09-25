package hs.f.forschungsprojektss2019.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import hs.f.forschungsprojektss2019.R;
import util.DbHelper;
import util.StepDetector;
import util.StepListener;

//SchrittzaehlerActivity = MAIN ACTIVITY . Handles Pedemeter.
public class SchrittzaehlerActivity extends Activity implements SensorEventListener, StepListener{

    private TextView steps;
    private SensorManager sensorManager;
    private Sensor sensor;
    private StepDetector simpleStepDetector;
    private int numSteps;
    private DbHelper dbHelper;
    private String today;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schrittzaehler);
        //Flush DB (for test)
        //getApplicationContext().deleteDatabase("PedometerHistory");
        dbHelper = new DbHelper(getApplicationContext());
        getDateOfToday();

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        steps = (TextView) findViewById(R.id.steps);
        setNumSteps();

        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        createButtons();
        steps.setText(String.valueOf(numSteps));
    }

    private void setNumSteps(){
        if (dbHelper.getAllDataForASpecificDay(getApplicationContext(), today, getMacAddress()).isEmpty()){
            numSteps = 0;
        } else{
            numSteps = Integer.valueOf(
                    dbHelper.getAllDataForASpecificDay(getApplicationContext(), today, getMacAddress())
                            .get(0).stepcount);
        }
    }

    private void getDateOfToday(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        today = df.format(c);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            simpleStepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){
    }

    private void createButtons(){
        //Buttons
        final Button schrittverlaufButton = findViewById(R.id.schrittverlauf);
        schrittverlaufButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Läd Seite des Schrittverlaufs
                startActivity(new Intent(getApplicationContext(), SchrittverlaufActivity.class));
                setContentView(R.layout.activity_schrittverlauf);
            }
        });

        final Button synchronisierenButton = findViewById(R.id.synchronisieren);
        synchronisierenButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Läd Seite "Synchronisieren"
                startActivity(new Intent(getApplicationContext(), SynchronisierenActivity.class));
                setContentView(R.layout.activity_synchronisieren);
            }
        });

        final Button beendenButton = findViewById(R.id.beenden);
        beendenButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                saveStepsInDb();
                // Beendet App
                finish();
                System.exit(0);
            }
        });

        final Button infoButton = findViewById(R.id.info);
        infoButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Info über App anzeigen
                startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                setContentView(R.layout.activity_info);
            }
        });

        final Button debugButton = findViewById(R.id.debug);
        debugButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // BLE DEBUG anzeigen
                startActivity(new Intent(getApplicationContext(), BluetoothConnector.class));
                setContentView(R.layout.activity_bluetooth);
            }
        });
    }

    private void saveStepsInDb(){
        if (dbHelper.getAllDataForASpecificDay(getApplicationContext(), today, getMacAddress()).isEmpty()){
            dbHelper.insertData(
                    dbHelper.createPedemeterHistoryObject(getMacAddress(), today, String.valueOf(numSteps)));
        } else{
            dbHelper.updateEntry(
                    dbHelper.createPedemeterHistoryObject(getMacAddress(), today, String.valueOf(numSteps)));
        }
    }

    @Override
    public void step(long timeNs){
        numSteps++;
        steps.setText(String.valueOf(numSteps));
    }

    private String getMacAddress(){
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }
}

