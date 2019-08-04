package hs.f.forschungsprojektss2019.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PedometerHistoryDao{
    @Query("SELECT * FROM pedometerhistory")
    List<PedometerHistory> getAllForAllUsers();

    @Query("SELECT * FROM PedometerHistory WHERE date = :date AND user = :user")
    List<PedometerHistory> getStepsForASpecificDay(String date, String user); //Format of dd.mm.yyyy

    @Query("SELECT * FROM PedometerHistory WHERE user = :user")
    List<PedometerHistory> getHistoryForSpecificUser(String user);

    @Insert
    void insertAll(PedometerHistory... histories);

    @Delete
    void delete(PedometerHistory history);
}

