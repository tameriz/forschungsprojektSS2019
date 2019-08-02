/*
 * SynchronisierenActivity.java
 *
 * Created on 2019-07-04
 *
 * Copyright (C) 2019 Volkswagen AG, All rights reserved.
 */

/*
 * SynchronisierenActivity.java
 *
 *
 */

package hs.f.forschungsprojektss2019.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import hs.f.forschungsprojektss2019.R;
import hs.f.forschungsprojektss2019.dao.InAppDatabase;

//Le = Lowenergy

@TargetApi(21)
public class SynchronisierenActivity extends AppCompatActivity{

    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 10000;
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private BluetoothGatt mGatt;
    private String BLUETOOTH_NAME="TODO";
    //TODO
    private java.util.UUID UUID;
    private int propierties;
    private int permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schrittzaehler);
        mHandler = new Handler();
        //Check if BLE is enabled on the device
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
            Toast.makeText(this, "BLE Not Supported", Toast.LENGTH_SHORT).show();
            finish();
        }
        //create Bluethoothmanager with Adapter
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()){
            //Create BLE-Intent
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else{
            //Handle SDK Build Version above > 21
            if (Build.VERSION.SDK_INT >= 21){
                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
                settings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
                filters = new ArrayList<ScanFilter>();
            }
            //Start Scan of Devices
            scanDevice(true);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()){
            scanDevice(false);
        }
    }

    @Override
    protected void onDestroy(){
        if (mGatt == null){
            return;
        }
        mGatt.close();
        mGatt = null;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_ENABLE_BT){
            if (resultCode == Activity.RESULT_CANCELED){
                //Bluetooth not enabled.
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scanDevice(final boolean enable){
        if (enable){
            mHandler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    if (Build.VERSION.SDK_INT < 21){
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    } else{
                        mLEScanner.stopScan(mScanCallback);
                    }
                }
            }, SCAN_PERIOD);
            if (Build.VERSION.SDK_INT < 21){
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            } else{
                mLEScanner.startScan(filters, settings, mScanCallback);
            }
        } else{
            if (Build.VERSION.SDK_INT < 21){
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            } else{
                mLEScanner.stopScan(mScanCallback);
            }
        }
    }

    private ScanCallback mScanCallback = new ScanCallback(){
        @Override
        public void onScanResult(int callbackType, ScanResult result){
            Log.i("callbackType", String.valueOf(callbackType));
            Log.i("result", result.toString());
            BluetoothDevice btDevice = result.getDevice();
            connectToDevice(btDevice);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results){
            for (ScanResult sr : results){
                Log.i("ScanResult - Results", sr.toString());
            }
        }

        @Override
        public void onScanFailed(int errorCode){
            Log.e("Scan Failed", "Error Code: " + errorCode);
        }
    };

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback(){
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord){
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    Log.i("onLeScan", device.toString());
                    connectToDevice(device);
                }
            });
        }
    };

    public void connectToDevice(BluetoothDevice device){
        if (mGatt == null){
            if(device.getName()==BLUETOOTH_NAME){
                mGatt = device.connectGatt(this, false, gattCallback);
                scanDevice(false);// will stop after first device detection
            }
        }
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback(){
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState){
            Log.i("onConnectionStateChange", "Status: " + status);
            switch (newState){
                case BluetoothProfile.STATE_CONNECTED:
                    Log.i("gattCallback", "STATE_CONNECTED");
                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Log.e("gattCallback", "STATE_DISCONNECTED");
                    break;
                default:
                    Log.e("gattCallback", "STATE_OTHER");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status){
            List<BluetoothGattService> services = gatt.getServices();
            Log.i("onServicesDiscovered", services.toString());
           //TODO: MUSS ANGEPASST WERDEN AUF UNSEREN SERVICE
            gatt.readCharacteristic(services.get(1).getCharacteristics().get(0));

            //TODO ????? WELCHES DAVON?
            final BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID,propierties,permissions);
            gatt.writeCharacteristic(characteristic);

            final BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(UUID,permissions);
            gatt.writeDescriptor(descriptor);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status){
            Log.i("onCharacteristicRead", characteristic.toString());
            gatt.disconnect();
        }

    };
}
