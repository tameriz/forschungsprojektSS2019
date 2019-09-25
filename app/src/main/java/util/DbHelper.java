package util;

import java.util.List;

import android.content.Context;
import androidx.room.Room;
import hs.f.forschungsprojektss2019.dao.InAppDatabase;
import hs.f.forschungsprojektss2019.dao.PedometerHistory;

//DbHelper for handle SQL
public class DbHelper{

    InAppDatabase db;

    public DbHelper(Context applicationContext){
        db = Room.databaseBuilder(applicationContext, InAppDatabase.class, "PedometerHistory").allowMainThreadQueries().build();
    }

    public List<PedometerHistory> getAllDataForAllUsers(Context context){
        return db.pedometerHistoryDao().getAllForAllUsers();
    }

    public List<PedometerHistory> getAllDataForOneUser(Context context, String user){
        return db.pedometerHistoryDao().getHistoryForSpecificUser(user);
    }

    public List<PedometerHistory> getAllDataForASpecificDay(Context context, String data, String user){
        return db.pedometerHistoryDao().getStepsForASpecificDay(data, user);
    }

    public void insertData(PedometerHistory... value){
        db.pedometerHistoryDao().insertAll(value);
    }

    public void deleteEntry(PedometerHistory value){
        db.pedometerHistoryDao().delete(value);
    }

    public void updateEntry(PedometerHistory value){
        db.pedometerHistoryDao().update(value);
    }

    public PedometerHistory createPedemeterHistoryObject(String user, String date, String stepcount){
        PedometerHistory pedometerHistory = new PedometerHistory();
        pedometerHistory.user = user;
        pedometerHistory.date = date;
        pedometerHistory.stepcount = stepcount;

        return pedometerHistory;
    }
}
