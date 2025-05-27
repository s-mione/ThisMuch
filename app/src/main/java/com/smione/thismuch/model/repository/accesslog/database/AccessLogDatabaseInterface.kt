package com.smione.thismuch.model.repository.accesslog.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.smione.thismuch.model.repository.accesslog.database.entity.AccessLogEntity

@Dao
interface AccessLogDatabaseInterface {

    @Query("SELECT * FROM AccessLogEntity")
    fun getAll(): List<AccessLogEntity>

    @Query("SELECT * FROM AccessLogEntity ORDER BY time_off DESC")
    fun getAllSortedByTimeDesc(): List<AccessLogEntity>

    @Insert
    fun insertAll(vararg users: AccessLogEntity)

    @Delete
    fun delete(user: AccessLogEntity)

    @Query("DELETE FROM AccessLogEntity")
    fun deleteAll()
}