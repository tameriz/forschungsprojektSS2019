package util;

import java.util.List;
import android.content.Context;
import androidx.room.Room;
import hs.f.forschungsprojektss2019.dao.InAppDatabase;
import hs.f.forschungsprojektss2019.dao.PedometerHistory;

public class DbHelper{

    InAppDatabase db;

    public DbHelper(Context applicationContext){
        db = Room.databaseBuilder(applicationContext, InAppDatabase.class, "PedometerHistory").build();
    }

    public List<PedometerHistory> getAllData(Context context){
        return db.pedometerHistoryDao().getAllForAllUsers();
    }

    public List<PedometerHistory> getAllDataForOneUser(Context context, String user){
        return db.pedometerHistoryDao().getHistoryForSpecificUser(user);
    }

    public List<PedometerHistory> getAllData(Context context, String data, String user){
        return db.pedometerHistoryDao().getStepsForASpecificDay(data, user);
    }

    public void insertData(PedometerHistory... value){
        db.pedometerHistoryDao().insertAll(value);
    }

    public void delteEntry(PedometerHistory value){
        db.pedometerHistoryDao().delete(value);
    }
}
