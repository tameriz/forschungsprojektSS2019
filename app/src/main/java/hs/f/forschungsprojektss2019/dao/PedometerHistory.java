//https://developer.android.com/training/data-storage/room#java

package hs.f.forschungsprojektss2019.dao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PedometerHistory{
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "user")
    public String user;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "stepcount")
    public String stepcount;
}

