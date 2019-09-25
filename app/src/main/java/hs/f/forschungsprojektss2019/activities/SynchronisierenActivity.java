package hs.f.forschungsprojektss2019.activities;

import java.nio.charset.Charset;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import hs.f.forschungsprojektss2019.R;

//SynchronisierenActivity to handle Snyc with App and Hub
public class SynchronisierenActivity extends Activity{

    private BluetoothLeAdvertiser advertiser;
    private UUID parcelUuid = UUID.randomUUID();//UUID.fromString("fffffffffffffffffffffffffff5");
    private TextView textfield;

    public SynchronisierenActivity(){
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try{
            if (!BluetoothAdapter.getDefaultAdapter().isMultipleAdvertisementSupported()){
                Toast.makeText(this, "Multiple advertisement not supported", Toast.LENGTH_SHORT).show();
            }

            setContentView(R.layout.activity_synchronisieren);

            textfield = (TextView) findViewById(R.id.message);
            textfield.append("MacAdress " + getMacAddress() + "\n");
            textfield.append(BluetoothAdapter.getDefaultAdapter().getAddress() + " BlEAdapter Address \n");
            textfield.append("Uuid " + parcelUuid.toString() + "\n");

            getBleAdvertiser();
            AdvertiseSettings settings = getAdvertiseSettings();
            createAdvertiserCallBackAndStartAdvertising(settings);
        } catch (Exception e){
            textfield.append(e.getMessage());
        }
    }

    private String getMacAddress(){
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    private void createAdvertiserCallBackAndStartAdvertising(final AdvertiseSettings settings){
        ParcelUuid pUuid = new ParcelUuid(parcelUuid);

        //REAL DATA HERE
        String stringToSend = "TESTITEST";

        AdvertiseData data = new AdvertiseData.Builder().setIncludeDeviceName(true).addServiceUuid(pUuid)
                                                        .addServiceData(pUuid,
                                                                        stringToSend.getBytes(Charset.forName("UTF-8")))
                                                        .build();

        AdvertiseCallback advertisingCallback = new AdvertiseCallback(){
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect){
                super.onStartSuccess(settingsInEffect);
            }

            @Override
            public void onStartFailure(int errorCode){
                Log.e("BLE", "Advertising onStartFailure: " + errorCode);
                super.onStartFailure(errorCode);
            }
        };
        advertiser.startAdvertising(settings, data, advertisingCallback);
        textfield.append("\n Start Advertising \n");
    }

    private AdvertiseSettings getAdvertiseSettings(){
        //Create Advertising Settings
        return new AdvertiseSettings.Builder().setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                                              .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                                              .setConnectable(false).build();
    }

    private void getBleAdvertiser(){
        //Get BLE Advertiser
        advertiser = BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();
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
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), SchrittzaehlerActivity.class));
        setContentView(R.layout.activity_schrittzaehler);
    }
}
