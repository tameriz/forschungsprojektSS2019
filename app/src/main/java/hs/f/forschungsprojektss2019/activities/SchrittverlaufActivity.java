/*
 * SchrittverlaufActivity.java
 *
 * Created on 2019-07-04
 *
 * Copyright (C) 2019 Volkswagen AG, All rights reserved.
 */

/*
 * SchrittverlaufActivity.java
 *
 *
 */

package hs.f.forschungsprojektss2019.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import hs.f.forschungsprojektss2019.R;
import hs.f.forschungsprojektss2019.dao.InAppDatabase;
import util.DbHelper;

public class SchrittverlaufActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schrittverlauf);
    }

    //DbHelper dbHelper = new DbHelper(getBaseContext());
    // Gets the data repository in write mode
   // SQLiteDatabase db = dbHelper.getWritableDatabase();
    // Create a new map of values, where column names are the keys
    //ContentValues values = new ContentValues();
   // values.put(DbHelper.FeedEntry.COLUMN_NAME_TITLE, title);
   // values.put(DbHelper.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);
    // Insert the new row, returning the primary key value of the new row
    //long newRowId = db.insert(DbHelper.FeedEntry.TABLE_NAME, null, values);


    //Get Objekt of the new DB TODO DB NAME
    InAppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                            InAppDatabase.class, "database-name").build();
}
