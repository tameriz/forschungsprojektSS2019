/*
 * PedometerHistoryDao.java
 *
 * Created on 2019-07-16
 *
 * Copyright (C) 2019 Volkswagen AG, All rights reserved.
 */

package hs.f.forschungsprojektss2019.dao;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

    @Dao
    public interface PedometerHistoryDao {
        @Query("SELECT * FROM pedometerhistory")
        List<PedometerHistory> getAll();

        @Query("SELECT * FROM PedometerHistory WHERE date = :date")
        List<PedometerHistory> getStepsForASpecificDay(int date);

        @Insert
        void insertAll(PedometerHistory... histories);

        @Delete
        void delete(PedometerHistory history);
    }

