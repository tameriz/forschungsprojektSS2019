package hs.f.forschungsprojektss2019.dao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Entity for the RoomDb (Table and Columns)
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

