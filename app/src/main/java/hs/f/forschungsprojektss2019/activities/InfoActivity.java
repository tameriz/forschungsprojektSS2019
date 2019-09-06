/*
 * InfoActivity.java
 *
 * Created on 2019-09-06
 *
 * Copyright (C) 2019 Volkswagen AG, All rights reserved.
 */

package hs.f.forschungsprojektss2019.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import hs.f.forschungsprojektss2019.R;

public class InfoActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(getApplicationContext(), SchrittzaehlerActivity.class));
        setContentView(R.layout.activity_schrittzaehler);
    }
}
