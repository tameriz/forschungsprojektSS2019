package hs.f.forschungsprojektss2019.activities;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import hs.f.forschungsprojektss2019.R;
import hs.f.forschungsprojektss2019.dao.PedometerHistory;

//SchrittverlaufActivity to display histroy of the pedometerDataSet
public class SchrittverlaufActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schrittverlauf);
        BarChart chart = (BarChart) findViewById(R.id.chart);
        final Description description = new Description();
        description.setText("Aktueller Schritteverlauf");
        chart.setDescription(description);
        chart.setData(addTestdataToBar());
        final View layout = findViewById(R.id.schrittverlauf);
        //chart.invalidate(); // refresh

    }

    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(getApplicationContext(), SchrittzaehlerActivity.class));
        setContentView(R.layout.activity_schrittzaehler);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    private List<PedometerHistory> createTestData(){

        final PedometerHistory ph1 = new PedometerHistory();
        ph1.date = "05.08.2019";
        ph1.user = "user1";
        ph1.stepcount = "7652";

        final PedometerHistory ph2 = new PedometerHistory();
        ph2.date = "06.08.2019";
        ph2.user = "user1";
        ph2.stepcount = "10652";

        final PedometerHistory ph3 = new PedometerHistory();
        ph3.date = "07.08.2019";
        ph3.user = "user1";
        ph3.stepcount = "4375";

        final PedometerHistory ph4 = new PedometerHistory();
        ph4.date = "08.08.2019";
        ph4.user = "user1";
        ph4.stepcount = "3662";

        final PedometerHistory ph5 = new PedometerHistory();
        ph5.date = "09.08.2019";
        ph5.user = "user1";
        ph5.stepcount = "23564";

        final PedometerHistory ph6 = new PedometerHistory();
        ph6.date = "10.08.2019";
        ph6.user = "user1";
        ph6.stepcount = "0";

        final PedometerHistory ph7 = new PedometerHistory();
        ph7.date = "11.08.2019";
        ph7.user = "user1";
        ph7.stepcount = "10000";

        List<PedometerHistory> pedometerHistoriesList = new ArrayList<>();
        pedometerHistoriesList.add(ph1);
        pedometerHistoriesList.add(ph2);
        pedometerHistoriesList.add(ph3);
        pedometerHistoriesList.add(ph4);
        pedometerHistoriesList.add(ph5);
        pedometerHistoriesList.add(ph6);
        pedometerHistoriesList.add(ph7);

        return pedometerHistoriesList;
    }

    private String getMacAddress(){
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    private BarData addTestdataToBar(){
        List<BarEntry> entries = new ArrayList<BarEntry>();
        final List<PedometerHistory> dummyData = createTestData();

        //REAL CODE not for presentation (Change dummyData to realData in the following for loop
        //final DbHelper dbHelper = new DbHelper(getApplicationContext());
        //final List<PedometerHistory> realData = dbHelper.getAllDataForOneUser(getApplicationContext(),getMacAddress
        // ());

        int i = 0;
        for (PedometerHistory ph : dummyData){
            final BarEntry entry = new BarEntry(i, Integer.parseInt(ph.stepcount));
            entries.add(new BarEntry(i, Integer.parseInt(ph.stepcount)));
            i++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "History");
        dataSet.setBarBorderColor(Color.GREEN);
        dataSet.setColor(Color.BLACK);
        dataSet.setBarBorderWidth(1);
        dataSet.setBarShadowColor(2);
        dataSet.setValueTextColor(3);
        return new BarData(dataSet);
    }
}
