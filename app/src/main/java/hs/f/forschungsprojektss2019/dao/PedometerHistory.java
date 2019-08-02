//https://developer.android.com/training/data-storage/room#java

package hs.f.forschungsprojektss2019.dao;

import javax.xml.datatype.XMLGregorianCalendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PedometerHistory{
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "date")
    public int date; //TODO convert to XML Gregorian Calender

    @ColumnInfo(name = "stepcount")
    public int stepcount;
}

