package hs.f.forschungsprojektss2019.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PedometerHistory.class},
          version = 1)
public abstract class InAppDatabase extends RoomDatabase{
    public abstract PedometerHistoryDao pedometerHistoryDao();
}





